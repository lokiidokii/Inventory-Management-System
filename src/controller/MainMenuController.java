package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


/**
 * Main Menu Controller Class.
 *
 * @author hannahbergman
 */

/** Main Menu Controller. */
public class MainMenuController implements Initializable {

    // MAIN MENU VARIABLES
    // These come from Scene Builder
    
    /** Parts Search Bar. */
    @FXML
    private TextField partsSearch;

    /** Parts Table. */
    @FXML
    private TableView<Part> partsTbl;

    /** Parts Table Inventory Level Column. */
    @FXML
    private TableColumn<Part, Integer> partsTblInvLevelCol;

    /** Parts Table Part ID Column. */
    @FXML
    private TableColumn<Part, Integer> partsTblPartIdCol;

    /** Parts Table Part Name Column. */
    @FXML
    private TableColumn<Part, String> partsTblPartNameCol;

    /** Parts Table Price Column. */
    @FXML
    private TableColumn<Part, Double> partsTblPriceCol;

    /** Products Search Bar. */
    @FXML
    private TextField productsSearch;

    /** Products Table. */
    @FXML
    private TableView<Product> productsTbl;

    /** Products Table Inventory Level Column. */
    @FXML
    private TableColumn<Product, Integer> productsTblInvLevelCol;

    /** Products Table Price Column. */
    @FXML
    private TableColumn<Product, Double> productsTblPriceCol;

    /** Products Table Product ID Column. */
    @FXML
    private TableColumn<Product, Integer> productsTblProductIdCol;

    /** Products Table Product Name Column. */
    @FXML
    private TableColumn<Product, String> productsTblProductNameCol;
    
    // REFERENCE VARIABLES
    
    /** Stage variable of type Stage. 
     * Sets the stage for user.
     */
    Stage stage;
    
    /** Scene variable of type Parent. 
     * Sets the scene for user. 
     */
    Parent scene; 
    
    /** Part variable - selected Part. 
     * Create variable for the selected part that will be deleted/modified.
     */
    private static Part selectedPart;
    
    /** Part variable - selected Product.
     * Create variable for the selected product that will be deleted/modified.
     * 
     * RUNTIME ERROR - Copy + pasted the above comment with the intention to switch the word part with product and realized my program was flagging the retrieval of this variable below.
     * Fix: Learned that I have to be careful with editing my comments because carelessness might lead to accidentally commenting out portions of my code. 
     */
    private static Product selectedProduct;
    
    // METHODS
    
    /** Get the selected part. 
     * Get the selected part for part deletion and modification.
     */
    public static Part getSelectedPart(){
        return selectedPart;
    }
   
    /** Get the selected product.
     * Get the selected product for product deletion and modification.
     * 
     */
    public static Product getSelectedProduct(){
        return selectedProduct;
    }

    //BUTTONS 
    
