package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class App extends Application {


    private AbstractWorldMap map;
    private GridPane mapGrid;

    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        MoveDirection[] directions = new OptionsParser().parse(args);
        this.map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(0, 0)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);

    }

    private void drawMap() {
        this.mapGrid = new GridPane();
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
//                    Label sq = new Label(map.objectAt(curMapPos).toString());
//                    sq.setFont(new Font(16));
                        VBox sq = elementCreator.mapElementView((IMapElement) map.objectAt(curMapPos));
                        mapGrid.add(sq, i + 1, j + 1);
                        GridPane.setHalignment(sq, HPos.CENTER);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Couldnt load files");
        }

        for (int k = 0; k <= map.getDrawUpperRight().x - map.getDrawLowerLeft().x + 1; k++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));

        }
        for (int l = 0; l <= map.getDrawUpperRight().y - map.getDrawLowerLeft().y + 1; l++) {

            mapGrid.getRowConstraints().add(new RowConstraints(30));

        }
        mapGrid.setGridLinesVisible(true);
    }

    public void start(Stage primaryStage) {
        drawMap();
        Scene scene = new Scene(mapGrid, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
