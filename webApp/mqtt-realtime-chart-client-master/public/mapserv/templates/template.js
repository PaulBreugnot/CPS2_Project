// mapserver template
[resultset layer=Nodes1]
{
  "type": "FeatureCollection",
  "features": [
    [feature trimlast=","]
    {
	"type": "Feature",
	"id": "[osm_id]",
	"geometry": {
            "type": "Point",
            "coordinates": [shpxy precision="5" xh="[" yf="]"]
	},
	"properties": {	    
            "name": "[name]"       
	}
    },
      [/feature]
  ]
}
[/resultset]

[resultset layer=CustomerSiret]
{
  "type": "FeatureCollection",
  "features": [
      [feature trimlast=","]
      {
	  "type": "Feature",
	  "id": "[gid]",
	  "geometry": {
              "type": "Point",
              "coordinates": [shpxy precision="5" xh="[" yf="]"]
	  },
	  "properties": {
	      "siren":"[siren]",
	      "name": "[logo]",
	      "address": "[address]",
	      "demand": "[demand]",
	      "demand CPD-RC": "[demandcpd_rc]",
	      "demand CPE-RC": "[demandcpe_rc]"	    
	  }
      },
      [/feature]
  ]
}
[/resultset]

[resultset layer=UCC]
{
    "type": "FeatureCollection",    
    "features": [
	[feature trimlast=","]
	{
	    "type": "Feature",
	    "id": "[gid]",
	     "geometry": {
		"type": "Point",
		"coordinates": [shpxy precision="5" xh="[" yf="]"]
	    },
	    "properties": {	    
		"name": "[logo]",
		"address": "[address]"
	    }
	},
	[/feature]
    ]
}
[/resultset]

[resultset layer=Voies]
{
    "type": "FeatureCollection",
    "features": [
	[feature trimlast=","]
	{
	    "type": "Feature",
	    "id": "[osm_id]",
	    "geometry": {
		"type": "LineString",
		"coordinates": [[shpxy cs="," precision="5" xh="[" yf="]"]]
	    },
	    "properties": {
		"name": "[name]",
		"type": "[highway]"
	    }
	},
	[/feature]
    ]
}
[/resultset]

[resultset layer=building]
{
    "type": "FeatureCollection",
    "features": [
	[feature trimlast=","]
	{
	    "type": "Feature",
	    "id": "[osm_id]",
	    "geometry": {
		"type": "Polygon",
		"coordinates": [[[shpxy cs="," precision="5" xh="[" yf="]"]]]
	    },
	    "properties": {		
		"name": "[name]"
	    }
	},
	[/feature]
    ]
}
[/resultset]
