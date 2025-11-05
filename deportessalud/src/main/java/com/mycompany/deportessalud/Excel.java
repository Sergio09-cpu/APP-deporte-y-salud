package com.mycompany.deportessalud;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.util.IOUtils;
import java.io.*;
import java.util.List;

public class Excel {

    private static final String ARCHIVO_ENTRENAMIENTO = "entrenamientos.xlsx";
    private static final String ARCHIVO_SALUD = "salud.xlsx";

    // eliminar archivos  previos para crear los nuevos 
    public static void inicializarArchivos() {
        try {
            File f1 = new File(ARCHIVO_ENTRENAMIENTO);
            File f2 = new File(ARCHIVO_SALUD);
            if (f1.exists()) f1.delete();
            if (f2.exists()) f2.delete();
            System.out.println("🧹 Archivos antiguos eliminados. Se crearán nuevos al guardar datos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // crear excel salud  o datos 
    private static XSSFWorkbook getWorkbook(String tipo) throws IOException {
        String ruta = tipo.equalsIgnoreCase("Salud") ? ARCHIVO_SALUD : ARCHIVO_ENTRENAMIENTO;
        File file = new File(ruta);

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return new XSSFWorkbook(fis);
            }
        } else {
            XSSFWorkbook wb = new XSSFWorkbook();
            wb.createSheet("Datos");
            return wb;
        }
    }

    private static void guardarWorkbook(XSSFWorkbook wb, String tipo) throws IOException {
        String ruta = tipo.equalsIgnoreCase("Salud") ? ARCHIVO_SALUD : ARCHIVO_ENTRENAMIENTO;
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            wb.write(fos);
        }
        wb.close();
    }

    // escribir datos 
    public static void escribirDatos(String tipo, List<String> datos) {
        try {
            XSSFWorkbook wb = getWorkbook(tipo);
            XSSFSheet sheet = wb.getSheet("Datos");
            int filaNum = sheet.getPhysicalNumberOfRows();
            Row fila = sheet.createRow(filaNum);

            for (int i = 0; i < datos.size(); i++) {
                fila.createCell(i).setCellValue(datos.get(i));
            }

            guardarWorkbook(wb, tipo);
            System.out.println("Datos escritos correctamente en " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //leer datos 
    public static void leerDatos(String tipo) {
        try (XSSFWorkbook wb = getWorkbook(tipo)) {
            XSSFSheet sheet = wb.getSheet("Datos");
            System.out.println("\n--- Datos en el archivo: " +
                    (tipo.equalsIgnoreCase("Salud") ? ARCHIVO_SALUD : ARCHIVO_ENTRENAMIENTO) + " ---");
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.toString() + " | ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // formato a las celdas
    public static void darFormato(String tipo) {
        try {
            XSSFWorkbook wb = getWorkbook(tipo);
            XSSFSheet sheet = wb.getSheet("Datos");

            // Ajuste de columnas
            for (int i = 0; i < 5; i++) sheet.autoSizeColumn(i);

            // Crear estilo
            CellStyle estilo = wb.createCellStyle();
            Font fuente = wb.createFont();
            fuente.setBold(true);
            estilo.setFont(fuente);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            estilo.setBorderBottom(BorderStyle.THIN);

            for (Row fila : sheet) {
                for (Cell celda : fila) {
                    celda.setCellStyle(estilo);
                }
            }

            guardarWorkbook(wb, tipo);
            System.out.println(" Formato aplicado en " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // insertar fila 
    public static void insertarFila(String tipo, int filaIndex) {
        try {
            XSSFWorkbook wb = getWorkbook(tipo);
            XSSFSheet sheet = wb.getSheet("Datos");
            sheet.shiftRows(filaIndex, sheet.getLastRowNum(), 1);
            Row nuevaFila = sheet.createRow(filaIndex);
            nuevaFila.createCell(0).setCellValue("Fila insertada automáticamente");
            guardarWorkbook(wb, tipo);
            System.out.println("Fila insertada en " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // insertar columna 
    public static void insertarColumna(String tipo, int columnaIndex) {
        try {
            XSSFWorkbook wb = getWorkbook(tipo);
            XSSFSheet sheet = wb.getSheet("Datos");

            for (Row row : sheet) {
                int lastCell = row.getLastCellNum();
                if (lastCell > 0) {
                    for (int col = lastCell; col > columnaIndex; col--) {
                        Cell oldCell = row.getCell(col - 1);
                        Cell newCell = row.createCell(col);
                        if (oldCell != null) {
                            newCell.setCellValue(oldCell.toString());
                        }
                    }
                    row.createCell(columnaIndex).setCellValue("Nueva columna");
                }
            }

            guardarWorkbook(wb, tipo);
            System.out.println("Columna insertada en " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //insertar imagen 
    public static void insertarImagen(String tipo, String rutaImagen) {
        try {
            XSSFWorkbook wb = getWorkbook(tipo);
            XSSFSheet sheet = wb.getSheet("Datos");

            InputStream is = new FileInputStream(rutaImagen);
            byte[] bytes = IOUtils.toByteArray(is);
            int imgIndex = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();

            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(sheet.getPhysicalNumberOfRows() + 2);
            anchor.setCol2(5);
            anchor.setRow2(sheet.getPhysicalNumberOfRows() + 10);

            drawing.createPicture(anchor, imgIndex);

            guardarWorkbook(wb, tipo);
            System.out.println(" Imagen insertada en " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
