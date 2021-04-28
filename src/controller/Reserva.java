package controller;

import java.time.LocalDate;

public class Reserva {
    private LocalDate DataFesta;
    private LocalDate DataPrevista;
    private LocalDate DataRetorno;
    private int HoraInicio;
    private int HoraRetorno;
    private String FormaDePagamento;
    private double PrecoFinal;

    public Reserva(LocalDate DataFesta, LocalDate DataPrevista, LocalDate DataRetorno, int HoraInicio, int HoraRetorno, String FormaDePagamento, double PrecoFinal) {
        this.DataFesta = DataFesta;
        this.DataPrevista = DataPrevista;
        this.DataRetorno = DataRetorno;
        this.HoraInicio = HoraInicio;
        this.HoraRetorno = HoraRetorno;
        this.FormaDePagamento = FormaDePagamento;
        this.PrecoFinal = PrecoFinal;
    }

    public LocalDate getDataFesta() {
        return DataFesta;
    }

    public void setDataFesta(LocalDate dataFesta) {
        DataFesta = dataFesta;
    }

    public LocalDate getDataPrevista() {
        return DataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        DataPrevista = dataPrevista;
    }

    public LocalDate getDataRetorno() {
        return DataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        DataRetorno = dataRetorno;
    }
    
    public int getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(int horaInicio) {
        HoraInicio = horaInicio;
    }

    public int getHoraRetorno() {
        return HoraRetorno;
    }

    public void setHoraRetorno(int horaRetorno) {
        HoraRetorno = horaRetorno;
    }

    public String getFormaDePagamento() {
        return FormaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        FormaDePagamento = formaDePagamento;
    }

    public double getPrecoFinal() {
        return PrecoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        PrecoFinal = precoFinal;
    }
}