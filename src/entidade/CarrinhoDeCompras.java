
package entidade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia os itens adicionados ao carrinho de compras e permite persistência.
 */
public class CarrinhoDeCompras {
    private List<Produto> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }

    public void adicionar(Produto p) {
        itens.add(p);
    }

    public void remover(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
        }
    }

    public List<Produto> getItens() {
        return itens;
    }

    public void salvarParaArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Produto p : itens) {
                writer.write(p.getId() + "," + p.getNome() + "," + p.getPreco());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double preco = Double.parseDouble(partes[2]);
                    itens.add(new Produto(id, nome, preco));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
