package com.megacomanda.vistas;
import com.megacomanda.modelo.*;import com.megacomanda.servicios.GestorPedidos;import javax.swing.*;import java.awt.*;import java.util.function.Predicate;
public class VistaMesero extends PanelObservador {
 public VistaMesero(GestorPedidos gestor){super(gestor,new String[]{"Comanda","Mesa","Estado","Total"});
  JPanel top=new JPanel(new BorderLayout(8,8));top.setOpaque(false);top.add(Estilo.encabezado("Panel del mesero","Consulta todas las comandas, entrega los pedidos listos y solicita cancelaciones."),BorderLayout.WEST);
  JPanel botones=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));botones.setOpaque(false);
  JButton entregar=Estilo.boton("✓  Marcar entregada",true);entregar.addActionListener(e->{Pedido p=pedidoSeleccionado();if(p==null){aviso("Selecciona una comanda.");return;}try{gestor.marcarEntregado(p);refrescar();}catch(RuntimeException ex){aviso(ex.getMessage());}});
  JButton cancelar=Estilo.boton("Solicitar cancelación",false);cancelar.addActionListener(e->{Pedido p=pedidoSeleccionado();if(p==null){aviso("Selecciona una comanda.");return;}int r=JOptionPane.showConfirmDialog(this,"¿Solicitar cancelar la comanda #"+p.getId()+"? Cocina deberá confirmar.","Solicitud de cancelación",JOptionPane.YES_NO_OPTION);if(r==JOptionPane.YES_OPTION)try{gestor.solicitarCancelacion(p);refrescar();}catch(RuntimeException ex){aviso(ex.getMessage());}});
  botones.add(entregar);botones.add(cancelar);top.add(botones,BorderLayout.SOUTH);add(top,BorderLayout.NORTH);refrescar();
 }
 private void aviso(String s){JOptionPane.showMessageDialog(this,s,"MegaComanda",JOptionPane.INFORMATION_MESSAGE);}
 @Override protected Predicate<Pedido> filtro(){return p->p.getEstado()!=EstadoPedido.CANCELADO;}
}
