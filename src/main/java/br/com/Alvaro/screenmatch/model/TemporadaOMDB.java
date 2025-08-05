package br.com.Alvaro.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadaOMDB(@JsonAlias("Season") Integer numTemp,
                            @JsonAlias("Episodes") List<EpisodioOMDB> episodiosTemporada) {
}
