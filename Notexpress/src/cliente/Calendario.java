package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Calendario extends JFrame {
    
  static JLabel lblMes, lblYear;
  static JButton btnAnterior, btnSiguiente;
  static JTable tblCalendario;
  static JComboBox cmbAnio;
  static Container panelContenedor;
  static DefaultTableModel mtblCalendario;
  static JScrollPane stblCalendario; 
  static JPanel pnlCalendario;
  
  static int hoyAnio, hoyMes, hoyDia, actualAnio, actualMes;
  
  static int altura, anchura;

    public Calendario() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        
      Dimension dimensionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
      altura = (int)dimensionPantalla.getHeight();
      anchura = (int)dimensionPantalla.getWidth();
      
        this.getContentPane().setLayout( null );
        
       panelContenedor = this.getContentPane(); 

      //Crear controles del calendario
       
      lblMes = new JLabel ("Enero");
      btnAnterior = new JButton ("<");
      btnSiguiente = new JButton (">");
      mtblCalendario = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
      tblCalendario = new JTable(mtblCalendario);
      stblCalendario = new JScrollPane(tblCalendario);
      pnlCalendario = new JPanel(null);
      cmbAnio = new JComboBox();

      
      // Registrar los listeners
      btnAnterior.addActionListener(new ActionListener() { public void actionPerformed( ActionEvent ae ) {  btnAnterior_ActionPerformed( ae ); }} );
      btnSiguiente.addActionListener(new ActionListener() { public void actionPerformed( ActionEvent ae ) {  btnSiguiente_ActionPerformed( ae ); }} ); 
      cmbAnio.addActionListener(new ActionListener() { public void actionPerformed( ActionEvent ae ) {  cmbAnio_ActionPerformed( ae ); }} ); 
      
      //Agregar controles al panel
      panelContenedor.add(pnlCalendario);
      pnlCalendario.add(lblMes);
      pnlCalendario.add(cmbAnio);
      pnlCalendario.add(btnAnterior);
      pnlCalendario.add(btnSiguiente);
      pnlCalendario.add(stblCalendario);
      
      // Establecer fuentes
      Font fontLabel = new Font("Times New Roman", Font.BOLD, 30);
      Font fontButton = new Font("Times New Roman", Font.BOLD, 28);
      tblCalendario.setFont(fontLabel);
      cmbAnio.setFont(fontLabel);
      tblCalendario.getTableHeader().setFont(fontLabel);
      lblMes.setFont(fontLabel);
      btnSiguiente.setFont(fontButton);
      btnAnterior.setFont(fontButton);
     
      //Establecer posiciones
      pnlCalendario.setBounds(0,0, anchura-5, altura-50);
      btnAnterior.setBounds(10, 15, 70, 55);
      btnSiguiente.setBounds(anchura-btnSiguiente.getPreferredSize().width-30, 15, 70, 55);   
      lblMes.setBounds((anchura/2)-lblMes.getPreferredSize().width/2, 10, 250, 55);
      cmbAnio.setBounds(anchura-btnSiguiente.getPreferredSize().width-150-cmbAnio.getPreferredSize().width, 15, 120, 55);
      stblCalendario.setBounds(10, 100, anchura-40, altura-50);
      

      //Obtener fecha y mes actual
      GregorianCalendar cal = new GregorianCalendar(); 
      hoyDia = cal.get(GregorianCalendar.DAY_OF_MONTH); 
      hoyMes = cal.get(GregorianCalendar.MONTH);
      hoyAnio = cal.get(GregorianCalendar.YEAR);
      actualMes = hoyMes; 
      actualAnio = hoyAnio;
      

      //Agregar cabeceras
      String[] cabeceras  = {"Domingo", "Lunes", "Martes", "Miercoles", 
                             "Jueves", "Viernes", "Sabado"}; 
      
      for (int i=0; i<7; i++){
        mtblCalendario.addColumn(cabeceras[i]);
      }
      
      tblCalendario.getParent().setBackground(tblCalendario.getBackground()); 

      //Desactivar el cambio de tamaño y de posición
      tblCalendario.getTableHeader().setResizingAllowed(false);
      tblCalendario.getTableHeader().setReorderingAllowed(false);

      tblCalendario.setColumnSelectionAllowed(true);
      tblCalendario.setRowSelectionAllowed(true);
      tblCalendario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      tblCalendario.setRowHeight(altura/10);
      mtblCalendario.setColumnCount(7);
      mtblCalendario.setRowCount(6);
      
      // Agregar los años al combo 
      for (int i=hoyAnio-10; i<=hoyAnio+10; i++){
        cmbAnio.addItem(String.valueOf(i));
      }
      

      actualizarCalendario (hoyMes, hoyAnio); 


       
      this.setSize(anchura, altura);
      this.setLocation(0, 0);
      this.setResizable(false);
      this.setVisible(true);

    }
    /**
     * Actualiza la información que se muestra en el JTable
     * @param mes
     * @param anio
     */

  public static void actualizarCalendario(int mes, int anio){
        
      String[] meses =  {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
                        "Diciembre"};
      int nod, som; 

      btnAnterior.setEnabled(true);
      btnSiguiente.setEnabled(true);
      
      if (mes == 0 && anio <= hoyAnio-10){btnAnterior.setEnabled(false);} 
      if (mes == 11 && anio >= hoyAnio+100){btnSiguiente.setEnabled(false);}
      lblMes.setText(meses[mes]);
      lblMes.setBounds((anchura/2)-lblMes.getPreferredSize().width/2, 10, 250, 55);
      cmbAnio.setSelectedItem(String.valueOf(anio)); 
      
      for (int i=0; i<6; i++){
        for (int j=0; j<7; j++){
          mtblCalendario.setValueAt(null, i, j);
        }
      }
      
      GregorianCalendar cal = new GregorianCalendar(anio, mes, 1);
      nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
      som = cal.get(GregorianCalendar.DAY_OF_WEEK);
      
    
      for (int i=1; i<=nod; i++){
        int row = new Integer((i+som-2)/7);
        int column  =  (i+som-2)%7;
        mtblCalendario.setValueAt(i, row, column);
      }

      tblCalendario.setDefaultRenderer(tblCalendario.getColumnClass(0), new tblCalendarioRenderer());
    }    

   
  void btnAnterior_ActionPerformed (ActionEvent e){
      if (actualMes == 0){ 
        actualMes = 11;
        actualAnio -= 1;
      }  else { 
        actualMes -= 1;
      }
      actualizarCalendario(actualMes, actualAnio);
    }

  
  void btnSiguiente_ActionPerformed (ActionEvent e){
      if (actualMes == 11){
        actualMes = 0;
        actualAnio += 1;
      } else { 
        actualMes += 1;
      }
      actualizarCalendario(actualMes, actualAnio);
    }

  void cmbAnio_ActionPerformed (ActionEvent e){
      if (cmbAnio.getSelectedItem() != null) {
        String b = cmbAnio.getSelectedItem().toString();
        actualAnio = Integer.parseInt(b);
        actualizarCalendario(actualMes, actualAnio);
      }
    }
  
  
  static class tblCalendarioRenderer extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
      super.getTableCellRendererComponent(table, value, selected, focused, row, column);
      if (column == 0 || column == 6){ 
        setBackground(new Color(220, 220, 220));
      } else { 
        setBackground(new Color(255, 255, 255));
      }
      if (value != null){
        if (Integer.parseInt(value.toString()) == hoyDia && actualMes == hoyMes && actualAnio == hoyAnio){ 
          setBackground(new Color(220, 220, 250));
        }
      }
      if (selected == true){
         setBackground(new Color(173,213,241));
      }
      ((JLabel)this).setVerticalAlignment( SwingConstants.BOTTOM );
      ((JLabel)this).setHorizontalAlignment( SwingConstants.LEFT );
      setBorder(null);
      setForeground(Color.black);
      return this;  
    }
  }  
}

