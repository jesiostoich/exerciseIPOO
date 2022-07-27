package ar.edu.unnoba.ui;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class modelHistorial extends AbstractListModel implements ListModel, Observer {

    private List<Clima> historial = new ArrayList<Clima>();
    private EstacionMeteorologica estacion;

    public modelHistorial(EstacionMeteorologica estacion){
        this.estacion = estacion;
        getEstacion().addObserver(this);
    }

    public EstacionMeteorologica getEstacion() {
        return estacion;
    }

    public void setEstacion(EstacionMeteorologica estacion) {
        this.estacion = estacion;
    }


    @Override
    public int getSize() {
        return historial.size();
    }

    @Override
    public Clima getElementAt(int index) {
        return getHistorial().get(index);
    }

    public List<Clima> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Clima> historial) {
        this.historial = historial;
    }

    @Override
    public void update(Observable o, Object arg) {

        List<Clima> climas = (List<Clima>) arg;
        if(climas.isEmpty()){
            setHistorial(climas);
            this.fireIntervalAdded(this, 0, getSize());
        }
        setHistorial(climas);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
}
