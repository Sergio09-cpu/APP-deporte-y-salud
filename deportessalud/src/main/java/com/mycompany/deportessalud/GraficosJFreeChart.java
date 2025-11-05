package com.mycompany.deportessalud;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraficosJFreeChart {

    private static final String ARCHIVO_SALUD = "salud.xlsx";
    private static final String ARCHIVO_ENTRENAMIENTOS = "entrenamientos.xlsx";

    // Gráfico de barras de entrenamientos
    public static void generarGraficoBarrasEntrenamiento() {
        File archivo = new File(ARCHIVO_ENTRENAMIENTOS);
        if (!archivo.exists()) {
            System.out.println("No existe el archivo de entrenamientos todavía.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheet("Datos");
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                System.out.println("No hay datos de entrenamientos.");
                return;
            }

            int fuerza = 0;
            int resistencia = 0;

            for (Row row : sheet) {
                if (row.getCell(0) == null) continue;
                String tipo = row.getCell(0).getStringCellValue().toLowerCase();
                if (tipo.contains("fuerza")) fuerza++;
                else if (tipo.contains("resistencia")) resistencia++;
            }

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(fuerza, "Entrenamientos", "Fuerza");
            dataset.addValue(resistencia, "Entrenamientos", "Resistencia");

            JFreeChart grafico = ChartFactory.createBarChart(
                    "Resumen de Entrenamientos",
                    "Tipo",
                    "Cantidad",
                    dataset
            );

            // Guardamos el gráfico con el nombre que usará el PDF
            ChartUtils.saveChartAsJPEG(new File("grafico_entrenamiento.jpg"), grafico, 800, 600);
            System.out.println(" Gráfico de barras actualizado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gráfico circular de salud
    public static void generarGraficoCircularSalud() {
        File archivo = new File(ARCHIVO_SALUD);
        if (!archivo.exists()) {
            System.out.println("No existe el archivo de salud todavía.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheet("Datos");
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                System.out.println("No hay datos de salud.");
                return;
            }

            Map<String, Integer> conteo = new HashMap<>();
            conteo.put("Bajo peso", 0);
            conteo.put("Peso normal", 0);
            conteo.put("Sobrepeso", 0);
            conteo.put("Obesidad", 0);

            for (Row row : sheet) {
                if (row.getPhysicalNumberOfCells() < 5) continue;
                String estado = row.getCell(4).getStringCellValue();
                conteo.computeIfPresent(estado, (k, v) -> v + 1);
            }

            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

            JFreeChart grafico = ChartFactory.createPieChart(
                    "Distribución de IMC",
                    dataset,
                    true, true, false
            );

            // Guardamos el gráfico con el nombre que usará el PDF
            ChartUtils.saveChartAsJPEG(new File("grafico_salud.jpg"), grafico, 800, 600);
            System.out.println("Gráfico circular actualizado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
