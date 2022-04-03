// event sub-class that represents the end of checkout event
public class EndCheckout extends Event {
    // lane field
    private CheckoutLane lane;
    // constructor that set's the super fields and the lane
    public EndCheckout(double time, Customer customer, CheckoutLane lane) {
        super(time, customer);
        this.lane = lane;
    }
    // toString that returns the time of the checkout, customer and his lane, and the wait time
    public String toString() {
        return String.format("%,.2f", getTime())+ ": Finished Checkout " +getCustomer()+ " on " +lane+ " (" +String.format("%,.2f", getCustomer().getWaitTime())+ " minute wait)";
    }
}