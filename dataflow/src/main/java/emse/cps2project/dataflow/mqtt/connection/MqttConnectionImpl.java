package emse.cps2project.dataflow.mqtt.connection;

import emse.cps2project.dataflow.DataflowApplicationConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttConnectionImpl implements MqttConnection {

    Logger logger = LoggerFactory.getLogger(MqttConnectionImpl.class);

    private IMqttClient client;
    private static final String clientId = "DataFlowApp";

    @Autowired
    private MqttConnectionHandler mqttConnectionHandler;

    public MqttConnectionImpl() {
    }

    @Override
    public void connect() {
        logger.info("Connecting to mqtt broker...");
        try {
            client = new MqttClient("tcp://ec2-54-236-113-5.compute-1.amazonaws.com:1883", clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.setCallback(new MqttConnectionCallback(client, this, mqttConnectionHandler));
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
