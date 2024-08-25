import java.util.*;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private LinkedHashMap<String, User> users;
  private ArrayList<Driver> drivers;
  private ArrayList<User> userList;

  private Queue<TMUberService>[] serviceRequests = new Queue[4];

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users = new LinkedHashMap<String, User>();
    drivers = new ArrayList<Driver>();
     // Initialize the array of queues for service requests in different city zones
     for (int i = 0; i < 4; i++) {
      serviceRequests[i] = new LinkedList<TMUberService>();
     }
    
    
    totalRevenue = 0;
  }
  
  // Builds the users map given a user arraylist
  public void setUsers(ArrayList<User> userArrList){
    for (User user : userArrList) {
      users.put(user.getAccountId(), user);
    }
  }
  // To set the drivers list
  public void setDrivers(ArrayList<Driver> drivers){
    this.drivers = drivers;
  }

  
  // To change the driver's address; change zone
  public void driveTo(String driverId, String address) throws DriverNotFoundException, InvalidAddressException, DriverNotAvailableException{
    
    Driver theDriver = getDriverById(driverId);

    if(theDriver == null){
      throw new DriverNotFoundException("Driver Not Found");
    }

    if (theDriver.getStatus().equals(Driver.Status.DRIVING)) {
      throw new DriverNotAvailableException("Driver is Currently not Available");
    
    }

    else if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }
    else{
      theDriver.setAddress(address);
      theDriver.setZone(CityMap.getCityZone(address));
    }
     
  }
  
  // To get the Driver object by driverId
  public Driver getDriverById(String driverId)
  {
    for (Driver driver : drivers) {
      if (driver.getId().equals(driverId)) {
        return driver;
      }
    }
    return null;
  }

  
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Going through each user's ID to find a match
    for (String userID : users.keySet()) {
      if(userID.equals(accountId)){
        return users.get(userID);
      }
    }
   

    return null;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    return users.containsValue(user);
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   
    return drivers.contains(driver);

 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    for (Queue<TMUberService> theQueue : serviceRequests) {
      if (theQueue.contains(req)) {
        return true;
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Going through each driver's status to find the first available one
    for (Driver driver : drivers) {
      switch (driver.getStatus()) {
        case AVAILABLE:
          return driver;
        case DRIVING:
          continue;
      }
    }
    return null;

  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    int index = 0;
    
    System.out.println();

    for (User user : users.values()) {
      index++;
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println();
    }

  }
  // A method to list all sorted users
  public void sortlistAllUsers()
  {
   
    System.out.println();

   for(int i = 0; i< userList.size(); i++){
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      userList.get(i).printInfo();
      System.out.println(); 
   }

  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {

    

    System.out.println();

    for(int i = 0; i < serviceRequests.length; i++){
      Queue<TMUberService> zoneQueue = serviceRequests[i];
      int requestNum = 1;
      
      System.out.println("ZONE " + i);
      System.out.println("======");
      System.out.println();

      for(TMUberService reqService : zoneQueue){
        System.out.println(requestNum + ". ----------------------------------------------------------------------");
        reqService.printInfo();
        System.out.println();
        System.out.println();
        requestNum++;
      }
      
    }
    
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) throws InvalidUserNameException, InvalidUserAddressException, InvalidWalletMoneyException, UserAlreadyExistsException
  {
    
    
    if(name.equals("") || name.equals(null)){
      throw new InvalidUserNameException("Invalid User Name");
    }
    
   
    if(!CityMap.validAddress(address)){
      throw new InvalidUserAddressException("Invalid User Address");
    }
    if(wallet < 0){
      throw new InvalidWalletMoneyException("Invalid Money in Wallet");
    }

    // To format the address
    address = address.substring(0,4) + address.substring(4, 6).toLowerCase() + " " +
    address.substring(7,8).toUpperCase() + address.substring(8).toLowerCase();

    User newRegUser = new User("" + userAccountId + users.size(), name, address, wallet);

    if(userExists(newRegUser)){
      throw new UserAlreadyExistsException("User Already Exists in System");
    }
    users.put(newRegUser.getAccountId(), newRegUser);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address) throws InvalidDriverNameException, InvalidCarModelException, InvalidLicencePlateException, InvalidAddressException, DriverAlreadyExistsException
  {

    // To validate the name parameter
    if(name.equals("") || name.equals(null)){
      throw new InvalidDriverNameException("Invalid Driver Name");
    }
    
    // To validate the carModel parameter
    if(carModel.equals("") || carModel.equals(null)){
      throw new InvalidCarModelException("Invalid Car Model");
    }

    // To validate the carLicencePlate parameter
    if(carLicencePlate.equals("") || carLicencePlate.equals(null)){
     throw new InvalidLicencePlateException("Invalid Car Licence Plate");
    }

    if(address.equals("") || address.equals(null) || !CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }

    // To create a new object of Driver type if parameters are valid
    Driver newRegDriver = new Driver("" + driverId + drivers.size(), name, carModel, carLicencePlate.toUpperCase(), address);
    
    // To make sure the driver is not an existing driver
    if(driverExists(newRegDriver)){
      throw new DriverAlreadyExistsException("Driver Already Exists in System");
    }

    // Adds newly registered driver to the list of drivers
    drivers.add(newRegDriver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to) throws UserAccountNotFoundException, InsufficientTravelDistanceException, InvalidAddressException, InsufficientFundsException, ExistingRideRequestException, DriverNotFoundException
  {
     
    // This var will be used to capture the user
    User theGuy = getUser(accountId);
    
    // If the var is still 'null' user was not found
    if(theGuy == null){
      throw new UserAccountNotFoundException("User Account Not Found");
    }
      

    // To validate the from and to addresses
    if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid Address");
    }


    // To get the distance between the from and to addresses
    int distance = CityMap.getDistance(from, to);

    // To make sure that distance is greater than 1 city block!
    if(distance <= 1){
      throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
    }
    
    // To make sure user has enough funds for the ride
    if(getRideCost(distance) > theGuy.getWallet()){
      throw new InsufficientFundsException("Insufficient Funds");
    }
    
    // To capture an available driver
    Driver availableDriver = getAvailableDriver();

    // If an available driver was found this is excuted
    if(availableDriver != null){
      // Creating a TMUberRide object
      TMUberRide newRide = new TMUberRide(availableDriver, from, to, theGuy, distance, 
      getDeliveryCost(distance));

      // Checking if user has already put a request
      if(existingRequest(newRide)){
        throw new ExistingRideRequestException("User Already Has Ride Request");
      }
      // If everything checks out, the ride request is created
      else{
        serviceRequests[CityMap.getCityZone(from)].add(newRide);// Add ride to serviceRequests list
        theGuy.addRide(); // Increments user's number of rides
      }

    }
    // This is control flow statement is excuted when no drivers are found
    else{
      throw new DriverNotFoundException("Driver Not Found");
    }

  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) throws UserAccountNotFoundException, InvalidAddressException, InsufficientTravelDistanceException, InsufficientFundsException, NoDriversAvailableException, ExistingDelRequestException
  {
    
    // This var will be used to capture the user
    User theGuy = getUser(accountId);
    
    // If the var is still 'null' user was not found
    if(theGuy == null){
     throw new UserAccountNotFoundException("User Account Not Found");
    }

    // To validate the from and to addresses
    if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid Address");
    }
    

    // To get the distance between the from and to addresses
    int distance = CityMap.getDistance(from, to);

    // To make sure that distance is greater than 1 city block!
    if(distance <= 1){
      throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
    }

    // To make sure user has enough funds for the delivery
    if(getDeliveryCost(distance) > theGuy.getWallet()){
      throw new InsufficientFundsException("Insufficient Funds");
    }

     // To capture an available driver
     Driver availableDriver = getAvailableDriver();

     // Execute if availableDriver found
     if(availableDriver != null){
      
       // Creating a TMUberRide object
       TMUberDelivery newDel = 
       new TMUberDelivery(availableDriver, from, to, theGuy, distance, getDeliveryCost(distance), restaurant, foodOrderId);

      for(int i = 0; i < serviceRequests.length; i++){
      // To look for duplicate delivery orders 
      for (TMUberService req : serviceRequests[i]) {
        // First check if the service is a delivery
        if(req.getServiceType().equals("DELIVERY")){
          TMUberDelivery reqDelivery = (TMUberDelivery) req;
          // Then, check if the delivery is the same with any other existing delivery
          if(reqDelivery.equals(newDel)){
            throw new ExistingDelRequestException("User Already Has Delivery Request at Restaurant with this Food Order");
          }
        }
      }
    }
      

      
      serviceRequests[CityMap.getCityZone(from)].add(newDel);// Add ride to serviceRequests list
      theGuy.addDelivery(); // Increments user's number of rides

    }
    // This is control flow statement is excuted when no drivers are found
    else{
      throw new NoDriversAvailableException("No Drivers Available");
    }

  }


  // Cancel an existing service request. 
  public void cancelServiceRequest(int zone, int request) throws NoRequestsException, InvalidRequestNumberException, InvalidZoneNumber
  {
    
  
    if(zone < 0 || zone > 3){
      throw new InvalidZoneNumber("Invalid Zone #");
    }
     // To acccount for an invalid request number
    if(request <= 0 || request  > serviceRequests[zone].size()){
      throw new InvalidRequestNumberException("Invalid Request #");
    }
    // To account for if there are no requests
    if(serviceRequests[zone].isEmpty()){
      throw new NoRequestsException("There are current no requests");
    }
    
    // Using an iterator to get the request and remove it
    Iterator <TMUberService> iter = serviceRequests[zone].iterator();
    int currentIndex = 1;
    while (iter.hasNext()) {
      TMUberService service = iter.next();
      if(request == currentIndex){
        iter.remove();
        if(service instanceof TMUberRide){
          ((TMUberRide) service).getUser().subRide();
        }
        if(service instanceof TMUberDelivery){
          ((TMUberDelivery) service).getUser().subDelivery();
        }
      }
      currentIndex++;
    }

  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId) throws DriverNotFoundException, DriverNotDrivingException
  {
    // To find the driver by their ID
    Driver theDriver = getDriverById(driverId);

    if (theDriver == null) {
      throw new DriverNotFoundException("Driver Not Found");
    }


    if(!theDriver.getStatus().equals(Driver.Status.DRIVING)){
      throw new DriverNotDrivingException("The driver is not currently driving.");
    }

    TMUberService service = theDriver.getService();
    

    // To get the distance of the ride/delivery
    int distance = service.getDistance();


    // To store the fee paid to driver
    double fee = 0.0;

    if(service.getServiceType().equals("RIDE")){
      
      // To add ride cost to revenue
      totalRevenue += getRideCost(distance);

      // To store the fee paid to driver for a ride
      fee = PAYRATE * getRideCost(distance);

      // To deduct cost of service from user
      service.getUser().payForService(getRideCost(distance));
    }
    else{
      
      // To add ride cost to revenue
      totalRevenue += getDeliveryCost(distance);

      // To store the fee paid to driver for a delivery
      fee = PAYRATE * getDeliveryCost(distance);

      // To deduct cost of service from user
      service.getUser().payForService(getDeliveryCost(distance));

    }

    // To pay driver/update wallet
    theDriver.pay(fee);

    // To deduct driver fee from total revenues
    totalRevenue -= fee;

    // To make driver available
    theDriver.setStatus(Driver.Status.AVAILABLE);

    // Set the reference to the TMUberService object inside the Driver object to null
    theDriver.setService(null);

    // Set the new driver address to the drop-off address and update the driver zone
    theDriver.setAddress(service.getTo());
    int zone = CityMap.getCityZone(service.getTo());
    theDriver.setZone(zone);
  }

  // A method to pickup a ride or a delivery
  public void pickup(String driverID) throws DriverNotFoundException, NoServiceRequestsException
  {

    // To find the driver by their ID
    Driver theDriver = getDriverById(driverID);

    if (theDriver == null) {
      throw new DriverNotFoundException("Driver Not Found");
    }


    // Get the driver's zone
    int zone = theDriver.getZone();

    // Check if there's a service request in the queue for the driver's zone
    if (serviceRequests[zone].isEmpty()) {
      throw new NoServiceRequestsException("No Service Request in Zone " + Integer.toString(theDriver.getZone()));
    }

    // Remove the service request from the front of the queue for this zone
    Queue<TMUberService> service = serviceRequests[zone];

    // Set the driver's new service variable to refer to this TMUberService object
    theDriver.setService(service.peek());

    // Set the driver status to DRIVING
    theDriver.setStatus(Driver.Status.DRIVING);

    // Set the driver address to the From address for this service request
    theDriver.setAddress(service.peek().getFrom());

    serviceRequests[zone].poll();
  }


 

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    userList = new ArrayList<>(users.values());
    Collections.sort(userList, new NameComparator());
    sortlistAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator <User>
  {
    public int compare(User a, User b){
      return a.getName().compareTo(b.getName());
    }
  }
  
  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    userList = new ArrayList<>(users.values());
    Collections.sort(userList, new UserWalletComparator());
    sortlistAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User a, User b){
      if(a.getWallet() < b.getWallet()){
        return -1;
      }
      else if (a.getWallet() > b.getWallet()){
        return 1;
      }
      else{
        return 0;
      }
    }
  }


}
// -----------------------------------
// Exception Classes
class DriverNotFoundException extends Exception{
  public DriverNotFoundException(String msg) {
    super(msg);
  }
}

