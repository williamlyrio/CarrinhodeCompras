
package application;

import entidade.Produto;
import entidade.CarrinhoDeCompras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Interface gráfica para interação com o carrinho de compras.
 */
public class InterfaceLoja {
    private JFrame frame;
    private JList<Produto> listaProdutos;
    private DefaultListModel<Produto> modeloProdutos;
    private JList<Produto> listaCarrinho;
    private DefaultListModel<Produto> modeloCarrinho;
    private CarrinhoDeCompras carrinho;
    private List<Produto> listaOriginal;

    public InterfaceLoja() {
        carrinho = new CarrinhoDeCompras();
        carrinho.carregarDeArquivo("carrinho.txt");

        frame = new JFrame("Interface Loja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setLayout(new BorderLayout());

        modeloProdutos = new DefaultListModel<>();
        modeloCarrinho = new DefaultListModel<>();

        listaProdutos = new JList<>(modeloProdutos);
        listaCarrinho = new JList<>(modeloCarrinho);

        JScrollPane scrollProdutos = new JScrollPane(listaProdutos);
        JScrollPane scrollCarrinho = new JScrollPane(listaCarrinho);

        
        JTextField campoBusca = new JTextField(15);
        JButton botaoBuscar = new JButton("Buscar Produto");

        JPanel painelBusca = new JPanel();
        painelBusca.add(new JLabel("Buscar:"));
        painelBusca.add(campoBusca);
        painelBusca.add(botaoBuscar);

        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.add(painelBusca, BorderLayout.NORTH);
        painelEsquerdo.add(scrollProdutos, BorderLayout.CENTER);

        JButton botaoAdicionar = new JButton("Adicionar ao Carrinho");
        painelEsquerdo.add(botaoAdicionar, BorderLayout.SOUTH);

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.add(new JLabel("Carrinho"), BorderLayout.NORTH);
        painelDireito.add(scrollCarrinho, BorderLayout.CENTER);

        JButton botaoRemover = new JButton("Remover Selecionado");
        JButton botaoSalvar = new JButton("Salvar Carrinho");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoSalvar);

        painelDireito.add(painelBotoes, BorderLayout.SOUTH);

        frame.add(painelEsquerdo, BorderLayout.WEST);
        frame.add(painelDireito, BorderLayout.CENTER);

        Produto[] produtos = {
            new Produto(1, "Feijão", 5.50),
            new Produto(2, "Arroz", 4.20),
            new Produto(3, "Café", 8.30),
            new Produto(4, "Leite", 3.00),
            new Produto(5, "Pão", 2.50),
            new Produto(6, "Carne K/G", 31.00),
            new Produto (7, "Queijo", 2.50),
            new Produto (8, "Refrigerante", 5.50),
            new Produto (9, "Chocolate", 7.00),
            new Produto (10, "Batata K/G", 5.40)
        };

        listaOriginal = Arrays.asList(produtos);

        for (Produto p : listaOriginal) {
            modeloProdutos.addElement(p);
        }

        for (Produto p : carrinho.getItens()) {
            modeloCarrinho.addElement(p);
        }

        botaoAdicionar.addActionListener(e -> {
            Produto selecionado = listaProdutos.getSelectedValue();
            if (selecionado != null) {
                carrinho.adicionar(selecionado);
                modeloCarrinho.addElement(selecionado);
            }
        });

        botaoRemover.addActionListener(e -> {
            int index = listaCarrinho.getSelectedIndex();
            if (index != -1) {
                carrinho.remover(index);
                modeloCarrinho.remove(index);
            }
        });

        botaoSalvar.addActionListener(e -> {
            carrinho.salvarParaArquivo("carrinho.txt");
            JOptionPane.showMessageDialog(frame, "Carrinho salvo com sucesso!");
        });

        botaoBuscar.addActionListener(e -> {
            String termo = campoBusca.getText().toLowerCase();
            modeloProdutos.clear();
            for (Produto p : listaOriginal) {
                if (p.getNome().toLowerCase().contains(termo)) {
                    modeloProdutos.addElement(p);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceLoja::new);
    }
}
