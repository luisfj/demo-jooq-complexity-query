package dev.luisjohann.vo;

import java.time.LocalDateTime;
import java.util.List;

import dev.luisjohann.enums.TipoOperacao;

public class OperacaoVO {

    private Integer id;
    private LocalDateTime dataHora;
    private PessoaVO pessoa;
    private UsuarioVO usuario;
    private TipoOperacao tipoOperacao;
    private List<OperacaoItemVO> itens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public PessoaVO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaVO pessoa) {
        this.pessoa = pessoa;
    }

    public UsuarioVO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public List<OperacaoItemVO> getItens() {
        return itens;
    }

    public void setItens(List<OperacaoItemVO> itens) {
        this.itens = itens;
    }

}