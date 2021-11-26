/**
 * Created by Tim on 12/1/2015.
 */

/*
This is the providers terminal. It is the interface by which providers use to do their job.
They will have to login then will be prompted with choices of activities, much like the manager.
Providers can enter a record after a service, request the provider directory, add/remove/edit
members, and view weekly reports.
 */
import java.io.*;
import java.util.Scanner;
public class ProviderTerminal {


    //This is the function that will facilitate the activities of the provider. It is passed in all
    //the necessary information to do the activities and is meant to be the interface of the terminal.
    public void startProgram(Provider providerList[], Member memberList[], ProviderReport reportList[], ServiceReport serviceReportList[], int maxMembers) throws FileNotFoundException{

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String answer = "yes";
        int a = 0;
        int providerIndex = -1;

        //Login process. If user quits, program exits interface.
        while (!answer.toUpperCase().equals("NO")){
            providerIndex = logIn(providerList, maxMembers);
            if (providerIndex == -1) {
                System.out.println("Retry? (Yes/No) ");
                answer = input.nextLine();
                if (answer.toUpperCase().equals("NO"))
                    return;
            }
            else
                answer = "NO";

        }

        //Interface by which the provider chooses and activity. Idealy, the interface will look
        //like buttons the provider can press to start an activity. Can keep doing activities
        //until they log out/exit.
        do {
            System.out.println("Please choose an action (choose a number): " + "\n1. Enter new service record." +
                    "\n2. Request provider's directory. " + "\n3. Add new member." +
                    "\n4. Remove a member. " + "\n5. Validate member. " + "\n6. View your weekly report." +
                    "\n7. Exit.");
            a = input.nextInt();

            if (a == 1)
                newServiceRecord(memberList, providerList, reportList, serviceReportList, providerIndex);
            if (a == 2)
                displayProviderDirectory();
            if (a == 3)
                addMember(memberList, maxMembers);
            if (a == 4)
                removeMember(memberList);
            if (a == 5)
                validateMember(memberList);
            if (a == 6)
                weeklyReport(providerList, providerIndex);

        }while (a != 7);
    }

    //Helper function to login. Requires name and number of the provider.
    public int logIn(Provider providerList[], final int maxMembers) {
        //Necessary declarations
        String name;
        String number;
        Scanner input = new Scanner(System.in);

        //Gets the user name and number
        System.out.println("Please login.");
        System.out.println("Name: ");
        name = input.nextLine();
        System.out.println("Number: ");
        number = input.nextLine();

        //Tests for authentic information.
        for (int i = 0; i < maxMembers; ++i) {
            if (providerList[i] != null) {
                if (providerList[i].name.equals(name) && providerList[i].number.equals(number))
                    return i;
            }
            else {
                System.out.println("Invalid login information.");
                return -1;
            }

        }
        return -1;
    }

    //Activity the providers will do after services a customer. This function will create a provider directory,
    //Correctly map the provider and members report list to it, and create the service report.
    public void newServiceRecord(Member memberList[], Provider providerList[], ProviderReport reportList[], ServiceReport serviceReportList[], int providerIndex) throws FileNotFoundException{

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String response = "0";
        int a = -2;
        int b = 0;
        int SRLIndex = 0;
        String comments = "o";

        //Finding an open spot for the service report to fit in.
        while (serviceReportList[SRLIndex] != null)
            ++SRLIndex;

        //Asks for member name.
        while(a == -2) {
            System.out.println("Enter name of member: ");
            response = input.nextLine();
            a = validateMember(memberList, response);
            if (a == -1){
                System.out.println("Invalid member. Exiting");
                return;
            }
        }

        //Searched for open space in the provider report list.
        while (reportList[b] != null)
            ++b;

        //Creates a new report, assigns information, and asks for other information.
        reportList[b] = new ProviderReport();
        reportList[b].provider = providerList[providerIndex].name;
        checkServiceCode(reportList[b]);
        reportList[b].memberName = response;
        reportList[b].memberNumber = retrieveMemberNumber(memberList, response);

        System.out.println("Enter current date and time (MM-DD-YYYY hh:mm am/pm): ");
        reportList[b].currentDate = input.nextLine();
        System.out.println("Enter month of service (MM, for example, February = '02'): ");
        reportList[b].month = input.nextInt();
        System.out.println("Enter day of service(DD, for example, '12'): ");
        reportList[b].day = input.nextInt();
        System.out.println("Enter year of service (YYYY, for example, 2015): ");
        reportList[b].year = input.nextInt();
        input.nextLine();
        System.out.println("Add comments about service here: ");
        comments = input.nextLine();
        reportList[b].calculateTotalDays();

        //Here the provider and members report list are correctly assigned to the new report.
        memberList[a].assignReport(reportList[b]);
        providerList[providerIndex].assignReport(reportList[b]);

        //New service report is created.
        serviceReportList[SRLIndex] = new ServiceReport(reportList[b].currentDate, reportList[b].currentDate, providerList[providerIndex].number,
                                                        retrieveMemberNumber(memberList, response), reportList[b].serviceCode, comments);
    }

