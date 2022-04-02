public class EndCheckout extends Event {

    private CheckoutLane lane;

    public EndCheckout(double time, Customer customer, CheckoutLane lane) {
        super(time, customer);
        this.lane = lane;
    }

    public String toString() {
        return endCheckoutTime(lane)+ ": Finished Checkout " +getCustomer()+ " on " +lane;
    }
}