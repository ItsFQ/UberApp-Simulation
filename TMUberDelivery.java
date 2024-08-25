/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is-a TMUberService with some extra functionality
 */
public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
  public TMUberDelivery(Driver driver, String from, String to, User user, int distance, double cost,
                        String restaurant, String order)
  {
    super(driver, from, to, user, distance, cost, TYPENAME);
    this.restaurant = restaurant;
    foodOrderId = order;
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    
    // Cast other to a TMUService reference 
    TMUberDelivery delService2 = (TMUberDelivery) other;

    // Check if two Delivery Requests are equal
    return super.equals(other) && delService2.getRestaurant().equals(restaurant) 
    && delService2.getFoodOrderId().equals(foodOrderId);

  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    // Using inheritance to first print info about a basic service request
    super.printInfo();
    
    // Priniting specific subclass info
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
  }
}
