package fr.eql.ai116.team.linus.annuaire;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.ExportToPdf;
import fr.eql.ai116.team.linus.annuaire.view.elements.AnchorPaneViewStagiaire;
import fr.eql.ai116.team.linus.annuaire.view.elements.SearchPanel;
import fr.eql.ai116.team.linus.annuaire.view.elements.TootipBorderPane;
import fr.eql.ai116.team.linus.annuaire.view.elements.VBoxAdmin;
import fr.eql.ai116.team.linus.annuaire.view.windows.AdministratorWindow;
import fr.eql.ai116.team.linus.annuaire.view.windows.ConnexionWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application extends javafx.application.Application {

    private static Application instance;
    private static final Logger log = LogManager.getLogger();

    private double width = 1500;
    private double height = 800;

    public Administrator account = null;
    private TableView<Stagiaire> table;

    private SearchPanel searchPanel;
    private TootipBorderPane tooltipPanel;
    private HBox adminPanel;

    public Application() {
        super();
        instance = this;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        /**
         * general variable initialization
         */

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, width, height);
        table = new TableView<>();

        /**
         * Center panel
         */

        BorderPane centerPane = new BorderPane();
        AnchorPaneViewStagiaire anchorPane = new AnchorPaneViewStagiaire(table);
        centerPane.setCenter(anchorPane);

        /**
         * Top panel
         */

        searchPanel = new SearchPanel(anchorPane);
        searchPanel.setAlignment(Pos.BOTTOM_LEFT);
        searchPanel.setPadding(new Insets(0,0,5,20));

        tooltipPanel = new TootipBorderPane(stage,width,height);

        BorderPane topPane = new BorderPane();
        topPane.setRight(tooltipPanel);
        topPane.setLeft(searchPanel);

        /**
         * Bottom Panel
         */

        adminPanel = new HBox();
        adminPanel.setAlignment(Pos.CENTER);

        /**
         * Scene & Stage setup
         */
        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(adminPanel);

        stage.setTitle("Application stagiaire EQL");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static Application getInstance() {
        return instance;
    }

    public Administrator getAccount() {
        return account;
    }

    public void setAccount(Administrator account) {
        if (account != null) {
            onConnection(account);
        } else {
            onDisconnection(this.account);
        }
        this.account = account;

        tooltipPanel.updateConnectionInfo();
    }

    public void onConnection(Administrator account) {
        if (adminPanel.getChildren().isEmpty()) {
            adminPanel.getChildren().add(new VBoxAdmin(table, searchPanel));
        }
    }

    public void onDisconnection(Administrator account) {
        if (!adminPanel.getChildren().isEmpty()){
            adminPanel.getChildren().remove(0);
        }
    }

}
