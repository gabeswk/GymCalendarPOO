import ui.TelaBase; // Supondo que exista, ou pode ser removido se não usar
// Certifique-se de que RepositorioTreinos, TemaEscuro, TreinoDoDia, Exercicio, DialogoEscuro e Exec existem no seu projeto.

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class TelaHome extends JFrame {

    // Constantes para facilitar manutenção
    private static final Color[] PALETA_CORES = {
            new Color(52, 152, 219), new Color(231, 76, 60), new Color(155, 89, 182),
            new Color(46, 204, 113), new Color(241, 196, 15), new Color(230, 126, 34),
            new Color(26, 188, 156), new Color(232, 67, 147), new Color(52, 73, 94)
    };

    private final JPanel painelCalendario;
    private final JPanel painelDireito;
    private int anoAtual, mesAtual;
    private final String usuarioEmail;

    public TelaHome(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;

        // Configurações da Janela
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        } catch (Exception ignored) {} // Evita erro se imagem não existir

        setTitle("Agenda de Treinos - " + usuarioEmail);
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(TemaEscuro.FUNDO);

        // Inicialização dos Painéis
        painelCalendario = new JPanel(new BorderLayout());
        painelDireito = new JPanel();
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));

        configurarEstiloPainel(painelCalendario);
        configurarEstiloPainel(painelDireito);
        painelDireito.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Adiciona à janela
        add(painelCalendario, BorderLayout.WEST);
        add(criarScrollPersonalizado(painelDireito), BorderLayout.CENTER);

        // Data Inicial
        LocalDate hoje = LocalDate.now();
        anoAtual = hoje.getYear();
        mesAtual = hoje.getMonthValue();

        // Renderização Inicial
        montarCalendario(anoAtual, mesAtual);
        atualizarPainelDireito(null);
        setVisible(true);
    }

    // ==============================================================================================
    // LÓGICA DO CALENDÁRIO
    // ==============================================================================================

    private void montarCalendario(int ano, int mes) {
        painelCalendario.removeAll();

        // --- Topo (Navegação + Logout) ---
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(TemaEscuro.FUNDO);

        JPanel navegacao = new JPanel(new FlowLayout());
        navegacao.setBackground(TemaEscuro.FUNDO);

        JLabel lblMes = new JLabel(YearMonth.of(ano, mes).getMonth().toString() + " " + ano);
        TemaEscuro.aplicarLabel(lblMes);

        navegacao.add(criarNavBtn("<", e -> mudarMes(-1)));
        navegacao.add(lblMes);
        navegacao.add(criarNavBtn(">", e -> mudarMes(1)));

        JButton btnLogout = new JButton("Sair");
        TemaEscuro.aplicarBotao(btnLogout);
        btnLogout.setBackground(new Color(192, 57, 43));
        btnLogout.addActionListener(e -> realizarLogout());

        topo.add(navegacao, BorderLayout.CENTER);
        topo.add(btnLogout, BorderLayout.EAST);
        painelCalendario.add(topo, BorderLayout.NORTH);

        // --- Grid dos Dias ---
        JPanel dias = new JPanel(new GridLayout(0, 7, 5, 10));
        dias.setBackground(TemaEscuro.FUNDO);
        dias.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Cabeçalho da Semana
        String[] nomesSemana = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        for (String n : nomesSemana) {
            JLabel l = new JLabel(n, SwingConstants.CENTER);
            l.setForeground(TemaEscuro.BOTAO);
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dias.add(l);
        }

        // Espaços vazios antes do dia 1
        YearMonth ym = YearMonth.of(ano, mes);
        int diaSemanaPrimeiroDia = ym.atDay(1).getDayOfWeek().getValue() % 7;
        for (int i = 0; i < diaSemanaPrimeiroDia; i++) dias.add(new JLabel(""));

        // Botões dos dias
        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            LocalDate date = LocalDate.of(ano, mes, d);
            TreinoDoDia t = RepositorioTreinos.getTreino(usuarioEmail, date);
            dias.add(criarBotaoDia(d, t, date));
        }

        painelCalendario.add(dias, BorderLayout.CENTER);
        painelCalendario.revalidate();
        painelCalendario.repaint();
    }

    private JButton criarBotaoDia(int dia, TreinoDoDia treino, LocalDate date) {
        String textoBotao;

        if (treino != null) {
            String nomeExibicao = treino.getDescricao();
            if (nomeExibicao.length() > 6) nomeExibicao = nomeExibicao.substring(0, 5) + "..";
            // Formatação HTML otimizada
            textoBotao = String.format("<html><center><font size='-2'>%s</font><br><b>%d</b></center></html>", nomeExibicao, dia);
        } else {
            textoBotao = String.valueOf(dia);
        }

        JButton b = new JButton(textoBotao);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        b.setBackground(treino != null ? treino.getCor() : TemaEscuro.CAMPO);
        b.addActionListener(e -> atualizarPainelDireito(date));
        return b;
    }

    private void mudarMes(int delta) {
        mesAtual += delta;
        if (mesAtual < 1) { mesAtual = 12; anoAtual--; }
        else if (mesAtual > 12) { mesAtual = 1; anoAtual++; }
        montarCalendario(anoAtual, mesAtual);
    }

    // ==============================================================================================
    // PAINEL DE DETALHES (DIREITO)
    // ==============================================================================================

    private void atualizarPainelDireito(LocalDate dia) {
        painelDireito.removeAll();

        if (dia == null) {
            addLabel("Selecione um dia no calendário.", painelDireito);
        } else {
            addLabel("Dia: " + dia, painelDireito);
            painelDireito.add(Box.createVerticalStrut(20));

            TreinoDoDia treino = RepositorioTreinos.getTreino(usuarioEmail, dia);

            if (treino == null) {
                // Cenário: Sem treino no dia
                addLabel("Nenhum treino cadastrado.", painelDireito);
                addBtn("Agendar Treino", null, painelDireito, e -> abrirDialogCadastrarTreino(dia));
            } else {
                // Cenário: Com treino no dia
                montarDetalhesTreino(dia, treino);
            }
        }
        painelDireito.revalidate();
        painelDireito.repaint();
        // Atualiza calendário para refletir mudanças de cores/nomes
        montarCalendario(anoAtual, mesAtual);
    }

    private void montarDetalhesTreino(LocalDate dia, TreinoDoDia treino) {
        addLabel("Treino: " + treino.getDescricao(), painelDireito);
        painelDireito.add(Box.createVerticalStrut(10));

        // Botões de Ação do Treino
        addBtn("Editar Treino", new Color(52, 152, 219), painelDireito, e -> editarTreino(dia, treino));
        addBtn("Remover Treino", new Color(192, 57, 43), painelDireito, e -> removerTreino(dia));
        addBtn("Apagar todos os treinos iguais", new Color(136, 0, 21), painelDireito, e -> removerTodosTreinosIguais(treino));

        // Lista de Exercícios
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        treino.getExercicios().forEach(ex -> modeloLista.addElement(ex.toString()));

        JList<String> listaExercicios = new JList<>(modeloLista);
        listaExercicios.setBackground(TemaEscuro.CAMPO);
        listaExercicios.setForeground(TemaEscuro.TEXTO);
        listaExercicios.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollLista = new JScrollPane(listaExercicios);
        scrollLista.setPreferredSize(new Dimension(450, 200));
        scrollLista.setBorder(BorderFactory.createLineBorder(TemaEscuro.BORDA));
        painelDireito.add(scrollLista);
        painelDireito.add(Box.createVerticalStrut(15));

        // Botões de Ação dos Exercícios
        JPanel btnsExercicio = new JPanel();
        btnsExercicio.setLayout(new BoxLayout(btnsExercicio, BoxLayout.Y_AXIS));
        btnsExercicio.setBackground(TemaEscuro.FUNDO);

        addBtn("Adicionar Exercício", null, btnsExercicio, e -> acaoAdicionarExercicio(dia, treino));
        addBtn("Editar Selecionado", new Color(211, 84, 0), btnsExercicio, e -> acaoEditarExercicio(dia, treino, listaExercicios));
        addBtn("Remover Selecionado", new Color(192, 57, 43), btnsExercicio, e -> acaoRemoverExercicio(dia, treino, listaExercicios));

        painelDireito.add(btnsExercicio);
    }

    // ==============================================================================================
    // AÇÕES E LÓGICA DE NEGÓCIO
    // ==============================================================================================

    private void acaoAdicionarExercicio(LocalDate dia, TreinoDoDia treino) {
        try {
            String nome = DialogoEscuro.mostrarInput(this, "Nome:");
            if (nome == null || nome.isEmpty()) return;

            int s = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Séries:"));
            int r = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Reps:"));

            Exercicio novoEx = new Exercicio(nome, s, r);

            // 1. Adiciona no dia atual
            treino.adicionarExercicio(novoEx);

            // 2. Tenta propagar para outros dias
            propagarExercicio(dia, treino.getDescricao(), novoEx);

            atualizarPainelDireito(dia);
        } catch (Exception ex) {
            DialogoEscuro.mostrarErro(this, "Erro: valores inválidos.");
        }
    }

    private void propagarExercicio(LocalDate dataInicial, String nomeTreino, Exercicio modeloExercicio) {
        int op = JOptionPane.showConfirmDialog(this,
                "Deseja adicionar este exercício em todos os futuros treinos de \"" + nomeTreino + "\"?",
                "Replicar Exercício",
                JOptionPane.YES_NO_OPTION);

        if (op != JOptionPane.YES_OPTION) return;

        LocalDate dataCursor = dataInicial.plusDays(1);
        LocalDate dataLimite = dataInicial.plusMonths(6); // Busca pelos próximos 6 meses

        boolean alterouAlgo = false;

        while (dataCursor.isBefore(dataLimite)) {
            TreinoDoDia t = RepositorioTreinos.getTreino(usuarioEmail, dataCursor);

            if (t != null && t.getDescricao().equals(nomeTreino)) {
                // Cria cópia para garantir independência das referências
                Exercicio copia = new Exercicio(modeloExercicio.getNome(), modeloExercicio.getSeries(), modeloExercicio.getRepeticoes());
                t.adicionarExercicio(copia);
                RepositorioTreinos.salvarTreino(usuarioEmail, dataCursor, t);
                alterouAlgo = true;
            }
            dataCursor = dataCursor.plusDays(1);
        }

        if (alterouAlgo) {
            DialogoEscuro.mostrarMensagem(this, "Exercício replicado para treinos futuros!");
        } else {
            DialogoEscuro.mostrarMensagem(this, "Nenhum outro treino futuro encontrado para replicar.");
        }
    }

    private void acaoEditarExercicio(LocalDate dia, TreinoDoDia treino, JList<String> lista) {
        int idx = lista.getSelectedIndex();
        if (idx < 0) return;

        Exercicio ex = treino.getExercicios().get(idx);
        try {
            String novoNome = DialogoEscuro.mostrarInput(this, "Nome:", ex.getNome());
            if (novoNome != null) ex.setNome(novoNome);

            String novasSeries = DialogoEscuro.mostrarInput(this, "Séries:", String.valueOf(ex.getSeries()));
            if (novasSeries != null) ex.setSeries(Integer.parseInt(novasSeries));

            String novasReps = DialogoEscuro.mostrarInput(this, "Reps:", String.valueOf(ex.getRepeticoes()));
            if (novasReps != null) ex.setRepeticoes(Integer.parseInt(novasReps));

            atualizarPainelDireito(dia);
        } catch (Exception err) {
            DialogoEscuro.mostrarErro(this, "Erro ao editar.");
        }
    }

    private void acaoRemoverExercicio(LocalDate dia, TreinoDoDia treino, JList<String> lista) {
        if (lista.getSelectedIndex() >= 0) {
            treino.removerExercicio(lista.getSelectedIndex());
            atualizarPainelDireito(dia);
        }
    }

    private void abrirDialogCadastrarTreino(LocalDate start) {
        String nome = DialogoEscuro.mostrarInput(this, "Nome do treino (ex: Peito, A, B):");
        if (nome == null || nome.isEmpty()) return;

        try {
            int freq = Integer.parseInt(DialogoEscuro.mostrarInput(this, "Repetir a cada quantos dias (2-7)?"));
            if (freq < 1 || freq > 7) throw new Exception("Frequência inválida");

            Color cor = PALETA_CORES[Math.abs(nome.hashCode()) % PALETA_CORES.length];

            List<LocalDate> datasParaSalvar = new ArrayList<>();
            // Gera datas para 4 semanas
            for (LocalDate d = start; !d.isAfter(start.plusDays(28)); d = d.plusDays(freq)) {
                datasParaSalvar.add(d);
            }

            // Verifica conflitos antes de salvar
            for (LocalDate dt : datasParaSalvar) {
                if (RepositorioTreinos.existeTreino(usuarioEmail, dt)) {
                    DialogoEscuro.mostrarErro(this, "Conflito: Já existe treino em " + dt);
                    return;
                }
            }

            // Salva
            for (LocalDate dt : datasParaSalvar) {
                RepositorioTreinos.salvarTreino(usuarioEmail, dt, new TreinoDoDia(nome, cor));
            }

            DialogoEscuro.mostrarMensagem(this, "Agendado com sucesso!");
            atualizarPainelDireito(start);

        } catch (Exception e) {
            DialogoEscuro.mostrarErro(this, "Dados inválidos! " + e.getMessage());
        }
    }

    private void editarTreino(LocalDate dia, TreinoDoDia treino) {
        String novoNome = DialogoEscuro.mostrarInput(this, "Editar nome do treino:", treino.getDescricao());
        if (novoNome != null && !novoNome.isEmpty()) {
            treino.setDescricao(novoNome);
            DialogoEscuro.mostrarMensagem(this, "Treino atualizado!");
            atualizarPainelDireito(dia);
        }
    }

    private void removerTreino(LocalDate dia) {
        int op = JOptionPane.showConfirmDialog(this, "Remover o treino deste dia?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            RepositorioTreinos.removerTreino(usuarioEmail, dia);
            atualizarPainelDireito(null);
        }
    }

    private void removerTodosTreinosIguais(TreinoDoDia treino) {
        int op = JOptionPane.showConfirmDialog(this,
                "Apagar TODOS os treinos com o nome:\n\"" + treino.getDescricao() + "\"?",
                "Confirmação Perigosa",
                JOptionPane.YES_NO_OPTION);

        if (op == JOptionPane.YES_OPTION) {
            RepositorioTreinos.removerTodosTreinosIguais(usuarioEmail, treino.getDescricao());
            atualizarPainelDireito(null);
        }
    }

    private void realizarLogout() {
        int op = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            dispose();
            Exec.abrirTelaInicial();
        }
    }

    // ==============================================================================================
    // HELPERS E UTILITÁRIOS VISUAIS
    // ==============================================================================================

    private void configurarEstiloPainel(JPanel p) {
        p.setBackground(TemaEscuro.FUNDO);
        p.setPreferredSize(new Dimension(500, 650));
    }

    private JScrollPane criarScrollPersonalizado(Component view) {
        JScrollPane sc = new JScrollPane(view);
        sc.setBorder(null);
        sc.getViewport().setBackground(TemaEscuro.FUNDO);
        sc.getVerticalScrollBar().setUI(new CustomScrollBarUI()); // Usando UI Customizada interna ou externa
        sc.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
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
        TemaEscuro.aplicarBotao(b);
        if (bg != null) b.setBackground(bg);
        b.addActionListener(acao);
        p.add(b);
        p.add(Box.createVerticalStrut(10));
    }

    // Classe interna para scrollbar customizada (caso não venha de fora)
    static class CustomScrollBarUI extends BasicScrollBarUI {
        @Override protected void configureScrollBarColors() { this.thumbColor = new Color(100, 100, 100); }
        @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
        private JButton createZeroButton() {
            JButton jbutton = new JButton();
            jbutton.setPreferredSize(new Dimension(0, 0));
            return jbutton;
        }
    }
}