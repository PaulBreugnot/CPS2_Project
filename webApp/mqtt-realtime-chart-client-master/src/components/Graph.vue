<template>
    <div>
        <div class="container-fluid">
            <div class="row text-center">

                <!-- Panel div start -->
                <!--                <div class="col-xs-12 col-lg-4 col-lg-offset-4">
                    <img src="../assets/logo.png" width="100" height="100">
                </div>-->

                <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                    <h1>Status: {{connStatus}}</h1>

                    <!-- Panel div start -->
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Values</h3>
                        </div>
                        <div class="panel-body">
                            <!-- Chart container -->
                            <div id="chart_container">
                                <div id="y_axis"></div>
                                <div id="demo_chart" ref="panel"></div>
                            </div>
                            <!-- End of chart container -->
                        </div>
                        <div class="panel-footer">
                            <p v-if="displayedValues.length > 0">
                                <small v-for="topic in topics">
                                    <span v-bind:style="{ color: dvColors[topic]}"> {{displayedValues[0][topic]}} </span>{{measures[topics.indexOf(topic)]}}
                                </small>
                            </p>
                            <p v-else>
                                <img src="../assets/Loading_icon.gif">
                            </p>
                        </div>
                    </div>
                    <!-- Panel div end -->

                    <!-- Range slider chart-refresh control -->
                    <div class="col-xs-6 col-xs-offset-3 col-md-6 col-md-offset-3 col-lg-8 col-lg-offset-2">
                        <input v-model="renderEveryNth" type="range" min="1" max="20" value="1">
                        <p>Render after <strong>{{renderEveryNth}}</strong> message(s)</p>
                    </div>
                    <!-- End of range slider -->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import io from 'socket.io-client'
    import Rickshaw from 'rickshaw'
    import 'rickshaw/rickshaw.min.css'
    import 'bootstrap/dist/css/bootstrap.css'
    //var socket = io.connect("http://ec2-54-236-113-5.compute-1.amazonaws.com:9001");
    // Test with : mosquitto_sub -h ec2-54-236-113-5.compute-1.amazonaws.com -p 1883 -t test_topic
    //var socket = io.connect("http://localhost:3000");
    var magnitudeChart;

    const client = mqtt.connect("ws://ec2-54-236-113-5.compute-1.amazonaws.com:9001");

    var mini;
    var maxi;

    function HSVtoRGB(h, s, v) {
        var r, g, b, i, f, p, q, t;
        if (arguments.length === 1) {
            s = h.s, v = h.v, h = h.h;
        }
        i = Math.floor(h * 6);
        f = h * 6 - i;
        p = v * (1 - s);
        q = v * (1 - f * s);
        t = v * (1 - (1 - f) * s);
        switch (i % 6) {
            case 0:
                r = v, g = t, b = p;
                break;
            case 1:
                r = q, g = v, b = p;
                break;
            case 2:
                r = p, g = v, b = t;
                break;
            case 3:
                r = p, g = q, b = v;
                break;
            case 4:
                r = t, g = p, b = v;
                break;
            case 5:
                r = v, g = p, b = q;
                break;
        }
        return {
            r: Math.round(r * 255),
            g: Math.round(g * 255),
            b: Math.round(b * 255)
        };
    }

    function componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }

    function rgbToHex(r, g, b) {
        return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
    }

    const main = {
        name: 'graph',
        data() {
            return {
                messageSeries: [],
                renderEveryNth: 1,
                updateInterval: 20,
                streamFrequency: 50,
                connStatus: "Disconnected",
                messageIndex: 0,
                topics: [],
                measures: [],
                displayedValues: [],
                dvColors: {},
                lastValue: {}
            }
        },
        mounted() {

            const tempMain = this;

            client.on("connect", function() {

                const req = new XMLHttpRequest();

                req.open('GET', 'http://192.168.12.1:8080/api/sensorlayers', true);

                req.onload = function() {
                    // Ici, this.readyState égale XMLHttpRequest.DONE .
                    if (req.status === 200) {
                        const layers = JSON.parse(req.responseText);

                        layers.forEach(function(layer) {

                            const sensors = layer.sensors;

                            sensors.forEach(function(sensor) {

                                if (sensor.topic) {

                                    const availableMeasures = sensor.availableMeasures;

                                    availableMeasures.forEach(function(measure) {

                                        client.subscribe(sensor.topic + "/metrics/" + measure.type, function(err) {
                                            if (err) {
                                                console.log(err);
                                            }
                                        });

                                        tempMain.measures.push(measure.unit);
                                        tempMain.topics.push(sensor.topic + "/metrics/" + measure.type);
                                    });

                                }
                            });
                        });

                        let index = 0;

                        tempMain.topics.forEach(function(elt) {
                            const color = HSVtoRGB(index / tempMain.topics.length, 1, 0.63);
                            tempMain.dvColors[elt] = rgbToHex(color.r, color.g, color.b);
                            index++;
                        });

                        tempMain.initChart();
                        tempMain.openSocketListeners();

                    } else {
                        console.log("Status de la réponse: %d (%s)", req.status, req.statusText);
                    }
                };

                req.send(null);
            });
        },
        watch: {
            renderEveryNth: function() {
                this.messageSeries = [];
                this.messageIndex = 0;
            }
        },
        methods: {
            /* Rickshaw.js initialization */
            initChart() {

                let listSeries = [];

                for (var i = 0; i < this.topics.length; i++) {
                    listSeries.push({
                        name: this.topics[i],
                        color: this.dvColors[this.topics[i]]
                    });
                }

                magnitudeChart = new Rickshaw.Graph({
                    element: document.querySelector("#demo_chart"),
                    width: "500",
                    height: "180",
                    renderer: "scatterplot",
                    min: 0,
                    max: 0,
                    series: new Rickshaw.Series.FixedDuration(listSeries, undefined, {
                        timeInterval: this.updateInterval,
                        maxDataPoints: 100,
                        timeBase: new Date().getTime() / 1000

                    })
                });

                magnitudeChart.renderer.dotSize = 2;

                var y_axis = new Rickshaw.Graph.Axis.Y({
                    graph: magnitudeChart,
                    orientation: 'left',
                    tickFormat: function(y) {
                        return y.toFixed(1);
                    },
                    ticks: 5,
                    element: document.getElementById('y_axis'),
                });

                this.resizeChart(magnitudeChart);

                window.addEventListener('resize', () => {
                    this.resizeChart(magnitudeChart)
                });

            },
            resizeChart(chart) {
                chart.configure({
                    width: this.$refs.panel.clientWidth ? this.$refs.panel.clientWidth : document.getElementById("graphContainer").style.width,
                });
                chart.render();
            },
            /* Insert received datapoints into the chart */
            insertDatapoints(messages, chart) {

                const tempMain = this;

                for (let i = 0; i < messages.length; i++) {

                    let voltageData = {};

                    this.topics.forEach(function(key) {
                        if (messages[i][key]) {

                            const value = parseFloat(messages[i][key]);

                            voltageData[key] = value;

                            if (mini && maxi) {
                                if (value < mini) {
                                    mini = value;
                                }
                                if (value > maxi) {
                                    maxi = value;
                                }
                            } else {
                                mini = value;
                                maxi = value;
                            }
                        }
                    });

                    if (voltageData) {
                        chart.series.addData(voltageData);
                    }
                }

                magnitudeChart.min = mini - 5;
                magnitudeChart.max = maxi + 5;

                chart.render();
            },
            /* Update displayed values every second on average */
            updateDisplayedValues() {
                this.displayedValues = this.messageSeries;
                if (this.messageIndex == this.streamFrequency) {
                    this.messageIndex = 0;
                    this.displayedValues = this.messageSeries;
                } else if (this.messageIndex == 0) {
                    this.displayedValues = this.messageSeries;
                    this.messageIndex++;
                } else {
                    this.messageIndex++;
                };
            },
            openSocketListeners() {
                /*
                socket.on('connect', () => {
                    this.connStatus = "Connected";
                });

                socket.on('disconnect', () => {
                    this.connStatus = "Disconnected";
                });
                */

                // Update chart after every #renderEveryNth message
                //socket.on('voltageData', (message) => {
                /*
                socket.on('test_topic', (message) => {

                    console.log(message);

                    // Check if displayed values have to be updated
                    this.updateDisplayedValues();

                    // Push stream data to current series, if it's not yet render-time
                    if (this.messageSeries.length < this.renderEveryNth) {
                        this.messageSeries.push(message);
                    }

                    // Render-time!
                    if (this.messageSeries.length == this.renderEveryNth) {
                        this.insertDatapoints(this.messageSeries, magnitudeChart);
                        this.messageSeries = [];
                    }
                });
                */

                client.on("connect", () => {
                    this.connStatus = "Connected";
                });

                client.on("close", () => {
                    this.connStatus = "Disconnected";
                });

                this.connStatus = client.connected ? "Connected" : "Disconnected";

                client.on("message", (topic, payload) => {
                    const messageReceived = new TextDecoder("utf-8").decode(payload);
                    this.lastValue[topic] = parseFloat(messageReceived);
                });

                setInterval(() => {
                    if (Object.keys(this.lastValue).length) {

                        // Check if displayed values have to be updated
                        this.updateDisplayedValues();

                        // Push stream data to current series, if it's not yet render-time
                        if (this.messageSeries.length < this.renderEveryNth) {
                            this.messageSeries.push(this.lastValue);

                        }

                        // Render-time!
                        if (this.messageSeries.length == this.renderEveryNth) {
                            this.insertDatapoints(this.messageSeries, magnitudeChart);
                            this.messageSeries = [];
                        }

                        this.lastValue = {};
                    }

                }, 100)

            }
        },
        computed: {
            myCustomStyles() {
                return {
                    tree: {
                        height: 'auto',
                        maxHeight: '300px',
                        overflowY: 'visible',
                        display: 'inline-block'
                    },
                    row: {
                        width: '500px',
                        cursor: 'pointer',
                        child: {
                            height: '35px'
                        }
                    },
                    addNode: {
                        class: 'custom_class',
                        style: {
                            color: '#007AD5'
                        }
                    },
                    editNode: {
                        class: 'custom_class',
                        style: {
                            color: '#007AD5'
                        }
                    },
                    deleteNode: {
                        class: 'custom_class',
                        style: {
                            color: '#EE5F5B'
                        }
                    },
                    selectIcon: {
                        class: 'custom_class',
                        style: {
                            color: '#007AD5'
                        },
                        active: {
                            class: 'custom_class',
                            style: {
                                color: '#2ECC71'
                            }
                        }
                    },
                    text: {
                        style: {},
                        active: {
                            style: {
                                'font-weight': 'bold',
                                color: '#2ECC71'
                            }
                        }
                    }
                };
            },
            myCustomOptions() {
                return {
                    treeEvents: {
                        expanded: {
                            state: true,
                            fn: null,
                        },
                        collapsed: {
                            state: false,
                            fn: null,
                        },
                        selected: {
                            state: false,
                            fn: null,
                        },
                        checked: {
                            state: true,
                            fn: this.myCheckedFunction,
                        }
                    },
                    events: {
                        expanded: {
                            state: true,
                            fn: null,
                        },
                        selected: {
                            state: false,
                            fn: null,
                        },
                        checked: {
                            state: false,
                            fn: null,
                        },
                        editableName: {
                            state: false,
                            fn: null,
                            calledEvent: null,
                        }
                    },
                    addNode: {
                        state: false,
                        fn: null,
                        appearOnHover: false
                    },
                    editNode: {
                        state: true,
                        fn: null,
                        appearOnHover: true
                    },
                    deleteNode: {
                        state: true,
                        fn: null,
                        appearOnHover: true
                    },
                    showTags: true,
                };
            }
        }
    }

    export default main;

</script>

<style scoped>
    #chart_container {
        padding-right: 40px;
        padding-bottom: 20px;
        margin-top: 20px;
        position: relative;
    }

    #demo_chart {
        position: relative;
        left: 40px;
    }

    #y_axis {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 40px;
    }

    .footy {
        position: relative;
        width: 100%;
        margin-top: 50px;
        height: 60px;
        opacity: 0.2;
    }

    .glyphicon {
        color: #8E44AD;
        font-weight: bold;
    }

</style>
