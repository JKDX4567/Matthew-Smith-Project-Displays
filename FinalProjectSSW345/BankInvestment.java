import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class BankInvestment extends Investment {
    double futureWorth;

    public BankInvestment(double presentWorth, double annualGenerated, double annualInvested, int numberOfYears, double intrest, String investmentName, double futureWorth){
        super(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName);
        this.futureWorth = futureWorth;
    }

    public String toString() {
        return "Project: " + this.investmentName + "\nPresent worth: " + String.valueOf(this.presentWorth) + "\nAnnual Generated: " + String.valueOf(this.annualGenerated) + "\nAnnual Invested: " + String.valueOf(this.annualInvested) + "\nNumber of years: " + String.valueOf(this.numberOfYears) + "\nIntrest: " + String.valueOf(this.intrest) + "\nFuture Worth: " + String.valueOf(this.futureWorth);
    }

    public double getFutureWorth(){
        return this.futureWorth;
    }
   //Used for calculations
    
}
