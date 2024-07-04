//SID: 2317058
//Group 8

package certificategenerator;

import java.util.Scanner;

/**
 * Certificate generator!
 *
 */

//Main Welcome Page for Certificate Generator Application
public class App 
{
    //class attributes
    static Scanner scanner = new Scanner(System.in);
    static User user = null;

    //main function to run all the code
    public static void main(String[] args) throws Exception {
        int userChoice;
        boolean loggedin = false;

        //loop runs till user quits
        while (true) {

            user = null;
            //do while loop to make sure correct input entered
            do{
                //clear console
                System.out.print("\033[H\033[2J");  
                System.out.flush(); 

                displayWelcomeMessage();
                displayOptions("Login", "Register", "Quit");

                //input user choice
                userChoice = getUserChoice();

                //error message incase of wrong choice
                if( userChoice !=1 && userChoice!= 2 && userChoice!= 3 ){
                    System.out.println("Invalid choice.");
                }
            } while ( userChoice !=1 && userChoice!= 2 && userChoice!= 3 );

            //performing actions according to userChoice
            if (userChoice == 1){
                user = new User();
                loggedin = user.Login();
            }
            else if (userChoice == 2){
                user = new User();
                loggedin = user.Register();
            }
            else if (userChoice == 3){
                //quit code
                System.out.println("Exitting Application...");

                try { //pause
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return;
            }

            //check if user is logged in
            if(loggedin){
                //navigate to homepage
                HomePage home = new HomePage(user);
                home.displayHomePage();
            }

        }
    }

    //function to display welcome message
    public static void displayWelcomeMessage(){
        System.out.println("--------------------------------------------------------------------");
        
        System.out.println("   ____ _____ ____ _____ ___ _____ ___ ____    _  _____ _____ ");
        System.out.println("  / ___| ____|  _ \\_   _|_ _|  ___|_ _/ ___|  / \\|_   _| ____|");
        System.out.println(" | |   |  _| | |_) || |  | || |_   | | |     / _ \\ | | |  _|  ");
        System.out.println(" | |___| |___|  _ < | |  | ||  _|  | | |___ / ___ \\| | | |___ ");
        System.out.println("  \\____|_____|_| \\_\\|_| |___|_|   |___\\____/_/   \\_\\_| |_____|\n");

        System.out.println("     ____ _____ _   _ _____ ____      _  _____ ___  ____  ");
        System.out.println("    / ___| ____| \\ | | ____|  _ \\    / \\|_   _/ _ \\|  _ \\ ");
        System.out.println("   | |  _|  _| |  \\| |  _| | |_) |  / _ \\ | || | | | |_) |");
        System.out.println("   | |_| | |___| |\\  | |___|  _ <  / ___ \\| || |_| |  _ < ");
        System.out.println("    \\____|_____|_| \\_|_____|_| \\_\\/_/   \\_\\_| \\___/|_| \\_\\");

        System.out.println("--------------------------------------------------------------------");
    
    }

    //function to display choices
    public static void displayOptions(String option1, String option2, String option3){
        System.out.println("Please select an option:");
        System.out.println("1) "+option1);
        System.out.println("2) "+option2);
        System.out.println("3) "+option3);
    }

    //function to input userchoice
    public static int getUserChoice() throws Exception{
        try{
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.nextLine();
            return 3;
        }
    }

}
