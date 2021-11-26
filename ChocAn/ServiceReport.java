/**
 * Created by Tim on 12/1/2015.
 */

/*
This object is the file that is created after finishing a service automatically and
is used only for verification purposes. Is only accessed by manager and has very
limited functionality.
 */
public class ServiceReport {

    //Necessary declarations as requested by assignment.
    public String dateTime;
    public String date;
    public String providerNumber;
    public String memberNumber;
    public String serviceCode;
    public String comments;

    //Constructor.
    public ServiceReport(String dateTime, String date, String providerNumber, String memberNumber, String serviceCode, String comments) {
        this.dateTime = dateTime;
        this.date = date;
        this.providerNumber = providerNumber;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.comments = comments;
    }

    //Display
    public void displayAll() {
        System.out.println("Date of filing: " + this.dateTime +
                            "\nDate of service: " + this.date +
                            "\nProvider number: " + this.providerNumber +
                            "\nMember number: " + this.memberNumber +
                            "\nService Code: " + this.serviceCode +
                            "\nComments: " + comments);
    }
}
