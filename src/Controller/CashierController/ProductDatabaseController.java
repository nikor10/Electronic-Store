package Controller.CashierController;

import View.CashierView.ProductDatabaseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Item;

public class ProductDatabaseController {
    private final ProductDatabaseView view = new ProductDatabaseView();
    private final ObservableList<Item> itemsList = FXCollections.observableArrayList();

    public ProductDatabaseController() {
        initialize();
        setUpEventHandlers();
    }

    public ProductDatabaseView getView() {
        return view;
    }

    private void initialize() {
        getItemsList();
        view.getProductTable().setItems(itemsList);
        view.getProductTable().setEditable(false);
        setUpEventHandlers();
    }

    private void setUpEventHandlers() {
        view.getSearchBtn().setOnAction(event -> {showSearchResult();});

        view.getProductTable().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showProductDetails(newValue);
            }
        });
    }

    private void showProductDetails(Item selectedProduct) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title = selectedProduct.getItemName();
        Label customHeader = new Label("Details: " + title);
        customHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: rgb(255,207,153); -fx-text-alignment: CENTER");

        HBox titleBg = new HBox();
        titleBg.setStyle("-fx-background-color: rgb(17,29,74);");
        titleBg.setAlignment(Pos.TOP_CENTER);
        titleBg.getChildren().add(customHeader);

        dialog.getDialogPane().setStyle("-fx-background-color: #ffcf99;");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #ffcf99;");
        grid.setPadding(new Insets(10));

        grid.add(titleBg, 0, 0, 2, 1);
        GridPane.setMargin(customHeader, new Insets(0, 0, 20, 5)); // Add margin below the title

        Label name = createLabel("Name");
        Label sellingPrice = createLabel("Selling Price");
        Label purchasePrice = createLabel("Purchase Price");
        Label stock = createLabel("Stock");
        Label section = createLabel("Section");
        Label supplier = createLabel("Supplier");

        Label nameValue = new Label();
        nameValue = createDetailLabel(nameValue, selectedProduct.getItemName());
        Label sellingPriceValue = new Label();
        sellingPriceValue = createDetailLabel(sellingPriceValue, String.valueOf(selectedProduct.getSellingPrice()));
        Label purchasePriceValue = new Label();
        purchasePriceValue = createDetailLabel(purchasePriceValue, String.valueOf(selectedProduct.getPurchasedPrice()));
        Label stockValue = new Label();
        stockValue = createDetailLabel(stockValue, String.valueOf(selectedProduct.getStock()));
        Label sectionValue = new Label();
        sectionValue = createDetailLabel(sectionValue, selectedProduct.getSection());
        Label supplierValue = new Label();
        supplierValue = createDetailLabel(supplierValue, selectedProduct.getSupplier());

        grid.add(name, 0, 1);
        grid.add(nameValue, 1, 1);
        grid.add(sellingPrice, 0, 2);
        grid.add(sellingPriceValue, 1, 2);
        grid.add(purchasePrice, 0, 3);
        grid.add(purchasePriceValue, 1, 3);
        grid.add(stock, 0, 4);
        grid.add(stockValue, 1, 4);
        grid.add(section, 0, 5);
        grid.add(sectionValue, 1, 5);
        grid.add(supplier, 0, 6);
        grid.add(supplierValue, 1, 6);

        dialog.getDialogPane().setPrefSize(300, 300);
        dialog.getDialogPane().setContent(grid);


        dialog.showAndWait();
    }

    public ObservableList<Item> getItemsList() {
        itemsList.add(new Item("Battery", 1, "Electronics", "Alibaba", 10.0, 20.0, 100));
        itemsList.add(new Item("Lamp", 2, "Appliances", "Temu", 5.5, 12.0, 200));
        itemsList.add(new Item("iPhone", 3, "Electronics", "Apple", 50.0, 800.0, 50));
        itemsList.add(new Item("iPad", 4, "Electronics", "Apple", 70.0, 700.0, 100));
        itemsList.add(new Item("XBox", 5, "Electronics", "Microsoft", 30.0, 600.0, 50));
        itemsList.add(new Item("SamsungTV", 6, "Electronics", "Samsung", 60.0, 800.0, 30));
        itemsList.add(new Item("Laptop", 7, "Electronics", "DELL", 50.0, 1600.0, 50));
        itemsList.add(new Item("TV", 8, "Electronics", "LG", 55.0, 900.0, 20));
        return itemsList;
    }


    public ObservableList<Item> searchProductId()
    {
        String targetProduct = view.getSearchField().getText().toLowerCase();
        ObservableList<Item> filteredList = FXCollections.observableArrayList();
        for(Item item : itemsList)
        {
            if(String.valueOf(item.getItemid()).equals(targetProduct) || item.getItemName().toLowerCase().contains(targetProduct))
                filteredList.add(item);
        }
        return filteredList;
    }

    public void showSearchResult() {
        ObservableList<Item> filteredList = searchProductId();
        if(filteredList.size() > 0)
            view.getProductTable().setItems(filteredList);
        else
            showAlert(Alert.AlertType.INFORMATION, "No such item found.", "We couldn't find any item matching your search.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        label.setAlignment(Pos.CENTER_LEFT);
        return label;
    }

    public Label createDetailLabel(Label label, String name) {
        label.setText(name);
        label.setStyle("-fx-font-size: 20;");
        return label;
    }

}
