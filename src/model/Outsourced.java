package model;

/**
 *
 * @author hannahbergman
 */

/**
 * Creation of the Outsourced class inheriting from Part class.
 * CLASS CREATION FEATURING INHERITANCE
 */
public class Outsourced extends Part {
    /** Declare constructor, adding company name.
     *
     * @param id Outsourced ID
     * @param name Outsourced Name
     * @param price Outsourced Price
     * @param stock Outsourced Stock
     * @param min Outsourced Min
     * @param max Outsourced Max
     * @param companyName Outsourced Company Name
     *  
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // VARIABLE - company name
    /** Company Name. */
    private String companyName;

    // METHODS
    // GETTER + SETTER
    /** Get the Company Name.
     *
     * @return Company Name 
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Set the Company Name.
     *
     * @param companyName Company Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
