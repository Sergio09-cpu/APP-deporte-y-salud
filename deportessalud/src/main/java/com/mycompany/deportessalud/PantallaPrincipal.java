package com.mycompany.deportessalud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.deportessalud.Imagen;
import com.mycompany.deportessalud.Excel;
import com.mycompany.deportessalud.GraficosExcel;
import com.mycompany.deportessalud.GraficosJFreeChart;
import com.mycompany.deportessalud.InformePDF;

public class PantallaPrincipal extends JFrame {

    private JComboBox<String> comboEntrenamientos; // Combobox dinámico

    public PantallaPrincipal() {
        initComponents();
        configurarCombo();
        configurarImagenes();
        configurarEventos();
        configurarRedimension();

        // Pantalla completa al abrir
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // =============================================================
    // Configuración de imágenes
    // =============================================================
    private void configurarImagenes() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Imagen local (Deporte)
        labeldeporte.setText(" ");
        labeldeporte.setPreferredSize(new Dimension(screenSize.width / 2 - 20, screenSize.height - 150));
        labeldeporte.setIcon(Imagen.loadLocalImage(
                "C:\\Users\\Sandra\\Documents\\NetBeansProjects\\deportessalud\\src\\main\\java\\com\\mycompany\\deportessalud\\deporte_local.jpg",
                labeldeporte.getPreferredSize().width, labeldeporte.getPreferredSize().height));

        // Imagen online (Salud)
        labelsalud.setText(" ");
        labelsalud.setPreferredSize(new Dimension(screenSize.width / 2 - 20, screenSize.height - 150));
        labelsalud.setIcon(Imagen.loadOnlineImage(
                "https://www.noticiasensalud.com/wp-content/uploads/2021/04/Medios-actuales-para-cuidar-de-la-salud-bienestar-fisico-y-mental-1000x600.jpg",
                labelsalud.getPreferredSize().width, labelsalud.getPreferredSize().height));
    }

    // =============================================================
    // Configuración de eventos
    // =============================================================
    private void configurarEventos() {
        // Abrir VentanaDeporte según el tipo seleccionado
        labeldeporte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String seleccionado = (String) comboEntrenamientos.getSelectedItem();
                if (seleccionado.equalsIgnoreCase("Fuerza") || seleccionado.equalsIgnoreCase("Resistencia")) {
                    new VentanaDeporte(seleccionado);
                }
            }
        });

        // Abrir VentanaSalud al hacer clic
        labelsalud.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new VentanaSalud(); // Se abrirá maximizando la ventana dentro de su constructor
            }
        });
    }

    // =============================================================
    // Redimensionar imágenes
    // =============================================================
    private void configurarRedimension() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int anchoMitad = getWidth() / 2 - 40;
                int alto = getHeight() - 150;

                // Reescalar imagen local (Deporte)
                labeldeporte.setIcon(Imagen.loadLocalImage(
                        "C:\\Users\\Sandra\\Documents\\NetBeansProjects\\deportessalud\\src\\main\\java\\com\\mycompany\\deportessalud\\deporte_local.jpg",
                        anchoMitad, alto));

                // Reescalar imagen online (Salud)
                labelsalud.setIcon(Imagen.loadOnlineImage(
                        "https://www.noticiasensalud.com/wp-content/uploads/2021/04/Medios-actuales-para-cuidar-de-la-salud-bienestar-fisico-y-mental-1000x600.jpg",
                        anchoMitad, alto));
            }
        });
    }

    // =============================================================
    // ComboBox de selección de tipo de entrenamiento
    // =============================================================
    private void configurarCombo() {
        comboEntrenamientos = new JComboBox<>();
        comboEntrenamientos.addItem("Fuerza");
        comboEntrenamientos.addItem("Resistencia");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        getContentPane().add(comboEntrenamientos, c);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblDeporte = new javax.swing.JLabel();
        labeldeporte = new javax.swing.JLabel();
        labelsalud = new javax.swing.JLabel();

        lblDeporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDeporte.setToolTipText("Pulsa la pantalla para administrar tus entrenamientos");
        lblDeporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        labeldeporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labeldeporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(labeldeporte, gridBagConstraints);

        labelsalud.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelsalud.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(labelsalud, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   

  public static void main(String[] args) {
        Excel.inicializarArchivos();

        // Genera los gráficos dentro de los Excel
        GraficosExcel.crearGraficoEntrenamiento();
        GraficosExcel.crearGraficoSalud();

        // Genera los gráficos en imagen JPG (JFreeChart)
        GraficosJFreeChart.generarGraficoBarrasEntrenamiento();
        GraficosJFreeChart.generarGraficoCircularSalud();

        // Genera el PDF con tablas e imágenes
        InformePDF.generarInformeCompleto();

        java.awt.EventQueue.invokeLater(() -> new PantallaPrincipal().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labeldeporte;
    private javax.swing.JLabel labelsalud;
    private javax.swing.JLabel lblDeporte;
    // End of variables declaration//GEN-END:variables
}
