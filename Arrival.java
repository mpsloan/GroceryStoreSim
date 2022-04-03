// sub-class for customer arrival that extends parent Event class
public class Arrival extends Event {
    // constructor that passes in same values as super
    public Arrival(double time, Customer customer) {
       super(time, customer);
    }
    /* toString that prints when customer arrival happens 
    gets the arrival time and customer from super-class */
    public String toString() {
        return getArrivalTime() + ": Arrival " + getCustomer();
    }
}