package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.MusicItem;
import util.WestminsterMusicStoreManager;

public class StoreManagerGUI extends Application {

    private static WestminsterMusicStoreManager storeManager;
    private Stage window;
    private TableView<MusicItem> tableView;
    private TextField searchInput;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Products display");

        storeManager = new WestminsterMusicStoreManager();

        //Table view
        TableColumn<MusicItem, String> itemNumberColumn = new TableColumn<>("Number");
        itemNumberColumn.setMinWidth(20);
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("itemID"));

        TableColumn<MusicItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("title"));

        TableColumn<MusicItem, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setMinWidth(200);
        artistColumn.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("artist"));

        TableColumn<MusicItem, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setMinWidth(200);
        genreColumn.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("genre"));

        tableView = new TableView<>();
        tableView.setItems(getAllItems());
        tableView.getColumns().addAll(itemNumberColumn, titleColumn, artistColumn, genreColumn);

        //Top label
        Label title = new Label();
        title.setText("Products Display");
        title.setFont(new Font("Arial", 30));
        title.setPadding(new Insets(15, 15, 15, 15));
        title.setAlignment(Pos.CENTER);

        //Search input
        searchInput = new TextField();
        searchInput.setPromptText("Enter the search query");

        //Search button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchButtonClicked());

        //Exit button
        Button exitButton = new Button("Exit Program");
        exitButton.setOnAction(e -> exitButtonClicked());

        //Search wrapper
        HBox searchWrapper = new HBox();
        searchWrapper.setAlignment(Pos.CENTER_RIGHT);
        searchWrapper.setPadding(new Insets(0, 0, 0, 150));
        searchWrapper.getChildren().addAll(searchInput, searchButton);

        //Bottom wrapper
        HBox bottomWrapper = new HBox();
        bottomWrapper.setAlignment(Pos.CENTER_RIGHT);
        bottomWrapper.setPadding(new Insets(10, 30, 10, 10));
        bottomWrapper.getChildren().addAll(exitButton);

        //Top bar wrapper
        HBox topbarWrapper = new HBox();
        topbarWrapper.setPadding(new Insets(20, 20, 20, 10));
        topbarWrapper.setSpacing(10);
        topbarWrapper.getChildren().addAll(title, searchWrapper);

        //Overall wrapper
        VBox overallWrapper = new VBox();
        overallWrapper.getChildren().addAll(topbarWrapper, tableView, bottomWrapper);

        Scene scene = new Scene(overallWrapper);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    private void exitButtonClicked() {
        window.close();
    }

    private void searchButtonClicked() {
        String searchQuery = searchInput.getText();
        tableView.setItems(getSearchItems(searchQuery));
    }

    private ObservableList<MusicItem> getAllItems() {
        ObservableList<MusicItem> items = FXCollections.observableArrayList();
        items.addAll(storeManager.getStoreItems());
        return items;
    }

    private ObservableList<MusicItem> getSearchItems(String query) {
        ObservableList<MusicItem> items = FXCollections.observableArrayList();
        items.addAll(storeManager.searchItems(query));
        return items;
    }
}
