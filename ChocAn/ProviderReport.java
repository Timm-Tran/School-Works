/**
 * Created by Tim on 12/1/2015.
 */

/*
This object is used to store all the information required in the provider report, as requested
by the assignment. Like the member class, it will be stored in an array and has limited
functionality.
 */
public class ProviderReport {

    //Necessary declarations for the report.
    public int month;
    public int day;
    public int year;
    public String currentDate;
    public String memberName;
    public String memberNumber;
    public String serviceName;
    public String serviceCode;
    public String fee;
    public String provider;
    public int totalDays;

    //Unnecessary constructor.
    /*public ProviderReport(String dateTime, int date, String memberName, int memberNumber, int serviceCode, int fee) {
        this.dateTime = dateTime;
        this.date = date;
        this.memberName = memberName;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.fee = fee;
    }*/

    //Display functions.
    public void displayAll(){
        System.out.println("Name of member: " + memberName);
        System.out.println("Member number: " + memberNumber);
        System.out.println("Date and time of submission: " + currentDate);
        System.out.println("Date of service: " + month + "-" + day + "-" + year);
        System.out.println("Service code: " + serviceCode);
        System.out.println("Fee of service: " + fee);
    }

    public void weeklyProviderDisplay() {
        System.out.println("Date of service: " + month + "-" + day + "-" + year +
                            "\nDate and time of submission: " + currentDate +
                            "\nMember name: " + memberName +
                            "\nMember number: " + memberNumber +
                            "\nService code: " + serviceCode +
                            "\nFee of service: " + fee);
    }

    //Helper function.
    public void calculateTotalDays() {
        totalDays = 0;
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int count =  0; count <= month -1; ++count)
            totalDays += daysInMonth[count];
        totalDays += day;
        if (year/4 == 0)
            ++totalDays;
    }


}
