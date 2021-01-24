package com.wizzdi.flexicore.boot.flyway.annotations;

import com.wizzdi.flexicore.boot.flyway.init.FlyWayPluginHandlerModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(FlyWayPluginHandlerModule.class)
@Configuration
public @interface EnableFlexiCoreFlyWayPlugins {
}
