package com.megacomanda.vistas;

import com.megacomanda.modelo.Pedido;
import com.megacomanda.observer.ObservadorPedido;
import com.megacomanda.servicios.GestorPedidos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

abstract class PanelObservador extends JPanel implements ObservadorPedido {
    protected final GestorPedidos gestor;
    protected final DefaultTableModel modelo;
    protected final JTable tabla;
    protected final JLabel detalle = new JLabel("Selecciona una comanda para consultar sus productos.");
    protected final JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
    protected PanelObservador(GestorPedidos gestor, String[] columnas) {
        super(new BorderLayout(12,12)); this.gestor=gestor; setBackground(Estilo.FONDO); setBorder(new EmptyBorder(18,20,18,20));
        modelo=new DefaultTableModel(columnas,0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tabla=new JTable(modelo); tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); Estilo.tabla(tabla);
        JPanel tablaCard=Estilo.tarjeta(); tablaCard.add(Estilo.subtitulo("SEGUIMIENTO DE COMANDAS"),BorderLayout.NORTH); tablaCard.add(Estilo.scroll(tabla),BorderLayout.CENTER);
        JPanel pie=Estilo.tarjeta(); detalle.setFont(new Font("SansSerif",Font.PLAIN,12)); detalle.setForeground(Estilo.SUAVE); pie.add(detalle,BorderLayout.CENTER);
        acciones.setOpaque(false);
        add(tablaCard,BorderLayout.CENTER); add(pie,BorderLayout.SOUTH);
        tabla.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting())mostrarDetalleSeleccionado();});
    }
    protected abstract Predicate<Pedido> filtro();
    protected void refrescar(){ int seleccionado=tabla.getSelectedRow(); modelo.setRowCount(0); List<Pedido> ps=gestor.getPedidos(); for(Pedido p:ps) if(filtro().test(p)) modelo.addRow(new Object[]{p.getId(),"Mesa "+p.getMesa().getNumero(),etiqueta(p.getEstado()),String.format("$%,.0f",p.getTotal())}); }
    private String etiqueta(com.megacomanda.modelo.EstadoPedido e){return switch(e){case ENVIADO->"Enviado";case SOLICITUD_CANCELACION->"Cancelación solicitada";case LISTO->"Listo";case ENTREGADO->"Entregado";case PAGADO->"Pagado";case CANCELADO->"Cancelado";};}
    protected Pedido pedidoSeleccionado(){int f=tabla.getSelectedRow();if(f<0)return null;int id=(int)modelo.getValueAt(tabla.convertRowIndexToModel(f),0);return gestor.getPedidos().stream().filter(p->p.getId()==id).findFirst().orElse(null);}
    protected void mostrarDetalleSeleccionado(){Pedido p=pedidoSeleccionado();if(p==null){detalle.setText("Selecciona una comanda para consultar sus productos.");return;}StringBuilder s=new StringBuilder("COMANDA #"+p.getId()+"  ·  MESA "+p.getMesa().getNumero()+"  ·  ");p.getItems().forEach(i->s.append(i.getCantidad()).append(" × ").append(i.getProducto().getNombre()).append(i.getObservacion().isBlank()?"":" ["+i.getObservacion()+"]").append("     "));detalle.setText(s.toString());}
    @Override public void actualizarPedido(Pedido pedido){SwingUtilities.invokeLater(this::refrescar);}
}
