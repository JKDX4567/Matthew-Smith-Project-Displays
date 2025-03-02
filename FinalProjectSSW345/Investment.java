import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public abstract class Investment {
	protected double presentWorth;
    protected double annualGenerated;
    protected double annualInvested;
    protected int numberOfYears;
    protected double intrest;
    protected String investmentName;

    
    public Investment(double presentWorth, double annualGenerated, double annualInvested, int numberOfYears, double intrest, String investmentName){
        this.presentWorth = presentWorth;
        this.annualGenerated = annualGenerated;
        this.annualInvested = annualInvested;
        this.numberOfYears = numberOfYears;
        this.intrest = intrest;
        this.investmentName = investmentName;
    }

    public double getPresentWorth(){
        return this.presentWorth;
    }
    public double getAnnualGenerated(){
        return this.annualGenerated;
    }
    public double getAnnualInvested(){
        return this.annualInvested;
    }
    public int getNumberOfYears(){
        return this.numberOfYears;
    }
    public double getIntrest(){
        return this.intrest;
    }
    public String getInvestmentName(){
        return this.investmentName;
    }
    // get methods are used for comparisons
}
