package View.CashierView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class StatisticsView extends BorderPane {
    private final Label result = new Label();
    private final ComboBox<String> profitDate = new ComboBox<>();
    public VBox billCard;
    private Label profit = new Label();
    private Label billId = new Label();
    private Label productId = new Label();
    private Label quantity = new Label();
    private Label price = new Label();
    private Label totalAmount = new Label();
    private Label date = new Label();
    private VBox singleBill = new VBox(10);
    private HBox productDetailRow = new HBox(50);

    public VBox getRightLayout() {
        return rightLayout;
    }

    VBox rightLayout = new VBox(25);

    public VBox getBillCard() {
        return billCard;
    }

    public StatisticsView() {
        setPadding(new Insets(20));
        setStyle("-fx-background-color: rgb(255, 248, 240);");

        //title
        Label title = new Label("Display of Bills");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: rgb(255,207,153); -fx-font-size: 30");
        title.setAlignment(Pos.CENTER);

        HBox titleRow = new HBox(20, title);
        titleRow.setPadding(new Insets(20));
        titleRow.setStyle("-fx-background-color: rgb(17,29,74)");
        titleRow.setAlignment(Pos.CENTER);
        setTop(titleRow);

        VBox container = new VBox();

        //second label
        profit.setStyle("-fx-text-fill: black; -fx-font-size: 20;");
        profit.setAlignment(Pos.TOP_LEFT);

        profitDate.getItems().addAll("Today", "All Time");
        profitDate.setValue("Today");
        profitDate.setStyle("-fx-text-fill: black; -fx-background-color: transparent; -fx-border-color: #111d4a");
        setAlignment(profitDate, Pos.BASELINE_RIGHT);

        HBox statsRow = new HBox();
        statsRow.setPadding(new Insets(20));
        statsRow.setStyle("-fx-background-color: rgb(255,207,153)");
        statsRow.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        statsRow.getChildren().addAll(profit, spacer, profitDate);

        VBox leftLayout = new VBox(10);
        leftLayout.maxWidthProperty().bind(this.widthProperty().divide(2));
        leftLayout.prefWidthProperty().bind(this.widthProperty().divide(2));

        container.getChildren().addAll(titleRow, statsRow);
        setTop(container);

        //bills list background
        billCard = new VBox(25);
        billCard.setPadding(new Insets(10));
        billCard.setStyle("-fx-background-color: rgb(17,29,74); -fx-border-color: rgb(160, 160, 160); -fx-border-width: 2");
        billCard.setPrefWidth(300);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: rgb(137,149,194)");
        scrollPane.setContent(billCard);

        leftLayout.getChildren().addAll(billCard, scrollPane);
        setLeft(leftLayout);

        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        setRight(scrollPane2);
        scrollPane2.setStyle("-fx-background-color: rgb(137,149,194)");
        scrollPane2.setContent(rightLayout);

        rightLayout.setPadding(new Insets(20, 50, 20, 50));
        rightLayout.setAlignment(Pos.CENTER);
        setRight(rightLayout);
    }

    public void clearBillCards() {
        billCard.getChildren().clear();
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }

    public Label getResult() {
        return result;
    }

    public ComboBox<String> getProfitDate() {
        return profitDate;
    }

    public Label getBillId() {
        return billId;
    }

    public Label getProductId() {
        return productId;
    }

    public Label getQuantity() {
        return quantity;
    }

    public Label getPrice() {
        return price;
    }

    public Label getDate() {
        return date;
    }

    public Label getTotalAmount() {
        return totalAmount;
    }

    public Label getProfit() {
        return profit;
    }

    public VBox getSingleBill() {
        return singleBill;
    }

}
