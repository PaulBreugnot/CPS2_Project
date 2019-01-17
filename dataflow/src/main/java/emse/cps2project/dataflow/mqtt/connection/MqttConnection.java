package emse.cps2project.dataflow.mqtt.connection;

public interface MqttConnection {

    String connected_topic = "/connected";
    String disconnected_topic = "/disconnected";

    String data_topic = "emse/fayol/e0/itm/sensors/+/metrics/+";

    void connect();
    // void checkConnectedModule();
    // void publishColor(long buildingId, long roomId, long lightId, String color);
    // void publishSwitch(long buildingId, long roomId, long lightId, Status status);
}
