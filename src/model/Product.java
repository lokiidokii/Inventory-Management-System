package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hannahbergman
 */

/**
 * Creation of the Product class.
 * CLASS CREATION
 */
public class Product {
    /** Declare constructor.
     *
     * @param id Product ID.
     * @param name Product Name.
     * @param stock Product Inv (Stock).
     * @param price Product Price.
     * @param min Product Min
     * @param max Product Max. 
     * 
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // VARIABLES
    
    /** Observable list of type Parts called associatedParts. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Product ID. */
    int id;

    /** Product Name. */
    String name;

    /** Product Price. */
    double price;

    /** Product Inv (Stock). */
    int stock;

    /** Product Min. */
    int min;

    /** Product Max. */
    int max;

    // GETTERS + SETTERS
    
    /** Get product ID.
     *
     * @return Product ID
     */
    public int getId() {
        return id;
    }
    
    /** Set Product ID.
     *
     * @param id Product ID 
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Get product Name.
     *
     * @return Product Name
     */
    public String getName() {
        return name;
    }
    
    /** Set Product Name.
     *
     * @param name Product Name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get product Price.
     *
     * @return Product Price
     */
    public double getPrice() {
        return price;
    }
    
    /** Set Product Price.
     *
     * @param price Product Price 
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Get product Inv (Stock).
     *
     * @return Product Inv (Stock)
     */
    public int getStock() {
        return stock;
    }
    
    /** Set Product Inv (Stock).
     *
     * @param stock Product Inv (Stock)
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Get product Min.
     *
     * @return Product Min
     */
    public int getMin() {
        return min;
    }
    
    /** Set Product Min.
     *
     * @param min Product Min 
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Get product Max.
     *
     * @return Product Max
     */
    public int getMax() {
        return max;
    }
    
    /** Set Product Max.
     *
     * @param max Product Max 
     */
    public void setMax(int max) {
        this.max = max;
    }

    // OTHER METHODS
    
    /** Adds parts to the associated parts list.
     *
     * @param part Part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Delete selected part from the associated parts list.
     *
     * @param selectedAssociatedPart Selected associated part to delete
     * @return status of part deletion (true/false)
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /** List of associated parts.
     *
     * @return list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
