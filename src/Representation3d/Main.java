package Representation3d;

import TestPersonel.CorpsTest;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.Box;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;

public class Main extends Application {

    private static final int tailleEcranX = 600;
    private static final int tailleEcranY = 600;
    final PhongMaterial redMaterial = new PhongMaterial();
    final PhongMaterial blueMaterial = new PhongMaterial();


    /**
     * methode automatique de lancement
     *
     * @param primaryStage
     * @throws Exception en cas d erreur
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //nom de la page

        Rotate rx = new Rotate();
        { rx.setAxis(Rotate.X_AXIS); }
        Rotate ry = new Rotate();
        { ry.setAxis(Rotate.Y_AXIS); }
        Rotate rz = new Rotate();
        { rz.setAxis(Rotate.Z_AXIS); }

        //creatoin de la couleur rouge
        redMaterial.setSpecularColor(Color.RED);
        redMaterial.setDiffuseColor(Color.RED);

        //creatoin de la couleur bleu
        blueMaterial.setSpecularColor(Color.BLUE);
        blueMaterial.setDiffuseColor(Color.BLUE);

        Sphere sphere = new Sphere(30);
        Box boite = new Box(30,30,20);
        boite.translateXProperty().set(220);
        boite.translateYProperty().set(370);
        boite.setMaterial(redMaterial);

        Cylinder cylindre1  = new Cylinder(20, 80);
        cylindre1.translateXProperty().set(180);
        cylindre1.translateYProperty().set(210);
        cylindre1.setMaterial(blueMaterial);

        sphere.translateXProperty().set(tailleEcranX/2);
        sphere.translateYProperty().set(tailleEcranY/4);

        Camera camera = new PerspectiveCamera();

        Line ligne1 = dessinneLigne(100.0, 100.0, 200.0, 300);
        Line ligne2 = dessinneLigne(200.0, 300.0, 300.0, 320.0);
        //creation de la racine
        Group racine = new Group();

        // ajout des nouveaux elements
        racine.getChildren().addAll(ligne1);
        racine.getChildren().addAll(ligne2);
        racine.getChildren().addAll(sphere);
        racine.getChildren().add(boite);
        racine.getChildren().add(cylindre1);

        //creation de la scene
        Scene scene = new Scene(racine, tailleEcranX, tailleEcranY, Color.WHITE);

        //creation de boutons
        Button boutonAnimation = new Button();
        Button boutonVisualisationDonnee = new Button();
        Button boutonBlocage = new Button();
        Button boutonZoomAvant = new Button();
        Button boutonZoomArriere = new Button();
        Button boutonAvanceCamera = new Button();
        Button boutonReculeCamera = new Button();

        //bouton de camera -
        boutonAvanceCamera.setLayoutX(220);
        boutonAvanceCamera.setLayoutY(20);
        boutonAvanceCamera.setText("camera +");
        boutonAvanceCamera.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("avance camera");
                camera.translateZProperty().set(camera.getTranslateZ() -50);
            }
        });

        //bouton de camera +
        boutonReculeCamera.setLayoutX(320);
        boutonReculeCamera.setLayoutY(20);
        boutonReculeCamera.setText("camera -");
        boutonReculeCamera.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("recule camera");
                camera.translateZProperty().set(camera.getTranslateZ() +20);
            }
        });


        //bouton de zoom +
        boutonZoomAvant.setLayoutX(130);
        boutonZoomAvant.setLayoutY(20);
        boutonZoomAvant.setText("Zoom -");
        boutonZoomAvant.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Zoom -");
                sphere.translateZProperty().set(sphere.getTranslateZ() + 10);
            }
        });

        //bouton de zoom -
        boutonZoomArriere.setLayoutX(60);
        boutonZoomArriere.setLayoutY(20);
        boutonZoomArriere.setText("Zoom +");
        boutonZoomArriere.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Zoom +");
                sphere.translateZProperty().set(sphere.getTranslateZ() - 10);
            }
        });


        //bouton animation qui lancera l'animation du personage
        boutonAnimation.setLayoutX(20);
        boutonAnimation.setLayoutY(500);
        boutonAnimation.setText("Animation");
        boutonAnimation.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Animation");
                CorpsTest corpsTest = new CorpsTest();
                corpsTest.initialisation();
            }
        });

        //bouton de visualisation des donnees
        boutonVisualisationDonnee.setLayoutX(120);
        boutonVisualisationDonnee.setLayoutY(500);
        boutonVisualisationDonnee.setText("Visualisation");
        boutonVisualisationDonnee.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("");
                CorpsTest corpsTest = new CorpsTest();
                corpsTest.initialisation();
            }
        });

        //bouton qui permettra de bloquer pendant l'animation une partie du corps
        boutonBlocage.setLayoutX(230);
        boutonBlocage.setLayoutY(500);
        boutonBlocage.setText("Blocage");
        boutonBlocage.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("Blocage");
                CorpsTest corpsTest = new CorpsTest();
                corpsTest.initialisation();
            }
        });


        //ajout des 3 boutons a la racine de la visualisation
        racine.getChildren().add(boutonAnimation);
        racine.getChildren().add(boutonVisualisationDonnee);
        racine.getChildren().add(boutonBlocage);
        racine.getChildren().add(boutonZoomAvant);
        racine.getChildren().add(boutonZoomArriere);
        racine.getChildren().add(boutonAvanceCamera);
        racine.getChildren().add(boutonReculeCamera);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W: sphere.translateZProperty().set(sphere.getTranslateZ() + 10);
                        boite.translateZProperty().set(sphere.getTranslateZ() + 10);
                        cylindre1.translateZProperty().set(sphere.getTranslateZ() + 10);
                break;
                case A: sphere.translateZProperty().set(sphere.getTranslateZ() - 10);
                        boite.translateZProperty().set(sphere.getTranslateZ() - 10);
                        cylindre1.translateZProperty().set(sphere.getTranslateZ() - 10);
                break;
            }
        });



        //lancement
        scene.setCamera(camera);
        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(0);
        primaryStage.setTitle("Intelligence3D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class Cam extends Group {
        Translate t  = new Translate();
        Translate p  = new Translate();
        Translate ip = new Translate();
        Rotate rx = new Rotate();
        { rx.setAxis(Rotate.X_AXIS); }
        Rotate ry = new Rotate();
        { ry.setAxis(Rotate.Y_AXIS); }
        Rotate rz = new Rotate();
        { rz.setAxis(Rotate.Z_AXIS); }
        Scale s = new Scale();
        public Cam() { super(); getTransforms().addAll(t, p, rx, rz, ry, s, ip); }
    }


    private Line dessinneLigne(double xDebut, double yDebut, double xFin, double yFin) {

        Line ligne = new Line();
        ligne.setStartX(xDebut);
        ligne.setStartY(yDebut);
        ligne.setEndX(xFin);
        ligne.setEndY(yFin);
        return ligne;
    }

    private Circle dessinecercle(double centreX, double centreY, double radius){

        return new Circle(centreX, centreY, radius);
    }

    public static void main(String[] args) {

        //Lancement et ouverture de la fenetre de ressultat
        launch(args);
    }
}
