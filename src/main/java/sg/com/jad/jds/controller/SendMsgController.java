package sg.com.jad.jds.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.com.jad.jds.config.IvdMsgID;
import sg.com.jad.jds.model.IvdHeader;
import sg.com.jad.jds.service.GeolocationServiceImpl;
import sg.com.jad.jds.service.InVehicleUnitServiceImpl;
import sg.com.jad.jds.service.JdsServerServiceImpl;

@Controller
public class SendMsgController {

	// variables here
	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);
	private static final SimpleDateFormat MSG_LOGS_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	@Autowired
	private JdsServerServiceImpl jdsMQTTService;
	@Autowired
	private GeolocationServiceImpl geolocationService;
	@Autowired
	private InVehicleUnitServiceImpl inVehicleUnitService;
	
	@GetMapping("/")
	public String index(Model model) {
		//model.addAttribute("msgLogs", msgLogs);
		return "index";
	}
	

	@GetMapping("/sendMsg")
	public String sendMsg(Model model) {
		ArrayList<String> msgLogs = jdsMQTTService.getMsgLogs();
		model.addAttribute("msgLogs", msgLogs);
		return "sendMsg";
	}

	@RequestMapping(value = "/execute")
	public String execute(Model model, @RequestParam String msg) {
		if (!msg.isEmpty()) {
			jdsMQTTService.sendMsg(msg);
		}
		return "redirect:sendMsg";
	}

	@RequestMapping(value = "/reset")
	public String reset() {
		jdsMQTTService.clearLogs();
		return "redirect:sendMsg";
	}
	
	// test
	@RequestMapping("/location")
	public String getLocation() {
		return "redirect:sendMsg";
		
	}
	
}
