
package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.model.Produto;
import com.baozistore.baozi_store.repository.ProdutoRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@Valid @RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("/{id}")
    public Produto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Produto dados) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));

        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setQuantidadeEstoque(dados.getQuantidadeEstoque());

        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        produtoRepository.deleteById(id);
    }
}
