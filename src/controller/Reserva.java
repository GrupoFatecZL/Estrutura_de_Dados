package controller;

import java.time.LocalDate;
import java.time.LocalTime;

import cliente.Clientes;
import enfeite.Enfeites;

public class Reserva {
    private LocalDate DataFesta;
    private LocalDate DataPrevista;
    private LocalDate DataRetorno;
    private LocalTime HoraInicio;
    private LocalTime HoraPrevisto;
    private LocalTime HoraRetorno;
    private String FormaDePagamento;
    private double PrecoFinal;

    private Clientes cliente;
    private Enfeites enfeite;

    public Reserva( LocalDate DataFesta, LocalDate DataPrevista, 
    LocalDate DataRetorno, LocalTime HoraInicio, LocalTime HoraPrevisto, LocalTime HoraRetorno, 
    String FormaDePagamento, double PrecoFinal, Clientes cliente, Enfeites enfeite ) {
        this.DataFesta = DataFesta;
        this.DataPrevista = DataPrevista;
        this.DataRetorno = DataRetorno;
        this.HoraInicio = HoraInicio;
        this.HoraPrevisto = HoraPrevisto;
        this.HoraRetorno = HoraRetorno;
        this.FormaDePagamento = FormaDePagamento;
        this.PrecoFinal = PrecoFinal;
        this.cliente = cliente;
        this.enfeite = enfeite;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public LocalTime getHoraPrevisto() {
        return HoraPrevisto;
    }

    public void setHoraPrevisto(LocalTime horaPrevisto) {
        HoraPrevisto = horaPrevisto;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Enfeites getEnfeite() {
        return enfeite;
    }

    public void setEnfeite(Enfeites enfeite) {
        this.enfeite = enfeite;
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
    
    public LocalTime getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        HoraInicio = horaInicio;
    }

    public LocalTime getHoraRetorno() {
        return HoraRetorno;
    }

    public void setHoraRetorno(LocalTime horaRetorno) {
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