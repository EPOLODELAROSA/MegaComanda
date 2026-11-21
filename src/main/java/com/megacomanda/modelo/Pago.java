package com.megacomanda.modelo;

import java.time.LocalDateTime;

/** Registro inmutable de un abono recibido para una mesa. */
public class Pago {
    private final int id;
    private final Mesa mesa;
    private final Integer pedidoId; // null cuando el abono se registra a la cuenta general de la mesa
    private final long monto;
    private final MetodoPago metodo;
    private final String responsable;
    private final LocalDateTime fecha;

    public Pago(int id, Mesa mesa, Integer pedidoId, long monto, MetodoPago metodo,
                String responsable, LocalDateTime fecha) {
        if (mesa == null || monto <= 0 || metodo == null || responsable == null || responsable.isBlank() || fecha == null)
            throw new IllegalArgumentException("Datos del pago incompletos o inválidos.");
        this.id = id; this.mesa = mesa; this.pedidoId = pedidoId; this.monto = monto;
        this.metodo = metodo; this.responsable = responsable.trim(); this.fecha = fecha;
    }
    public int getId() { return id; }
    public Mesa getMesa() { return mesa; }
    public Integer getPedidoId() { return pedidoId; }
    public long getMonto() { return monto; }
    public MetodoPago getMetodo() { return metodo; }
    public String getResponsable() { return responsable; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDestino() { return pedidoId == null ? "Cuenta de mesa" : "Comanda #" + pedidoId; }
}
