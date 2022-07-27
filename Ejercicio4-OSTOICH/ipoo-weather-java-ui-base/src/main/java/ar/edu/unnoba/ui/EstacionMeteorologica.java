package ar.edu.unnoba.ui;

import ar.edu.unnoba.model.Channel;
import ar.edu.unnoba.model.City;
import ar.edu.unnoba.service.WeatherService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class EstacionMeteorologica extends Observable implements Observer{

    private String ciudad;

    private List<Clima> historialClimas = new ArrayList<Clima>();
    private Clima climaActual;

    private WeatherService service;

    public EstacionMeteorologica() {
        setService(new WeatherService(City.LaPlata, 5));
        service.addObserver(this);
        service.start();

    }

    public String getCiudad() {
        return ciudad;
    }

    public List<Clima> getHistorialClimas() {
        return historialClimas;
    }

    public void setHistorialClimas(List<Clima> historial){
        this.historialClimas = historial;
    }

    public void agregarClima(Clima unClima){
        historialClimas.add(unClima);
    }

    public void eliminarClima(Clima unClima){
        historialClimas.remove(unClima);
    }

    public WeatherService getService() {
        return service;
    }

    public void setService(WeatherService service) {
        this.service = service;
    }

    public void ordenarPorTemperatura(){
        List<Clima> aux = new ArrayList<Clima>();

        aux = getHistorialClimas().stream().sorted(Comparator.comparing(Clima::getTemperatura)).collect(Collectors.toList());
        setHistorialClimas(aux);
        setChanged();
        notifyObservers(historialClimas);


    }

    public void ordenarPorFecha(){
        List<Clima> aux = new ArrayList<Clima>();

        aux = getHistorialClimas().stream().sorted(Comparator.comparing(Clima::getFecha)).collect(Collectors.toList());
        setHistorialClimas(aux);
        setChanged();
        notifyObservers(historialClimas);

    }

    public void clean(){
        historialClimas.clear();
        setChanged();
        notifyObservers(historialClimas);

    }

    public void close() {
        getService().stop();
        getService().deleteObserver(this);
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public void setClimaActual(Clima climaActual) {
        this.climaActual = climaActual;
    }

    @Override
    public void update(Observable o, Object param) {
        Channel channel = (Channel) param;
        int temp = (int) channel.getTemperature();

        Clima newClima = new Clima(temp, channel.getPressure(), channel.getWind(), channel.getHumidity(), channel.getRequestedOn(), channel.getLocation(), channel.getState(), channel.getWeatherIconUrl());

        agregarClima(newClima);
        setClimaActual(newClima);
        setChanged();
        notifyObservers(historialClimas);
    }
}
