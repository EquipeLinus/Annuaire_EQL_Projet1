package fr.eql.ai116.team.linus.annuaire.view.windows;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewAdministrators;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class AdministratorWindow extends AnchorPane {

    private static final Logger logger = LogManager.getLogger();

    public AdministratorWindow(Stage stage) {

        VBox root = new VBox(10);
        AnchorPane.setLeftAnchor(root, 50.);
        AnchorPane.setRightAnchor(root, 50.);
        AnchorPane.setTopAnchor(root, 30.);
        AnchorPane.setBottomAnchor(root, 30.);

        Label labelModifyAccount = new Label("Modifier mon compte");
        labelModifyAccount.setFont(new Font(24));

        root.getChildren().addAll(labelModifyAccount, getModifyIdSection(), getModifyMDPSection());

        root.getChildren().addAll(getSuperAdminElements());
        getChildren().add(root);
        openWindow();
    }

    private List<Node> getSuperAdminElements() {
        List<Node> elements = new ArrayList<>();

        Administrator account = Application.getInstance().getAccount();
        System.out.println(account.getStatut());
        if (account != null && !account.getStatut().equals("Super Administrateur")) return elements;

        Label labelSuperAdmin = new Label("Gestion des administrateurs (super-admin)");
        labelSuperAdmin.setFont(new Font(24));

        TableView<Administrator> table = new TableView<>();
        AnchorPaneViewAdministrators anchorPaneViewAdministrators = new AnchorPaneViewAdministrators(table);

        Button btnDeleteAdministrator = new Button();
        btnDeleteAdministrator.setVisible(false);

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Administrator>() {
            @Override
            public void changed(ObservableValue<? extends Administrator> observable, Administrator oldValue, Administrator newValue) {
                if (newValue == null) return;
                btnDeleteAdministrator.setVisible(true);
                btnDeleteAdministrator.setText("Supprimer l'administrateur sélectionné ?");
            }
        });

        elements.add(labelSuperAdmin);
        elements.add(getAddAdminSection());
        elements.add(anchorPaneViewAdministrators);
        elements.add(btnDeleteAdministrator);

        return elements;
    }

    private void openWindow() {

        Scene administrationWindows = new Scene(this);
        Stage administrationWindow = new Stage();
        administrationWindow.setTitle("Gestion de compte");
        administrationWindow.setScene(administrationWindows);

        Application.getInstance().setCurrentPopup(administrationWindow);
    }

    private Accordion getModifyIdSection() {
        Accordion idAccordion = new Accordion();
        idAccordion.setPrefWidth(20);
        Label lblNewID = new Label("Nouvel identifiant:");
        TextField txtUsernameChange = new TextField(Application.getInstance().getAccount().getUsername());

        HBox btnBox = new HBox(5);
        Button btnModifyUsername = new Button("Modifier l'identifiant");
        Label confirmationLbl = new Label("");
        confirmationLbl.setFont(new Font(12));
        btnBox.setAlignment(Pos.CENTER_LEFT);
        btnBox.getChildren().addAll(btnModifyUsername,confirmationLbl);

        TitledPane idAccordionTP = new TitledPane("Identifiant",
                new VBox(lblNewID, txtUsernameChange, btnBox));
        idAccordion.getPanes().add(idAccordionTP);

        btnModifyUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /// AJOUTER FONCTION QUI PERMET DE VRAIMENT MODIFIER L' ID:

                confirmationLbl.setTextFill(Color.GREEN);
                confirmationLbl.setText("Identifiant modifié avec succès");
            }
        });

        idAccordion.getPanes().get(0).expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) confirmationLbl.setText("");
            }
        });

        return idAccordion;
    }

    private Accordion getModifyMDPSection() {
        Accordion mdpAccordion = new Accordion();
        mdpAccordion.setPrefWidth(20);
        Label labelPasswordChange = new Label("Nouveau mot de passe:");
        PasswordField txtPasswordChange = new PasswordField();
        Label labelConfirmationPasswordChange = new Label("Confirmation nouveau mot de passe:");
        PasswordField txtConfirmationPasswordChange = new PasswordField();

        HBox btnBox = new HBox(5);
        Button btnModifyPassword = new Button("Modifier mot de passe");
        Label confirmationLbl = new Label("");
        confirmationLbl.setFont(new Font(12));
        btnBox.setAlignment(Pos.CENTER_LEFT);
        btnBox.getChildren().addAll(btnModifyPassword,confirmationLbl);

        TitledPane mdpAccordionTP = new TitledPane("Mot de passe",
                new VBox(labelPasswordChange, txtPasswordChange, labelConfirmationPasswordChange, txtConfirmationPasswordChange, btnBox));
        mdpAccordion.getPanes().add(mdpAccordionTP);

        btnModifyPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AdministratorSorter.modifyAdministrator(Application.getInstance().getAccount().getUsername(),
                        Application.getInstance().getAccount().getUsername(),
                        txtPasswordChange.getText(),
                        "Administrateur"
                        );

                confirmationLbl.setTextFill(Color.GREEN);
                confirmationLbl.setText("Mot de passe modifié avec succès");
            }
        });

        mdpAccordion.getPanes().get(0).expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) confirmationLbl.setText("");
            }
        });

        return mdpAccordion;
    }

    private Accordion getAddAdminSection() {
        Accordion adminCreationAccordion = new Accordion();
        adminCreationAccordion.setPrefWidth(20);
        Label labelAdministrator = new Label("Identifiant:");
        TextField txtAdministrator = new TextField();
        Label labelPassword = new Label("Mot de passe:");
        TextField txtPassword = new TextField();

        HBox btnBox = new HBox(5);
        Button btnCreate = new Button("Créer administrateur");
        Label confirmationLbl = new Label("");
        confirmationLbl.setFont(new Font(12));
        btnBox.setAlignment(Pos.CENTER_LEFT);
        btnBox.getChildren().addAll(btnCreate,confirmationLbl);

        TitledPane adminCreationAccordionTP = new TitledPane("Créer un nouveau administrateur",
                new VBox(labelAdministrator, txtAdministrator, labelPassword, txtPassword, btnBox));
        adminCreationAccordion.getPanes().add(adminCreationAccordionTP);

        btnCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AdministratorSorter.addAdministratorToFile(txtAdministrator.getText(),txtPassword.getText(),"Administrateur");

                confirmationLbl.setTextFill(Color.GREEN);
                confirmationLbl.setText("Administrateur créer avec succès");
            }
        });

        adminCreationAccordion.getPanes().get(0).expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) confirmationLbl.setText("");
            }
        });

        return adminCreationAccordion;
    }
}
