package br.com.Alvaro.DC1_ApiFIPE.main;

import br.com.Alvaro.DC1_ApiFIPE.model.Dados;
import br.com.Alvaro.DC1_ApiFIPE.model.DadosVeiculo;
import br.com.Alvaro.DC1_ApiFIPE.model.Modelos;
import br.com.Alvaro.DC1_ApiFIPE.service.ConsumoAPI;
import br.com.Alvaro.DC1_ApiFIPE.service.ConverteDadosAPI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainDC1 {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDadosAPI conversor = new ConverteDadosAPI();
    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void buscaVeiculo(String veiculo){
        String endApi = ENDERECO + veiculo + "/marcas/";
        String json = consumoAPI.obterDados(endApi);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        System.out.println("\n===MARCAS===");
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(d -> System.out.printf("Cód: %s  \tMarca: %s\n", d.codigo(), d.nome()));

        System.out.print("Digite o código da marca desejada: ");
        var marca = scanner.nextInt();
        scanner.nextLine();
        endApi += marca + "/modelos/";
        json = consumoAPI.obterDados(endApi);
        var modelos = conversor.obterDados(json, Modelos.class);
        System.out.println("\n===MODELOS===");
        modelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(d -> System.out.printf("Cód: %s    \tModelo: %s\n", d.codigo(), d.nome()));

        System.out.print("Digite o nome do carro para buscar: ");
        var carroBusca = scanner.nextLine();
        System.out.println("\n===BUSCA MODELOS===");
        modelos.modelos().stream()
                .filter(d -> d.nome().toUpperCase().contains(carroBusca.toUpperCase()))
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(d -> System.out.printf("Cód: %s    \tModelo: %s\n", d.codigo(), d.nome()));

        System.out.print("Digite o código do modelo desejado: ");
        var modelo = scanner.nextInt();
        scanner.nextLine();
        endApi += modelo + "/anos/";
        json = consumoAPI.obterDados(endApi);
        var veiculos = conversor.obterLista(json, Dados.class);
        List<DadosVeiculo> dadosVeiculos = new ArrayList<>();
        final var endApi2 = endApi;
        veiculos.forEach(d -> dadosVeiculos.add(conversor.obterDados(consumoAPI.obterDados(endApi2 + d.codigo()), DadosVeiculo.class)));
        System.out.println("\n===TABELA VEICULO===");
        dadosVeiculos.forEach(d -> System.out.printf("%s %s (%s): Valor= %s, Combustível= %s\n",d.Marca(), d.Modelo(), d.AnoModelo(), d.Valor(), d.Combustivel()));
        System.out.print("\nAperte Enter para continuar...");
        scanner.nextLine();
    }

    public void principal(){
        System.out.println("Bem vindo à Consulta de Veículos na Tabela FIPE!");
        int opt;
        do {
            System.out.println("""
                \n===OPÇÕES===
                1 - carros
                2 - motos
                3 - caminhoes
                0 - sair
                """);
            System.out.print("Digite o numero da opção desejada: ");
            opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1: {buscaVeiculo("carros");break;}
                case 2: {buscaVeiculo("motos");break;}
                case 3: {buscaVeiculo("caminhoes");break;}
                case 0: {System.out.println("Saindo...");break;}
                default: {System.out.println("Opção inválida! Tente novamente");break;}
            }
        } while (opt != 0);
    }
}
