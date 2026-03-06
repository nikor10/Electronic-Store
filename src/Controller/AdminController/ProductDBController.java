package Controller.AdminController;

import View.AdminViews.ProductDBView;
import View.CashierView.ProductDatabaseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import models.Item;

public class ProductDBController {
    private final ProductDBView view = new ProductDBView();
    private final ObservableList<Item> itemsList = FXCollections.observableArrayList();

    public ProductDBController() {
        initialize();
        setUpEventHandlers();
    }

    private void initialize() {
        getItemsList();
        view.getProductTable().setItems(itemsList);
        view.getProductTable().setEditable(false);
        setUpEventHandlers();
    }

    private void setUpEventHandlers() {
        view.getSearchBtn().setOnAction(event -> {showSearchResult();});
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

    public ProductDBView getView() {
        return view;
    }


}
