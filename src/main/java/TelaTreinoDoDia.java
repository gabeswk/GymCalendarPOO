import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaTreinoDoDia extends JFrame {

    public TelaTreinoDoDia(LocalDate dia, Usuario usuario) {

        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        setTitle("Treinos de " + dia);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        TreinoDoDia treino = RepositorioTreinos.getTreino(usuario.getEmail(), dia);

        JLabel label = new JLabel("Treinos do dia: " + dia);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnConcluido = new JButton("Concluído");

        btnConcluido.addActionListener(e -> {
            if (treino != null) {
                treino.setConcluido(true);
                RepositorioTreinos.salvarTreino(usuario.getEmail(), dia, treino);
            }
            dispose();
        });



        add(label, BorderLayout.CENTER);
        add(btnConcluido, BorderLayout.SOUTH);

        setVisible(true);
    }

}
