package dev.luisjohann.vo;

import java.math.BigDecimal;

import dev.luisjohann.enums.TipoOperacaoAjuste;

public class AjusteItemVO {

        private Integer id;
        private AjusteVO ajuste;
        private ProdutoVO produto;
        private BigDecimal quantidade;
        private TipoOperacaoAjuste tipoOperacao;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public AjusteVO getAjuste() {
                return ajuste;
        }

        public void setAjuste(AjusteVO ajuste) {
                this.ajuste = ajuste;
        }

        public ProdutoVO getProduto() {
                return produto;
        }

        public void setProduto(ProdutoVO produto) {
                this.produto = produto;
        }

        public BigDecimal getQuantidade() {
                return quantidade;
        }

        public void setQuantidade(BigDecimal quantidade) {
                this.quantidade = quantidade;
        }

        public TipoOperacaoAjuste getTipoOperacao() {
                return tipoOperacao;
        }

        public void setTipoOperacao(TipoOperacaoAjuste tipoOperacao) {
                this.tipoOperacao = tipoOperacao;
        }

}