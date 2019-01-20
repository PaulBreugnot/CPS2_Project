package emse.cps2project.dataflow.mqtt.connection;

import emse.cps2project.dataflow.model.measure.MeasureType;
import emse.cps2project.dataflow.model.measure.dao.MeasureTypeDao;
import emse.cps2project.dataflow.model.observation.Observation;
import emse.cps2project.dataflow.model.observation.dao.ObservationDao;
import emse.cps2project.dataflow.model.sensor.Sensor;
import emse.cps2project.dataflow.model.sensor.dao.SensorDao;
import emse.cps2project.dataflow.mqtt.topic.DataTopic;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqttConnectionHandler implements IMqttMessageListener {

    Logger logger = LoggerFactory.getLogger(MqttConnectionHandler.class);

    @Autowired
    private MqttConnection mqttConnection;

    @Autowired
    private MeasureTypeDao measureTypeDao;

    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private ObservationDao observationDao;

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info("Data message received on '" + topic + "' : " + new String(message.getPayload()));
        DataTopic dataTopic = new DataTopic(topic);
        MeasureType measureType = measureTypeDao.findByType(dataTopic.getMetric().toString());
        if (measureType != null) {
            logger.info("Measure type : " + measureType.getType());
            Sensor sensor = sensorDao.findById(dataTopic.getIdSensor()).orElse(null);
            if (sensor != null) {
                logger.info("Sensor : " + sensor.getId());
                Observation observation = new Observation(
                        new Date(),
                        Float.parseFloat(new String(message.getPayload())),
                        measureType,
                        sensor
                );
                measureType.getObservations().add(observation);
                sensor.getObservations().add(observation);

                // observationDao.save(observation);
            }
            else {
                logger.error("Unknown Sensor : " + dataTopic.getIdSensor());
            }
        }
        else {
            logger.error("Unknown MeasureType : " + dataTopic.getMetric());
        }
    }
}
