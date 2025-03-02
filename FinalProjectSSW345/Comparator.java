import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public abstract class Comparator {
    Investment investmentOneP;
    Investment investmentTwoP;

    public Comparator(Investment investmentOne, Investment investmentTwo){
        this.investmentOneP = investmentOne;
        this.investmentTwoP = investmentTwo;
    }

    public String getInvestmentOne(){
        return "Investmet one: " + this.investmentOneP.toString();
    }
    public String getInvestmentTwo(){
        return "Investmet two: " + this.investmentTwoP.toString();
    }



}