    //This activity sets a members status to active. If they were not active before,
    //it was due to a late fee or missed payment.
    public int validateMember(Member memberList[], String name){

        //Necessary declarations
        int i = 0;
        String validation;
        Scanner input = new Scanner(System.in);

        //Asks for members name to verify.
        while (!memberList[i].name.equals(name)) {
            ++i;
            if (memberList[i] == null) {
                System.out.println("Member does not exist. Try again? (Yes/No)");
                validation = input.nextLine();
                if (validation.toUpperCase().equals("YES"))
                    return -2;
                else
                    return -1;
            }
        }

        //Shows currently status of member.
        validation = memberList[i].getValidation();
        System.out.println("Valid member: " + validation);

        //Updates member status to active.
        if (validation.toUpperCase().equals("YES"))
            return i;
        else
            return -1;
    }

    //Helper function to check the service code that happens in the provider report
    //creating process, as requested by the assignment. Prompts user if the entered
    //service code represents the correct service provided.
    public void checkServiceCode(ProviderReport report) throws FileNotFoundException{

        //Necessary declarations.
        Scanner input = new Scanner(System.in);
        Scanner fileLine = new Scanner(new File("ProviderDirectory.txt"));
        String response;
        String serviceName = "0";
        String serviceCode = "0";
        String serviceFee = "0";

        //Asks for code, double checks, spits error when code does not match.
        do {
            System.out.println("Enter service code: ");
            response = input.nextLine();

            while (fileLine.hasNextLine() && !response.equals(serviceCode)) {
                serviceName = fileLine.nextLine();
                serviceCode = fileLine.nextLine();
                serviceFee = fileLine.nextLine();
            }
            fileLine.close();
            fileLine = new Scanner(new File("ProviderDirectory.txt"));

            if (response.equals(serviceCode)) {
                System.out.println("Service: " + serviceName + "\nIs this code correct? ");
                response = input.nextLine();
            }
            else {
                System.out.println("The code you entered does not exist. Try again.");
            }
        } while(!response.toUpperCase().equals("YES"));

        //Assigns service information to report.
        report.serviceName = serviceName;
        report.serviceCode = serviceCode;
        report.fee = serviceFee;
    }

    //Helper function to get the member number. Used in the provider report creating activity.
    public String retrieveMemberNumber(Member memberList[], String name) {
        for (int i = 0; i < 100; ++i) {
            if (memberList[i] != null) {
                if (memberList[i].name.equals(name))
                    return memberList[i].number;
            }
            else
                ++i;
        }
        return "0";
    }

    //This shows the provider the provider directory, per assignment request. I cannot have
    //it emailed to the provider, so it is simply displayed on interface. Information is
    //loaded from an external text file and dispalyed.
    public void displayProviderDirectory() throws FileNotFoundException{

        //Necessary declarations
        Scanner input = new Scanner(new File("ProviderDirectory.txt"));
        String info;

        //Displays all information from the external file.
        while(input.hasNextLine()) {
            info = input.nextLine();
            System.out.println("Name of service: " + info);
            info = input.nextLine();
            System.out.println("Service number: " + info);
            info = input.nextLine();
            System.out.println("Fee of service: " + info);
        }
    }

