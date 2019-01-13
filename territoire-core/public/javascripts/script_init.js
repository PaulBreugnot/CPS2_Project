$(document).ready(function(){
    mapbase = createMapBase('mapboxStreets');
    map = createMap(mapbase,'map',center,tabext,baseview);
    currentResolution = map.getView().getResolution();
    map.getView().on('change:resolution',map_changeresolution);
    // map.on('singleclick',map_onclick);
    map.addInteraction(selectPointerMove);
    map.addInteraction(selectSingleClick);
});
