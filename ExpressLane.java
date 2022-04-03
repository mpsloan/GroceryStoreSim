// CheckoutLane sub-class that represents an Express lane for 12 items or less
public class ExpressLane extends CheckoutLane {
    // ExpressLane fields
    private double minPerItem = 0.10;
    private double processing = 1.0;
    // constructor that calls super class for ID 
    public ExpressLane(int id) {
        super(id);
    }
    /* overriding the previous checkoutTime method to account for different
    time per item and different processing time */
    @Override
    public double checkoutTime(Customer customer) {
        return (customer.getItems() * this.minPerItem) + this.processing;
    }
    /* toString that returns they had 12 or fewer items and returing the lane number 
    and number of people in it */
    public String toString() {
        return "12 or fewer, chose Lane " +getLaneID()+ " (" +this.size()+ ")";
    }
}