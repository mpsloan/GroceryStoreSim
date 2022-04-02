public class EndShopping extends Event {
    
    public EndShopping(double time, Customer customer) {
        super(time, customer);
    }

    public String toString() {
        return endShoppingTime()+ ": Finished Shopping " +getCustomer();
    }
}