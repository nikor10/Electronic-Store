package Controller.AdminController;

import View.AdminViews.SupplierView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import models.Item;
import models.SupplierTable;

import java.util.ArrayList;
import java.util.Arrays;

public class SupplierController {

    private final ObservableList<SupplierTable> supplierList;
    private final ObservableList<Item> productList;
    private final FilteredList<SupplierTable> filteredData;
    private final TableView<SupplierTable> supplierTable;

    private final SupplierView supplierView;

    public SupplierController() {
        // Initialize supplier and product data
        supplierList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(supplierList, p -> true);
        supplierView = new SupplierView();
        supplierTable = supplierView.getSupplierTable();

        // Bind the TableView to the filtered supplier list
        supplierTable.setItems(filteredData);

        // Add demo data
        addDemoData();
    }

    private void addDemoData() {
        supplierList.addAll(
                new SupplierTable(1, "Supplier A", new ArrayList<>(Arrays.asList("Product X", "Product Y")), "123-456-789"),
                new SupplierTable(2, "Supplier B", new ArrayList<>(Arrays.asList("Product Z", "Product W")), "987-654-321"),
                new SupplierTable(3, "Supplier C", new ArrayList<>(Arrays.asList("Product Q", "Product R")), "456-789-123"),
                new SupplierTable(4, "Supplier D", new ArrayList<>(Arrays.asList("Product S", "Product T")), "654-321-987")
        );
    }

    public ObservableList<SupplierTable> getSupplierList() {
        return supplierList;
    }

    public ObservableList<Item> getProductList() {
        return productList;
    }

    public void filterSupplierList(String searchText) {
        filteredData.setPredicate(supplier -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Show all suppliers if search is empty
            }
            String lowerCaseFilter = searchText.toLowerCase();

            // Perform filtering logic based on the supplier attributes
            boolean matchesNumber = String.valueOf(supplier.getNumber()).contains(lowerCaseFilter);
            boolean matchesName = supplier.getName().toLowerCase().contains(lowerCaseFilter);
            boolean matchesContact = supplier.getSupplierContact().toLowerCase().contains(lowerCaseFilter);
            boolean matchesProducts = supplier.getProducts().stream()
                    .anyMatch(product -> product.toLowerCase().contains(lowerCaseFilter));

            return matchesNumber || matchesName || matchesContact || matchesProducts;
        });
    }

    public SupplierView getSupplierView() {
        return supplierView;
    }
}
