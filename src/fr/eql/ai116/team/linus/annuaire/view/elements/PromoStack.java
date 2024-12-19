package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.view.Clean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class PromoStack extends HBox {
    private HBox buttonContainer = new HBox();
    private final TextField promoTxt;
    private List<String> selectedPromos = new ArrayList<>();
    private List<Button> selectedButtons = new ArrayList<>();


    public PromoStack(TextField promoTxt) {
        super(5.);

        this.promoTxt = promoTxt;
        Button btnValidate = new Button("Valider promo");

        btnValidate.setMinWidth(130);
        btnValidate.setMaxWidth(130);

        getChildren().addAll(btnValidate, buttonContainer);

        btnValidate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promoButtons();}
        });
    }

    private void promoButtons() {
        addPromo(promoTxt.getText());
        displayPromoButtons ();
    }

    private void displayPromoButtons() {
        selectedPromos.sort(String::compareTo); // A verifier si la méthode sauvegarde la liste ordonnée dans la liste originale...
        for (String selectedPromo : selectedPromos) {
            Button promoBtn = new Button (selectedPromo);
            promoBtn.setMinWidth(80);
            promoBtn.setMaxWidth(80);
            selectedButtons.add(promoBtn);

           /* promoBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removePromo(selectedPromo);
                }
            });*/
        }
        buttonContainer.getChildren().addAll(selectedButtons);
    }

    /* public void addPromos(String name) {
        if (name.isEmpty()) return;
        promoTxt.setText(""); // A réflechir où à le mettre...
        String cleanedName = Clean.cleanPromo(name);
        selectedPromos.add(cleanedName);
        selectedPromos.sort(String::compareTo); // A verifier si la méthode sauvegarde la liste ordonnée dans la liste originale...
        for (String selectedPromo : selectedPromos) {
            Button promoBtn = new Button(cleanedName);
            promoBtn.setMinWidth(80);
            promoBtn.setMaxWidth(80);
            selectedButtons.add(promoBtn);

            promoBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removePromo(cleanedName);
                }
            });
            buttonContainer.getChildren().addAll(selectedButtons);
        }
    }*/

        public void addPromo (String name){
            if (name.isEmpty()) return;
            promoTxt.setText("");
            String cleanedName = Clean.cleanPromo(name);
            selectedPromos.add(cleanedName);
    }

            /*
            Button promoBtn = new Button(cleanedName);
            promoBtn.setMinWidth(80);
            promoBtn.setMaxWidth(80);

            promoBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removePromo(cleanedName);
                }
            });

            buttonContainer.getChildren().add(promoBtn);
        }*/

        public void removePromo (String name){

            for (Node child : buttonContainer.getChildren()) {
                if (child instanceof Button && ((Button) child).getText().equals(name)) {
                    buttonContainer.getChildren().remove(child);
                    break;
                }
            }
        }

        public String[] getValidatedPromos () {
            String[] result = new String[buttonContainer.getChildren().size()];
            for (int i = 0; i < buttonContainer.getChildren().size(); i++) {
                result[i] = ((Button) buttonContainer.getChildren().get(i)).getText();
            }
            return result;
        }
}
