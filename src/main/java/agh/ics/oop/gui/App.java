package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.io.FileNotFoundException;


public class App extends Application implements IAnimalObserver {


    private GridPane mapGrid;
    private SimulationEngine engine;
    private GrassField map;
    private int eraCount = 0;
    private XYChart.Series animalsChartSeries = new XYChart.Series();
    private XYChart.Series plantChartSeries = new XYChart.Series();
    private XYChart.Series energyAvgChartSeries = new XYChart.Series();
    private XYChart.Series lifespanDeadAnimalAvgChartSeries = new XYChart.Series();
    private XYChart.Series childAmountAvgChartSeries = new XYChart.Series();


    public void init() {
        this.mapGrid = new GridPane();
    }

    private void drawMap(boolean redraw) {
        mapGrid.setGridLinesVisible(false);
        Label yx = new Label("y/x");
        yx.setFont(new Font(16));
        mapGrid.add(yx, 0, 0);
        GridPane.setHalignment(yx, HPos.CENTER);
        GuiElementBox elementCreator;

        //TODO DISABLE DRAWING Y/X COORDS

        try {
            elementCreator = new GuiElementBox();
//            for (int k = 0; k <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x; k++) {
//                Label idx = new Label("" + (map.getDrawLowerLeft().x + k));
//                idx.setFont(new Font(16));
//                mapGrid.add(idx, k + 1, 0);
//                GridPane.setHalignment(idx, HPos.CENTER);
//            }
//
//            for (int k = 0; k <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y; k++) {
//                Label idx = new Label("" + (map.getDrawUpperRight().y - k));
//                idx.setFont(new Font(16));
//                mapGrid.add(idx, 0, k + 1);
//                GridPane.setHalignment(idx, HPos.CENTER);
//            }


            for (int i = 0; i <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x; i++) {
                for (int j = 0; j <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y; j++) {
                    Vector2d curMapPos = new Vector2d(map.getDrawLowerLeft().x + i, map.getDrawUpperRight().y - j);
                    StackPane sq = elementCreator.mapElementView((IMapElement) map.objectAt(curMapPos), this.map, curMapPos);
                    mapGrid.add(sq, i, j);
                    GridPane.setHalignment(sq, HPos.CENTER);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Couldnt load files");
        }


        if (!redraw) {
            for (int k = 0; k <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x + 1; k++)
                mapGrid.getColumnConstraints().add(new ColumnConstraints(20));
            for (int l = 0; l <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y + 1; l++)
                mapGrid.getRowConstraints().add(new RowConstraints(20));
        }

    }

    public void start(Stage primaryStage) throws Exception {

        Label title = new Label("Life Simulation");
        title.setFont(new Font("Helvetica", 32));
        title.setAlignment(Pos.BASELINE_RIGHT);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setPadding(new Insets(10, 10, 10, 10));

        Label info1 = new Label("Enter your parameters:");
        info1.setFont(new Font("Helvetica", 16));
        info1.setAlignment(Pos.CENTER_RIGHT);

        Button setParamsButton = new Button("Set parameters");
        setParamsButton.setPadding(new Insets(10, 10, 10, 10));

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        startButton.setPadding(new Insets(20, 30, 20, 30));
        stopButton.setPadding(new Insets(20, 30, 20, 30));

        // <<- INPUT AND LABELS START ->>
        TextField mapWidthTf = new TextField("20");
        TextField mapHeightTf = new TextField("20");
        TextField animalsAmountTf = new TextField("20");
        TextField startEnergyTf = new TextField("20");
        TextField moveEnergyTf = new TextField("1");
        TextField plantEnergyTf = new TextField("20");
        TextField jungleRatioTf = new TextField("0.5");
        TextField moveDelayTf = new TextField("200");
        mapWidthTf.setMaxWidth(60);
        mapHeightTf.setMaxWidth(60);
        animalsAmountTf.setMaxWidth(60);
        startEnergyTf.setMaxWidth(60);
        moveEnergyTf.setMaxWidth(60);
        plantEnergyTf.setMaxWidth(60);
        jungleRatioTf.setMaxWidth(60);
        moveDelayTf.setMaxWidth(60);
        Label mapWidth = new Label("Map width: ");
        Label mapHeight = new Label("Map height: ");
        Label animalsAmount = new Label("Animals amount: ");
        Label startEnergy = new Label("Start energy: ");
        Label moveEnergy = new Label("Move energy: ");
        Label plantEnergy = new Label("Plant energy: ");
        Label jungleRatio = new Label("Jungle ratio: ");
        Label moveDelay = new Label("Movement delay [ms]: ");
        HBox var1 = new HBox(mapWidth, mapWidthTf);
        HBox var2 = new HBox(mapHeight, mapHeightTf);
        HBox var3 = new HBox(animalsAmount, animalsAmountTf);
        HBox var4 = new HBox(startEnergy, startEnergyTf);
        HBox var5 = new HBox(moveEnergy, moveEnergyTf);
        HBox var6 = new HBox(plantEnergy, plantEnergyTf);
        HBox var7 = new HBox(jungleRatio, jungleRatioTf);
        HBox var8 = new HBox(moveDelay, moveDelayTf);
        var1.setAlignment(Pos.BASELINE_RIGHT);
        var2.setAlignment(Pos.BASELINE_RIGHT);
        var3.setAlignment(Pos.BASELINE_RIGHT);
        var4.setAlignment(Pos.BASELINE_RIGHT);
        var5.setAlignment(Pos.BASELINE_RIGHT);
        var6.setAlignment(Pos.BASELINE_RIGHT);
        var7.setAlignment(Pos.BASELINE_RIGHT);
        var8.setAlignment(Pos.BASELINE_RIGHT);
        // <<- INPUT AND LABELS END ->>
        VBox inputBox = new VBox(info1, var1, var2, var3, var4, var5, var6, var7, var8, setParamsButton);
        inputBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox startStopButtons = new HBox(startButton, stopButton);
        VBox entryScreenBox = new VBox(title, inputBox, startStopButtons);

        entryScreenBox.setPadding(new Insets(30, 30, 30, 30));
        entryScreenBox.setMaxWidth(300);


        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Total animals per Era");
        this.animalsChartSeries.setName("Era");
        lineChart.getData().add(this.animalsChartSeries);
        HBox dataBox = new HBox(lineChart);
        HBox mapBox = new HBox(this.mapGrid);
        VBox simulationScreenBox = new VBox(mapBox, dataBox, startStopButtons);
        simulationScreenBox.setVisible(false);

        VBox appBox = new VBox(entryScreenBox, simulationScreenBox);
        mapGrid.setHgap(0);
        mapGrid.setVgap(0);

        Scene scene = new Scene(appBox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


        startButton.setOnAction(ev -> {
            this.engine.startEngine();
        });

        setParamsButton.setOnAction(ev -> {
            this.map = new GrassField(
                    Integer.parseInt(mapWidthTf.getText()),
                    Integer.parseInt(mapHeightTf.getText()),
                    Integer.parseInt(plantEnergyTf.getText()),
                    Float.parseFloat(jungleRatioTf.getText()));
            this.engine = new SimulationEngine(
                    Integer.parseInt(animalsAmountTf.getText()),
                    Integer.parseInt(startEnergyTf.getText()),
                    Integer.parseInt(moveEnergyTf.getText()),
                    this.map);
            this.engine.addObserver(this);
            engine.setMoveDelay(Integer.parseInt(moveDelayTf.getText()));


            drawMap(false);

            Thread engineThread = new Thread(this.engine);
            engineThread.start();

            entryScreenBox.setVisible(false);
            entryScreenBox.setManaged(false);
            simulationScreenBox.setVisible(true);
        });

        stopButton.setOnAction(ev -> {
            this.engine.stopEngine();
        });
    }


    @Override
    public void animalMoved() {
        Platform.runLater(() -> {
            mapGrid.getChildren().clear();
            drawMap(true);

            this.eraCount++;

            this.animalsChartSeries.getData().add(new XYChart.Data(this.eraCount, this.engine.countAnimals()));

            // TODO: complete other charts!!


        });
    }


}
