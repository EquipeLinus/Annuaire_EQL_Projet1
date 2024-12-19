package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.view.Clean;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchPanel extends GridPane {

    TextField textFieldPromo = new TextField();
    TextField textFieldLastName = new TextField();
    TextField textFieldFirstName = new TextField();
    PromoStack promotionStack = new PromoStack(textFieldPromo);

    Button validerButton = new Button("Rechercher");

    private AnchorPaneViewStagiaire viewStagiaire;

    public SearchPanel(AnchorPaneViewStagiaire viewStagiaire) {
        super();

        this.viewStagiaire = viewStagiaire;

        textFieldPromo.setPromptText("Entrez une promotion");
        textFieldLastName.setPromptText("Entrez un nom");
        textFieldFirstName.setPromptText("Entrez un prénom");

        setVgap(15);
        setHgap(12);

        getColumnConstraints().add(new ColumnConstraints(200,200,200));
        getColumnConstraints().add(new ColumnConstraints(200,200,200));

        addRow(0, textFieldPromo, promotionStack);
        addRow(1, textFieldLastName, textFieldFirstName, validerButton);


        // Action à effectuer lors du clic sur le bouton "Valider" pour promo
        validerButton.setOnAction(e -> {
            search();
        });

        textFieldPromo.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    promotionStack.addPromo(Clean.cleanPromo(textFieldPromo.getText()));
                }
            }
        });
    }

    public void search() {
        try {
            BinManager binManager = new BinManager();
            List<Stagiaire> currentStagiaires = getStagiaireByPromos(binManager);

            if (!textFieldFirstName.getText().isEmpty()) {
                currentStagiaires = currentStagiaires.stream().filter(
                        s -> Objects.equals(s.getFirstName(), Clean.cleanFirstName(textFieldFirstName.getText()))
                ).collect(Collectors.toList());
            }

            if (!textFieldLastName.getText().isEmpty()) {
                currentStagiaires = currentStagiaires.stream().filter(
                        s -> Objects.equals(s.getLastName(), Clean.cleanLastName(textFieldLastName.getText()))
                ).collect(Collectors.toList());
            }

            viewStagiaire.setTable(currentStagiaires);

        } catch (FileNotFoundException ex1) {
            throw new RuntimeException(ex1);
        } catch (IOException ex2) {
            throw new RuntimeException(ex2);
        }
    }

    private List<Stagiaire> getStagiaireByPromos(BinManager binManager) throws IOException {
        List <Stagiaire> result = new ArrayList<>();
        String[] validatedPromos = promotionStack.getValidatedPromos();

        if (validatedPromos.length == 0) {
            result = binManager.getAll(0,new ArrayList<>());}
        else {
            for (String validatedPromo : validatedPromos) {
                result = binManager.searchPromo(validatedPromo, 0, result);
            }
        }
        return result;
    }

    private void addPromoStack() {

    }
}

/*
private void searchStagiaire() {
        System.out.println("searchStagiaireWithName");
        try {
            BinManager binManager = new BinManager();
            List<Stagiaire> stagiaires = new ArrayList<>();

            String[] promosToSearch = getPromoToSearch(binManager);
            for (String currentPromo : promosToSearch) {
                String firstName = textFieldFirstName.getText();
                String lastName = textFieldLastName.getText();
                String searchParameter = currentPromo + "_" + lastName + "_" + firstName;
                Stagiaire currentStagiaire = binManager.searchStagiaire(searchParameter, 0);

                if (currentStagiaire != null) stagiaires.add(currentStagiaire);
            }

            viewStagiaire.setTable(stagiaires);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 */





