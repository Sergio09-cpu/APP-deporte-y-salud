package com.mycompany.deportessalud;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;
import java.awt.Color;

public class InformePDF {

    private static final String PDF_ENTRENAMIENTO = "informe_entrenamientos.pdf";
    private static final String PDF_SALUD = "informe_salud.pdf";
    private static final String EXCEL_ENTRENAMIENTO = "entrenamientos.xlsx";
    private static final String EXCEL_SALUD = "salud.xlsx";
    private static final String IMG_ENTRENAMIENTO = "grafico_entrenamiento.jpg";
    private static final String IMG_SALUD = "grafico_salud.jpg";

    // === INFORME ENTRENAMIENTOS ===
    public static void generarInformeEntrenamiento() {
        File pdf = new File(PDF_ENTRENAMIENTO);
        if (pdf.exists()) pdf.delete();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            //  Título
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 20);
            content.newLineAtOffset(50, 750);
            content.showText("INFORME DE ENTRENAMIENTOS");
            content.endText();

            // Leer datos del Excel
            List<String[]> datos = leerDatosDesdeExcel(EXCEL_ENTRENAMIENTO);

            // Dibujar tabla
            float margin = 50;
            float yStart = 700;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float col1Width = tableWidth * 0.4f;
            float col2Width = tableWidth * 0.6f;

            // Encabezado
            drawTableRow(content, margin, yPosition, rowHeight, col1Width, col2Width,
                    new String[]{"Tipo de Entrenamiento", "Descripción"}, true);
            yPosition -= rowHeight;

            // Filas con datos
            for (String[] fila : datos) {
                if (yPosition < 150) break;
                drawTableRow(content, margin, yPosition, rowHeight, col1Width, col2Width, fila, false);
                yPosition -= rowHeight;
            }

            // Insertar gráfico
            File img = new File(IMG_ENTRENAMIENTO);
            if (img.exists()) {
                PDImageXObject image = PDImageXObject.createFromFileByContent(img, doc);
                content.drawImage(image, 80, 80, 450, 250);
            } else {
                System.out.println(" No se encontró la imagen del gráfico de entrenamientos: " + IMG_ENTRENAMIENTO);
            }

            content.close();
            doc.save(PDF_ENTRENAMIENTO);
            System.out.println("Informe PDF de entrenamientos generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === INFORME SALUD ===
    public static void generarInformeSalud() {
        File pdf = new File(PDF_SALUD);
        if (pdf.exists()) pdf.delete();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            // Título
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 20);
            content.newLineAtOffset(50, 750);
            content.showText("INFORME DE SALUD");
            content.endText();

            // Leer datos del Excel
            List<String[]> datos = leerDatosDesdeExcel(EXCEL_SALUD);

            float margin = 50;
            float yStart = 700;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float col1Width = tableWidth * 0.4f;
            float col2Width = tableWidth * 0.6f;

            // Encabezado
            drawTableRow(content, margin, yPosition, rowHeight, col1Width, col2Width,
                    new String[]{"Dato", "Valor"}, true);
            yPosition -= rowHeight;

            // Filas con datos
            for (String[] fila : datos) {
                if (yPosition < 150) break;
                drawTableRow(content, margin, yPosition, rowHeight, col1Width, col2Width, fila, false);
                yPosition -= rowHeight;
            }

            //  Insertar gráfico circular
            File img = new File(IMG_SALUD);
            if (img.exists()) {
                PDImageXObject image = PDImageXObject.createFromFileByContent(img, doc);
                content.drawImage(image, 80, 80, 450, 250);
            } else {
                System.out.println(" No se encontró la imagen del gráfico de salud: " + IMG_SALUD);
            }

            content.close();
            doc.save(PDF_SALUD);
            System.out.println("Informe PDF de salud generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === DIBUJAR TABLA ===
    private static void drawTableRow(PDPageContentStream content, float startX, float startY, float rowHeight,
                                     float col1Width, float col2Width, String[] valores, boolean header) throws IOException {
        float cellPadding = 4f;
        float textY = startY - 15;

        // Dibujar rectángulos (bordes)
        content.setStrokingColor(Color.BLACK);
        content.addRect(startX, startY - rowHeight, col1Width, rowHeight);
        content.addRect(startX + col1Width, startY - rowHeight, col2Width, rowHeight);
        content.stroke();

        // Texto
        content.beginText();
        content.setFont(header ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, 10);
        content.newLineAtOffset(startX + cellPadding, textY);
        content.showText(valores.length > 0 ? valores[0] : "");
        content.endText();

        content.beginText();
        content.setFont(header ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, 10);
        content.newLineAtOffset(startX + col1Width + cellPadding, textY);
        content.showText(valores.length > 1 ? valores[1] : "");
        content.endText();
    }

    // === LEER DATOS DEL EXCEL ===
    private static List<String[]> leerDatosDesdeExcel(String archivo) {
        List<String[]> datos = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(archivo);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) return datos;

            for (Row row : sheet) {
                Cell c1 = row.getCell(0);
                Cell c2 = row.getCell(1);
                String col1 = (c1 != null) ? c1.toString() : "";
                String col2 = (c2 != null) ? c2.toString() : "";
                if (!col1.isEmpty() || !col2.isEmpty()) {
                    datos.add(new String[]{col1, col2});
                }
            }
        } catch (Exception e) {
            System.out.println(" No se pudo leer el Excel: " + archivo);
        }
        return datos;
    }

    // === GENERAR AMBOS ===
    public static void generarInformeCompleto() {
        generarInformeEntrenamiento();
        generarInformeSalud();
    }
}
