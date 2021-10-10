package br.com.felipe.awss3;

import br.com.felipe.awss3.sdk.MySdkS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsS3Application implements CommandLineRunner {

	@Autowired
	private MySdkS3 uploadS3;

	public static void main(String[] args) {
		SpringApplication.run(AwsS3Application.class, args);
	}

	@Override
	public void run(String... args) {
		//uploadS3.upload();
	}
}
