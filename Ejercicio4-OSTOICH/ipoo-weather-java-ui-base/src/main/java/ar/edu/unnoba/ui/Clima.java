package ar.edu.unnoba.ui;

import ar.edu.unnoba.model.Channel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Clima {

    private int temperatura;
    private double presion;
    private double viento;
    private double humedad;
    private String fecha;
    private String ciudad;
    private String estado;
    private String url;

    public Clima(int temperatura, double presion, double viento, double humedad, String fecha, String ciudad, String estado, String url) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.viento = viento;
        this.humedad = humedad;
        this.fecha = fecha;
        this.ciudad = ciudad;
        this.estado = estado;
        this.url = url;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }


    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return getFecha() + " " + getCiudad() + " " + getTemperatura() + "° " + getEstado();
    }



}
