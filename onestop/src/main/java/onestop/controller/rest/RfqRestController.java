package onestop.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import onestop.model.Rfq;
import onestop.service.EntityService;
import onestop.webui.ResponseJSend;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/REST/Rfq")
public class RfqRestController {
	
	public static Logger logger = LoggerFactory.getLogger(RfqRestController.class);
	
	@Autowired
	private EntityService entityService;
	
	@RequestMapping(value = "/search/page/{pageNum}", method = {RequestMethod.POST}) 
	public ResponseJSend<Page<Rfq>> findByExample(
			@RequestBody JsonNode node, @PathVariable Integer pageNum) throws JsonMappingException {
		return entityService.findRfqByExample(node, pageNum);
	}

	@RequestMapping(value= "/{id}", method = {RequestMethod.GET})
	public Rfq findById(@PathVariable Long id) {
		return entityService.findRfqById(id);
	}
	
	@RequestMapping(value= "/{id}", method = {RequestMethod.PUT})
	public ResponseJSend<Rfq> updateById(@RequestBody JsonNode node
			) throws JsonMappingException {
		return entityService.saveRfq(node);
	}
	
	@RequestMapping(value= "/{id}", method = {RequestMethod.DELETE})
	public ResponseJSend<Rfq> deleteById(
			@PathVariable Long id) {
		return entityService.deleteRfq(id);
	}
	
	
	@RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT}) 
	public ResponseJSend<Rfq> save(@RequestBody JsonNode node) throws JsonMappingException {
		
		return this.entityService.saveRfq(node);
	}

}