class InvalidRequestNumberException extends Exception{
  public InvalidRequestNumberException (String msg) {
    super(msg);
  }
}

class NoRequestsException extends Exception{
  public NoRequestsException (String msg) {
    super(msg);
  }
}

class NoDriversAvailableException extends Exception{
  public NoDriversAvailableException (String msg) {
    super(msg);
  }
  
}

class ExistingDelRequestException extends Exception{
  public ExistingDelRequestException (String msg) {
    super(msg);
  }
}

class InsufficientFundsException extends Exception{
  public InsufficientFundsException (String msg) {
    super(msg);
  }
  
}

class InsufficientTravelDistanceException extends Exception{
  public InsufficientTravelDistanceException (String msg) {
    super(msg);
  }
 
}
class InvalidAddressException extends Exception{
  public InvalidAddressException (String msg) {
    super(msg);
  }
 
}
class ExistingRideRequestException extends Exception{
  public ExistingRideRequestException (String msg) {
    super(msg);
  }
}

class UserAccountNotFoundException extends Exception{
  public UserAccountNotFoundException (String msg) {
    super(msg);
  }
  
}

class DriverAlreadyExistsException extends Exception{
  public DriverAlreadyExistsException (String msg) {
    super(msg);
  }
  
}

class InvalidLicencePlateException extends Exception{
  public InvalidLicencePlateException (String msg) {
    super(msg);
  }
  
}

class InvalidCarModelException extends Exception{
  public InvalidCarModelException (String msg) {
    super(msg);
  }
  
}

class InvalidDriverNameException extends Exception{
  public InvalidDriverNameException (String msg) {
    super(msg);
  }
}

class UserAlreadyExistsException extends Exception{
  public UserAlreadyExistsException (String msg) {
    super(msg);
  }

}
class InvalidWalletMoneyException extends Exception{
  public InvalidWalletMoneyException (String msg) {
    super(msg);
  }
}
class InvalidUserAddressException extends Exception{
  public InvalidUserAddressException (String msg) {
    super(msg);
  }
  
}
class InvalidUserNameException extends Exception{
  public InvalidUserNameException (String msg) {
    super(msg);
  }
  
}

class DriverNotAvailableException extends Exception{
  public DriverNotAvailableException (String msg) {
    super(msg);
  }
  
}

class NoServiceRequestsException extends Exception{
  public NoServiceRequestsException(String msg){
    super(msg);
  }
}

class DriverNotDrivingException extends Exception{
  public DriverNotDrivingException (String msg) {
    super(msg);
  }
}
class InvalidZoneNumber extends Exception{
  public InvalidZoneNumber (String msg) {
    super(msg);
  }
}

