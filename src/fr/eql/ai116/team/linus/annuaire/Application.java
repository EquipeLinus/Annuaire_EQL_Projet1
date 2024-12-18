package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
//import fr.eql.ai116.team.linus.annuaire.model.program.ExportToPdf;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.elements.SearchPanel;
import fr.eql.ai116.team.linus.annuaire.view.elements.VBoxAdmin;
import fr.eql.ai116.team.linus.annuaire.view.windows.AdministratorWindow;
import fr.eql.ai116.team.linus.annuaire.view.windows.ConnexionWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class Application extends javafx.application.Application {

    private static Application instance;
    private static final Logger log = LogManager.getLogger();

    private double width = 1500;
    private double height = 800;

    public Administrator account = null;// si null administrateur
    private TableView<Stagiaire> table;

    private SearchPanel searchPanel;
    private HBox adminPanel;

    public Application() {
        super();
        instance = this;
    }

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
        table = new TableView<>();

        AnchorPaneViewStagiaire anchorPane = new AnchorPaneViewStagiaire(table);
        centerPane.setCenter(anchorPane);


        /**
         * Top panel
         */

        HBox topPane = new HBox();
        topPane.setPrefSize(width, height /6);

        searchPanel = new SearchPanel(anchorPane);
        searchPanel.setAlignment(Pos.CENTER_LEFT);
        searchPanel.setPrefSize(width /2, height /4);


        Pane rightTopPane = new Pane();
        rightTopPane.setPrefSize(width /2, height /4);


        topPane.getChildren().addAll(searchPanel,rightTopPane);
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
        btnTutorial.setOnAction(e-> {
            if (Desktop.isDesktopSupported()) {
                try {
                    if ( account == null) {
                        File myFile = new File("test.pdf");
                        Desktop.getDesktop().open(myFile);
                    }else {
                        File myFile = new File("resources/test2.pdf");
                        Desktop.getDesktop().open(myFile);
                    }

                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        });

//        btnExport.setOnAction(e-> {
//            ExportToPdf.exportAnchorPaneViewStagiaireToPdf(table);
//
//        });

        btnPannelAdmin.setOnAction(e-> {
            AdministratorWindow administratorWindow = new AdministratorWindow(stage,width,height);
        });


        /**
         * Bottom Panel
         */

        adminPanel = new HBox();
        adminPanel.setAlignment(Pos.CENTER);

        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(adminPanel);

        stage.setTitle("Application stagiaire EQL");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static Application getInstance() {
        return instance;
    }

    public Administrator getAccount() {
        return account;
    }

    public void setAccount(Administrator account) {
        if (account != null) {
            if (adminPanel.getChildren().isEmpty()) {
                adminPanel.getChildren().add(new VBoxAdmin(table, searchPanel));
            }
        } else if (adminPanel.getChildren().size() > 1){
            adminPanel.getChildren().remove(0);
        }

        this.account = account;
    }








}
