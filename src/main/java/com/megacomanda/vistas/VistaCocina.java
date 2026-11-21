package com.megacomanda.vistas;
import com.megacomanda.modelo.*;import com.megacomanda.servicios.GestorPedidos;import javax.swing.*;import java.awt.*;import java.util.function.Predicate;
public class VistaCocina extends PanelObservador {
 public VistaCocina(GestorPedidos gestor){super(gestor,new String[]{"Comanda","Mesa","Estado","Total"});
  JPanel top=new JPanel(new BorderLayout(8,8));top.setOpaque(false);top.add(Estilo.encabezado("Panel de cocina","Prepara las comandas en orden y actualiza su estado con un solo clic."),BorderLayout.WEST);
  JPanel botones=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));botones.setOpaque(false);
  JButton listo=Estilo.boton("✓  Marcar como lista",true);listo.addActionListener(e->accion(()->gestor.marcarListo(pedidoSeleccionado())));
  JButton confirmar=Estilo.boton("Confirmar cancelación",false);confirmar.addActionListener(e->accion(()->gestor.confirmarCancelacion(pedidoSeleccionado())));
  JButton rechazar=Estilo.boton("Rechazar cancelación",false);rechazar.addActionListener(e->accion(()->gestor.rechazarCancelacion(pedidoSeleccionado())));
  botones.add(listo);botones.add(confirmar);botones.add(rechazar);top.add(botones,BorderLayout.SOUTH);add(top,BorderLayout.NORTH);refrescar();
 }
 private void accion(Runnable r){Pedido p=pedidoSeleccionado();if(p==null){aviso("Selecciona una comanda.");return;}try{r.run();refrescar();}catch(RuntimeException ex){aviso(ex.getMessage());}}
 private void aviso(String s){JOptionPane.showMessageDialog(this,s,"MegaComanda",JOptionPane.INFORMATION_MESSAGE);}
 @Override protected Predicate<Pedido> filtro(){return p->p.getEstado()==EstadoPedido.ENVIADO||p.getEstado()==EstadoPedido.SOLICITUD_CANCELACION;}
}
