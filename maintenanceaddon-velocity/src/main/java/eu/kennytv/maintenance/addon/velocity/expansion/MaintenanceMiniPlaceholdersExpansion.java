package eu.kennytv.maintenance.addon.velocity.expansion;

import eu.kennytv.maintenance.api.proxy.MaintenanceProxy;
import eu.kennytv.maintenance.core.config.Config;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.TagsUtils;

public final class MaintenanceMiniPlaceholdersExpansion {
	private final Expansion expansion;

	public MaintenanceMiniPlaceholdersExpansion(MaintenanceProxy maintenance, Config config) {
		expansion = Expansion.builder("maintenance")
				.globalPlaceholder("status", (queue, ctx) -> {
					String status = config.getString(maintenance.isMaintenance() ? "maintenance-on" : "maintenance-off");

					if (status == null) {
						return TagsUtils.EMPTY_TAG;
					}

					return TagsUtils.staticTag(status);
				})
				.globalPlaceholder("server", (queue, ctx) -> {
					if (!queue.hasNext()) {
						return TagsUtils.EMPTY_TAG;
					}

					String serverName = queue.pop().lowerValue();
					String serverStatus = config.getString(maintenance.getMaintenanceServers().contains(serverName) ? "single-maintenance-on" : "single-maintenance-off");

					if (serverStatus == null) {
						return TagsUtils.EMPTY_TAG;
					}

					return TagsUtils.staticTag(serverStatus);
				})
				.build();
	}

	public void register() {
		expansion.register();
	}
}
