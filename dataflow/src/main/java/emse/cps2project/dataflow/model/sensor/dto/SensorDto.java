package emse.cps2project.dataflow.model.sensor.dto;

import emse.cps2project.dataflow.model.measure.MeasureType;
import emse.cps2project.dataflow.model.measure.dto.MeasureTypeDto;
import emse.cps2project.dataflow.model.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorDto {

    private Integer id;
    private String name;
    private List<MeasureTypeDto> availableMeasures;
    private String topic;

    public SensorDto(Sensor sensor) {
        id = sensor.getId();
        name = sensor.getName();
        availableMeasures = new ArrayList<>();
        for (MeasureType measureType : sensor.getAvailableMeasureTypes()) {
            availableMeasures.add(new MeasureTypeDto(measureType));
        }
        topic = sensor.getTopic();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MeasureTypeDto> getAvailableMeasures() {
        return availableMeasures;
    }

    public String getTopic() {
        return topic;
    }
}
