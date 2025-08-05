package br.com.Alvaro.DC1_ApiFIPE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(String Valor, String Marca, String Modelo, String AnoModelo, String Combustivel) {
}
