/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class Game 
{
    //-------------------------------------------------------------------------- initialized variables
    ArrayList<Briefcase> briefcases = new ArrayList();
    Banker banker = new Banker();
    
    private int casesRemaining;
    private int round;
    private int totalCases;
    private int thisRoundCases;
    
    private boolean isGameActive;
    private boolean acceptedOffer;
    private boolean dealPending;
    
    private final int[] listOfCases = {1,2,5,10,50,75,100,200,300,400,500,750,1000,5000,10000,25000,50000,75000,100000,200000,300000,400000,500000,750000,1000000};
    private final int[] casesPerRound = {7,6,5,4,2,1};
    
    //-------------------------------------------------------------------------- getters and setters
    public boolean getGameActive()
    {
        return isGameActive;
    }
    
    public boolean getDealPending()
    {
        return dealPending;
    }
    
    public boolean getOfferAccepted()
    {
        return acceptedOffer;
    }
    
    //-------------------------------------------------------------------------- class constructor
    public Game()
    {
        Banker banker = new Banker();
    }
    
    //-------------------------------------------------------------------------- methods
    /**
     * shuffles the cases for each new game
     */
    public void shuffleCases()
    {
        Random random = new Random();
        for(int i = 0; i <listOfCases.length; i++)
        {
            int pos = random.nextInt(listOfCases.length);
            int counter = listOfCases[i];
            listOfCases[i] = listOfCases[pos];
            listOfCases[pos] = counter;
        }
    }
    
    /**
     * sets up the cases for each new game
     * @return briefcase
     */
    public List<Briefcase> setupCases()
    {
        shuffleCases();
        for(int i = 0; i<listOfCases.length; i++)
        {
            briefcases.add(new Briefcase(listOfCases[i]));
            
        }
        //setting up all variables for use in further code
        this.totalCases = listOfCases.length;
        this.round = 1;
        this.casesRemaining = listOfCases.length;
        this.thisRoundCases = casesPerRound[0];
        this.isGameActive = true;
        this.dealPending = false;
        this.acceptedOffer = false;
        return briefcases;
                
    }
    
    /**
     * starts a new game and creates messages to display in the j text area
     * @return outputMessage
     */
    public String startGame()
    {
        String outputMessage;
        if(round == 1 && casesRemaining == totalCases)
        {
            outputMessage = "INSTRUCTIONS: "
                    + "\nPick a case to keep until the end, open up other cases to see how much money is in them."
                    + "\nThe Banker will offer you money as the remaining cases are opened."
                    + "\nThe aim of the game is to walk away with as much money as possible."
                    + "\nGood luck!!"
                    + "\n\n*** Starting the Game: Please Select Your Case! ***";
            //INSTRUCTIONS BRO
        }
        else if((casesRemaining == 18 || casesRemaining == 12 || casesRemaining == 7 || casesRemaining == 3) || (casesRemaining == 1) && (thisRoundCases == 0))
        {
            startDeal();
            outputMessage = "The Bankers offer is $" + banker.returnOffer() + ".00. Deal or No Deal?";
        }
        else if(casesRemaining == 0)
        {
            outputMessage = this.finalDeal();
        }
        else
        {
            if(thisRoundCases == 1)
            {
                outputMessage = "Please select " + Integer.toString(thisRoundCases) + " more case. ";
            }
            else
            {
                outputMessage = "Please select " + Integer.toString(thisRoundCases) + " cases. ";
            }
        }
        return outputMessage;
    }
    
    /**
     * starts a deal between the user and the banker
     */
    private void startDeal()
    {
        if(thisRoundCases == 0)
        {
            banker.createOffer(round, briefcases);
            this.round += 1;
            this.thisRoundCases = casesPerRound[round - 1];
            this.dealPending = true;
        }
    }
    
    public void checkCase(Briefcase briefcase)
    {
        if(casesRemaining == totalCases)
        {
            briefcase.setUserCase(true);
            briefcase.setRemoved(true);
            briefcase.setEnabled(false);
            this.casesRemaining -= 1;
            this.thisRoundCases -= 1;
        }
        
        else if(briefcase.getIsRemoved() == false)
        {
            briefcase.setRemoved(true);
            briefcase.setEnabled(false);
            this.casesRemaining -= 1;
            this.thisRoundCases -= 1;
        }
    }
    
    /**
     * method for when the deal has been accepted
     */
    public void deal()
    {
        this.dealPending = false;
        this.acceptedOffer = true;
        this.isGameActive = false;
        //finalDeal();
    }
    
    /**
     * method for when the deal has been declined
     */
    public void noDeal()
    {
        this.dealPending = false;
        this.acceptedOffer = false;
        this.isGameActive = true;      
    }
    
    /**
     * method for the user to input their name to be saved to the database
     */
    public String inputName()
    {
        Scanner scan = new Scanner(System.in);
        String userName = JOptionPane.showInputDialog(null, "Enter your username: ");
        return userName;
    }
   
    /**
     * method for when there are no more unopened cases left in the game
     * @return 
     */
    public String finalDeal()
    {
        
        int briefcaseValue = 0;
        String outputMessage;
        if(acceptedOffer == true || (casesRemaining == 0 && acceptedOffer == false))
        {
            for(int i = 0; i < briefcases.size(); i ++)
            {
                if(briefcases.get(i).getUserCase() == true)
                {
                    briefcaseValue = briefcases.get(i).getFaceValue();
                }
            }
        }
        if(briefcaseValue < banker.returnOffer())
        {
            if(acceptedOffer == true)
            {
                outputMessage = "Your case was worth $" + briefcaseValue + ".00. \nYou accepted the offer of $" + banker.returnOffer() + ".00. \nYOU WIN!";
            }
            else
            {
                outputMessage = "Your case was worth $" + briefcaseValue + ".00. \nYou did not accept the offer of $" + banker.returnOffer() + ".00. \nYOU LOSE!";
            }
        }
        else
        {
            if(acceptedOffer == true)
            {
                outputMessage = "Your case was worth $" + briefcaseValue + ".00. \nYou accepted the offer of $" + banker.returnOffer() + ".00. \nYOU LOSE!";
            }
            else
            {
                outputMessage = "Your case was worth $" + briefcaseValue + ".00. \nYou did not accept the offer of $" + banker.returnOffer() + ".00. \nYOU WIN!";
            }     
        }
        inputName();
        return outputMessage;
    }       
}
