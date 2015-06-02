package onestop.service;

import onestop.model.Rfq;
import onestop.webui.ResponseJSend;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface EntityService {

	ResponseJSend<Page<Rfq>> findRfqByExample(JsonNode node, Integer pageNum);

	Rfq findRfqById(Long id);

	ResponseJSend<Rfq> saveRfq(JsonNode node) throws JsonMappingException;

	ResponseJSend<Rfq> deleteRfq(Long id);

}
