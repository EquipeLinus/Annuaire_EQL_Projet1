package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.view.Clean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromoStack extends HBox {
    private HBox buttonContainer = new HBox();
    private final TextField promoTxt;
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
                addPromo(Clean.cleanPromo(promoTxt.getText()));
            }
        });
    }
    public void addPromo(String name) {
        if (name.isEmpty()) return;
        promoTxt.setText("");
        Button promoBtn = new Button(name);
        promoBtn.setMinWidth(80);
        promoBtn.setMaxWidth(80);
        promoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removePromo(name);
            }
        });
        buttonContainer.getChildren().add(promoBtn);

        /* MODIFICATIONS FROM ANDRAS
        Button[] promoButtonsTable = new Button[buttonContainer.getChildren().size()];
        for (int i = 0; i < buttonContainer.getChildren().size(); i++) {
            promoButtonsTable[i] = (Button) buttonContainer.getChildren().get(i);
        }

        buttonContainer.getChildren().removeAll(promoButtonsTable);

        String[] promoButtonsNameTable = new String[promoButtonsTable.length];
        for (int i = 0; i < promoButtonsNameTable.length; i++) {
            promoButtonsNameTable[i] = String.valueOf(promoButtonsTable[i]);
        }

        promoButtonsTable = new Button[];

        promoButtonsNameTable = Arrays.stream(promoButtonsNameTable).sorted(String::compareTo).toArray(String[]::new);

        for (int i = 0; i < promoButtonsNameTable.length; i++) {
            Button promoButtonToReadd = new Button();
            promoButtonsNameTable[i] = (Button) promoButtonsTable[i];
        }

        promoButtonsTable.

        String[] promoButtonsNameTable = new String[promoButtonsTable.length];
        for (int i = 0; i < promoButtonsNameTable.length; i++) {
            promoButtonsNameTable[i] = ((Button) buttonContainer.getChildren().get(i)).getText();
        }
        Button[] promoButtonsTable = new Button[];
        for (int i = 0; i < buttonContainer.getChildren().size(); i++) {
            result[i] = ((Button) buttonContainer.getChildren().get(i)).getText();
        }
        buttonContainer.getChildren().removeAll(buttonContainer.getChildren().);
        promoButtonsNameTable = Arrays.stream(promoButtonsNameTable).sorted(String::compareTo).toArray(String[]::new);
        for (String promoButtonsToReaddName : promoButtonsNameTable) {
            String promoButtonToReaddName = new String(promoButtonsToReaddName);
            Button promoButtonToReadd = new Button(promoButtonToReaddName);
            promoButtonToReadd.setMinWidth(80);
            promoButtonToReadd.setMaxWidth(80);
            buttonContainer.getChildren().add(promoButtonToReadd);
        }
        // END OF ANDRAS' MODIFICATIONS */

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
            result[i] = ((Button) buttonContainer.getChildren().get(i)).getText();
        }

        result = Arrays.stream(result).sorted(String::compareTo).toArray(String[]::new);
        return result;
    }
}
