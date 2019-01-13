function load_tab(elt)
{
    //console.log(elt.id);
    if(elt.id === 'onglet1')
    {
	if( layer1.getVisible() )
	{
	    layer1.setVisible(false);
	    map.removeLayer(layer1);
	}
	else
	{
	    if(map.getView().getResolution() > 1)
	    {
		// layer1 = createLayerSensorCluster('sensorbase_4');
    layer1 = createLayerSensorCluster('sensoritm');
    console.log("Create LayerSensorCluster");
	    }
	    else
	    {
		// layer1 = createLayerSensor('sensorbase_4');
    layer1 = createLayerSensor('sensoritm');
    console.log("Create LayerSensor");
	    }
	    map.addLayer(layer1);
	    layer1.setVisible(true);
	}
	return;
    }
    if(elt.id === 'onglet2')
    {
	if( layer2.getVisible() )
	{
	    layer2.setVisible(false);
	    map.removeLayer(layer2);
	}
	else
	{
	    // layer2 = createLayerOffice('office_4');
      layer2 = createLayerOffice('itm');
      console.log("Create LayerOffice");
	    map.addLayer(layer2);
	    layer2.setVisible(true);
	}
	return;
    }
}
