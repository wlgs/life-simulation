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
            for (int k = 0; k <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x; k++) {
                Label idx = new Label("" + (map.getDrawLowerLeft().x + k));
                idx.setFont(new Font(16));
                mapGrid.add(idx, k + 1, 0);
                GridPane.setHalignment(idx, HPos.CENTER);
            }


            for (int k = 0; k <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y; k++) {
                Label idx = new Label("" + (map.getDrawUpperRight().y - k));
                idx.setFont(new Font(16));
                mapGrid.add(idx, 0, k + 1);
                GridPane.setHalignment(idx, HPos.CENTER);
            }


            for (int i = 0; i <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x; i++) {
                for (int j = 0; j <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y; j++) {
                    Vector2d curMapPos = new Vector2d(map.getDrawLowerLeft().x + i, map.getDrawUpperRight().y - j);
                    VBox sq = elementCreator.mapElementView((IMapElement) map.objectAt(curMapPos));
                    mapGrid.add(sq, i + 1, j + 1);
                    GridPane.setHalignment(sq, HPos.CENTER);

                    //TODO APPLY COLOR FILTER TO ANIMAL BASED ON HIS ENERGY LEVEL
                    //TODO APPLY FLOOR COLOR BASED ON JUNGLE/SAVANNA, MAKE METHOD IS SAVANNA?

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

        Button startButton = new Button("Start");
        Button setParamsButton = new Button("Set parameters");
        Button stopButton = new Button("Stop");

        HBox buttonBox = new HBox(setParamsButton, startButton,stopButton);

        TextField mapWidthTf = new TextField("50");
        TextField mapHeightTf = new TextField("30");
        TextField animalsAmountTf = new TextField("5");
        TextField startEnergyTf = new TextField("40");
        TextField moveEnergyTf = new TextField("2");
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

        VBox inputBox = new VBox(var1,var2,var3,var4,var5,var6,var7,var8);
        VBox input2Box = new VBox(inputBox, buttonBox);
        HBox screenBox = new HBox(this.mapGrid, input2Box);



        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Total animals per Era");
        this.animalsChartSeries.setName("Era");
        lineChart.getData().add(this.animalsChartSeries);

        lineChart.setVisible(false);
        startButton.setVisible(false);
        stopButton.setVisible(false);

        HBox dataBox = new HBox(lineChart);
        VBox appBox = new VBox(screenBox, dataBox);

        mapGrid.setHgap(0);
        mapGrid.setVgap(0);
        inputBox.setPadding(new Insets(0,0,0,20));

        Scene scene = new Scene(appBox, 1280, 800);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
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


            // TODO: draw map normally without redraw=false/true
            drawMap(false);

            Thread engineThread = new Thread(this.engine);
            engineThread.start();

            lineChart.setVisible(true);
            startButton.setVisible(true);
            stopButton.setVisible(true);
            setParamsButton.setVisible(false);
            inputBox.setVisible(false);
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
