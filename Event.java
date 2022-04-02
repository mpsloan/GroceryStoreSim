public class Event implements Comparable<Event> {
    
    private Customer customer;
    private double time;

    public Event(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    public double endShoppingTime() {
        return customer.checkoutReadyTime();
    }

    public double endCheckoutTime(CheckoutLane lane) {
        return lane.checkoutTime(getCustomer());
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getArrivalTime() {
        return customer.getArrivalTime();
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        }
        else if (this.time > other.time) {
            return 1;
        }
        else {
            return 0;
        }
    }
}