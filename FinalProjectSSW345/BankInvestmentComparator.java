import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class BankInvestmentComparator extends Comparator{
    BankInvestment investmentOne;
    BankInvestment investmentTwo;
    public BankInvestmentComparator(BankInvestment investmentOne, BankInvestment investmentTwo){
        super(investmentOne, investmentTwo);
        this.investmentOne = investmentOne;
        this.investmentTwo = investmentTwo;
    }
    public String getBankInvestmentOne(){
        return "Bank Investment one: " + this.investmentOne.toString();
    }
    public String getBankInvestmentTwo(){
        return "Bank Investment two: " + this.investmentTwo.toString();
    }
    
    public Investment findBetterBankInvestment(){
        double BCRatioOne;
        double BCRatioTwo;
        // Varibles used to determine which one is better
        double PW = this.investmentOne.getPresentWorth();
        double AG = this.investmentOne.getAnnualGenerated();
        double AI = this.investmentOne.getAnnualInvested();
        double FW = this.investmentOne.getFutureWorth();
        double I = this.investmentOne.getIntrest();
        int N = this.investmentOne.getNumberOfYears();
        // Variables used for investment one (Makes it easier to read the formulas)
        double PWT = this.investmentTwo.getPresentWorth();
        double AGT = this.investmentTwo.getAnnualGenerated();
        double AIT = this.investmentTwo.getAnnualInvested();
        double FWT = this.investmentTwo.getFutureWorth();
        double IT = this.investmentTwo.getIntrest();
        int NT = this.investmentTwo.getNumberOfYears();
        // Variables used for investment two (Makes it easier to read the formulas)
        double factor = (I* Math.pow(1 + I, N))/(Math.pow(1+I, N));
        double PTA = PW * factor;
        factor = I/(Math.pow(1+I, N));
        double FWTA = factor * FW;
        BCRatioOne = (AG + FWTA)/(PTA + AI);  
        
        // First, we convert the present worth and future worth values to AW using the factor, 
        // Then we solve for the benefit to cost ratio using the conventional formula
        factor = (IT* Math.pow(1 + IT, NT))/(Math.pow(1+IT, NT));
        double PTAT = PWT * factor;
        factor = IT/(Math.pow(1+IT, NT));
        double FWTAT = factor * FWT;
        BCRatioTwo = (AGT + FWTAT)/(PTAT + AIT);  
        // Do the same nut for the second investment (T denotes invesment Two)
        System.out.println(String.valueOf(BCRatioOne));
        System.out.println(String.valueOf(BCRatioTwo));
        if(BCRatioOne > BCRatioTwo){
            return investmentOne;
        }
        else if(BCRatioTwo > BCRatioOne){
            return investmentTwo;
        }
        else{
            System.out.println("They were tied");
            return investmentTwo;
        }
        // Determine which one is better using its benefit/cost ratio
    }

}