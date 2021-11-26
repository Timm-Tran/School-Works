/**
 * Created by Tim on 12/1/2015.
 */

/*
This is the managers terminal. Anything the manger needs to do can be done
from here. The manager cannot do all the things providers can do however,
as the manager is not on the the field. They do have access to things the
providers cannot do however. Manager is capable of adding/removing/editing
providers, sending out weekly reports to members, and viewing the summary
of the week from the providers. Can also view the service report.
 */
import java.util.Scanner;

public class ManagerTerminal {

    //The function that all other will be ran out of. The is the part of the software that gives
    //control to the manager.
    public void startProgram(Provider providerList[], Member memberList[], ProviderReport reportList[], ServiceReport serviceReportList[], int maxProviders) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String name = "Marvin";
        String password = "Martian";
        String response = "YES";
        int a = 0;
        String ident1 = "0";
        String ident2 = "0";

        //Below is the login process. Users can keep trying, but if they quit, software will exit.
        while (response.toUpperCase().equals("YES")){
            System.out.println("Name: ");
            ident1 = input.nextLine();
            System.out.println("Password: ");
            ident2 = input.nextLine();
            if (!ident1.equals(name) || !ident2.equals(password)) {
                System.out.println("Name or password is incorrect. Try again? ");
                response = input.nextLine();
                if (!response.toUpperCase().equals("YES"))
                    return;
            }
            else
                response = "NO";
        }

