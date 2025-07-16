package br.com.Alvaro.screenmatch;

import br.com.Alvaro.screenmatch.model.SerieOMDB;
import br.com.Alvaro.screenmatch.service.ConsumoAPI;
import br.com.Alvaro.screenmatch.service.ConverteDadosAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var conversor = new ConverteDadosAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=dragons+race+to+the+edge&apikey=b4a8766e");
		System.out.println(json);
		var dadosSerie = conversor.obterDados(json, SerieOMDB.class);
		System.out.println(dadosSerie);
	}
}
