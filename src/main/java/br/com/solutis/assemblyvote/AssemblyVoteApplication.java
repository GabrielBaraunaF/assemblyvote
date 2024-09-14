package br.com.solutis.assemblyvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AssemblyVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblyVoteApplication.class, args);
	}

}
