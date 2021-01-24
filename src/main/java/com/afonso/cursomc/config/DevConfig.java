package com.afonso.cursomc.config;

import com.afonso.cursomc.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Afonso
 */
@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instanciateDatabase() throws Exception {
        if (!strategy.equals("create")) {
            return false;
        }

        dbService.instantiateTestDatabase();
        return true;
    }
}
