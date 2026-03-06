package View.CashierView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Bill;

public class CheckOutView extends BorderPane {
    DropShadow shadow = new DropShadow(10, 5, 5, Color.GRAY);
    private final Button addBtn = new Button("Add");
    private Button deleteBtn = new Button("Delete");
    private final TextField prodIdTF = new TextField();
    private final TextField quantityTF = new TextField();
    private Label informationLabel = new Label();
    private final Button printBillBtn = new Button("Print Bill");
    private final TableView<Bill.BillRow> currentBill = new TableView<>();
    private ObservableList<Bill.BillRow> billRows = FXCollections.observableArrayList();
    private TextField clientCashInput = new TextField();
    private Label clientChange = new Label();

    public CheckOutView() {
        setStyle("-fx-background-color: rgb(255, 248, 240);");
        setPadding(new Insets(10));

        Label welcomeLabel = new Label("Welcome Cashier");
        welcomeLabel.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 30; -fx-font-weight: bold;");
        welcomeLabel.setEffect(shadow);
        welcomeLabel.setAlignment(Pos.BASELINE_CENTER);
        setTop(welcomeLabel);
        setAlignment(welcomeLabel, Pos.CENTER);

        // Product ID TextField
        prodIdTF.setPromptText("Product ID...");
        prodIdTF.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 25; -fx-text-fill: rgb(70,70,70);");
        prodIdTF.setPrefHeight(50);

        // Quantity TextField
        quantityTF.setPromptText("Quantity...");
        quantityTF.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 25; -fx-text-fill: rgb(70,70,70);");
        quantityTF.setPrefHeight(50);

        addBtn.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-font-size: 20; -fx-text-fill: rgb(255, 207, 153)");
        addBtn.setEffect(shadow);

        deleteBtn.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-font-size: 20; -fx-text-fill: rgb(255, 207, 153);");
        deleteBtn.setEffect(shadow);

        printBillBtn.setPrefHeight(50);
        printBillBtn.setStyle("-fx-background-color: rgb(17, 29, 74); -fx-font-size: 20; -fx-text-fill: rgb(255, 207, 153);");
        printBillBtn.setEffect(shadow);

        HBox informationRow = new HBox();
        informationRow.setStyle("-fx-background-color: rgb(255,207,153);");
        informationRow.setAlignment(Pos.CENTER_LEFT);

        informationLabel.setStyle("-fx-text-fill: #111D4AFF; -fx-font-size: 18; -fx-font-weight: bold;");
        informationLabel.setAlignment(Pos.CENTER);
        informationRow.getChildren().add(informationLabel);

        HBox buttonRow1 = new HBox(25);
        buttonRow1.setAlignment(Pos.CENTER);
        buttonRow1.setPadding(new Insets(20));
        buttonRow1.getChildren().addAll(addBtn, deleteBtn);

        Label cashLabel = new Label("CASH: ");
        cashLabel.setStyle("-fx-text-fill: #111D4AFF; -fx-font-size: 18; -fx-font-weight: bold;");
        clientCashInput.setStyle("-fx-text-fill: #111D4AFF; -fx-font-size: 18; -fx-font-weight: bold;"); //boxi
        clientCashInput.setPrefSize(60, 10);
        clientCashInput.setEditable(true);
        HBox clientCash = new HBox();//cash: box
        clientCash.getChildren().addAll(cashLabel, clientCashInput);

        clientChange.setStyle("-fx-text-fill: #111D4AFF; -fx-font-size: 18; -fx-font-weight: bold;"); //restoja
        HBox changeRow = new HBox(clientCash, clientChange);
        changeRow.setStyle("-fx-background-color: rgb(253,206,152)");
        

        VBox leftSide = new VBox(15);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.getChildren().addAll(prodIdTF, quantityTF, buttonRow1, printBillBtn, informationRow, changeRow);
        setAlignment(leftSide, Pos.CENTER_LEFT);

        TableColumn<Bill.BillRow, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setStyle("-fx-text-fill: #111D4AFF; -fx-background-color: rgb(253,246,239); -fx-font-size: 18;");

        TableColumn<Bill.BillRow, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-text-fill: #111D4AFF; -fx-background-color: rgb(253,246,239); -fx-font-size: 18; -fx-text-alignment: center;");

        TableColumn<Bill.BillRow, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setStyle("-fx-text-fill: #111D4AFF; -fx-background-color: rgb(253,246,239); -fx-font-size: 18; -fx-text-alignment: center;");

        TableColumn<Bill.BillRow, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setStyle("-fx-text-fill: #111D4AFF; -fx-background-color: rgb(253,246,239); -fx-font-size: 18; -fx-text-alignment: center;");
        quantityColumn.setOnEditCommit(event -> {
            Bill.BillRow row = event.getRowValue();
            row.setQuantity(event.getNewValue());
            row.updateTotal();
        });

        TableColumn<Bill.BillRow, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalColumn.setStyle("-fx-text-fill: #111D4AFF; -fx-background-color: rgb(253,246,239); -fx-font-size: 18; -fx-text-alignment: center;");

        currentBill.getColumns().addAll(nameColumn, idColumn, priceColumn, quantityColumn, totalColumn);
        currentBill.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        currentBill.setMaxHeight(500);
        currentBill.setMinWidth(500);
        currentBill.setItems(billRows);
        currentBill.setStyle("-fx-border-color: rgb(217,214,212); -fx-background-color: rgba(255, 248, 240, 1); -fx-text-fill: #111D4AFF;");
        setAlignment(currentBill, Pos.CENTER_LEFT);

        ScrollPane scrollPane = new ScrollPane(currentBill);
        scrollPane.setFitToWidth(true);

        HBox layout = new HBox(50);
        layout.getChildren().addAll(leftSide, currentBill);
        layout.setAlignment(Pos.CENTER);
        setCenter(layout);
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }

    public Label getInformationLabel() {
        return informationLabel;
    }

    public TextField getQuantityTF() {
        return quantityTF;
    }

    public DropShadow getShadow() {
        return shadow;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public TextField getProdIdTF() {
        return prodIdTF;
    }

    public Button getPrintBillBtn() {
        return printBillBtn;
    }

    public TableView<Bill.BillRow> getCurrentBill() {
        return currentBill;
    }

    public TextField getClientCashInput() {
        return clientCashInput;
    }

    public Label getClientChange() {
        return clientChange;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
