package com.megacomanda.modelo;

public class Mesa {
    private final int numero;
    public Mesa(int numero) { this.numero = numero; }
    public int getNumero() { return numero; }
    @Override public String toString() { return "Mesa " + numero; }
}
