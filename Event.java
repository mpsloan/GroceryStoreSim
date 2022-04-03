// event class that implements comparable
public class Event implements Comparable<Event> {
    // event class fields
    private Customer customer;
    private double time;
    // constructor that sets the event class fields
    public Event(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }
    // getting the time that the customer ends their shopping
    public double endShoppingTime() {
        return customer.checkoutReadyTime();
    }
    /* getting the time that the customer ends their checkout
    uses the customer's checkout ready time plus their wait and the checkoutTime of their lane */
    public double endCheckoutTime(CheckoutLane lane) {
        return this.customer.checkoutReadyTime() + this.customer.getWaitTime() + lane.checkoutTime(customer);
    }
    // getting the time of the event
    public double getTime() {
        return time;
    }
    // setting the time of the event
    public void setTime(double time){
        this.time = time;
    }
    // getting the customer associated with this event
    public Customer getCustomer() {
        return customer;
    }
    // getting the arrival time of this customer
    public double getArrivalTime() {
        return customer.getArrivalTime();
    }
    // comparing the time of the events to use in priority queue
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