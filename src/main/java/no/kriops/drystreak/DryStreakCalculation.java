package no.kriops.drystreak;

import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.math3.distribution.GeometricDistribution;

public class DryStreakCalculation implements ActionListener {
    final FlatTextField inputField;
    final JShadowedLabel outputField;

    DryStreakCalculation(FlatTextField inputField, JShadowedLabel outputField) {
        this.inputField = inputField;
        this.outputField = outputField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String explanation = "At this number of kills, you are less likely to have received zero drops than not.";
        String message = Optional.ofNullable(inputField.getText())
            .map(input -> {
                try {
                    return 1.0 / Integer.parseInt(input);
                } catch (NumberFormatException ignored) {
                }

                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException ignored) {
                }

                try {
                    return Fraction.getFraction(input).doubleValue();
                } catch (NumberFormatException ignored) {
                }

                return null;
            })
            .map(p -> {
                if (0 < p && p <= 1) {
                    try {
                        int result = new GeometricDistribution(null, p).inverseCumulativeProbability(0.5);
                        return String.format("<p>Expected dry streak: %s</p><br><p>%s</p>", result, explanation);
                    } catch (Exception ignored) {
                        return "Error performing calculation.";
                    }
                } else {
                    return "Drop rate must be greater than zero.";
                }
            })
            .orElse("Unable to parse input.");

        outputField.setText(asHtml(message));
    }

    private String asHtml(String input) {
        return String.format("<html>%s</html>", input);
    }

}
