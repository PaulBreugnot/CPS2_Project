function map_onclick(evt) {
    var features = new Array();
    if (map.getView().getResolution() <= 1) {
        map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
            features.push(feature);
        }, {
            layerFilter: function (layer) {
                return ((layer.get('name') === 'office' && layer.getVisible()));
            }
        });
        var arraylength = features.length;
        if (arraylength > 0) {
            alert('feature selected on click: ' + arraylength);
        }
    }
}
