/**
 * Created by Tim on 12/1/2015.
 */

/*
Much like the member and provider class, this object will store necessary information
in an array. This information relates to the provider. Has limited functionality.
 */
import java.util.Scanner;

public class Provider {

    //Necessary declarations, as requested by assignment.
    public String name;
    public String number;
    public String streetAddress;
    public String city;
    public String state;
    public int zipCode;
    public int consultants;
    public String totalFee;
    public ProviderReport[] reportList = new ProviderReport[30];
    public int numOfReports = 0;

    //Constructors each having their own uses. One creates with data, other without.
    public Provider() {  }

    public Provider(String name, String number, String streetAddress, String city, String state, int zipCode) {
        this.name = name;
        this.number = number;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    //Display functions.
    public void displayInfo() {
        System.out.println("Provider name: " + name +
                            "\nProvider number: " + number +
                            "\nStreet address: " + streetAddress +
                            "\tCity: " + city +
                            "\tState: " + state +
                            "\tZipcode: " + zipCode);
    }

    public void displayReports() {
        int i = 0;
        while (reportList[i] != null) {
            reportList[i].displayAll();
            ++i;
        }
    }

    //Helper and editing functions.
    public void assignReport(ProviderReport report) {
        int i = 0;
        while(this.reportList[i] != null)
            ++i;

        this.reportList[i] = report;
        this.totalFee += report.fee;
        ++this.consultants;
    }

    public void editInfo() {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter name of provider: ");
        this.name = input.nextLine();
        System.out.println("Enter number of provider: ");
        this.number = input.nextLine();
        System.out.println("Enter street address of provider: " );
        this.streetAddress = input.nextLine();
        System.out.println("Enter city of provider: " );
        this.city = input.nextLine();
        System.out.println("Enter state of provider: " );
        this.state = input.nextLine();
        System.out.println("Enter zipcode of provide: " );
        this.zipCode = input.nextInt();
        input.nextLine();
    }
}