    /** Delete Part Button. 
     * Deletes the selected part.
     * Prompts user with an alert to confirm part's deletion. 
     * If there is an associated part, user will be shown an alert notifying them that they can't delete the part.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        // Get selected product
        Part selectedPart = partsTbl.getSelectionModel().getSelectedItem();
        // Alert user that part must be selected to continue
        if (selectedPart == null) {
            displayAlert(0);
        } else {
            // Alert user that partt will be deleted + confirm with them that it's ok
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE PART");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> userResponse = alert.showAndWait();
            // Alert user that they can't delete a product if it has an associated part
            // Alert user that they must delete the associated part before they can delete the product
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /** Delete Product Button. 
     * Deletes the selected product.
     * Prompts user with an alert to confirm product's deletion. 
     * If there is an associated part, user will be shown an alert notifying them that they can't delete the product.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        // Get selected product
        Product selectedProduct = productsTbl.getSelectionModel().getSelectedItem();
        // Alert user that product must be selected to continue
        if (selectedProduct == null) {
            displayAlert(1);
        } else {
            // Alert user that product will be deleted + confirm with them that it's ok
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE PRODUCT");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> userResponse = alert.showAndWait();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                ObservableList<Part> associatedPart = selectedProduct.getAllAssociatedParts();
                // Alert user that they can't delete a product if it has an associated part
                // Alert user that they must delete the associated part before they can delete the product
                if (associatedPart.size() >= 1) {
                    displayAlert(2);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }  
    }

    /** Exit Application Button.
     * Returns user to the Main Menu.
     */
    @FXML
    void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** Add Parts Button.
     * Takes user to the Add Part form.
     */
    @FXML
    void onActionGoToAddPartForm(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Add Products Button.
     * Takes user to the Add Product form.
     */
    @FXML
    void onActionGoToAddProductForm(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Modify Parts Button. 
     * Takes user to the Modify Parts form. 
     * If part is selected, moves selected part into the Modify Parts form.
     * If part isn't selected, alerts user to select part before continuing.
     */
    @FXML
    public void onActionGoToModifyPartForm(ActionEvent event) throws IOException {

        // Get selected part
        selectedPart = partsTbl.getSelectionModel().getSelectedItem();
        
        /** RUNTIME ERROR: If user doesn't select a part, application crashes.
         * This alert reminds them to select a part before continuing.
         */
        if(selectedPart == null){
            displayAlert(0);
        }
        // Once part is selected, user is then taken to Modify Part form
        else {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }
       
    /** Modify Products Button. 
     * Takes user to the Modify Products form. 
     * If product is selected, moves selected part into the Modify Products form.
     * If product isn't selected, alerts user to select product before continuing.
     * 
     * RUNTIME ERROR: Forgot to add a break after my 2nd alert, causing the alerts to continue.
     * Fix: Added breaks after each alert case to separate the alerts. 
     */
    @FXML
    void onActionGoToModifyProductForm(ActionEvent event) throws IOException {
     
        // Get selected product
        selectedProduct = productsTbl.getSelectionModel().getSelectedItem();
        
         /** RUNTIME ERROR: If user doesn't select a product, application crashes.
         * This alert reminds them to select a product before continuing.
         */
        if (selectedProduct == null) {
            displayAlert(1);
        } else {
        // Once part is selected, user is then taken to Modify Product form
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/modifyProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }
    
    // SEARCH BARS
    
    /** 
     * Search for parts by ID or name using the search bar on main menu. 
     *
     * RUNTIME ERROR: I was able to search by product ID but not by product name
     * Fix: Realized the "Search by Part ID or Name" needs to be Prompt Text, can't be part of Text in Scene Builder.
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchedPart = partsSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchedPart) ||
                    part.getName().contains(searchedPart)) {
                foundPart.add(part);
            }
        }

        partsTbl.setItems(foundPart);

        if (foundPart.isEmpty()) {
            displayAlert(3);
        }
        
     } 
    


    /** 
     * Search for products by ID or name using the search bar on main menu. 
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        String searchString = productsSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                foundProduct.add(product);
            }
        }

        productsTbl.setItems(foundProduct);

        if (foundProduct.isEmpty()) {
            displayAlert(4);
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url The initialized url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /**
         * Provide data to Parts table in the Main Menu.
         */
        partsTblPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTblPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTblPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTbl.setItems(Inventory.getAllParts());
        /**
         * Provide data to Products table in the Main Menu.
         */
        productsTblProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsTblProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsTblPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));       
        productsTbl.setItems(Inventory.getAllProducts());
    }    
 
    // ALERT MESSAGES
    
    /** Alert messages for various errors. 
     *  
     *  0 - User needs to select part to mod or delete
     *  1 - User needs to select product to mod or delete
     *  2 - Parts associated with product
     *  3 - Part not found
     *  4 - Product not found
     * 
     */
    private void displayAlert(int alertType){
        Alert alert = new Alert(AlertType.ERROR);
        
        switch (alertType) {
            case 0: 
                alert.setTitle("UNABLE TO MODIFY OR DELETE PART");
                alert.setHeaderText("Please select the part you want to modify or delete");
                alert.showAndWait();
                break;
            case 1: 
                alert.setTitle("UNABLE TO MODIFY OR DELETE PRODUCT");
                alert.setHeaderText("Please select the product you want to modify or delete");
                alert.showAndWait();
                break;
            case 2: 
                alert.setTitle("CANNOT DELETE PRODUCT");
                alert.setHeaderText("There are parts associated with this product");
                alert.setContentText("All parts associated with this product must be deleted first");
                alert.showAndWait();
                break;
            case 3: 
                alert.setTitle("PART NOT FOUND");
                alert.setHeaderText("There are no parts with that name or ID number");
                alert.showAndWait();
                break;
            case 4: 
                alert.setTitle("PRODUCT NOT FOUND");
                alert.setHeaderText("There are no products with that name or ID number");
                alert.showAndWait();
                break;
         
        }
    }
}

