import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bpreg
 */
public class DatabaseUtility {

   
 // private ArrayList<MenuItem> foodList;
 // private ArrayList<MenuItem> beverageList;
 PreparedStatement statement=null;
 Connection con= null;
 ResultSet rs = null;
    
    String MenuTab = "Create Table IF NOT EXISTS MenuItem ("+
                      "MenuItemID int, MenuName varchar(50), PRIMARYKEY(MenuItemID) );";

    String NutrientTab = "Create Table IF NOT EXISTS Nutrient ("+
                        "NutrientID int, MenuItemID int, Value DOUBLE(20),NutrientName varchar(50),"+
                         "PRIMARYKEY(NutrientID,MenuItemID) ); "; 

   
                                  
    String OrderedTab = "Create Table IF NOT EXISTS OrderedCustomer ("+
                        "OrderID int(6) UNSIGNED AUTO_INCREMENT PRIMARYKEY,"+
                        "CustomerName varchar(255),"+
                        "OrderDate datetime,"+
                        "TableNum int,"+
                        "Status varchar(20) );";
                
    String OrderedMenuTab = "Create Table IF NOT EXISTS OrderedMenuItem ("+
                            "OrderID int,"+
                            "MenuItemID int,"+
                            "PRIMARYKEY(OrderID,MenuItemID) );";

    
    public DatabaseUtility() {

        try{
            Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql:localhost:3306/db","user","pwd");
             statement = con.prepareStatement(MenuTab);
            statement.execute();
            statement = con.prepareStatement(NutrientTab);
            statement.execute();
            statement = con.prepareStatement(OrderedTab);
            statement.execute();
            statement = con.prepareStatement(OrderedMenuTab);
            statement.execute();

        }
        catch(Exception e) {
            e.printStackTrace();
        }



    }


    public void fillData(ArrayList<MenuItem> foodList,ArrayList<MenuItem> beverageList) { //After Reading the CSV file Call it after Load Data from file methodin main

       fillQuery(foodList);
       fillQuery(beverageList);
      
    }

    private void fillQuery(ArrayList<MenuItem> temp) {
        
        try {
           
           for(int i=0;i<temp.size();i++) {
              MenuItem menuItem = temp.get(i);
              String itemName = menuItem.getMenuItemName();
              int id = menuItem.getMenuItemId();
              ArrayList<Nutrient> nutrientList = menuItem.getNutrientList();

              statement = con.prepareStatement("Insert into MenuItem (MenuItemID,MenuName) Values (?,?);");
              statement.setInt(1, id);
              statement.setString(2, itemName);
              statement.executeUpdate();

              for(int j=0;j<nutrientList.size();j++) {
                  Nutrient nutrient = nutrientList.get(i);
                  statement = con.prepareStatement("Insert into Nutrient (NutrientID,MenuItemID, Value,NutrientName) Values (?,?,?,?);");
                  statement.setInt(1,j);
                  statement.setInt(2,id);
                  statement.setDouble(3, nutrient.getQuantity());
                  statement.setString(4,nutrient.getNutrientName());

                  statement.executeUpdate();
              }
           }

           
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }
    }

    public void EnterData(String name,int table,String food,String beverage){  //for Enter Data Button arguments are cus name,tableno,fooditem,bev item

        int foodid = getMenuId(food);
        int bevid = getMenuId(beverage);
       
        int foodorder = getOrderId(name, table);

        setOrder(foodid,foodorder);
        setOrder(bevid,foodorder);
        

    }

