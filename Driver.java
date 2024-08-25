/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;

  private TMUberService service;    // refers to a TMUberService object
  private String address;          // keeps track of the driver address
  private int zone;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";

    this.service = null;      // initialize the service to null
    this.address = address;        // initialize the address to an empty string
    this.zone = CityMap.getCityZone(address);           // intialize zone to -1 indicating no zone is assigned
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-15s Wallet: %2.2f \nStatus: %-15s Address: %-15s Zone: %-15s\n", 
                      id, name, carModel, licensePlate, wallet, status, address, zone);
          
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }

  // Getter and Setter methods
  public TMUberService getService()
  {
    return service;
  }
  public void setService(TMUberService service)
  {
    this.service = service;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public int getZone()
  {
    return zone;
  }
  public void setZone(int zone)
  {
    this.zone = zone;
  }



  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   */
  public boolean equals(Object other)
  {
    Driver d2 = (Driver) other;
    return this.name.equals(d2.name) && this.licensePlate.equals(d2.licensePlate);
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
