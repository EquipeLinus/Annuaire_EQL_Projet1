package fr.eql.ai116.team.linus.annuaire.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.TreeSet;

public class PromoStack extends HBox {

    private HBox buttonContainer = new HBox();
    private final TextField promoTxt;
    private TreeSet<String> selectedPromos = new TreeSet<>();

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
                addPromo(promoTxt.getText());
            }
        });
    }

    public void addPromo(String name) {
        if (name.isEmpty()) return;
        String cleanedName = Clean.cleanPromo(name);
        promoTxt.setText("");
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
    }

    public void removePromo(String name) {

        for (Node child : buttonContainer.getChildren()) {
            if (child instanceof Button && ((Button) child).getText().equals(name)) {
                buttonContainer.getChildren().remove(child);
                break;
            }
        }
    }

    public String[] getValidatedPromos() {
        String[] result = new String[buttonContainer.getChildren().size()];
        for (int i = 0; i < buttonContainer.getChildren().size(); i++) {
            result[i] = ((Button)buttonContainer.getChildren().get(i)).getText();
        }
        return result;
    }
}
