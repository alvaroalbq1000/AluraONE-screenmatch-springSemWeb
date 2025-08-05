package br.com.Alvaro.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer numTemp, numEp;
    private String tituloEp;
    private Double notaImdbEp;
    private LocalDate dataLancEp;

    public Episodio(Integer temporada, EpisodioOMDB epOMDB) {
        this.numTemp = temporada;
        this.numEp = epOMDB.numEp();
        this.tituloEp = epOMDB.titulo();
        try {
            this.notaImdbEp = Double.valueOf(epOMDB.notaIMDB());
        } catch (NumberFormatException e) {
            this.notaImdbEp = 0.0;
        }
        try{
            this.dataLancEp = LocalDate.parse(epOMDB.dataLanc());
        }catch (DateTimeParseException e){
            this.dataLancEp = null;
        }
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public Integer getNumEp() {
        return numEp;
    }

    public void setNumEp(Integer numEp) {
        this.numEp = numEp;
    }

    public String getTituloEp() {
        return tituloEp;
    }

    public void setTituloEp(String tituloEp) {
        tituloEp = tituloEp;
    }

    public Double getNotaImdbEp() {
        return notaImdbEp;
    }

    public void setNotaImdbEp(Double notaImdbEp) {
        this.notaImdbEp = notaImdbEp;
    }

    public LocalDate getDataLancEp() {
        return dataLancEp;
    }

    public void setDataLancEp(LocalDate dataLancEp) {
        this.dataLancEp = dataLancEp;
    }

    @Override
    public String toString() {
        return "T%dE%d: '%s', notaIMDB=%.2f, dataLanc=%s".formatted(numTemp, numEp, tituloEp, notaImdbEp, dataLancEp);
    }
}
