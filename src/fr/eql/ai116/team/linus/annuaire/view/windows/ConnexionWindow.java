package fr.eql.ai116.team.linus.annuaire.view.windows;


import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import fr.eql.ai116.team.linus.annuaire.view.Clean;
import fr.eql.ai116.team.linus.annuaire.view.elements.InitializeTxtPanel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionWindow extends VBox {

    private Boolean labelIs = false;

    public ConnexionWindow(Stage stage, double width, double height) {

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);

        Label labelAdministrator = new Label("Nom d'utilisateur :");
        TextField txtAdministrator = new TextField();

        Label labelPassword = new Label("Mot de passe:");
        PasswordField txtPassword = new PasswordField();

        Button btnConnexion = new Button("Se connecter");

        VBox connexionBox = new VBox(5);
        connexionBox.setAlignment(Pos.CENTER_LEFT);
        connexionBox.setMaxSize(200,300);
        root.getChildren().add(connexionBox);

        Region emptyRegion1 = new Region();
        emptyRegion1.setPrefSize(1,10);

        Region emptyRegion2 = new Region();
        emptyRegion2.setPrefSize(1,10);

        connexionBox.getChildren().addAll(labelAdministrator, txtAdministrator,emptyRegion1, labelPassword, txtPassword,emptyRegion2, btnConnexion);

        /**
         * Scene setup & window opening
         */
        Scene connexionScene = new Scene(root, 300, 300);
        Stage connexionWindow = new Stage();
        connexionWindow.setTitle("Connexion");
        connexionWindow.setScene(connexionScene);

        connexionWindow.setX(stage.getX() + width/2 -200);
        connexionWindow.setY(stage.getY() + height/2 -200);
        Application.getInstance().setCurrentPopup(connexionWindow);

        /**
         * Button setup
         */
        txtAdministrator.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    txtPassword.requestFocus();
                }
            }
        });

        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    btnConnexion.fire();
                }
            }
        });

        btnConnexion.setOnAction(e -> {
            System.out.println(AdministratorSorter.getListAdmins());
            Administrator account = AdministratorSorter.checkLogs(txtAdministrator.getText(), txtPassword.getText());
            Application.getInstance().setAccount(account);

            Label labelConnexionAnswer = new Label("");

            if (account != null) {
                System.out.println();
                root.getChildren().clear();
                labelConnexionAnswer.setText("Vous êtes connecté en tant que " + account.getUsername() + " avec les droits "
                        + account.getStatut());

                if (StagiairesSorter.verifyIfStagiaireTxtIsEmpty(account)) {
                    InitializeTxtPanel.openWindow();
                }

                delay(500, connexionWindow::close);

                root.getChildren().add(labelConnexionAnswer);

            } else if (!labelIs) {
                labelConnexionAnswer.setText("L'identifiant ou le mot de passe est incorrect ");
                root.getChildren().add(labelConnexionAnswer);
                labelIs = true;
            }

        });
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

}
