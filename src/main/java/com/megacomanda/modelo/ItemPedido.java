package com.megacomanda.modelo;

public class ItemPedido {
    private final Producto producto;
    private final int cantidad;
    private final String observacion;
    public ItemPedido(Producto producto, int cantidad, String observacion) {
        if (producto == null) throw new IllegalArgumentException("Selecciona un producto.");
        if (cantidad < 1) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        this.producto = producto; this.cantidad = cantidad;
        this.observacion = observacion == null ? "" : observacion.trim();
    }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public String getObservacion() { return observacion; }
    public double getSubtotal() { return producto.getPrecio() * cantidad; }
}
