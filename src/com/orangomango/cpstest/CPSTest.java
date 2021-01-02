package com.orangomango.cpstest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.canvas.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CPSTest extends Application {

    public static int clicks = 0;
    public static volatile boolean go = false;
    public static double cps = 0.0;
    public static NumberFormat formatter = new DecimalFormat("##.##");
    public static Label cpslabel = new Label("cps: " + cps + "/s");
    public static Label timer = new Label("Timer: 0.0");
    public static long stime = 0l, etime = 0l;

    public static void update() {
        etime = System.currentTimeMillis();
        float difference = 0f;
        if (etime != 0l && stime != 0l) {
            difference = (etime - stime) / 1000f;
        }
        timer.setText("Timer: " + formatter.format(difference));
        cps = clicks / difference;
        cpslabel.setText("cps: " + formatter.format(cps) + "/s");
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setHgap(5);

        primaryStage.setTitle("CPS Test com.orangomango.cpstest");
        Canvas canvas = new Canvas(300, 300);
        Label clicksLabel = new Label("Clicks: " + clicks);

        root.add(canvas, 0, 0);
        root.add(cpslabel, 3, 0);
        root.add(timer, 4, 0);
        root.add(clicksLabel, 2, 0);

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (go) {
                    clicks++;
                    clicksLabel.setText("Clicks: " + clicks);
                } else {
                    go = true;
                    stime = System.currentTimeMillis();
                }
            }
        });

        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setStroke(Color.BLACK);
        g.setLineWidth(5.0);
        g.strokeRect(0, 0, 300, 300);

        Scene scene = new Scene(root, 550, 300);
        primaryStage.setScene(scene);

        Thread t = new Thread(() -> monitor());

        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(100),
                        event -> {
                            update();
                        }
                )
        );

        t.setDaemon(true);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        t.start();

        primaryStage.show();

    }

    public static void monitor() {
        while (!go) {
        }
        long start = System.currentTimeMillis();
        long end = start + 5 * 1000; // 60 seconds * 1000 ms/sec

        while (System.currentTimeMillis() < end) {

        }

        Platform.runLater(() -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Your cps");
            alert.setHeaderText(null);
            alert.setContentText("You made " + formatter.format(cps) + " cps");
            alert.showAndWait();

            System.exit(0);

        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
