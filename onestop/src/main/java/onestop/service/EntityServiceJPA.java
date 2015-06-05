package onestop.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import onestop.model.QRfq;
import onestop.model.Rfq;
import onestop.model.global.District;
import onestop.model.global.Province;
import onestop.repository.RfqRepo;
import onestop.webui.DefaultProperty;
import onestop.webui.ResponseJSend;
import onestop.webui.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysema.query.BooleanBuilder;

public class EntityServiceJPA implements EntityService {
	
	public static Logger logger = LoggerFactory.getLogger(EntityServiceJPA.class);

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
			Integer pageNum) throws JsonMappingException {
		logger.debug("findRfq");
		
		ObjectMapper mapper = getObjectMapper();
		
		Rfq webModel;
		
		try {
			webModel = mapper.treeToValue(node, Rfq.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JsonMappingException(e.getMessage() + "\n  JSON: " + node.toString());
		}
		
		QRfq q = QRfq.rfq;
		
		BooleanBuilder p = new BooleanBuilder();
		
		
		
		if(webModel.getId() != null) {
			
			p = p.and(q.id.eq(webModel.getId()));
		} 
		
		
		
		PageRequest pageRequest =
	            new PageRequest(pageNum -1, DefaultProperty.NUMBER_OF_ELEMENT_PER_PAGE, Sort.Direction.DESC, "id");
		
		Page<Rfq> rfqs = rfqReo.findAll(p, pageRequest); 
		
		ResponseJSend<Page<Rfq>> response = new ResponseJSend<Page<Rfq>>();
		response.data=rfqs;
		response.status=ResponseStatus.SUCCESS;
		
		return response;
	}

	@Override
	public Rfq findRfqById(Long id) {
		Rfq rfq= rfqReo.getOne(id);
		rfq.getFiles().size();
		
		return rfq;
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
		
		dbModel.setRfqDate(new Date());
		
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

	@Override
	public List<Province> findProvinces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<District> findDistrictsOfProvince(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRfq(Rfq rfq) {
		rfqReo.save(rfq);
	}
	
	
	
	
	

}
