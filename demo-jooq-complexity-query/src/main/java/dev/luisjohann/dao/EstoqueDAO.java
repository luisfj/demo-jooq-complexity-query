package dev.luisjohann.dao;

import java.util.function.Consumer;

import org.jooq.Record;
import org.jooq.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.function.ThrowingConsumer;

import dev.luisjohann.config.JooqContext;
import dev.luisjohann.vo.context.EstoqueContextVO;

@Service
public class EstoqueDAO {
    private static final Logger logger = LoggerFactory.getLogger(EstoqueDAO.class);
    protected JooqContext jooq;

    public EstoqueDAO(JooqContext jooq) {
        this.jooq = jooq;
    }

    public EstoqueContextVO buscarEstoqueDoProdutoPorId(Integer produtoId) {
        return leia(produtoId);
    }

    private EstoqueContextVO leia(Integer produtoId) {
        EstoqueReadContext ctx = new EstoqueReadContext();
        ctx.dsl = this.jooq.dsl();

        QueryForProduto vwProduto;
        {
            vwProduto = ctx.queries.vwProduto(produtoId);
            {
                var vwOperacaoItem = ctx.queries.vwOperacaoItem(vwProduto);
                ctx.queries.vwOperacao(vwOperacaoItem);
            }
            {
                var vwAjusteItem = ctx.queries.vwAjusteItem(vwProduto);
                ctx.queries.vwAjuste(vwAjusteItem);
            }
        }

        SelectQuery<? extends Record> query = ctx.buildQuery(ctx.queries.viewList);

        printQuery(query, "main");

        Consumer<? super Record> forEachAction = rec -> {
            Integer tableId = rec.getValue(EstoqueDAOConstants.FLD_TB_ID, Integer.class);
            ThrowingConsumer<Record> leitorDeRegistro = ctx.leitorDeRegistroMap.get(tableId);
            if (leitorDeRegistro != null) {
                leitorDeRegistro.accept(rec);
            }
        };

        query.forEach(forEachAction);

        this.standalone("standalone1", ctx, forEachAction);
        this.standalone("standalone2", ctx, forEachAction);
        this.standalone("standalone3", ctx, forEachAction);
        this.standalone("standalone4", ctx, forEachAction);
        this.standalone("standalone5", ctx, forEachAction);
        this.standalone("standalone6", ctx, forEachAction);
        this.standalone("standalone7", ctx, forEachAction);

        ctx.collectors.postCollect();

        return ctx.estoque;
    }

    private void standalone(String name, EstoqueReadContext ctx, Consumer<? super Record> forEachAction) {
        EstoqueStandaloneQueries standaloneQueries = new EstoqueStandaloneQueries(ctx);
        standaloneQueries.vwProduto();
        standaloneQueries.vwPessoa();
        standaloneQueries.vwUsuario();
        standaloneQueries.vwOperacao();
        standaloneQueries.vwAjuste();
        standaloneQueries.vwAjusteItem();
        standaloneQueries.vwOperacaoItem();

        if (!standaloneQueries.viewList.isEmpty()) {
            SelectQuery<? extends Record> query = ctx.buildQuery(standaloneQueries.viewList);
            printQuery(query, name);
            query.forEach(forEachAction);
        }
    }

    protected void printQuery(SelectQuery<? extends Record> query, String name) {
        // NOOP: Serve para fins de depuracao
        logger.info(name);
        logger.info(query.getSQL());
    }
}
