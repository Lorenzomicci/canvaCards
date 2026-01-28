package com.terraludyca.canva.CardsCreator;

import org.springframework.boot.SpringApplication;
import com.terraludyca.ludoteca.config.LudotecaProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.terraludyca")
@EnableConfigurationProperties(LudotecaProperties.class)
public class CardsCreatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsCreatorApplication.class, args);
	}

}
