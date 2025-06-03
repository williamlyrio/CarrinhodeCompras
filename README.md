
# Carrinho de Compras - Interface Loja

Este é um projeto Java com interface gráfica Swing para gerenciar um carrinho de compras com produtos alimentícios.

## Funcionalidades

- Lista de produtos: Feijão, Arroz, Café, Leite, Pão.
- Adicionar produtos ao carrinho.
- Remover itens do carrinho.
- Persistência de dados:
  - O carrinho é carregado automaticamente do arquivo `carrinho.txt`, se existir.
  - Botão "Salvar Carrinho" para armazenar os itens atuais no arquivo.

## Estrutura de Arquivos

- `Produto.java`: Representa um produto do mercado.
- `CarrinhoDeCompras.java`: Gerencia os itens e realiza a persistência (implementado na mesma classe do projeto).
- `InterfaceLoja.java`: Interface gráfica principal com funcionalidades de interação.

## Execução

Compile e execute o projeto com uma IDE compatível com projetos Java Swing, como o NetBeans.

```bash
javac InterfaceLoja.java
java InterfaceLoja
```

