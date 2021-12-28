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
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class App extends Application implements IAnimalObserver {


    private GridPane mapGrid1 = new GridPane();
    private GridPane mapGrid2 = new GridPane();
    private SimulationEngine engine1;
    private SimulationEngine engine2;
    private GrassField map1;
    private GrassField map2;
    private ExportData exportData = new ExportData();
    private int eraCount = 0;

    private XYChart.Series animalsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series plantsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgEnergyChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgKidsChartSeriesW1 = new XYChart.Series();
    private XYChart.Series avgLifeSpanChartSeriesW1 = new XYChart.Series();
    private ArrayList<XYChart.Series> chartSeriesArrW1;


    private XYChart.Series animalsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series plantsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgEnergyChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgKidsChartSeriesW2 = new XYChart.Series();
    private XYChart.Series avgLifeSpanChartSeriesW2 = new XYChart.Series();
    private ArrayList<XYChart.Series> chartSeriesArrW2;

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
        Button exportDataButton = new Button("Export current chart data");
        exportDataButton.setDisable(true);

        Insets btnPadding = new Insets(20,30,20,30);
        startButton.setPadding(btnPadding);
        stopButton.setPadding(btnPadding);
        exportDataButton.setPadding(btnPadding);

        // <<- INPUT AND LABELS START ->>
        TextField mapWidthTf = new TextField("25");
        TextField mapHeightTf = new TextField("20");
        TextField animalsAmountTf = new TextField("40");
        TextField startEnergyTf = new TextField("60");
        TextField moveEnergyTf = new TextField("2");
        TextField plantEnergyTf = new TextField("60");
        TextField jungleRatioTf = new TextField("0.1");
        TextField moveDelayTf = new TextField("100");
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
        Label moveDelay = new Label("Render delay [ms]: ");
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
        CheckBox magicFoldedCb = new CheckBox("Can magic happen on folded map?");
        CheckBox magicNotFoldedCb = new CheckBox("Can magic happen on regular map?");
        magicFoldedCb.setPadding(new Insets(10, 0, 10, 0));
        magicNotFoldedCb.setPadding(new Insets(10, 0, 10, 0));
        VBox inputBox = new VBox(info1, var1, var2, var3, var4, var5, var6, var7, var8, magicFoldedCb, magicNotFoldedCb, setParamsButton);
        inputBox.setAlignment(Pos.BASELINE_RIGHT);
        // <<- INPUT AND LABELS END ->>

        Label guideTitle = new Label("Guide to Life Simulation");
        Label guideInfo1 = new Label("1. Animals turn green when they overeat.");
        Label guideInfo2 = new Label("2. Animals turn blue when they are about to die.");
        Label guideInfo3 = new Label("3. Animals can reproduce if they are strong enough.");
        Label guideInfo4 = new Label("4. Reproduced animals act similarly to his parents.");
        Label guideInfo5 = new Label("5. Magic = clone 5 animals, when there are 5 animals left.");
        Label guideInfo6 = new Label("6. Magic can only happen 3 times.");
        Label guideInfo7 = new Label("7. Be respectful about your machine possibilities.");

        VBox guideBox = new VBox(guideTitle, guideInfo1, guideInfo2, guideInfo3, guideInfo4, guideInfo5, guideInfo6, guideInfo7);
        guideBox.setPadding(btnPadding);

        HBox startStopButtons = new HBox(startButton, stopButton, exportDataButton);
        VBox entryScreenBox = new VBox(title, inputBox, guideBox, startStopButtons);

        entryScreenBox.setPadding(new Insets(30, 30, 30, 30));
        entryScreenBox.setMaxWidth(400);

        // <<- CHARTS START ->>
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

        this.chartSeriesArrW1 = new ArrayList<XYChart.Series>() {
            {
                add(animalsChartSeriesW1);
                add(plantsChartSeriesW1);
                add(avgEnergyChartSeriesW1);
                add(avgKidsChartSeriesW1);
                add(avgLifeSpanChartSeriesW1);
            }
        };


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

        this.chartSeriesArrW2 = new ArrayList<XYChart.Series>() {
            {
                add(animalsChartSeriesW2);
                add(plantsChartSeriesW2);
                add(avgEnergyChartSeriesW2);
                add(avgKidsChartSeriesW2);
                add(avgLifeSpanChartSeriesW2);
            }
        };

        // <<- CHARTS END ->>


        HBox dataBox = new HBox(lineChartW1, lineChartW2);
        Label map1Title = new Label("World 1. [Folded]");
        Label map2Title = new Label("World 1. [Not folded]");
        map1Title.setFont(new Font("Helvetica", 26));
        map2Title.setFont(new Font("Helvetica", 26));
        VBox map1Box = new VBox(map1Title, this.mapGrid1);
        VBox map2Box = new VBox(map2Title, this.mapGrid2);
        HBox mapBox = new HBox(map1Box, map2Box);
        mapBox.setAlignment(Pos.CENTER);
        dataBox.setAlignment(Pos.CENTER);
        startStopButtons.setAlignment(Pos.CENTER);
        startStopButtons.setPadding(new Insets(15, 15, 15, 15));
        VBox simulationScreenBox = new VBox(mapBox, dataBox, startStopButtons);
        simulationScreenBox.setVisible(false);

        VBox appBox = new VBox(entryScreenBox, simulationScreenBox);
        appBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(appBox, 1280, 960);
        primaryStage.getIcons().add(new Image(new FileInputStream("src/main/resources/animal.png")));
        primaryStage.setTitle("Life simulation");
        primaryStage.setScene(scene);
        primaryStage.show();


        startButton.setOnAction(ev -> {
            this.engine1.startEngine();
            this.engine2.startEngine();
            exportDataButton.setDisable(true);
        });

        setParamsButton.setOnAction(ev -> {
            this.map1 = new GrassField(
                    Integer.parseInt(mapWidthTf.getText()),
                    Integer.parseInt(mapHeightTf.getText()),
                    Integer.parseInt(plantEnergyTf.getText()),
                    Float.parseFloat(jungleRatioTf.getText()),
                    true,
                    magicFoldedCb.isSelected());
            this.map2 = new GrassField(
                    Integer.parseInt(mapWidthTf.getText()),
                    Integer.parseInt(mapHeightTf.getText()),
                    Integer.parseInt(plantEnergyTf.getText()),
                    Float.parseFloat(jungleRatioTf.getText()),
                    false,
                    magicNotFoldedCb.isSelected());
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
            entryScreenBox.setVisible(false);
            entryScreenBox.setManaged(false);
            simulationScreenBox.setVisible(true);
        });

        stopButton.setOnAction(ev -> {
            this.engine1.stopEngine();
            this.engine2.stopEngine();
            exportDataButton.setDisable(false);
        });

        exportDataButton.setOnAction(ev -> {
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION, "Successfully exported data to dataWorld1.csv and dataWorld2.csv");
            alertSuccess.setTitle("Success!");
            Alert alertFail = new Alert(Alert.AlertType.INFORMATION, "Could not export the chart data. Check console for error log.");
            alertFail.setTitle("Failure!");
            try {
                this.exportData.exportDataFromChartSeries(this.chartSeriesArrW1, "dataWorld1.csv");
                this.exportData.exportDataFromChartSeries(this.chartSeriesArrW2, "dataWorld2.csv");
                alertSuccess.show();
            } catch (IOException ex) {
                System.out.println("Could not export the chart data. -> " + ex);
                alertFail.show();
            }
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

    public void magicHappened(boolean folded) {
        Platform.runLater(() -> {
            System.out.println("magic has happened");
            Alert alertFolded = new Alert(Alert.AlertType.INFORMATION, "Magic has happened on left map [folded]!");
            Alert alertNotFolded = new Alert(Alert.AlertType.INFORMATION, "Magic has happened on right map [not folded]!");
            if (folded)
                alertFolded.show();
            else
                alertNotFolded.show();
        });
    }


}
