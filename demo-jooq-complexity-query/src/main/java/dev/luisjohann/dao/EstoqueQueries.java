package dev.luisjohann.dao;

import java.util.ArrayList;
import java.util.List;

public class EstoqueQueries {

    public EstoqueReadContext ctx;
    public List<BaseQuery> viewList = new ArrayList<>();

    public EstoqueQueries(EstoqueReadContext ctx) {
        this.ctx = ctx;
    }

    public QueryForProduto vwProduto(Integer produtoId) {
        QueryForProduto vwProduto = new QueryForProduto(ctx, "vwProduto")
                .filterProduto(produtoId)
                .saveOnEstoque()
                .build();
        this.viewList.add(vwProduto);
        return vwProduto;
    }

    public QueryForOperacaoItem vwOperacaoItem(QueryForProduto vwProduto) {
        QueryForOperacaoItem vwOperacaoItem = new QueryForOperacaoItem(ctx, "vwOperacaoItem")
                .addExistsByProdutoId(vwProduto.view, vwProduto.getIdField())
                .saveOnEstoque()
                .build();
        this.viewList.add(vwOperacaoItem);
        return vwOperacaoItem;
    }

    public QueryForOperacao vwOperacao(QueryForOperacaoItem vwOperacaoItem) {
        QueryForOperacao vwOperacao = new QueryForOperacao(ctx, "vwOperacao")
                .addExistsByIdField(vwOperacaoItem.view, vwOperacaoItem.getOperacaoId())
                .saveOnEstoque()
                .build();
        this.viewList.add(vwOperacao);
        return vwOperacao;
    }

    public QueryForAjusteItem vwAjusteItem(QueryForProduto vwProduto) {
        QueryForAjusteItem vwAjusteItem = new QueryForAjusteItem(ctx, "vwAjusteItem")
                .addExistsByProdutoId(vwProduto.view, vwProduto.getIdField())
                .saveOnEstoque()
                .build();
        this.viewList.add(vwAjusteItem);
        return vwAjusteItem;
    }

    public QueryForAjuste vwAjuste(QueryForAjusteItem vwAjusteItem) {
        QueryForAjuste vwAjuste = new QueryForAjuste(ctx, "vwAjuste")
                .addExistsByIdField(vwAjusteItem.view, vwAjusteItem.getAjusteId())
                .saveOnEstoque()
                .build();
        this.viewList.add(vwAjuste);
        return vwAjuste;
    }

}
