package dev.luisjohann.vo.context;

import java.util.List;

import dev.luisjohann.vo.AjusteItemVO;
import dev.luisjohann.vo.AjusteVO;
import dev.luisjohann.vo.OperacaoItemVO;
import dev.luisjohann.vo.OperacaoVO;
import dev.luisjohann.vo.PessoaVO;
import dev.luisjohann.vo.ProdutoVO;
import dev.luisjohann.vo.UsuarioVO;

public class EstoqueContextVO {

    private ProdutoVO produtoVO;
    private List<UsuarioVO> usuarios;
    private List<PessoaVO> pessoas;
    private List<ProdutoVO> produtos;
    private List<AjusteItemVO> itensAjustes;
    private List<AjusteVO> ajustes;
    private List<OperacaoItemVO> itensOperacoes;
    private List<OperacaoVO> operacoes;

    public ProdutoVO getProdutoVO() {
        return produtoVO;
    }

    public void setProdutoVO(ProdutoVO produtoVO) {
        this.produtoVO = produtoVO;
    }

    public List<UsuarioVO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioVO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<PessoaVO> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaVO> pessoas) {
        this.pessoas = pessoas;
    }

    public List<ProdutoVO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVO> produtos) {
        this.produtos = produtos;
    }

    public List<AjusteItemVO> getItensAjustes() {
        return itensAjustes;
    }

    public void setItensAjustes(List<AjusteItemVO> itensAjustes) {
        this.itensAjustes = itensAjustes;
    }

    public List<AjusteVO> getAjustes() {
        return ajustes;
    }

    public void setAjustes(List<AjusteVO> ajustes) {
        this.ajustes = ajustes;
    }

    public List<OperacaoItemVO> getItensOperacoes() {
        return itensOperacoes;
    }

    public void setItensOperacoes(List<OperacaoItemVO> itensOperacoes) {
        this.itensOperacoes = itensOperacoes;
    }

    public List<OperacaoVO> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<OperacaoVO> operacoes) {
        this.operacoes = operacoes;
    }

}
