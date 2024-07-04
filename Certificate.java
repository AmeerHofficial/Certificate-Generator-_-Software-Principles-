//SID: 2317058
//Group 8

package certificategenerator;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;
import java.awt.Color;

// Import PDFBox classes
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Certificate {
    //class attribbutes
    static Scanner scanner = new Scanner(System.in);
    String user = "";
    String recipientName = "";
    String EventName = "";
    String giverName = "";
    char style = ' ';

    //default constructor to set default values
    Certificate(String username){
        user = username;
        recipientName = "Recipient Name";
        EventName = "Event Name";
        giverName = "Giver Name";
        style = '-';
    }

    //function for make design page
    public void displayMakeDesignPage(){
        int s = 0;

        //loop to validate style choice
        do{
            //clear screen
            System.out.print("\033[H\033[2J");  
            System.out.flush(); 

            //display welcome message
            System.out.println("------------------------------------");
            System.out.println("    WELCOME TO MAKE DESIGN PAGE ");
            System.out.println("------------------------------------\n");

            //display style options
            System.out.println("Select a style option");
            System.out.println("1) -");
            System.out.println("2) +");
            System.out.println("3) *");
            System.out.println("4) /");
            System.out.println("5) \\");
            System.out.println("6) ?");
            System.out.println("7) >");
            System.out.println("8) <");

            //input style from user
            try{
                s = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Expected a number.");
                scanner.nextLine();
            }

            //error displayed if invalid style option
            if( s < 1 || s > 8){
                System.out.println("Invalid style choice. Try Again \n");

                //pause
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }while (s<1 || s>8);

        //assigning style a value
        if(s == 1) style = '-';
        if(s == 2) style = '+';
        if(s == 3) style = '*';
        if(s == 4) style = '/';
        if(s == 5) style = '\\';
        if(s == 6) style = '?';
        if(s == 7) style = '>';
        if(s == 8) style = '<';

        scanner.nextLine(); //get extra character

        //displaying sample certificate with style option
        System.out.println("\nSample Certificate Template\n");
        printCertificate();

        //inputting certificate detail
        System.out.print("Enter Recipient Name: ");
        recipientName = scanner.nextLine();

        System.out.print("Enter Event Name: ");
        EventName = scanner.nextLine();

        System.out.print("Enter Giver Name: ");
        giverName = scanner.nextLine();

        //displaying final certificate
        printCertificate();

        System.out.println("Loading...");
        //pause
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //function to print certificate
    void printCertificate() {
        System.out.print(" ");
        for (int i = 0; i<34;i++) {
            System.out.print(style);
        }
        System.out.print("\n"+style+" ");
        for (int i = 0; i<31;i++) {
            System.out.print(style);
        }
        System.out.print(" "+style+"\n");
        System.out.print(style+" ");
        for (int i = 0; i<31;i++) {
            System.out.print(" ");
        }
        System.out.print(" "+style+"\n");

        System.out.print(style+ "   RECIPIENT: "+recipientName);
        for (int i = 0; i<19-recipientName.length();i++) {
            System.out.print(" ");
        }
        System.out.print(style+"\n");

        System.out.print(style+ "   EVENT: "+EventName);
        for (int i = 0; i<23-EventName.length();i++) {
            System.out.print(" ");
        }
        System.out.print(style+"\n");

        System.out.print(style+ "   AWARDED BY: "+giverName);
        for (int i = 0; i<18-giverName.length();i++) {
            System.out.print(" ");
        }
        System.out.print(style+"\n");
        System.out.print(style+" ");
        for (int i = 0; i<31;i++) {
            System.out.print(" ");
        }
        System.out.print(" "+style+"\n");

        System.out.print(style+" ");
        for (int i = 0; i<31;i++) {
            System.out.print(style);
        }
        System.out.print(" "+style+"\n");

        System.out.print(" ");
        for (int i = 0; i<34;i++) {
            System.out.print(style);
        }
        System.out.println("\n\n");
    }
    
    //funciton to edit certificate
    void editCertificate(){
        //printing the certificate that will be editted
        System.out.println("Editing Certificate: ");
        printCertificate();

        //inputting user choices
        System.out.print("Enter recipient name: ");
        recipientName = scanner.nextLine();

        System.out.print("Enter event name: ");
        EventName = scanner.nextLine();

        System.out.print("Enter giver name: ");
        giverName = scanner.nextLine();

        //displaying editted certificate
        System.out.println("Updated Certificate: ");
        printCertificate();

        System.out.println("Loading...");
        //pause
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //function to download certificate as PDF
    public void downloadPDFCertificate(String fileName) {
        try {
            //creating pdf document
            PDDocument document = new PDDocument();
            
            //setting height width for landscape orientation
            float width = PDRectangle.A4.getHeight() + 100;
            float height = PDRectangle.A4.getWidth() - 100;
            PDPage page = new PDPage(new PDRectangle(width, height));
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
    
            //changing background color to light blue
            contentStream.setNonStrokingColor(Color.decode("#ADD7E8"));
            contentStream.fillRect(0, 0, width, height);
            
            //font style is times new roman and size is 16
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
            contentStream.setNonStrokingColor(Color.BLACK);
            
            //setting position variables
            float startX = (width /2 )- 80;
            float startY = (height /2 ) + 100;
            float lineHeight = 80;

            //border start
            contentStream.beginText();
            contentStream.newLineAtOffset(0, height-15);
            String border = "";
            for(int i = 0; i<width;i++){
                border += style;
            }
            contentStream.showText(border);
            contentStream.endText();
            
            //recipient name
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText("Recipient: " + recipientName);
            contentStream.endText();
            
            //event name
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY - lineHeight);
            contentStream.showText("Event: " + EventName);
            contentStream.endText();
            
            //awarded by
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY - 2 * lineHeight);
            contentStream.showText("Awarded By: " + giverName);
            contentStream.endText();

            //border end
            contentStream.beginText();
            contentStream.newLineAtOffset(0,15);
            border = "";
            for(int i = 0; i<width;i++){
                border += style;
            }
            contentStream.showText(border);
            contentStream.endText();
            contentStream.close();
    
            //save certificate
            document.save(fileName);
            document.close();
    
            System.out.println("Certificate saved as PDF: " + fileName);
            System.out.println("Press enter to continue...");
            scanner.nextLine();
        } catch (IOException e) {
            System.err.println("Error!! Cannot generate Certificate PDF.");
        }
    }
}
