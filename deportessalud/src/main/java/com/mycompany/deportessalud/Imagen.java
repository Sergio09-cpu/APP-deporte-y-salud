package com.mycompany.deportessalud;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import java.io.File;


public class Imagen {

    // -------------------------------------------------
    // MÉTODOS ORIGINALES 
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
    // MÉTODOS AÑADIDOS
    // -------------------------------------------------

    
    public static Image getLocalImage(String path) {
        try {
            return new ImageIcon(path).getImage();
        } catch (Exception e) {
            System.err.println("Error cargando imagen local: " + e.getMessage());
            return null;
        }
    }

    
    public static Image getOnlineImage(String url) {
        try {
            return new ImageIcon(new URL(url)).getImage();
        } catch (Exception e) {
            System.err.println("Error cargando imagen online: " + e.getMessage());
            return null;
        }
    }

    
    public static boolean existeImagenLocal(String path) {
        File archivo = new File(path);
        return archivo.exists() && archivo.isFile();
    }

    
    public static ImageIcon escalarImagen(Image img, int width, int height) {
        if (img == null) return null;
        Image nueva = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(nueva);
    }
}
