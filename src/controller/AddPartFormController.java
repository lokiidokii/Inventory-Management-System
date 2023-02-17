package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author hannahbergman
 */


/** Add Part Form Controller. */
public class AddPartFormController implements Initializable {
    
    // ADD PART VARIABLES

    /** Add Part Form - Part ID. */
    @FXML
    private TextField addPartIDTxt;

    /** Add Part Form - Inv. */
    @FXML
    private TextField addPartInvTxt;

    /** Add Part Form - Max. */
    @FXML
    private TextField addPartMaxTxt;

    /** Add Part Form - Min. */
    @FXML
    private TextField addPartMinTxt;

    /** Add Part Form - Part Name. */
    @FXML
    private TextField addPartNameTxt;

    /** Add Part Form - Price. */
    @FXML
    private TextField addPartPriceTxt;
    
    // CHANGING NAME RADIO BUTTONS
    
    /** The changing name Label - Machine ID/Company Name. */
    // This changes depending on whether In-House or Outsourced is selected
    @FXML
    private Label changingName;
    
    /** Add Part Form Changing Text Field - Machine ID/Company Name. */
    @FXML
    private TextField addPartChangingNameTxt;
   
    /** In-House Radio Button. */
    @FXML
    private RadioButton addPartInHouseRadBtn;
    
    /** Outsourced Radio Button. */
    @FXML
    private RadioButton addPartOutsourcedRadBtn;
    
    /** In-House Radio Button. */
    @FXML
    void APchangeNameToInHouse(ActionEvent event) {
        // Changes label to Machine ID when In-House is selected
        changingName.setText("Machine ID");
    }
    
    /** Outsourced Radio Button. */
    @FXML
    void APchangeNameToOutsourced(ActionEvent event) {
         // Changes label to Company Name when Outsourced is selected
        changingName.setText("Company Name");
    }
    
    // REFERENCE VARIABLES
    
    /** Stage variable of type Stage. 
     * Sets the stage for user.
     */
    Stage stage; 
    
    /** Scene variable of type Parent. 
     * Sets the scene for user. 
     */
    Parent scene;  
    
    // BUTTONS
    
    /** Cancel (return to Main Menu) button. 
     *
     * @throws java.io.IOException
     */
    @FXML
    public void onActionMainMenu(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();  
    }

    /** Save Button. */
    @FXML
    void onActionSavePart(ActionEvent event) {
       
        try {
            int id = 4;
            String name = addPartNameTxt.getText();
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int stock = Integer.parseInt(addPartInvTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int machineId;
            String companyName;
            boolean addedPart = false;
            // Check to make sure min and inv values are valid
            if (minVal(min, max) && invVal(min, max, stock)) {
            // If In-House Button is selected
            if (addPartInHouseRadBtn.isSelected()) {
                try {
                    machineId = Integer.parseInt(addPartChangingNameTxt.getText());
                    InHouse addedInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    addedInHousePart.setId(Inventory.getUniquePartId());
                    Inventory.addPart(addedInHousePart);
                    addedPart = true;
                } catch (Exception ex) {
                    // Alert user that the Machine ID can only contain numbers
                    displayAlert(3);
                }
            }
            if(addPartOutsourcedRadBtn.isSelected()) {
                companyName = addPartChangingNameTxt.getText();
                Outsourced addedOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                addedOutsourcedPart.setId(Inventory.getUniquePartId());
                Inventory.addPart(addedOutsourcedPart);
                addedPart = true;
            }

            }
            // If the part is added, return user to main 
            if (addedPart) {
                onActionMainMenu(event); // return user to main menu
            }
        } catch (Exception ex) {
            // Alert user if fields are empty or contain invalid values
            displayAlert(0);
        }
        
    }
    
    // ERROR HANDLING
    
    /** Alert user that the Inv must be a number equal to or between Min and Max values.
     *
     * @param min Min 
     * @param max Max 
     * @param stock Inv
     * @return Boolean true if inv value is valid
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
   
    /** Alert user that the min value must be greater than 0 and less than max. 
     *
     * @param min Min 
     * @param max Max 
     * @return Boolean true if min value is valid
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
    
    /**
     * Initializes the controller class.
     * @param url The initialized url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
    // ALERT MESSAGES
    
    /** Alert messages for various errors. 
     *
     *  0 - Empty fields or fields with invalid values
     *  1 - Invalid value for Inv
     *  2 - Invalid value for Min
     *  3 - Invalid value for MachineId
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
         
        }
    } 
}
