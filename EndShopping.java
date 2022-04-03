public class EndShopping extends Event {
    
    public EndShopping(double time, Customer customer) {
        super(time, customer);
    }

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