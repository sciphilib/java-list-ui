package com.nstu.lab1.data.factories;

import java.util.Comparator;
import java.util.Random;

import com.nstu.lab1.data.Fraction;

public class FractionFactory implements IFactory<Fraction> {
    private Random rand = new Random();
    
    @Override
    public Fraction create(Object... args) {
        if (args.length == 3
                && args[0] instanceof Integer
                && args[1] instanceof Integer
                && args[2] instanceof Integer) {
            return new Fraction((Integer) args[0], (Integer) args[1], (Integer) args[2]);
        }
        throw new IllegalArgumentException("Invalid arguments for creating a ProperFraction");
    }

    @Override
    public Fraction createRandom() {
        int numerator = rand.nextInt(100);
        int denominator = numerator + rand.nextInt(100) + 1;
        int wholePart = rand.nextInt(10);
        return new Fraction(wholePart, numerator, denominator);
    }

    @Override
    public Comparator<Fraction> getComparator() {
        return Comparator
                .comparingInt(Fraction::getWholePart)
                .thenComparingInt(Fraction::getNumerator)
                .thenComparingInt(Fraction::getDenominator);
    }
}
