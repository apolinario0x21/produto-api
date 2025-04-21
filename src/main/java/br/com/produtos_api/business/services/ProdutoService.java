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

        if (dto == null) {
            throw new ServerException("Produto não pode ser nulo.");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new InvalidValueException("Nome do produto não pode ser nulo ou vazio.");
        }

        if (dto.descricao() == null || dto.descricao().isBlank()) {
            throw new InvalidValueException("Descrição do produto não pode ser nula ou vazia.");
        }

        if (dto.preco() == null || dto.quantidadeEstoque() == null) {
            throw new InvalidValueException("Preço e/ou Quantidade não pode ser nula.");
        }

        if (dto.preco().compareTo(BigDecimal.ZERO) <= 0 || dto.quantidadeEstoque() < 0) {
            throw new InvalidValueException("Preço e/ou quantidade em estoque devem ser maiores que zero.");
        }

        if (dto.quantidadeEstoque() >= 10000) {
            throw new InvalidValueException("Quantidade em estoque não pode ser maior que 10.000.");
        }

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

    public ProdutoResponseDTO findById(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        return toDTO(produto);
    }

    public void updateProduto(Long id, ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco().setScale(2, RoundingMode.HALF_UP));
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        produtoRepository.save(produto);

        if (dto.preco().compareTo(BigDecimal.ZERO) < 0 || dto.quantidadeEstoque() < 0) {

            throw new InvalidValueException("Atualização inválida.");
        }

        if (dto.quantidadeEstoque() >= 10000) {
            throw new InvalidValueException("Quantidade em estoque não pode ser maior que 10.000.");
        }

        if (dto == null) {
            throw new ServerException("Produto não pode ser nulo.");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new InvalidValueException("Nome do produto não pode ser nulo ou vazio.");
        }

        if (dto.descricao() == null || dto.descricao().isBlank()) {
            throw new InvalidValueException("Descrição do produto não pode ser nula ou vazia.");
        }
    }

    public void deleteProduto(Long id) {

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
