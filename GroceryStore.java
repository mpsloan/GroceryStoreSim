import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
// GroceryStore class that contains the main method along with a few helper methods
public class GroceryStore {
    // method that returns an ArrayList of customers by reading the .txt file that was provided
    public static ArrayList<Customer> customers(String filename) throws FileNotFoundException {
        // creating the file
        File file = new File(filename);
        // ArrayList to hold customers
        ArrayList<Customer> customers = new ArrayList<>();
        // scanner to read the file
        Scanner scan = new Scanner(file);
        // unique ID number for each of the customers
        int id = 0;
        // while loop to cycle until there are no more lines in .txt file
        while (scan.hasNextLine()) {
            // customer fields
            double arrivalTime = scan.nextDouble();
            int items = scan.nextInt();
            double pickupTime = scan.nextDouble();
            // creating the customer and adding them to the ArrayList
            Customer customer = new Customer(arrivalTime, items, pickupTime, id);
            customers.add(customer);
            // incrementing the ID each time to make it unique for each customer
            id ++;
        }
        scan.close();
        return customers;
    }
    // method that returns the lane with the shortest wait time
    public static CheckoutLane pickALane(Customer customer, ArrayList<CheckoutLane> lanes) {
        // instantiating a lane and
        CheckoutLane lane = null;
        // 2 separate ArrayList to hold regular and express lanes
        ArrayList<CheckoutLane> regular = new ArrayList<>();
        ArrayList<CheckoutLane> express = new ArrayList<>();
        // for each loop to cycle through all the lanes which were passed in
        for (CheckoutLane currLane : lanes) {
            // seeing if the current lane if express or not and adding to ArrayList
            if (currLane instanceof ExpressLane) {
                express.add(currLane);
            }
            else {
                regular.add(currLane);
            }
        }
        /* checking if the customer is expressEligible and if he is
        sort the express lane ArrayList and set lane = smallest one 
        if not sort regular lanes and get the smallest lane size */
        if (customer.expressEligible()) {
            Collections.sort(express);
            lane = express.get(0);
        } 
        else {
            Collections.sort(regular);
            lane = regular.get(0);
        }
        return lane;
    }
    // main method which runs the simulation
    public static void main(String[] args) throws IOException {
        // instantiating variables to use throughout simulation
        double simClock = 0;
        double tempWait = 0;
        double averageWaitTime = 0;
        // setting an events queue to poll and organize each time
        PriorityQueue<Event> events = new PriorityQueue<>();
        // second event queue to print at the end, same as first except don't poll from it
        PriorityQueue<Event> events2 = new PriorityQueue<>();
        // setting the file to print solution to
        File f = new File("GroceryStoreData/my_solution.txt");
        // creating a file writer to print the data
        FileWriter writer = new FileWriter(f);
        // setting the customers ArrayList from first method of this class and instantiating lanes ArrayList
        ArrayList<Customer> customers = customers("GroceryStoreData/arrival medium.txt");
        ArrayList<CheckoutLane> lanes = new ArrayList<>();
        // scheduling an arrival for each customer
        for (Customer c: customers) {
            // creating arrival and adding it to the queues
            Arrival arrival = new Arrival(c.getArrivalTime(), c);
            events.offer(arrival);
            events2.offer(arrival);
        }
        // for loop that by default creates 4 regular lanes and 2 express
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
        // while loop that continues until event queue is empty
        while (!events.isEmpty()) {
            // polling the first event to order queue, and reference event
            Event event = events.poll();
            // sorting the lanes
            Collections.sort(lanes);
            // checking if the current event is an Arrival
            if (event instanceof Arrival) {
                // setting the simClock to the end of shopping time for the customer
                simClock = event.endShoppingTime();
                // creating the end of shopping event
                EndShopping eShopping = new EndShopping(simClock, event.getCustomer());
                // adding the new event to the queues
                events.offer(eShopping);
                events2.offer(eShopping);
            }
            // checking if the event is an EndShopping event
            else if(event instanceof EndShopping) {
                // setting the simClock to the end of shopping time for the customer
                simClock = event.endShoppingTime();
                // having the customer pick the best lane
                CheckoutLane l = pickALane(event.getCustomer(), lanes);
                // adding the customer to the lane
                l.add(event.getCustomer());
                // setting the process count of the lane
                l.setProcessCount(l.getProcessCount()+1);
                // setting the customers lane ID
                event.getCustomer().setLaneID(l.getLaneID());
                // setting the simClock to the endCheckout time of the lane plus the simClock
                simClock = event.endCheckoutTime(l) + simClock;
                // scheduling the EndCheckout event
                EndCheckout eCheckout = new EndCheckout(simClock, event.getCustomer(), l);
                // adding the events to the queues
                events.offer(eCheckout);
                events2.offer(eCheckout);                
            }
            // handling EndCheckout event
            else {
                // setting the temp wait to 0
                tempWait = 0.0;
                CheckoutLane currentLane = null;
                // for each cycling through lanes ArrayList
                for(CheckoutLane l : lanes){
                    // setting the customer's lane to currentLane
                    if(l.getLaneID() == event.getCustomer().getLaneID()){
                        currentLane = l;
                        break;
                    }
                }
                /* if the currentLane's size is one or if the current customer is first in line
                set their wait to 0 */
                if (currentLane.size() == 1 || currentLane.get(0) == event.getCustomer()) {
                    tempWait = 0.0;
                }
                // if there's more people in line, find the customer's position in line 
                else {
                    int positionInLine = 0;
                    // for each loop to cycle through every customer in the current lane
                    for (Customer customer : currentLane) {
                        // if the current customer is the this person, break
                        if (customer == event.getCustomer()) {
                            break;
                        }
                        // if not, increment their position in line
                        positionInLine++;
                    }                
                    //  find the person in front's checkout time by subtracting 1 from position in line
                    double prevCheckoutTime = currentLane.get(positionInLine-1).getWaitTime()+ currentLane.get(positionInLine-1).checkoutReadyTime() + currentLane.checkoutTime(currentLane.get(positionInLine-1));
                    // find current person's checkoutReady time
                    double readyCheckOutTime = event.getCustomer().checkoutReadyTime();
                    /*  getting time it takes from the previous person to check out - the time it takes current person to checkout
                    that's how much time the person waited */
                    tempWait = Math.abs(prevCheckoutTime - readyCheckOutTime);
                }
                // setting the wait time for the customer
                event.getCustomer().setWaitTime(tempWait);
                // updating simClock to the endCheckout time of current lane
                simClock = event.endCheckoutTime(currentLane);
                // setting time of the event
                event.setTime(simClock);
                // removing the customer from the lane once checkout is over
                currentLane.remove(event.getCustomer());
            }
        }
        // prints out events2 and a new line while it isn't empty
        while(!events2.isEmpty()) {
            writer.write(events2.poll() + "\n");
        }
        // for each loop to calculate the waitTime of each customer
        for (Customer c: customers) {
            averageWaitTime += c.getWaitTime();
        }
        // calculating the averageWaitTime by dividing it by number of customers in ArrayList
        averageWaitTime = averageWaitTime / customers.size();
        // printing the average wait time rounded to thousandths decimal places
        writer.write("Average wait time: " +String.format("%,.3f", averageWaitTime));
        writer.close();
    }    
}