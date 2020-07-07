package sg.com.jad.jds.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import sg.com.jad.jds.service.GeolocationServiceImpl;
import sg.com.jad.jds.service.JdsServerServiceImpl;

@Controller
public class GeolocationController {

	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);

	@Autowired
	private GeolocationServiceImpl geolocationService;
	
	public void getLocation() {
//		geolocationService.setLocation(ivdHeader);
	}
	
}
