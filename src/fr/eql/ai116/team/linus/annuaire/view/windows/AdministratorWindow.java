package fr.eql.ai116.team.linus.annuaire.view.windows;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;

import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewAdministrators;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewStagiaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
        AnchorPane.setLeftAnchor(root,50.);
        AnchorPane.setRightAnchor(root,50.);
        AnchorPane.setTopAnchor(root,30.);
        AnchorPane.setBottomAnchor(root,30.);

        Label labelModifyAccount = new Label("Modifier mon compte");
        labelModifyAccount.setFont(new Font(24));

        HBox idRow = new HBox();
        Label labelUsernameChange = new Label("Identifiant:");
        TextField txtUsernameChange = new TextField(Application.getInstance().getAccount().getUsername());
        Button btnModifyUsername = new Button("Modifier l'identifiant");
        idRow.getChildren().addAll(txtUsernameChange,btnModifyUsername);

        HBox mdpRow = new HBox();
        Label labelPasswordChange = new Label("Mot de passe:");
        PasswordField txtPasswordChange = new PasswordField();
        Label labelConfirmationPasswordChange = new Label("Confirmation nouveau mot de passe:");
        PasswordField txtConfirmationPasswordChange = new PasswordField();
        Button btnModifyPassword = new Button("Modifier mot de passe");
        mdpRow.getChildren().addAll(txtPasswordChange,labelConfirmationPasswordChange,txtConfirmationPasswordChange,btnModifyPassword);

        root.getChildren().addAll(labelModifyAccount,labelUsernameChange,idRow,labelPasswordChange,mdpRow);

        btnModifyUsername.setOnAction(e-> {

            if(!txtUsernameChange.getText().isEmpty()){

                AdministratorSorter.modifyUsernameAdministrator(txtUsernameChange.getText(),Application.getInstance().getAccount());
            }

        });

        root.getChildren().addAll(getSuperAdminElements());
        getChildren().add(root);
        openWindow();
    }

    private List<Node> getSuperAdminElements() {
        List<Node> elements = new ArrayList<>();

        Administrator account = Application.getInstance().getAccount();
        if (account != null && !account.getStatut().equals("Super Administrateur")) return elements;

        Label labelSuperAdmin = new Label("Gestion des administrateurs (super-admin)");
        labelSuperAdmin.setFont(new Font(24));

        TableView<Administrator> table = new TableView<>();

        AnchorPaneViewAdministrators anchorPaneViewAdministrators = new AnchorPaneViewAdministrators(table);

        GridPane gridPaneAddAdmin = new GridPane();

        Label labelCreateAccount = new Label("Créer un nouveau administrateur");
        labelCreateAccount.setFont(new Font(20));

        Label labelAdministrator = new Label("New Username:");
        TextField txtAdministrator= new TextField();
        Label labelPassword = new Label("New Password:");
        TextField txtPassword = new TextField();
        Button btnCreate = new Button("Créer administrateur");

        gridPaneAddAdmin.addRow(1,labelAdministrator,txtAdministrator,labelPassword,txtPassword,btnCreate);
        gridPaneAddAdmin.setHgap(10);
        gridPaneAddAdmin.setVgap(10);

        Button btnDeleteAdministrator = new Button();
        btnDeleteAdministrator.setVisible(false);

        btnCreate.setOnAction(e-> {AdministratorSorter.createAdmin(
                txtAdministrator.getText(),txtPassword.getText(), "Super Administrateur");
                txtAdministrator.setText("");
                txtPassword.setText("");
        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Administrator>() {
            @Override
            public void changed(ObservableValue<? extends Administrator> observable, Administrator oldValue, Administrator newValue) {
                if (newValue == null) return;
                btnDeleteAdministrator.setVisible(true);
                btnDeleteAdministrator.setText("Supprimer l'administrateur sélectionné ?");
            }
        });

        elements.add(labelSuperAdmin);
        elements.add(labelCreateAccount);
        elements.add(gridPaneAddAdmin);
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
}
