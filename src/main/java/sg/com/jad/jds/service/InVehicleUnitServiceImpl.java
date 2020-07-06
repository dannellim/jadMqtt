package sg.com.jad.jds.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.com.jad.jds.config.IvdMsgID;
import sg.com.jad.jds.helper.Util;
import sg.com.jad.jds.model.IvdHeader;
import sg.com.jad.jds.model.Job;
import sg.com.jad.jds.model.JobAccept;

@Service
public class InVehicleUnitServiceImpl {

	@Autowired
	private JdsServerServiceImpl jdsMQTTService;

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
		String message = 
				Util.decToHex(ivdHeader.getID())
				+ Util.decToHex(ivdHeader.getSN())
				+ Util.decToHex(ivdHeader.getMobile_id())
				+ Util.decToHex((int) (ivdHeader.getLatitude()*1000000))
				+ Util.decToHex((int)(ivdHeader.getLongtitude()*1000000))
				+ Util.decToHex(ivdHeader.getSpeed())
				+ Util.decToHex(ivdHeader.getDirection())
				+ Util.asciiToHex(ivdHeader.getStatus())
				+ Util.decToHex(ivdHeader.getZone());
		jdsMQTTService.message(message,"powerup");
	}
	
	private synchronized int setSN() {
		if (PACKET_SEQUENCE_NUMBER == MAX_PACKET_SEQUENCE_NUMBER) { // reset to zero once serial number hits 127
			PACKET_SEQUENCE_NUMBER = 0;
		} else if (PACKET_SEQUENCE_NUMBER != 0) {
			PACKET_SEQUENCE_NUMBER++;
		}
		return PACKET_SEQUENCE_NUMBER;
	}
	
	private IvdHeader getIvdHeader(int cmdID, String status) {
		IvdHeader ivdHeader = new IvdHeader();
		ivdHeader.setID(cmdID);
		ivdHeader.setSN(setSN());
		ivdHeader.setMobile_id(MOBILE_ID);
		ivdHeader.setLatitude(1.3200000524520874f);
		ivdHeader.setLongtitude(103.8198013305664f);
		ivdHeader.setSpeed(0);
		ivdHeader.setDirection(0);
		ivdHeader.setStatus(status);
		ivdHeader.setZone(30);
		return ivdHeader;
	}

}
