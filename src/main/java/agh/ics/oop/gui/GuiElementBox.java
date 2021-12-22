package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    Image imageGrass = null;
    Image imageEmpty = null;
    Image imageAnimal = null;
    Image imageJungle = null;


    public GuiElementBox() throws FileNotFoundException {
        try {
            this.imageGrass = new Image(new FileInputStream("src/main/resources/grass.png"));
            this.imageAnimal = new Image(new FileInputStream("src/main/resources/animal.png"));
            this.imageEmpty = new Image(new FileInputStream("src/main/resources/empty.png"));
            this.imageJungle = new Image(new FileInputStream("src/main/resources/jungletile.png"));

        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't load files -> " + ex);
        }

    }

    public StackPane mapElementView(IMapElement mapElement, GrassField map, Vector2d pos) {
        ImageView elementView;
        ImageView elementViewEmpty;
        if (!map.isJungleTile(pos))
             elementViewEmpty = new ImageView(imageEmpty);
        else
            elementViewEmpty = new ImageView(imageJungle);
        if (mapElement instanceof Animal a) {
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
            if (a.getEnergy()<0.25*a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(-1,0,0,0));
            else if (a.getEnergy()<0.5*a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(-0.6,0,0,0));
            else if (a.getEnergy()<0.75*a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(-0.2,0,0,0));
            else if(a.getEnergy()<1.25*a.getStartEnergy() && a.getEnergy()>a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(0.2,0,0,0));
            else if(a.getEnergy()<1.5*a.getStartEnergy() && a.getEnergy()>a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(0.4,0,0,0));
            else if(a.getEnergy()<1.75*a.getStartEnergy() && a.getEnergy()>a.getStartEnergy())
                elementView.setEffect(new ColorAdjust(0.6,0,0,0));
        } else if (mapElement instanceof Grass){
            elementView = new ImageView(imageGrass);
        }
        else{
            elementView = new ImageView(imageEmpty);
            elementView.setImage(null);
        }
        elementView.setFitHeight(20);
        elementView.setFitWidth(20);


        elementViewEmpty.setFitHeight(20);
        elementViewEmpty.setFitWidth(20);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(elementViewEmpty, elementView);
        return stackPane;

    }
}
