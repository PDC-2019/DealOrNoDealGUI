/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;

/**
 * 
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class Briefcase extends JButton
{
    //-------------------------------------------------------------------------- initialized variables
    private int amount;
    private int faceValue;
    private boolean removed;
    private boolean userCase;
    private NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

    //-------------------------------------------------------------------------- class constructor
    public Briefcase(int valueInCase)
    {
        super();
        faceValue = valueInCase;
        removed = false;
        userCase = false;
    }
    
    //-------------------------------------------------------------------------- getter and setter methods 
    /**
     * gets the value of faceValue
     * @return faceValue
     */
    public int getFaceValue()
    {
        return faceValue;
    }
    /**
     * sets the value of faceValue
     * @param faceValue 
     */
    public void setFaceValue(int faceValue)
    {
        this.faceValue = faceValue;
    }
    
    /**
     * gets the value of amount
     * @return amount
     */
    public int getAmount()
    {
        return this.amount;
    }
    /**
     * sets the value of amount 
     * @param amount 
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
    
    /**
     * gets the users selected case
     * @return userCase
     */
    public boolean getUserCase()
    {
        return userCase;
    }
    /**
     * sets the users selected case
     * @param isUserCase 
     */
    public void setUserCase(boolean isUserCase)
    {
        userCase = isUserCase;
    }
    
    /**
     * gets if the case has been selected removed from the game
     * @return removed
     */
    public boolean getIsRemoved()
    {
        return removed;
    }
    /**
     * sets the selected user case to be removed from the game
     * @param isRemoved 
     */
    public void setRemoved(boolean isRemoved)
    {
        removed = isRemoved;
        if(removed == true)
        {
            if(userCase == true)
            {
                this.setText("<< This is your Case >>");
            }
            else
            {
                this.setText(format.format(faceValue));
            }
        }  
    }  
}
