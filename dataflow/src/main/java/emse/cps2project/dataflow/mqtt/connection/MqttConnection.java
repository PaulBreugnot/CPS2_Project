package emse.cps2project.dataflow.mqtt.connection;

public interface MqttConnection {

    String BROKER_URL = "tcp://ec2-54-236-113-5.compute-1.amazonaws.com:1883";
    // String BROKER_URL = "tcp://localhost:1883";

    String connected_topic = "/connected";
    String disconnected_topic = "/disconnected";

    String data_topic = "emse/fayol/e0/itm/sensors/+/metrics/+";

    void connect();
    // void checkConnectedModule();
}
