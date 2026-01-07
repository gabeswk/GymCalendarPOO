import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import javax.swing.*;

public class TemaEscuro {

    public static final Color FUNDO = new Color(45, 45, 48);
    public static final Color CAMPO = new Color(60, 60, 64);
    public static final Color BOTAO = new Color(0, 122, 204);
    public static final Color BOTAO_HOVER = new Color(28, 151, 234);
    public static final Color TEXTO = new Color(241, 241, 241);
    public static final Color BORDA = new Color(80, 80, 84);

    // Estilo do input
    public static void aplicarInput(JTextField campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        campo.setBackground(CAMPO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // Estilo do Botão
    public static void aplicarBotao(JButton botao) {
        botao.setBackground(BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tamanho fixo do botão (não estica)
        botao.setPreferredSize(new Dimension(200, 40));
        botao.setMinimumSize(new Dimension(200, 40));
        botao.setMaximumSize(new Dimension(200, 40));

        // Adiciona padding interno
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover com transição mais suave
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(BOTAO_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(BOTAO);
            }
        });
    }

    public static void aplicarBotaoAcao(JButton b, Color corBase) {
        b.setBackground(corBase);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(180, 36));

        // Cantos arredondados
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // Hover
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(corBase.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(corBase);
            }
        });
    }

    public static void padronizarBotaoLateral(JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        b.setPreferredSize(new Dimension(200, 36));
    }



    // Estilo da label
    public static void aplicarLabel(JLabel label) {
        label.setForeground(new Color(230, 230, 230)); // Texto bem mais claro
        label.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Negrito e maior
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizado
        label.setHorizontalAlignment(SwingConstants.CENTER); // Texto centralizado
    }
}


//ScrollBar mais bonita
class CustomScrollBarUI extends BasicScrollBarUI {

    // Use cores do seu tema escuro (ajuste conforme necessário)
    private static final Color TRACK_COLOR = new Color(50, 50, 50);  // Fundo escuro da trilha
    private static final Color THUMB_COLOR = new Color(80, 80, 80);  // Polegar (parte arrastável)
    private static final Color THUMB_HOVER_COLOR = new Color(100, 100, 100);  // Efeito de hover

    @Override
    protected void configureScrollBarColors() {
        this.trackColor = TRACK_COLOR;
        this.thumbColor = THUMB_COLOR;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(TRACK_COLOR);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(isThumbRollover() ? THUMB_HOVER_COLOR : THUMB_COLOR);
        g.fillRoundRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1, 10, 10);  // Cantos arredondados
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}