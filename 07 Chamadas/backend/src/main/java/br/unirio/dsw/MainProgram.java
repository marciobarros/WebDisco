package br.unirio.dsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:configuration.properties")
public class MainProgram
{
	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(MainProgram.class, args);
	}
}