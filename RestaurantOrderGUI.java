/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bpreg
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;



/**
 * *Author Mary Tom
 * Created 15th April 2018 and last updated on 12 May 2018.
 * This class is modeled to have the necessary GUI components and event handling functions
 * for the user to use the application
 * This file also contains the data structure to load records from the file and the file processing 
 * Method.
 * The variable names are self explanatory.
 * It is good to add more comments.
 */
enum status { waiting, served, billed};
public class RestaurantOrderGUI extends JFrame{
   ArrayList<Customer> customerList;
   ArrayList<MenuItem> foodList;
   ArrayList<MenuItem> beverageList;
    
   
   private enum Status{ waiting, served, billed};
   
   private  JLabel lblCustomerName; // JLabel with just text
   private  JLabel lblTableNumner; 
   private  JLabel lblWeight;
   private  JLabel lblPwd;
   private JLabel lblFoodCombo;
   private  JLabel lblPalLevel;
   private  JLabel lblBeverageCombo;
   private JLabel lblNoofCustomers;
   private JLabel lblStatus;
   
   
   private  JTextField txtCustomerName; // text field with set size
   private  JTextField txtTableNumber; // text field with text
   private  JTextField txtWeight; // text field with text and size
  private JTextField txtNoofCustomers; //***
   private  JTextField txtPalLevel;
   private JTextField txtStatus; //***
   
   private  JComboBox<String> cmbFood;
   private  JComboBox<String> cmbBeverage;
   
   private JRadioButton BreakFast;//***
  private JRadioButton Lunch; //***
  private JRadioButton Dinner; //***
   
   private  final String[] initialComboValue  = {"Choose your Food MenuItem"}; 
   private  final String[] initialBeverageComboValue  = {"Choose your Beverage MenuItem"};
   private static final int FRAME_WIDTH = 1500;
   private static final int FRAME_HEIGHT = 600;
   private final int NUM_NUTRIENTS = 5;
   private final int MIN_TABLE_NUMBER =1;
   private final int MAX_TABLE_NUMBER = 8;

    private JTextArea displayArea;
    
    private JButton btnEnterData;
    private  JButton btnDisplayOrder;
    private  JButton btnDisplayChoices;
    private  JButton btnClear;
    private JButton btnPrepare;
    private JButton btnBill;
    private  JButton btnQuit;
   JPanel contentPanel; 
 
