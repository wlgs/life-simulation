package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class App extends Application implements IAnimalObserver {


    private GridPane mapGrid;
    private SimulationEngine engine;
    private GrassField map;


    public void init() {
//        this.map = new GrassField(10);
//        Vector2d[] positions = {new Vector2d(0, 0),new Vector2d(5, 5),new Vector2d(3, 3)};
//        this.engine = new SimulationEngine(map, positions);
//        this.engine.addObserver(this);
//        engine.setMoveDelay(100);
        this.mapGrid = new GridPane();
    }

    private void drawMap(boolean redraw) {
        mapGrid.setGridLinesVisible(false);
        Label yx = new Label("y/x");
        yx.setFont(new Font(16));
        mapGrid.add(yx, 0, 0);
        GridPane.setHalignment(yx, HPos.CENTER);
        GuiElementBox elementCreator;
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
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Couldnt load files");
        }


        if (!redraw) {
            for (int k = 0; k <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x + 1; k++)
                mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
            for (int l = 0; l <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y + 1; l++)
                mapGrid.getRowConstraints().add(new RowConstraints(30));
        }

    }

    public void start(Stage primaryStage) throws Exception {

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        HBox buttonBox = new HBox(startButton,stopButton);

        TextField mapWidthTf = new TextField("50");
        TextField mapHeightTf = new TextField("30");
        TextField animalsAmountTf = new TextField("5");
        TextField startEnergyTf = new TextField("40");
        TextField moveEnergyTf = new TextField("2");
        TextField plantEnergyTf = new TextField("20");
        TextField jungleRatioTf = new TextField("0.5");

        Label mapWidth = new Label("Map width:");
        Label mapHeight = new Label("Map height:");
        Label animalsAmount = new Label("Animals amount:");
        Label startEnergy = new Label("Start energy:");
        Label moveEnergy = new Label("Move energy:");
        Label plantEnergy = new Label("Plant energy:");
        Label jungleRatio = new Label("Jungle ratio:");

        HBox var1 = new HBox(mapWidth, mapWidthTf);
        HBox var2 = new HBox(mapHeight, mapHeightTf);
        HBox var3 = new HBox(animalsAmount, animalsAmountTf);
        HBox var4 = new HBox(startEnergy, startEnergyTf);
        HBox var5 = new HBox(moveEnergy, moveEnergyTf);
        HBox var6 = new HBox(plantEnergy, plantEnergyTf);
        HBox var7 = new HBox(jungleRatio, jungleRatioTf);

        VBox inputBox = new VBox(var1,var2,var3,var4,var5,var6,var7,buttonBox);

        HBox appBox = new HBox(this.mapGrid, inputBox);

//        mapGrid.setAlignment(Pos.CENTER);
        mapGrid.setHgap(0);
        mapGrid.setVgap(0);
//        buttonBox.setAlignment(Pos.CENTER);
//        appBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(0,0,0,10));


        Scene scene = new Scene(appBox, 1280, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        startButton.setOnAction(ev -> {
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
            engine.setMoveDelay(100);
            drawMap(false);
            Thread engineThread = new Thread(this.engine);
            engineThread.start();
        });
    }

    @Override
    public void animalMoved() {
        Platform.runLater(() -> {
            mapGrid.getChildren().clear();
            drawMap(true);
        });
    }


}
