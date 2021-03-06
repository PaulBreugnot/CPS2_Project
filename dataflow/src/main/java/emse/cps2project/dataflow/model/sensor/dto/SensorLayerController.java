package emse.cps2project.dataflow.model.sensor.dto;

import emse.cps2project.dataflow.model.sensor.SensorLayer;
import emse.cps2project.dataflow.model.sensor.dao.SensorLayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/sensorlayers")
@Transactional
public class SensorLayerController {

    @Autowired
    private SensorLayerDao sensorLayerDao;

    @GetMapping
    public List<SensorLayerDto> getLayers() {
        return sensorLayerDao.findAll()
                .stream()
                .map(SensorLayerDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public SensorLayerDto getLayer(@PathVariable("id") Integer idLayer) {
        SensorLayer sensorLayer = sensorLayerDao.findById(idLayer).orElse(null);
        if (sensorLayer == null) {
            throw new SensorLayerNotFoundException();
        }
        else {
            return new SensorLayerDto(sensorLayer);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class SensorLayerNotFoundException extends RuntimeException {

    }
}
