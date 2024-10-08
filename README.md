# UberApp-Simulation

This project is a simple command-line-based app that simulates ride-sharing and delivery services. The application allows users to interact with the system through various commands, enabling them to manage rides and deliveries.

## Features

- **Ride-Sharing Service**: Simulate requesting and managing ride-sharing services.
- **Delivery Service**: Simulate delivery requests and manage them through the system.
- **Command-Line Interaction**: Users can interact with the application through command-line inputs.

## Installation

To run this program, you'll need to have Java installed on your machine.

1. **Clone the repository** to your local machine:
    ```bash
    git clone https://github.com/ItsFQ/UberApp-Simulation.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd UberApp-Simulation
    ```
3. **Compile the Java files**:
    ```bash
    javac TMUberUI.java
    ```
4. **Run the program**:
    ```bash
    java TMUberUI
    ```

## Usage

Once the program is running, you'll see a command prompt (`>`). You can type various commands to interact with the system. Below is a list of available commands and their functionalities:

### Command List

- **DRIVERS**: Displays a list of all registered drivers.
- **USERS**: Displays a list of all registered users.
- **REQUESTS**: Shows all active ride or delivery requests.
- **REGDRIVER**: Registers a new driver in the system.
- **REGUSER**: Registers a new user in the system.
- **REQRIDE**: Creates a new ride request.
- **REQDLVY**: Creates a new delivery request.
- **SORTBYNAME**: Sorts the list of users or drivers by name.
- **SORTBYWALLET**: Sorts users or drivers by the balance in their wallet.
- **CANCELREQ**: Cancels an existing ride or delivery request.
- **DROPOFF**: Marks a ride or delivery as complete.
- **REVENUES**: Displays the revenue generated by the system.
- **ADDR**: Adds a new ride to the system.
- **DIST**: Calculates or displays distances between locations.
- **ZONE**: Manages zones, potentially used for geographic categorization.
- **LOADUSERS**: Loads users from an external file.
- **LOADDRIVERS**: Loads drivers from an external file.
- **PICKUP**: Indicates a driver has picked up a user or package.
- **DRIVETO**: Simulates driving to a location.
- **QUIT**: Exit the application.

### Example Commands

```bash
>loadusers
User File: users.txt
Users Loaded

>users

1 . Id: 9000  Name: Ash, T.         Address: 34 5th Street   Wallet: 25.00
2 . Id: 9001  Name: Keff, R.        Address: 71 1st Street   Wallet: 55.00
3 . Id: 9002  Name: Jeff, E.        Address: 55 9th Avenue   Wallet: 125.00
4 . Id: 9003  Name: Bob, A          Address: 15 2nd Avenue   Wallet: 15.00
5 . Id: 9004  Name: Rebecca, P.     Address: 32 3rd Street   Wallet: 13.00
6 . Id: 9005  Name: Zo, I.          Address: 64 6th Avenue   Wallet: 27.00
7 . Id: 9006  Name: Tilu, M.        Address: 28 7th Avenue   Wallet: 22.00
8 . Id: 9007  Name: Dinu, E.        Address: 10 7th Avenue   Wallet: 34.00
9 . Id: 9008  Name: Wen, D.         Address: 48 8th Street   Wallet: 11.00
10. Id: 9009  Name: Andrea, M.      Address: 83 4th Street   Wallet: 41.00

>loaddrivers
Driver File: drivers.txt
Drivers Loaded

>drivers

1 . Id: 7000 Name: Tom Cruise      Car Model: Toyota Corolla  License Plate: MAVERICK        Wallet: 0.00 
Status: AVAILABLE       Address: 34 4th street   Zone: 3              

2 . Id: 7001 Name: Brad Pitt       Car Model: Audi S4         License Plate: FGDR 983        Wallet: 0.00 
Status: AVAILABLE       Address: 85 8th street   Zone: 1              

3 . Id: 7002 Name: Millie Brown    Car Model: Tesla           License Plate: STRNGRTHGS      Wallet: 0.00 
Status: AVAILABLE       Address: 67 9th avenue   Zone: 1              

4 . Id: 7003 Name: Tim Chalamet    Car Model: Thopter         License Plate: DUNE            Wallet: 0.00 
Status: AVAILABLE       Address: 21 8th avenue   Zone: 2              

5 . Id: 7004 Name: John Boyega     Car Model: X-Wing          License Plate: REBEL           Wallet: 0.00 
Status: AVAILABLE       Address: 32 7th avenue   Zone: 2              


>reqride
User Account Id: 9000
From Address: 65 6th street
To Address: 95 8th avenue

RIDE for: Ash, T.         From: 65 6th street   To: 95 8th avenue  
>requests

ZONE 0
======

ZONE 1
======

1. ----------------------------------------------------------------------

Type: RIDE      From: 65 6th street   To: 95 8th avenue  
User: Id: 9000  Name: Ash, T.         Address: 34 5th Street   Wallet: 25.00

ZONE 2
======

ZONE 3
======


>pickup
Driver Id: 7001 

Driver 7001 Picking up in Zone 1

>dropoff
Driver ID #: 7001
Driver 7001 Dropping Off
>requests

ZONE 0
======

ZONE 1
======

ZONE 2
======

ZONE 3
======


>drivers

1 . Id: 7000 Name: Tom Cruise      Car Model: Toyota Corolla  License Plate: MAVERICK        Wallet: 0.00 
Status: AVAILABLE       Address: 34 4th street   Zone: 3              

2 . Id: 7001 Name: Brad Pitt       Car Model: Audi S4         License Plate: FGDR 983        Wallet: 0.75 
Status: AVAILABLE       Address: 95 8th avenue   Zone: 1              

3 . Id: 7002 Name: Millie Brown    Car Model: Tesla           License Plate: STRNGRTHGS      Wallet: 0.00 
Status: AVAILABLE       Address: 67 9th avenue   Zone: 1              

4 . Id: 7003 Name: Tim Chalamet    Car Model: Thopter         License Plate: DUNE            Wallet: 0.00 
Status: AVAILABLE       Address: 21 8th avenue   Zone: 2              

5 . Id: 7004 Name: John Boyega     Car Model: X-Wing          License Plate: REBEL           Wallet: 0.00 
Status: AVAILABLE       Address: 32 7th avenue   Zone: 2              


>quit
