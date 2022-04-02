public class ExpressLane extends CheckoutLane {

    private double minPerItem = 0.10;
    private double processing = 1.0;

    public ExpressLane(int id) {
        super(id);
    }

    @Override
    public double checkoutTime(Customer customer) {
        return (customer.getItems() * minPerItem) + processing;
    }

    public String toString() {
        return "12 or fewer, chose Lane " +getLaneID()+ " (" +this.size()+ ")";
    }
  
}