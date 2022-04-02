public class Arrival extends Event {

    public Arrival(double time, Customer customer) {
       super(time, customer);
    }

    public String toString() {
        return getArrivalTime() + ": Arrival " + getCustomer();
    }
}