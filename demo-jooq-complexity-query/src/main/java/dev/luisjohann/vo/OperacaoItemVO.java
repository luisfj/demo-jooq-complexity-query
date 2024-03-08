package dev.luisjohann.vo;

import java.math.BigDecimal;

public class OperacaoItemVO {

        private Integer id;
        private OperacaoVO operacao;
        private ProdutoVO produto;
        private BigDecimal quantidade;
        private BigDecimal valorUn;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public OperacaoVO getOperacao() {
                return operacao;
        }

        public void setOperacao(OperacaoVO operacao) {
                this.operacao = operacao;
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

        public BigDecimal getValorUn() {
                return valorUn;
        }

        public void setValorUn(BigDecimal valorUn) {
                this.valorUn = valorUn;
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
                OperacaoItemVO other = (OperacaoItemVO) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

}
