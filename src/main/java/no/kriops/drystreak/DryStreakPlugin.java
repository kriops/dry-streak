package no.kriops.drystreak;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.awt.image.BufferedImage;
import javax.annotation.Nullable;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;


@Slf4j
@PluginDescriptor(
        name = "Dry Streak"
)
public class DryStreakPlugin extends Plugin {
    @Inject
    @Nullable
    private Client client;
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private DryStreakConfig config;

    private NavigationButton navButton;
    private DryStreakPanel dryStreakPanel;

    @Provides
    DryStreakConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(DryStreakConfig.class);
    }

    @Override
    protected void startUp() {
        dryStreakPanel = injector.getInstance(DryStreakPanel.class);
        BufferedImage icon;
        try {
            icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
        } catch (Exception e) {
            icon = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
        }
        navButton = NavigationButton.builder()
                .tooltip("Dry Streak")
                .priority(5)
                .panel(dryStreakPanel)
                .icon(icon)
                .build();
        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception {
        clientToolbar.removeNavigation(navButton);
        super.shutDown();
    }
}
