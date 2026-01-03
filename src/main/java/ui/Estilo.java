package ui;

import javax.swing.*;
import java.awt.*;

public class Estilo {

    public static final Color FUNDO_APP = new Color(245, 245, 245);
    public static final Color FUNDO_PAINEL = Color.WHITE;
    public static final Color AZUL = new Color(33, 150, 243);

    public static final Font TEXTO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 18);

    public static void aplicar() {
        UIManager.put("Label.font", TEXTO);
        UIManager.put("Button.font", TEXTO);
        UIManager.put("TextField.font", TEXTO);
        UIManager.put("PasswordField.font", TEXTO);
        UIManager.put("TextArea.font", TEXTO);

        UIManager.put("Panel.background", FUNDO_APP);
        UIManager.put("Viewport.background", FUNDO_PAINEL);

        UIManager.put("Button.background", AZUL);
        UIManager.put("Button.foreground", Color.WHITE);
    }
}
