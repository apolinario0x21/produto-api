package br.com.produtos_api.controller.dto;

public record ProdutoCreateRequestDTO(

        String nome,
        String descricao,
        Double preco,
        Double quantidadeEstoque
) {
}
