package no.kriops.drystreak;

import io.vavr.control.Option;
import io.vavr.control.Try;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
        Option
                .of(inputField.getText())
                .map(Integer::parseInt)
                .filter(Objects::nonNull)
                .filter(i -> i > 0)
                .map(this::calculate)
                .peek(p -> outputField.setText(String.format("Expected dry streak: %s", p)))
                .onEmpty(() -> outputField.setText("Something went wrong."))
        ;
    }
    private int calculate(int p) {
        GeometricDistribution dist = new GeometricDistribution(null, 1.0 / p);
        return Math.toIntExact(dist.inverseCumulativeProbability(0.5));
    }

}
