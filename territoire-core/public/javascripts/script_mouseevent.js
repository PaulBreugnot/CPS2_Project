selectPointerMove = new ol.interaction.Select({
    layers: function(layer) {
        return ( layer.get('name') === 'sensor' && layer.getVisible() );
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

selectSingleClick = new ol.interaction.Select({
    layers: function(layer) {
        return ( layer.get('name') === 'office' && layer.getVisible() );
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
    var texthtml = 'Hovered <br />';
    tabfeatures.forEach(function(f){
	texthtml+='id: ' + f.get('id') + ' -- ';
	texthtml+='base size: ' + f.get('capacity') + '<br />'; 
    });    
    $('#statushover').html(texthtml); 
});

selectSingleClick.on('select', function(e) {
    var tabfeatures = e.target.getFeatures();
    var texthtml = 'Selected <br />';
    tabfeatures.forEach(function(f){
	texthtml+='id: ' + f.get('id') + ' -- ';
	texthtml+='ref: ' + f.get('refoffice') + '<br />'; 
    });    
    $('#statusselect').html(texthtml); 
});
    

