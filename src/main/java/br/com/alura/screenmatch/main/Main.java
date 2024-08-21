package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ApiConsumption;
import br.com.alura.screenmatch.services.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        seasons.forEach(s ->
                s.episodes().forEach(e ->
                        System.out.println(e.title())));

        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("/nTop 5 episódios");
        episodesData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.number(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);
    }
}