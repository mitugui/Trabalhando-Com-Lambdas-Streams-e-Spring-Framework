package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ApiConsumption;
import br.com.alura.screenmatch.services.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumption = new ApiConsumption();
		var json =  apiConsumption.getData("http://www.omdbapi.com/?" +
				"t=The+Office" +
				"&apikey=");
		System.out.println(json);

		var converter = new DataConverter();
		SeriesData seriesData = converter.getData(json, SeriesData.class);
		System.out.println(seriesData);

		json = apiConsumption.getData("http://www.omdbapi.com/?" +
				"t=The+Office" +
				"&Season=2" +
				"&Episode=4" +
				"&apikey=");
		EpisodeData episodeData = converter.getData(json, EpisodeData.class);
		System.out.println(episodeData);
	}
}