package sg.com.jad.jds.service;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.com.jad.jds.config.IvdMsgID;
import sg.com.jad.jds.helper.Util;
import sg.com.jad.jds.model.Geolocation;
import sg.com.jad.jds.model.IvdHeader;

@Service
public class GeolocationServiceImpl {
	
	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);
	private static final ScheduledExecutorService TASK_SERVICE = Executors.newSingleThreadScheduledExecutor();
	public static ArrayList<Geolocation> FAKE_MOVEMENT = new ArrayList<>();
	private static double lat = 0;
	private static double lon = 0;
	private static int speed = 0;
	private static int direction = 0;
	private static int index = 0;
	private static int start = 0;
	private static int speed_max = 120;
	private static int direction_max = 360;
	
	@Autowired
	private InVehicleUnitServiceImpl inVehicleUnitService;
	
	@PostConstruct
	public void postConstruct() {
		
		fakeGeolocation();
		TASK_SERVICE.scheduleAtFixedRate(LOCATION_SERVICE, 5, 5, TimeUnit.SECONDS);
	}
	
	private void fakeGeolocation() {
		FAKE_MOVEMENT.add(new Geolocation(1.331266, 103.967562, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.333310, 103.965887, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.334584, 103.961479, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.333608, 103.959848, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.332008, 103.948786, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.344804, 103.942059, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.309169, 103.886228, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.333410, 103.863686, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.341495, 103.834567, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.351177, 103.683242, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.333445, 103.683194, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.318835, 103.706677, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.264703, 103.822469, fakeSpeed(), fakeDirection()));
		FAKE_MOVEMENT.add(new Geolocation(1.331266, 103.967562, fakeSpeed(), fakeDirection()));
	}
	
	private static int fakeSpeed() {
		return Util.randomNumber(start, speed_max);
	}
	
	private static int fakeDirection() {
		return Util.randomNumber(start, direction_max);
	}
	
	private final Runnable LOCATION_SERVICE = new Runnable() {
		@Override
		public void run() {
			// variables here
			lat = FAKE_MOVEMENT.get(index).getLat();
			lon = FAKE_MOVEMENT.get(index).getLon();
			
			speed = FAKE_MOVEMENT.get(index).getSpeed();
			direction = FAKE_MOVEMENT.get(index).getDirection();
			
			
			if(index==FAKE_MOVEMENT.size()-1) {
				index = 0;
			} else {
				index++;
			}
//			LOGGER.info(FAKE_MOVEMENT.get(index).toString());
			
			// pass to header here
			getIvdHeader();
		}
	};
	
	public void getIvdHeader() {
		
		// set here
		IvdHeader ivdHeader = inVehicleUnitService.getIvdHeader(IvdMsgID.REGULAR_REPORT_ID, "ONLINE");
		
		// convert here
		String convertedHeader = inVehicleUnitService.convertHeader(ivdHeader);
		
		LOGGER.info("convertedHeader is: " + convertedHeader);
	}
	
	public Geolocation getLocation() {
		return new Geolocation(lat, lon, speed, direction);
	}

}
