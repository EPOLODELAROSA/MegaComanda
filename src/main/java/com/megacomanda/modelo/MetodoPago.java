package com.megacomanda.modelo;

public enum MetodoPago {
    EFECTIVO("Efectivo"), TRANSFERENCIA("Transferencia");
    private final String etiqueta;
    MetodoPago(String etiqueta) { this.etiqueta = etiqueta; }
    public String getEtiqueta() { return etiqueta; }
    @Override public String toString() { return etiqueta; }
}
