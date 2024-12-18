package fr.eql.ai116.team.linus.annuaire.view.windows;


import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import fr.eql.ai116.team.linus.annuaire.view.elements.InitializeTxtPanel;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionWindow extends VBox{

    private Boolean labelIs = false;
    public ConnexionWindow(Stage stage, double width, double height) {

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);

        GridPane connexionBox = new GridPane();
        connexionBox.setAlignment(Pos.CENTER);
        root.getChildren().add(connexionBox);
        Label labelAdministrator = new Label("Username :");
        TextField txtAdministrator= new TextField();
        Label labelPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        Button btnConnexion = new Button("Se connecter");

        connexionBox.addRow(0,labelAdministrator,txtAdministrator);
        connexionBox.addRow(1,labelPassword,txtPassword);
        connexionBox.addRow(2,btnConnexion);
        connexionBox.setVgap(20);

        Scene connexionWindows = new Scene(root, 500, 300);

        Stage connexionWindow = new Stage();
        connexionWindow.setTitle("Connexion");
        connexionWindow.setScene(connexionWindows);

        connexionWindow.setX(stage.getX() + width/2 -200);
        connexionWindow.setY(stage.getY() + height/2 -200);

        connexionWindow.show();
        Stage stageConnexion = (Stage) btnConnexion.getScene().getWindow();

        btnConnexion.setOnAction(e-> {

            Administrator account = AdministratorSorter.checkLogs(txtAdministrator.getText(),txtPassword.getText());
            Application.getInstance().setAccount(account);

            Label labelConnexionAnswer = new Label("");

            if(account != null){
                System.out.println();
                root.getChildren().clear();
                labelConnexionAnswer.setText("Vous êtes connecté en tant que " + account.getUsername() + " avec les droits "
                        + account.getStatut());

                if(StagiairesSorter.verifyIfStagiaireTxtIsEmpty(account)){
                    InitializeTxtPanel init = new InitializeTxtPanel();
                    Scene initialiseStagiaireWindows = new Scene(init, 230, 100);

                    // New window (Stage)
                    Stage newWindow = new Stage();

                    newWindow.setTitle("Initialisation stagiaire.txt");
                    newWindow.setScene(initialiseStagiaireWindows);
                    newWindow.initModality(Modality.WINDOW_MODAL);
                    newWindow.show();

                }

                delay(500, () -> {
                    stageConnexion.close();
                });

                root.getChildren().add(labelConnexionAnswer);

            }else if(!labelIs) {
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
