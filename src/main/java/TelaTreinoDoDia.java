import javax.swing.*;
import java.time.LocalDate;

public class TelaTreinoDoDia extends JFrame {

    public TelaTreinoDoDia(LocalDate dia, Usuario usuario) {

        setTitle("Treinos de " + dia.toString());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Treinos do dia: " + dia.toString());
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label);

        setVisible(true);
    }
}
