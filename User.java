//SID: 2317058
//Group 8

package certificategenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;


public class User {
    //class attributes
    static Scanner scanner = new Scanner(System.in);
    String name = "";
    String dob = "";
    String country ="";
    String phone = "";
    String email = "";
    String username = "";
    String password = "";
    Certificate[] certificates = null;


    //function to update file
    public boolean writeCertificates(){
        try {
            File certificateFile = new File("certificates.txt");
            File tempFile = new File("temp.txt");
    
            BufferedReader readFile = new BufferedReader(new FileReader(certificateFile));
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(tempFile, true));
    
            String line;

            //read line by line
            while ((line = readFile.readLine()) != null) {
                String[] details = line.split(","); //split on basis of ,
                if (! details[0].equals(username)) {
                    writeFile.write(line + System.lineSeparator()); //write the line into temp file
                }
            }
            readFile.close();

            for(Certificate certificate : certificates ){
                writeFile.write(username + "," + certificate.recipientName + "," + certificate.EventName + "," + certificate.giverName+","+certificate.style);
                writeFile.newLine(); //newline appended after details
            }

            writeFile.close();
    
            //Delete the certificates file
            if (certificateFile.delete()) {
                //Rename the tempfile
                if (!tempFile.renameTo(certificateFile)) {
                    System.out.println("Error, cannot rename the temp file.");
                    return false;
                }
    
                return true;
            } else {
                System.out.println("Error, Cannot delete the certificates file.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error occurred while writing certificates to file.");
            return false;
        }
    }

    //function to add certificate to array
    public void addCertificateToarray(Certificate certificate){
        //checking if array is not yet initialized
        if(certificates == null){
            certificates = new Certificate[1];
            certificates[0] = certificate;
            return;
        }

        //creating a temporary array with length one greater than the certificates array
        int arrayLength = certificates.length;
        Certificate[] tempArray = new Certificate[arrayLength+1];

        //copying data of certificates to tempArray
        for( int i = 0; i < arrayLength; i++){
            tempArray[i] = certificates[i];
        }

        //adding new certificate to tempArray
        tempArray[arrayLength] = certificate;

        //Setting certificates equal to tempArray
        certificates = tempArray;
    }

    //function to add certificate to the certificates array
    public boolean addCertificate(Certificate certificate){
       
        addCertificateToarray(certificate);

        try {
            //open file to append
            FileWriter writetoFile = new FileWriter("certificates.txt", true);
            BufferedWriter writer = new BufferedWriter(writetoFile);
            writer.write(username + "," + certificate.recipientName + "," + certificate.EventName + "," + certificate.giverName+","+certificate.style);
            writer.newLine(); //newline appended after details
            writer.close();
            return true; //certificate saved successfully
        } catch (IOException e) {
            System.out.println("Error occurred while saving certificate to file.");
            return false; //failed to save certificate
        }
    }

