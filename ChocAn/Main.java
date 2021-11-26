import sun.plugin.perf.PluginRollup;

/**
 * Created by Tim on 11/30/2015.
 */

import java.io.*;
import java.util.Scanner;

/*
    This program is for ChocAn to use from their providers and manager. This is the
    raw code that lacks interface and that is reflected in the coding style. In the
    interface, the choices the providers and managers can choose from should be buttons
    rather than manual inputs. Forms that are to be filled out should look like forms
    with blank spaces to select and add in elements. That will work best for how this
    software has been programmed. Software was also written assuming that a server
    will always be on, meaning data will only have to be loaded from text files once.
 */

//Beginning of software
public class Main {

    /*Function that is required to pull members from the external text file. Used purely as testing
    purposes here because I cannot keep a server running indefinitely while working. The list of
    members included in the text file may be thrown away when software is put into use.*/
    public static void initializeMembers(String filename, Member memberList[], int maxMembers) throws FileNotFoundException{
        //Below are necessary declarations. input opens the file to read from. It is the member file.
        Scanner input = new Scanner(new File(filename));
        int index = 0;

        String name = null;
        String validation = null;
        String number = null;
        String streetAddress = null;
        String city = null;
        String state = null;
        int zipCode = 0;

        //While loop pulls information from the file, which is in order, to create a new member
        //and store it for use.
        while (input.hasNextLine()) {
            name = input.nextLine();
            validation = input.nextLine();
            number = input.nextLine();
            streetAddress = input.nextLine();
            city = input.nextLine();
            state = input.nextLine();
            zipCode = input.nextInt();
            input.nextLine();

            //New member is created here. Maximum members supported right now is 100.
            memberList[index] = new Member(name, validation, number, streetAddress, city, state, zipCode);
            ++index;
            if (index == maxMembers)
                return;
        }
    }

    /*Function that will initialize the mock providers. Like the member initializer, it will
    pull them from a text file and will be used solely for testing. They can be thrown away
    once software is put into use.*/
    public static void initializeProviders(String filename, Provider providerList[], int maxProviders) throws FileNotFoundException{
        //Necessary declarations
        Scanner input = new Scanner(new File(filename));
        int index = 0;

        String name = null;
        String number = null;
        String streetAddress = null;
        String city = null;
        String state = null;
        int zipCode = 0;

        //While loop pulls information from the file, which is in order, to create a new provider
        //and store it for use.
        while (input.hasNextLine()) {
            name = input.nextLine();
            number = input.nextLine();
            streetAddress = input.nextLine();
            city = input.nextLine();
            state = input.nextLine();
            zipCode = input.nextInt();
            input.nextLine();

            //New provider is created here. Maximum providers right now is 30.
            providerList[index] = new Provider(name, number, streetAddress, city, state, zipCode);
            ++index;
            if (index == maxProviders)
                return;
        }

    }

    /*This is where the software actually runs. It will start with creating all the data
    structures, including the members, providers, and terminals. Following that, user
    chooses which terminal to access, and control is transfered respectively.*/
    public static void main(String [] args) throws FileNotFoundException{
        //Necessary declarations
        int i = 0;
        String s;
        Scanner in = new Scanner(System.in);

        //Terminals being created.
        ProviderTerminal PTerm = new ProviderTerminal();
        ManagerTerminal MTerm = new ManagerTerminal();

        //Temporary static variables. Change to fit use as needed.
        final int maxMembers = 100;
        final int maxProviders = 30;
        final int limit = 1000;

        //Data structures being created.
        Member[] memberList = new Member[maxMembers];
        Provider[] providerList = new Provider[maxProviders];
        ProviderReport[] reportList = new ProviderReport[limit];
        ServiceReport[] serviceReportList = new ServiceReport[limit];

        //Filling member and provider data structures with mock data.
        initializeMembers("Members.txt", memberList, maxMembers);
        initializeProviders("Providers.txt", providerList, maxProviders);

        //Where the software will start running and taking input.
        do {

            System.out.println("Enter 1 for provider. Enter 2 for manager. Enter 3 to exit: ");
            i = in.nextInt();

            //If user chooses, the provider's terminal will run here.
            if ( i == 1){
                PTerm.startProgram(providerList, memberList, reportList, serviceReportList, maxMembers);
            }

            //If the user chooses, the manager's terminal will run here.
            if ( i == 2){
                MTerm.startProgram(providerList, memberList, reportList, serviceReportList, maxProviders);
            }

            //Software continually prompts user to log in until they explicitly log out/exit.
            } while(i != 3);

    }
}

