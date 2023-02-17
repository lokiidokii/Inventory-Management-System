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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author hannahbergman
 */

/** Add Product Form Controller. */
public class AddProductFormController implements Initializable {
    
   // REFERENCE VARIABLES
    
    /** Stage variable of type Stage. 
     * Sets the stage for user.
     */
    Stage stage; 
    
    /** Scene variable of type Parent. 
     * Sets the scene for user. 
     */
    Parent scene;  

    // ADD PRODUCT 
    
    /** Add Product ID. */
    @FXML
    private TextField APIdTxtFld;
    
    /** Add Product Name. */
    @FXML
    private TextField APNameTxt;

    /** Add Product Inv. */
    @FXML
    private TextField APInvTxt;
    
    /** Add Product Price. */
    @FXML
    private TextField APPriceTxt;

    /** Add Product Max. */
    @FXML
    private TextField APMaxTxt;

    /** Add Product Min. */
    @FXML
    private TextField APMinTxt;
    
    // ADD PRODUCT DATA TABLE

     /** Search Bar. */
    @FXML
    private TextField APSearchBar;
    
    /** Add Product Table. */
    @FXML
    private TableView<Part> APDataTbl;

    /** Add Product Data Table - Inv. */
    @FXML
    private TableColumn<Part, Integer> APpartDataInvLevel;

    /** Add Product Data Table - Part ID. */
    @FXML
    private TableColumn<Part, Integer> APpartDataPartId;

    /** Add Product Data Table - Part Name. */
    @FXML
    private TableColumn<Part, String> APpartDataPartName;

    /** Add Product Data Table - Price. */
    @FXML
    private TableColumn<Part, Double> APpartDataPrice;
    
    // ASSOCIATED PART TABLE

    /** Associated Part Table - Inventory Level Column. */
    @FXML
    private TableColumn<Part, Integer> APAssociatedInvLevel;

    /** Associated Part Table - Part ID Column. */
    @FXML
    private TableColumn<Part, Integer> APAssociatedPartId;

    /** Associated Part Table - Part Name Column. */
    @FXML
    private TableColumn<Part, String> APAssociatedPartName;

    /** Associated Part Table - Price Column. */
    @FXML
    private TableColumn<Part, Double> APAssociatedPrice;

    /** Associated Part Table. */
    @FXML
    private TableView<Part> APAssociatedTbl;
    
    /** Observable list for associated parts. */
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
 
    // BUTTONS
    
    /** Add Button. 
     *
     * User selects a part and clicks Add button then the part is added to associated parts table below.
     *
     * @param actionEvent Add button 
     */
    @FXML
    void onActionAPAdd(ActionEvent event) {
        //select part
        Part selectedPart = APDataTbl.getSelectionModel().getSelectedItem();
         if (selectedPart == null) {
            displayAlert(4);
        } else {
            // Add the selected part to associated parts 
            associatedParts.add(selectedPart);
            APAssociatedTbl.setItems(associatedParts);
        }

    }

    /** Cancel (Back to Main Menu) Button. */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Remove Associated Part Button. */
    @FXML
    void onActionRemoveAPAssociatedPart(ActionEvent event) {
        //Select part
        Part selectedPart = APAssociatedTbl.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null){
            displayAlert(4); //alert if user doesn't make a selection
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("REMOVE ASSOCIATED PART");
            alert.setContentText("Are you sure you want to remove this associated part?"); //confirmation alert about removal of associated part
            Optional<ButtonType> selectOK = alert.showAndWait();
            if(selectOK.isPresent() && selectOK.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            APAssociatedTbl.setItems(associatedParts);
        }
        }
            
    }

    /** Save Button. */
    @FXML
    void onActionSaveAP(ActionEvent event) {
  try {
            int id = 4;
            String name = APNameTxt.getText();
            double price = Double.parseDouble(APPriceTxt.getText());
            int stock = Integer.parseInt(APInvTxt.getText());
            int min = Integer.parseInt(APMinTxt.getText());
            int max = Integer.parseInt(APMaxTxt.getText());
            boolean addedPart = false;
            // Check to make sure min and inv values are valid
            if (minVal(min, max) && invVal(min, max, stock)) {
                Product product = new Product(id, name, price, stock, min, max);
                for (Part part : associatedParts) {
                    product.addAssociatedPart(part);
                }
                product.setId(Inventory.getUniqueProductId());
                Inventory.addProduct(product);
                onActionMainMenu(event); 
            }
  } catch (Exception ex) {
      displayAlert(0);
  }          
   }
    
    /**
     * Search bar.
     */
    @FXML
    void APsearch(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchedPart = APSearchBar.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchedPart) ||
                    part.getName().contains(searchedPart)) {
                foundPart.add(part);
            }
        }

        APDataTbl.setItems(foundPart);

        if (foundPart.isEmpty()) {
            displayAlert(5);
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url The initialized url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Let the columns know where they'll be getting their data from.
        // Associated Parts table
        APpartDataPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        APpartDataPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        APpartDataInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        APpartDataPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        APDataTbl.setItems(Inventory.getAllParts());
        
        //POPULATES THE PRODUCTS TABLE
        APAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        APAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        APAssociatedInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        APAssociatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        
    }    
    
    // ERROR HANDLING
    
    /** Alert user the Inv must be a number equal to or between Min and Max values.
     *
     * @param min Min.
     * @param max Max.
     * @param stock Inv.
     * @return Boolean True if inv value is valid.
     */
    private boolean invVal(int min, int max, int stock) {

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
    private boolean minVal(int min, int max) {

        boolean isValid = true;
        // If Min is less than or equal to 0 or greater or equal to Max, alert user the value is invalid
        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(2);
        }
        return isValid;
    }
    
    // ALERT MESSAGES
    
    /** Alert messages for various errors. 
     *
     *  0 - Empty fields or fields with invalid values
     *  1 - Invalid value for Inv
     *  2 - Invalid value for Min
     *  3 - Machine ID can only be numbers
     *  4 - Empty selection
     *  5 - Part not found
     */
    private void displayAlert(int alertType){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        switch (alertType) {
            case 0: 
                alert.setTitle("UNABLE TO ADD PART");
                alert.setHeaderText("Form contains empty fields or invalid values, please fix");
                alert.showAndWait();
                break;
            case 1: 
                alert.setTitle("UNABLE TO ADD PART");
                alert.setHeaderText("Invalid value for Inv");
                alert.setContentText("Inv must be a number equal to or between Min and Max values");
                alert.showAndWait();
                break;
            case 2: 
                alert.setTitle("UNABLE TO ADD PART");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min should be greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 3: 
                alert.setTitle("INCORRECT VALUES");
                alert.setHeaderText("Machine ID can only contain numbers");
                alert.showAndWait();
                break;
            case 4: 
                alert.setTitle("PLEASE SELECT A PART");
                alert.setHeaderText("Part cannot be added or removed because nothing is selected");
                alert.showAndWait();
                break;
            case 5: 
                alert.setTitle("PART NOT FOUND");
                alert.setHeaderText("There are no parts with that name or ID number");
                alert.showAndWait();
                break;
         
        }
    } 
}
