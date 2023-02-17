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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author hannahbergman
 */

/** Modify Part Form Controller. */
public class ModifyPartFormController implements Initializable {

    
    // MODIFY PART VARIABLES
    
    /** In-House Radio Button. */
    @FXML
    private RadioButton modifyPartInHouseRadBtn;
    
    /** Outsourced Radio Button. */
    @FXML
    private RadioButton modifyPartOutsourcedRadBtn;
    
    /** The changing Label - Machine ID/Company Name. */
    // This changes depending on whether In-House or Outsourced is selected
    @FXML
    private Label changingLabelModifyPart;
    
    /** Modify Part Form Changing Text Field - Machine ID/Company Name. */
    @FXML
    private TextField modifyPartChangingNameTxt;

    /** Modify Part ID. */
    @FXML
    private TextField modifyPartIdTxt;

    /** Modify Part Inv. */
    @FXML
    private TextField modifyPartInvTxt;

    /** Modify Part Max. */
    @FXML
    private TextField modifyPartMaxTxt;

    /** Modify Part Min. */
    @FXML
    private TextField modifyPartMinTxt;

    /** Modify Part Name. */
    @FXML
    private TextField modifyPartNameTxt;

    /** Modify Part Price. */
    @FXML
    private TextField modifyPartPriceTxt;
    
    /** In-House Radio Button. */
    @FXML
    void modPartInHouseBtn(ActionEvent event) {
        // Changes label to Machine ID when In-House is selected
        changingLabelModifyPart.setText("Machine ID");
    }
    /** Outsourced Radio Button. */
    @FXML
    void modPartOutsourcedBtn(ActionEvent event) {
        // Changes label to Company Name when Outsourced is selected
        changingLabelModifyPart.setText("Company Name");
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
    
    /** 
     * Create selected part variable.
     * 
     * This is important to bring over selected part from main menu to modify
     */
    private Part selectedPart;
    
    // BUTTONS

    /** Cancel (return to Main Menu) button. */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Modify Part Save Button. */
    @FXML
    void onActionModPartSaveBtn(ActionEvent event) {
        
        try {
            int id = selectedPart.getId();
            String name = modifyPartNameTxt.getText();
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int machineId;
            String companyName;
            boolean modifiedPart = false;

            if (minVal(min, max) && invVal(min, max, stock)) {

                if (modifyPartInHouseRadBtn.isSelected()) {
                    try {
                        machineId = Integer.parseInt(modifyPartChangingNameTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        modifiedPart = true;
                    } catch (Exception ex) {
                        displayAlert(3);
                    }
                }

                if (modifyPartOutsourcedRadBtn.isSelected()) {
                    companyName = modifyPartChangingNameTxt.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPart);
                    modifiedPart = true;
                }

                if (modifiedPart) {
                    Inventory.deletePart(selectedPart);
                    // Return to Main Menu when part is saved
                  stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                  scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                  stage.setScene(new Scene(scene));
                  stage.show();
                }
            }
        } catch(Exception ex) {
            displayAlert(0);
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url The initialized url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        selectedPart = MainMenuController.getSelectedPart();
        
        if (selectedPart instanceof InHouse) {
            modifyPartInHouseRadBtn.setSelected(true);
            changingLabelModifyPart.setText("Machine ID");
            modifyPartChangingNameTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced) {
            modifyPartOutsourcedRadBtn.setSelected(true);
            changingLabelModifyPart.setText("Company Name");
            modifyPartChangingNameTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }
        
         /**
         * Collect changes from the Modify Part form.
         */
        modifyPartIdTxt.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameTxt.setText(String.valueOf(selectedPart.getName()));
        modifyPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modifyPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
        modifyPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));

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
     */
    private void displayAlert(int alertType){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        switch (alertType) {
            case 0: 
                alert.setTitle("UNABLE TO MODIFY PART");
                alert.setHeaderText("Form contains empty fields or invalid values, please fix");
                alert.showAndWait();
                break;
            case 1: 
                alert.setTitle("UNABLE TO MODFIY PART");
                alert.setHeaderText("Invalid value for Inv");
                alert.setContentText("Inv must be a number equal to or between Min and Max values");
                alert.showAndWait();
                break;
            case 2: 
                alert.setTitle("UNABLE TO MODIFY PART");
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
