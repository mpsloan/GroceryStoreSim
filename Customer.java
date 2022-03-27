public class Customer implements Comparable<Customer> {
    
    private double arrivalTime;
    private int items;
    private double pickupTime;

    public Customer(double arrivalTime, int items, double pickupTime) {
        this.arrivalTime = arrivalTime;
        this.items = items;
        this.pickupTime = pickupTime;
    }

    public double checkoutReady() {
        return (items * pickupTime) + arrivalTime;
    }

    public boolean expressEligible() {
        if (items <= 12) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int compareTo(Customer other) {
        if (this.arrivalTime < other.arrivalTime) {
            return -1;
        }
        else if (this.arrivalTime > other.arrivalTime) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public String toString() {
        return "Customer arrived at " + arrivalTime + " is getting " + items + " items andtakes this long " + pickupTime;
    }

    
}