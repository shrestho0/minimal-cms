package me.shrestho.minimalcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Stand-alone API for minimal-cms
 */
@SpringBootApplication
@EnableJpaAuditing
public class MinimalCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinimalCmsApplication.class, args);
	}

}
