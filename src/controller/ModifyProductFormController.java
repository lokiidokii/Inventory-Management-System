package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author hannahbergman
 */

/** Modify Product Form Controller. */
public class ModifyProductFormController implements Initializable {

    // MODIFY PRODUCT VARIABLES
  
    /** Modify Product ID. */      
    @FXML
    private TextField modifyProductIdTxt;
    
    /** Modify Product Name. */ 
    @FXML
    private TextField modifyProductNameTxt;

    /** Modify Product Inv. */ 
    @FXML
    private TextField modifyProductInvTxt;
    
    /** Modify Product Price. */
    @FXML
    private TextField modifyProductPriceTxt;

    /** Modify Product Max. */ 
    @FXML
    private TextField modifyProductMaxTxt;

    /** Modify Product Min. */ 
    @FXML
    private TextField modifyProductMinTxt;
    
    // PART TABLE
    
    /** Part Table View. */
    @FXML
    private TableView<Part> modifyProductPartDataTbl;

    /** Part Table Part ID Column. */
    @FXML
    private TableColumn<Part, Integer> MPpartDataPartId;

    /** Part Table Part Name Column. */
    @FXML
    private TableColumn<Part, String> MPpartDataPartName;
    
    /** Part Table Part Inventory Level Column. */
    @FXML
    private TableColumn<Part, Integer> MPpartDataInvLevel;

    /** Part Table Part Price Column. */
     @FXML
    private TableColumn<Part, Double> MPpartDataPrice;
    
    // ASSOCIATED PART TABLE 
    
    /** Associated Part Table View. */
    @FXML
    private TableView<Part> modifyProductAssociatedPartTbl;
    
   /** Associated Part Table Part ID Column. */
    @FXML
    private TableColumn<Part, Integer> MPassociatePartPartId;
    
    /** Associated Part Table Part Name Column. */
    @FXML
    private TableColumn<Part, String> MPassociatedPartName;
    
    /** Associated Part Table Part Inventory Level Column. */
    @FXML
    private TableColumn<Part, Integer> MPassociatedPartInvLevel;

    /** Associated Part Table Part Price Column. */
    @FXML
    private TableColumn<Part, Double> MPassociatedPartPrice;

    // SEARCH BAR
    
    /** Part Search Bar Text Field. */
    @FXML
    private TextField modifyPartSearchBar;
    
