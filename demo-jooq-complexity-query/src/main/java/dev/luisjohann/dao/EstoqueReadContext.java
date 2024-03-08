package dev.luisjohann.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableInt;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOrderByStep;
import org.jooq.SelectQuery;
import org.jooq.WithStep;
import org.jooq.impl.DSL;
import org.springframework.util.function.ThrowingConsumer;

import dev.luisjohann.vo.context.EstoqueContextVO;

public class EstoqueReadContext {
    public final Map<Integer, ThrowingConsumer<Record>> leitorDeRegistroMap = new HashMap<>();

    public final EstoqueContextVO estoque = new EstoqueContextVO();
    public final MutableInt tableViewIdGen = new MutableInt();

    public DSLContext dsl;

    public final EstoqueCollectors collectors = new EstoqueCollectors();
    public final EstoqueQueries queries = new EstoqueQueries(this);

    public SelectQuery<? extends Record> buildQuery(List<BaseQuery> queries) {
        BaseQuery query0 = queries.get(0);
        WithStep withClauses = this.dsl.with(query0.view);
        for (int i = 1; i < queries.size(); i++) {
            withClauses = withClauses.with(queries.get(i).view);
        }

        SelectJoinStep<Record> joinStepClause = withClauses.select(DSL.asterisk())
                .from(query0.view.as("vw_" + query0.getTableId()));
        if (queries.size() > 1) {
            BaseQuery query1 = queries.get(1);
            SelectOrderByStep<Record> unionAllClause = joinStepClause
                    .unionAll(DSL.select(DSL.asterisk()).from(query1.view.as("vw_" + query1.getTableId())));

            for (int i = 2; i < queries.size(); i++) {
                BaseQuery query = queries.get(i);
                if (query.isAllView()) {
                    continue;
                }
                unionAllClause = unionAllClause
                        .unionAll(DSL.select(DSL.asterisk()).from(query.view.as("vw_" + query.getTableId())));
            }
            return unionAllClause.getQuery();
        }
        return joinStepClause.getQuery();
    }

    public <T> void addOnSet(Supplier<Set<T>> getter, Consumer<Set<T>> setter, T bean) {
        Set<T> collection = getter.get();
        if (collection == null) {
            collection = new HashSet<>();
            setter.accept(collection);
        }
        collection.add(bean);
    }

    public <T> void addOnList(Supplier<List<T>> getter, Consumer<List<T>> setter, T bean) {
        List<T> collection = getter.get();
        if (collection == null) {
            collection = new ArrayList<>();
            setter.accept(collection);
        }
        collection.add(bean);
    }
}
