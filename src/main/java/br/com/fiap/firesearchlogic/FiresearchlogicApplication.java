package br.com.fiap.firesearchlogic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FiresearchlogicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiresearchlogicApplication.class, args);
	}

}
