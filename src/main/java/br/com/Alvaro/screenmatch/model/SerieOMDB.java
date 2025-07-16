package br.com.Alvaro.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieOMDB(@JsonAlias("Title") String titulo,
                        @JsonAlias("Year") String anoExibicao,
                        @JsonAlias("imdbRating") String notaIMDB,
                        @JsonAlias("totalSeasons") Integer temporadas) {
}
