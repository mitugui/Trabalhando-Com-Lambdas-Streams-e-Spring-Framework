package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ApiConsumption;
import br.com.alura.screenmatch.services.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner reading = new Scanner(System.in);
    private ApiConsumption apiConsumption = new ApiConsumption();
    private DataConverter converter = new DataConverter();

    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=";

    public void displayMenu() {
        System.out.print("Digite o nome da série para buscá-la: ");
        var seriesName = reading.nextLine();
        var json =  apiConsumption.getData(
                ADDRESS +
                        seriesName.replace(" ", "+")
                        + API_KEY);

        SeriesData seriesData = converter.getData(json, SeriesData.class);
        System.out.println(seriesData);



		List<SeasonData> seasons = new ArrayList<>();
		for (int i = 1; i <= seriesData.totalSeasons(); i++) {
			json = apiConsumption.getData(
                    ADDRESS +
                            seriesName.replace(" ", "+")
                            + "&Season=" + i
                            + API_KEY);

			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

		seasons.forEach(System.out::println);
    }
}