package br.com.Alvaro.screenmatch.service;

public interface IConverteDadosAPI {
    <T> T obterDados(String json, Class<T> classe);
}
