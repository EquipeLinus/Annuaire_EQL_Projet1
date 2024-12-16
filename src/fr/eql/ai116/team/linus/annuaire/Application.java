package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.view.BorderPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.GridPaneSearchStagiaires;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application extends javafx.application.Application {

    private static final Logger log = LogManager.getLogger();

    private double width = 1500;
    private double height = 900;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, width, height);


        /** Top panel
         *
         */

        HBox topPane = new HBox();
        topPane.setPrefSize(width, height /6);

        Pane leftTopPane = new Pane();
        leftTopPane.setPrefSize(width /2, height /4);
        leftTopPane.setStyle("-fx-background-color: blue");

        Pane rightTopPane = new Pane();
        rightTopPane.setPrefSize(width /2, height /4);
        rightTopPane.setStyle("-fx-background-color: purple");

        topPane.getChildren().addAll(leftTopPane,rightTopPane);




        /**
         * Center panel
         */

        Pane centerPane = new Pane();
        centerPane.setPrefSize(width, height /2);


        TableView<Stagiaire> table = new TableView<Stagiaire>();


        BorderPaneViewStagiaire borderPane = new BorderPaneViewStagiaire(table);

        centerPane.getChildren().add(borderPane);


        /**
         * Bottom Panel
         */
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(width, height /8);
        bottomPane.setStyle("-fx-background-color: green");


        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);


        stage.setTitle("Application stagiaire EQL");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();




    }
}
