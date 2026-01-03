package ui;

import javax.swing.*;
import java.awt.*;

public abstract class TelaBase extends JFrame {

    public TelaBase(String titulo) {
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilo.FUNDO_APP);
        setLayout(new BorderLayout());
    }
}
