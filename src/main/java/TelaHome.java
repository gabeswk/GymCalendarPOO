import ui.TelaBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class TelaHome extends JFrame {

    private JPanel painelCalendario, painelDireito;
    private int anoAtual, mesAtual;
    private String usuarioEmail;

    public TelaHome(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        setTitle("Agenda de Treinos - " + usuarioEmail);
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(TemaEscuro.FUNDO);

        painelCalendario = new JPanel(new BorderLayout());
        painelDireito = new JPanel();
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));

        configurarPainel(painelCalendario);
        configurarPainel(painelDireito);
        painelDireito.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(painelCalendario, BorderLayout.WEST);
        add(criarScrollPersonalizado(painelDireito), BorderLayout.CENTER);

        LocalDate hoje = LocalDate.now();
        anoAtual = hoje.getYear();
        mesAtual = hoje.getMonthValue();

        montarCalendario(anoAtual, mesAtual);
        atualizarPainelDireito(null);
        setVisible(true);
    }

    private void montarCalendario(int ano, int mes) {
        painelCalendario.removeAll();
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(TemaEscuro.FUNDO);

        // navegação existente
        JPanel navegacao = new JPanel(new FlowLayout());
        navegacao.setBackground(TemaEscuro.FUNDO);

        JLabel lblMes = new JLabel(YearMonth.of(ano, mes).getMonth() + " " + ano);
        TemaEscuro.aplicarLabel(lblMes);

        navegacao.add(criarNavBtn("<", e -> mudarMes(-1)));
        navegacao.add(lblMes);
        navegacao.add(criarNavBtn(">", e -> mudarMes(1)));

        // botão logout
        JButton btnLogout = new JButton("Sair");
        TemaEscuro.aplicarBotaoLogout(btnLogout);
        btnLogout.setBackground(TemaEscuro.BOTAO); // Define cor azul padrão
        btnLogout.addActionListener(e -> realizarLogout());

        // Painel para adicionar margem ao botão
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.setBackground(TemaEscuro.FUNDO);
        painelBotao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margem
        painelBotao.add(btnLogout);

        // montagem final
        topo.add(navegacao, BorderLayout.CENTER);
        topo.add(painelBotao, BorderLayout.EAST);

        painelCalendario.add(topo, BorderLayout.NORTH);

        // Aumentei um pouco o gap vertical (segundo parâmetro '5' para '10') para caber
        // melhor o texto duplo
        JPanel dias = new JPanel(new GridLayout(0, 7, 5, 10));
        dias.setBackground(TemaEscuro.FUNDO);
        dias.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] nomes = { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" };
        for (String n : nomes) {
            JLabel l = new JLabel(n, SwingConstants.CENTER);
            l.setForeground(TemaEscuro.BOTAO);
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dias.add(l);
        }

        YearMonth ym = YearMonth.of(ano, mes);
        int offset = ym.atDay(1).getDayOfWeek().getValue() % 7;
        for (int i = 0; i < offset; i++)
            dias.add(new JLabel(""));

        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            LocalDate date = LocalDate.of(ano, mes, d);
            TreinoDoDia t = RepositorioTreinos.getTreino(usuarioEmail, date);

            String textoBotao;
            if (t != null) {
                // Lógica para mostrar nome acima do dia usando HTML
                String nomeExibicao = t.getDescricao();
                // Corta nomes muito longos para não quebrar o layout
                if (nomeExibicao.length() > 6) {
                    nomeExibicao = nomeExibicao.substring(0, 5) + "..";
                }
                // HTML para duas linhas: Nome pequeno em cima, dia em negrito embaixo
                textoBotao = "<html><center><font size='-2'>" + nomeExibicao + "</font><br><b>" + d
                        + "</b></center></html>";
            } else {
                textoBotao = String.valueOf(d);
            }

            JButton b = new JButton(textoBotao);
            // Margem zero para aproveitar o espaço do botão
            b.setMargin(new Insets(0, 0, 0, 0));
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setForeground(Color.WHITE);
            // Usar PLAIN para que o <b> do HTML funcione corretamente
            b.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            b.setBackground(t != null ? t.getCor() : TemaEscuro.CAMPO);
            b.addActionListener(e -> atualizarPainelDireito(date));
            dias.add(b);
        }
        painelCalendario.add(dias, BorderLayout.CENTER);
        painelCalendario.revalidate();
        painelCalendario.repaint();
    }

    private void mudarMes(int delta) {
        mesAtual += delta;
        if (mesAtual < 1) {
            mesAtual = 12;
            anoAtual--;
        } else if (mesAtual > 12) {
            mesAtual = 1;
            anoAtual++;
        }
        montarCalendario(anoAtual, mesAtual);
    }

    private void atualizarPainelDireito(LocalDate dia) {
        painelDireito.removeAll();
        if (dia == null) {
            addLabel("Selecione um dia no calendário.", painelDireito);
        } else {
            addLabel("Dia: " + dia, painelDireito);
            painelDireito.add(Box.createVerticalStrut(20));

            TreinoDoDia treino = RepositorioTreinos.getTreino(usuarioEmail, dia);
            if (treino == null) {
                addLabel("Nenhum treino cadastrado.", painelDireito);
                addBtn("Agendar Treino", null, painelDireito, e -> abrirDialogCadastrarTreino(dia));
            } else {
                addLabel("Treino: " + treino.getDescricao(), painelDireito);
                painelDireito.add(Box.createVerticalStrut(10));

                addBtn("Editar Treino", new Color(52, 152, 219), painelDireito,
                        e -> editarTreino(dia, treino));

                addBtn("Remover Treino", new Color(192, 57, 43), painelDireito,
                        e -> removerTreino(dia));

                addBtn("Limpar Tudo",
                        new Color(136, 0, 21),
                        painelDireito,
                        e -> removerTodosTreinosIguais(treino));

                DefaultListModel<String> modelo = new DefaultListModel<>();
                treino.getExercicios().forEach(ex -> modelo.addElement(ex.toString()));
                JList<String> lista = new JList<>(modelo);
                lista.setBackground(TemaEscuro.CAMPO);
                lista.setForeground(TemaEscuro.TEXTO);
                lista.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                JScrollPane scroll = new JScrollPane(lista);
                scroll.setPreferredSize(new Dimension(450, 200));
                scroll.setBorder(BorderFactory.createLineBorder(TemaEscuro.BORDA));
                painelDireito.add(scroll);
                painelDireito.add(Box.createVerticalStrut(15));

                JPanel btns = new JPanel();
                btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
                btns.setBackground(TemaEscuro.FUNDO);

                addBtn("Adicionar Exercício", null, btns, e -> {
                    try {
                        String nome = DialogoEscuro.mostrarInput(this, "Nome:");
                        if (nome == null || nome.isEmpty())
                            return;
                        int s = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Séries:"));
                        int r = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Reps:"));
                        treino.adicionarExercicio(new Exercicio(nome, s, r));
                        atualizarPainelDireito(dia);
                    } catch (Exception ex) {
                        DialogoEscuro.mostrarErro(this, "Erro: valores inválidos.");
                    }
                });

                addBtn("Editar Selecionado", new Color(211, 84, 0), btns, e -> {
                    int idx = lista.getSelectedIndex();
                    if (idx < 0)
                        return;
                    Exercicio ex = treino.getExercicios().get(idx);
                    try {
                        ex.setNome(DialogoEscuro.mostrarInput(this, "Nome:", ex.getNome()));
                        ex.setSeries(Integer
                                .parseInt(DialogoEscuro.mostrarInput(this, "Séries:", String.valueOf(ex.getSeries()))));
                        ex.setRepeticoes(Integer.parseInt(
                                DialogoEscuro.mostrarInput(this, "Reps:", String.valueOf(ex.getRepeticoes()))));
                        atualizarPainelDireito(dia);
                    } catch (Exception err) {
                        DialogoEscuro.mostrarErro(this, "Erro ao editar.");
                    }
                });

                addBtn("Remover Selecionado", new Color(192, 57, 43), btns, e -> {
                    if (lista.getSelectedIndex() >= 0) {
                        treino.removerExercicio(lista.getSelectedIndex());
                        atualizarPainelDireito(dia);
                    }
                });
                painelDireito.add(btns);
            }
        }
        painelDireito.revalidate();
        painelDireito.repaint();
        montarCalendario(anoAtual, mesAtual);
    }

    private void abrirDialogCadastrarTreino(LocalDate start) {
        String nome = DialogoEscuro.mostrarInput(this, "Nome do treino (ex: Peito, A, B):");
        if (nome == null || nome.isEmpty())
            return;
        try {
            int freq = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Repetir a cada quantos dias (2-7)?"));
            if (freq < 1 || freq > 7)
                throw new Exception();

            // Lógica de Cores Automática
            Color[] paleta = {
                    new Color(52, 152, 219), new Color(231, 76, 60), new Color(155, 89, 182),
                    new Color(46, 204, 113), new Color(241, 196, 15), new Color(230, 126, 34),
                    new Color(26, 188, 156), new Color(232, 67, 147), new Color(52, 73, 94)
            };
            Color cor = paleta[Math.abs(nome.hashCode()) % paleta.length];

            java.util.List<LocalDate> datas = new ArrayList<>();
            for (LocalDate d = start; !d.isAfter(start.plusDays(28)); d = d.plusDays(freq))
                datas.add(d);

            for (LocalDate dt : datas)
                if (RepositorioTreinos.existeTreino(usuarioEmail, dt)) {
                    DialogoEscuro.mostrarErro(this, "Conflito em " + dt);
                    return;
                }
            for (LocalDate dt : datas)
                RepositorioTreinos.salvarTreino(usuarioEmail, dt, new TreinoDoDia(nome, cor));

            DialogoEscuro.mostrarMensagem(this, "Agendado!");
            atualizarPainelDireito(start);
        } catch (Exception e) {
            DialogoEscuro.mostrarErro(this, "Dados inválidos!");
        }
    }

    // --- Helpers ---
    private void configurarPainel(JPanel p) {
        p.setBackground(TemaEscuro.FUNDO);
        p.setPreferredSize(new Dimension(500, 650));
    }

    private void editarTreino(LocalDate dia, TreinoDoDia treino) {
        String novoNome = DialogoEscuro.mostrarInput(
                this,
                "Editar nome do treino:",
                treino.getDescricao());

        if (novoNome == null || novoNome.isEmpty())
            return;

        treino.setDescricao(novoNome);

        DialogoEscuro.mostrarMensagem(this, "Treino atualizado!");
        atualizarPainelDireito(dia);
    }

    private void removerTreino(LocalDate dia) {
        boolean confirmou = DialogoEscuro.mostrarConfirmacao(
                this,
                "Remover o treino deste dia?");

        if (confirmou) {
            RepositorioTreinos.removerTreino(usuarioEmail, dia);
            atualizarPainelDireito(null);
        }
    }

    private void removerTodosTreinosIguais(TreinoDoDia treino) {
        boolean confirmou = DialogoEscuro.mostrarConfirmacao(
                this,
                "Apagar TODOS os treinos com o nome:\n\"" + treino.getDescricao() + "\"?");

        if (confirmou) {
            RepositorioTreinos.removerTodosTreinosIguais(usuarioEmail, treino.getDescricao());
            atualizarPainelDireito(null);
        }
    }

    private void realizarLogout() {
        boolean confirmou = DialogoEscuro.mostrarConfirmacao(
                this,
                "Deseja realmente sair?");

        if (confirmou) {
            dispose();
            Exec.abrirTelaInicial();
        }
    }

    // Na classe TelaHome.java
    private JScrollPane criarScrollPersonalizado(Component view) {
        JScrollPane sc = new JScrollPane(view);
        sc.setBorder(null);
        sc.getViewport().setBackground(TemaEscuro.FUNDO);

        // --- Barra Vertical ---
        sc.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        sc.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0)); // Largura 12px

        // --- Barra Horizontal (ADICIONAR ISSO) ---
        sc.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        sc.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 12)); // Altura 12px

        return sc;
    }

    private JButton criarNavBtn(String txt, java.awt.event.ActionListener acao) {
        JButton b = new JButton(txt);
        b.setBackground(TemaEscuro.BOTAO);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.addActionListener(acao);
        return b;
    }

    private void addLabel(String txt, JPanel p) {
        JLabel l = new JLabel(txt);
        TemaEscuro.aplicarLabel(l);
        p.add(l);
    }

    private void addBtn(String txt, Color bg, JPanel p, java.awt.event.ActionListener acao) {
        JButton b = new JButton(txt);

        if (bg != null) {
            // Se tem cor personalizada, usa o método colorido
            TemaEscuro.aplicarBotaoColorido(b, bg);
        } else {
            // Se não tem cor, usa o padrão azul
            TemaEscuro.aplicarBotao(b);
        }

        b.addActionListener(acao);
        p.add(b);
        p.add(Box.createVerticalStrut(10));
    }

}