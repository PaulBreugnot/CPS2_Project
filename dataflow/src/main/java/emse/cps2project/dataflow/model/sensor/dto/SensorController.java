package emse.cps2project.dataflow.model.sensor.dto;

import emse.cps2project.dataflow.model.sensor.Sensor;
import emse.cps2project.dataflow.model.sensor.SensorLayer;
import emse.cps2project.dataflow.model.sensor.dao.SensorDao;
import emse.cps2project.dataflow.model.sensor.dao.SensorLayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/sensors")
@Transactional
public class SensorController {

    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private SensorLayerDao sensorLayerDao;

    @GetMapping
    public List<SensorDto> getSensorLayer(@RequestParam(name="layer", required=false) Integer layer_id) {
        System.out.println("Layer_id : " + layer_id);
        if(layer_id == null) {
            return sensorDao.findAll()
                    .stream()
                    .map(SensorDto::new)
                    .collect(Collectors.toList());
        }
        else {
            SensorLayer sensorLayer = sensorLayerDao.findById(layer_id).orElse(null);
            if (sensorLayer == null) {
                return new ArrayList<>();
            }
            else {
                return sensorLayer.getSensors()
                        .stream()
                        .map(SensorDto::new)
                        .collect(Collectors.toList());
            }
        }
    }
}
