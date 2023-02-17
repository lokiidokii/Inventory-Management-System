package model;

/**
 *
 * @author hannahbergman
 */

/** 
 * Creation of the InHouse class inheriting from Part class.
 * CLASS CREATION FEATURING INHERITANCE
 */
public class InHouse extends Part {
    /** Declare constructor, adding machine id.
     *
     * @param id In-House ID
     * @param name In-House Name
     * @param price In-House Price
     * @param stock In-House Stock
     * @param min In-House Min
     * @param max In-House Max
     * @param machineId In-House Machine ID
     *  
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // VARIABLE - machine id
    /** In-House Machine ID. */
    private int machineId;

    // METHODS
    // GETTER + SETTER
    /** Get Machine ID.
     *
     * @return Machine ID 
     */
    public int getMachineId() {
        return machineId;
    }

    /** Set Machine ID.
     *
     * @param machineId Machine ID 
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
