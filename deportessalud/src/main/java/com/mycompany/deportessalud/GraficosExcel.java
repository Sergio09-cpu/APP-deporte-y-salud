package com.mycompany.deportessalud;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.*;
import java.awt.Color;

public class GraficosExcel {

    private static final String ARCHIVO_ENTRENAMIENTO = "entrenamientos.xlsx";
    private static final String ARCHIVO_SALUD = "salud.xlsx";
    private static final String PDF_REPORTE = "reporte.pdf";
    private static final String IMG_ENTRENAMIENTO = "grafico_entrenamiento.jpg";
    private static final String IMG_SALUD = "grafico_salud.jpg";

    // =================== GRÁFICO ENTRENAMIENTO ===================
    public static void crearGraficoEntrenamiento() {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(50, "Entrenamiento", "Lunes");
            dataset.addValue(70, "Entrenamiento", "Miércoles");
            dataset.addValue(90, "Entrenamiento", "Viernes");

            JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart(
                    "Entrenamientos", "Día", "Progreso", dataset);

            try (FileOutputStream fos = new FileOutputStream(IMG_ENTRENAMIENTO)) {
                ChartUtils.writeChartAsJPEG(fos, chart, 800, 600);
            }
            System.out.println("Imagen del gráfico de entrenamientos creada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =================== GRÁFICO SALUD ===================
    public static void crearGraficoSalud() {
        try {
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Peso Normal", 40);
            dataset.setValue("Sobrepeso", 30);
            dataset.setValue("Obesidad", 20);
            dataset.setValue("Bajo Peso", 10);

            JFreeChart chart = org.jfree.chart.ChartFactory.createPieChart(
                    "Distribución IMC", dataset, true, true, false);

            try (FileOutputStream fos = new FileOutputStream(IMG_SALUD)) {
                ChartUtils.writeChartAsJPEG(fos, chart, 800, 600);
            }
            System.out.println(" Imagen del gráfico de salud creada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===================  PDF CON IMÁGENES Y TABLAS ===================
    public static void generarPDF() {
        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);
            float yPos = 780;

            // ===== TÍTULO =====
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 20);
            content.newLineAtOffset(120, yPos);
            content.showText("Informe de Entrenamientos y Salud");
            content.endText();
            yPos -= 40;

            // ===== IMAGEN ENTRENAMIENTO =====
            try {
                PDImageXObject image = PDImageXObject.createFromFile(IMG_ENTRENAMIENTO, document);
                float imgWidth = 450;
                float imgHeight = 300;
                content.drawImage(image, 80, yPos - imgHeight, imgWidth, imgHeight);
                yPos -= (imgHeight + 40);
            } catch (IOException e) {
                System.out.println(" No se encontró la imagen de entrenamientos.");
            }

            // ===== IMAGEN SALUD =====
            try {
                PDImageXObject image2 = PDImageXObject.createFromFile(IMG_SALUD, document);
                float imgWidth2 = 450;
                float imgHeight2 = 300;
                if (yPos < 350) { // si no hay espacio suficiente, crear nueva página
                    content.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    yPos = 780;
                }
                content.drawImage(image2, 80, yPos - imgHeight2, imgWidth2, imgHeight2);
                yPos -= (imgHeight2 + 40);
            } catch (IOException e) {
                System.out.println(" No se encontró la imagen de salud.");
            }

            // ===== TABLAS DE DATOS =====
            yPos = escribirTablaDesdeExcel(content, ARCHIVO_ENTRENAMIENTO, "INFORME DE ENTRENAMIENTOS", yPos);
            yPos -= 40;
            yPos = escribirTablaDesdeExcel(content, ARCHIVO_SALUD, "INFORME DE SALUD", yPos);

            content.close();
            document.save(PDF_REPORTE);
            System.out.println("PDF generado correctamente: " + PDF_REPORTE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =================== LEE EXCEL Y LO MUESTRA EN PDF ===================
    private static float escribirTablaDesdeExcel(PDPageContentStream content, String rutaExcel, String titulo, float yPos) throws IOException {
        try (FileInputStream fis = new FileInputStream(rutaExcel);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = wb.getSheetAt(0);
            if (sheet == null) return yPos;

            if (yPos < 150) return yPos; // evitar escribir fuera de la página

            // Título de la tabla
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.newLineAtOffset(50, yPos);
            content.showText(titulo);
            content.endText();
            yPos -= 20;

            // Cabecera (primera fila)
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                StringBuilder headerText = new StringBuilder();
                for (Cell cell : headerRow) {
                    headerText.append(cell.toString()).append(" | ");
                }
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                content.newLineAtOffset(50, yPos);
                content.showText(headerText.toString());
                content.endText();
                yPos -= 15;
            }

            // Filas siguientes
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StringBuilder rowText = new StringBuilder();
                for (Cell cell : row) {
                    rowText.append(cell.toString()).append(" | ");
                }

                if (yPos < 50) break; // evitar escribir fuera de la página
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 11);
                content.newLineAtOffset(50, yPos);
                content.showText(rowText.toString());
                content.endText();
                yPos -= 15;
            }

        } catch (Exception e) {
            System.out.println("️ Error leyendo " + rutaExcel);
        }
        return yPos;
    }

    // =================== MAIN ===================
    public static void main(String[] args) {
        crearGraficoEntrenamiento();
        crearGraficoSalud();
        generarPDF();
    }
}
