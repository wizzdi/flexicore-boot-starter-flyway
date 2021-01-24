
# ![](https://support.wizzdi.com/wp-content/uploads/2020/05/flexicore-icon-extra-small.png) FlexiCore Boot Starter Flyway [![Build Status](https://jenkins.wizzdi.com/buildStatus/icon?job=wizzdi+organization%flexicore-boot-starter-flyway%2Fmaster)](https://jenkins.wizzdi.com/job/wizzdi%20organization/job/flexicore-boot-starter-flyway/job/master/)[![Maven Central](https://img.shields.io/maven-central/v/com.wizzdi/flexicore-boot-starter-flyway.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.wizzdi%22%20AND%20a:%22flexicore-boot-starter-flyway%22)


For comprehensive information about FlexiCore Boot Starter Flyway please visit our [site](http://wizzdi.com/).

## What it does?

FlexiCore Boot Starter Flyway is a FlexiCore Module that enables Flyway migrations inside FlexiCore Plugins.

## How to use?
Add the flexicore-boot-starter-flyway dependency using the latest version available from maven central:

            <dependency>
                <groupId>com.wizzdi</groupId>
                <artifactId>flexicore-boot-starter-flyway</artifactId>
                <version>LATEST</version>
            </dependency>
Simply annotate your application class or your configuration class with

    @EnableFlexiCoreFlyWayPlugins

## Example
your application class:

    @EnableFlexiCorePlugins  
    @EnableFlexiCoreFlyWayPlugins
    @SpringBootApplication  
    public class App {  
      
       public static void main(String[] args) {  
      
          SpringApplication app = new SpringApplication(App.class);  
      app.addListeners(new ApplicationPidFileWriter());  
      ConfigurableApplicationContext context=app.run(args);  
      
      }
    }
a Flyway Migration:

    @Extension
    public class V1__RandomBookName extends BaseJavaMigration implements Plugin {

	    @Override
	    public void migrate(Context context) throws Exception {
		    try (Statement select = context.getConnection().createStatement()) {
			    try (ResultSet rows = select.executeQuery("SELECT id FROM BOOK ORDER BY id")) {
				    while (rows.next()) {
					    int id = rows.getInt(1);
					    String anonymizedName = "Anonymous" + id;
					    try (Statement update = context.getConnection().createStatement()) {
						    update.execute("UPDATE BOOK SET name='" + anonymizedName + "' WHERE id=" + id);
					    }
				    }

			    }
		    }
	    }
    }


## Main Dependencies

[FlexiCore Boot](https://github.com/wizzdi/flexicore-boot)


[Flyway Core](https://search.maven.org/artifact/org.flywaydb/flyway-core)