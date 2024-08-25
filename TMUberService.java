/*
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 */
abstract public class TMUberService 
{
  private Driver driver;   
  private String from;
  private String to;
  private User user;
  private String type;  // Currently Ride or Delivery but other services could be added      
  private int distance; // Units are City Blocks
  private double cost;  // Cost of the service
  
  public TMUberService(Driver driver, String from, String to, User user, int distance, double cost, String type)
  {
    this.driver = driver;
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
  }


  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY") 
  abstract public String getServiceType();

  // Getters and Setters
  public Driver getDriver()
  {
    return driver;
  }
  public void setDriver(Driver driver)
  {
    this.driver = driver;
  }
  public String getFrom()
  {
    return from;
  }
  public void setFrom(String from)
  {
    this.from = from;
  }
  public String getTo()
  {
    return to;
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public int getDistance()
  {
    return distance;
  }
  public void setDistance(int distance)
  {
    this.distance = distance;
  }
  public double getCost()
  {
    return cost;
  }
  public void setCost(double cost)
  {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  public int compareDistance(Object other){

    // Casts other from Object to TMUberService type
    TMUberService serviceReq2 = (TMUberService) other;

    // Compares the 2 distances of the service requests
    return Integer.compare(getDistance(), serviceReq2.getDistance());
  }


  
  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  public boolean equals(Object other)
  {
    // Casts other from Object to TMUberService type
    TMUberService serviceReq2 = (TMUberService) other;

    // Checks if 2 service requests are equal
    if(type.equals(serviceReq2.type) && user.equals(serviceReq2.user)){
      return true;
    }
    return false;
  }
  
  // Print Information 
  public void printInfo()
  {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
  }
}
