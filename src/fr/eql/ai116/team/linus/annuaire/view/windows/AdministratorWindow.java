package fr.eql.ai116.team.linus.annuaire.view.windows;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;

import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewAdministrators;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewStagiaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AdministratorWindow extends VBox {

    private static final Logger logger = LogManager.getLogger();
    public AdministratorWindow(Stage stage, double width, double height) {

        VBox root = new VBox(10);
        TableView<Administrator> table = new TableView<Administrator>();

        GridPane gridPaneModifyAccount= new GridPane();
        Label labelModifyAccount = new Label("Modifier compte");
        labelModifyAccount.setFont(new Font(24));

        Label labelUsernameChange = new Label("New Username:");
        TextField txtUsernameChange = new TextField();
        Label labelPasswordChange = new Label("New Password:");
        TextField txtPasswordChange = new TextField();
        Label labelConfirmationPasswordChange = new Label("Confirm new password:");
        TextField txtConfirmationPasswordChange = new TextField();
        Button btnModify = new Button("Modifier information");

        gridPaneModifyAccount.addRow(1,labelUsernameChange,txtUsernameChange);
        gridPaneModifyAccount.addRow(2,labelPasswordChange,txtPasswordChange,labelConfirmationPasswordChange,txtConfirmationPasswordChange,btnModify);
        gridPaneModifyAccount.setVgap(10);
        gridPaneModifyAccount.setHgap(10);

        Label labelListAdministrators = new Label("Liste des administrateurs");
        AnchorPaneViewAdministrators anchorPaneViewAdministrators = new AnchorPaneViewAdministrators(table);


        GridPane gridPaneAddAdmin = new GridPane();

        Label labelCreateAccount = new Label("Créer un nouveau administrateur");
        labelCreateAccount.setFont(new Font(24));

        Label labelAdministrator = new Label("New Username:");
        TextField txtAdministrator= new TextField();
        Label labelPassword = new Label("New Password:");
        TextField txtPassword = new TextField();
        Button btnCreate = new Button("Créer administrateur");

        gridPaneAddAdmin.addRow(1,labelAdministrator,txtAdministrator,labelPassword,txtPassword,btnCreate);
        gridPaneAddAdmin.setHgap(10);
        gridPaneModifyAccount.setVgap(10);


        GridPane gridPaneDeleteAdmin = new GridPane();

        Label labelDeleteAccount = new Label("Administrateur à supprimer");
        labelDeleteAccount.setFont(new Font(24));

        Label labelAdministratorToDelete = new Label("Username de l'administrateur à supprimer");
        TextField txtAdministratorToDelete = new TextField();

        Button btnDeleteAdministrator = new Button("Supprimer administrateur");

        gridPaneDeleteAdmin.addRow(1, labelAdministratorToDelete, txtAdministratorToDelete, btnDeleteAdministrator);
        gridPaneDeleteAdmin.setHgap(10);

        ;

        btnCreate.setOnAction(e-> {AdministratorSorter.createAdmin(
                txtAdministrator.getText(),txtPassword.getText(),
                "Super User","Super Password");
            txtAdministrator.setText("");
            txtPassword.setText("");
        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Administrator>() {
            @Override
            public void changed(ObservableValue<? extends Administrator> observable, Administrator oldValue, Administrator newValue) {
                if (newValue == null) return;
                root.getChildren().addAll(labelDeleteAccount, gridPaneDeleteAdmin);
                txtAdministratorToDelete.setText(newValue.getUsername());
            }
        });


        root.getChildren().addAll(labelModifyAccount,gridPaneModifyAccount,labelListAdministrators,anchorPaneViewAdministrators
                    ,labelCreateAccount,gridPaneAddAdmin);

        Scene administrationWindows = new Scene(root, 1000, 800);

        Stage administrationWindow = new Stage();
        administrationWindow.setTitle("Administrator");
        administrationWindow.setScene(administrationWindows);

        administrationWindow.setX(stage.getX() + width/2 -500);
        administrationWindow.setY(stage.getY() + height/2 -400);

        administrationWindow.show();



    }

        /* @Override
        public void changed(ObservableValue<? extends Administrator> observable, Stagiaire oldValue, Stagiaire newValue) {
            if (newValue == null) return;
            txtFirstName.setText(newValue.getFirstName());
            txtLastName.setText(newValue.getLastName());
            txtPromotion.setText(newValue.getPromotion());
            txtYear.setText(Integer.toString(newValue.getYear()));
            txtDepartment.setText(Integer.toString(newValue.getDepartment()));

            selectedStagiaire = newValue;
            updateBtnBox();
        }*/

}
