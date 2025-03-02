import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class InvestmentManager{
    static Scanner input = new Scanner(System.in);
    static int decision = 1;
    // Used to read inputs, and decision is used to determine what to do (1 is default so it runs)

    public static void main(String[] args){
        Database theDatabase = Database.getInstance();
        while(decision != 0){
            System.out.println("What would you like to do? ");
            System.out.println("1: Add project ");	
            System.out.println("2: Add bank investment ");	
            System.out.println("3: See project database");
            System.out.println("4: See bank investment database");
            System.out.println("5: Add project with missing information ");	
            System.out.println("6: Add bank investment with missing information");	
            System.out.println("7: Compare projects");
            System.out.println("8: Compare bank investments");
            System.out.println("0: Im done for the day");
            decision = input.nextInt();
            if(decision == 1){
                System.out.println("Present Worth: ");
                double presentWorth = input.nextDouble();
                System.out.println("Anuall Generated: ");	
                double annualGenerated = input.nextDouble();
                System.out.println("Anuall costs: ");
                double annualInvested = input.nextDouble();
                System.out.println("Number of years ");
                int numberOfYears = input.nextInt();
                System.out.println("Intrest rate as decimal: ");
                double intrest = input.nextDouble();
                System.out.println("Salvage Value: ");
                double salvageValue = input.nextDouble();
                System.out.println("This is a private project (true or false): ");
                boolean isPrivate = input.nextBoolean();
                System.out.println("Investment name: ");
                input.nextLine();// Here becauase of a bug
                String investmentName = input.nextLine();
                ProjectInvestment tempProject  = new ProjectInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, salvageValue, isPrivate);
                theDatabase.addProject(tempProject);
                System.out.println(theDatabase);
                System.out.println(tempProject);
            }
            else if(decision == 2){
                System.out.println("Present Worth: ");
                double presentWorth = input.nextDouble();
                System.out.println("Anuall Generated: ");	
                double annualGenerated = input.nextDouble();
                System.out.println("Anuall costs: ");
                double annualInvested = input.nextDouble();
                System.out.println("Number of years ");
                int numberOfYears = input.nextInt();
                System.out.println("Intrest rate as decimal: ");
                double intrest = input.nextDouble();
                System.out.println("Future Worth: ");
                double futureWorth = input.nextDouble();
                System.out.println("Investment name: ");
                input.nextLine();// Here becauase of a bug
                String investmentName = input.nextLine();
                BankInvestment tempInvestment  = new BankInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, futureWorth);
                theDatabase.addInvestment(tempInvestment);
                System.out.println(theDatabase);
                System.out.println(tempInvestment);
            }
            else if(decision == 3){
                System.out.println(theDatabase.projectDatabaseToString());
                System.out.println("Would you like to see an individual project? ");
                System.out.println("1:Yes ");
                System.out.println("Other number:No ");
                decision = input.nextInt();
                if (decision == 1){
                    System.out.println("Which one? ");
                    input.nextLine();// Here becauase of a bug
                    String projectName = input.nextLine();
                    System.out.println(theDatabase.getProject(projectName));
                }
            }
            else if(decision == 4){
                System.out.println(theDatabase.investmentDatabaseToString());
                System.out.println("Would you like to see an individual investment? ");
                System.out.println("1:Yes ");
                System.out.println("Other number:No ");
                decision = input.nextInt();
                if (decision == 1){
                    System.out.println("Which one? ");
                    input.nextLine();// Here becauase of a bug
                    String investmentName = input.nextLine();
                    System.out.println(theDatabase.getInvestment(investmentName));
                }
            }
            else if(decision == 5){
                System.out.println("Which data is missing? ");
                System.out.println("1: Present Worth");
                System.out.println("2: Annual Generated");
                System.out.println("3: Annual Invested");
                System.out.println("4: Salvage Value");
                System.out.println("Other number: Go Back");
                decision = input.nextInt();
                if (decision == 1){ // Project type, wants to know present worth
                    double presentWorth = 0;
                    System.out.println("Anuall Generated: ");	
                    double annualGenerated = input.nextDouble();
                    System.out.println("Anuall costs: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Salvage Value: ");
                    double salvageValue = input.nextDouble();
                    System.out.println("This is a private project (true or false): ");
                    boolean isPrivate = input.nextBoolean();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information

                    double annualOutputPW = (annualGenerated - annualInvested)*(1-Math.pow(1+intrest, numberOfYears*-1)/intrest);
                    double salvageValuePW = salvageValue/(Math.pow(1+intrest, numberOfYears));
                    if(isPrivate){
                        presentWorth = annualOutputPW-salvageValuePW;
                    }
                    else{
                        presentWorth = annualOutputPW+salvageValuePW;
                    }
                    presentWorth = Math.round(presentWorth*100.0)/100.0;
                    System.out.println("Calculated data ");
                    ProjectInvestment tempProject  = new ProjectInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, salvageValue, isPrivate);
                    theDatabase.addProject(tempProject);
                }
                else if(decision == 2){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();	
                    double annualGenerated = 0;
                    System.out.println("Anuall costs: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Salvage Value: ");
                    double salvageValue = input.nextDouble();
                    System.out.println("This is a private project (true or false): ");
                    boolean isPrivate = input.nextBoolean();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information
                    if(isPrivate){
                        salvageValue = salvageValue * -1;
                    }
                    annualGenerated = (presentWorth-((salvageValue)/(Math.pow(1+intrest, numberOfYears)))/ ((1-(Math.pow(1+intrest, numberOfYears*-1)))/intrest)) + annualInvested;
                    if(isPrivate){
                        salvageValue = salvageValue * -1;
                    }
                    // The reason this is done twice is because for the math it has to be negative, but when its in the system it should be positive
                    annualGenerated = Math.round(annualGenerated*100.0)/100.0;
                    ProjectInvestment tempProject  = new ProjectInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, salvageValue, isPrivate);
                    theDatabase.addProject(tempProject);

                }
                else if(decision == 3){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();
                    System.out.println("Anuall Generated: ");		
                    double annualGenerated = input.nextDouble();
                    double annualInvested = 0;
                    System.out.println("Number of years: ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Salvage Value: ");
                    double salvageValue = input.nextDouble();
                    System.out.println("This is a private project (true or false): ");
                    boolean isPrivate = input.nextBoolean();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information
                    if(isPrivate){
                        salvageValue = salvageValue * -1;
                    }
                    annualInvested = ((presentWorth-((salvageValue)/(Math.pow(1+intrest, numberOfYears)))/ ((1-(Math.pow(1+intrest, numberOfYears*-1)))/intrest)) * -1) + annualGenerated;
                    if(isPrivate){
                        salvageValue = salvageValue * -1;
                    }
                    annualInvested = Math.round(annualInvested*100.0)/100.0;
                    // The reason this is done twice is because for the math it has to be negative, but when its in the system it should be positive
                    ProjectInvestment tempProject  = new ProjectInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, salvageValue, isPrivate);
                    theDatabase.addProject(tempProject);
                }
                else if(decision == 4){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();
                    System.out.println("Anuall Generated: ");		
                    double annualGenerated = input.nextDouble();
                    System.out.println("Anuall Costs: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years: ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as a decimal: ");
                    double intrest = input.nextDouble();
                    double salvageValue = 0;
                    System.out.println("This is a private project (true or false): ");
                    boolean isPrivate = input.nextBoolean();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information
                    
                    salvageValue = (presentWorth - (annualGenerated-annualInvested)*((1-Math.pow(1+intrest, numberOfYears*-1))/intrest))*(Math.pow(1+intrest,numberOfYears));
                    salvageValue = Math.round(salvageValue*100.0)/100.0;
                    ProjectInvestment tempProject  = new ProjectInvestment (presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, salvageValue, isPrivate);
                    theDatabase.addProject(tempProject);
                }
                else{
                    System.out.println("Canceling request, going back. ");
                }
                decision = 1;
            }
            else if(decision == 6){
                System.out.println("Which data is missing? ");
                System.out.println("1: Present Worth");
                System.out.println("2: Annual Generated");
                System.out.println("3: Annual Invested");
                System.out.println("4: Future Worth");
                System.out.println("Other number: Go Back");
                decision = input.nextInt();
                if(decision == 1){
                    double presentWorth = 0;
                    System.out.println("Anuall Generated: ");	
                    double annualGenerated = input.nextDouble();
                    System.out.println("Anuall Investment: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Future worth: ");
                    double futureWorth = input.nextDouble();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information

                    double PVG = annualGenerated * ((1-(Math.pow(1+intrest, numberOfYears*-1)))/intrest);
                    double PVF = futureWorth * (Math.pow(1+intrest, numberOfYears*-1));
                    double PVI = annualInvested * ((1-(Math.pow(1+intrest, numberOfYears*-1)))/intrest);
                    presentWorth = PVG + PVF -PVI;
                    presentWorth = Math.round(presentWorth*100.0)/100.0;
                    BankInvestment tempInvestment = new BankInvestment(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, futureWorth);
                    theDatabase.addInvestment(tempInvestment);
                }
                else if(decision == 2){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();	
                    double annualGenerated = 0;
                    System.out.println("Anuall Investment: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Future worth: ");
                    double futureWorth = input.nextDouble();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information

                    annualGenerated = (futureWorth - (presentWorth * (Math.pow(1+intrest, numberOfYears)))-(annualInvested*(((Math.pow(1+intrest, numberOfYears))-1)/intrest)))/((Math.pow(1+intrest, numberOfYears)-1)/intrest);
                    annualGenerated = Math.round(annualGenerated*100.0)/100.0;
                    BankInvestment tempInvestment = new BankInvestment(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, futureWorth);
                    theDatabase.addInvestment(tempInvestment);
                }
                else if(decision == 3){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();
                    System.out.println("Annual Generated: ");	
                    double annualGenerated = input.nextDouble();
                    double annualInvested = 0;
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    System.out.println("Future worth: ");
                    double futureWorth = input.nextDouble();
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information

                    annualInvested = (futureWorth - (presentWorth * (Math.pow(1+intrest, numberOfYears)))-(annualGenerated*(((Math.pow(1+intrest, numberOfYears))-1)/intrest)))/((Math.pow(1+intrest, numberOfYears)-1)/intrest);
                    annualInvested = Math.round(annualInvested*100.0)/100.0;
                    BankInvestment tempInvestment = new BankInvestment(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, futureWorth);
                    theDatabase.addInvestment(tempInvestment);
                }
                else if(decision == 4){
                    System.out.println("Present Worth: ");
                    double presentWorth = input.nextDouble();
                    System.out.println("Annual Generated: ");	
                    double annualGenerated = input.nextDouble();
                    System.out.println("Anuall Investment: ");
                    double annualInvested = input.nextDouble();
                    System.out.println("Number of years ");
                    int numberOfYears = input.nextInt();
                    System.out.println("Intrest rate as decimal: ");
                    double intrest = input.nextDouble();
                    double futureWorth = 0;
                    System.out.println("Investment name: ");
                    input.nextLine();// Here because of bug
                    String investmentName = input.nextLine();
                    //Get neccesary information

                    futureWorth = (presentWorth*Math.pow(1+intrest,numberOfYears)) + annualInvested*(((Math.pow(1+intrest, numberOfYears))-1)/intrest) + annualGenerated * (((Math.pow(1+intrest, numberOfYears))-1)/intrest);
                    futureWorth = Math.round(futureWorth*100.0)/100.0;
                    BankInvestment tempInvestment = new BankInvestment(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName, futureWorth);
                    theDatabase.addInvestment(tempInvestment);
                }
                decision = 1;
            }
            else if(decision == 7){
                System.out.println("Get name of first project: ");
                input.nextLine();// Here because of bug
                String investmentName = input.nextLine();
                ProjectInvestment testProject  = theDatabase.getProject(investmentName);
                System.out.println("Get name of second project: ");
                String investmentNameTwo = input.nextLine();
                ProjectInvestment testProjectTwo  = theDatabase.getProject(investmentNameTwo);
                ProjectComparator comparisonProject = new ProjectComparator(testProject, testProjectTwo);
                System.out.println(comparisonProject.findBetterProject().getInvestmentName() + " Is the better project ");
            }
            else if(decision == 8){
                System.out.println("Get name of first investment: ");
                input.nextLine();// Here because of bug
                String investmentName = input.nextLine();
                BankInvestment testInvestment  = theDatabase.getInvestment(investmentName);
                System.out.println("Get name of second project: ");
                String investmentNameTwo = input.nextLine();
                BankInvestment testInvestmentTwo  = theDatabase.getInvestment(investmentNameTwo);
                BankInvestmentComparator comparisonInvestment = new BankInvestmentComparator(testInvestment, testInvestmentTwo);
                System.out.println(comparisonInvestment.findBetterBankInvestment().getInvestmentName() + " Is the better investment ");
            }
            else if(decision == 0){
                System.out.println("Have a good day");
            }
            else{
                System.out.println("Invalid input, try again");
            }
        }

    }
}