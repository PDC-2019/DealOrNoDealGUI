/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class GameFrame extends JFrame
{
    //-------------------------------------------------------------------------- initialized variables
    private Game game;
    private List<Briefcase> listOfCases;
    private ArrayList<String> scores;
    
    JPanel displayPanel = new JPanel();
    JTextArea status = new JTextArea();
    JPanel briefcasePanel = new JPanel();
    JButton dealButton = new JButton("Deal");
    JButton noDealButton = new JButton("No Deal");
    JButton recordsButton =  new JButton("Scoreboard");
    JPanel dealerInput = new JPanel();
    JTextArea playerName = new JTextArea();
    
    Connection conn = null; //initialises a connection object to establish a connection with the database
    String url = "jdbc:derby:DONDDB;create=true"; 
    String username = "play";  //the DB username
    String password = "play";   //the DB password
    String command = "SELECT * FROM SCOREBOARD";
    Statement statement = null; //a statement is an interface that represents an SQL statement
    ResultSet rs;
    
    //-------------------------------------------------------------------------- class consructor
    /**
     * method to initialize components for the gui
     */
    public GameFrame()
    {
        game = new Game();
        listOfCases = game.setupCases();
        scores = new ArrayList<>();
        
        //GUI BUILDING
        this.setTitle("Deal or No Deal");
        Box DisplayBox = Box.createVerticalBox();
        status.setText(game.startGame());
        
        briefcasePanel.setLayout(new GridLayout(5,5));
        
        for(int i = 0; i <listOfCases.size(); i++)
        {
            briefcasePanel.add(listOfCases.get(i));
            listOfCases.get(i).addMouseListener(new MouseWatcher());
        }
        
        dealerInput.add(dealButton);
        dealerInput.add(noDealButton);
        dealerInput.add(recordsButton);
        dealButton.addActionListener(new ButtonHandler());
        dealButton.setEnabled(false);
        noDealButton.addActionListener(new ButtonHandler());
        noDealButton.setEnabled(false);
        recordsButton.addActionListener(new ButtonHandler());
        recordsButton.setEnabled(true);
        
        DisplayBox.add(Box.createVerticalStrut(5));
        DisplayBox.add(status);
        status.setLineWrap(true);
        status.setMaximumSize(new Dimension(1000,300));
        DisplayBox.add(briefcasePanel);
        DisplayBox.add(dealerInput);
        dealerInput.setMaximumSize(dealerInput.getPreferredSize());
        
        this.add(DisplayBox);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); 
        
        this.recordsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("Button pressed!");
                try {
                    try {
                        connectDealOrNoDealDB();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    scores.add("*** HIGH SCORES ***\n");
                    printRecords(); 
                    JOptionPane.showMessageDialog(displayPanel, Arrays.toString(scores.toArray()).replace(",", "\n").replace("[", "").replace("]", ""));
                    scores.clear();
                } catch (SQLException ex) {
                   System.err.println("SQLException: " + ex.getMessage());
                }
            }
        });
    }
    
    //-------------------------------------------------------------------------- class methods
    /**
     * start method to determine if the game is active or not
     */
    public void start()
    {
        if(game.getGameActive() == true)
        {

            status.setText(game.startGame());
            if(game.getDealPending() == true)
            {
                dealButton.setEnabled(true);
                noDealButton.setEnabled(true);
            }
            else
            {
                dealButton.setEnabled(false);
                noDealButton.setEnabled(false);
            }
        }
        else
        {
            status.setText(game.finalDeal());
            dealButton.setEnabled(false);
            noDealButton.setEnabled(false);
        }
    }
    
    /**
     * method for if the game is active and no deal is in progress
     * the briefcases are able to be clicked
     */
    private class MouseWatcher extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Object source = e.getSource();
            if(source instanceof Briefcase)
            {
                if((game.getDealPending() == false) && game.getGameActive() == true)
                {
                    game.checkCase((Briefcase) source);
                    GameFrame.this.start();
                }
            }
        }
    }
    
    /**
     * method for when the deal or no deal buttons are clicked
     * calls the appropriate methods from the game class
     */
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == dealButton)
            {
                game.deal();
                GameFrame.this.start();
            }
            else if(event.getSource() == noDealButton)
            {
                game.noDeal();
                GameFrame.this.start();
                status.setText("Select another case to continue the game!");
            }

        }
    }
    
    /**
     * method to establish a connection with the database driver
     */
    public void connectDealOrNoDealDB() throws ClassNotFoundException 
    {
        try 
        {
            Scanner scan = new Scanner(System.in);
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.conn = DriverManager.getConnection(this.url, this.username, this.password); //connect with the driver manager
            System.out.println("\n"+url + " is now connected."); //outputs if connected to console
            statement = conn.createStatement(); //create a statement object that can accept and execute a string that is an SQL statement   
        } 
        catch (SQLException ex) 
        {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
    
    /**
     * method for saving the users name input to the database
     * @throws SQLException 
     */
    public void inputRecords() throws SQLException
    {

            
            //creates the database table name SCOREBOARD with columns PLAYERNAME and MONEY
            statement.addBatch("CREATE TABLE APP.SCOREBOARD(PLAYERNAME VARCHAR(30), MONEY INT");
            Game games = new Game();
            Banker banker = new Banker();
            String name = games.inputName();
            int money = banker.returnOffer();
            
            //commands for saving values to the database
            String sql = "INSERT INTO APP.SCOREBOARD(PLAYERNAME, MONEY)" + "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString (1, name);
            ps.setDouble (2, money);

            ps.execute();
            conn.close(); //closes the connection
    }
    
    /**
     * 
     * method for retrieving and printing the information from the database and
     * printing it to the gui dialogue box and the console
     * the method also contains our attempt of the idiom pattern
     *
     * @throws SQLException
     */
    public void printRecords() throws SQLException 
    {
        System.out.println("\n*** HIGH SCORES ***");
        String command = "SELECT * FROM APP.SCOREBOARD";
        this.rs = statement.executeQuery(command); 
        //OUR USE OF AN IDIOMATIC PATTERN
        while (rs.next()) 
        {
            String username = rs.getString(1);
            int moneyamount = (int) rs.getDouble(2);
            scores.add(username + ": $" + Integer.toString(moneyamount)+".00");
            System.out.println(username + ": $" + moneyamount+".00");
        }
    }
    
    /**
     * main driver class
     * @param args 
     */
    public static void main(String args[]) throws ClassNotFoundException
    {
        GameFrame newgame = new GameFrame();
        Console.run(newgame, 1000, 750);
        newgame.connectDealOrNoDealDB();
    }  
}
