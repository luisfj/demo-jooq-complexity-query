package dev.luisjohann.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EstoqueStandaloneQueries {

    public EstoqueReadContext ctx;
    public List<BaseQuery> viewList = new ArrayList<>();

    public EstoqueStandaloneQueries(EstoqueReadContext ctx) {
        this.ctx = ctx;
    }

    public QueryForProduto vwProduto() {
        QueryForProduto vwProduto = null;

        Set<Integer> idSet = this.ctx.collectors.produtoCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwProduto = new QueryForProduto(ctx, "vwProduto")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwProduto);
        }
        return vwProduto;
    }

    public QueryForPessoa vwPessoa() {
        QueryForPessoa vwPessoa = null;

        Set<Integer> idSet = this.ctx.collectors.pessoaCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwPessoa = new QueryForPessoa(ctx, "vwPessoa")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwPessoa);
        }
        return vwPessoa;
    }

    public QueryForUsuario vwUsuario() {
        QueryForUsuario vwUsuario = null;

        Set<Integer> idSet = this.ctx.collectors.usuarioCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwUsuario = new QueryForUsuario(ctx, "vwUsuario")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwUsuario);
        }
        return vwUsuario;
    }

    public QueryForOperacao vwOperacao() {
        QueryForOperacao vwOperacao = null;

        Set<Integer> idSet = this.ctx.collectors.operacaoCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwOperacao = new QueryForOperacao(ctx, "vwOperacao")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwOperacao);
        }
        return vwOperacao;
    }

    public QueryForAjuste vwAjuste() {
        QueryForAjuste vwAjuste = null;

        Set<Integer> idSet = this.ctx.collectors.ajusteCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwAjuste = new QueryForAjuste(ctx, "vwAjuste")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwAjuste);
        }
        return vwAjuste;
    }

    public QueryForAjusteItem vwAjusteItem() {
        QueryForAjusteItem vwAjusteItem = null;

        Set<Integer> idSet = this.ctx.collectors.ajusteItemCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwAjusteItem = new QueryForAjusteItem(ctx, "vwAjusteItem")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwAjusteItem);
        }
        return vwAjusteItem;
    }

    public QueryForOperacaoItem vwOperacaoItem() {
        QueryForOperacaoItem vwOperacaoItem = null;

        Set<Integer> idSet = this.ctx.collectors.operacaoItemCollector.getMissingIdSet();

        if (idSet.size() > 0) {
            vwOperacaoItem = new QueryForOperacaoItem(ctx, "vwOperacaoItem")
                    .addExistByIdValues(idSet)
                    .saveOnEstoque()
                    .build();
            viewList.add(vwOperacaoItem);
        }
        return vwOperacaoItem;
    }

}
