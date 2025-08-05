package br.com.Alvaro.DC1_ApiFIPE.service;

import java.util.List;

public interface IConverteDadosAPI {
    <T> T obterDados(String json, Class<T> classe);
    <T> List<T> obterLista(String json, Class<T> classe);
}
