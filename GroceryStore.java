import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class GroceryStore {
    
    public static ArrayList<Customer> customers(String filename) throws FileNotFoundException {
        File file = new File(filename);
        ArrayList<Customer> customers = new ArrayList<>();
        Scanner scan = new Scanner(file);
        int id = 0;
        while (scan.hasNextLine()) {
            double arrivalTime = scan.nextDouble();
            int items = scan.nextInt();
            double pickupTime = scan.nextDouble();
            Customer customer = new Customer(arrivalTime, items, pickupTime, id);
            customers.add(customer);
            id ++;
        }
        scan.close();
        return customers;
    }

    public static PriorityQueue<PriorityQueue<Customer>> events() {
        PriorityQueue<PriorityQueue<Customer>> events = new PriorityQueue<>();
        return events;
    }
    public static void main(String[] args) throws IOException {
        double simClock = 0;
        PriorityQueue<Event> events = new PriorityQueue<>();
        File f = new File("GroceryStoreData/test_solution.txt");
        FileWriter writer = new FileWriter(f);
        ArrayList<Customer> customers = customers("GroceryStoreData/arrival simple.txt");
        ArrayList<CheckoutLane> lanes = new ArrayList<>();
        for (Customer c: customers) {
            Arrival arrival = new Arrival(c.getArrivalTime(), c);
            events.add(arrival);
        }
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                CheckoutLane checkLane = new CheckoutLane();
                lanes.add(checkLane);
            }
            else {
                ExpressLane eLane = new ExpressLane();
                lanes.add(eLane);
            }

        }

        while (!events.isEmpty()) {
            if (events.poll() instanceof Arrival) {
                EndShopping eShopping = new EndShopping((customers.get(0).checkoutReadyTime()), customers.get(0));
                events.add(eShopping);
            }

            else if(events.poll() instanceof EndShopping) {
                if (lanes.get(0) instanceof ExpressLane && customers.get(0).expressEligible()) {
                    lanes.get(0).add(customers.remove(0));
                }
                else {
                    lanes.get(0).add(customers.remove(0));
                }
            }
            else {
                writer.write(customers + "");
            }

        }
        
        
        writer.close();
    }
}