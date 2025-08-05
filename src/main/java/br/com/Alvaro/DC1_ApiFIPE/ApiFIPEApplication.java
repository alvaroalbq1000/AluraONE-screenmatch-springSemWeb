package br.com.Alvaro.DC1_ApiFIPE;

import br.com.Alvaro.DC1_ApiFIPE.main.MainDC1;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiFIPEApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(ApiFIPEApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var main = new MainDC1();
        main.principal();
    }
}
