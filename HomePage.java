//SID: 2317058
//Group 8

package certificategenerator;

import java.util.Scanner;
import java.util.InputMismatchException;


public class HomePage {
    //class attributes
    static Scanner scanner = new Scanner(System.in);
    User user = null;

    //parameterized constructor
    HomePage(User u){
        user = u;
    }

    //function to handle main homepage funcitonality
    public int displayHomePage(){
        int choice = 0;

        //loop runs till user logs out
        while (true){
            //validate user choice
            do{
                //clear screen
                System.out.print("\033[H\033[2J");  
                System.out.flush(); 

                //display welcome message
                System.out.println("------------------------------------");
                System.out.println("   WELCOME TO HOME "+user.name);
                System.out.println("------------------------------------\n");

                //printing user details
                user.printUserDetails();
                
                //Displaying options
                System.out.println("Press 1 to Make Certificate");
                System.out.println("Press 2 to Recents");
                System.out.println("Press 3 to Logout");

                //input user choice
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine();
                }

                //invalid choice displays error
                if( choice!= 1 && choice != 2 && choice != 3 ){
                    System.out.println("Invalid choice. Choose Again. \n");

                    //pause
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }while(choice!= 1 && choice != 2 && choice != 3);

            //choice 3 means user logged out
            if(choice == 3){
                System.out.println("Logging Out...");

                try {//pause
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
            //choice 1 user navigates to make certifcate
            else if (choice == 1){
                Certificate certificate = new Certificate(user.username);
                certificate.displayMakeDesignPage();

                //add certiifcate to user's certiciates array
                user.addCertificate(certificate);
            }
            //choice 2 to view certificates. navigate to recents page
            else if (choice == 2){
                user.viewCertificates();
            }
        }
        //3 is user logged out
        return 3;
    }
    
}
