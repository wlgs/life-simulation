package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    Image imageUp = null;
    Image imageDown = null;
    Image imageRight = null;
    Image imageLeft = null;
    Image imageGrass = null;


    public GuiElementBox() throws FileNotFoundException{
        try{
            this.imageUp = new Image(new FileInputStream("src/main/resources/up.png"));
            this.imageDown = new Image(new FileInputStream("src/main/resources/down.png"));
            this.imageRight = new Image(new FileInputStream("src/main/resources/right.png"));
            this.imageLeft = new Image(new FileInputStream("src/main/resources/left.png"));
            this.imageGrass = new Image(new FileInputStream("src/main/resources/grass.png"));

        } catch (FileNotFoundException ex){
            System.out.println("Couldn't load files -> " + ex);
        }

    }
    public VBox mapElementView(IMapElement mapElement)  {
        Label elementLabel;
        ImageView elementView;
        if (mapElement instanceof Animal) {
            elementLabel = new Label("Z " + mapElement.getPosition());
            elementView = switch (((Animal) mapElement).getDirection()) {
                case NORTH -> new ImageView(imageUp);
                case EAST -> new ImageView(imageRight);
                case WEST -> new ImageView(imageLeft);
                case SOUTH -> new ImageView(imageDown);
            };
        }
        else{
            elementLabel = new Label("Trawa");
            elementView = new ImageView(imageGrass);
        }
        elementView.setFitWidth(20);
        elementView.setFitHeight(20);
        elementLabel.setFont(new Font(10));
        VBox elementVBox = new VBox();
        elementVBox.getChildren().addAll(elementView, elementLabel);
        elementVBox.setAlignment(Pos.CENTER);

        return elementVBox;

    }
}
