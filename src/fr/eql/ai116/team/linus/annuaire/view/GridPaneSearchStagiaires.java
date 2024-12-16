package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import javafx.application.Application;
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

    TextField textFieldPromo = new TextField();
    TextField textFieldLastName = new TextField();
    TextField textFieldFirstName = new TextField();

    Button validerButton = new Button("Valider");

    private AnchorPaneViewStagiaire viewStagiaire;

    public GridPaneSearchStagiaires(AnchorPaneViewStagiaire viewStagiaire) {
        super();
        this.viewStagiaire = viewStagiaire;

        textFieldPromo.setPromptText("Entrez une promotion");
        textFieldLastName.setPromptText("Entrez un nom");
        textFieldFirstName.setPromptText("Entrez un nom");

        Label resultLabel = new Label("Résultat :");
        Label firsNameLabel = new Label("Prénom : ");
        Label lastNameLabel = new Label("Nom : ");
        Label promotionLabel = new Label("Promotion : ");
        Label yearLabel = new Label("Année : ");
        Label departmentLabel = new Label("Département : ");

        addRow(0, textFieldPromo);
        addRow(1, textFieldLastName, textFieldFirstName, validerButton);


        // Action à effectuer lors du clic sur le bouton "Valider" pour promo
        validerButton.setOnAction(e -> {
            if (!textFieldPromo.getText().isEmpty() && textFieldLastName.getText().isEmpty() && textFieldFirstName.getText().isEmpty()) {
                searchPromoGridPane ();
            } else if (!textFieldPromo.getText().isEmpty() && !textFieldLastName.getText().isEmpty() && textFieldFirstName.getText().isEmpty()) {
                searchStagiaireGridPane ();
            }
        });

    }

    private void searchStagiaireGridPane() {
        try {
            BinManager binManager = new BinManager();
            Stagiaire currentStagiaire = new Stagiaire();
            currentStagiaire = binManager.searchStagiaire(textFieldLastName.getText(), 0);


            System.out.println(currentStagiaire);

            // viewStagiaire.setStagiaireListDao(currentStagiaires);

        } catch (FileNotFoundException ex1) {
            throw new RuntimeException(ex1);
        } catch (IOException ex2) {
            throw new RuntimeException(ex2);
        }
    }

    private void searchPromoGridPane() {
        try {
            BinManager binManager = new BinManager();
            List<Stagiaire> currentStagiaires = new ArrayList<>();
            currentStagiaires = binManager.searchPromo(textFieldPromo.getText(), 0, new ArrayList<>());

            for (Stagiaire currentStagiaire : currentStagiaires) {
                System.out.println(currentStagiaire);
            }

            viewStagiaire.setTable(currentStagiaires);

        } catch (FileNotFoundException ex1) {
            throw new RuntimeException(ex1);
        } catch (IOException ex2) {
            throw new RuntimeException(ex2);
        }
    }
/*
    @Override
    public void start(Stage stage) {

        // Liste des stagiaires


        // Création des composants de l'interface
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

        // Action à effectuer lors du clic sur le bouton "Valider" pour promo
        validerButton.setOnAction(e -> {
            try {
                BinManager binManager = new BinManager();
                List<Stagiaire> currentStagiaires = new ArrayList<>();
                currentStagiaires = binManager.searchPromo(textFieldPromo.getText(), 0, new ArrayList<>());
                for (Stagiaire stagiaire : currentStagiaires) {
                    if (stagiaire.getPromotion().equalsIgnoreCase(String.valueOf(textFieldPromo))) {
                        // Afficher les informations du stagiaire trouvée
                        firsNameLabel.setText("Prénom : " + stagiaire.getFirstName());
                        lastNameLabel.setText("Nom : " + stagiaire.getLastName());
                        promotionLabel.setText("Promotion : " + stagiaire.getPromotion());
                        yearLabel.setText("Année : " + stagiaire.getYear());
                        departmentLabel.setText("Département: " + stagiaire.getDepartment());
                        resultLabel.setText("Résultat : Stagiaire trouvée !");
                    }
                }
            } catch (FileNotFoundException ex1) {
                throw new RuntimeException(ex1);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }

            // Rechercher la promotion dans la liste
            for (Stagiaire stagiaire : stagiaires) {
                if (stagiaire.getLastName().equalsIgnoreCase(searchPromo)) {
                    // Afficher les informations du stagiaire trouvée
                    firsNameLabel.setText("Prénom : " + stagiaire.getFirstName());
                    lastNameLabel.setText("Nom : " + stagiaire.getLastName());
                    promotionLabel.setText("Promotion : " + stagiaire.getPromotion());
                    yearLabel.setText("Année : " + stagiaire.getYear());
                    departmentLabel.setText("Département: " + stagiaire.getDepartment());
                    resultLabel.setText("Résultat : Stagiaire trouvée !");
                    found = true;
                    break;
                }
            }

            // Si promotion non trouvée
            if (!found) {
                resultLabel.setText("Résultat : Promotion non trouvée !");
                firsNameLabel.setText("Prénom : ");
                lastNameLabel.setText("Nom : ");
                promotionLabel.setText("Promotion : ");
                yearLabel.setText("Année : ");
                departmentLabel.setText("Département : ");
            }

        });


        // Action à effectuer lors du clic sur le bouton "Valider" pour nom
        validerButton.setOnAction(e -> {
            String searchLastName = textFieldLastName.getText().trim();
            boolean found = false;

            // Rechercher le stagiaire dans la liste
            for (Stagiaire stagiaire : stagiaires) {

                if (stagiaire.getLastName().equalsIgnoreCase(searchLastName)) {
                    // Afficher les informations du stagiaire trouvée
                    firsNameLabel.setText("Prénom : " + stagiaire.getFirstName());
                    lastNameLabel.setText("Nom : " + stagiaire.getLastName());
                    promotionLabel.setText("Promotion : " + stagiaire.getPromotion());
                    yearLabel.setText("Année : " + stagiaire.getYear());
                    departmentLabel.setText("Département: " + stagiaire.getDepartment());
                    resultLabel.setText("Résultat : Stagiaire trouvée !");
                    found = true;
                    break;
                }
            }

            // Si stagiaire non trouvée
            if (!found) {
                resultLabel.setText("Résultat : Stagiaire non trouvée !");
                firsNameLabel.setText("Prénom : ");
                lastNameLabel.setText("Nom : ");
                promotionLabel.setText("Promotion : ");
                yearLabel.setText("Année : ");
                departmentLabel.setText("Département : ");
            }

        });


        // Création d'un VBox pour organiser les éléments
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.getChildren().addAll(textFieldPromo, validerButton, resultLabel, firsNameLabel, lastNameLabel, promotionLabel, yearLabel, departmentLabel);

        // Création de la scène
        Scene scene = new Scene(vbox, 400, 300);

        // Configuration de la fenêtre
        stage.setTitle("Recherche de Stagiaire");
        stage.setScene(scene);
        stage.show();
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }

 */
}






