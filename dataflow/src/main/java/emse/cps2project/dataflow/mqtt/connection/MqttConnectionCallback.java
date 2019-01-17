package emse.cps2project.dataflow.mqtt.connection;

import emse.cps2project.dataflow.DataflowApplicationConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttConnectionCallback implements MqttCallbackExtended {

    Logger logger = LoggerFactory.getLogger(MqttConnectionCallback.class);

    private IMqttClient client;
    private MqttConnection mqttConnection;
    private MqttConnectionHandler mqttConnectionHandler;

    public MqttConnectionCallback(IMqttClient client, MqttConnection mqttConnection, MqttConnectionHandler mqttConnectionHandler) {
        this.client = client;
        this.mqttConnection = mqttConnection;
        this.mqttConnectionHandler = mqttConnectionHandler;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        logger.info("MQTT connection complete.");
        try {
             // client.subscribe(MqttConnection.connected_topic, 1, mqttConnectionHandler);
             // client.subscribe(MqttConnection.disconnected_topic, 1, mqttConnectionHandler);
            client.subscribe(MqttConnection.data_topic, 1, mqttConnectionHandler);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
