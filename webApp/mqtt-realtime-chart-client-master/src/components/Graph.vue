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
                            <h3 class="panel-title">Magnitude</h3>
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
                                <small>
                                    <span v-bind:style="{ color: dvColors.v1}">{{displayedValues[0].v1}} </span> ℃
                                </small>
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
                displayedValues: [],
                dvColors: {
                    v1: "#cb503a"
                }
            }
        },
        mounted() {
            this.initChart();
            this.openSocketListeners();

            const tempMain = this;

            client.on("connect", function() {

                const req = new XMLHttpRequest();

                req.open('GET', 'http://192.168.12.1:8080/api/sensorlayers', true);

                req.onload = function() {
                    // Ici, this.readyState égale XMLHttpRequest.DONE .
                    if (req.status === 200) {
                        const layers = JSON.parse(req.responseText);

                        layers.forEach(function(elt) {

                            const sensors = elt.sensors;

                            sensors.forEach(function(sensor) {

                                if (sensor.topic) {

                                    const availableMeasures = sensor.availableMeasures;

                                    availableMeasures.forEach(function(measure) {

                                        client.subscribe(sensor.topic + "/metrics/" + measure.type, function(err) {
                                            if (err) {
                                                console.log(err);
                                            }
                                        });
                                    });

                                }
                            });
                        });

                    } else {
                        console.log("Status de la réponse: %d (%s)", req.status, req.statusText);
                    }
                };

                req.send(null);
            });

            client.subscribe("emse/fayol/e0/itm/sensors/-3/metrics/TMP", function(err) {
                if (err) {
                    console.log(err);
                }
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
                magnitudeChart = new Rickshaw.Graph({
                    element: document.querySelector("#demo_chart"),
                    width: "500",
                    height: "180",
                    renderer: "line",
                    min: 0,
                    max: 0,
                    series: new Rickshaw.Series.FixedDuration([{
                        name: 'v1',
                        color: '#EC644B'
                    }], undefined, {
                        timeInterval: this.updateInterval,
                        maxDataPoints: 100,
                        timeBase: new Date().getTime() / 1000

                    })
                });

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
                for (let i = 0; i < messages.length; i++) {
                    let voltageData = {
                        Magnitude1: messages[i].v1
                    };
                    chart.series.addData(voltageData);

                    if (mini && maxi) {
                        if (parseFloat(messages[i].v1) < mini) {
                            mini = parseFloat(messages[i].v1)
                        }
                        if (parseFloat(messages[i].v1) > maxi) {
                            maxi = parseFloat(messages[i].v1);
                        }
                    } else {
                        mini = parseFloat(messages[i].v1)
                        maxi = parseFloat(messages[i].v1);
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

                client.on("message", (topic, payload) => {
                    const messageReceived = new TextDecoder("utf-8").decode(payload);

                    const message = {
                        v1: messageReceived
                    }

                    // Check if displayed values have to be updated
                    this.updateDisplayedValues();

                    // Push stream data to current series, if it's not yet render-time
                    if (this.messageSeries.length < this.renderEveryNth) {
                        try {
                            parseInt(message);
                            this.messageSeries.push(message);
                        } catch (err) {
                            console.log(err);
                        }

                    }

                    // Render-time!
                    if (this.messageSeries.length == this.renderEveryNth) {
                        main.methods.insertDatapoints(this.messageSeries, magnitudeChart);
                        this.messageSeries = [];
                    }
                });

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
