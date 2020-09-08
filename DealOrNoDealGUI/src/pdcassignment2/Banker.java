/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.util.List;

/**
 *
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class Banker 
{

    //-------------------------------------------------------------------------- initialized variable
    private int amount;
    
    //-------------------------------------------------------------------------- class constructor
    public Banker()
    {
        this.amount = amount;
    }

    //-------------------------------------------------------------------------- methods
    /**
     * calculates the amount of money the banker will offer the player
     * @param round
     */
    public void createOffer(int round, List<Briefcase> cases) 
    {
        int remainingCases = 0;
        int total = 0;
        for (int i = 0; i < cases.size(); i++) {
            if (cases.get(i).getIsRemoved() == false) {
                total += cases.get(i).getFaceValue();
                remainingCases += 1;
            }
        }
        this.amount = (total)/remainingCases * round/10;
    }

    /**
     * returns the amount of money the banker will offer the player
     *
     * @return amount
     */
    public int returnOffer() {
        return amount;
    }

}
