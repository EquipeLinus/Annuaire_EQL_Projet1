package fr.eql.ai116.team.linus.annuaire.view;


import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ConnexionWindow extends VBox {

    public static Administrator account = null;
    private Boolean labelIs = false;

    public ConnexionWindow(Stage stage, double width, double height) {

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);

        GridPane connexionBox = new GridPane();
        connexionBox.setAlignment(Pos.CENTER);
        root.getChildren().add(connexionBox);
        Label labelAdministrator = new Label("Username :");
        TextField txtAdministrator = new TextField();
        Label labelPassword = new Label("Password:");
        TextField txtPassword = new TextField();
        Button btnConnexion = new Button("Se connecter");

        connexionBox.addRow(0, labelAdministrator, txtAdministrator);
        connexionBox.addRow(1, labelPassword, txtPassword);
        connexionBox.addRow(2, btnConnexion);
        connexionBox.setVgap(20);

        Scene secondScene = new Scene(root, 500, 300);
        btnConnexion.setOnAction(e -> {

            Application.account = AdministratorSorter.checkLogs(txtAdministrator.getText(), txtPassword.getText());
            account = AdministratorSorter.checkLogs(txtAdministrator.getText(), txtPassword.getText());

            Label labelConnexionAnswer = new Label("");

            if (account != null) {
                root.getChildren().clear();
                labelConnexionAnswer.setText("Vous êtes connecter en tant que " + account.getUsername() + " avec les droits "
                        + account.getStatut());
                root.getChildren().add(labelConnexionAnswer);

            } else if (!labelIs) {
                labelConnexionAnswer.setText("L'identifiant ou le mot de passe est incorrect ");
                root.getChildren().add(labelConnexionAnswer);
                labelIs = true;

            }


        });

        Stage newWindow = new Stage();
        newWindow.setTitle("Connexion");
        newWindow.setScene(secondScene);

        newWindow.setX(stage.getX() + width / 2 - 200);
        newWindow.setY(stage.getY() + height / 2 - 200);

        newWindow.show();

    }

}
