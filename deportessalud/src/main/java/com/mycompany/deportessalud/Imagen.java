package com.mycompany.deportessalud;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import java.io.File;

/**
 * Clase Imagen
 * 
 * Permite cargar imágenes locales o desde Internet, escalarlas suavemente,
 * y ahora también obtenerlas como objetos Image o comprobar su existencia.
 */
public class Imagen {

    // -------------------------------------------------
    // 🔹 MÉTODOS ORIGINALES (NO TOCADOS)
    // -------------------------------------------------

    public static ImageIcon loadLocalImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static ImageIcon loadOnlineImage(String url, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(new URL(url));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
            return null;
        }
    }

    // -------------------------------------------------
    // 🔹 MÉTODOS AÑADIDOS (NO AFECTAN A LOS TUYOS)
    // -------------------------------------------------

    /**
     * Carga una imagen local sin escalar (devuelve el objeto Image).
     * Útil para usar directamente con ImagenEscalada.
     */
    public static Image getLocalImage(String path) {
        try {
            return new ImageIcon(path).getImage();
        } catch (Exception e) {
            System.err.println("Error cargando imagen local: " + e.getMessage());
            return null;
        }
    }

    /**
     * Carga una imagen desde una URL sin escalar (devuelve el objeto Image).
     * Esto permite usar la imagen directamente con JPanel personalizados.
     */
    public static Image getOnlineImage(String url) {
        try {
            return new ImageIcon(new URL(url)).getImage();
        } catch (Exception e) {
            System.err.println("Error cargando imagen online: " + e.getMessage());
            return null;
        }
    }

    /**
     * Comprueba si una imagen local existe en el disco antes de intentar cargarla.
     */
    public static boolean existeImagenLocal(String path) {
        File archivo = new File(path);
        return archivo.exists() && archivo.isFile();
    }

    /**
     * Escala una imagen ya existente al tamaño indicado (útil si ya la cargaste antes).
     */
    public static ImageIcon escalarImagen(Image img, int width, int height) {
        if (img == null) return null;
        Image nueva = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(nueva);
    }
}
