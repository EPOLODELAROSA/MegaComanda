package com.megacomanda.vistas;

import com.megacomanda.modelo.*;
import com.megacomanda.servicios.GestorPedidos;
import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class VistaCaja extends PanelObservador {
    public VistaCaja(GestorPedidos gestor) {
        super(gestor, new String[]{"Comanda", "Mesa", "Estado", "Total", "Abonado", "Saldo"});
        JPanel top = new JPanel(new BorderLayout(8, 8)); top.setOpaque(false);
        top.add(Estilo.encabezado("Caja y administración", "Consulta las cuentas entregadas y registra pagos parciales o totales."), BorderLayout.WEST);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0)); botones.setOpaque(false);
        JButton pagarComanda = Estilo.boton("$  Abonar comanda", true);
        pagarComanda.addActionListener(e -> abonarComanda());
        JButton pagarMesa = Estilo.boton("$  Abono a mesa", false);
        pagarMesa.addActionListener(e -> abonarMesa());
        JButton historial = Estilo.boton("Ver pagos", false);
        historial.addActionListener(e -> verHistorial());
        botones.add(pagarComanda); botones.add(pagarMesa); botones.add(historial);
        top.add(botones, BorderLayout.SOUTH); add(top, BorderLayout.NORTH); refrescar();
    }

    private void aviso(String mensaje) { JOptionPane.showMessageDialog(this, mensaje, "MegaComanda", JOptionPane.INFORMATION_MESSAGE); }
    private String moneda(long valor) { return String.format("$%,d", valor); }

    @Override protected Predicate<Pedido> filtro() {
        return p -> p.getEstado() == EstadoPedido.ENTREGADO || p.getEstado() == EstadoPedido.PAGADO;
    }

    @Override protected void refrescar() {
        modelo.setRowCount(0);
        for (Pedido p : gestor.getPedidos()) if (filtro().test(p)) {
            modelo.addRow(new Object[]{p.getId(), "Mesa " + p.getMesa().getNumero(),
                    p.getEstado() == EstadoPedido.PAGADO ? "Pagado" : "Pendiente / parcial",
                    moneda(Math.round(p.getTotal())), moneda(gestor.getPagadoPedido(p)), moneda(gestor.getSaldoPedido(p))});
        }
        mostrarDetalleSeleccionado();
    }

    private void abonarComanda() {
        Pedido pedido = pedidoSeleccionado();
        if (pedido == null) { aviso("Selecciona una comanda de la tabla."); return; }
        long saldo = gestor.getSaldoPedido(pedido);
        if (saldo <= 0) { aviso("Esta comanda ya está pagada."); return; }
        JTextField monto = new JTextField(String.valueOf(saldo));
        JComboBox<MetodoPago> metodo = new JComboBox<>(MetodoPago.values());
        JTextField responsable = new JTextField();
        JPanel form = formulario("Comanda #" + pedido.getId() + " · Mesa " + pedido.getMesa().getNumero(),
                moneda(saldo), monto, metodo, responsable);
        if (JOptionPane.showConfirmDialog(this, form, "Registrar abono a comanda", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                gestor.registrarPago(pedido, leerMonto(monto), (MetodoPago) metodo.getSelectedItem(), responsable.getText());
                refrescar(); aviso("Pago registrado correctamente.");
            } catch (RuntimeException ex) { aviso(ex.getMessage()); }
        }
    }

    private void abonarMesa() {
        JComboBox<Mesa> mesa = new JComboBox<>(gestor.getMesas().toArray(new Mesa[0]));
        JTextField monto = new JTextField(); JComboBox<MetodoPago> metodo = new JComboBox<>(MetodoPago.values());
        JTextField responsable = new JTextField();
        mesa.addActionListener(e -> monto.setText(String.valueOf(gestor.getSaldoMesa((Mesa) mesa.getSelectedItem()))));
        if (mesa.getItemCount() > 0) monto.setText(String.valueOf(gestor.getSaldoMesa((Mesa) mesa.getSelectedItem())));
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Mesa:")); form.add(mesa);
        form.add(new JLabel("Saldo pendiente:")); JLabel saldo = new JLabel();
        Runnable actualizarSaldo = () -> saldo.setText(moneda(gestor.getSaldoMesa((Mesa) mesa.getSelectedItem())));
        actualizarSaldo.run(); mesa.addActionListener(e -> actualizarSaldo.run()); form.add(saldo); form.add(new JLabel("Valor recibido:")); form.add(monto);
        form.add(new JLabel("Método:")); form.add(metodo); form.add(new JLabel("Recibido por:")); form.add(responsable);
        if (JOptionPane.showConfirmDialog(this, form, "Abono general a cuenta de mesa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                Mesa seleccionada = (Mesa) mesa.getSelectedItem();
                gestor.registrarAbonoMesa(seleccionada, leerMonto(monto), (MetodoPago) metodo.getSelectedItem(), responsable.getText());
                refrescar(); aviso("Abono de mesa registrado y distribuido entre las comandas pendientes.");
            } catch (RuntimeException ex) { aviso(ex.getMessage()); }
        }
    }

    private JPanel formulario(String referencia, String saldo, JTextField monto, JComboBox<MetodoPago> metodo, JTextField responsable) {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Cuenta:")); form.add(new JLabel(referencia));
        form.add(new JLabel("Saldo pendiente:")); form.add(new JLabel(saldo));
        form.add(new JLabel("Valor recibido:")); form.add(monto);
        form.add(new JLabel("Método:")); form.add(metodo);
        form.add(new JLabel("Recibido por:")); form.add(responsable);
        return form;
    }

    private long leerMonto(JTextField campo) {
        try { return Long.parseLong(campo.getText().trim().replace(".", "").replace(",", "")); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Escribe un valor numérico válido, sin puntos ni comas."); }
    }

    private void verHistorial() {
        Pedido seleccionado = pedidoSeleccionado();
        Mesa mesa = seleccionado == null ? null : seleccionado.getMesa();
        if (mesa == null) { aviso("Selecciona una comanda para consultar el historial de su mesa."); return; }
        StringBuilder texto = new StringBuilder("Historial de pagos · Mesa ").append(mesa.getNumero()).append("\n\n");
        gestor.getPagosMesa(mesa).forEach(p -> texto.append("#").append(p.getId()).append("  ")
                .append(p.getDestino()).append("  ·  ").append(moneda(p.getMonto())).append("  ·  ")
                .append(p.getMetodo()).append("  ·  ").append(p.getResponsable()).append("  ·  ")
                .append(p.getFecha().toString().replace('T', ' ')).append("\n"));
        if (gestor.getPagosMesa(mesa).isEmpty()) texto.append("No hay pagos registrados.");
        JTextArea area = new JTextArea(texto.toString(), 12, 55); area.setEditable(false); area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Historial de pagos", JOptionPane.INFORMATION_MESSAGE);
    }
}
