package io.secret.santa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecretSantaApplication {

	static {
		new ApplicationDBInitializer();
	}
	public static void main(String[] args) {
		SpringApplication.run(SecretSantaApplication.class, args);
	}

}
