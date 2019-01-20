package emse.cps2project.dataflow.mqtt.connection;

import org.springframework.beans.factory.annotation.Value;

public interface MqttConnection {

    String connected_topic = "/connected";
    String disconnected_topic = "/disconnected";

    void connect();

    String getDataTopic();
    // void checkConnectedModule();
}