    private int getMenuId(String item) {

        int id=-1;
        try {
            statement = con.prepareStatement("Select MenuItemID from MenuItem where MenuName = ?");
            statement.setString(1, item);

            rs = statement.executeQuery();
            if(rs.next()){
                id = rs.getInt("MenuItemID");
            }          
             
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        
        return id;
    }


    private int getOrderId(String name,int table) {
        
        int id = -1;

        try {
            statement = con.prepareStatement("Insert into OrderedCustomer (CustomerName,OrderDate,TableNum,Status) values (?,?,?,?)");
            java.util.Date utilDate = new java.util.Date();
            java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(utilDate);
    
            statement.setString(1, name);
            statement.setString(2, time);
            statement.setInt(3, table);
            statement.setString(4,"waiting");
            
            statement.executeUpdate();

            statement = con.prepareStatement("Select OrderID from OrderedCustomer where CustomerName = ? and OrderDate = ? and TableNum = ?");
            statement.setString(1, name);
            statement.setString(2, time);
            statement.setInt(3,table);

             rs = statement.executeQuery();

            if(rs.next()) {
                id = rs.getInt("OrderID");
            }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
    
        return id;
    }

    private void setOrder(int menuid,int orderid) {
        try {
            statement = con.prepareStatement("Insert into OrderedMenuItem (OrderID, MenuItemID) values (?,?)");
            statement.setInt(1,orderid);
            statement.setInt(2, menuid);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }

    }
    

    public String DisplayChoices() { //In display choices returns string which is to be displayed no arguments

        String s = "";
        int orderid=0;

        try{
            statement = con.prepareStatement("Select * from OrderedCustomer orderby OrderID desc LIMIT 0,1");
             rs = statement.executeQuery();

            if(rs.next()) {
                s+= "\nCustomer Name: " + rs.getString(1) + ",           Table Number:   " + rs.getString(3);
                orderid = rs.getInt(0);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        ArrayList<Integer> menuIds = getMenuIds(orderid);
        HashMap<String,Double> total = new HashMap<String,Double>();
        total.put("Energy(KJ)",new Double(0.0));
        total.put("Protein (g)",new Double(0.0));
        total.put("Carbohydrates with sugar alcohols (g)",new Double(0.0));
        total.put("Total fat (g)",new Double(0.0));
        total.put("Dietary fibre (g)",new Double(0.0));

        for(int i=0;i<menuIds.size();i++) {

            try {
                
                statement = con.prepareStatement("Select * from MenuItem where MenuItemID = ?");
                statement.setInt(1,menuIds.get(i));

                rs = statement.executeQuery();

                if(rs.next()) {
                    s+="\n \n Menu Item Name: \t"+rs.getString(1);
                }

                statement = con.prepareStatement("Select * from Nutrient where MenuItemID = ?");
                statement.setInt(1, menuIds.get(i));
                
                rs = statement.executeQuery();

                while(rs.next()){
                    s+=rs.getString(3)+": "+Double.toString(rs.getDouble(2));
                    Double d = total.get(rs.getString(3));
                    d+=rs.getDouble(2);
                    total.put(rs.getString(3),rs.getDouble(2));
                }


                s+="Total : ";
                for(HashMap.Entry<String,Double> entry : total.entrySet()) {
                    s+=entry.getKey()+" : "+entry.getValue();
                }


            } catch (Exception e) {
                //TODO: handle exception
            }
        }

        return s;
    }


    private ArrayList<Integer> getMenuIds(int orderid) {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try{
            statement = con.prepareStatement("Select * from OrderedMenuItem where OrderID = ?");
            statement.setInt(1,orderid);

            statement.executeQuery();

            while(rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
        catch(Exception e) {

        }

        return ids;
    }

    public String DisplayOrder(int table) { //in display orde with table number, returns string
        
        String s = "";

        try {
            statement = con.prepareStatement("Select * from OrderedCustomer where TableNum = ?");
            statement.setInt(1, table);

            rs = statement.executeQuery();

            while(rs.next()) {
                s+= "\nCustomer Name: " + rs.getString(1) + ",           Table Number:   " + rs.getString(3);
                int orderid = rs.getInt(0);
                s+=getMenuItems(orderid);
            }


        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }

        return s;
    }

    private String getMenuItems(int orderid) {
        String s="";
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {
            
            PreparedStatement stmt = con.prepareStatement("Select * from OrderedMenuItem where OrderID = ?");
            stmt.setInt(1, orderid);

            ResultSet res = stmt.executeQuery();

            while(res.next()) {
                ids.add(res.getInt(1));
            }

            for(int i = 0;i<ids.size();i++) {
                stmt = con.prepareStatement("Select * from MenuItem where MenuItemID = ?");
                stmt.setInt(1,ids.get(i));

                res = stmt.executeQuery();

                while(res.next()) {
                    s+="\n \n Menu Item Name: \t"+res.getString(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }

        return s;
    } 




    
    
    

    
    
  
}
