package emse.cps2project.dataflow.mqtt.topic;

public class DataTopic extends Topic {

    private static final int id_index = 5;
    private static final int id_metric = 7;

    private Integer idSensor;
    private Metric metric;

    public DataTopic(String topic) {
        super(topic);
        idSensor = Integer.parseInt(fields.get(id_index));
        metric = Metric.valueOf(fields.get(id_metric));
    }

    public Integer getIdSensor() {
        return idSensor;
    }

    public Metric getMetric() {
        return metric;
    }
}
