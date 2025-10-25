package com.mycompany.deportessalud;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import com.mycompany.deportessalud.Excel;

public class VentanaDeporte extends JFrame {

    private final ArrayList<String> ejerciciosGuardados = new ArrayList<>();
    private final String tipoVentana; // Fuerza o Resistencia

   

    public VentanaDeporte(String tipo) {
        this.tipoVentana = tipo;
        initComponents();          
        configurarVentana();
        adaptarCamposSegunTipo();
        configurarBotonGuardar();
        configurarCamposTexto(); 
    }

    private void configurarVentana() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setVisible(true);
    }
    
    private void configurarCamposTexto() {
    Dimension tamañoCampo = new Dimension(200, 40); // ancho  y alto
    Font fuente = new Font("Segoe UI", Font.PLAIN, 18);

    // Aplicar a los campos de texto
    jTextField1.setPreferredSize(tamañoCampo);
    jTextField1.setFont(fuente);

    jTextField2.setPreferredSize(tamañoCampo);
    jTextField2.setFont(fuente);

    // Forzar que se redistribuyan
    revalidate();
    repaint();
}
private void adaptarCamposSegunTipo() {
    // Limpiamos contenido previo
    getContentPane().removeAll();
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 10, 10);
    c.fill = GridBagConstraints.HORIZONTAL;
    Font fuente = new Font("Segoe UI", Font.PLAIN, 18);

    if (tipoVentana.equalsIgnoreCase("Resistencia")) {
        // resistencia
        JLabel lblKm = new JLabel("Distancia (km):");
        JLabel lblTiempo = new JLabel("Tiempo (min):");
        lblKm.setFont(fuente);
        lblTiempo.setFont(fuente);

        jTextField1.setText("");
        jTextField2.setText("");
        jTextField1.setFont(fuente);
        jTextField2.setFont(fuente);

        // Distancia
        c.gridx = 0; c.gridy = 0;
        getContentPane().add(lblKm, c);
        c.gridx = 1;
        getContentPane().add(jTextField1, c);

        // Tiempo
        c.gridx = 0; c.gridy = 1;
        getContentPane().add(lblTiempo, c);
        c.gridx = 1;
        getContentPane().add(jTextField2, c);

        // Botón guardar
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        getContentPane().add(jButton1, c);

    } else {
        // fuerzA
        JLabel lblZona = new JLabel("Zona muscular:");
        JLabel lblEjercicio = new JLabel("Ejercicio:");
        JLabel lblSeries = new JLabel("Series:");
        JLabel lblReps = new JLabel("Repeticiones:");
        JLabel lblTipo = new JLabel("Tipo:");
        lblZona.setFont(fuente);
        lblEjercicio.setFont(fuente);
        lblSeries.setFont(fuente);
        lblReps.setFont(fuente);
        lblTipo.setFont(fuente);

        jTextField1.setText("");
        jTextField1.setFont(fuente);

        // Zona muscular
        c.gridx = 0; c.gridy = 0;
        getContentPane().add(lblZona, c);
        c.gridx = 1;
        getContentPane().add(jComboBox1, c);

        // Ejercicio
        c.gridx = 0; c.gridy = 1;
        getContentPane().add(lblEjercicio, c);
        c.gridx = 1;
        getContentPane().add(jTextField1, c);

        // Series
        c.gridx = 0; c.gridy = 2;
        getContentPane().add(lblSeries, c);
        c.gridx = 1;
        getContentPane().add(jSpinner1, c);

        // Repeticiones
        c.gridx = 0; c.gridy = 3;
        getContentPane().add(lblReps, c);
        c.gridx = 1;
        getContentPane().add(jSpinner2, c);

        // Tipo
        c.gridx = 0; c.gridy = 4;
        getContentPane().add(lblTipo, c);
        c.gridx = 1;
        getContentPane().add(jComboBox2, c);

        // Botón guardar
        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        getContentPane().add(jButton1, c);
    }

    revalidate();
    repaint();
}


private void configurarBotonGuardar() {
    jButton1.addActionListener(e -> {
        String registro;

        if (tipoVentana.equalsIgnoreCase("Fuerza")) {
            String zona = (String) jComboBox1.getSelectedItem();
            String ejercicio = jTextField1.getText();
            int series = (Integer) jSpinner1.getValue();
            int repeticiones = (Integer) jSpinner2.getValue();
            String tipo = (String) jComboBox2.getSelectedItem();

            registro = "Zona: " + zona +
                       ", Ejercicio: " + ejercicio +
                       ", Series: " + series +
                       ", Repeticiones: " + repeticiones +
                       ", Tipo: " + tipo;

        } else { // Resistencia
            try {
                double km = Double.parseDouble(jTextField1.getText());
                double tiempoMin = Double.parseDouble(jTextField2.getText());
                double tiempoHoras = tiempoMin / 60.0;
                double velocidadMedia = km / tiempoHoras;

                registro = String.format(
                    "Resistencia: %.2f km, Tiempo: %.2f min, Velocidad media: %.2f km/h",
                    km, tiempoMin, velocidadMedia
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Introduce valores numéricos válidos para km y tiempo.",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        ejerciciosGuardados.add(registro);
        
        // Guardar también en Excel
        Excel.escribirDatos("Entrenamientos", java.util.Arrays.asList(tipoVentana, registro));
        Excel.darFormato("Entrenamientos");
        String todos = String.join("\n", ejerciciosGuardados);
        JOptionPane.showMessageDialog(
            this,
            todos,
            tipoVentana + " Guardado",
            JOptionPane.INFORMATION_MESSAGE
        );
    });
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pecho", "Espalda", "Brazo", "Pierna(no se hace)" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jComboBox1, gridBagConstraints);

        jTextField1.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jTextField1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jSpinner1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jSpinner2, gridBagConstraints);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Con maquina", "Sin maquina" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jComboBox2, gridBagConstraints);

        jButton1.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(jButton1, gridBagConstraints);

        jTextField2.setText("jTextField2");
        getContentPane().add(jTextField2, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
   
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new VentanaDeporte("Fuerza"));
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
