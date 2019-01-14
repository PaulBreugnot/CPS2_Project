function createMapBase(spec)
{
    if(spec == 'mapboxLight')
	{
	    return new ol.layer.Tile({
		source: new ol.source.XYZ({
		    url: 'https://api.mapbox.com/styles/v1/mapbox/light-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw'
		})
	    });

	}

    if(spec == 'mapboxDark')
	{
	    return new ol.layer.Tile({
		source: new ol.source.XYZ({
		    url: 'https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw' })
	    });

	}

    if(spec == 'mapboxStreets')
	{
	    return new ol.layer.Tile({
		source: new ol.source.XYZ({
		    url: 'https://api.mapbox.com/styles/v1/mapbox/streets-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZmFiaWVuLWVtc2UiLCJhIjoiY2l6anYyMXpjMDA0cjJ3dGZ0Z3NrdDUxNCJ9.37SAR2gk4Hur4a8ZTkbRSw' })
	    });

	}

    var tilebase =  new ol.layer.Tile({
	title: 'Annona OSM Layer',
	source: new ol.source.OSM(),
	type: 'base',
	visible: true
    });

    tilebase.set('name', 'MapBase');
    return tilebase;
}

function createMap(elt_base,elt_div,center,tabext,baseview)
{
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
