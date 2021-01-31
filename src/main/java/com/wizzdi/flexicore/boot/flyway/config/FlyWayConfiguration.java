package com.wizzdi.flexicore.boot.flyway.config;


import com.wizzdi.flexicore.boot.base.init.FlexiCorePluginManager;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class FlyWayConfiguration {



	@Lazy
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public FlywayConfigurationCustomizer flywayConfigurationCustomizer(@Lazy FlexiCorePluginManager flexiCorePluginManager) {


		JavaMigration[] javaMigrations = flexiCorePluginManager.getExtensions(JavaMigration.class).toArray(JavaMigration[]::new);
		Callback[] callbacks = flexiCorePluginManager.getExtensions(Callback.class).toArray(Callback[]::new);
		return configuration -> configuration.javaMigrations(javaMigrations).callbacks(callbacks);
	}






}
