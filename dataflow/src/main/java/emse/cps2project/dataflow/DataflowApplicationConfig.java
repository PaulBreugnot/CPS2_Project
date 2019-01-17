package emse.cps2project.dataflow;

import emse.cps2project.dataflow.mqtt.connection.MqttConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataflowApplicationConfig {
    Logger logger = LoggerFactory.getLogger(DataflowApplicationConfig.class);


    @Autowired
    public DataflowApplicationConfig(MqttConnection mqttConnection) {
        logger.info("MQTT Configuration");
        mqttConnection.connect();
    }
}
