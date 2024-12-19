package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.view.windows.AdministratorWindow;
import fr.eql.ai116.team.linus.annuaire.view.windows.ConnexionWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class TootipBorderPane extends BorderPane {
    boolean isLoggedIn = false;
    Label lblConnectionInfo = new Label("Compte utilisateur");
    Button btnAccountManagement = new Button("Gestion de compte");

    public TootipBorderPane(Stage stage, double width, double height) {

        /**
         * Right panel
         */
        VBox rightPanel = new VBox(10.);

        Button btnConnexion = new Button("Connexion");
        Button btnDeconnexion = new Button("Déconnexion");
        Button btnExport = new Button("Exporter");

        btnConnexion.setMinWidth(120);
        btnConnexion.setMinHeight(40);
        btnExport.setMinWidth(120);
        btnExport.setMinHeight(40);

        rightPanel.setPadding(new Insets(0,100,20,0));
        rightPanel.getChildren().addAll(btnConnexion,btnExport);
        setRight(rightPanel);

        /**
         * Top panel
         */
        HBox topPanel = new HBox(20.);

        Button btnHelp = new Button("Aide");
        btnAccountManagement.setVisible(false);

        topPanel.setPadding(new Insets(-5,0,0,0));

        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.getChildren().addAll(btnHelp,btnAccountManagement,lblConnectionInfo);
        setTop(topPanel);

        lblConnectionInfo.setMaxWidth(350);
        lblConnectionInfo.setMinWidth(350);


        btnConnexion.setOnAction(e-> {
            ConnexionWindow connexionWindow = new ConnexionWindow(stage, width, height);
            if (!isLoggedIn) {

                btnConnexion.setText("Déconnexion");
                btnAccountManagement.setVisible(false);
            } else {
                // Si l'utilisateur est connecté, fermer la fenêtre de connexion
                if (connexionWindow != null) {

                    isLoggedIn = false; // L'utilisateur est maintenant déconnecté
                    btnConnexion.setText("Connexion");
                }
            }
        });


        //toggleLogInLogOut(btnConnexion);
        //});



        btnExport.setOnAction(e-> {
            //ExportToPdf.exportAnchorPaneViewStagiaireToPdf(table);
        });

        btnAccountManagement.setOnAction(e-> {
            AdministratorWindow administratorWindow = new AdministratorWindow(stage,width,height);
        });

        btnHelp.setOnAction(e-> {
            if (Desktop.isDesktopSupported()) {
                try {
                    if ( Application.getInstance().getAccount() == null) {
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
    }

    public void updateConnectionInfo() {

        String message;
        if (Application.getInstance().getAccount()==null) {
            message = "Compte utilisateur (non connecté)";
            btnAccountManagement.setVisible(false);
        } else {
            Administrator account = Application.getInstance().getAccount();
            message = "Bienvenu, " + account.getUsername() + " (" + account.getStatut() + ")";
            btnAccountManagement.setVisible(true);
        }
        lblConnectionInfo.setText(message);
    }
}
