package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GridPaneSearchStagiaires extends GridPane {


    public GridPaneSearchStagiaires(AnchorPaneViewStagiaire viewStagiaire) {
        super();

        TextField textFieldPromo = new TextField();
        textFieldPromo.setPromptText("Entrez une promotion");
        TextField textFieldLastName = new TextField();
        textFieldLastName.setPromptText("Entrez un nom");
        TextField textFieldFirstName = new TextField();
        textFieldFirstName.setPromptText("Entrez un nom");

        Button validerButton = new Button("Valider");

        Label resultLabel = new Label("Résultat :");
        Label firsNameLabel = new Label("Prénom : ");
        Label lastNameLabel = new Label("Nom : ");
        Label promotionLabel = new Label("Promotion : ");
        Label yearLabel = new Label("Année : ");
        Label departmentLabel = new Label("Département : ");

        addRow(0, textFieldPromo);
        addRow(0,validerButton);

        // Action à effectuer lors du clic sur le bouton "Valider" pour promo
        validerButton.setOnAction(e -> {
            try {
                BinManager binManager = new BinManager();
                List<Stagiaire> currentStagiaires = new ArrayList<>();
                currentStagiaires = binManager.searchPromo(textFieldPromo.getText(), 0, new ArrayList<>());

                viewStagiaire.setTable(currentStagiaires);

            } catch (FileNotFoundException ex1) {
                throw new RuntimeException(ex1);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }
        });
    }
}






