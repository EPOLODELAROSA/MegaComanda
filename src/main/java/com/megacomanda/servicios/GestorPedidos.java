package com.megacomanda.servicios;

import com.megacomanda.modelo.*;
import com.megacomanda.observer.TableroComandas;
import com.megacomanda.persistencia.BitacoraCSV;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class GestorPedidos {
    private final List<Mesa> mesas = new ArrayList<>();
    private final List<Producto> menu = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();
    private final Map<Integer, Long> pagadoPorPedido = new LinkedHashMap<>();
    private int siguientePagoId = 1;
    private final TableroComandas tablero;
    private final BitacoraCSV bitacora;
    public GestorPedidos(TableroComandas tablero, BitacoraCSV bitacora) {
        this.tablero = tablero; this.bitacora = bitacora;
        for (int i = 1; i <= 16; i++) mesas.add(new Mesa(i));
        // Menú transcrito de las dos caras de la carta entregada por el restaurante.
        String alitas = "Combos de alitas y especiales";
        String broaster = "Pollo broaster";
        String asado = "Pollo asado";
        String presas = "Presas broaster";
        String adicionales = "Adicionales";
        String bebidas = "Bebidas";

        agregar(alitas, "5 alitas + papas francesas + gaseosa 250 ml", 16000);
        agregar(alitas, "4 alitas + 3 chuletas de pollo + papas francesas + gaseosa 250 ml", 18000);
        agregar(alitas, "7 alitas + papas francesas + gaseosa 250 ml", 19000);
        agregar(alitas, "10 alitas + papas francesas + gaseosa 250 ml", 25000);
        agregar(alitas, "12 alitas + 4 costillitas de cerdo + papas francesas + gaseosa 250 ml", 44000);
        agregar(alitas, "22 alitas + papas francesas + gaseosa 1.5 L", 50000);
        agregar(alitas, "10 alitas + 10 costillitas de cerdo + papas francesas + gaseosa 1.5 L", 60000);
        agregar(alitas, "Mixto: 4 alitas + 2 costillitas + papas francesas + gaseosa 250 ml", 22000);
        agregar(alitas, "Mega Snacks: nuggets de pollo + papas francesas + gaseosa", 16000);
        agregar(alitas, "1/2 pollo broaster + 1/2 pollo asado + 2 arepas + papas francesas + gaseosa 1.5 L", 50000);
        agregar(alitas, "Mega combinado: costillas de pollo + costillitas de cerdo + papas francesas + gaseosa", 20000);
        agregar(alitas, "5 costillitas + papas francesas + gaseosa 250 ml", 25000);
        agregar(alitas, "Combo muslo contramuslo + 2 acompañantes a elección + gaseosa 250 ml", 13000);
        agregar(alitas, "Combo pechuga + ala + 2 acompañantes a elección + gaseosa 250 ml", 15000);
        agregar(alitas, "6 alitas + 4 costillitas + papas francesas + gaseosa 250 ml", 32000);

        agregar(broaster, "Mega Familiar: 12 presas de pollo broaster + 12 arepas fritas + papas al vapor + gaseosa 1.5 L", 75000);
        agregar(broaster, "Mega Picada: 10 muslitos + 10 alitas + 8 costillitas + 8 chuletas de pollo + papas fritas + arepas + gaseosa 1.5 L", 85000);
        agregar(broaster, "Pollo broaster + 2 acompañantes a elección + salsas", 45000);
        agregar(broaster, "1/2 pollo broaster + 2 acompañantes a elección + salsas", 22500);
        agregar(broaster, "1/4 pollo broaster (muslo + contramuslo) + 2 acompañantes + salsas", 10000);
        agregar(broaster, "1/4 pollo broaster (pechuga + ala) + 2 acompañantes + salsas", 12500);

        agregar(asado, "Pollo asado + 2 acompañantes a elección + salsas", 38000);
        agregar(asado, "1/2 pollo asado + 2 acompañantes a elección + salsas", 19000);
        agregar(asado, "1/4 pollo asado (muslo + contramuslo) + 2 acompañantes + salsas", 10000);
        agregar(asado, "1/4 pollo asado (pechuga + ala) + 2 acompañantes + salsas", 11000);

        agregar(presas, "Ala broaster + papas al vapor o bollo", 5000);
        agregar(presas, "Ala broaster + papas francesas", 7000);
        agregar(presas, "Muslo broaster + papas al vapor o bollo", 5000);
        agregar(presas, "Muslo broaster + papas francesas", 7000);
        agregar(presas, "Contramuslo broaster + papas al vapor o bollo", 5000);
        agregar(presas, "Contramuslo broaster + papas francesas", 7000);
        agregar(presas, "Pechuga broaster + papas al vapor o bollo", 7500);
        agregar(presas, "Pechuga broaster + papas francesas", 9000);

        agregar(adicionales, "Alitas (unidad)", 2500);
        agregar(adicionales, "Papas francesas", 6000);
        agregar(adicionales, "Costillitas", 4000);
        agregar(adicionales, "Arepitas x5", 2500);
        agregar(adicionales, "Papas al vapor", 3500);
        agregar(adicionales, "Salsa de ajo", 2500);

        agregar(bebidas, "Gaseosa 250 ml Postobón", 3000);
        agregar(bebidas, "Gaseosa 250 ml Coca-Cola", 3000);
        agregar(bebidas, "PET Postobón 400 ml", 4000);
        agregar(bebidas, "PET Coca-Cola 400 ml", 4000);
        agregar(bebidas, "Econolitro", 5000);
        agregar(bebidas, "Gaseosa 1.5 L Postobón", 7500);
        agregar(bebidas, "Gaseosa 1.5 L Coca-Cola", 8500);
        agregar(bebidas, "Mega Familiar Coca-Cola", 8500);
        agregar(bebidas, "Jugo Hit 500 ml", 4000);
        agregar(bebidas, "Jugo Hit 1 L", 6500);
        agregar(bebidas, "Cerveza Coronita", 4500);
        agregar(bebidas, "Cerveza Heineken", 4500);
        agregar(bebidas, "Speed Max", 2000);
        agregar(bebidas, "Agua botella pequeña", 1500);
        agregar(bebidas, "Agua botella grande", 3000);
    }
    private void agregar(String categoria, String nombre, double precio) { menu.add(new Producto(nombre, precio, categoria)); }
    public Pedido crearPedido(Mesa mesa) { return new Pedido(mesa); }
    public void agregarItem(Pedido pedido, Producto producto, int cantidad, String observacion) {
        pedido.agregarItem(new ItemPedido(producto, cantidad, observacion));
    }
    public void enviarPedido(Pedido pedido) {
        // Primero se registra la comanda en la colección y después se notifica.
        // Así, los observadores pueden consultarla al refrescar sus tablas.
        pedido.cambiarEstado(EstadoPedido.ENVIADO);
        pedidos.add(pedido);
        bitacora.registrar(pedido);
        tablero.notificarObservadores(pedido);
    }
    public void solicitarCancelacion(Pedido pedido) {
        cambiarEstado(pedido, EstadoPedido.SOLICITUD_CANCELACION);
    }
    public void confirmarCancelacion(Pedido pedido) {
        if (pedido.getEstado() != EstadoPedido.SOLICITUD_CANCELACION)
            throw new IllegalStateException("El pedido no tiene una solicitud de cancelación pendiente.");
        cambiarEstado(pedido, EstadoPedido.CANCELADO);
    }
    public void rechazarCancelacion(Pedido pedido) {
        if (pedido.getEstado() != EstadoPedido.SOLICITUD_CANCELACION)
            throw new IllegalStateException("El pedido no tiene una solicitud de cancelación pendiente.");
        pedido.cambiarEstado(EstadoPedido.ENVIADO);
        bitacora.registrar(pedido); tablero.notificarObservadores(pedido);
    }
    public void marcarListo(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.SOLICITUD_CANCELACION)
            throw new IllegalStateException("Primero debes resolver la solicitud de cancelación.");
        cambiarEstado(pedido, EstadoPedido.LISTO);
    }
    public void marcarEntregado(Pedido pedido) { cambiarEstado(pedido, EstadoPedido.ENTREGADO); }
    /** Registra un abono dirigido a una comanda entregada. */
    public Pago registrarPago(Pedido pedido, long monto, MetodoPago metodo, String responsable) {
        validarPedidoPagable(pedido);
        long saldo = getSaldoPedido(pedido);
        validarMonto(monto, saldo);
        Pago pago = new Pago(siguientePagoId++, pedido.getMesa(), pedido.getId(), monto, metodo,
                responsable, LocalDateTime.now());
        pagos.add(pago);
        aplicarAbono(pedido, monto);
        bitacora.registrarPago(pago);
        tablero.notificarObservadores(pedido);
        return pago;
    }

    /** Registra un abono general y lo distribuye entre las comandas entregadas de la mesa,
     * empezando por la más antigua que aún tenga saldo pendiente. */
    public Pago registrarAbonoMesa(Mesa mesa, long monto, MetodoPago metodo, String responsable) {
        if (mesa == null) throw new IllegalArgumentException("Selecciona una mesa.");
        long saldo = getSaldoMesa(mesa);
        validarMonto(monto, saldo);
        Pago pago = new Pago(siguientePagoId++, mesa, null, monto, metodo, responsable, LocalDateTime.now());
        pagos.add(pago);
        long restante = monto;
        for (Pedido pedido : pedidos) {
            if (pedido.getMesa().getNumero() != mesa.getNumero() || !esPedidoPagable(pedido)) continue;
            long pendiente = getSaldoPedido(pedido);
            if (pendiente <= 0) continue;
            long aplicado = Math.min(restante, pendiente);
            aplicarAbono(pedido, aplicado);
            restante -= aplicado;
            tablero.notificarObservadores(pedido);
            if (restante == 0) break;
        }
        bitacora.registrarPago(pago);
        return pago;
    }

    private void aplicarAbono(Pedido pedido, long monto) {
        long acumulado = pagadoPorPedido.getOrDefault(pedido.getId(), 0L) + monto;
        pagadoPorPedido.put(pedido.getId(), acumulado);
        if (acumulado == Math.round(pedido.getTotal()) && pedido.getEstado() == EstadoPedido.ENTREGADO) {
            pedido.cambiarEstado(EstadoPedido.PAGADO);
            bitacora.registrar(pedido);
        }
    }
    private void validarPedidoPagable(Pedido pedido) {
        if (pedido == null || !pedidos.contains(pedido)) throw new IllegalArgumentException("La comanda no existe en el sistema.");
        if (!esPedidoPagable(pedido)) throw new IllegalStateException("Solo se pueden recibir pagos de comandas entregadas.");
    }
    private boolean esPedidoPagable(Pedido pedido) {
        return pedido.getEstado() == EstadoPedido.ENTREGADO || pedido.getEstado() == EstadoPedido.PAGADO;
    }
    private void validarMonto(long monto, long saldo) {
        if (monto <= 0) throw new IllegalArgumentException("El valor del pago debe ser mayor que cero.");
        if (saldo <= 0) throw new IllegalStateException("Esta cuenta ya está pagada.");
        if (monto > saldo) throw new IllegalArgumentException("El pago supera el saldo pendiente de $" + String.format("%,d", saldo) + ".");
    }
    public long getPagadoPedido(Pedido pedido) { return pagadoPorPedido.getOrDefault(pedido.getId(), 0L); }
    public long getSaldoPedido(Pedido pedido) { return Math.max(0L, Math.round(pedido.getTotal()) - getPagadoPedido(pedido)); }
    public long getTotalMesa(Mesa mesa) { return pedidos.stream().filter(p -> p.getMesa().getNumero() == mesa.getNumero() && esPedidoPagable(p)).mapToLong(p -> Math.round(p.getTotal())).sum(); }
    public long getPagadoMesa(Mesa mesa) { return pedidos.stream().filter(p -> p.getMesa().getNumero() == mesa.getNumero()).mapToLong(this::getPagadoPedido).sum(); }
    public long getSaldoMesa(Mesa mesa) { return Math.max(0L, getTotalMesa(mesa) - getPagadoMesa(mesa)); }
    public List<Pago> getPagos() { return Collections.unmodifiableList(pagos); }
    public List<Pago> getPagosMesa(Mesa mesa) { return pagos.stream().filter(p -> p.getMesa().getNumero() == mesa.getNumero()).toList(); }
    /** Compatibilidad temporal con la versión anterior: solo liquida el saldo completo. */
    @Deprecated public void registrarPago(Pedido pedido) {
        registrarPago(pedido, getSaldoPedido(pedido), MetodoPago.EFECTIVO, "No especificado");
    }
    private void cambiarEstado(Pedido pedido, EstadoPedido estado) {
        pedido.cambiarEstado(estado); bitacora.registrar(pedido); tablero.notificarObservadores(pedido);
    }
    public List<Mesa> getMesas() { return Collections.unmodifiableList(mesas); }
    public List<Producto> getMenu() { return Collections.unmodifiableList(menu); }
    public List<Pedido> getPedidos() { return Collections.unmodifiableList(pedidos); }
}
