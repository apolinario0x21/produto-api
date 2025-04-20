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

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO addProdutos(ProdutoRequestDTO dto) {

        if (dto == null) {
            throw new ServerException("Produto não pode ser nulo.");
        }

        if (dto.preco() <= 0 || dto.quantidadeEstoque() < 0) {
            throw new InvalidValueException("Valor negativo. Valor inválido.");
        }

        Produto produto = Produto.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .quantidadeEstoque(dto.quantidadeEstoque())
                .dataCadastro(OffsetDateTime.now())
                .build();

        Produto salvo = produtoRepository.save(produto);

        return toDTO(salvo);
    }

    public List<ProdutoResponseDTO> getAllProdutos() {

        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new ListException("Nenhum produto cadastrado.");
        }

        return produtos.stream().map(this::toDTO).toList();
    }

    public ProdutoResponseDTO getProdutoById(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        return toDTO(produto);
    }

    public void updateProduto(Long id, ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        if (dto.preco() <= 0 || dto.quantidadeEstoque() < 0) {

            throw new InvalidValueException("Atualização inválida.");
        }

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        produtoRepository.save(produto);
    }


    public void removeProduto(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Produto não encontrado. ID: " + id));

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO toDTO(Produto p) {

        return new ProdutoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getPreco(),
                p.getQuantidadeEstoque(),
                p.getDataCadastro()
        );
    }
}
