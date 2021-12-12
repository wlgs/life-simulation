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
        this.map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(0, 0)};
        this.engine = new SimulationEngine(map, positions);
        this.engine.addObserver(this);
        engine.setMoveDelay(300);
        this.mapGrid = new GridPane();
    }

    private void drawMap(boolean redraw) {
        mapGrid.setGridLinesVisible(false);
        mapGrid.setGridLinesVisible(true);
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
                    if (map.objectAt(curMapPos) != null) {
                        VBox sq = elementCreator.mapElementView((IMapElement) map.objectAt(curMapPos));
                        mapGrid.add(sq, i + 1, j + 1);
                        GridPane.setHalignment(sq, HPos.CENTER);
                    }
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

        TextField movesInput = new TextField();
        Button startButton = new Button("Run moves");
        VBox inputBox = new VBox(movesInput, startButton);
        VBox appBox = new VBox(this.mapGrid, inputBox);
        mapGrid.setAlignment(Pos.CENTER);
        inputBox.setAlignment(Pos.CENTER);
        appBox.setAlignment(Pos.CENTER);
        movesInput.setMaxWidth(80);


        drawMap(false);
        Scene scene = new Scene(appBox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        startButton.setOnAction(ev -> {
            String[] args = movesInput.getText().split("");
            MoveDirection[] directions = new OptionsParser().parse(args);
            engine.setMoves(directions);
            Thread engineThread = new Thread(engine);
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
