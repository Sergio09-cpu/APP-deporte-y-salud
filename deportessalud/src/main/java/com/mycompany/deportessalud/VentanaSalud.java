package com.mycompany.deportessalud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.mycompany.deportessalud.Excel;

public class VentanaSalud extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaSalud.class.getName());
    private final ArrayList<String> registrosSalud = new ArrayList<>();

    public VentanaSalud() {
        initComponents(); 
        configurarEstilo();
        configurarBotonGuardar();

        // Ajuste de pantalla completa
        configurarPantallaCompleta();
    }

    /**
     * Fuerza que la ventana se abra en pantalla completa centrada
     */
    private void configurarPantallaCompleta() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra si no se maximiza

        // Garantiza pantalla completa incluso con pack()
        SwingUtilities.invokeLater(() -> {
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza
            setVisible(true);
        });
    }

    // Ajusta tamaños y estilos de los componentes
     
    private void configurarEstilo() {
        // Tamaño de los campos de texto
        Dimension campoGrande = new Dimension(300, 50);
        jTextField1.setPreferredSize(campoGrande);
        jTextField2.setPreferredSize(campoGrande);
        jTextField3.setPreferredSize(campoGrande);

        // Fuentes
        Font fuente = new Font("Segoe UI", Font.PLAIN, 20);
        jTextField1.setFont(fuente);
        jTextField2.setFont(fuente);
        jTextField3.setFont(fuente);
        jLabel1.setFont(fuente);
        jLabel2.setFont(fuente);
        jLabel3.setFont(fuente);
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 22));
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 22));

        // Fondo y espaciado
        getContentPane().setBackground(new Color(240, 245, 250));
        ((GridBagLayout) getContentPane().getLayout()).columnWeights = new double[]{0.5, 0.5};
        ((GridBagLayout) getContentPane().getLayout()).rowWeights = new double[]{0.5, 0.5, 0.5, 0.5, 0.5};

        pack();
    }

    // Configura el botón Guardar
     
    private void configurarBotonGuardar() {
        jButton1.addActionListener(e -> guardarRegistro());
    }

     // Guarda los datos e IMC
 
    private void guardarRegistro() {
        try {
            double peso = Double.parseDouble(jTextField1.getText());
            double alturaCm = Double.parseDouble(jTextField2.getText());
            double alturaM = alturaCm / 100.0;
            double horasSueño = Double.parseDouble(jTextField3.getText());

            double imc = peso / (alturaM * alturaM);
            String imcTexto = String.format("IMC: %.2f", imc);
            jLabel4.setText(imcTexto);

            String estado;
            if (imc < 18.5) {
                estado = "Bajo peso";
            } else if (imc < 25) {
                estado = "Peso normal";
            } else if (imc < 30) {
                estado = "Sobrepeso";
            } else {
                estado = "Obesidad";
            }

            String registro = String.format("Peso: %.1f kg | Altura: %.1f cm | Sueño: %.1f h | %s (%s)",
                    peso, alturaCm, horasSueño, imcTexto, estado);
            registrosSalud.add(registro);
            // Guardar también en Excel
            Excel.escribirDatos("Salud", java.util.Arrays.asList(
            String.format("%.1f", peso),
            String.format("%.1f", alturaCm),
            String.format("%.1f", horasSueño),
            imcTexto,
            estado
            ));
            Excel.darFormato("Salud");

            String todos = String.join("\n", registrosSalud);
            JOptionPane.showMessageDialog(this, todos, "Registros de Salud", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Introduce valores numéricos válidos.",
                    "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("PESO KG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jTextField1, gridBagConstraints);

        jLabel2.setText("ALTURA(CM)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jTextField2, gridBagConstraints);

        jLabel3.setText("HORAS DE SUEÑO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jTextField3, gridBagConstraints);

        jLabel4.setText("IMC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel4, gridBagConstraints);

        jButton1.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jButton1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new VentanaSalud().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
