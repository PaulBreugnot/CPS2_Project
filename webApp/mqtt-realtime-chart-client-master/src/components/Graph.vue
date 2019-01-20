<template>
    <div>
        <header id="site-header" class="nav is-fixed" style="display: flex;">
            <div class="logos">
                <div class="column">
                    <a href="https://minbot.fr/">
                        <img class="logo" src="../assets/logo-pixled.png" alt="Pixled" title="Pixled">
                    </a>
                </div>
                <div class="column">
                    <a href="/">
                        <img class="logo" src="../assets/logo-emse.png" alt="EMSE" title="EMSE">
                    </a>
                </div>
            </div>
            <br style="clear:both;" />
            <nav class="nav">
                <div class="sub-menu-left" style="float: left;">
                    <scrollactive ref="scrollactive" :offset="offset" :always-track="alwaysTrack" :duration="duration" :click-to-scroll="clickToScroll" :bezier-easing-value="easing">
                        <ul class="nav-left">
                            <li><a href="#section-0" class="scrollactive-item nav-item">Subject</a></li>
                            <li><a href="#section-1" class="scrollactive-item nav-item">Map</a></li>
                            <li><a href="#section-2" class="scrollactive-item nav-item">Graph</a></li>
                        </ul>
                    </scrollactive>
                </div>
                <br style="clear:both;" />
                <div class="sub-menu-right" style="float: right;">
                    <ul class="nav-right">
                        <li class="nav-item"><button id="mapboxDark">Map Dark</button></li>
                        <li class="nav-item"><button id="mapboxLight">Map Light</button></li>
                        <li class="nav-item"><button id="mapboxStreets">Map Streets</button></li>
                    </ul>
                </div>
            </nav>
        </header>

        <main>
            <section id="section-0">
                <h3 id="trues2-workshop-context-acquisition-and-representation-in-itm-factory">
                    S2. Workshop context acquisition and representation in ITM’Factory
                </h3>
                <div class="paragraph">
                    <p>Definition and selection of set of sensors to acquire environmental data in ITM’Factory.
                        Representation, processing and visualisation of these data will have to take into account the geolocalized position of the sensors as well as the global architecture of the workshop (positioning of machines, workers, etc).
                        Indicators related to the quality of the working conditions in the workshop will be computed from these data. They will be displayed on an Interactive communication platform placed in workshops of the factory.</p>
                </div>
            </section>
            <section id="section-1" class="mapContainer">
                <div class="maincontent">
                    <div id="menu">
                        <ul>
                            <li><img src="../assets/iconlayer1.png" id="onglet1" align="left" width="45px" height="38px"></li>
                            <li><img src="../assets/iconlayer2.png" id="onglet2" align="left" width="45px" height="38px"></li>
                        </ul>
                    </div>
                    <div id="map"></div>
                    <div id="status"><b>Entity information</b> <br />
                        <div id="statusselect">Selected <br /></div>
                        <div id="statushover">Hovered <br /></div>
                    </div>
                </div>
            </section>
            <br style="clear:both;" />
            <section id="section-2">
                <div style="display: flex; position: relative;">
                    <div style="float: left;">
                        <div class="tree3">
                            <input class="tree-search-input" type="text" v-model="searchword" placeholder="search..." />
                            <button class=" tree-search-btn" type="button" @click="search">search</button>
                            <v-tree ref='tree1' :canDeleteRoot="false" :data='layersTree' :draggable='false' :tpl='tpl' :halfcheck='true' :multiple="true" />
                        </div>
                    </div>
                    <br style="clear:both;" />
                    <div style="float: right; width: 100%; top: 0; bottom: 0; left: 0; right: 0; margin: 0;" id="graphContainer">
                        <div class="container-fluid">
                            <div class="row text-center">
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
                                            <p v-if="displayedTopics.length > 0 && displayedValues.length > 0">
                                                <span v-for="topic in displayedTopics">
                                                    <span v-bind:style="{ color: dvColors[topic]}"> {{displayedValues[0][topic]}} {{measures[topics.indexOf(topic)]}} / </span>
                                                </span>
                                            </p>
                                            <p v-else>
                                                <img src="../assets/Loading_icon.gif" style="width: 100%;">
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
                </div>
            </section>
        </main>

        <footer>
            <p>Institut Fayol @ École des Mines de Saint-Étienne - Inspiring innovation</p>
        </footer>
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
                displayedTopics: [],
                dvColors: {},
                lastValue: {},
                searchword: '',
                initSelected: ['Layers'],
                layersTree: [{
                    title: 'Layers',
                    expanded: true,
                    children: []
                }],
                elements: [],
                alwaysTrack: false,
                duration: 600,
                clickToScroll: true,
                offset: 52,
                easing: '.5,0,.35,1'
            }
        },
        computed: {
            numberOfElements() {
                return this.elements.length;
            },
        },
        mounted() {

            const tempMain = this;

            this.elements = this.$el.querySelectorAll('.scrollactive-item');

            //------------------------------------------------------------------------------------------
            // Script map
            //------------------------------------------------------------------------------------------

            // script_onglets

            function load_tab(elt) {
                if (elt.id === 'onglet1') {
                    if (layer1.getVisible()) {
                        layer1.setVisible(false);
                        map.removeLayer(layer1);
                    } else {
                        if (map.getView().getResolution() > 1) {
                            // layer1 = createLayerSensorCluster('sensorbase_4');
                            layer1 = createLayerSensorCluster('sensoritm');
                            console.log("Create LayerSensorCluster");
                        } else {
                            // layer1 = createLayerSensor('sensorbase_4');
                            layer1 = createLayerSensor('sensoritm');
                            console.log("Create LayerSensor");
                        }
                        map.addLayer(layer1);
                        layer1.setVisible(true);
                    }
                    return;
                }
                if (elt.id === 'onglet2') {
                    if (layer2.getVisible()) {
                        layer2.setVisible(false);
                        map.removeLayer(layer2);
                    } else {
                        // layer2 = createLayerOffice('office_4');
                        layer2 = createLayerOffice('itm');
                        console.log("Create LayerOffice");
                        map.addLayer(layer2);
                        layer2.setVisible(true);
                    }
                    return;
                }
            }

            // script_changeresolution

            function map_changeresolution(evt) {
                var viewResolution = /** @type {number} */ (map.getView().getResolution());
                if (layer1.getVisible()) {
                    if (viewResolution > 1 && currentResolution <= 1) {
                        layer1.setVisible(false);
                        map.removeLayer(layer1);
                        // layer1 = createLayerSensorCluster('sensorbase_4');
                        layer1 = createLayerSensor('sensoritm');
                        map.addLayer(layer1);
                    } else if (viewResolution <= 1 && currentResolution > 1) {
                        layer1.setVisible(false);
                        map.removeLayer(layer1);
                        // layer1 = createLayerSensor('sensorbase_4');
                        layer1 = createLayerSensor('sensoritm');
                        map.addLayer(layer1);
                        layer1.setVisible(true);
                    }
                }
                currentResolution = viewResolution;
            }

            // script_mouseevent

            selectPointerMove = new ol.interaction.Select({
                layers: function(layer) {
                    return (layer.get('name') === 'sensor' && layer.getVisible());
                },
                filter: function(feature, layer) {
                    return map.getView().getResolution() <= 1;
                },
                condition: ol.events.condition.pointerMove,
                style: [new ol.style.Style({
                    image: new ol.style.RegularShape({
                        fill: new ol.style.Fill({
                            color: 'rgba(220,20,60,0.6)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: 'rgba(220,20,60,1.0)',
                            width: 3
                        }),
                        points: 3,
                        radius: 14,
                        rotation: Math.PI / 4,
                        angle: 0
                    })
                })]
            });

            // script_onclick

            function map_onclick(evt) {
                var features = new Array();
                if (map.getView().getResolution() <= 1) {
                    map.forEachFeatureAtPixel(evt.pixel, function(feature, layer) {
                        features.push(feature);
                    }, {
                        layerFilter: function(layer) {
                            return ((layer.get('name') === 'office' && layer.getVisible()));
                        }
                    });
                    var arraylength = features.length;
                    if (arraylength > 0) {
                        alert('feature selected on click: ' + arraylength);
                    }
                }
            }

            // script_map

            function createMapBase(spec) {
                if (spec == 'mapboxLight') {
                    return new ol.layer.Tile({
                        source: new ol.source.XYZ({
                            url: 'https://api.mapbox.com/styles/v1/mapbox/light-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw'
                        })
                    });

                }

                if (spec == 'mapboxDark') {
                    return new ol.layer.Tile({
                        source: new ol.source.XYZ({
                            url: 'https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw'
                        })
                    });

                }

                if (spec == 'mapboxStreets') {
                    return new ol.layer.Tile({
                        source: new ol.source.XYZ({
                            url: 'https://api.mapbox.com/styles/v1/mapbox/streets-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw'
                        })
                    });

                }

                var tilebase = new ol.layer.Tile({
                    title: 'Annona OSM Layer',
                    source: new ol.source.OSM(),
                    type: 'base',
                    visible: true
                });

                tilebase.set('name', 'MapBase');
                return tilebase;
            }

            function createMap(elt_base, elt_div, center, tabext, baseview) {
                var minZ = 19;
                var maxZ = 21;
                var zoomZ = baseview;
                var valcenter = ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857');
                var valextent = ol.extent.applyTransform(tabext, ol.proj.getTransform("EPSG:4326", "EPSG:3857"));

                var map = new ol.Map({
                    target: elt_div,
                    renderer: 'canvas',
                    controls: ol.control.defaults().extend([
                        new ol.control.OverviewMap()
                    ]),
                    layers: [elt_base],
                    /*interactions: ol.interaction.defaults({
                       dragPan: false
                       }),*/
                    view: new ol.View({
                        projection: 'EPSG:3857',
                        center: valcenter,
                        //[5.38035,43.30937] Marseille
                        //[4.38833,45.43656] Saint-Etienne
                        zoom: zoomZ,
                        rotation: -0.35,
                        minZoom: minZ,
                        maxZoom: maxZ,
                        extent: valextent,
                        //[5.28796, 43.37792, 5.53023, 43.2234],
                    }),
                });
                return map;
            }

            selectSingleClick = new ol.interaction.Select({
                layers: function(layer) {
                    return (layer.get('name') === 'office' && layer.getVisible());
                },
                filter: function(feature, layer) {
                    return map.getView().getResolution() <= 1;
                },
                condition: ol.events.condition.click,
                style: [new ol.style.Style({
                    fill: new ol.style.Fill({
                        color: 'rgba(255,0,255, 0.3)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(255,0,255, 0.6)',
                        width: 3
                    })
                })]
            });

            selectPointerMove.on('select', function(e) {
                var tabfeatures = e.target.getFeatures();

                console.log(tabfeatures);
                console.log(tabfeatures.getArray());

                var texthtml = 'Hovered <br />';
                tabfeatures.forEach(function(f) {

                    console.log(f);

                    texthtml += 'id: ' + f.get('id') + ' -- ';
                    texthtml += 'base size: ' + f.get('capacity') + '<br />';
                });
                $('#statushover').html(texthtml);
            });

            selectSingleClick.on('select', function(e) {
                var tabfeatures = e.target.getFeatures();

                console.log(tabfeatures);

                var texthtml = 'Selected <br />';
                tabfeatures.forEach(function(f) {
                    texthtml += 'id: ' + f.get('id') + ' -- ';
                    texthtml += 'ref: ' + f.get('refoffice') + '<br />';
                });
                $('#statusselect').html(texthtml);
            });

            // script_layer

            function createLayerSensorCluster(table) {
                var imageCK = new ol.style.Circle({
                    radius: 12,
                    stroke: new ol.style.Stroke({
                        color: 'rgba(220,20,60,0.8)',
                        width: 2
                    }),
                    fill: new ol.style.Fill({
                        color: 'rgba(220,20,60,0.4)'
                    })
                });
                var formatSensor = new ol.format.GML2({
                    featureType: "Sensorbase",
                    featureNS: "http://mapserver.gis.umn.edu/mapserver",
                    version: "1.1.0",
                    featurePrefix: "ms",
                    geometryName: "msGeometry"
                });

                var sensor = new ol.source.Vector({
                    loader: function(extent) {
                        sensor.clear();
                        $.ajax(url_mapserv + 'map=' + mymap, {
                            type: 'POST',
                            data: {
                                service: 'WFS',
                                version: '1.1.0',
                                request: 'GetFeature',
                                outputformat: 'GML2',
                                typename: 'Sensorbase',
                                srsname: 'EPSG:4326',
                                bbox: extent.join(',') + ',EPSG:3857',
                                solname: table
                            }
                        }).done(function(response) {
                            var testV = formatSensor.readFeatures(response, {
                                dataProjection: ol.proj.Projection('EPSG:4326'),
                                featureProjection: ol.proj.Projection('EPSG:3857')
                            });
                            for (var i = 0; i < testV.length; i++) {
                                testV[i].getGeometry().transform('EPSG:4326', 'EPSG:3857');

                            }
                            sensor.addFeatures(testV);
                        });
                    },
                    strategy: ol.loadingstrategy.bbox,
                    projection: 'EPSG:3857',
                    format: formatSensor
                });

                var clusterSource = new ol.source.Cluster({
                    distance: 20,
                    source: sensor,
                });

                var styleCache = {};
                var sensorL = new ol.layer.Vector({
                    source: clusterSource,
                    style: function(feature, resolution) {
                        var size = feature.get('features').length;
                        var style = styleCache[size];
                        if (!style) {
                            style = [new ol.style.Style({
                                image: imageCK,
                                text: new ol.style.Text({
                                    text: size.toString(),
                                    fill: new ol.style.Fill({
                                        color: '#ffffff'
                                    })
                                })
                            })];
                            styleCache[size] = style;
                        }
                        return style;
                    }
                });
                sensorL.set('name', 'sensor');
                sensorL.setZIndex(5);
                return sensorL;
            }

            function createLayerSensor(table) {
                var formatSensor = new ol.format.GML2({
                    featureType: "Sensorbase",
                    featureNS: "http://mapserver.gis.umn.edu/mapserver",
                    version: "1.1.0",
                    featurePrefix: "ms",
                    geometryName: "msGeometry"
                });

                var sensor = new ol.source.Vector({
                    loader: function(extent) {
                        sensor.clear();
                        $.ajax(url_mapserv + 'map=' + mymap, {
                            type: 'POST',
                            data: {
                                service: 'WFS',
                                version: '1.1.0',
                                request: 'GetFeature',
                                outputformat: 'GML2',
                                typename: 'Sensorbase',
                                srsname: 'EPSG:4326',
                                bbox: extent.join(',') + ',EPSG:3857',
                                solname: table
                            }
                        }).done(function(response) {
                            var testV = formatSensor.readFeatures(response, {
                                dataProjection: ol.proj.Projection('EPSG:4326'),
                                featureProjection: ol.proj.Projection('EPSG:3857')
                            });
                            for (var i = 0; i < testV.length; i++) {
                                testV[i].getGeometry().transform('EPSG:4326', 'EPSG:3857');
                            }
                            sensor.addFeatures(testV);
                        });
                    },
                    strategy: ol.loadingstrategy.bbox,
                    projection: 'EPSG:3857',
                    format: formatSensor
                });


                var sensorL = new ol.layer.Vector({
                    source: sensor,
                    style: [new ol.style.Style({
                        image: new ol.style.RegularShape({
                            fill: new ol.style.Fill({
                                color: 'rgba(220,20,60,0.4)'
                            }),
                            stroke: new ol.style.Stroke({
                                color: 'rgba(220,20,60,0.8)',
                                width: 2
                            }),
                            points: 3,
                            radius: 10,
                            rotation: Math.PI / 4,
                            angle: 0
                        })
                    })]
                });

                sensorL.set('name', 'sensor');
                sensorL.setZIndex(5);
                return sensorL;
            }

            function createLayerOffice(table) {
                var formatOffice = new ol.format.GML2({
                    featureType: "Office",
                    featureNS: "http://mapserver.gis.umn.edu/mapserver",
                    version: "1.1.0",
                    featurePrefix: "ms",
                    geometryName: "msGeometry"
                });

                var office = new ol.source.Vector({
                    loader: function(extent) {
                        office.clear();
                        $.ajax(url_mapserv + 'map=' + mymap, {
                            type: 'POST',
                            data: {
                                service: 'WFS',
                                version: '1.1.0',
                                request: 'GetFeature',
                                outputformat: 'GML2',
                                typename: 'Office',
                                srsname: 'EPSG:4326',
                                bbox: extent.join(',') + ',EPSG:3857',
                                solname: table
                            }
                        }).done(function(response) {
                            var testV = formatOffice.readFeatures(response, {
                                dataProjection: ol.proj.Projection('EPSG:4326'),
                                featureProjection: ol.proj.Projection('EPSG:3857')
                            });
                            for (var i = 0; i < testV.length; i++) {
                                testV[i].getGeometry().transform('EPSG:4326', 'EPSG:3857');
                            }
                            office.addFeatures(testV);
                        });
                    },
                    strategy: ol.loadingstrategy.bbox,
                    projection: 'EPSG:3857',
                    format: formatOffice
                });


                var officeL = new ol.layer.Vector({
                    source: office,
                    style: [new ol.style.Style({
                        fill: new ol.style.Fill({
                            color: 'rgba(190,190,190, 0.5)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: 'rgba(255,0,255, 0.6)',
                            width: 2
                        })
                    })]
                });

                officeL.set('name', 'office');
                officeL.setZIndex(2);
                return officeL;
            }

            // script_init

            $(document).ready(function() {
                mapbase = createMapBase('mapboxStreets');
                map = createMap(mapbase, 'map', center, tabext, baseview);

                map.overlayContainerStopEvent_.hidden = true;

                currentResolution = map.getView().getResolution();
                map.getView().on('change:resolution', map_changeresolution);
                map.addInteraction(selectPointerMove);
                map.addInteraction(selectSingleClick);
            });

            // button interaction

            function loadMap(elt) {
                $("#map").empty();
                mapbase = createMapBase(elt.id);
                map = createMap(mapbase, 'map', center, tabext, baseview);

                map.overlayContainerStopEvent_.hidden = true;

                currentResolution = map.getView().getResolution();
                map.getView().on('change:resolution', map_changeresolution);
                map.addInteraction(selectPointerMove);
                map.addInteraction(selectSingleClick);
            }

            $('#mapboxDark').click(function() {
                loadMap(this)
            });
            $('#mapboxLight').click(function() {
                loadMap(this)
            });
            $('#mapboxStreets').click(function() {
                loadMap(this)
            });

            $('#onglet1').click(function() {
                load_tab(this)
            });
            $('#onglet2').click(function() {
                load_tab(this)
            });

            //------------------------------------------------------------------------------------------
            // Connection to client
            //------------------------------------------------------------------------------------------

            client.on("connect", function() {

                const req = new XMLHttpRequest();

                /*req.open('GET', 'http://192.168.12.1:8080/api/sensorlayers', true);*/
                req.open('GET', 'http://ec2-54-236-113-5.compute-1.amazonaws.com:8090/api/sensorlayers', true);

                function onLoad() {
                    // Ici, this.readyState égale XMLHttpRequest.DONE .
                    if (req.status === 200) {
                        const layers = JSON.parse(req.responseText);

                        layers.forEach(function(layer) {

                            const tempTree = {
                                title: layer.name,
                                id: "emse/fayol/e0/" + layer.name.replace('sensor', ''),
                                children: []
                            };

                            layer.sensors.forEach(function(sensor) {

                                const availableMeasures = []

                                const topic = sensor.topic ? sensor.topic : "emse/fayol/e0/itm/sensors/" + sensor.id

                                sensor.availableMeasures.forEach(function(measure) {

                                    availableMeasures.push({
                                        title: measure.type,
                                        id: topic + "/metrics/" + measure.type
                                    });

                                    client.subscribe(topic + "/metrics/" + measure.type, function(err) {
                                        if (err) {
                                            console.log(err);
                                        }
                                    });

                                    tempMain.measures.push(measure.unit);
                                    tempMain.topics.push(topic + "/metrics/" + measure.type);
                                    tempMain.displayedTopics.push(topic + "/metrics/" + measure.type);
                                });

                                tempTree.children.push({
                                    title: sensor.name ? sensor.name : sensor.id,
                                    id: topic,
                                    children: availableMeasures
                                });
                            });

                            tempMain.layersTree[0].children.push(tempTree);

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
                        setTimeout(onLoad, 5000);
                    }
                };

                req.onload = onLoad();

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
            nodechecked(node, v) {
                alert('that a node-check envent ...' + node.title + v)
            },
            // tpl (node, ctx, parent, index, props) {
            tpl(...args) {
                let {
                    0: node,
                    2: parent,
                    3: index
                } = args;
                let titleClass = node.selected ? 'node-title node-selected' : 'node-title';
                if (node.searched) titleClass += ' node-searched';
                return <span > <
                    span
                class = {
                    titleClass
                }
                domPropsInnerHTML = {
                    node.title
                }
                onClick = {
                    () => {
                        this.$refs.tree1.nodeSelected(node);
                        this.updateDisplayedTopics();
                    }
                } > < /span>< /
                span >
            },
            search() {
                this.$refs.tree1.searchNodes(this.searchword)
            },
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
                    width: "300",
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

                const tempThis = this;

                window.onresize = function() {
                    tempThis.resizeChart(magnitudeChart)
                };

            },
            resizeChart(chart) {

                console.log(document.getElementById("graphContainer"));
                //console.clear();

                chart.configure({
                    width: this.$refs.panel.clientWidth ? this.$refs.panel.clientWidth : document.getElementById("graphContainer").clientWidth,
                });
                chart.render();
            },
            /* Insert received datapoints into the chart */
            insertDatapoints(messages, chart) {

                const tempMain = this;

                for (let i = 0; i < messages.length; i++) {

                    let voltageData = {};

                    tempMain.topics.forEach(function(key) {

                        if (messages[i][key]) {

                            const value = messages[i][key];

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

                    if (Object.keys(voltageData).length) {
                        chart.series.addData(voltageData);
                    }
                }

                if (mini && maxi) {
                    magnitudeChart.min = mini - 5;
                    magnitudeChart.max = maxi + 5;
                }

                chart.render();
            },
            updateDisplayedTopics() {

                const listCheckedNodes = this.$refs.tree1.getCheckedNodes();
                this.displayedTopics = [];

                listCheckedNodes.forEach((node) => {
                    if (node && node.id && this.topics.includes(node.id)) {
                        this.displayedTopics.push(node.id);
                    }
                });
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
                    if (this.displayedTopics.includes(topic)) {
                        const messageReceived = new TextDecoder("utf-8").decode(payload);
                        try {
                            this.lastValue[topic] = parseFloat(messageReceived);
                        } catch (err) {
                            console.log(err);
                        }
                    }
                });

                setInterval(() => {
                    if (Object.keys(this.lastValue).length) {
                        setTimeout(() => {

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
                        }, 500)
                    }
                }, 2000)

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
    @import '../assets/stylesheets/style_iot.css';

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

    .tree3 {
        float: left;
        /*width: 33%;*/
        padding: 10px;
        box-sizing: border-box;
        border: 1px dotted #ccccdd;
        overflow: auto;
        height: 800px;
        width: 100%;
    }

    .treebtn1 {
        background-color: transparent;
        border: 1px solid #ccc;
        padding: 1px 3px;
        border-radius: 5px;
        margin-right: 5px;
        color: rgb(148, 147, 147);
    }

    .treebtn2 {
        background-color: transparent;
        border: 1px solid #ccc;
        padding: 3px 5px;
        border-radius: 5px;
        margin-left: 5px;
        color: rgb(97, 97, 97);
    }

    .treebtn3 {
        background-color: transparent;
        border: 1px solid #ccc;
        padding: 3px 5px;
        border-radius: 5px;
        margin-left: 5px;
        color: rgb(63, 63, 63);
    }

    .tree-search-input {
        width: 70%;
        padding: 6px 8px;
        outline: none;
        border-radius: 6px;
        border: 1px solid #ccc;
    }

    .tree-search-btn {
        width: 25%;
        padding: 6px 8px;
        outline: none;
        border-radius: 6px;
        background-color: rgb(218, 218, 218);
        border: 1px solid rgb(226, 225, 225);
        color: rgb(117, 117, 117);
    }

</style>
