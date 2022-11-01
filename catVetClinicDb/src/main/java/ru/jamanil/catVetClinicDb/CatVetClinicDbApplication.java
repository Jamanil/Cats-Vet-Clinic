package ru.jamanil.catVetClinicDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.jamanil.catVetClinicDb.config.CatsVetClinicConfig;

@SpringBootApplication
public class CatVetClinicDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatVetClinicDbApplication.class, args);
	}
}
