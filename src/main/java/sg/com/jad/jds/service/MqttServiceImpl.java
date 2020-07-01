package sg.com.jad.jds.service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import org.springframework.stereotype.Service;

@Service
public class MqttServiceImpl {

	private static final Logger LOGGER = LogManager.getLogger(MqttServiceImpl.class);
	private static String IP_ADDRESS = "127.0.0.1";
	private static String SERVER_URL = "tcp://127.0.0.1:1883";

	private MqttClient clientSend;
	private MqttClient clientReceive;
	private final ScheduledExecutorService TASK_SERVICE = Executors.newSingleThreadScheduledExecutor();

	@PostConstruct
	public void postConstruct() {
		LOGGER.info("MQTT SERVICE IS RUNNING");
		try {
			// IP_ADDRESS = InetAddress.getLocalHost().getHostAddress();
			LOGGER.info("Ip Address = " + IP_ADDRESS);
			SERVER_URL = "tcp://" + IP_ADDRESS + ":1883";
			LOGGER.info("Server Url = " + SERVER_URL);
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
			LOGGER.info("disconnected!");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Disconnect error", e);
		}
	}

	private void initMqtt() {
		try {

			clientSend = new MqttClient(SERVER_URL, "TestSendClient");
			clientReceive = new MqttClient(SERVER_URL, "TestReceiveClient");

			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);

			clientSend.connect(options);
			clientReceive.connect(options);

			clientReceive.subscribe("alicia/jaime");
			clientReceive.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub

				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String msg = new String(message.getPayload());
					LOGGER.info(topic + " ------------ " + msg);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
				}

			});

			TASK_SERVICE.scheduleAtFixedRate(testSend, 5, 5, TimeUnit.SECONDS);

		} catch (Exception e) {
			LOGGER.error("Init MQTT Error", e);
		}

	}

	private final Runnable testSend = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				MqttMessage message = new MqttMessage();
				message.setPayload(("The date & time now is: " + new Date()).getBytes());
				clientSend.publish("alicia/jaime", message);
			} catch (Exception e) {
				LOGGER.error("send error", e);
			}
		}
	};

}
