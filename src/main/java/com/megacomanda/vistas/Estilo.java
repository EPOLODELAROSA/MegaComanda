package com.megacomanda.vistas;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class Estilo {
    public static final Color FONDO = new Color(246,247,249);
    public static final Color BLANCO = Color.WHITE;
    public static final Color TINTA = new Color(35,39,47);
    public static final Color SUAVE = new Color(112,119,130);
    public static final Color NARANJA = new Color(235,91,35);
    public static final Color ROJO = new Color(157,38,48);
    public static final Color AMARILLO = new Color(249,190,63);
    public static final Color BORDE = new Color(229,232,237);
    private Estilo() {}
    public static JPanel tarjeta() { JPanel p=new JPanel(new BorderLayout(10,10)); p.setBackground(BLANCO); p.setBorder(new CompoundBorder(new LineBorder(BORDE,1,true),new EmptyBorder(16,16,16,16))); return p; }
    public static JLabel titulo(String s) { JLabel l=new JLabel(s); l.setFont(new Font("SansSerif",Font.BOLD,21)); l.setForeground(TINTA); return l; }
    public static JLabel subtitulo(String s) { JLabel l=new JLabel(s); l.setFont(new Font("SansSerif",Font.PLAIN,12)); l.setForeground(SUAVE); return l; }
    public static JButton boton(String s, boolean principal) { JButton b=new JButton(s); b.setFont(new Font("SansSerif",Font.BOLD,12)); b.setForeground(principal?Color.WHITE:TINTA); b.setBackground(principal?NARANJA:new Color(244,245,247)); b.setFocusPainted(false); b.setBorder(new CompoundBorder(new LineBorder(principal?NARANJA:BORDE,1,true),new EmptyBorder(10,15,10,15))); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return b; }
    public static void tabla(JTable t) { t.setRowHeight(34); t.setFont(new Font("SansSerif",Font.PLAIN,13)); t.setForeground(TINTA); t.setBackground(BLANCO); t.setGridColor(new Color(239,241,244)); t.setShowVerticalLines(false); t.setIntercellSpacing(new Dimension(0,1)); t.setSelectionBackground(new Color(255,232,218)); t.setSelectionForeground(TINTA); t.setFillsViewportHeight(true); JTableHeader h=t.getTableHeader(); h.setFont(new Font("SansSerif",Font.BOLD,12)); h.setForeground(SUAVE); h.setBackground(new Color(248,249,251)); h.setPreferredSize(new Dimension(0,38)); }
    public static JScrollPane scroll(JTable t) { JScrollPane s=new JScrollPane(t); s.setBorder(new LineBorder(BORDE,1,true)); s.getViewport().setBackground(BLANCO); return s; }
    public static JPanel encabezado(String titulo,String detalle) { JPanel p=new JPanel(new BorderLayout(4,4)); p.setOpaque(false); p.add(titulo(titulo),BorderLayout.NORTH); p.add(subtitulo(detalle),BorderLayout.SOUTH); return p; }
    public static JPanel pagina() { JPanel p=new JPanel(new BorderLayout(14,14)); p.setBackground(FONDO); p.setBorder(new EmptyBorder(20,22,20,22)); return p; }
}
