package com.nstu.lab1.data.factories;

import java.util.Comparator;
import java.util.Random;

import com.nstu.lab1.data.Point2D;

public class Point2DFactory implements IFactory<Point2D> {
    private Random rand = new Random();

    @Override
    public Point2D create(Object... args) {
        if (args.length == 2
                && args[0] instanceof Integer
                && args[1] instanceof Integer) {
            return new Point2D((Integer) args[0], (Integer) args[1]);
        }
        throw new IllegalArgumentException("Invalid arguments for creating a Point2D");
    }

    @Override
    public Point2D createRandom() {
        int x = rand.nextInt(100);
        int y = rand.nextInt(100);
        return new Point2D(x, y);
    }

    @Override
    public Comparator<Point2D> getComparator() {
        return new Comparator<Point2D>() {
            @Override
            public int compare(Point2D p1, Point2D p2) {
                double d1 = Math.sqrt(p1.getX() * p1.getX() + p1.getY() * p1.getY());
                double d2 = Math.sqrt(p2.getX() * p2.getX() + p2.getY() * p2.getY());
                return Double.compare(d1, d2);
            }
        };
    }
}
