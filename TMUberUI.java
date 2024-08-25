import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // The main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        try{
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license);
        } 
        catch(Exception e){
          System.out.println(e.getMessage());
        }
          
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        try{
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }  
        catch(Exception e){
          System.out.println(e.getMessage());
        }
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // To get User Account
        String accID = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          accID = scanner.nextLine();
        }

        // To get 'From Address'
        String fromAddress = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddress = scanner.nextLine();
        }

        // To get 'To Address'
        String toAddress = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddress = scanner.nextLine();
        }
        
        try{
          tmuber.requestRide(accID, fromAddress, toAddress);
          System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s", tmuber.getUser(accID).getName(), fromAddress, toAddress);
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }

      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        String accID = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          accID = scanner.nextLine();
        }
        String fromAddress = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddress = scanner.nextLine();
        }
        String toAddress = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddress = scanner.nextLine();
        }
        String restaurant = "";
        System.out.print("Restaurant: ");
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();
        }
        String orderNo = "";
        System.out.print("Food Order #: ");
        if (scanner.hasNextLine())
        {
          orderNo = scanner.nextLine();
        }
        try{
          tmuber.requestDelivery(accID, fromAddress, toAddress, restaurant, orderNo);
          System.out.printf("\nDELIVERY for: %-15s From: %-15s To: %-15s", tmuber.getUser(accID).getName(), fromAddress, toAddress);
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
        
       
       
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }

      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int request = -1;
        int zone = -1;
        System.out.print("Zone #: ");
        if (scanner.hasNextInt())
        {
          zone = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        try{
          tmuber.cancelServiceRequest(zone, request);
          System.out.println("Service request #" + request + " cancelled");
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
          
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverId = "";
        System.out.print("Driver ID #: ");
        if (scanner.hasNextInt())
        {
          driverId = scanner.nextLine();
        }
        try{
          tmuber.dropOff(driverId);
          System.out.printf("Driver %s Dropping Off", driverId);
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      // Unit Test of CityMap Zone Method
      else if (action.equalsIgnoreCase("ZONE")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.println(CityMap.getCityZone(address));
      }
      else if (action.equalsIgnoreCase("LOADUSERS")) 
      {
        String fileName = "";
        System.out.print("User File: ");
        if (scanner.hasNextLine())
        {
          fileName = scanner.nextLine();
        }
        try {
          tmuber.setUsers(TMUberRegistered.loadPreregisteredUsers(fileName));
          System.out.println("Users Loaded");
        } catch (FileNotFoundException e) {
          System.out.println("Users File " + fileName + " Not Found");
        } catch (IOException e) {
          return;
        }
        
      }
      // Loads drivers from a text file
      else if (action.equalsIgnoreCase("LOADDRIVERS")) 
      {
        String fileName = "";
        System.out.print("Driver File: ");
        if (scanner.hasNextLine())
        {
          fileName = scanner.nextLine();
        }
        try {
          tmuber.setDrivers(TMUberRegistered.loadPreregisteredDrivers(fileName));
          System.out.println("Drivers Loaded");
        } catch (FileNotFoundException e) {
          System.out.println("Drivers File " + fileName + " Not Found");
        } catch (IOException e) {
          return;
        }
        
      }
      // Gets a driver to pick up a request
      else if (action.equalsIgnoreCase("PICKUP")) 
      {
        String driverId = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextLine())
        {
          driverId = scanner.nextLine();
        }
        try {
          // Get the driver object using driverId
          Driver driver = tmuber.getDriverById(driverId);

          tmuber.pickup(driverId);                                      
          System.out.printf("\nDriver %s Picking up in Zone %s\n", driverId, CityMap.getCityZone(driver.getAddress()));
        }
        catch(Exception e) {
          System.out.println(e.getMessage());
        }
        
      }

      // To tell a driver to drive to a certain address; changing zone
      else if (action.equalsIgnoreCase("DRIVETO")) 
      {
        String driverId = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextInt())
        {
          driverId = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextInt())
        {
          address = scanner.nextLine();
        }
        try{
          tmuber.driveTo(driverId, address);
          System.out.printf("Driver %s Now in Zone %d", driverId, tmuber.getDriverById(driverId).getZone());
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
      }
      
      System.out.print("\n>");
    }
  }
}

