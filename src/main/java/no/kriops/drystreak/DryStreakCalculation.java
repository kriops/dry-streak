package no.kriops.drystreak;

import io.vavr.control.Try;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        final String input = inputField.getText();
        Try
                .of(() -> 1.0 / Integer.parseInt(input))
                .orElse(Try.of(() -> Double.parseDouble(input)))
                .orElse(Try.of(() -> Fraction.getFraction(input).doubleValue()))
                .toEither("Unable to parse input.")
                .filterOrElse(p -> 0 < p && p <= 1, integer -> "Drop rate must be greater than zero.")
                .flatMap(p -> Try.of(() -> new GeometricDistribution(null, p))
                        .mapTry(dist -> dist.inverseCumulativeProbability(0.5))
                        .toEither("Error performing calculation."))
                .map(result -> String.format("<p><b>Expected dry streak: %s</b></p><hr><p>%s</p>", result, explanation))
                .map(this::asHtml)
                .peek(outputField::setText)
                .mapLeft(this::asHtml)
                .peekLeft(outputField::setText)
        ;
    }

    private String asHtml(String input) {
        return String.format("<html>%s</html>", input);
    }

}
