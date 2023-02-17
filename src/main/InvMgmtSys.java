package invmgmtsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/* 
 * 
 * @author hannahbergman
 * 
 */

/** 
 * Creation of the InvMgmtSys class extending from the Application.
 */
public class InvMgmtSys extends Application{

/** 
 * RUNTIME ERROR - the launcher needed to run the JavaFX classes but didn't know where those classes were. 
 *
 * Fixed by telling the launcher where to get those classes + added those components to the Java VM.
 */
 
/*
 * FUTURE ENHANCEMENT - Update the UI to make it more user-friendly.
 * 
    Remove the 2 search bars on the Main Menu and replace with one that can search through both tables.
 * Adjust the text (size, font, and contrast) for improved readability. 
 * 
 */  
   
    /** This is the main method. 
     * 
     * This is the entry point, the first method that gets called when you run the java program. 
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        
        /** Sample products to show in products table in the Main Menu. */
        Product harleyBike = new Product(1, "Harley-Davidson", 15999.99, 15, 3, 35);
        Product kawasakiBike = new Product(2, "Kawasaki", 14560.00, 15, 1, 20);
        Product ducatiBike = new Product(3, "Ducati", 12050.90, 15, 1, 20);

        Inventory.addProduct(harleyBike);
        Inventory.addProduct(kawasakiBike);
        Inventory.addProduct(ducatiBike);

        /** Sample parts to show in parts table in the Main Menu. */
        InHouse airFilter = new InHouse(1, "Air Filter", 22.99, 35, 1, 50, 1);
        InHouse tankPad = new InHouse(2, "Tank Pad", 54.99, 5, 1, 20, 1);
        Outsourced mirror = new Outsourced(3, "Mirror", 74.99, 10, 1, 10, "Revzilla");

        Inventory.addPart(airFilter);
        Inventory.addPart(tankPad);
        Inventory.addPart(mirror);
   
        /** Start up JavaFX application with launch. */
        launch(args);  
   
     
    }   
        
    /** Display the Inv Sys Mgmt Menu. */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inv Mgmt Sys Menu");
        stage.show();
        
    }
    
}