    //function to view all certificate in certificates array
    public void viewCertificates(){
        int i;
        int choice = 3; //set to 3 by default 

        //loop to validate input choice
        do{
            //clear console
            System.out.print("\033[H\033[2J");  
            System.out.flush();  

            //display welcome message
            System.out.println("\n-------------------------------");
            System.out.println("    WELCOME TO RECENT PAGE");
            System.out.println("-------------------------------\n");

            //if certificates array has no ccertificares then display no certificates to show
            if(certificates == null ){
                System.out.println("No certificates to show");
                break;
            }
            else{
                i = 1;

                //loop to display all certificate in certificates array
                for (Certificate certificate : certificates) {
                    if(certificate.user == username){
                        System.out.println("Certificate "+i);
                        certificate.printCertificate();
                    }
                    i++;
                }

                //displaying choices for next step
                System.out.println("Press 1 to Edit Certificate");
                System.out.println("Press 2 to Download Certificate");
                System.out.println("Press 3 to Go Back");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine();
                }

                //incase of incorrect choice display error
                if( choice!= 1 && choice != 2 && choice != 3){
                    System.out.println("Invalid choice. Choose Again. \n");

                    //pause
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }while(choice != 1 && choice != 2 && choice != 3);

        //checking choice and performing action accordingly
        if (choice == 3){
            //pause before going back
            System.out.println("Loading...");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        else if (choice == 2){
            int num = 0;

            //loop to validate certificate number entered
            do{
                //take certificate number as input
                System.out.print("Enter certificate number you want to edit: ");
                try{
                    num = scanner.nextInt();
                }
                catch(InputMismatchException e){
                    System.out.println("Expected a number.");
                    scanner.nextLine();
                }

                //display error in case of wrong number
                if(num <1 || num > certificates.length){
                    System.out.println("Invalid Certificate number. Try again. \n");
                }
            }while (num <1 || num > certificates.length);

            //inputting filename for certificate
            System.out.print("Enter filename: ");
            scanner.nextLine();
            String name = scanner.nextLine();

            name += ".pdf";

            //calling download as pdf function to download the certificate at location num-1 in array
            certificates[num-1].downloadPDFCertificate(name);
        }
        else if (choice == 1){
            int num = 0;

            //loop to validate certificate number entered
            do{
                //take certificate number as input
                System.out.print("Enter certificate number you want to edit: ");
                try{
                    num = scanner.nextInt();
                }
                catch(InputMismatchException e){
                    System.out.println("Expected a number.");
                    scanner.nextLine();
                }

                //display error in case of wrong number
                if(num <1 || num > certificates.length){
                    System.out.println("Invalid Certificate number. Try again. \n");
                }
            }while (num <1 || num > certificates.length);

            //calling edit function to edit the certificate at location num-1 in array
            certificates[num-1].editCertificate();

            writeCertificates();
        }

    }

    //function to login into user account
    public boolean Login(){
        boolean userExist;

        //loop to validate input credentials
        do{
            //clear screen
            System.out.print("\033[H\033[2J");  
            System.out.flush();  

            //display welcome message
            System.out.println("\n-------------------------------");
            System.out.println("    WELCOME TO LOGIN PAGE");
            System.out.println("-------------------------------\n");

            //input username
            System.out.print("Enter your username: ");
            username = scanner.nextLine();

            //input password
            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            //checking if user exists
            userExist = authenticateUser(username, password);

            //displaying error if user does not exist
            if( !userExist ){
                System.out.println("Invalid credentials. Try Again.\n");

                //pause
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } while(!userExist);

        System.out.println("Loading...");
        //pause
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //if user exists get user details and update class attributes
        if (userExist)
            return getUserDetails(); //if details recieved returns true. User is logged in.
        

        return false; //false as user is not logged in
    }

    //funciton to check if user exists in file
    boolean authenticateUser(String username, String password){
        //exception handling
        try {
            //open file for reading
            File file = new File("userdetails.txt");
            Scanner scanfile = new Scanner(file);

            //read line by line
            while (scanfile.hasNextLine()) {
                String line = scanfile.nextLine();
                String[] details = line.split(","); //split of basis of ,
                String un = details[0];
                String pass = details[1];

                //if details match return true
                if (un.equals(username) && pass.equals(password)) {
                    scanfile.close();
                    return true; //user exists
                }
            }

            scanfile.close();
            return false; //user doesnot exist
        } catch (FileNotFoundException e) {
            System.out.println("Error occured cannot verify user.");
        }
        return false; //user doesnot exist
    }

    //function to register new user to file
    public boolean Register(){
        //clear screen
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 

        //display welcome message
        System.out.println("\n-------------------------------");
        System.out.println("    WELCOME TO REGISTER PAGE");
        System.out.println("-------------------------------\n");
        
        boolean userinFile, correctPass, confirmed = false, validDate, validEmail, validPhone;
        String n, d, e, p, c, un, pass, confirmPass;

        //taking user input for userdeatils
        System.out.print("Enter your Name: ");
        n = scanner.nextLine();

        //validating date is of correct format
        do{
            System.out.print("Enter your Date of birth in format dd/mm/yyyy: ");
            d = scanner.nextLine();

            validDate = validateDate(d);

            if(!validDate){
                System.out.println("Date should be in format dd/mm/yyyy");
            }
        }while(!validDate);

        System.out.print("Enter your Country: ");
        c = scanner.nextLine();

        //validating that email is of correct format
        do{
            System.out.print("Enter your Email Address: ");
            e = scanner.nextLine();
            validEmail = validateEmail(e);

            if(!validEmail){
                System.out.println("Email should be in format xyx@abc");
            }
        }while(!validEmail);

        do{
            System.out.print("Enter your Phone Number: ");
            p = scanner.nextLine();

            validPhone= validatePhoneNumber(p);

            if(!validPhone){
                System.out.println("Phone number should only contain numbers");
            }
        }while(!validPhone);

        //validating that username is not already taken
        do{
            System.out.print("Enter your username: ");
            un = scanner.nextLine();

            userinFile = userExists(un);

            if( userinFile ){
                System.out.println("Username already taken. Kindly choose another username.\n");
            }
        
        } while(userinFile);

        //validating that password is atleast 8 characters long
        do{
            System.out.print("Enter your password: ");
            pass = scanner.nextLine();

            correctPass = verifyPassword(pass);

            if(!correctPass){
                System.out.println("Password should be atleast 8 characters long.\n");
            }
        }while(!correctPass);

        //validating that the password and confirm password match
        do{
            System.out.print("Confirm your password: ");
            confirmPass = scanner.nextLine();

            if(pass.equals(confirmPass)){
                confirmed = true;
            }
            else{
                System.out.println("Passwords do not match.\n");
            }

        }while(!confirmed);

        //updating class attributes
        username = un;
        password = pass;
        name = n;
        dob = d;
        email = e;
        phone = p;
        country=c;

        System.out.println("Loading...");
        //pause
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //adding user to file
        boolean saved = saveUser(un, pass);
        return saved; //registration successful if user is saved
    }

    //function to validate phone number
    boolean validatePhoneNumber(String phoneNumber) {
        String regex = "[0-9]+"; //only contains numbers
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    //function to validate email
    boolean validateEmail(String e){
        String regex = ".*@.*"; //email format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(e);
        return matcher.matches();
    }

    //function to validate date format
    boolean validateDate(String date){
        String regex = "\\d{2}/\\d{2}/\\d{4}"; //date format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    //function to verify that the password is atleast 8 characters long
    boolean verifyPassword(String checkpass){
        if(checkpass.length() >= 8 ){
            return true;
        }
        return false;
    }

    //function to check if username  is taken
    boolean userExists(String checkusername){
        try {
            //open file for reading
            File file = new File("userdetails.txt");
            Scanner scanfile = new Scanner(file);

            //read line by line
            while (scanfile.hasNextLine()) {
                String line = scanfile.nextLine();
                String[] details = line.split(","); //split on basis of ,
                String un = details[0];

                //if username matched return exists
                if (un.equals(checkusername)) {
                    scanfile.close();
                    return true; //username taken
                }
            }

            scanfile.close();
            return false; //not taken
        } catch (FileNotFoundException e) {
            System.out.println("Error occured cannot verify user.");
        }

        return false; //not taken
    }

    //function to add new user to file
    boolean saveUser(String un, String pass){
        try {
            //open file to append
            FileWriter writetoFile = new FileWriter("userdetails.txt", true);
            BufferedWriter writer = new BufferedWriter(writetoFile);
            writer.write(username + "," + password + "," + name + "," + dob+","+country+","+email+","+phone);
            writer.newLine(); //newline appended after details
            writer.close();
            return true; //user saved successfully
        } catch (IOException e) {
            System.out.println("Error occurred while saving user details.");
            return false; //failed to save user
        }
    }

    //function to get certificates of user from file
    boolean getCertificates(){
        try {
            //open file for reading
            File file = new File("certificates.txt");
            Scanner scanfile = new Scanner(file);

            //read line by line
            while (scanfile.hasNextLine()) {
                String line = scanfile.nextLine();
                String[] details = line.split(","); //split on basis of ,
                String un = details[0];
                
                //when username matches, get all certificates
                if(un.equals(username)){
                    Certificate certificate = new Certificate(username);
                    certificate.recipientName = details[1];
                    certificate.EventName = details[2];
                    certificate.giverName = details[3];
                    certificate.style = details[4].charAt(0);
                    addCertificateToarray(certificate);
                }
            }
            scanfile.close();
            return true; //certificates fethed successfully
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while getting certificates from file.");
        }
        return false; //certificates not updated
    }

    //update class attributes with userdetails from file
    boolean getUserDetails(){
        try {
            //open file for reading
            File file = new File("userdetails.txt");
            Scanner scanfile = new Scanner(file);

            //read line by line
            while (scanfile.hasNextLine()) {
                String line = scanfile.nextLine();
                String[] details = line.split(","); //split on basis of ,
                String un = details[0];
                
                //when username matches, get all details
                if(un.equals(username)){
                    name = details[2];
                    dob = details[3];
                    country=details[4];
                    email=details[5];
                    phone=details[6];
                    scanfile.close();
                    getCertificates();
                    return true; //attributes updated
                }
            }
            scanfile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured while getting user details.");
        }
        return false; //attributes not updated
    }

    //function to print all user details
    public void printUserDetails(){
        System.out.println("\n---------------------------------");
        System.out.println("           USER DETAILS");
        System.out.println("---------------------------------");
        System.out.println("Name: "+name);
        System.out.println("Date of Birth: "+dob);
        System.out.println("Country: "+country);
        System.out.println("Phone Number: "+phone);
        System.out.println("Email: "+email);
        System.out.println("Username: "+username);
        System.out.println("---------------------------------\n");
    }
    
}
