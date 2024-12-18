package fr.eql.ai116.team.linus.annuaire.view.elements;

import javafx.application.Platform;

public class UpdateWindow {
    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");

        if(Platform.isFxApplicationThread()) treatment.run();
        else Platform.runLater(treatment);
    }
}
