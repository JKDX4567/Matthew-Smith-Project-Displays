import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class ProjectInvestment extends Investment {
    double salvageValue;
    boolean isPrivate; 
    String sector;

    public ProjectInvestment(double presentWorth, double annualGenerated, double annualInvested, int numberOfYears, double intrest, String investmentName, double salvageValue, boolean isPrivate){
        super(presentWorth, annualGenerated, annualInvested, numberOfYears, intrest, investmentName);
        this.salvageValue = salvageValue;
        this.isPrivate = isPrivate;
        if(isPrivate){
            sector = "Private";
        }
        else{
            sector = "Public";
        }
    }
    public ProjectInvestment(Investment someIvestment){
        super(someIvestment.getPresentWorth(), someIvestment.getAnnualGenerated(), someIvestment.getAnnualInvested(), someIvestment.getNumberOfYears(), someIvestment.getIntrest(), someIvestment.getInvestmentName());
        this.salvageValue = 0;
        this.isPrivate = true;
        if(isPrivate){
            sector = "Private";
        }
        else{
            sector = "Public";
        }
    }

    public String toString() {
        return "Project: " + this.investmentName + "\nPresent worth: " + String.valueOf(this.presentWorth) + "\nAnnual Generated: " + String.valueOf(this.annualGenerated) + "\nAnnual Invested: " + String.valueOf(this.annualInvested) + "\nNumber of years: " + String.valueOf(this.numberOfYears) + "\nIntrest: " + String.valueOf(this.intrest) + "\nSalvage value: " + String.valueOf(this.salvageValue) + "\nSector: " + this.sector;
    }

    public double getSalvageValue(){
        return this.salvageValue;
    }
    public boolean getIsPrivate(){
        return this.isPrivate;
    }
   //Used for calculations
    
}
