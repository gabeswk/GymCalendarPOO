import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        //Icone que fica no canto
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        setTitle("Agenda de Treinos - " + usuarioNome);
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Define cor de fundo da janela principal (Tema Escuro)
        getContentPane().setBackground(TemaEscuro.FUNDO);

        painelCalendario = new JPanel();
        painelDireito = new JPanel();

        painelCalendario.setPreferredSize(new Dimension(500, 650));
        painelDireito.setPreferredSize(new Dimension(500, 650));

        // Aplica o fundo nos painéis
        painelCalendario.setBackground(TemaEscuro.FUNDO);
        painelDireito.setBackground(TemaEscuro.FUNDO);

        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));
        // Adiciona uma margem interna no painel direito para não colar na borda
        painelDireito.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(painelCalendario, BorderLayout.WEST);

        // ScrollPane com estilo escuro (remove bordas padrão e ajusta fundo)
        JScrollPane scrollDireito = new JScrollPane(painelDireito);

        // Estilo da Scrollbar
        scrollDireito.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollDireito.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        // Deixa a scrollbar mais fina
        scrollDireito.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));  // Largura de 10px
        scrollDireito.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));  // Altura de 10px

        scrollDireito.setBorder(null);
        scrollDireito.getViewport().setBackground(TemaEscuro.FUNDO);
        add(scrollDireito, BorderLayout.CENTER);

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
        painelCalendario.setBackground(TemaEscuro.FUNDO);

        // topo com navegação de mês
        JPanel topo = new JPanel(new FlowLayout());
        topo.setBackground(TemaEscuro.FUNDO);

        JButton btnAnterior = criarBotaoNavegacao("<");
        JButton btnProximo = criarBotaoNavegacao(">");

        JLabel lblMes = new JLabel(YearMonth.of(ano, mes).getMonth().toString() + " " + ano);
        TemaEscuro.aplicarLabel(lblMes); // Aplica estilo do texto

        topo.add(btnAnterior);
        topo.add(lblMes);
        topo.add(btnProximo);
        painelCalendario.add(topo, BorderLayout.NORTH);

        // dias da semana
        JPanel dias = new JPanel(new GridLayout(0, 7, 5, 5)); // GridLayout com espaçamento (gap)
        dias.setBackground(TemaEscuro.FUNDO);
        dias.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margem ao redor do grid

        String[] nomes = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        for (String n : nomes) {
            JLabel l = new JLabel(n, SwingConstants.CENTER);
            l.setForeground(TemaEscuro.BOTAO); // Cor de destaque para os dias
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dias.add(l);
        }

        YearMonth ym = YearMonth.of(ano, mes);
        LocalDate primeiro = ym.atDay(1);
        int dayOfWeek = primeiro.getDayOfWeek().getValue(); // 1 = segunda
        int offset = dayOfWeek % 7;

        // Espaços vazios antes do dia 1
        for (int i = 0; i < offset; i++) {
            JLabel vazio = new JLabel("");
            dias.add(vazio);
        }

        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            LocalDate date = LocalDate.of(ano, mes, d);
            JButton b = new JButton("" + d);

            // Estilo manual para os botões do calendário
            b.setMargin(new Insets(2,2,2,2));
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            b.setFocusPainted(false);

            TreinoDoDia t = RepositorioTreinos.getTreino(date);
            if (t != null) {
                b.setBackground(t.getCor());
            } else {
                b.setBackground(TemaEscuro.CAMPO); // Fundo escuro se vazio
            }

            b.addActionListener((ActionEvent e) -> {
                atualizarPainelDireito(date);
            });

            dias.add(b);
        }

        painelCalendario.add(dias, BorderLayout.CENTER);

        btnAnterior.addActionListener(e -> {
            mesAtual--;
            if (mesAtual < 1) { mesAtual = 12; anoAtual--; }
            montarCalendario(anoAtual, mesAtual);
        });

        btnProximo.addActionListener(e -> {
            mesAtual++;
            if (mesAtual > 12) { mesAtual = 1; anoAtual++; }
            montarCalendario(anoAtual, mesAtual);
        });

        painelCalendario.revalidate();
        painelCalendario.repaint();
    }

    private JButton criarBotaoNavegacao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(TemaEscuro.BOTAO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void atualizarPainelDireito(LocalDate dia) {
        painelDireito.removeAll();

        if (dia == null) {
            JLabel lbl = new JLabel("Selecione um dia no calendário.");
            TemaEscuro.aplicarLabel(lbl);
            painelDireito.add(lbl);
            painelDireito.revalidate();
            painelDireito.repaint();
            return;
        }

        JLabel lblDia = new JLabel("📅 Dia: " + dia.toString());
        TemaEscuro.aplicarLabel(lblDia);
        painelDireito.add(lblDia);
        painelDireito.add(Box.createVerticalStrut(20));

        TreinoDoDia treino = RepositorioTreinos.getTreino(dia);

        if (treino == null) {
            JLabel lblNada = new JLabel("Nenhum treino cadastrado.");
            TemaEscuro.aplicarLabel(lblNada);
            painelDireito.add(lblNada);
            painelDireito.add(Box.createVerticalStrut(15));

            JButton cadastrar = new JButton("Agendar Treino");
            TemaEscuro.aplicarBotao(cadastrar);
            cadastrar.addActionListener(e -> abrirDialogCadastrarTreino(dia));

            painelDireito.add(cadastrar);

        } else {
            JLabel lblTreino = new JLabel("Treino: " + treino.getDescricao());
            TemaEscuro.aplicarLabel(lblTreino);
            painelDireito.add(lblTreino);
            painelDireito.add(Box.createVerticalStrut(10));

            DefaultListModel<String> modelo = new DefaultListModel<>();
            for (Exercicio ex : treino.getExercicios()) {
                modelo.addElement(ex.toString());
            }

            JList<String> listaEx = new JList<>(modelo);
            listaEx.setBackground(TemaEscuro.CAMPO);
            listaEx.setForeground(TemaEscuro.TEXTO);
            listaEx.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            JScrollPane scroll = new JScrollPane(listaEx);
            scroll.setPreferredSize(new Dimension(450, 200));
            scroll.setBorder(BorderFactory.createLineBorder(TemaEscuro.BORDA));
            painelDireito.add(scroll);
            painelDireito.add(Box.createVerticalStrut(15));

            JPanel botoesPanel = new JPanel();
            botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
            botoesPanel.setBackground(TemaEscuro.FUNDO);
            botoesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton btnAdd = new JButton("Adicionar Exercício");
            TemaEscuro.aplicarBotao(btnAdd);

            JButton btnRem = new JButton("Remover Selecionado");
            TemaEscuro.aplicarBotao(btnRem);
            btnRem.setBackground(new Color(192, 57, 43)); // Vermelho

            JButton btnEdit = new JButton("Editar Selecionado");
            TemaEscuro.aplicarBotao(btnEdit);
            btnEdit.setBackground(new Color(211, 84, 0)); // Laranja

            botoesPanel.add(btnAdd);
            botoesPanel.add(Box.createVerticalStrut(10));
            botoesPanel.add(btnEdit);
            botoesPanel.add(Box.createVerticalStrut(10));
            botoesPanel.add(btnRem);

            painelDireito.add(botoesPanel);

            btnAdd.addActionListener(e -> {
                //String nome = JOptionPane.showInputDialog(this, "Nome do exercício:");
                String nome = DialogoEscuro.mostrarInput(this, "Nome do exercício:");
                if (nome == null || nome.isEmpty()) return;
                String sSeries = DialogoEscuro.mostrarInput(this, "Séries:");
                String sReps = DialogoEscuro.mostrarInput(this, "Repetições:");
                try {
                    int series = Integer.parseInt(sSeries);
                    int reps = Integer.parseInt(sReps);
                    treino.adicionarExercicio(new Exercicio(nome, series, reps));
                    atualizarPainelDireito(dia);
                } catch (Exception err) {
                    DialogoEscuro.mostrarErro(this, "Erro: valores inválidos.");
                }
            });

            btnRem.addActionListener(e -> {
                int idx = listaEx.getSelectedIndex();
                if (idx >= 0) {
                    treino.removerExercicio(idx);
                    atualizarPainelDireito(dia);
                }
            });

            btnEdit.addActionListener(e -> {
                int idx = listaEx.getSelectedIndex();
                if (idx < 0) return;
                Exercicio ex = treino.getExercicios().get(idx);
                //String novoNome = JOptionPane.showInputDialog(this, "Nome:", ex.getNome());
                String novoNome = DialogoEscuro.mostrarInput(this, "Nome:", ex.getNome());
                //String novasSeries = JOptionPane.showInputDialog(this, "Séries:", ex.getSeries());
                String novasSeries = DialogoEscuro.mostrarInput(this, "Séries:", String.valueOf(ex.getSeries()));
                //String novasReps = JOptionPane.showInputDialog(this, "Repetições:", ex.getRepeticoes());
                String novasReps = DialogoEscuro.mostrarInput(this, "Repetições:", String.valueOf(ex.getRepeticoes()));
                try {
                    ex.setNome(novoNome);
                    ex.setSeries(Integer.parseInt(novasSeries));
                    ex.setRepeticoes(Integer.parseInt(novasReps));
                    atualizarPainelDireito(dia);
                } catch (Exception err) {
                    //JOptionPane.showMessageDialog(this, "Erro ao editar.");
                    DialogoEscuro.mostrarErro(this, "Erro ao editar.");
                }
            });

            // Nota: O botão "Marcar como realizado" foi removido nesta versão para simplificar o layout visual,
            // mas a lógica dele seria similar aos botões acima se quiser reimplementar.
        }

        painelDireito.revalidate();
        painelDireito.repaint();
        montarCalendario(anoAtual, mesAtual);
    }

    private void abrirDialogCadastrarTreino(LocalDate startDate) {
        //String nomeTreino = JOptionPane.showInputDialog(this, "Nome do treino (ex: Peito, Costas, Perna):");
        String nomeTreino = DialogoEscuro.mostrarInput(this, "Nome do treino (ex: Peito, Costas, Perna):");
        if (nomeTreino == null || nomeTreino.isEmpty()) return;

        //String sFreq = JOptionPane.showInputDialog(this, "A cada quantos dias se repete? (2 a 7) — tempo de repetição por 4 semanas:");
        String sFreq = DialogoEscuro.mostrarInput(this, "A cada quantos dias se repete? (2 a 7):");

        int freq;
        try {
            freq = Integer.parseInt(sFreq);
            if (freq < 1 || freq > 7) throw new Exception();
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, "Frequência inválida!");
            DialogoEscuro.mostrarErro(this, "Frequência inválida!");
            return;
        }

        // definir cor conforme nome do treino
        Color cor;
        switch (nomeTreino.toLowerCase()) {
            case "peito": cor = new Color(52, 152, 219); break;
            case "costas": cor = new Color(231, 76, 60); break;
            case "perna": cor = new Color(155, 89, 182); break; // roxo
            case "ombro": cor = new Color(243, 156, 18); break;
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
                //JOptionPane.showMessageDialog(this, "Erro: já existe treino cadastrado em " + dt.toString() + ". Operação cancelada.");
                DialogoEscuro.mostrarErro(this, "Erro: já existe treino cadastrado em " + dt.toString() + ". Operação cancelada.");
                return;
            }
        }

        // tudo certo — salva para todas as datas
        for (LocalDate dt : datas) {
            TreinoDoDia t = new TreinoDoDia(nomeTreino, cor);
            RepositorioTreinos.salvarTreino(dt, t);
        }

        //JOptionPane.showMessageDialog(this, "Treino agendado com sucesso!");
        DialogoEscuro.mostrarMensagem(this, "Treino agendado com sucesso!");
        atualizarPainelDireito(startDate);
    }
}