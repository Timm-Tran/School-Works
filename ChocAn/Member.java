/**
 * Created by Tim on 12/1/2015.
 */

/*
This is the object that will hold and work will member's information. It will be stored
in an array of the same object next to other members. It has relatively simple functionality,
as the terminals will be doing most of the work.
 */
import java.util.Scanner;

public class Member {

    //Necessary declarations of the member object as dictated by the assignment.
    public String name;
    private String validation;
    public String number;
    private String streetAddress;
    private String city;
    private String state;
    private int zipCode;
    public ProviderReport[] reportList = new ProviderReport[10];
    private String totalFee;

    //Constructors which can make an empty or completely filled member, depending on use.
    public Member(){    }

    public Member(String name, String validation, String number, String streetAddress, String city, String state, int zipCode) {
        this.name = name;
        this.validation = validation;
        this.number = number;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;

    }

    //Get and set functions.
    public String getValidation(){
        return this.validation;
    }
    public void setValidation(String validation) { this.validation = validation; }
    public String getNumber() { return this.number; }

    public void assignReport(ProviderReport report){
        int i = 0;
        while (this.reportList[i] != null){
            ++i;
        }
        this.reportList[i] = report;
        this.totalFee += report.fee;
    }

    //Display functions
    public void displayInfo() {
        System.out.println("Member name: " + name);
        System.out.println("Member status: " + validation);
        System.out.println("Member number: " + number);
        System.out.println("Street address: " + streetAddress);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("Zipcode: " + zipCode);
    }

    public void displayReports() {
        int i = 0;
        while (this.reportList[i] != null) {
            this.reportList[i].displayAll();
            ++i;
        }
    }

    //Editing function
    public void editInfo() {
        Scanner input = new Scanner(System.in);

        System.out.println("Name? ");
        this.name = input.nextLine();
        System.out.println("Valid members? ");
        this.validation = input.nextLine();
        System.out.println("Number? ");
        this.number = input.nextLine();
        System.out.println("Address? ");
        this.streetAddress = input.nextLine();
        System.out.println("City? ");
        this.city = input.nextLine();
        System.out.println("State? ");
        this.state = input.nextLine();
        System.out.println("Zipcode? ");
        this.zipCode = input.nextInt();
    }

}
