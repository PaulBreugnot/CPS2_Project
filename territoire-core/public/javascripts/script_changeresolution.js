function map_changeresolution (evt)
{
    var viewResolution = /** @type {number} */ (map.getView().getResolution());
    if( layer1.getVisible() )
    {
	if( viewResolution > 1 && currentResolution <= 1 )
	{
	    layer1.setVisible(false);
	    map.removeLayer(layer1);
	    // layer1 = createLayerSensorCluster('sensorbase_4');
      layer1 = createLayerSensor('sensoritm');
	    map.addLayer(layer1);
	}
	else if( viewResolution <= 1 && currentResolution > 1)
	{
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
