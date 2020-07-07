package sg.com.jad.jds.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.com.jad.jds.config.IvdMsgID;
import sg.com.jad.jds.helper.Util;
import sg.com.jad.jds.model.IvdHeader;

@Service
public class InVehicleUnitServiceImpl {

	@Autowired
	private JdsServerServiceImpl jdsMQTTService;
	
	@Autowired
	private GeolocationServiceImpl geolocationService;

	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);

	private static int PACKET_SEQUENCE_NUMBER = 0;
	private static final int MAX_PACKET_SEQUENCE_NUMBER = 127;
	private static int MOBILE_ID = 0;

	@PostConstruct
	public void postConstruct() {
		// send power up message here
		powerUp();
	}

	private void powerUp() {
		IvdHeader ivdHeader = getIvdHeader(IvdMsgID.POWER_UP_ID, "OFFLINE");
		String message = convertHeader(ivdHeader);
		LOGGER.info("powerUp() =========================== " + message);
		jdsMQTTService.message(message, "powerup");
	}

	private synchronized int setSN() {
		if (PACKET_SEQUENCE_NUMBER == MAX_PACKET_SEQUENCE_NUMBER) { // reset to zero once serial number hits 127
			PACKET_SEQUENCE_NUMBER = 0;
		} else if (PACKET_SEQUENCE_NUMBER != 0) {
			PACKET_SEQUENCE_NUMBER++;
		}
		return PACKET_SEQUENCE_NUMBER;
	}

	public IvdHeader getIvdHeader(int cmdID, String status) {
		IvdHeader ivdHeader = new IvdHeader();
		ivdHeader.setID(cmdID);
		ivdHeader.setSN(setSN());
		ivdHeader.setMobile_id(MOBILE_ID);
		ivdHeader.setLatitude(geolocationService.getLocation().getLat());
		ivdHeader.setLongtitude(geolocationService.getLocation().getLon());
		ivdHeader.setSpeed(geolocationService.getLocation().getSpeed());
		ivdHeader.setDirection(geolocationService.getLocation().getDirection());
		ivdHeader.setStatus(status);
		ivdHeader.setZone(30);
		return ivdHeader;
	}
	
	public String convertHeader(IvdHeader ivdHeader) {
		return Util.decToHex(ivdHeader.getID())
				+ Util.decToHex(ivdHeader.getSN())
				+ Util.decToHex(ivdHeader.getMobile_id()) 
				+ Util.decToHex((int) (ivdHeader.getLatitude() * 1000000))
				+ Util.decToHex((int) (ivdHeader.getLongtitude() * 1000000)) 
				+ Util.decToHex(ivdHeader.getSpeed())
				+ Util.decToHex(ivdHeader.getDirection()) 
				+ Util.asciiToHex(ivdHeader.getStatus())
				+ Util.decToHex(ivdHeader.getZone());
	}
}
