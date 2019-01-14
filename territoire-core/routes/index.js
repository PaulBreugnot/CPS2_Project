var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('index', {
      title: 'Plate-forme Territoire',
      solution: 'solution',
      place: 'Fayol institute',
      logo: 'logosolution.png',
      onglets: {'onglet1':'iconlayer1.png', 'onglet2':'iconlayer2.png'},
      param_center: '[4.4041,45.4278]',
      param_tabext: '[4.5998,45.3480,4.1655,45.5357]',
      param_baseview: 20,
      param_urlmapserv: 'http://ec2-54-236-113-5.compute-1.amazonaws.com/cgi-bin/mapserv?',
      // param_urlmapserv: 'http://localhost:80/cgi-bin/mapserv?',
      param_mapfile: '/usr/share/maps/coreMapAws.map' });
      // param_mapfile: '/srv/http/maps/coreMapAws.map' });
});

module.exports = router;
