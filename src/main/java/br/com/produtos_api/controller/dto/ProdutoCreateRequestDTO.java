package br.com.produtos_api.controller.dto;

import java.time.OffsetDateTime;

public record ProdutoCreateRequestDTO(
        String nome,
        String descricao,
        Double preco,
        Double quantidadeEstoque
) {
}
