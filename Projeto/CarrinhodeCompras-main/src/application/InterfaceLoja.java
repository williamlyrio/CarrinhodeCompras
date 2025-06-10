package application;

import entidade.Produto;
import entidade.CarrinhoDeCompras;
import entidade.ItemCarrinho;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

//Interface gráfica carrinho de compras.
public class InterfaceLoja {
    private JFrame frame;
    private JList<Produto> listaProdutos;
    private DefaultListModel<Produto> modeloProdutos;
    private JList<ItemCarrinho> listaCarrinho;
    private DefaultListModel<ItemCarrinho> modeloCarrinho;
    private CarrinhoDeCompras carrinho;
    private List<Produto> listaOriginal;
    private JLabel labelTotalCarrinho;

    public InterfaceLoja() {
        carrinho = new CarrinhoDeCompras();
        int opcao = JOptionPane.showConfirmDialog(
                null,
                "Deseja carregar o carrinho salvo?",
                "Carregar Carrinho",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            carrinho.carregarDeArquivo("carrinho.txt"); //
            if (!carrinho.getItens().isEmpty()) {
                gerarRelatorioConsole("CARRINHO CARREGADO DO ARQUIVO");
            }
        }

        frame = new JFrame("Interface Loja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // --- PAINEL ESQUERDO (Lista de Produtos) ---
        modeloProdutos = new DefaultListModel<>();
        listaProdutos = new JList<>(modeloProdutos);
        JScrollPane scrollProdutos = new JScrollPane(listaProdutos);

        JTextField campoBusca = new JTextField(15);
        JButton botaoBuscar = new JButton("Buscar Produto");
        JPanel painelBusca = new JPanel();
        painelBusca.add(new JLabel("Buscar (Nome ou ID):"));
        painelBusca.add(campoBusca);
        painelBusca.add(botaoBuscar);

        JButton botaoAdicionar = new JButton("Adicionar ao Carrinho");
        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.add(painelBusca, BorderLayout.NORTH);
        painelEsquerdo.add(scrollProdutos, BorderLayout.CENTER);
        painelEsquerdo.add(botaoAdicionar, BorderLayout.SOUTH);

        // --- PAINEL DIREITO (Carrinho) ---
        modeloCarrinho = new DefaultListModel<>();
        listaCarrinho = new JList<>(modeloCarrinho);
        JScrollPane scrollCarrinho = new JScrollPane(listaCarrinho);

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.add(new JLabel("Carrinho"), BorderLayout.NORTH);
        painelDireito.add(scrollCarrinho, BorderLayout.CENTER);

        // --- PAINEL SUL DIREITO (Botões do Carrinho e Total) ---
        JButton botaoRemover = new JButton("Remover Selecionado");
        JButton botaoEditarQuantidade = new JButton("Editar Quantidade");
        JButton botaoSalvar = new JButton("Salvar Carrinho");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoEditarQuantidade);
        painelBotoes.add(botaoSalvar);

        labelTotalCarrinho = new JLabel("Total: R$ 0.00", SwingConstants.CENTER);
        labelTotalCarrinho.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel painelSulDireito = new JPanel(new BorderLayout());
        painelSulDireito.add(painelBotoes, BorderLayout.NORTH);
        painelSulDireito.add(labelTotalCarrinho, BorderLayout.SOUTH);

        painelDireito.add(painelSulDireito, BorderLayout.SOUTH);

        frame.add(painelEsquerdo, BorderLayout.WEST);
        frame.add(painelDireito, BorderLayout.CENTER);

        // --- Carregamento dos Produtos e Action Listeners ---
        Produto[] produtos = { //
            new Produto(1, "Feijão", 5.50), //
            new Produto(2, "Arroz", 4.20), //
            new Produto(3, "Café", 8.30), //
            new Produto(4, "Leite", 3.00), //
            new Produto(5, "Pão", 2.50), //
            new Produto(6, "Carne K/G", 31.00), //
            new Produto(7, "Queijo", 2.50), //
            new Produto(8, "Refrigerante", 5.50), //
            new Produto(9, "Chocolate", 7.00), //
            new Produto(10, "Batata K/G", 5.40) //
        };
        listaOriginal = Arrays.asList(produtos);

        for (Produto p : listaOriginal) {
            modeloProdutos.addElement(p);
        }

        atualizarListaCarrinhoVisual();

        botaoAdicionar.addActionListener(e -> {
            Produto produtoSelecionado = listaProdutos.getSelectedValue();
            if (produtoSelecionado != null) {
                carrinho.adicionar(produtoSelecionado); //
                atualizarListaCarrinhoVisual();
                gerarRelatorioConsole("ITEM ADICIONADO");
            }
        });

        botaoRemover.addActionListener(e -> {
            ItemCarrinho itemSelecionado = listaCarrinho.getSelectedValue();
            if (itemSelecionado != null) {
                carrinho.removerPeloIndice(listaCarrinho.getSelectedIndex()); //
                atualizarListaCarrinhoVisual();
                gerarRelatorioConsole("ITEM REMOVIDO");
            }
        });

        botaoEditarQuantidade.addActionListener(e -> {
            ItemCarrinho itemSelecionado = listaCarrinho.getSelectedValue();
            if (itemSelecionado != null) {
                String qtdeAtualStr = String.valueOf(itemSelecionado.getQuantidade());
                String novaQtdeStr = JOptionPane.showInputDialog(frame, "Digite a nova quantidade para: " + itemSelecionado.getProduto().getNome(), qtdeAtualStr);

                if (novaQtdeStr != null && !novaQtdeStr.trim().isEmpty()) {
                    try {
                        int novaQuantidade = Integer.parseInt(novaQtdeStr.trim());
                        carrinho.atualizarQuantidade(itemSelecionado.getProduto().getId(), novaQuantidade); //
                        atualizarListaCarrinhoVisual();
                        gerarRelatorioConsole("QUANTIDADE EDITADA");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        botaoSalvar.addActionListener(e -> {
            carrinho.salvarParaArquivo("carrinho.txt"); //
            JOptionPane.showMessageDialog(frame, "Carrinho salvo com sucesso!");
            System.out.println("\n>> Carrinho salvo no arquivo 'carrinho.txt' <<\n");
        });

        botaoBuscar.addActionListener(e -> {
            String termo = campoBusca.getText().trim();
            modeloProdutos.clear(); //

            if (termo.isEmpty()) {
                for (Produto p : listaOriginal) modeloProdutos.addElement(p);
                return;
            }

            try {
                int idBusca = Integer.parseInt(termo);
                listaOriginal.stream()
                        .filter(p -> p.getId() == idBusca) //
                        .forEach(modeloProdutos::addElement);
            } catch (NumberFormatException ex) {
                String termoLower = termo.toLowerCase();
                listaOriginal.stream()
                        .filter(p -> p.getNome().toLowerCase().contains(termoLower)) //
                        .forEach(modeloProdutos::addElement);
            }
        });

        frame.setVisible(true);
    }

    private void atualizarListaCarrinhoVisual() {
        modeloCarrinho.clear(); //
        double totalGeral = carrinho.getValorTotal();

        for (ItemCarrinho item : carrinho.getItens()) { //
            modeloCarrinho.addElement(item); //
        }

        labelTotalCarrinho.setText(String.format("Total: R$ %.2f", totalGeral));
    }

    /**
     * Gera e imprime no console um relatório detalhado do estado atual do carrinho.
     * @param motivo Ação que disparou a geração do relatório.
     */
    private void gerarRelatorioConsole(String motivo) {
        System.out.println("\n==============================================");
        System.out.println("      RELATORIO DO CARRINHO DE COMPRAS      ");
        System.out.println("      " + motivo);
        System.out.println("==============================================");

        List<ItemCarrinho> itensDoCarrinho = carrinho.getItens(); //

        if (itensDoCarrinho.isEmpty()) {
            System.out.println("O carrinho está vazio.");
        } else {
            System.out.printf("%-20s | %s | %-12s | %s%n", "PRODUTO", "QTD", "PRECO UNIT.", "SUBTOTAL");
            System.out.println("----------------------------------------------------------------");
            for (ItemCarrinho item : itensDoCarrinho) {
                Produto p = item.getProduto(); //
                System.out.printf("%-20s | %-3d | R$ %-10.2f | R$ %.2f%n",
                        p.getNome(), item.getQuantidade(), p.getPreco(), item.getPrecoTotalItem());
            }
            System.out.println("----------------------------------------------------------------");
            System.out.printf("Valor Total do Carrinho: R$ %.2f%n", carrinho.getValorTotal());
        }
        System.out.println("==============================================\n");
    }
}