package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AdministratorWindow extends javafx.application.Application {

    private static final Logger logger = LogManager.getLogger();

    private double width = 1500;
    private double height = 900;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        GridPane root = new GridPane();

        Scene scene = new Scene(root, width, height);

        Label labelAdministrator = new Label("New Username:");
        TextField txtAdministrator= new TextField();
        Label labelPassword = new Label("New Password:");
        TextField txtPassword = new TextField();
        Button btnCreate = new Button("Créer administrateur");

        root.addRow(0,labelAdministrator,txtAdministrator);
        root.addRow(1,labelPassword,txtPassword);
        root.addRow(2,btnCreate);

        btnCreate.setOnAction(e-> {AdministratorSorter.createAdmin(
                txtAdministrator.getText(),txtPassword.getText(),
                "Super User","Super Password");
            txtAdministrator.setText("");
            txtPassword.setText("");
        });


        stage.setTitle("Windows Administrator");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }
}
