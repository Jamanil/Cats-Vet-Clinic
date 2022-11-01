package ru.jamanil.catVetClinicDb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Victor Datsenko
 * 27.10.2022
 */
@Configuration
public class CatsVetClinicConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
