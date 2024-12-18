package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.ExportToPdf;
import fr.eql.ai116.team.linus.annuaire.view.windows.AdministratorWindow;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.windows.ConnexionWindow;
import fr.eql.ai116.team.linus.annuaire.view.elements.SearchPanel;
import fr.eql.ai116.team.linus.annuaire.view.elements.HBoxAdmin;
import javafx.geometry.Pos;
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
         * Center panel
         */

        BorderPane centerPane = new BorderPane();
        TableView<Stagiaire> table = new TableView<Stagiaire>();

        AnchorPaneViewStagiaire anchorPane = new AnchorPaneViewStagiaire(table);
        centerPane.setCenter(anchorPane);


        /**
         * Top panel
         */

        HBox topPane = new HBox();
        topPane.setPrefSize(width, height /6);

        SearchPanel leftTopPane = new SearchPanel(anchorPane);
        leftTopPane.setAlignment(Pos.CENTER_LEFT);
        leftTopPane.setPrefSize(width /2, height /4);


        Pane rightTopPane = new Pane();
        rightTopPane.setPrefSize(width /2, height /4);

        topPane.getChildren().addAll(leftTopPane,rightTopPane);
        HBox btnPanel1 = new HBox(20);
        VBox btnPanel2 = new VBox(15);

        Button btnTutorial = new Button("Ressource");
        Button btnPannelAdmin = new Button("Account Admin");

        Button btnConnexion = new Button("Connexion");
        Button btnExport = new Button("Exporter");
        btnConnexion.setMinWidth(120);
        btnConnexion.setMinHeight(40);
        btnExport.setMinWidth(120);
        btnExport.setMinHeight(40);

        btnPanel1.getChildren().addAll(btnTutorial,btnPannelAdmin);
        btnPanel1.relocate(120,-5);

        btnPanel2.getChildren().addAll(btnConnexion,btnExport);
        btnPanel2.relocate((width/2-width/6),25);

        rightTopPane.getChildren().addAll(btnPanel1,btnPanel2);

        btnConnexion.setOnAction(e-> {
            ConnexionWindow connexionWindow = new ConnexionWindow(stage,width,height);
        });

        btnExport.setOnAction(e-> {
            ExportToPdf.exportAnchorPaneViewStagiaireToPdf(table);

        });

        btnPannelAdmin.setOnAction(e-> {
            AdministratorWindow administratorWindow = new AdministratorWindow(stage,width,height);
        });


        /**
         * Bottom Panel
         */

        HBoxAdmin bottomPane = new HBoxAdmin(table);
        bottomPane.setPrefSize(width, height /20);

        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);


        stage.setTitle("Application stagiaire EQL");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }


}
