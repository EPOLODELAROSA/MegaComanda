package com.megacomanda.modelo;

public class Producto {
    private final String nombre;
    private final double precio;
    private final String categoria;

    public Producto(String nombre, double precio) { this(nombre, precio, "Otros"); }

    public Producto(String nombre, double precio, String categoria) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria == null || categoria.isBlank() ? "Otros" : categoria;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }

    @Override public String toString() {
        return nombre + " - $" + String.format("%,.0f", precio);
    }
}
