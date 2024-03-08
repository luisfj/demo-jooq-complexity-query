package dev.luisjohann.config;

import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqContextPrettyPrinter extends DefaultExecuteListener {

    private static final Logger logger = LoggerFactory.getLogger(JooqContextPrettyPrinter.class);

    private long begin;
    private final boolean print;

    public JooqContextPrettyPrinter() {
        print = logger.isDebugEnabled();
    }

    @Override
    public void start(final ExecuteContext ctx) {
        if (print) {
            begin = System.currentTimeMillis();
        }
    }

    @Override
    public void end(final ExecuteContext ctx) {
        if (print && ctx.exception() != null) {
            long end = System.currentTimeMillis() - begin;
            if (end > 500) {
                DSLContext create = DSL.using(ctx.configuration().dialect(), new Settings());

                if (ctx.query() != null) {
                    logger.debug(end + "\t" + create.renderInlined(ctx.query()));
                } else if (ctx.routine() != null) {
                    logger.debug(end + "\t" + create.renderInlined(ctx.routine()));
                } else if (StringUtils.isNotBlank(ctx.sql())) {
                    logger.debug(end + "\t" + ctx.sql());
                }
            }
        }
    }
}
