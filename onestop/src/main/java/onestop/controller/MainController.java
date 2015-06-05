package onestop.controller;

import onestop.webui.PageMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	private Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping("/")
	public String index(Model model) {
		log.debug("Entering : /");
	
		
		
		PageMenu menu = new PageMenu();
		menu.setHomeMenu(true);
		
		model.addAttribute("menu", menu);
		
		return "index";
	}
	
	@RequestMapping("/testingService")
	public String testingService(Model model) {
		log.debug("Entering : /testingService");
	
		
		
		PageMenu menu = new PageMenu();
		menu.setHomeMenu(true);
		
		model.addAttribute("menu", menu);
		
		return "testingService";
	}
	
	@RequestMapping("/checkStatus")
	public String checkStatus(Model model) {
		log.debug("Entering : /testingService");
	
		
		PageMenu menu = new PageMenu();
		menu.setHomeMenu(true);
		
		model.addAttribute("menu", menu);
		
		return "checkStatus";
	}
	
	
	@RequestMapping("/admin/testingService")
	public String adminTestingService(Model model) {
		log.debug("Entering : /testingService");
	
		
		PageMenu menu = new PageMenu();
		menu.setHomeMenu(true);
		
		model.addAttribute("menu", menu);
		
		return "adminTestingService";
	}
	
	
}
