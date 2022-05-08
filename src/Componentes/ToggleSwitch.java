package Componentes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Componente de ToggleSwitch izquierda/derecha.
 * 
 * Pieza de código tomada de @TheItachiUchiha en GuitHub.
 * Para más información respecto al módulo remítase al origen:
 * https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75
 * Modificado por: Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 * @author TheItachiUchiha
 */
public class ToggleSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedRigth = new SimpleBooleanProperty(false);

        public ToggleSwitch() {
        init();
        switchedRigth.addListener((a, b, c) -> {
            if (c) {
                label.setText("Dirigido");
                setStyle("-fx-background-color: green;");
                label.toFront();
            } else {
                label.setText("No dirigido");
                setStyle("-fx-background-color: grey;");
                button.toFront();
            }
        });
    }

    private void init() {
        label.setText("No dirigido");

        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedRigth.set(!switchedRigth.get());
        });
        label.setOnMouseClicked((e) -> {
            switchedRigth.set(!switchedRigth.get());
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        setWidth(160);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    } 
    
    public SimpleBooleanProperty switchOnProperty() {
        return switchedRigth;
    }
}
