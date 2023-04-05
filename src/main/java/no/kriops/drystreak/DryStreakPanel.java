package no.kriops.drystreak;

import java.awt.*;
import javax.inject.Inject;
import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.ColorJButton;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

@Slf4j
public class DryStreakPanel extends PluginPanel {

    private final DryStreakCalculation dryStreakCalculation;
    private final FlatTextField dropRate;

    @Inject
    public DryStreakPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new GridBagLayout());

        // Expand sub items to fit width of panel, align to top of panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 10, 0);

        JShadowedLabel dropRateLabel = new JShadowedLabel("<html>Enter drop rate:</html>");
        add(dropRateLabel, c);
        c.gridy++;

        dropRate = new FlatTextField();
        dropRate.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        dropRate.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        dropRate.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        dropRate.setMinimumSize(new Dimension(0, 30));
        add(dropRate, c);
        c.gridy++;

        ColorJButton button = new ColorJButton("Calculate", new Color(255, 255, 255));
        add(button, c);
        c.gridy++;

        JShadowedLabel resultLabel = new JShadowedLabel("<html>Enter drop rate as 1/25, 25 or 0.04.</html>");
        add(resultLabel, c);
        c.gridy++;

        this.dryStreakCalculation = new DryStreakCalculation(dropRate, resultLabel);
        dropRate.addActionListener(dryStreakCalculation);
        button.addActionListener(dryStreakCalculation);
    }

    @Override
    public void onActivate() {
        super.onActivate();
        dropRate.requestFocusInWindow();
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
    }
}