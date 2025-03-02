import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Database{
    ArrayList<ProjectInvestment> projectList = new ArrayList<ProjectInvestment>();
    ArrayList<String> projectNameList = new ArrayList<String>();
    ArrayList<BankInvestment> investmentList = new ArrayList<BankInvestment>();
    ArrayList<String> investmentNameList = new ArrayList<String>();
    String database;

    public Database(String database){
        this.database = database;
    }
    public void addProject(ProjectInvestment investment){
        projectList.add(investment);
        projectNameList.add(investment.getInvestmentName());
    }
     public void addInvestment(BankInvestment investment){
        investmentList.add(investment);
        investmentNameList.add(investment.getInvestmentName());
    }
    public String investmentDatabaseToString() {
        
        return "DatabaseName: " + this.database + "\nNames: " + this.investmentNameList;
    }
     public String projectDatabaseToString() {
        
        return "DatabaseName: " + this.database + "\nNames: " + this.projectNameList;
    }

    public ProjectInvestment getProject(String investmentName){
        for (int i  = 0; i < projectNameList.size(); i++){
            if (projectNameList.get(i).equals(investmentName)){
                return projectList.get(i);
            }
        }
        
        System.out.println("Something went wrong");
        ProjectInvestment defaultInvestmentOne = new ProjectInvestment(0.0, 0.0, 0.0, 1, .1, "Default Project", 0, true);
        return defaultInvestmentOne;
    }
    public BankInvestment getInvestment(String investmentName){
        for (int i  = 0; i < investmentNameList.size(); i++){
            if (investmentNameList.get(i).equals(investmentName)){
                return investmentList.get(i);
            }
        }
        
        System.out.println("Something went wrong");
        BankInvestment defaultInvestmentTwo = new BankInvestment(0.0, 0.0, 0.0, 1, .1, "Default Investment", 0);
        return defaultInvestmentTwo;
        
    }

}