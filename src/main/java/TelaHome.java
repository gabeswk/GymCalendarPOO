import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class TelaHome extends JFrame {

    private JPanel painelCalendario;
    private JPanel painelDireito;
    private int anoAtual;
    private int mesAtual;

    public TelaHome(String usuarioNome) {
        setTitle("Agenda de Treinos - " + usuarioNome);
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        painelCalendario = new JPanel();
        painelDireito = new JPanel();

        painelCalendario.setPreferredSize(new Dimension(500, 650));
        painelDireito.setPreferredSize(new Dimension(500, 650));

        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));

        add(painelCalendario, BorderLayout.WEST);
        add(new JScrollPane(painelDireito), BorderLayout.CENTER);

        LocalDate hoje = LocalDate.now();
        anoAtual = hoje.getYear();
        mesAtual = hoje.getMonthValue();

        montarCalendario(anoAtual, mesAtual);
        atualizarPainelDireito(null);

        setVisible(true);
    }

    private void montarCalendario(int ano, int mes) {
        painelCalendario.removeAll();
        painelCalendario.setLayout(new BorderLayout());

        // topo com navegação de mês
        JPanel topo = new JPanel(new FlowLayout());
        JButton btnAnterior = new JButton("<");
        JButton btnProximo = new JButton(">");
        JLabel lblMes = new JLabel(YearMonth.of(ano, mes).getMonth().toString() + " " + ano);

        topo.add(btnAnterior);
        topo.add(lblMes);
        topo.add(btnProximo);
        painelCalendario.add(topo, BorderLayout.NORTH);

        // dias da semana
        JPanel dias = new JPanel(new GridLayout(0, 7));
        String[] nomes = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        for (String n : nomes) {
            JLabel l = new JLabel(n, SwingConstants.CENTER);
            l.setForeground(Color.BLUE.darker());
            dias.add(l);
        }

        YearMonth ym = YearMonth.of(ano, mes);
        LocalDate primeiro = ym.atDay(1);
        int dayOfWeek = primeiro.getDayOfWeek().getValue(); // 1 = segunda
        int offset = dayOfWeek % 7;

        for (int i = 0; i < offset; i++) {
            dias.add(new JLabel(""));
        }

        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            LocalDate date = LocalDate.of(ano, mes, d);
            JButton b = new JButton("" + d);
            b.setMargin(new Insets(2,2,2,2));
            b.setOpaque(true);
            b.setBorderPainted(false);

            TreinoDoDia t = RepositorioTreinos.getTreino(date);
            if (t != null) {
                b.setBackground(t.getCor());
            } else {
                b.setBackground(Color.LIGHT_GRAY);
            }

            b.addActionListener((ActionEvent e) -> {
                atualizarPainelDireito(date);
            });

            dias.add(b);
        }

        painelCalendario.add(dias, BorderLayout.CENTER);

        btnAnterior.addActionListener(e -> {
            int m = mes - 1;
            int a = ano;
            if (m < 1) {
                m = 12;
                a--;
            }
            anoAtual = a;
            mesAtual = m;
            montarCalendario(anoAtual, mesAtual);
        });

        btnProximo.addActionListener(e -> {
            int m = mes + 1;
            int a = ano;
            if (m > 12) {
                m = 1;
                a++;
            }
            anoAtual = a;
            mesAtual = m;
            montarCalendario(anoAtual, mesAtual);
        });

        painelCalendario.revalidate();
        painelCalendario.repaint();
    }

    private void atualizarPainelDireito(LocalDate dia) {
        painelDireito.removeAll();

        if (dia == null) {
            painelDireito.add(new JLabel("Selecione um dia no calendário."));
            painelDireito.revalidate();
            painelDireito.repaint();
            return;
        }

        painelDireito.add(new JLabel("📅 Dia: " + dia.toString()));
        painelDireito.add(Box.createVerticalStrut(15));

        TreinoDoDia treino = RepositorioTreinos.getTreino(dia);

        if (treino == null) {
            painelDireito.add(new JLabel("Nenhum treino cadastrado para este dia."));
            painelDireito.add(Box.createVerticalStrut(10));

            JButton cadastrar = new JButton("Cadastrar treino (individual ou recorrente)");
            cadastrar.addActionListener(e -> abrirDialogCadastrarTreino(dia));

            painelDireito.add(cadastrar);

        } else {
            painelDireito.add(new JLabel("Treino: " + treino.getDescricao()));
            painelDireito.add(Box.createVerticalStrut(10));

            DefaultListModel<String> modelo = new DefaultListModel<>();
            for (Exercicio ex : treino.getExercicios()) {
                modelo.addElement(ex.toString());
            }

            JList<String> listaEx = new JList<>(modelo);
            JScrollPane scroll = new JScrollPane(listaEx);
            scroll.setPreferredSize(new Dimension(450, 200));
            painelDireito.add(scroll);
            painelDireito.add(Box.createVerticalStrut(15));

            JButton btnAdd = new JButton("Adicionar exercício");
            btnAdd.addActionListener(e -> {
                String nome = JOptionPane.showInputDialog(this, "Nome do exercício:");
                if (nome == null || nome.isEmpty()) return;

                String sSeries = JOptionPane.showInputDialog(this, "Séries:");
                String sReps = JOptionPane.showInputDialog(this, "Repetições:");

                try {
                    int series = Integer.parseInt(sSeries);
                    int reps = Integer.parseInt(sReps);

                    treino.adicionarExercicio(new Exercicio(nome, series, reps));
                    atualizarPainelDireito(dia);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(this, "Erro: valores inválidos.");
                }
            });
            painelDireito.add(btnAdd);

            JButton btnRem = new JButton("Remover exercício selecionado");
            btnRem.addActionListener(e -> {
                int idx = listaEx.getSelectedIndex();
                if (idx >= 0) {
                    treino.removerExercicio(idx);
                    atualizarPainelDireito(dia);
                }
            });
            painelDireito.add(btnRem);

            JButton btnEdit = new JButton("Editar exercício selecionado");
            btnEdit.addActionListener(e -> {
                int idx = listaEx.getSelectedIndex();
                if (idx < 0) return;
                Exercicio ex = treino.getExercicios().get(idx);

                String novoNome = JOptionPane.showInputDialog(this, "Nome:", ex.getNome());
                String novasSeries = JOptionPane.showInputDialog(this, "Séries:", ex.getSeries());
                String novasReps = JOptionPane.showInputDialog(this, "Repetições:", ex.getRepeticoes());

                try {
                    ex.setNome(novoNome);
                    ex.setSeries(Integer.parseInt(novasSeries));
                    ex.setRepeticoes(Integer.parseInt(novasReps));
                    atualizarPainelDireito(dia);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar.");
                }
            });
            painelDireito.add(btnEdit);

            JButton btnMark = new JButton("Marcar como realizado");
            btnMark.addActionListener(e -> {
                // Aqui você pode marcar feito — por enquanto só muda a cor de fundo
                JOptionPane.showMessageDialog(this, "Marcado como realizado!");
                // opcional: poderia remover ou mudar cor
            });
            painelDireito.add(btnMark);
        }

        painelDireito.revalidate();
        painelDireito.repaint();

        // Atualiza o calendário para refletir cores
        montarCalendario(anoAtual, mesAtual);
    }

    private void abrirDialogCadastrarTreino(LocalDate startDate) {
        String nomeTreino = JOptionPane.showInputDialog(this, "Nome do treino (ex: Peito, Costas, Perna):");
        if (nomeTreino == null || nomeTreino.isEmpty()) return;

        String sFreq = JOptionPane.showInputDialog(this,
                "A cada quantos dias se repete? (2 a 7) — tempo de repetição por 4 semanas:");

        int freq;
        try {
            freq = Integer.parseInt(sFreq);
            if (freq < 1 || freq > 7) throw new Exception();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Frequência inválida!");
            return;
        }

        // definir cor conforme nome do treino
        Color cor;
        switch (nomeTreino.toLowerCase()) {
            case "peito": cor = Color.BLUE; break;
            case "costas": cor = Color.RED; break;
            case "perna": cor = new Color(128, 0, 128); break; // roxo
            case "ombro": cor = Color.ORANGE; break;
            default: cor = Color.GRAY; break;
        }

        // gerar datas para 4 semanas
        List<LocalDate> datas = new ArrayList<>();
        LocalDate d = startDate;
        for (int i = 0; i < (4 * 7 / freq + 1); i++) {
            datas.add(d);
            d = d.plusDays(freq);
        }

        // verificar conflito
        for (LocalDate dt : datas) {
            if (RepositorioTreinos.existeTreino(dt)) {
                JOptionPane.showMessageDialog(this,
                        "Erro: já existe treino cadastrado em " + dt.toString() + ". Operação cancelada.");
                return;
            }
        }

        // tudo certo — salva para todas as datas
        for (LocalDate dt : datas) {
            TreinoDoDia t = new TreinoDoDia(nomeTreino, cor);
            RepositorioTreinos.salvarTreino(dt, t);
        }

        JOptionPane.showMessageDialog(this, "Treino agendado com sucesso!");
        atualizarPainelDireito(startDate);
    }
}
