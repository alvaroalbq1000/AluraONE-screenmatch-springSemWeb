package br.com.Alvaro.screenmatch.main;

import br.com.Alvaro.screenmatch.model.Episodio;
import br.com.Alvaro.screenmatch.model.SerieOMDB;
import br.com.Alvaro.screenmatch.model.TemporadaOMDB;
import br.com.Alvaro.screenmatch.service.ConsumoAPI;
import br.com.Alvaro.screenmatch.service.ConverteDadosAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDadosAPI conversor = new ConverteDadosAPI();
    private final String ENDERECO = "https://www.omdbapi.com/?t=", API_KEY = "&apikey=b4a8766e";
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu(){
        System.out.println("=====Bem vindo ao SCREENMATCH!=====");
        System.out.println("Digite o nome da série que deseja buscar:");
        var serie = scanner.nextLine().replace(" ", "+");
        var json = consumoAPI.obterDados(ENDERECO + serie + API_KEY);
        System.out.println(json);
        var dadosSerie = conversor.obterDados(json, SerieOMDB.class);
        System.out.println(dadosSerie);

        List<TemporadaOMDB> temporadasSerie = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.temporadas(); i++){
            json = consumoAPI.obterDados(ENDERECO + serie + "&Season=" + i + API_KEY);
            temporadasSerie.add(conversor.obterDados(json, TemporadaOMDB.class));
        }
//        temporadasSerie.forEach(t -> t.episodiosTemporada().forEach(System.out::println));

//        List<EpisodioOMDB> episodiosOMDB = temporadasSerie.stream()
//                .flatMap(t -> t.episodiosTemporada().stream())
//                .collect(Collectors.toList());

//        episodiosOMDB.stream()
//                .filter(e -> !e.notaIMDB().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodioOMDB::notaIMDB).reversed())
//                .limit(5)
//                .forEach(System.out::println);

        System.out.println("===Episódios===");
        List<Episodio> episodios = temporadasSerie.stream()
                .flatMap(t -> t.episodiosTemporada().stream()
                        .map(e -> new Episodio(t.numTemp(), e)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

//        System.out.println("\n===Top 5 episódios===");
//        episodios.stream()
//                .sorted(Comparator.comparing(Episodio::getNotaImdbEp).reversed())
//                .limit(5)
//                .forEach(System.out::println);

        System.out.println("\n===Estatísticas===");
        Map<Integer, DoubleSummaryStatistics> estTemporadas = episodios.stream()
                .filter(e -> e.getNotaImdbEp() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getNumTemp, Collectors.summarizingDouble(Episodio::getNotaImdbEp)));
        estTemporadas.forEach((t, est) -> System.out.printf("T%d: médiaIMDB=%.2f, maiorNota=%.2f, menorNota=%.2f\n", t, est.getAverage(), est.getMax(), est.getMin()));
        int opt;
        do {
            System.out.println("""
                    Menu de ações:
                    1 - visualizar episódios lançados antes de algum ano
                    2 - visualizar episódios lançados depois de algum ano
                    3 - Buscar algum episódio por nome
                    0 - Sair""");
            opt = scanner.nextInt();
            scanner.nextLine();
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            int anoBusca;
            String trechoBusca;
            switch (opt) {
                case 1: {
                    System.out.print("Digite o ano:");
                    anoBusca = scanner.nextInt();
                    scanner.nextLine();
                    episodios.stream()
                            .filter(e -> e.getDataLancEp() != null && e.getDataLancEp().isBefore(LocalDate.of(anoBusca, 1, 1)))
                            .forEach(e -> System.out.printf("T%dE%d: '%s', data: %s\n",
                                    e.getNumTemp(), e.getNumEp(), e.getTituloEp(), e.getDataLancEp().format(formatoData)));
                    break;
                }
                case 2: {
                    System.out.print("Digite o ano:");
                    anoBusca = scanner.nextInt();
                    scanner.nextLine();
                    episodios.stream()
                            .filter(e -> e.getDataLancEp() != null && e.getDataLancEp().isAfter(LocalDate.of(anoBusca, 1, 1)))
                            .forEach(e -> System.out.printf("T%dE%d: '%s', data: %s\n",
                                    e.getNumTemp(), e.getNumEp(), e.getTituloEp(), e.getDataLancEp().format(formatoData)));
                    break;
                }
                case 3: {
                    System.out.print("Digite o nome a ser buscado:");
                    trechoBusca = scanner.nextLine();
                    Optional<Episodio> trechoBuscaPriResultado = episodios.stream()
                            .filter(e -> e.getTituloEp().toUpperCase().contains(trechoBusca.toUpperCase()))
                            .findFirst();
                    if (trechoBuscaPriResultado.isPresent()){
                        System.out.println("Episódio (1º) encontrado:\n" + trechoBuscaPriResultado.get());
                    }else{
                        System.out.println("Episódio não encontrado.");
                    }
                    break;
                }
                case 0: {
                    System.out.println("Saindo...");
                    break;
                }
                default: {
                    System.out.println("Opção inválida!");
                    break;
                }
            }
        } while (opt != 0);
    }
}