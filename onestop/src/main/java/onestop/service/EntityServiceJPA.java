package onestop.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import onestop.model.Rfq;
import onestop.repository.RfqRepo;
import onestop.webui.ResponseJSend;
import onestop.webui.ResponseStatus;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntityServiceJPA implements EntityService {

	@Autowired
	private RfqRepo rfqReo;
	
	private ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		mapper.setDateFormat(sdf);
		return mapper;
	}
	
	@Override
	public ResponseJSend<Page<Rfq>> findRfqByExample(JsonNode node,
			Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rfq findRfqById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ResponseJSend<Rfq> saveRfq(JsonNode node) throws JsonMappingException {
		ObjectMapper mapper = getObjectMapper();
		
		ObjectNode object = (ObjectNode) node;
		
		Rfq webModel;
		
		try {
			webModel = mapper.treeToValue(object, Rfq.class);
			webModel.setRfqDate(new Date());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JsonMappingException(e.getMessage() + "\n  JSON: " + node.toString());
		}
		
		Rfq dbModel = null;
				
		if(webModel.getId() == null) {
			dbModel = new Rfq();
			
		} else {
			dbModel = rfqReo.findOne(webModel.getId());
		}
		
		BeanUtils.copyProperties(webModel, dbModel);
		
		rfqReo.save(dbModel);

		ResponseJSend<Rfq> response = new ResponseJSend<Rfq>();
		response.status = ResponseStatus.SUCCESS;
		response.data = dbModel;
		return response;
	
	}

	@Override
	public ResponseJSend<Rfq> deleteRfq(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
