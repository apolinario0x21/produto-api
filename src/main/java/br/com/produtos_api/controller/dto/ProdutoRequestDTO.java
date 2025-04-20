package br.com.produtos_api.controller.dto;

public record ProdutoRequestDTO(

        String nome,
        String descricao,
        Double preco,
        Double quantidadeEstoque
) {
}
