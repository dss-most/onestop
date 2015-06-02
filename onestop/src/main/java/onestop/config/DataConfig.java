package onestop.config;

import onestop.service.EntityService;
import onestop.service.EntityServiceJPA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataConfig {
	@Bean
	public EntityService entityService() {
		return new EntityServiceJPA();
	}

}
