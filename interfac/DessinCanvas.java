/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfac;

import interfac.Figure;
import interfac.Groupe;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 *
 * @author lemei
 */
public class DessinCanvas extends Pane {
    
    private MainPanel main;
    

    private Canvas realCanvas;

    public DessinCanvas(MainPanel main) {
        this.main = main;
        this.realCanvas = new Canvas(this.getWidth(), this.getHeight());
        this.realCanvas.setRotate(Math.PI);;
        this.getChildren().add(this.realCanvas);
        this.realCanvas.heightProperty().bind(this.heightProperty());
        this.realCanvas.heightProperty().addListener((o) -> {
            this.redrawAll();
        });
        this.realCanvas.widthProperty().bind(this.widthProperty());
        this.realCanvas.widthProperty().addListener((o) -> {
            this.redrawAll();
        });
        this.realCanvas.setOnMouseClicked((t) -> {
            Controleur control = this.main.getControleur();
            control.clicDansZoneDessin(t);
        });
        this.redrawAll();
    }
    
    public void redrawAll() {
        GraphicsContext context = this.realCanvas.getGraphicsContext2D();
        Groupe model = this.main.getModel();
        model.dessine(context);
        List<Figure> select = this.main.getControleur().getSelection();
        if (! select.isEmpty()) {
            for (Figure f : select) {
                f.dessineSelection(context);
            }
        }
    }

    public void clear() {
       this.realCanvas = new Canvas(this.getWidth(), this.getHeight());

}
}
