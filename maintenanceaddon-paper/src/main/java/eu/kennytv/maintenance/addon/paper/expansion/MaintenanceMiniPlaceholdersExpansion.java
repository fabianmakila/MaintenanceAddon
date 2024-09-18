package eu.kennytv.maintenance.addon.paper.expansion;

import eu.kennytv.maintenance.addon.paper.MaintenancePaperAddon;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.TagsUtils;

public final class MaintenanceMiniPlaceholdersExpansion {
	private final Expansion expansion;

	public MaintenanceMiniPlaceholdersExpansion(MaintenancePaperAddon plugin) {
		expansion = Expansion.builder("maintenance")
				.globalPlaceholder("status", (queue, ctx) -> {
					String status = plugin.getMessages().get(plugin.isMaintenance() ? "maintenance-on" : "maintenance-off");
					return TagsUtils.staticTag(status);
				})
				.globalPlaceholder("server", (queue, ctx) -> {
					if (!queue.hasNext()) {
						return TagsUtils.EMPTY_TAG;
					}

					String serverName = queue.pop().lowerValue();
					String serverStatus = plugin.getMessages().get(plugin.getMaintenanceServers().contains(serverName) ? "single-maintenance-on" : "single-maintenance-off");
					return TagsUtils.staticTag(serverStatus);
				})
				.build();
	}

	public void register() {
		expansion.register();
	}
}
