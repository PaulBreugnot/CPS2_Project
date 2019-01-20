package emse.cps2project.dataflow.mqtt.connection;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttConnectionImpl implements MqttConnection {

    @Value("${mqttconnection.broker_url}")
    private String brokerUrl;

    @Value("${mqttconnection.client_id}")
    private String clientId;

    @Value("${mqttconnection.data_topic}")
    private String dataTopic;

    Logger logger = LoggerFactory.getLogger(MqttConnectionImpl.class);

    private IMqttClient client;

    @Autowired
    private MqttConnectionHandler mqttConnectionHandler;

    public MqttConnectionImpl() {
    }

    @Override
    public void connect() {
        logger.info("Connecting to mqtt broker...");
        try {
            client = new MqttClient(brokerUrl, clientId, new MqttDefaultFilePersistence("/usr/share/dataflow/mqtt"));
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

    @Override
    public String getDataTopic() {
        return dataTopic;
    }
}
