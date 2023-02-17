package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hannahbergman
 */

/**
* Creation of the Inventory class. 
*/
public class Inventory {
    /** Unique Part ID. 
     * Create Unique Part IDs starting at 4 because program initializes with 3 part samples
     */
    private static int uniquePartId = 3;

    /** Unique Product ID. 
     * Create Unique Product IDs starting at 4 because program initializes with 3 product samples
     */
    private static int uniqueProductId = 3;
    
    /** Set up Observable List of type Part, allParts.
     * Create an allParts OL based off UML
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Set up Observable List of type Product, allProducts. 
     * Create an allProducts OL based off UML
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /** Get unique Part ID.
     *
     * Incrementally increases the unique part id by 1
     *
     * @return Unique part ID 
     */
    public static int getUniquePartId() {
        return uniquePartId += 1;
    }

    /** Get unique Product ID.
     *
     * Incrementally increases the unique Product ID by 1
     *
     * @return Unique product ID 
     */
    public static int getUniqueProductId() {
        return uniqueProductId += 1;
    }

    /** Add Part to the allParts Observable List.
     *
     * @param newPart New part 
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /** Add Product to the allProducts Observable List.
     *
     * @param newProduct New product 
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /** Look up parts with Part ID.
     *
     * @param partId Part ID
     * @return Looked up part 
     */
    public static Part lookupPart(int partId) {
        // If the part isn't found, sets to empty (null)
        Part partFound = null; 
        for(Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        // Returns part, if found
        return partFound; 
    }

    /** Look up products with Product ID.
     *
     * @param productId Product ID
     * @return Looked up product 
     */
    public static Product lookupProduct(int productId) {
        // If the product isn't found, sets to empty (null)
        Product productFound = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
                
            }
        }
        // Returns product, if found
        return productFound; 
    }

    /** Look up part with Part Name.
     *
     * @param partName Part name
     * @return Searched part 
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> searchedPart = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().toUpperCase().contains(partName.toUpperCase())) {
                searchedPart.add(part);
            }
        }
        if (searchedPart.isEmpty()) {
            // Returns allParts if searched part isn't there
            return allParts; 
        }
        // Returns searchedPart
        return searchedPart;  
    }

    /** Look up product with Product Name in the Observable List.
     *
     * @param productName Product name
     * @return Searched product 
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> searchedProduct = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().toUpperCase().contains(productName.toUpperCase())) {
                searchedProduct.add(product);
            }
        }
        if (searchedProduct.isEmpty()) {
            // Returns allProducts if searched product isn't there
            return allProducts; 
        }
        // Returns searchedProduct
        return searchedProduct; 
    }

    /** Update the part based on index and selected part.
     *
     * @param index Index - allParts observable list
     * @param selectedPart Selected part 
     */
    public static void updatePart(int index, Part selectedPart) {
        // Set the selectedPart to the index
        allParts.set(index, selectedPart);
    }

    /** Update the product based on index and selected product.
     *
     * @param index Index - allProducts observable list
     * @param newProduct New product 
     */
    public static void updateProduct(int index, Product newProduct) {
        // Set the newProduct to the index
        allProducts.set(index, newProduct);
    }

    /** Delete the selected part.
     *
     * @param selectedPart Selected part
     * @return True 
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
        } else {
            return false;
        }
        return true;
    }

    /** Delete the selected product.
     *
     * @param selectedProduct Selected product
     * @return True 
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
        } else {
            return false;
        }
        return true;
    }

    /** Get all the parts from allParts Observable List, Part type.
     *
     * @return Observable list - allParts 
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Get all the products from allProducts Observable List, Product type.
     *
     * @return Observable list - allProducts 
     */
    public static ObservableList<Product> getAllProducts() { 
        return allProducts; 
    }
}
