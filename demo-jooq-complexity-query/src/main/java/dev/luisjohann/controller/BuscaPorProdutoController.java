package dev.luisjohann.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.dao.EstoqueDAO;
import dev.luisjohann.vo.context.EstoqueContextVO;

@RestController
public class BuscaPorProdutoController {

    final EstoqueDAO estoqueDAO;

    public BuscaPorProdutoController(EstoqueDAO estoqueDAO) {
        this.estoqueDAO = estoqueDAO;
    }

    @GetMapping("/produto")
    @ResponseStatus(HttpStatus.OK)
    public EstoqueContextVO buscarProdutoPorId() {
        return this.estoqueDAO.buscarEstoqueDoProdutoPorId(1);
    }

}
