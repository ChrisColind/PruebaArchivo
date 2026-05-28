/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rogelio
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
    public class ArchivoGUI extends JFrame{
 
    public Archivo analizador;
    public JTextField campoRuta;
    public JTextField campoBusqueda;
    public JTextArea areaResultados;
    public JButton btnExaminar;
    public JButton btnContar;
    public JButton btnBuscar;
    public JButton btnLimpiar;
 
    public ArchivoGUI(){
        analizador = new Archivo();
        configurarVentana();
        construirUI();
    }
 
    private void configurarVentana(){
        setTitle("Analizador de Sistema de Archivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
 
    private void construirUI(){
        JLabel lblRuta = new JLabel("Directorio raiz:");
        lblRuta.setBounds(15, 15, 110, 25);
 
        campoRuta = new JTextField();
        campoRuta.setBounds(130, 15, 430, 25);
 
        btnExaminar = new JButton("Elegir raiz");
        btnExaminar.setBounds(570, 15, 110, 25);
        btnExaminar.addActionListener(e -> seleccionarCarpeta(e));
 
        JLabel lblBusqueda = new JLabel("Texto busqueda:");
        lblBusqueda.setBounds(15, 55, 110, 25);
 
        campoBusqueda = new JTextField();
        campoBusqueda.setBounds(130, 55, 550, 25);
 
        btnContar = new JButton("Contar por extension");
        btnContar.setBounds(15, 95, 180, 28);
        btnContar.addActionListener(e -> ejecutarConteo(e));
 
        btnBuscar = new JButton("Buscar por nombre");
        btnBuscar.setBounds(205, 95, 170, 28);
        btnBuscar.addActionListener(e -> ejecutarBusqueda(e));
 
        JLabel lblResultados = new JLabel("Resultados:");
        lblResultados.setBounds(15, 135, 100, 20);
 
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);
 
        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBounds(15, 158, 660, 330);
 
        add(lblRuta);
        add(campoRuta);
        add(btnExaminar);
        add(lblBusqueda);
        add(campoBusqueda);
        add(btnContar);
        add(btnBuscar);
        add(lblResultados);
        add(scroll);
    }
 
    private void seleccionarCarpeta(ActionEvent e){
        JFileChooser selector = new JFileChooser(campoRuta.getText().trim());
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = selector.showOpenDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
            campoRuta.setText(selector.getSelectedFile().getAbsolutePath());
        }
    }
 
    private void ejecutarConteo(ActionEvent e){
        String ruta = campoRuta.getText().trim();
        if(!validarRuta(ruta)){
            return;
        }
        File dir = new File(ruta);
        analizador.contarExten(dir);
 
        StringBuilder sb = new StringBuilder();
        sb.append("===CONTEO POR EXTENSION===\n");
        sb.append("Directorio: ").append(ruta).append("\n\n");
        sb.append("TXT:    ").append(analizador.getTxt()).append(" archivos\n");
        sb.append("JAVA:   ").append(analizador.getJava()).append(" archivos\n");
        sb.append("PDF:    ").append(analizador.getPdf()).append(" archivos\n");
        sb.append("OTROS:  ").append(analizador.getOtros()).append(" archivos\n");
 
        int total = analizador.getTxt() + analizador.getJava()
                + analizador.getPdf() + analizador.getOtros();
        sb.append("\nTOTAL:  ").append(total).append(" archivos");
 
        areaResultados.setText(sb.toString());
    }
 
    private void ejecutarBusqueda(ActionEvent e){
        String ruta = campoRuta.getText().trim();
        if(!validarRuta(ruta)){
            return;
        }
        String busqueda = campoBusqueda.getText().trim();
        if(busqueda.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Ingrese un texto de busqueda.",
                    "Campo vacio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        File dir = new File(ruta);
        List<String> resultados = analizador.buscar(dir, busqueda);
 
        StringBuilder sb = new StringBuilder();
        sb.append("===BUSQUEDA POR NOMBRE===\n");
        sb.append("Directorio: ").append(ruta).append("\n");
        sb.append("Buscando: \"").append(busqueda).append("\"\n\n");
 
        if(resultados.isEmpty()){
            sb.append("No se encontraron archivos que coincidan con los puntos.");
        } else {
            sb.append("Encontrados (").append(resultados.size()).append("):\n\n");
            for(String path : resultados){
                sb.append(path).append("\n");
            }
        }
        areaResultados.setText(sb.toString());
    }
 
    private boolean validarRuta(String ruta){
        if(!analizador.esValido(ruta)){
            JOptionPane.showMessageDialog(this,
                    "La ruta no es valida o no es un directorio.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
 
}
 