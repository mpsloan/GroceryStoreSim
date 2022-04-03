// event sub-class that represents the end of a customer shopping
public class EndShopping extends Event {
    // constructor that calls the super-class fields
    public EndShopping(double time, Customer customer) {
        super(time, customer);
    }
    /* toString that prints the time and customer, but also handles whether that customer
    chooses an express lane or regular lane */
    public String toString() {
        String s = String.format("%,.2f", getTime())+ ": Finished Shopping " +getCustomer();
        if(getCustomer().getItems() <= 12) {
            s += "\n12 or fewer chose Lane " +getCustomer().getLaneID()+ " (" +getCustomer().getLaneSize()+ ")";
        }
        else {
            s += "\nMore than 12 items, chose Lane " +getCustomer().getLaneID()+ " (" +getCustomer().getLaneSize()+ ")";
        }
        return s;
    }
}