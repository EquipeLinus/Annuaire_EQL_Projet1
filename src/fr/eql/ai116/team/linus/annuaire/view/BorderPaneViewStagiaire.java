package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class BorderPaneViewStagiaire extends BorderPane {


    public BorderPaneViewStagiaire( TableView<Stagiaire> table){

        super();
        List<Stagiaire> stagiaireList = new ArrayList<>();
        Stagiaire stagiaire1 = new Stagiaire("Jean","Dupont","Ai116",1550,75012);
        Stagiaire stagiaire2 = new Stagiaire("Rachel","Aremab","Ai114",1950,75012);
        stagiaireList.add(stagiaire1);
        stagiaireList.add(stagiaire2);

        table.setEditable(false);

        TableColumn<Stagiaire, String> lastNameCol = new TableColumn<>("Nom");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("lastName"));

        TableColumn<Stagiaire, String> firstNameCol = new TableColumn<>("Prénom");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));

        TableColumn<Stagiaire, String> promotionCol = new TableColumn<>("Promotion");
        promotionCol .setMinWidth(200);
        promotionCol .setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promotion"));

        TableColumn<Stagiaire, String> yearCol = new TableColumn<>("Année");
        yearCol.setMinWidth(200);
        yearCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));

        TableColumn<Stagiaire, String> departmentCol = new TableColumn<>("Département");
        departmentCol.setMinWidth(200);
        departmentCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("department"));

        table.getColumns().addAll(lastNameCol,firstNameCol,promotionCol,yearCol,departmentCol);
        table.setItems(FXCollections.observableList(stagiaireList));

        this.setCenter(table);
    }


}
