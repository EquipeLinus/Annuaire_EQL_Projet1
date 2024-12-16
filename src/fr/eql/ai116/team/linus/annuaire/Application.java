package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.view.AnchorPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.GridPaneSearchStagiaires;
import fr.eql.ai116.team.linus.annuaire.view.HBoxAdmin;
import fr.eql.ai116.team.linus.annuaire.view.InitializeTxtPanel;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application extends javafx.application.Application {

    private static final Logger log = LogManager.getLogger();

    private double width = 1500;
    private double height = 900;

    private Administrator account = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, width, height);

        /**
         * Center panel
         */

        BorderPane centerPane = new BorderPane();
        TableView<Stagiaire> table = new TableView<Stagiaire>();

        AnchorPaneViewStagiaire borderPane = new AnchorPaneViewStagiaire(table);
        centerPane.setCenter(borderPane);

        /**
         * Top panel
         */

        HBox topPane = new HBox();
        topPane.setPrefSize(width, height /6);

        GridPaneSearchStagiaires leftTopPane = new GridPaneSearchStagiaires(borderPane);
        leftTopPane.setPrefSize(width /2, height /4);
        leftTopPane.setStyle("-fx-background-color: blue");

        Pane rightTopPane = new Pane();
        rightTopPane.setPrefSize(width /2, height /4);
        rightTopPane.setStyle("-fx-background-color: purple");

        topPane.getChildren().addAll(leftTopPane,rightTopPane);

        /**
         * Bottom Panel
         */

        HBoxAdmin bottomPane = new HBoxAdmin(table, account);
        bottomPane.setPrefSize(width, height /20);

        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        InitializeTxtPanel init = new InitializeTxtPanel();
        Scene secondScene = new Scene(init, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        newWindow.show();


        stage.setTitle("Application stagiaire EQL");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
