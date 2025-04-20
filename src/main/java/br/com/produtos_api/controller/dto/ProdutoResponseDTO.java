package br.com.produtos_api.controller.dto;

import java.time.OffsetDateTime;

public record ProdutoResponseDTO(

        Long id,
        String nome,
        String descricao,
        Double preco,
        Double quantidadeEstoque,
        OffsetDateTime dataCadastro
) {
}
