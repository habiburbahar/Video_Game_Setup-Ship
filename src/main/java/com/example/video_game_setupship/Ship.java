package com.example.video_game_setupship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.DoubleStream;

public class Ship implements Groupable{

    double translateX, translateY;
    double[] xs = {0,20,0,-20,0};
    double[] ys = {24,-20,-12,-20,24};
    double shipWidth, shipHeight;
    double[] displayXs, displayYs;
    double[] txs, tys;
    WritableImage buffer;
    PixelReader reader;
    double clickX, clickY;
    double angle;
    double left, right, top, bottom;

    public Ship(double newX, double newY){
        Canvas shipCanvas;
        GraphicsContext gc;
        translateX = newX;
        translateY = newY;
        angle = 0;
        left = DoubleStream.of(xs).min().getAsDouble();
        right = DoubleStream.of(xs).max().getAsDouble();
        shipWidth = right - left;
        top = DoubleStream.of(ys).min().getAsDouble();
        bottom = DoubleStream.of(ys).max().getAsDouble();
        shipHeight = bottom - top;
        displayXs = new double[xs.length];
        displayYs = new double[ys.length];
        txs = new double[xs.length];
        tys = new double[ys.length];
        for (int i = 0; i < displayXs.length; i++) {
            displayXs[i] = xs[i] + shipWidth/2;
            displayYs[i] = ys[i] + shipHeight/2;
            txs[i] = displayXs[i];
            tys[i] = displayYs[i];
        }
        shipCanvas = new Canvas(shipWidth,shipHeight);
        gc = shipCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillPolygon(displayXs, displayYs, displayXs.length);
        gc.strokePolygon(displayXs, displayYs, displayXs.length);
        buffer = shipCanvas.snapshot(null,null);
        reader = buffer.getPixelReader();

        for (int i = 0; i < displayXs.length; i++) {
            displayXs[i] = xs[i] + translateX;
            displayYs[i] = ys[i] + translateY;
        }
        recalculateBounds();
    }
    private void recalculateBounds() {
        // recalculate bounds
        left = DoubleStream.of(displayXs).min().getAsDouble();
        right = DoubleStream.of(displayXs).max().getAsDouble();
        top = DoubleStream.of(displayYs).min().getAsDouble();
        bottom = DoubleStream.of(displayYs).max().getAsDouble();
        // centre point is halfway between point 0 and point 2
        double rise = displayYs[2] - displayYs[0];
        double run = displayXs[2] - displayXs[0];
        if (run == 0) run = 0.0000001;
        double slope = rise / run;
        translateX = displayXs[0] + run * 0.65;
        translateY = displayYs[0] + rise * 0.65;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public List<Groupable> getChildren() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        double shipAngle = getAngle(displayXs[0],displayYs[0],displayXs[2],displayYs[2]);
        shipAngle += 90;
        clickX = x - translateX;
        clickY = y - translateY;
        double tempX;
        double radians = -1 * shipAngle * Math.PI / 180;
        tempX = clickX;
        clickX = rotateX(clickX, clickY, radians) + shipWidth/2;
        clickY = rotateY(tempX, clickY,radians) + shipHeight/2;
        // check bounding box first, then bitmap
        boolean inside = false;
        if (clickX >= 0 && clickX <= shipWidth && clickY >= 0 && clickY <= shipHeight) {
            if (reader.getColor((int) clickX, (int) clickY).equals(Color.BLACK)) inside = true;
        }
        return inside;
    }

    private double getAngle(double x1, double y1, double x2, double y2) {
        double calcAngle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (calcAngle < 0) calcAngle += 360;
        return calcAngle;
    }

    @Override
    public boolean isContained(double x1, double y1, double x2, double y2) {
        return left >= x1 && right <= x2 && top >= y1 && bottom <= y2;
    }

    @Override
    public void move(double dx, double dy) {
        translateX += dx;
        translateY += dy;
        for (int i = 0; i < displayXs.length; i++) {
            displayXs[i] += dx;
            displayYs[i] += dy;
        }
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
    }

    @Override
    public void rotate(double a) {
        rotate(a,translateX,translateY);
    }

    @Override
    public void rotate(double a, double cx, double cy) {
        double tempX, tempY;
        double radians = a * Math.PI / 180;
        angle += a;
        for (int i = 0; i < displayXs.length; i++) {
            tempX = displayXs[i] - cx;
            tempY = displayYs[i] - cy;
            displayXs[i] = rotateX(tempX, tempY, radians) + cx;
            displayYs[i] = rotateY(tempX, tempY, radians) + cy;
        }
        recalculateBounds();
    }

    private double rotateX(double x, double y, double thetaR) {
        return Math.cos(thetaR) * x - Math.sin(thetaR) * y;
    }

    private double rotateY(double x, double y, double thetaR) {
        return Math.sin(thetaR) * x + Math.cos(thetaR) * y;
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getTop() {
        return top;
    }

    @Override
    public double getRight() {
        return right;
    }

    @Override
    public double getBottom() {
        return bottom;
    }

    @Override
    public double getCX() {
        return translateX;
    }

    @Override
    public double getCY() {
        return translateY;
    }

    @Override
    public Groupable duplicate() {
        Ship dupe = new Ship(translateX,translateY);
        for (int i = 0; i < displayXs.length; i++) {
            dupe.displayXs[i] = displayXs[i];
            dupe.displayYs[i] = displayYs[i];
        }
        dupe.angle = angle;
        dupe.recalculateBounds();
        return dupe;
    }
}
