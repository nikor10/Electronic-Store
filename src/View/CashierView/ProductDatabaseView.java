package View.CashierView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Item;

import static javafx.scene.paint.Color.rgb;

public class ProductDatabaseView extends BorderPane {
        DropShadow shadow = new DropShadow(10, 5, 5, Color.GRAY);
        Color darkBlue = rgb(17, 29, 74); //dark blue
        private final Label title = new Label("Product Database");
        private final TableView<Item> productTable = new TableView<>();
        private final Button searchBtn = new Button("Search");
        private final TextField searchField = new TextField();

    public ProductDatabaseView() {
            setPadding(new Insets(20));
            setStyle("-fx-background-color: rgb(255, 248, 240);");

            //header
            title.setStyle("-fx-font-weight: bold; -fx-text-fill: rgb(255,207,153); -fx-font-size: 30");
            title.setAlignment(Pos.TOP_CENTER);

            HBox titleContainer = new HBox(title);
            titleContainer.setStyle("-fx-background-color: rgb(17,29,74);");
            titleContainer.setAlignment(Pos.CENTER);
            setTop(titleContainer);

            //prod table
            TableColumn<Item, String> productName = new TableColumn<>("Name");
            productName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
            productName.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");

            TableColumn<Item, String> productId = new TableColumn<>("ID");
            productId.setCellValueFactory(new PropertyValueFactory<Item, String>("itemid"));
            productId.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");

            TableColumn<Item, String> productQuantity = new TableColumn<>("Stock");
            productQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productQuantity.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");

            TableColumn<Item, String> productSection = new TableColumn<>("Section");
            productSection.setCellValueFactory(new PropertyValueFactory<>("section"));
            productSection.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");

            TableColumn<Item, String> productPrice = new TableColumn<>("Price");
            productPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
            productPrice.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");

            TableColumn<Item, String> productSupplier = new TableColumn<>("Supplier");
            productSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
            productSupplier.setStyle("-fx-text-fill: rgb(17,29,74); -fx-font-size: 16; -fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77); ");

            productTable.getColumns().addAll(productName, productId, productQuantity, productSection, productPrice);
            productTable.setPrefWidth(360);
            productTable.setEditable(false);
            productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            productTable.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-border-color: rgb(127,104,77);");
            productTable.setEffect(shadow);
            setMargin(productTable, new Insets(10));

            //search button
            searchBtn.setEffect(shadow);
            searchBtn.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255,207,153); -fx-text-alignment: center");
            searchField.setPromptText("Search product ID or name...");
            searchField.setPrefWidth(150);

            HBox searchBox = new HBox(15, searchField, searchBtn);
            searchBox.setSpacing(15);
            searchBox.setAlignment(Pos.TOP_RIGHT);

            VBox layout = new VBox(25);
            layout.setPadding(new Insets(25, 10, 10, 10));
            layout.getChildren().addAll(productTable, searchBox);
            setCenter(layout);
        }

        public TableView<Item> getProductTable() {
                return productTable;
        }

        public Label getTitle() {
                return title;
        }

        public Button getSearchBtn() {
                return searchBtn;
        }

        public TextField getSearchField() {
                return searchField;
        }

        public void addNavBar(HBox navBar)
        {
                setBottom(navBar);
        }
}
