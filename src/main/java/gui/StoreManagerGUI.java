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

    private Stage mainStage;
    private TableView<MusicItem> detailsDisplay;
    private TextField searchInput;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        storeManager = new WestminsterMusicStoreManager();

        mainStage = primaryStage;
        mainStage.setTitle("Music items showcase");

        //Table view
        TableColumn<MusicItem, String> itemIDClm = new TableColumn<>("ItemID");
        itemIDClm.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("itemID"));
        itemIDClm.setMinWidth(10);

        TableColumn<MusicItem, String> itemTitleClm = new TableColumn<>("Title");
        itemTitleClm.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("title"));
        itemTitleClm.setMinWidth(230);

        TableColumn<MusicItem, String> itemArtistClm = new TableColumn<>("Artist");
        itemArtistClm.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("artist"));
        itemArtistClm.setMinWidth(150);

        TableColumn<MusicItem, String> itemGenreClm = new TableColumn<>("Album Genre");
        itemGenreClm.setCellValueFactory(new PropertyValueFactory<MusicItem, String>("genre"));
        itemGenreClm.setMinWidth(100);

        detailsDisplay = new TableView<>();
        detailsDisplay.setItems(getListItems());
        detailsDisplay.getColumns().addAll(itemIDClm, itemTitleClm, itemArtistClm, itemGenreClm);

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
        searchButton.setOnAction(e -> performSearch());

        //Exit button
        Button exitButton = new Button("Exit Program");
        exitButton.setOnAction(e -> closeWindow());

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
        overallWrapper.getChildren().addAll(topbarWrapper, detailsDisplay, bottomWrapper);

        //Set scene
        Scene scene = new Scene(overallWrapper);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    //Start the searching process
    private void performSearch() {
        String searchQuery = searchInput.getText();
        detailsDisplay.setItems(searchForItems(searchQuery));
    }

    //Add all items to list
    private ObservableList<MusicItem> searchForItems(String query) {
        ObservableList<MusicItem> items = FXCollections.observableArrayList();
        items.addAll(storeManager.searchItems(query));
        return items;
    }

    //Search for items and add to table
    private ObservableList<MusicItem> getListItems() {
        ObservableList<MusicItem> items = FXCollections.observableArrayList();
        items.addAll(storeManager.getStoreItems());
        return items;
    }

    //Close the window
    private void closeWindow() {
        mainStage.close();
    }
}
