package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.ExportToPdf;
import fr.eql.ai116.team.linus.annuaire.view.windows.AdministratorWindow;
import fr.eql.ai116.team.linus.annuaire.view.windows.ConnexionWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class TootipBorderPane extends BorderPane {

    Label lblConnectionInfo = new Label("Compte utilisateur");
    Button btnAccountManagement = new Button("Gestion de compte");
    Button btnConnexion = new Button("Connexion");
    Label lblInfo = new Label("");
    boolean isLoggedIn = Application.getInstance().getAccount() != null;


    public TootipBorderPane(Stage stage, double width, double height) {

        /**
         * Right panel
         */
        VBox rightPanel = new VBox(10.);


        Button btnExport = new Button("Exporter");

        btnConnexion.setMinWidth(120);
        btnConnexion.setMinHeight(40);
        btnExport.setMinWidth(120);
        btnExport.setMinHeight(40);

        rightPanel.setPadding(new Insets(0, 100, 20, 0));
        rightPanel.getChildren().addAll(btnConnexion, btnExport,lblInfo);
        setRight(rightPanel);

        /**
         * Top panel
         */
        HBox topPanel = new HBox();

        Button btnHelp = new Button("Aide");
        btnAccountManagement.setVisible(false);

        topPanel.setPadding(new Insets(-5, 0, 0, 0));

        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.getChildren().addAll(btnHelp, btnAccountManagement, lblConnectionInfo);
        setTop(topPanel);

        lblConnectionInfo.setMaxWidth(350);
        lblConnectionInfo.setMinWidth(350);

        btnConnexion.setOnAction(e -> {
             if(!isLoggedIn) {
                ConnexionWindow connexionWindow = new ConnexionWindow(stage, width, height);
            } else {

                 Application.getInstance().setAccount(null);
            }
        });


        btnExport.setOnAction(e -> {
            lblInfo.setTextFill(Color.ORANGE);
            lblInfo.setText("Veuillez patienter");
            InitializeTxtPanel.delay(500, () -> {
                if(ExportToPdf.exportAnchorPaneViewStagiaireToPdf(Application.getInstance().getTable())){
                    lblInfo.setTextFill(Color.GREEN);
                    lblInfo.setText("Le pdf a été créé");
                }
                else {
                    lblInfo.setTextFill(Color.RED);
                    lblInfo.setText("Le pdf n'as pas pu être créé");
                }

            });
        });

        btnAccountManagement.setOnAction(e -> {
            AdministratorWindow administratorWindow = new AdministratorWindow(stage);
        });

        btnHelp.setOnAction(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    if (Application.getInstance().getAccount() == null) {
                        File myFile = new File("resources/guide_utilisateur.pdf");
                        Desktop.getDesktop().open(myFile);
                    } else {
                        File myFile = new File("resources/guide_administrateur.pdf");
                        Desktop.getDesktop().open(myFile);
                    }

                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        });
    }

    public void updateConnectionInfo() {
        isLoggedIn = Application.getInstance().getAccount() != null;

        String message;
        if (!isLoggedIn) {
            btnConnexion.setText("Connexion");
            message = "Compte utilisateur (non connecté)";
            btnAccountManagement.setVisible(false);

        } else {
            Administrator account = Application.getInstance().getAccount();
            btnConnexion.setText("Déconnexion");
            message = "Bienvenu, " + account.getUsername() + " (" + account.getStatut() + ")";
            btnAccountManagement.setVisible(true);
        }
        lblConnectionInfo.setText(message);
    }

}