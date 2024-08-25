import java.util.Arrays;
import java.util.Scanner;


// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    int[] block = {-1, -1};

    String[] parts = getParts(address);
    
    if (parts.length != 3)
      return false;
    
    boolean streetType = false; 
    
    // "street" or "avenue" check
    if (!parts[2].equalsIgnoreCase("street") && !parts[2].equalsIgnoreCase("avenue"))
      return false;
    // which is it?
    if (parts[2].equalsIgnoreCase("street"))
      streetType = true;

    // All digits and digit count == 2
    if (!allDigits(parts[0]) || parts[0].length() != 2)
      return false;

    // Get first digit of street number
    int num1 = Integer.parseInt(parts[0])/10;
    if (num1 == 0) return false;
   
    // Must be 'n'th or 1st or 2nd or 3rd
    String suffix = parts[1].substring(1);
    if (parts[1].length() != 3) 
      return false;
   
    if (!suffix.equals("th") && !parts[1].equals("1st") &&
        !parts[1].equals("2nd") && !parts[1].equals("3rd"))
      return false;

    String digitStr = parts[1].substring(0, 1);
    if (!allDigits(digitStr))
      return false;
    int num2 = Integer.parseInt(digitStr);
    if (num2 == 0)
      return false;
    if (streetType)
    {
      block[0] = num2;
      block[1] = num1;
    }
    else
    {
      block[0] = num1;
      block[1] = num2;
    }
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    // To store the avenue
    block[1] = address.charAt(3) - '0';

    if(address.substring(7).toLowerCase().equals("street")){
      // To store the avenue
      block[0] = address.charAt(0) - '0';

      //To store the street 
      block[1] = address.charAt(3) - '0';
    }
    else{
      //To store the avenue 
      block[0] = address.charAt(3) - '0';
      
      // To store the street
      block[1] = address.charAt(0) - '0';
    }

    // To return the coordinates
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  public static int getDistance(String from, String to)
  {
    
    // To account for an invalid 'from' or 'to' address
    if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      return -1;
    }

    // To get coordinates for the 'from' address
    int[] fromBlock = getCityBlock(from); 

    // To get coordinates for the 'to' address
    int[] toBlock = getCityBlock(to);

    // To return the distance
    return Math.abs((toBlock[0] - fromBlock[0])) + Math.abs((toBlock[1] - fromBlock[1]));

  }
  
  // Returns the zone given a valid address
  public static int getCityZone(String address){
    
    // To account for an invalid address
    if(!CityMap.validAddress(address)){
      return -1;
    }

    // To capture the address parts array
    String [] adrsParts = getParts(address);
    
    // Variable that will be used to store the avenue and street numbers
    int avenue;
    int street;

    // An inner class to find the zone given an avenue and street number
    class ZoneFinder {
      int zoneFinderLogic(int avenueNum, int streetNum){
        if(avenueNum >= 1 && avenueNum <= 5){
          if(streetNum >= 6 && streetNum <= 9){
            return 0;
          }
          return 3;
        }
        else{
          if(streetNum >= 6 && streetNum <= 9){
            return 1;
          }
          return 2;
        }
      }
    } 

    // To create an object of the Inner Class
    ZoneFinder zFinder = new ZoneFinder();

    // To check which format the address is given in
    if(adrsParts[2].toLowerCase().equals("avenue")){
      
      // To store the avenue and street numbers 
      avenue = adrsParts[1].charAt(0) - '0';
      street = adrsParts[0].charAt(0) - '0';

      // To return the appropriate zone 
      return zFinder.zoneFinderLogic(avenue, street);  
    }
    else{
      // To store the avenue and street numbers 
      avenue = adrsParts[0].charAt(0) - '0';
      street = adrsParts[1].charAt(0) - '0';

      // To return the appropriate zone 
      return zFinder.zoneFinderLogic(avenue, street);
    }

  }

  
}
