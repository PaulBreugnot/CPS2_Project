package emse.cps2project.dataflow.model.measure.dto;

import emse.cps2project.dataflow.model.measure.MeasureType;

public class MeasureTypeDto {

    private String type;
    private String unit;

    public MeasureTypeDto(MeasureType measureType) {
        type = measureType.getType();
        unit = measureType.getUnit();
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }
}
