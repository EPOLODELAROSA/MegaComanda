package com.megacomanda.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private static int siguienteId = 1;
    private final int id;
    private final Mesa mesa;
    private final List<ItemPedido> items = new ArrayList<>();
    private EstadoPedido estado;
    private EstadoPedido estadoAntesDeCancelar;
    private final LocalDateTime fechaCreacion;
    public Pedido(Mesa mesa) {
        if (mesa == null) throw new IllegalArgumentException("El pedido debe tener una mesa.");
        this.id = siguienteId++; this.mesa = mesa; this.estado = null; this.fechaCreacion = LocalDateTime.now();
    }
    public void agregarItem(ItemPedido item) {
        if (estado != null) throw new IllegalStateException("No se puede modificar un pedido enviado.");
        items.add(item);
    }
    public void eliminarUltimoItem() {
        if (estado != null) throw new IllegalStateException("No se puede modificar un pedido enviado.");
        if (!items.isEmpty()) items.remove(items.size() - 1);
    }
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) throw new IllegalArgumentException("Estado inválido.");
        if (items.isEmpty()) throw new IllegalStateException("No se puede enviar un pedido vacío.");
        boolean permitido = switch (nuevoEstado) {
            case ENVIADO -> estado == null || estado == EstadoPedido.SOLICITUD_CANCELACION;
            case SOLICITUD_CANCELACION -> estado == EstadoPedido.ENVIADO || estado == EstadoPedido.LISTO;
            case LISTO -> estado == EstadoPedido.ENVIADO || estado == EstadoPedido.SOLICITUD_CANCELACION;
            case CANCELADO -> estado == EstadoPedido.SOLICITUD_CANCELACION;
            case ENTREGADO -> estado == EstadoPedido.LISTO;
            case PAGADO -> estado == EstadoPedido.ENTREGADO;
        };
        if (!permitido) throw new IllegalStateException("Transición no permitida: " + estado + " → " + nuevoEstado);
        if (nuevoEstado == EstadoPedido.SOLICITUD_CANCELACION) estadoAntesDeCancelar = estado;
        if (nuevoEstado == EstadoPedido.ENVIADO && estado == EstadoPedido.SOLICITUD_CANCELACION) {
            estado = estadoAntesDeCancelar == null ? EstadoPedido.ENVIADO : estadoAntesDeCancelar;
            estadoAntesDeCancelar = null;
            return;
        }
        estado = nuevoEstado;
        if (nuevoEstado == EstadoPedido.CANCELADO) estadoAntesDeCancelar = null;
    }
    public int getId() { return id; }
    public Mesa getMesa() { return mesa; }
    public List<ItemPedido> getItems() { return Collections.unmodifiableList(items); }
    public EstadoPedido getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public double getTotal() { return items.stream().mapToDouble(ItemPedido::getSubtotal).sum(); }
    @Override public String toString() { return "Pedido #" + id + " - " + mesa; }
}
