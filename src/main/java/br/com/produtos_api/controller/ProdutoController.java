package br.com.produtos_api.controller;

import br.com.produtos_api.business.services.ProdutoService;
import br.com.produtos_api.controller.dto.ProdutoCreateRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
//import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    //private final GenericResponseService responseBuilder;


    @PostMapping
    @Operation(summary = "Adicionar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao adicionar produto"),
            @ApiResponse(responseCode = "422", description = "Campos não atende os requisitos de produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> addProduto(@RequestBody ProdutoCreateRequestDTO produto) {

        produtoService.addProdutos(produto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto listado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao listar produto"),
            @ApiResponse(responseCode = "422", description = "Campos não atende os requisitos de produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<ProdutoRequestDTO>> getAllProducts() {

        List<ProdutoRequestDTO> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar produto"),
            @ApiResponse(responseCode = "422", description = "Campos não atende os requisitos de produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ProdutoRequestDTO> getProdutcsById(@PathVariable Long id) {

        ProdutoRequestDTO produto = produtoService.getProdutoById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar produto"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Campos não atende os requisitos de produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProdutoCreateRequestDTO produtoAtualizado) {

        try {
            produtoService.updateProduto(id, produtoAtualizado);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao remover produto"),
            @ApiResponse(responseCode = "422", description = "Campos não atende os requisitos de produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {

        try {
            produtoService.removeProduto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
