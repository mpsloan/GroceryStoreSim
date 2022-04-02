import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public static void main(String[] args) throws IOException {
        double simClock = 0;
        double averageWaitTime = 0;
        PriorityQueue<Event> events = new PriorityQueue<>();
        File f = new File("GroceryStoreData/test_solution.txt");
        FileWriter writer = new FileWriter(f);
        ArrayList<Customer> customers = customers("GroceryStoreData/arrival simple.txt");
        Collections.sort(customers);
        ArrayList<CheckoutLane> lanes = new ArrayList<>();
        for (Customer c: customers) {
            Arrival arrival = new Arrival(c.getArrivalTime(), c);
            events.add(arrival);
        }
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                CheckoutLane checkLane = new CheckoutLane(i);
                lanes.add(checkLane);
            }
            else {
                ExpressLane eLane = new ExpressLane(i);
                lanes.add(eLane);
            }

        }

        while (!events.isEmpty()) {
            Event event = events.poll();
            if (event instanceof Arrival) {
                simClock = event.getArrivalTime();
                writer.write(event + "\n");
                EndShopping eShopping = new EndShopping(event.endShoppingTime()+ customers.get(0).getArrivalTime(), customers.get(0));
                events.offer(eShopping);
            }
    
            else if(event instanceof EndShopping) {
                simClock = event.endShoppingTime() + customers.get(0).getArrivalTime();
                writer.write(event + "\n");
                if (lanes.get(0) instanceof ExpressLane && customers.get(0).expressEligible()) {
                    writer.write("12 or fewer, chose " +lanes.get(0) + "\n");
                    lanes.get(0).offer(customers.get(0));
                }
                else {
                    writer.write("More than 12, chose " +lanes.get(0) + "\n");
                    lanes.get(0).offer(customers.get(0));
                }
                EndCheckout eCheckout = new EndCheckout(event.endCheckoutTime(lanes.get(0)), customers.get(0), lanes.get(0));
                events.offer(eCheckout);
            }
            else {
                simClock = event.endCheckoutTime(lanes.get(0));
                writer.write(event + "\n");
                lanes.remove(0);
                customers.remove(0);
            }
        }
        writer.close();
    }
}