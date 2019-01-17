package emse.cps2project.dataflow.mqtt.topic;

import java.util.Arrays;
import java.util.List;

public class Topic {

    protected List<String> fields;

    public Topic(String topic) {
        fields = Arrays.asList(topic.split("/"));
    }

    public List<String> getFields() {
        return fields;
    }
}
