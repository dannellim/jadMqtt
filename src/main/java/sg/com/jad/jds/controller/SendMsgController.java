package sg.com.jad.jds.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.com.jad.jds.service.JdsServerServiceImpl;

@Controller
public class SendMsgController {

	// variables here
	private static final Logger LOGGER = LogManager.getLogger(JdsServerServiceImpl.class);
	private static final SimpleDateFormat MSG_LOGS_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private ArrayList<String> msgLogs = new ArrayList<String>();
	
	private String str_message;
	
	@Autowired
	private JdsServerServiceImpl jdsMQTTService;
	
	@GetMapping("/")
	public String index(Model model) {
		//model.addAttribute("msgLogs", msgLogs);
		return "index";
	}

	@GetMapping("/sendMsg")
	public String sendMsg(Model model) {
		model.addAttribute("msgLogs", msgLogs);
		return "sendMsg";
	}

	@RequestMapping(value = "/execute")
	public String execute(Model model, @RequestParam String msg) {
		if (!msg.isEmpty()) {
			
			// 
			msgLogs.add(MSG_LOGS_FORMAT.format(new Date()) + ":      " + msg);
			
			// -------------------------------------------------
			// send message here
			jdsMQTTService.sendMsg(msg);
			
			// get item here
//			this.str_message = jdsMQTTService.str_message;
			this.str_message = jdsMQTTService.getMessage();
			// -------------------------------------------------
			
			
			// test
			LOGGER.info("controller str_message is: " + this.str_message);
			
			// pass to view here
			model.addAttribute("msgLogs", msgLogs);
			model.addAttribute("str_message", this.str_message);
			
		}
		return "redirect:sendMsg";
	}

	@RequestMapping(value = "/reset")
	public String reset() {
		msgLogs.clear();
		return "redirect:sendMsg";
	}
}
