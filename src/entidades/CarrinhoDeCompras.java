package entidades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Realiza operações de adicionar, remover, salvar e gerenciar quantidades.
public class CarrinhoDeCompras {
    private List<ItemCarrinho> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }

    public void adicionar(Produto produto) {
        for (ItemCarrinho itemExistente : itens) {
            if (itemExistente.getProduto().getId() == produto.getId()) {
                itemExistente.incrementarQuantidade();
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, 1));
    }

    public void removerPeloIndice(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
        }
    }

    public Optional<ItemCarrinho> getItemPeloIdProduto(int produtoId) {
        return itens.stream()
                    .filter(item -> item.getProduto().getId() == produtoId)
                    .findFirst();
    }
    
    /**
     * NOVO MÉTODO: Calcula o valor total de todos os itens no carrinho.
     * @return O valor total como um double.
     */
    public double getValorTotal() {
        double total = 0.0;
        for (ItemCarrinho item : this.itens) {
            total += item.getPrecoTotalItem();
        }
        return total;
    }

    public void atualizarQuantidade(int produtoId, int novaQuantidade) {
        Optional<ItemCarrinho> itemOpt = getItemPeloIdProduto(produtoId);
        if (itemOpt.isPresent()) {
            if (novaQuantidade > 0) {
                itemOpt.get().setQuantidade(novaQuantidade);
            } else {
                itens.removeIf(item -> item.getProduto().getId() == produtoId);
            }
        }
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void salvarParaArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (ItemCarrinho item : itens) {
                Produto p = item.getProduto();
                writer.write(p.getId() + "," + p.getNome() + "," + p.getPreco() + "," + item.getQuantidade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar carrinho: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists() || file.length() == 0) {
            return; // Inicia carrinho vazio se não houver arquivo
        }
        this.itens.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        String nome = partes[1].trim();
                        double preco = Double.parseDouble(partes[2].trim());
                        int quantidade = Integer.parseInt(partes[3].trim());
                        this.itens.add(new ItemCarrinho(new Produto(id, nome, preco), quantidade));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao parsear linha do arquivo carrinho (formato inválido): " + linha + " - " + e.getMessage());
                    }
                } else {
                     System.err.println("Linha do arquivo carrinho com formato inesperado (esperava 4 colunas): " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar carrinho: " + e.getMessage());
            e.printStackTrace();
        }
    }
}