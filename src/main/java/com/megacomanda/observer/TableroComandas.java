package com.megacomanda.observer;

import com.megacomanda.modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

/** Sujeto del patrón Observer: mantiene observadores y les comunica los cambios. */
public class TableroComandas {
    private final List<ObservadorPedido> observadores = new ArrayList<>();
    public void agregarObservador(ObservadorPedido observador) {
        if (observador != null && !observadores.contains(observador)) observadores.add(observador);
    }
    public void eliminarObservador(ObservadorPedido observador) { observadores.remove(observador); }
    public void notificarObservadores(Pedido pedido) {
        for (ObservadorPedido observador : List.copyOf(observadores)) observador.actualizarPedido(pedido);
    }
    public int cantidadObservadores() { return observadores.size(); }
}
