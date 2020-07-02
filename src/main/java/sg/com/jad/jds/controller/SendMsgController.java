package sg.com.jad.jds.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SendMsgController {

	private static final SimpleDateFormat MSG_LOGS_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	private ArrayList<String> msgLogs = new ArrayList<String>();

	@GetMapping("/sendMsg")
	public String index(Model model) {
		model.addAttribute("msgLogs", msgLogs);
		return "sendMsg";
	}

	@RequestMapping(value = "/execute")
	public String sendMsg(@RequestParam String msg) {
		if (!msg.isEmpty())
			msgLogs.add(MSG_LOGS_FORMAT.format(new Date()) + ":      " + msg);
		return "redirect:sendMsg";
	}

	@RequestMapping(value = "/reset")
	public String reset() {
		msgLogs.clear();
		return "redirect:sendMsg";
	}
}
