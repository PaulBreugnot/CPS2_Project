package emse.cps2project.dataflow.model.sensor.dto;

import emse.cps2project.dataflow.model.sensor.Sensor;
import emse.cps2project.dataflow.model.sensor.SensorLayer;

import java.util.ArrayList;
import java.util.List;

public class SensorLayerDto {

    private Integer id;
    private String name;
    private List<SensorDto> sensors;

    public SensorLayerDto(SensorLayer sensorLayer) {
        id = sensorLayer.getId();
        name = sensorLayer.getName();
        sensors = new ArrayList<>();
        for (Sensor sensor : sensorLayer.getSensors()) {
            sensors.add(new SensorDto(sensor));
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SensorDto> getSensors() {
        return sensors;
    }
}
