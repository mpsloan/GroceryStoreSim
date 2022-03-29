import java.util.PriorityQueue;

public class EndShopping extends PriorityQueue<Customer> {
    
    public EndShopping(double simClock, Customer customer) {
        simClock = customer.getArrivalTime() + customer.checkoutReadyTime();
        this.poll();

    }
}