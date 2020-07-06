package sg.com.jad.jds.service;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JdsServerServiceImpl implements JdsServerService {

	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);
	private static final SimpleDateFormat MSG_LOGS_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS");
	private static String HOST_URL = "127.0.0.1";

	@Value("${mqtt.broker.url}")
	private String mqttBrokerUrl;

	private MqttClient clientSend;
	private MqttClient clientReceive;
	
	private volatile ArrayList<String> msgLogs = new ArrayList<String>();


	@PostConstruct
	public void postConstruct() {
		LOGGER.info("JDS SERVER SERVICE IS RUNNING");
		try {
			HOST_URL = InetAddress.getLocalHost().getHostAddress();
			initMqtt();
		} catch (Exception e) {
			LOGGER.error("Init error", e);
		}
	}

	@PreDestroy
	public void preDestroy() {
		try {
			if (clientSend != null && clientReceive != null) {
				if (clientSend.isConnected() && clientReceive.isConnected()) {
					clientSend.disconnect();
					clientReceive.disconnect();
				}
			}
			LOGGER.info("JDS SERVER SERVICE HAS STOPPED");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Disconnect error", e);
		}
	}

	private void initMqtt() {
		try {
			clientSend = new MqttClient(mqttBrokerUrl, "JdsSend(" + HOST_URL + ")", new MemoryPersistence());
			clientReceive = new MqttClient(mqttBrokerUrl, "JdsReceive(" + HOST_URL + ")", new MemoryPersistence());

			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);

			// connect here
			clientSend.connect(options);
			clientReceive.connect(options);

			// subscribe here
			clientReceive.subscribe("jds/#");
			clientReceive.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub

				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String msg = new String(message.getPayload());
					LOGGER.info(topic + " ----- " + msg);
					msgLogs.add(MSG_LOGS_FORMAT.format(new Date()) + "     Receiv    " + topic + " ----- " + msg);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
				}

			});

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Init mqtt error", e);
		}
	}
	
	public ArrayList<String> getMsgLogs() {
		return this.msgLogs;
	}
	
	public void clearLogs() {
		this.msgLogs.clear();
	}

	public void sendMsg(String msg) {
		try {
			MqttMessage message = new MqttMessage();
			message.setPayload((msg).getBytes());
			LOGGER.info("Sent Message is: " + message);
			msgLogs.add(MSG_LOGS_FORMAT.format(new Date()) + "    Sent Message is: " + message);
			clientSend.publish("jds/in1", message);
			
		} catch (Exception e) {
			LOGGER.error("send error", e);
		}
	}
	
	public void message(String msg, String topic) {
		try {
			MqttMessage message = new MqttMessage();
			message.setPayload((msg).getBytes());
			LOGGER.info("Sent Message is: " + message);
			msgLogs.add(MSG_LOGS_FORMAT.format(new Date()) + "    Sent Message is: " + message);
			clientSend.publish("jds/"+ topic, message);
		} catch (Exception e) {
			LOGGER.error("send generic message error", e);
		}
	}

}
