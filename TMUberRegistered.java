import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    public static ArrayList<User> loadPreregisteredUsers(String fileName) throws FileNotFoundException, IOException
    {
        ArrayList<User> users = new ArrayList<>();

        File userFile = new File(fileName);
        Scanner sc = new Scanner(userFile);

        while (sc.hasNextLine()) {
            users.add(new User(generateUserAccountId(users), sc.nextLine(), sc.nextLine(), Integer.parseInt(sc.nextLine())));
        }
        sc.close();
        return users;
    }

    // Database of Preregistered users
    public static ArrayList<Driver> loadPreregisteredDrivers(String fileName) throws FileNotFoundException, IOException
    {
        ArrayList<Driver> drivers = new ArrayList<>();

        File driverFile = new File(fileName);
        Scanner sc = new Scanner(driverFile);

        while (sc.hasNextLine()) {
            drivers.add(new Driver(generateDriverId(drivers), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()));
        }
        sc.close();
        return drivers;
    }
}

