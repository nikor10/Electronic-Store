package View.AdminViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.SupplierTable;

public class SupplierView extends BorderPane {
    private final TableView<SupplierTable> supplierTable;

//    public SupplierView() {
//        // Title
//        Label title = new Label("Supplier List");
//        title.setStyle("-fx-text-fill: rgb(17, 29, 74); -fx-font-size: 24; -fx-font-weight: bold;");
//        title.setAlignment(Pos.TOP_CENTER);
//        setTop(title);
//
//        // Table View
//        supplierTable = new TableView<>();
//        createTableColumns();
//
//        setStyle("-fx-background-color: rgb(255, 248, 240);");
//        getChildren().addAll(title, supplierTable);
//        setPadding(new Insets(20));
//        setCenter(supplierTable);
//    }

    public SupplierView() {
        // Title
        Label title = new Label("Supplier List");
        title.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 38; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        title.setAlignment(Pos.TOP_CENTER);
        setTop(title);

        // Table View
        supplierTable = new TableView<>();
        createTableColumns();

        setStyle("-fx-background-color: rgb(255, 248, 240);");
        setPadding(new Insets(20));
        setCenter(supplierTable); // Correctly set the table in the center of the BorderPane
    }


    private void createTableColumns() {
        TableColumn<SupplierTable, Integer> supplierNumberCol = new TableColumn<>("Num");
        supplierNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        supplierNumberCol.setPrefWidth(50); // Adjusted width for consistency

        TableColumn<SupplierTable, String> supplierNameCol = new TableColumn<>("Supplier");
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierNameCol.setPrefWidth(200);

        TableColumn<SupplierTable, String> productsCol = new TableColumn<>("Products");
        productsCol.setCellValueFactory(new PropertyValueFactory<>("products"));
        productsCol.setPrefWidth(200);

        TableColumn<SupplierTable, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        contactCol.setPrefWidth(150);

        supplierTable.getColumns().addAll(supplierNumberCol, supplierNameCol, productsCol, contactCol);
        supplierTable.setStyle("-fx-border-color: rgb(17, 29, 74);");
        supplierTable.setPrefHeight(400); // Adjust table height
    }

    public TableView<SupplierTable> getSupplierTable() {
        return supplierTable;
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }
}

