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

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                AjusteItemVO other = (AjusteItemVO) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

}