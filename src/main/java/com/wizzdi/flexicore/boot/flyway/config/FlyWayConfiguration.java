package com.wizzdi.flexicore.boot.flyway.config;


import com.wizzdi.flexicore.boot.base.events.PluginsLoadedEvent;
import com.wizzdi.flexicore.boot.base.init.FlexiCorePluginManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.migration.JavaMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FlyWayConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(FlyWayConfiguration.class);
	private static final AtomicBoolean init = new AtomicBoolean(false);

	@Lazy
	@Autowired
	private Flyway flyway;

	@Lazy
	@Autowired
	private FlexiCorePluginManager pluginManager;

	@EventListener
	public void onPluginsLoaded(ContextRefreshedEvent pluginsLoadedEvent) {
		if (init.compareAndSet(false, true)) {
			logger.info("handling flyway plugin migrations");
			FluentConfiguration configuration = new FluentConfiguration().configuration(flyway.getConfiguration());
			JavaMigration[] javaMigrations = pluginManager.getExtensions(JavaMigration.class).toArray(JavaMigration[]::new);
			Callback[] callbacks = pluginManager.getExtensions(Callback.class).toArray(Callback[]::new);
			configuration.javaMigrations(javaMigrations).callbacks(callbacks);
			Flyway pluginsFlyway = new Flyway(configuration);
			pluginsFlyway.migrate();
			logger.info("completed flyway plugin migrations");

		}

	}


}
