package com.wizzdi.flexicore.boot.flyway.plugin;

import com.wizzdi.flexicore.boot.base.interfaces.Plugin;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.pf4j.Extension;

import java.sql.ResultSet;
import java.sql.Statement;

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
