package onestop.controller.rest;

import java.util.List;

import onestop.model.global.District;
import onestop.model.global.Province;
import onestop.service.EntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/REST/Global")
public class GlobalRestController {
	
	@Autowired
	private EntityService entityService;
	
	@RequestMapping(value = "/provinces", method = RequestMethod.GET)
	public List<Province> findAllProvince() {
		return entityService.findProvinces();
	}
	
	@RequestMapping(value = "/province/{id}/districts", method = RequestMethod.GET)
	public List<District> findAllDistrictOfProvince(@PathVariable Long id) {
		return entityService.findDistrictsOfProvince(id);
	}
}
