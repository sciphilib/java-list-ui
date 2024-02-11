package com.nstu.lab1.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Fraction {
    private int wholePart;
    private int numerator;
    private int denominator;

    @JsonCreator
    public Fraction(
            @JsonProperty("wholePart") int wholePart,
            @JsonProperty("numerator") int numerator,
            @JsonProperty("denominator") int denominator) {
        this.wholePart = wholePart;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction() { }

    public int getWholePart() {
        return wholePart;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setWholePart(int wholePart) {
        this.wholePart = wholePart;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return "ProperFraction{" +
                "wholePart=" + wholePart +
                ", numerator=" + numerator +
                ", denominator=" + denominator +
                '}';
    }
}
