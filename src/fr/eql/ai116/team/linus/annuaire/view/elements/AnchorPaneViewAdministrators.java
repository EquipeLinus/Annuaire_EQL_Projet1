package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.AdministratorSorter;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnchorPaneViewAdministrators extends AnchorPane {
    private final TableView<Administrator> table;

    public AnchorPaneViewAdministrators(TableView<Administrator> table){
        super();
        this.table = table;

        TableColumn<Administrator, String> usernameCol = new TableColumn<>("Identifiant administrateur(s) existant");
        usernameCol.setMinWidth(200);
        usernameCol.setCellValueFactory(new PropertyValueFactory<Administrator, String>("username"));

        table.getColumns().addAll(usernameCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Administrator> administratorList = AdministratorSorter.getListAdmins();

        setTable(administratorList);

        getChildren().add(table);

        setRightAnchor(table,5.);
        setLeftAnchor(table,5.);
        setTopAnchor(table,5.);
        setBottomAnchor(table,5.);

        setMinHeight(50);
    }

    public void setTable(List<Administrator> list) {
        table.setItems(FXCollections.observableList(list));
        table.refresh();
    }
}
