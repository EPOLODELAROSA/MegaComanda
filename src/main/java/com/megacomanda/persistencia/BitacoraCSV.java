package com.megacomanda.persistencia;

import com.megacomanda.modelo.Pedido;
import com.megacomanda.modelo.Pago;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class BitacoraCSV {
    private final Path archivo = Path.of("bitacora_mega_comanda.csv");
    private final Path archivoPagos = Path.of("pagos_mega_comanda.csv");
    public BitacoraCSV() {
        try {
            if (Files.notExists(archivo)) {
                Files.writeString(archivo, "fecha,pedido,mesa,estado,total\n", StandardOpenOption.CREATE);
            }
        } catch (IOException e) { throw new IllegalStateException("No se pudo crear la bitácora CSV.", e); }
    }
    public void registrar(Pedido pedido) {
        String fila = String.format("%s,%d,%d,%s,%.0f%n", LocalDateTime.now(), pedido.getId(),
                pedido.getMesa().getNumero(), pedido.getEstado(), pedido.getTotal());
        try { Files.writeString(archivo, fila, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
        catch (IOException e) { throw new IllegalStateException("No se pudo escribir en la bitácora CSV.", e); }
    }
    public void registrarPago(Pago pago) {
        try {
            if (Files.notExists(archivoPagos)) Files.writeString(archivoPagos,
                    "fecha,id_pago,mesa,destino,monto,metodo,responsable,resultado\n", StandardOpenOption.CREATE);
        } catch (IOException e) { throw new IllegalStateException("No se pudo crear el historial de pagos.", e); }
        String destino = pago.getPedidoId() == null ? "MESA" : "COMANDA_" + pago.getPedidoId();
        String fila = String.format("%s,PAGO_%d,%d,%s,%d,%s,%s,%s%n", pago.getFecha(), pago.getId(),
                pago.getMesa().getNumero(), destino, pago.getMonto(), pago.getMetodo(),
                pago.getResponsable().replace(",", " "), "REGISTRADO");
        try { Files.writeString(archivoPagos, fila, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
        catch (IOException e) { throw new IllegalStateException("No se pudo escribir el pago en el historial CSV.", e); }
    }
    public Path getArchivo() { return archivo; }
    public Path getArchivoPagos() { return archivoPagos; }
}
