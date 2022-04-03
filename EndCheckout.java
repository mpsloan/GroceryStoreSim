public class EndCheckout extends Event {

    private CheckoutLane lane;

    public EndCheckout(double time, Customer customer, CheckoutLane lane) {
        super(time, customer);
        this.lane = lane;
    }

    public String toString() {
        return String.format("%,.2f", getTime())+ ": Finished Checkout " +getCustomer()+ " on " +lane+ " (" +String.format("%,.2f", getCustomer().getWaitTime())+ " minute wait)";
    }
}