        //Here is where the manager chooses what they want to do. Each number has its
        //own corresponding activity. Manager continues to choose activities until
        //they request to exit.
        do {
            System.out.println("Please choose an action (Choose a number). " + "\n1. Add new provider. " +
                                "\n2. Remove provider. " + " \n3. Update provider. " +
                                "\n4. View weekly report. " + "\n5. Send members their report." +
                                "\n6. View service reports." + "\n7. Exit");

            a = input.nextInt();

            if (a == 1)
                addProvider(providerList, maxProviders);
            if (a == 2)
                removeProvider(providerList);
            if (a == 3)
                editProvider(providerList);
            if (a == 4)
                viewWeeklyReport(providerList, reportList);
            if (a == 5)
                sendMemberReports(memberList);
            if (a == 6)
                viewServiceReports(serviceReportList);
        } while (a != 7);
    }

    //The first activity of the manager, they can add new providers to the existing list.
    public void addProvider(Provider providerList[], int maxProviders) {
        //Only declaration, an index holder.
        int i = 0;

        //Loop searches for an open space in the array. If maximum number of providers
        //already exists, error is spat and function halts.
        while (providerList[i] != null) {
            ++i;
            if (i == maxProviders) {
                System.out.println("Already maximum number of providers.");
                return;
            }
        }

        //Creates and adds info to new provider.
        providerList[i] = new Provider();
        providerList[i].editInfo();

    }

    //Removes an existing provider from the list.
    public void removeProvider(Provider providerList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String response = "0";
        String check = "0";

        //Asks for name of provider and double checks.
        do {
            System.out.println("Enter name of member to be removed: ");
            response = input.nextLine();
            System.out.println("Are you sure you want to remove " + response + "? (Yes/No) ");
            check = input.nextLine();
        } while (!check.toUpperCase().equals("YES"));

        //Looks through list for provider. If found, the provider is deleted, and
        //the empty space is filled by the last provider in the list. If whole list
        //is traversed, and error will sound saying the provider does not exist.
        int a = 0;
        int b = 0;
        while (!providerList[a].name.equals(response)) {
            ++a;
            ++b;
            if (providerList[a] == null){
                System.out.println("Provider does not exist.");
                return;
            }
        }

        //Here is where the replacement happens.
        while (providerList[b] != null)
            ++b;

        providerList[a] = providerList[b-1];
        providerList[b-1] = null;

    }

    //Editing existing providers information. Ex. change in date, last name, etc.
    public void editProvider(Provider providerList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        String response = "0";
        int i = 0;

        //Prints all providers names
        while (providerList[i] != null) {
            System.out.println(providerList[i].name + "\n");
            ++i;
        }

        //Prompts user to choose a provider.
        System.out.println("Which provider would you like to update? ");
        response = input.nextLine();
        i = 0;
        //Takes response, finds, and edits the provider.
        while (providerList[i] != null) {
            if (providerList[i].name.equals(response)) {
                providerList[i].editInfo();
                return;
            }
            else
                ++i;
        }
        //Error spat if provider cannot be found.
        System.out.println("Provider does not exist.");

    }

    //Here is where the manager can view the weekly progress of the providers. Does as
    //assignment asks. Finds a provider that has done work, adds their totals, displays,
    //and adds to grant total.
    public void viewWeeklyReport(Provider providerList[], ProviderReport reportList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = 0;
        int month = 0;
        int year = 0;
        int totalDays = 0;
        int consultants = 0;
        int totalConsultants = 0;
        int fee = 0;
        int totalFee = 0;
        int providers = 0;
        int index = 0;
        int a = 0;

        //Since date of software use is undetermined, here the software requests up to which
        //date they want to see information. Info will be loaded 7 days prior to chosen date.
        System.out.println("Year of weekly report: ");
        year = input.nextInt();
        System.out.println("Month of weekly report: ");
        month = input.nextInt();
        System.out.println("Day of the last day for the week: ");
        day = input.nextInt();


        //Algorithm: I first convert the date into strictly days from the start of the year.
        //I then compare it to number I calculated using the same process stored in the reports.
        //If the report is dated within 7 days of the requested time, it is displayed. This process
        //is done easily because the calculated numbers are each a large number, rather than a date.
        for (int count =  0; count <= month -1; ++count)
            totalDays += daysInMonth[count];
        totalDays += day;
        if (year/4 == 0)
            ++totalDays;

        //Loops sift through providers, their reports, and dates to find ones matching
        //the request.
        while (providerList[index] != null) {
            while (providerList[index].reportList[a] != null) {
                if (providerList[index].reportList[a].year == year) {
                    for (int counter = 0; counter < 8; ++counter) {
                        if (providerList[index].reportList[a].totalDays + counter == totalDays) {
                            ++consultants;
                            ++totalConsultants;
                            fee += Integer.parseInt(providerList[index].reportList[a].fee);
                            totalFee += fee;
                        }
                    }
                }
                ++a;
            }
            //Information on each provider is displayed here.
            if (consultants != 0) {
                ++providers;
                System.out.println(providerList[index].name + "\nAppointments: " + consultants +
                        "\tTotal fee: $" + fee);
            }

            consultants = 0;
            fee = 0;
            a = 0;
            ++index;
        }

        //Information is displayed after gathered. Grand totals here.
        System.out.println("\nTotal number of providers active this week: " + providers +
                            "\nTotal number of appointments this week: " + totalConsultants +
                            "\nTotal fee due this week: $" + totalFee);
    }

    //Helper that did not quite work right.
    /*public int getDesiredDate() {
        Scanner input = new Scanner(System.in);
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = 0;
        int month = 0;
        int year = 0;
        int totalDays = 0;

        System.out.println("Year of weekly report: ");
        year = input.nextInt();
        System.out.println("Month of weekly report: ");
        month = input.nextInt();
        System.out.println("Day of the last day for the week: ");
        day = input.nextInt();


        for (int count =  0; count <= month -1; ++count)
            totalDays += daysInMonth[count];
        totalDays += day;
        if (year/4 == 0)
            ++totalDays;

        return totalDays;
    }*/

    //Manager can send reports to their members weekly. Uses a similar algorithm to the previous
    //function to search through reports. Because I cannot actually send this information out,
    //it is instead displayed for the manager to see.
    public void sendMemberReports(Member memberList[]) {

        //Necessary declarations
        Scanner input = new Scanner(System.in);
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = 0;
        int month = 0;
        int year = 0;
        int totalDays = 0;
        int index = 0;
        int a = 0;
        int check = 0;

        //Since date of software use is undetermined, here the software requests up to which
        //date they want to see information. Info will be loaded 7 days prior to chosen date.
        System.out.println("Year of weekly report: ");
        year = input.nextInt();
        System.out.println("Month of weekly report: ");
        month = input.nextInt();
        System.out.println("Day of the last day for the week: ");
        day = input.nextInt();


        //Algorithm is same as the one for providers. Different information is displayed however.
        for (int count =  0; count <= month -1; ++count)
            totalDays += daysInMonth[count];
        totalDays += day;
        if (year/4 == 0)
            ++totalDays;

        //Sifts through the information
        while (memberList[index] != null) {
            while (memberList[index].reportList[a] != null) {
                if (memberList[index].reportList[a].year == year) {
                    for (int counter = 0; counter < 8; ++counter) {
                        if (memberList[index].reportList[a].totalDays + counter == totalDays) {
                            if (check == 0) {
                                memberList[index].displayInfo();
                                ++check;
                            }
                            //Displays information
                            System.out.println("Service name: " + memberList[index].reportList[a].serviceName +
                                                "\nDate of service: " + memberList[index].reportList[a].month + "-" +
                                                memberList[index].reportList[a].day + "-" +
                                                memberList[index].reportList[a].year +
                                                "\nProvider name: " + memberList[index].reportList[a].provider);
                        }
                    }
                }
                ++a;
            }
            ++index;
            check = 0;
            a = 0;
        }
    }

    //Manager can view the service reports here.
    public void viewServiceReports(ServiceReport serviceReportList[]) {
        int i = 0;
        while (serviceReportList[i] != null) {
            serviceReportList[i].displayAll();
            ++i;
        }
    }
}
