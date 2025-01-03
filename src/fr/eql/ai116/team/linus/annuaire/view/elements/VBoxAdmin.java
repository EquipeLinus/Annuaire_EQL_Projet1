package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.model.program.Delay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class VBoxAdmin extends VBox {

    private VBox vBoxFirstName;
    private Label lblFirstName;
    private TextField txtFirstName;
    private VBox vBoxLastName;
    private Label lblLastName;
    private TextField txtLastName;
    private VBox vBoxPromotion;
    private Label lblPromotion;
    private TextField txtPromotion;
    private VBox vBoxYear;
    private Label lblYear;
    private TextField txtYear;
    private VBox vBoxDepartment;
    private Label lblDepartment;
    private TextField txtDepartment;

    private VBox vBoxBtn;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;

    private Label errorLabel = new Label("");

    private Stagiaire selectedStagiaire;

    public VBoxAdmin(TableView<Stagiaire> table, SearchPanel searchPanel) {
        super();

        vBoxFirstName = new VBox(5);
        lblFirstName = new Label("Prénom:");
        txtFirstName = new TextField();
        vBoxFirstName.getChildren().addAll(lblFirstName, txtFirstName);

        vBoxLastName = new VBox(5);
        lblLastName = new Label("Nom:");
        txtLastName = new TextField();
        vBoxLastName.getChildren().addAll(lblLastName, txtLastName);

        vBoxPromotion = new VBox(5);
        lblPromotion = new Label("Promotion:");
        txtPromotion = new TextField();
        vBoxPromotion.getChildren().addAll(lblPromotion, txtPromotion);

        vBoxYear = new VBox(5);
        lblYear = new Label("Année:");
        txtYear = new TextField();
        vBoxYear.getChildren().addAll(lblYear, txtYear);

        vBoxDepartment = new VBox(5);
        lblDepartment = new Label("Département:");
        txtDepartment = new TextField();
        vBoxDepartment.getChildren().addAll(lblDepartment, txtDepartment);

        vBoxBtn = new VBox(5);
        btnAdd = new Button("Ajouter");
        btnModify = new Button("Modifier");
        btnDelete = new Button("Supprimer");
        vBoxBtn.getChildren().addAll(btnAdd, btnDelete);

        HBox generalBox = new HBox(5);
        generalBox.getChildren().addAll(vBoxFirstName,vBoxLastName,vBoxPromotion,vBoxYear,vBoxDepartment,vBoxBtn);

        getChildren().addAll(generalBox, errorLabel);

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stagiaire>() {
            @Override
            public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue, Stagiaire newValue) {
                if (newValue == null) return;
                txtFirstName.setText(newValue.getFirstName());
                txtLastName.setText(newValue.getLastName());
                txtPromotion.setText(newValue.getPromotion());
                txtYear.setText(Integer.toString(newValue.getYear()));
                txtDepartment.setText(Integer.toString(newValue.getDepartment()));

                selectedStagiaire = newValue;
                updateBtnBox();
            }
        });

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetErrorLabel();
                try {
                    int year = Integer.parseInt(txtYear.getText());
                    int department = Integer.parseInt(txtDepartment.getText());

                    BinManager bManager = new BinManager();
                    if (bManager.addStagiaire(new Stagiaire(
                            txtFirstName.getText(),
                            txtLastName.getText(),
                            txtPromotion.getText(),
                            year,
                            department
                    ))) {
                        setConfirmLabel("Stagiaire ajouté avec succès");
                    } else {
                        setErrorLabel("Stagiaire déjà existant");
                    }
                    searchPanel.search();
                    //bManager.displayTree(BinManager.root,0);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException e) {
                    setErrorLabel("Année ou département incorrect");
                }
            }
        });

        btnModify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetErrorLabel();
                try {
                    int year = Integer.parseInt(txtYear.getText());
                    int department = Integer.parseInt(txtDepartment.getText());

                    BinManager bManager = new BinManager();
                    bManager.modifyStagiaire(selectedStagiaire.getID(), new Stagiaire(
                            txtFirstName.getText(),
                            txtLastName.getText(),
                            txtPromotion.getText(),
                            year,
                            department
                    ));
                    searchPanel.search();

                    setConfirmLabel("Stagiaire modifié avec succès");
                    //bManager.displayTree(BinManager.root,0);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException e) {
                    setErrorLabel("Année ou département incorrect");
                }
            }
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetErrorLabel();
                try {
                    BinManager bManager = new BinManager();
                    if (bManager.removeStagiaire(selectedStagiaire.getID())) {
                        searchPanel.search();
                        setConfirmLabel("Stagiaire supprimé avec succès");
                        //bManager.displayTree(BinManager.root,0);
                    } else {
                        setErrorLabel("Stagiaire non existant");
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ChangeListener<String> onTextFieldChanged = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateBtnBox();
            }
        };

        txtFirstName.textProperty().addListener(onTextFieldChanged);
        txtLastName.textProperty().addListener(onTextFieldChanged);
        txtPromotion.textProperty().addListener(onTextFieldChanged);
        txtYear.textProperty().addListener(onTextFieldChanged);
        txtDepartment.textProperty().addListener(onTextFieldChanged);

        generalBox.setSpacing(10);
        generalBox.setAlignment(Pos.BASELINE_CENTER);

        setAlignment(Pos.TOP_CENTER);
    }

    private void updateBtnBox() {
        if (selectedStagiaire == null) return;
        try {
            int year = Integer.parseInt(txtYear.getText());
            int department = Integer.parseInt(txtDepartment.getText());

            Stagiaire currentStagiaire = new Stagiaire(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtPromotion.getText(),
                    year,
                    department
            );

            if (Objects.equals(selectedStagiaire, currentStagiaire)) {
                vBoxBtn.getChildren().set(1, btnDelete);
            } else {
                vBoxBtn.getChildren().set(1, btnModify);
            }

            resetErrorLabel();

        } catch (NumberFormatException e) {
            vBoxBtn.getChildren().set(1, btnModify);
            errorLabel.setText("Année ou département incorrect");
        }
    }

    private void resetErrorLabel() {
        errorLabel.setText("");
    }

    private void setErrorLabel(String text) {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText(text);
        Delay.delay(5000, () -> errorLabel.setText(""));
    }

    private void setConfirmLabel(String text) {
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText(text);
        Delay.delay(5000, () -> errorLabel.setText(""));
    }
}
