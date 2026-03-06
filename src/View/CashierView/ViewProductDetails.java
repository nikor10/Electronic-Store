package View.CashierView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.rgb;

public class ViewProductDetails extends Node {
    public ViewProductDetails() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: rgb(255, 248, 240);");

        //back button stack setup
        Button backBtn = new Button(" Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(137,149,194); -fx-font-size: 17");
        Rectangle backBG = new Rectangle(65, 30);
        backBtn.setAlignment(Pos.CENTER);
        backBG.setFill(rgb(32,55,140));
        backBG.setArcHeight(40);
        backBG.setArcWidth(40);

        StackPane backStack = new StackPane(backBG, backBtn);
        backStack.setAlignment(Pos.CENTER);
        backStack.setPrefWidth(100);
        backStack.setPadding(new Insets(10));

        //top bar
        Label title = new Label("Product: " /* + productName*/);
        title.setStyle("-fx-font-size: 30; -fx-text-fill: rgb(255,207,153); -fx-font-weight: italic");
        title.setAlignment(Pos.BASELINE_CENTER);

        HBox titleHBox = new HBox(backStack, title);
        titleHBox.setAlignment(Pos.CENTER_LEFT);
        titleHBox.setSpacing(25);
        titleHBox.setPadding(new Insets(10));
        titleHBox.setStyle("-fx-background-color: rgb(17,29,74)");

        root.getChildren().add(titleHBox);

        //product info
        Label productInfo = new Label("Name:\nID:\nPrice:\nStock:\nSection:\nSupplier:\n");
        productInfo.setStyle("-fx-font-size: 20");
        productInfo.setAlignment(Pos.CENTER_LEFT);
        productInfo.setPadding(new Insets(20, 10, 10, 10));

        Pane productInfoVBox = new Pane( productInfo);
        productInfoVBox.setPadding(new Insets(10, 20, 30, 40));
        productInfoVBox.setStyle("-fx-background-color: rgb(255,207,153)");

        root.getChildren().add(productInfoVBox);
    }
}
