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


    private GridPane mapGrid1 = new GridPane();
    private GridPane mapGrid2 = new GridPane();
    private SimulationEngine engine1;
    private SimulationEngine engine2;
    private GrassField map1;
    private GrassField map2;
    private int eraCount = 0;

    private XYChart.Series animalsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series plantsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgEnergyChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgKidsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgLifeSpanChartSeriesW1 = new XYChart.Series();

    private XYChart.Series animalsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series plantsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgEnergyChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgKidsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgLifeSpanChartSeriesW2 = new XYChart.Series();

    public void init() {
    }

    private void drawMap(boolean redraw, GrassField map, GridPane mapGrid) {
        mapGrid.setGridLinesVisible(false);
        Label yx = new Label("y/x");
        yx.setFont(new Font(16));
        mapGrid.add(yx, 0, 0);
        GridPane.setHalignment(yx, HPos.CENTER);
        GuiElementBox elementCreator;
        try {
            elementCreator = new GuiElementBox();
            for (int i = 0; i <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x; i++) {
                for (int j = 0; j <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y; j++) {
                    Vector2d curMapPos = new Vector2d(map.getDrawLowerLeft().x + i, map.getDrawUpperRight().y - j);
                    StackPane sq = elementCreator.mapElementView((IMapElement) map.objectAt(curMapPos), map, curMapPos);
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


        final NumberAxis xAxisW1 = new NumberAxis();
        final NumberAxis yAxisW1 = new NumberAxis();
        final LineChart<Number, Number> lineChartW1 = new LineChart<>(xAxisW1, yAxisW1);
        lineChartW1.setTitle("World 1. Statistics");
        this.animalsChartSeriesW1.setName("Total animals");
        this.plantsChartSeriesW1.setName("Total plants");
        this.avgEnergyChartSeriesW1.setName("AVG Energy");
        this.avgKidsChartSeriesW1.setName("AVG Children of Alive Animals");
        this.avgLifeSpanChartSeriesW1.setName("AVG Lifespan of Dead Animals");
        lineChartW1.getData().add(this.animalsChartSeriesW1);
        lineChartW1.getData().add(this.plantsChartSeriesW1);
        lineChartW1.getData().add(this.avgEnergyChartSeriesW1);
        lineChartW1.getData().add(this.avgKidsChartSeriesW1);
        lineChartW1.getData().add(this.avgLifeSpanChartSeriesW1);

        final NumberAxis xAxisW2 = new NumberAxis();
        final NumberAxis yAxisW2 = new NumberAxis();
        final LineChart<Number, Number> lineChartW2 = new LineChart<>(xAxisW2, yAxisW2);
        lineChartW2.setTitle("World 2. Statistics");
        this.animalsChartSeriesW2.setName("Total animals");
        this.plantsChartSeriesW2.setName("Total plants");
        this.avgEnergyChartSeriesW2.setName("AVG Energy");
        this.avgKidsChartSeriesW2.setName("AVG Children of Alive Animals");
        this.avgLifeSpanChartSeriesW2.setName("AVG Lifespan of Dead Animals");
        lineChartW2.getData().add(this.animalsChartSeriesW2);
        lineChartW2.getData().add(this.plantsChartSeriesW2);
        lineChartW2.getData().add(this.avgEnergyChartSeriesW2);
        lineChartW2.getData().add(this.avgKidsChartSeriesW2);
        lineChartW2.getData().add(this.avgLifeSpanChartSeriesW2);

        HBox dataBox = new HBox(lineChartW1, lineChartW2);
        Label map1Title = new Label("World 1. [Not foldable]");
        Label map2Title = new Label("World 1. [Foldable]");
        map1Title.setFont(new Font("Helvetica", 26));
        map2Title.setFont(new Font("Helvetica", 26));
        VBox map1Box = new VBox(map1Title, this.mapGrid1);
        VBox map2Box = new VBox(map2Title, this.mapGrid2);
        HBox mapBox = new HBox(map1Box, map2Box);
        VBox simulationScreenBox = new VBox(mapBox, dataBox, startStopButtons);
        simulationScreenBox.setVisible(false);

        VBox appBox = new VBox(entryScreenBox, simulationScreenBox);

        Scene scene = new Scene(appBox, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();


        startButton.setOnAction(ev -> {
            this.engine1.startEngine();
            this.engine2.startEngine();
        });

        setParamsButton.setOnAction(ev -> {
            this.map1 = new GrassField(
                    Integer.parseInt(mapWidthTf.getText()),
                    Integer.parseInt(mapHeightTf.getText()),
                    Integer.parseInt(plantEnergyTf.getText()),
                    Float.parseFloat(jungleRatioTf.getText()),
                    false);
            this.map2 = new GrassField(
                    Integer.parseInt(mapWidthTf.getText()),
                    Integer.parseInt(mapHeightTf.getText()),
                    Integer.parseInt(plantEnergyTf.getText()),
                    Float.parseFloat(jungleRatioTf.getText()),
                    true);
            this.engine1 = new SimulationEngine(
                    Integer.parseInt(animalsAmountTf.getText()),
                    Integer.parseInt(startEnergyTf.getText()),
                    Integer.parseInt(moveEnergyTf.getText()),
                    this.map1);
            this.engine2 = new SimulationEngine(
                    Integer.parseInt(animalsAmountTf.getText()),
                    Integer.parseInt(startEnergyTf.getText()),
                    Integer.parseInt(moveEnergyTf.getText()),
                    this.map2);
            engine1.setMoveDelay(Integer.parseInt(moveDelayTf.getText()));
            engine2.setMoveDelay(Integer.parseInt(moveDelayTf.getText()));
            this.engine1.addObserver(this);
            this.engine2.addObserver(this);



            drawMap(false, map1, this.mapGrid1);
            drawMap(false, map2, this.mapGrid2);

            ThreadGroup simThreads = new ThreadGroup("Simulation Threads");
            Thread engine1Thread = new Thread(simThreads, this.engine1);
            Thread engine2Thread = new Thread(simThreads, this.engine2);
            engine1Thread.start();
            engine2Thread.start();
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            entryScreenBox.setVisible(false);
            entryScreenBox.setManaged(false);
            simulationScreenBox.setVisible(true);
        });

        stopButton.setOnAction(ev -> {
            this.engine1.stopEngine();
            this.engine2.stopEngine();
        });
    }


    @Override
    public void animalMoved() {
        Platform.runLater(() -> {
            mapGrid1.getChildren().clear();
            mapGrid2.getChildren().clear();
            drawMap(true, this.map1, this.mapGrid1);
            drawMap(true, this.map2, this.mapGrid2);

            this.eraCount++;

            this.animalsChartSeriesW1.getData().add(new XYChart.Data(this.eraCount, this.engine1.countAnimals()));
            this.plantsChartSeriesW1.getData().add(new XYChart.Data(this.eraCount, this.map1.getTotalGrassAmount()));
            this.avgEnergyChartSeriesW1.getData().add(new XYChart.Data(this.eraCount, this.engine1.getAvgEnergy()));
            this.avgKidsChartSeriesW1.getData().add(new XYChart.Data(this.eraCount, this.engine1.getAvgChildrenAmount()));
            this.avgLifeSpanChartSeriesW1.getData().add(new XYChart.Data(this.eraCount, this.engine1.getAvgLifeSpan()));

            this.animalsChartSeriesW2.getData().add(new XYChart.Data(this.eraCount, this.engine2.countAnimals()));
            this.plantsChartSeriesW2.getData().add(new XYChart.Data(this.eraCount, this.map2.getTotalGrassAmount()));
            this.avgEnergyChartSeriesW2.getData().add(new XYChart.Data(this.eraCount, this.engine2.getAvgEnergy()));
            this.avgKidsChartSeriesW2.getData().add(new XYChart.Data(this.eraCount, this.engine2.getAvgChildrenAmount()));
            this.avgLifeSpanChartSeriesW2.getData().add(new XYChart.Data(this.eraCount, this.engine2.getAvgLifeSpan()));

        });
    }


}
