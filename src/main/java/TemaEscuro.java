import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TemaEscuro {

    public static final Color FUNDO = new Color(30, 30, 30);
    public static final Color CAMPO = new Color(60, 60, 64);
    public static final Color BOTAO = new Color(0, 122, 204);
    public static final Color BOTAO_HOVER = new Color(28, 151, 234);
    public static final Color TEXTO = new Color(241, 241, 241);
    public static final Color BORDA = new Color(80, 80, 84);

    // --- MÉTODOS PÚBLICOS ---

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

    public static void aplicarBotao(JButton botao) {
        // Reutiliza a lógica base passando as cores padrão
        configurarBaseBotao(botao, BOTAO, BOTAO_HOVER, new Dimension(200, 40), 14);
    }

    public static void aplicarBotaoLogout(JButton botao) {
        // Reutiliza a base, mas com tamanho e fonte menores
        configurarBaseBotao(botao, BOTAO, BOTAO_HOVER, new Dimension(80, 32), 12);
        // Ajuste específico de padding para o logout
        botao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public static void aplicarBotaoColorido(JButton botao, Color corBase) {
        // Calcula a cor de hover automaticamente (um pouco mais escura ou clara)
        Color corHover = corBase.darker();
        configurarBaseBotao(botao, corBase, corHover, new Dimension(200, 40), 14);
    }

    public static void aplicarLabel(JLabel label) {
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void aplicarBotaoCompacto(JButton botao, Color cor) {
        configurarBaseBotao(botao, cor, cor.darker(), new Dimension(100, 32), 12);
        botao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    // --- MÉTODOS PRIVADOS AUXILIARES ---

    /**
     * Centraliza a configuração visual de todos os botões
     */
    private static void configurarBaseBotao(JButton botao, Color bg, Color bgHover, Dimension dim, int fontSize) {
        botao.setBackground(bg);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define tamanho
        if (dim != null) {
            botao.setPreferredSize(dim);
            botao.setMinimumSize(dim);
            botao.setMaximumSize(dim);
        }

        // Padding padrão
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Adiciona o efeito de hover
        adicionarHoverEffect(botao, bg, bgHover);
    }

    /**
     * Cria o listener de mouse
     */
    private static void adicionarHoverEffect(JButton botao, Color normal, Color hover) {
        for (var l : botao.getMouseListeners()) {
            if (l instanceof HoverListener)
                botao.removeMouseListener(l);
        }

        botao.addMouseListener(new HoverListener(botao, normal, hover));
    }

    // Classe estática interna para evitar criação excessiva de classes anônimas
    private static class HoverListener extends MouseAdapter {
        private final JButton botao;
        private final Color normal;
        private final Color hover;

        public HoverListener(JButton botao, Color normal, Color hover) {
            this.botao = botao;
            this.normal = normal;
            this.hover = hover;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            botao.setBackground(hover);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            botao.setBackground(normal);
        }
    }
}

class CustomScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(TemaEscuro.FUNDO);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled())
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(isThumbRollover() ? TemaEscuro.BOTAO : TemaEscuro.BORDA);

        int arco = 8;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y, thumbBounds.width - 4, thumbBounds.height, arco, arco);
        } else {
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y + 2, thumbBounds.width, thumbBounds.height - 4, arco, arco);
        }
        g2.dispose();
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