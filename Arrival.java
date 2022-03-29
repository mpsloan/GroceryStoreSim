import java.util.PriorityQueue;

public class Arrival extends PriorityQueue<Customer> {

    public Arrival(double simClock, Customer customer) {
        simClock = customer.getArrivalTime();
        this.poll();
    }
}