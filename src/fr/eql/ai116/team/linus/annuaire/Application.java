package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.view.AnchorPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.ConnexionWindow;
import fr.eql.ai116.team.linus.annuaire.view.HBoxAdmin;
import fr.eql.ai116.team.linus.annuaire.view.InitializeTxtPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application extends javafx.application.Application {

    private static final Logger log = LogManager.getLogger();

    private double width = 1500;
    private double height = 900;

    public static Administrator account = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, width, height);

        /**
         * Top panel
         */

        HBox topPane = new HBox();
        topPane.setPrefSize(width, height /6);

        Pane leftTopPane = new Pane();
        leftTopPane.setPrefSize(width /2, height /4);
        leftTopPane.setStyle("-fx-background-color: blue");

        Pane rightTopPane = new Pane();
        rightTopPane.setPrefSize(width /2, height /4);

        HBox btnPanel1 = new HBox(20);
        VBox btnPanel2 = new VBox(15);



        Button btnTutorial = new Button("Ressource");
        Button btnAdmin = new Button("Account Admin");

        Button btnConnexion = new Button("Connexion");
        Button btnExport = new Button("Exporter");
        btnConnexion.setMinWidth(120);
        btnConnexion.setMinHeight(40);
        btnExport.setMinWidth(120);
        btnExport.setMinHeight(40);




        btnPanel1.getChildren().addAll(btnTutorial,btnAdmin);
        btnPanel1.relocate(120,-5);

        btnPanel2.getChildren().addAll(btnConnexion,btnExport);
        btnPanel2.relocate((width/2-width/6),10);

        rightTopPane.getChildren().addAll(btnPanel1,btnPanel2);

        btnConnexion.setOnAction(e-> {
            ConnexionWindow connexionWindow = new ConnexionWindow(stage,width,height);
            System.out.println(account);
        });

        topPane.getChildren().addAll(leftTopPane,rightTopPane);

        /**
         * Center panel
         */

        BorderPane centerPane = new BorderPane();
        TableView<Stagiaire> table = new TableView<Stagiaire>();

        AnchorPaneViewStagiaire borderPane = new AnchorPaneViewStagiaire(table);
        centerPane.setCenter(borderPane);

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
