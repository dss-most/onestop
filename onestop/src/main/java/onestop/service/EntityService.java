package onestop.service;

import java.util.List;

import onestop.model.Rfq;
import onestop.model.global.District;
import onestop.model.global.Province;
import onestop.webui.ResponseJSend;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface EntityService {

	ResponseJSend<Page<Rfq>> findRfqByExample(JsonNode node, Integer pageNum) throws JsonMappingException;

	Rfq findRfqById(Long id);

	ResponseJSend<Rfq> saveRfq(JsonNode node) throws JsonMappingException;

	ResponseJSend<Rfq> deleteRfq(Long id);

	List<Province> findProvinces();

	List<District> findDistrictsOfProvince(Long id);

	void saveRfq(Rfq rfq);

}
