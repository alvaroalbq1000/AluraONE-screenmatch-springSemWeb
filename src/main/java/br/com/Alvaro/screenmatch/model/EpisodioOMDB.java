package br.com.Alvaro.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodioOMDB(@JsonAlias("Title") String titulo,
                           @JsonAlias("Released") String dataLanc,
                           @JsonAlias("Episode") Integer numEp,
                           @JsonAlias("imdbRating") String notaIMDB) {
}
