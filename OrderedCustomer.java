/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bpreg
 */

 /**
  * Filename: OrderedCustomer.java
  * Author Mary Tom
 *Date: 15th April 2018 
 * Version 1.
 * Written for Java SE 8
 * This is for storing the MenuItem name, and the five nutrient details of a MenuItem.
 * The nutrient details stored are: energy, protein, carbohydrate, total fat, dietary fiber.
 * This class contains two parameterised constructors, accessor, mutator methods, and a toString() method.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderedCustomer extends Customer{
	private ArrayList<MenuItem> menuItemList;
	
	
	// Constructor to initialize
	public OrderedCustomer(List<MenuItem> menuItems, String name, int tableNumber) throws Exception {
		super(name, tableNumber);
             menuItemList = new ArrayList<>();
		for (MenuItem menu: menuItems)
                   menuItemList.add(menu);
	}
   
      public OrderedCustomer( String name, int tableNumber) throws Exception {
         super(name, tableNumber);
      }

	// Method to calculate total nutrition of given menu items
	public MenuItem getTotalNutrition() {
          //create a menuItem with nutrient values of the first menuItem
             
		MenuItem total = new MenuItem("Total", this.menuItemList.get(0).getNutrientList(),0);
           //  System.out.println(total.toString());
		for (int i=1; i < this.menuItemList.size();i++)
                  for (int j=0; j< this.menuItemList.get(i).getNutrientList().size(); j++) {
			    total.getNutirentAtIndex(j).setQuantity(total.getNutirentAtIndex(j).getQuantity()+this.menuItemList.get(i).getNutirentAtIndex(j).getQuantity() );
                         
         }
	     
		return total;
	}

	// Getter of menu items
	public ArrayList<MenuItem> getMenuItems() {
		return menuItemList;
	}
      
	// Setter of menu items
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItemList = menuItems;
      }
   
      @Override 
      public String toString() {
         StringBuilder str = new StringBuilder();
         str.append("\n");
         str.append(super.toString());
         for (MenuItem menu: this.menuItemList)
            str.append(String.format("\n \n Menu Item Name: \t%20s ", menu.getMenuItemName()));
         return str.toString();
      }
}
   

