package dev.luisjohann.vo;

import java.time.LocalDateTime;
import java.util.Set;

public class AjusteVO {

    private Integer id;
    private LocalDateTime dataHora;
    private UsuarioVO usuario;
    private Set<AjusteItemVO> itens;

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

    public UsuarioVO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }

    public Set<AjusteItemVO> getItens() {
        return itens;
    }

    public void setItens(Set<AjusteItemVO> itens) {
        this.itens = itens;
    }

}