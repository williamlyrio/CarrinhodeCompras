package entidade;

public class ItemCarrinho {
    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }

    public void incrementarQuantidade() {
        this.quantidade++;
    }

    public double getPrecoTotalItem() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) - R$%.2f", produto.getNome(), quantidade, getPrecoTotalItem());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemCarrinho that = (ItemCarrinho) obj;
        return produto.getId() == that.produto.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(produto.getId());
    }
}