function createLayerSensorCluster(table)
{
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
    var formatSensor = new ol.format.GML2({featureType : "Sensorbase",
					   featureNS : "http://mapserver.gis.umn.edu/mapserver",
					   version: "1.1.0",
					   featurePrefix: "ms",
					   geometryName: "msGeometry"});

    var sensor = new ol.source.Vector({
	    loader: function(extent) {
		sensor.clear();
		$.ajax(url_mapserv+'map='+mymap, {
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
		    var testV = formatSensor.readFeatures(response,{dataProjection: ol.proj.Projection('EPSG:4326'), featureProjection: ol.proj.Projection('EPSG:3857')});
		    for(var i = 0; i < testV.length;i++){
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

function createLayerSensor(table)
{
    var formatSensor = new ol.format.GML2({featureType : "Sensorbase",
					featureNS : "http://mapserver.gis.umn.edu/mapserver",
					version: "1.1.0",
					featurePrefix: "ms",
					geometryName: "msGeometry"});

    var sensor = new ol.source.Vector({
	loader: function(extent) {
	    sensor.clear();
	    $.ajax(url_mapserv+'map='+mymap, {
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
		var testV = formatSensor.readFeatures(response,{dataProjection: ol.proj.Projection('EPSG:4326'), featureProjection: ol.proj.Projection('EPSG:3857')});
		for(var i = 0; i < testV.length;i++){
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

function createLayerOffice(table)
{
    var formatOffice = new ol.format.GML2({featureType : "Office",
					featureNS : "http://mapserver.gis.umn.edu/mapserver",
					version: "1.1.0",
					featurePrefix: "ms",
					geometryName: "msGeometry"});

    var office = new ol.source.Vector({
	loader: function(extent) {
	    office.clear();
	    $.ajax(url_mapserv+'map='+mymap, {
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
		var testV = formatOffice.readFeatures(response,{dataProjection: ol.proj.Projection('EPSG:4326'), featureProjection: ol.proj.Projection('EPSG:3857')});
		for(var i = 0; i < testV.length;i++){
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
