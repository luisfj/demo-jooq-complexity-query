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

}
