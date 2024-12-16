package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GridPaneSearchStagiaires extends Application {

    @Override
    public void start(Stage stage)  {

        // Liste des stagiaires
        List<Stagiaire> stagiaires = new ArrayList<>();
        //public BordPaneTable(Stagiaires stagiaires, TableView<Stagiaire> table){
        stagiaires.add(new Stagiaire("Jean", "Dupont", "Ai116", 1550, 75012));
        stagiaires.add(new Stagiaire("Rachel", "Aremab", "Ai114", 1950, 75012));



        // Création des composants de l'interface
        TextField textField = new TextField();
        textField.setPromptText("Entrez une promotion");
        textField.setPromptText("Entrez un nom");

        Button validerButton = new Button("Valider");

        Label resultLabel = new Label("Résultat :");
        Label firsNameLabel = new Label("Prénom : ");
        Label lastNameLabel = new Label("Nom : ");
        Label promotionLabel = new Label("Promotion : ");
        Label yearLabel = new Label("Année : ");
        Label departmentLabel = new Label("Département : ");

        // Action à effectuer lors du clic sur le bouton "Valider" pour promo
        validerButton.setOnAction(e -> {
            String searchPromo = textField.getText().trim();
            boolean found = false;

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
            String searchLastName = textField.getText().trim();
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
        vbox.getChildren().addAll(textField, validerButton, resultLabel, firsNameLabel, lastNameLabel, promotionLabel,yearLabel,departmentLabel);

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
}