    //The activity to add members to the list. Prompts user for information and spits error
    //when member list is already full.
    public void addMember(Member memberList[], int maxMembers) {

        //Necessary declaration
        int i = 0;

        //Looks for open spot in list.
        while (memberList[i] != null)
            ++i;

        //Creates new member or warns of full list.
        if (i < maxMembers){
            memberList[i] = new Member();
            memberList[i].editInfo();
        }
        else
            System.out.println("Member list is full. Cannot add new. Consult with manager.");
    }

    //The activity to remove an existing member. After member is remove, it is replace by the
    //last member in the list, so there is no holes in the list.
    public void removeMember(Member memberList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String response;
        String check;

        //Prompts for the member to be removed.
        do {
            System.out.println("Enter name of member to be removed: ");
            response = input.nextLine();
            System.out.println("Are you sure you want to remove " + response + "? (Yes/No) ");
            check = input.nextLine();
        } while (!check.toUpperCase().equals("YES"));

        //Searches for prompted member. Spits error if member cannot be found. Otherwise, stored an index.
        int a = 0;
        int b = 0;
        while (!memberList[a].name.equals(response)) {
            ++a;
            ++b;
            if (memberList[a] == null){
                System.out.println("Member does not exist.");
                return;
            }
        }
        while (memberList[b] != null)
            ++b;

        //Member is then deleted and replaced by a member at the end of the list.
        memberList[a] = memberList[b-1];
        memberList[b-1] = null;

    }

    //Function to validate a member. Asks for member to search for and changes
    //the information in the validation data member. (I did not realize I made a second
    //function doing the same thing. Too late to fix.)
    public void validateMember(Member memberList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String response;

        //Requests name of member.
        System.out.println("Enter name of member to validate: ");
        response = input.nextLine();

        //Searches and validates member. If member cannot be found, warning is spat.
        int i = 0;
        while (memberList[i] != null) {
            if (memberList[i].name.equals(response)){
                System.out.println("Member's current validation status: " + memberList[i].getValidation() +
                                    "\nEnter new information. If now valid, simply enter 'Yes'. ");
                response = input.nextLine();
                memberList[i].setValidation(response);
                return;
            }
            else
                ++i;
        }
        System.out.println("Member does not exist.");
    }

    //The activity that happens at the end of the week. Providers receives a report in their email
    //about all the services they have provided throughout the week and totals of fees and appointments.
    //I cannot email them so the information is simply displayed on terminal.
    public void weeklyReport(Provider providerList[], int providerIndex) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int totalDays = 0;
        int day = 0;
        int month = 0;
        int year = 0;
        int consultants = 0;
        int totalFee = 0;
        int j = 0;

        //Uses same algorithm as the manager terminal when sending members their reports.
        //Takes the requested date and turns it into one large number representing all days
        //of the year leading up to it. Compares with similar number in report information.
        System.out.println("Year of weekly report: ");
        year = input.nextInt();
        System.out.println("Month of weekly report: ");
        month = input.nextInt();
        System.out.println("Day of the last day for the week: ");
        day = input.nextInt();


        //Creates the total number of days.
        for (int count =  0; count <= month -1; ++count)
            totalDays += daysInMonth[count];
        totalDays += day;
        if (year/4 == 0)
            ++totalDays;

        //Sifts through information to find reports that match the request details.
        while (providerList[providerIndex].reportList[j] != null) {
            if (providerList[providerIndex].reportList[j].year == year) {
                for (int counter = 0; counter < 8; ++counter) {
                    if (providerList[providerIndex].reportList[j].totalDays + counter == totalDays) {
                        providerList[providerIndex].displayInfo();
                        providerList[providerIndex].reportList[j].weeklyProviderDisplay();
                        ++consultants;
                        totalFee += Integer.parseInt(providerList[providerIndex].reportList[j].fee);
                    }
                }
            }
            ++j;
        }
        //Prints the information that matches that which was requested.
        System.out.println("\nCTotal number of appointments: " + consultants + "\nTotal fee: $" + totalFee);
    }
}