    @FXML
    void MPSearchBar(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchedPart = modifyPartSearchBar.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchedPart) ||
                    part.getName().contains(searchedPart)) {
                foundPart.add(part);
            }
        }

        modifyProductPartDataTbl.setItems(foundPart);

        if (foundPart.isEmpty()) {
            displayAlert(4);
        }
    }

    // ASSOCIATED PARTS OBSERVABLE LIST
    
    /** Associated Parts Observable List. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // BUTTONS
    
    /** Add Button. 
     *
     * User selects a part and clicks Add button then the part is added to associated parts table below.
     *
     * @param actionEvent Add button 
     */
    @FXML
    void onActionMPAdd(ActionEvent event) {
        // Get the selected part
        Part selectedPart = modifyProductPartDataTbl.getSelectionModel().getSelectedItem();
       if (selectedPart == null) {
            displayAlert(3);
        } else {
            // Add the selected part to associated parts 
            associatedParts.add(selectedPart);
            modifyProductAssociatedPartTbl.setItems(associatedParts);
        }
    }

    /** Cancel (Back to Main Menu) Button. 
     * 
     * @param event Back to main menu button
     * @throws java.io.IOException 
     */
    @FXML
    public void onActionMainMenu(ActionEvent event) throws IOException {
    
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();  
    }

    /** Remove Associated Parts Button. 
     *
     * @param actionEvent Remove Associated Part button
     */
    @FXML
    void onActionRemoveMPAssociatedPart(ActionEvent event) {
        // Get the selected part
        Part selectedPart = modifyProductAssociatedPartTbl.getSelectionModel().getSelectedItem();
        // Alert informing user to select a part
        if (selectedPart == null) {
            displayAlert(3);
        } else {
            // Confirmation alert asking user if they're sure they want to remove the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("PLEASE CONFIRM YOUR CHOICE");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> clickOk = alert.showAndWait();
            // If the user clicks "OK", the part is removed 
            if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                modifyProductAssociatedPartTbl.setItems(associatedParts);
            }
        }
    }

    /** Save Button.
     *
     * @param actionEvent Save button
     * 
     * RUNTIME ERROR: My Name field kept changing to the ID number whenever it would save (for example, "Ducati" would turn to "3").
     * Fix: Realized I accidentally wrote String name = Double.parseDouble((modifyProduct.getText()); and needed to put modifyProductNameTxt instead of modifyProduct.
     */
    @FXML
    void onActionSaveMP(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = modifyProductNameTxt.getText();
            int stock = Integer.parseInt(modifyProductInvTxt.getText());
            double price = Double.parseDouble(modifyProductPriceTxt.getText());   
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());
                // Check to make sure min and inv values are valid
                if (minValue(min, max) && invValue(min, max, stock)) {
                    // If not, error handling occurs to alert the user to make appropriate changes --see below--
                    // If they are valid, update the product with the new modifications
                    Product updateProduct = new Product(id, name, price, stock, min, max);
                    for (Part part : associatedParts) {
                        updateProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(updateProduct);
                    Inventory.deleteProduct(selectedProduct);
                    onActionMainMenu(event); // return user to main menu
                }
        } catch (Exception ex) {
            // Alert user of empty fields or fields with invalid values
            displayAlert(0);
        }
    }
    
   // PRODUCT MODIFICATION ERROR HANDLING
    
    /** Alert user the Inv must be a number equal to or between Min and Max values.
     *
     * @param min Min.
     * @param max Max.
     * @param stock Inv.
     * @return Boolean True if inv value is valid.
     */
    private boolean invValue(int min, int max, int stock) {

        boolean isValid = true;
        // If Inv is less than min or greater than max, alert user the value is invalid
        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(1);
        }
        return isValid;
    }
   
    /** Alert user the min value must be greater than 0 and less than max. 
     *
     * @param min Min.
     * @param max Max.
     * @return Boolean True if min value is valid.
     */
    private boolean minValue(int min, int max) {

        boolean isValid = true;
        // If Min is less than or equal to 0 or greater or equal to Max, alert user the value is invalid
        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(2);
        }
        return isValid;
    }
    
    // REFERENCE VARIABLES
    
    /**
     * Created Selected Product variable.
     */
    Product selectedProduct;
    
    /** Stage variable of type Stage. 
     * Sets the stage for user.
     */
    Stage stage; 
    
    /** Scene variable of type Parent. 
     * Sets the scene for user. 
     */
    Parent scene;    
    
    /**
     * Initializes the controller class.
     * @param url The initialized url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedProduct = MainMenuController.getSelectedProduct();
        associatedParts = selectedProduct.getAllAssociatedParts();
        
        // Let the columns know where they'll be getting their data from.
        MPpartDataPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        MPpartDataPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MPpartDataInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MPpartDataPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPartDataTbl.setItems(Inventory.getAllParts());

        MPassociatePartPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        MPassociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MPassociatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MPassociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAssociatedPartTbl.setItems(associatedParts);
        
        modifyProductIdTxt.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameTxt.setText(selectedProduct.getName());
        modifyProductInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        
    }  
    
    // ALERT MESSAGES
    
    /** Alert messages for various errors. 
     *
     *  0 - Empty fields or fields with invalid values
     *  1 - Invalid value for Inv
     *  2 - Invalid value for Min
     *  3 - Part not selected
     *  4 - Part not found
     *  
     */
    private void displayAlert(int alertType){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        switch (alertType) {
            case 0: 
                alert.setTitle("UNABLE TO MODIFY PRODUCT");
                alert.setHeaderText("Form contains empty fields or invalid values, please fix");
                alert.showAndWait();
                break;
            case 1: 
                alert.setTitle("UNABLE TO MODIFY PRODUCT");
                alert.setHeaderText("Invalid value for Inv");
                alert.setContentText("Inv must be a number equal to or between Min and Max values");
                alert.showAndWait();
                break;
            case 2: 
                alert.setTitle("UNABLE TO MODIFY PRODUCT");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min should be greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 3: 
                alert.setTitle("PLEASE SELECT PART");
                alert.setHeaderText("Select the part you'd like to remove");
                alert.showAndWait();
                break;
            case 4: 
                alert.setTitle("PART NOT FOUND");
                alert.setHeaderText("There are no parts with that name or ID number");
                alert.showAndWait();
                break;
         
         
        }
    } 
}
