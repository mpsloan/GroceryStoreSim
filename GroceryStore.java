import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class GroceryStore {
    
    public static PriorityQueue<Customer> customers(String filename) throws FileNotFoundException {
        File file = new File(filename);
        PriorityQueue<Customer> customers = new PriorityQueue<>();
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            double arrivalTime = scan.nextDouble();
            int items = scan.nextInt();
            double pickupTime = scan.nextDouble();
            Customer customer = new Customer(arrivalTime, items, pickupTime);
            customers.add(customer);
        }
        return customers;
    }
    public static void main(String[] args) throws FileNotFoundException {
        PriorityQueue<Customer> q = customers("GroceryStoreData/arrival simple.txt");
        while (!q.isEmpty()) {
            System.out.println(q.poll());
        }
        
    }
}