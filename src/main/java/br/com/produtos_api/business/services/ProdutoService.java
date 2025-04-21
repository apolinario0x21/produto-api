package br.com.produtos_api.business.services;

import br.com.produtos_api.controller.dto.ProdutoRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoResponseDTO;

import br.com.produtos_api.infrastructure.entities.Produto;
import br.com.produtos_api.infrastructure.exceptions.InvalidValueException;
import br.com.produtos_api.infrastructure.exceptions.ListException;
import br.com.produtos_api.infrastructure.exceptions.ProductNotFound;
import br.com.produtos_api.infrastructure.exceptions.ServerException;
import br.com.produtos_api.infrastructure.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO dto) {

        Produto produto = Produto.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco().setScale(2, RoundingMode.HALF_UP))
                .quantidadeEstoque(dto.quantidadeEstoque())
                .dataCadastro(OffsetDateTime.now())
                .build();

        Produto salvo = produtoRepository.save(produto);

        return toDTO(salvo);
    }

    public List<ProdutoResponseDTO> findAllProdutos() {

        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new ListException("Nenhum produto cadastrado.");
        }

        return produtos.stream().map(this::toDTO).toList();
    }

    public ProdutoResponseDTO findById(UUID id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        return toDTO(produto);
    }

    public void updateProduto(UUID id, ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco().setScale(2, RoundingMode.HALF_UP));
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        produtoRepository.save(produto);
    }

    public void deleteProduto(UUID id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO toDTO(Produto produto) {

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getDataCadastro()
        );
    }
}
