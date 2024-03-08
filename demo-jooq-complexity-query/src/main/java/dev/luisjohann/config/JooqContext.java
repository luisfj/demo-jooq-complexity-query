package dev.luisjohann.config;

import java.util.concurrent.atomic.AtomicReference;

import javax.sql.DataSource;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class JooqContext {
    public static final AtomicReference<JooqContext> BEAN = new AtomicReference<>();

    @Autowired
    protected DataSource ds;

    protected boolean executeLogging;

    private ThreadLocal<DSLContext> dslContext = ThreadLocal.withInitial(() -> {
        Configuration configuration = new DefaultConfiguration();

        configuration.set(new DefaultExecuteListenerProvider(new JooqContextPrettyPrinter()));
        configuration.set(new DataSourceConnectionProvider(ds));
        configuration.set(SQLDialect.POSTGRES);

        org.jooq.conf.Settings settings = new org.jooq.conf.Settings();
        settings.setExecuteLogging(this.executeLogging);
        configuration.set(settings);

        return DSL.using(configuration);
    });

    public Configuration configuration() {
        return this.dslContext.get().configuration();
    }

    public DSLContext dsl() {
        return this.dslContext.get();
    }

    public Settings settings() {
        return this.dslContext.get().settings();
    }

    public SQLDialect dialect() {
        return this.dslContext.get().dialect();
    }

    public SQLDialect family() {
        return this.dslContext.get().family();
    }
}
