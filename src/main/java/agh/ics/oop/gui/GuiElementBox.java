package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    Image imageGrass = null;
    Image imageEmpty = null;
    Image imageAnimal = null;


    public GuiElementBox() throws FileNotFoundException {
        try {
            this.imageGrass = new Image(new FileInputStream("src/main/resources/grass.png"));
            this.imageAnimal = new Image(new FileInputStream("src/main/resources/animal.png"));
            this.imageEmpty = new Image(new FileInputStream("src/main/resources/empty.png"));

        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't load files -> " + ex);
        }

    }

    public VBox mapElementView(IMapElement mapElement) {
        ImageView elementView;
        if (mapElement instanceof Animal) {
            elementView = new ImageView(imageAnimal);
            switch (((Animal) mapElement).getDirection()) {
                case NORTH -> elementView.setRotate(elementView.getRotate() + 0);
                case EAST -> elementView.setRotate(elementView.getRotate() + 90);
                case WEST -> elementView.setRotate(elementView.getRotate() + 270);
                case SOUTH -> elementView.setRotate(elementView.getRotate() + 180);
                case SOUTHEAST -> elementView.setRotate(elementView.getRotate() + 135);
                case SOUTHWEST -> elementView.setRotate(elementView.getRotate() + 225);
                case NORTHWEST -> elementView.setRotate(elementView.getRotate() + 315);
                case NORTHEAST -> elementView.setRotate(elementView.getRotate() + 45);
            };
        } else if (mapElement instanceof Grass){
            elementView = new ImageView(imageGrass);
        }
        else{
            elementView = new ImageView(imageEmpty);
        }
        elementView.setFitWidth(30);
        elementView.setFitHeight(30);
        VBox elementVBox = new VBox();
        elementVBox.setPadding(new Insets(0,0,0,0));
        elementVBox.getChildren().add(elementView);
        elementVBox.setAlignment(Pos.CENTER);
        return elementVBox;

    }
}