   public RestaurantOrderGUI()
   {
      customerList = new ArrayList();
      foodList = new ArrayList<>();
      beverageList = new ArrayList<>();
      contentPanel  = new JPanel();
       contentPanel.setLayout(new BorderLayout());
       contentPanel.setBorder( BorderFactory.createTitledBorder("Customer Order Choices")); 
       JPanel topPanel = new JPanel(new GridLayout(3,1));
       buildTopPanel(topPanel);
       contentPanel.add(topPanel, BorderLayout.NORTH);
       JPanel midPanel = new JPanel(); 
       buildMidPanel(midPanel);
       contentPanel.add(midPanel,BorderLayout.CENTER);
       JPanel bottomPanel = new JPanel (new GridLayout(1, 4));
       buildBottomPanel(bottomPanel);
       contentPanel.add(bottomPanel, BorderLayout.SOUTH);
       add (contentPanel);
       loadDataFromFile ();
       initComponents();
   }
   public void initComponents()
   {
      
       if (foodList.size() >0)
          for (MenuItem menu: foodList)
               cmbFood.addItem(menu.getMenuItemName());
       if (beverageList.size() >0)
          for (MenuItem menu: beverageList)
                cmbBeverage.addItem(menu.getMenuItemName());
       
        cmbFood.addItemListener(
            new ItemListener()
            {
               @Override
               public void itemStateChanged(ItemEvent event) {
                  if (event.getStateChange() == ItemEvent.SELECTED)
                  {   
                     String select =(String)cmbFood.getSelectedItem();
               }
            }
              
            });
        
            
         btnClear.addActionListener(
            new ActionListener()
            {
             @Override
            public void actionPerformed(ActionEvent event)
            {
               txtCustomerName.setText("");
                txtTableNumber.setText("");
               
                cmbFood.setSelectedIndex(0);
                cmbBeverage.setSelectedIndex(0);
               
                displayArea.setText("");
                displayArea.repaint();
            }   
            });
        /* btnPrepare.addActionListener(
            new ActionListener(){
            @override
            public void actionPerformed(ActionEvent event){
                enum status = ('waiting','served','billing');
                    txtStatus.set("served");
                
                        }
                        
            }
            }
         );*/
         /*
        btnBill.addActionListener(
        new ActionListener(){
        @override
        public void actionPerformed(ActionEvent event){
            txtStatus.set(" billed");
        }
        }
        )
        
        */
        
         btnEnterData.addActionListener(
             new ActionListener()
            {
             @Override
            public void actionPerformed(ActionEvent event)
            {
                   String name ="",
                       foodChoice = "",
                        beverageChoice = "";
                    MenuItem food = new MenuItem(),
                                    beverage = new MenuItem();
                    ArrayList<MenuItem> temp = new ArrayList<>();
                  int table =0;
                  
                   boolean userData = true;
              
                    
                //check that customer name, table number, food, beverage  are correctly entered/selected.
                  if (txtCustomerName.getText() == null || txtCustomerName.getText().equals(""))
                  {
                      btnPrepare.setVisible(false);
                            btnBill.setVisible(false);
                      displayArea.setText("Please enter Customer name");
                      userData = false;
                 } 
                else if  (txtTableNumber.getText() == null || txtTableNumber.getText().equals(""))
                {
                            //***
                            
                           displayArea.setText("Please enter Table Number");
                           userData = false;
                 } 
               
                else if  (cmbFood.getSelectedIndex() == 0)
                 {
                           displayArea.setText("Please select a Food Menu Item");
                           userData = false;
                 }
                else if ( cmbBeverage.getSelectedIndex()==0)
                 {
                            displayArea.setText("Please select Beverage Menu Item");
                            userData = false;
                 }
                if (userData) 
                {
                    //get user input
                   name =  txtCustomerName.getText();
                   table =  Integer.parseInt(txtTableNumber.getText());
                   foodChoice = (String) cmbFood.getSelectedItem();
                   beverageChoice = (String) cmbBeverage.getSelectedItem();
                   for (MenuItem menu: foodList)
                        if (menu.getMenuItemName().equalsIgnoreCase(foodChoice))
                           food =  menu;
                    for (MenuItem menu: beverageList)  
                           if (menu.getMenuItemName().equalsIgnoreCase(beverageChoice))
                           beverage = menu;
                    //create array List of selected items
                   temp.add(food);
                   temp.add(beverage);
                   try {
                      customerList.add(new OrderedCustomer(temp,name, table));
                  }
                   catch (Exception e) {
                      displayArea.setText(e.getMessage());
                    }
             
                }                       
              //  public class Chef extends JFrame{
                
                }
                 
                
         
       });
        btnDisplayChoices.addActionListener(
           new ActionListener()
           {
             @Override
            public void actionPerformed(ActionEvent event) {
               
               //get the index of the last added customer
                 int index =customerList.size() -1;
                
                 displayArea.setText(customerList.get(index).getCustomerName()); //displays customer details
                 displayArea.append(String.format ("\tTable Number:   %d", customerList.get(index).getTableNumber()));
                  //getTotalNutrition() method is only in the sublcass. So downcasting required
                 OrderedCustomer current = (OrderedCustomer)customerList.get(index); 
                 for (MenuItem menu: current.getMenuItems())
                       displayArea.append(menu.toString());
                 displayArea.append("\n ----------------------------------------------------------------------------------------------------");
                 displayArea.append(current.getTotalNutrition().toString());
            }
        });
        btnDisplayOrder.addActionListener(
           new ActionListener()
           {
             @Override
            public void actionPerformed(ActionEvent event) {
               
               //get the index of the last added customer
               int table ;   
                if  (txtTableNumber.getText() == null || txtTableNumber.getText().equals(""))
                {
                           displayArea.setText("Please enter Table Number");
                          // userData = false;
                 }
                 table =  Integer.parseInt(txtTableNumber.getText());
                 if  (table < MIN_TABLE_NUMBER || table > MAX_TABLE_NUMBER)
                     displayArea.setText("Table number should be between 1 and 8");
                 //it is required to use the subclass toString() So downcasting required
                 else{
                        displayArea.setText("");  //clear display area
                        displayArea.append(String.format("\n \n                                    Menu Items Ordered  "));
                        displayArea.append(String.format("\n -----------------------------------------------------------------------------------------------"));
                        for (int i = 0; i < customerList.size(); i++)
                        {
                              if (customerList.get(i).getTableNumber()== table) {
                                  //it is required to use the subclass toString() So downcasting required
                                  OrderedCustomer current = (OrderedCustomer)customerList.get(i); //returns a customer
                                  
                                  displayArea.append(current.toString());
                              }
                        }         
                 }
            }
        });
        
         btnQuit.addActionListener(
             new ActionListener()
            {
                  @Override
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }  
          });


   }
    public final  void buildTopPanel(JPanel aPanel)
    {
        lblCustomerName = new JLabel("Customer Name");
        txtCustomerName = new JTextField(25);
        lblTableNumner = new JLabel("Table Number");
        txtTableNumber = new JTextField(6);
        lblNoofCustomers = new JLabel("No of Persons");
        txtNoofCustomers = new JTextField(6);
        lblStatus = new JLabel("Status");
        txtStatus = new JTextField(10);
         ButtonGroup group= new ButtonGroup();
        JRadioButton BreakFast = new JRadioButton("BreakFast",true);
        JRadioButton Lunch = new JRadioButton("Lunch");
        JRadioButton Dinner = new JRadioButton("Dinner");
        
        group.add(BreakFast);
        group.add(Lunch);
        group.add(Dinner);
        
         lblFoodCombo = new JLabel( "Food Menu Item");
         cmbFood      = new JComboBox (initialComboValue);
         lblBeverageCombo = new JLabel("Beverage Menu Item");
         cmbBeverage = new JComboBox(initialBeverageComboValue);
         txtPalLevel =  new JTextField(8);
         
         txtStatus.setEditable(false);
         //txtStatus.addActionListener(prepare);
       
         JPanel RowOnePanel = new JPanel(new FlowLayout());
  
         JPanel RowTwoPanel = new JPanel(new FlowLayout());
         JPanel RowThreePanel = new JPanel(new FlowLayout()); //****
        
         RowOnePanel.add( lblCustomerName);
         RowOnePanel.add(txtCustomerName);
         RowOnePanel.add( lblTableNumner);
         RowOnePanel.add (txtTableNumber);
         RowOnePanel.add (lblNoofCustomers);
         RowOnePanel.add (txtNoofCustomers);  //***
         RowOnePanel.add(lblStatus);
         RowOnePanel.add(txtStatus);
         
         RowTwoPanel.add(BreakFast);
         RowTwoPanel.add(Lunch);//***
         RowTwoPanel.add(Dinner);
         
         RowThreePanel.add(lblFoodCombo);
         RowThreePanel.add(cmbFood);
         RowThreePanel.add( lblBeverageCombo);
         RowThreePanel.add(cmbBeverage);
       
         aPanel.add(RowOnePanel);
         aPanel.add(RowTwoPanel);
         aPanel.add(RowThreePanel);
    }
 
   public final void buildMidPanel(JPanel aPanel)
    {
      //text area to diaplay menu choices and nutrition information
        displayArea = new JTextArea(46,96);
        
        JScrollPane scrolPane = new JScrollPane(displayArea);
        displayArea.setText(" ");
        displayArea.setEditable(false);
        
        //add displayARea and scrollPane to Panel
        
        aPanel.add(scrolPane);
        
        aPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Order Details" ));
        
    }
    public final void buildBottomPanel(JPanel aPanel)
   {
         btnEnterData = new JButton("Enter Data");
         btnClear = new JButton("Clear Display");
         
         btnDisplayChoices = new JButton("Display Choices");
         btnQuit   = new JButton("Quit");
         btnDisplayOrder = new JButton("Display Order");
         btnPrepare =new JButton("Prepare");
         btnBill = new JButton("Bill");
        aPanel.add(btnEnterData);
        aPanel.add(btnDisplayChoices);
        aPanel.add(btnDisplayOrder);
        aPanel.add(btnPrepare);
        aPanel.add(btnBill);
        aPanel.add(btnClear);
        aPanel.add(btnQuit);
        aPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Command Buttons"));
    }

   public  void loadDataFromFile ()   
   {
     
     String fileName = "C://Users/Bhanu prasad/Desktop/Bhanu_Projects/Restaurant/Ass1Data.csv";
      int count =0,
           index =0;
      String line = "";
      
     
      String [] menuDetails = new String[20];
      String [] heading = new String[20];
    
      try
      {
         BufferedReader in = new BufferedReader (new FileReader(fileName));
         line = in.readLine();
        // System.out.println(line);
         while (line != null)
         {
           if (count == 0)
             heading = line.split(",");
           else {
               menuDetails = line.split(",");
            if (menuDetails[0].equalsIgnoreCase("food")) {
                //add a new menuItem with item name and menuItemId
                foodList.add(new MenuItem(menuDetails[1], Integer.parseInt(menuDetails[7])));
                //get the index of the currently added menuItem
                index = foodList.size()-1;
             
                //add all the nutrients with name from the heading and value from the currently read line.
               
                for (int i= 0; i<NUM_NUTRIENTS; i++)
                     foodList.get(index).getNutrientList().add(new Nutrient(heading[2+i], Double.parseDouble(menuDetails[2+i])));
             }
             else if (menuDetails[0].equalsIgnoreCase("beverage")) {
                        beverageList.add(new MenuItem(menuDetails[1], Integer.parseInt(menuDetails[7])));
                         //get the index of the currently added menuItem
                        index = beverageList.size()-1;
                        for (int i= 0; i<NUM_NUTRIENTS; i++)
                              beverageList.get(index).getNutrientList().add(new Nutrient(heading[2+i], Double.parseDouble(menuDetails[2+i])));
                    }
            
           }  
             line = in.readLine(); 
            
               count++;
               
         }
         in.close();  // close the file
      }
      //handle file loading and input realted exceptions
      catch (NoSuchElementException | IllegalStateException | IOException  ex)
      {
        System.out.println("file loading failed.");
        //System.exit (1);
      } 
 }
 public static void main (String[] args)
 {
     
     RestaurantOrderGUI display = new RestaurantOrderGUI();
     display.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //this part sets the frame in the center of the screen (optional)
			//gets dimension of the screen
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  int screenWidth = screenSize.width;
	  int screenHeight = screenSize.height;
          
          //locates upper-left corner (x,y) of centered frame
          int x = (screenWidth - display.getWidth())/2;
          int y = (screenHeight - display.getHeight())/2;
          
          //sets location
          display.setLocation(x,y);
          display.setVisible(true);
          display.setResizable(false);
        // loadDataFromFile () ;
          display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

    
   
 }
}

