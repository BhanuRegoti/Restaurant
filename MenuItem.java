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
  * Author Mary Tom
 *Date: 15th April 2018 
 * Version 1.
 * Written for Java SE 8
 * This is for storing the MenuItem name, and the five nutrient details of a MenuItem.
 * The nutrient details stored are: energy, protein, carbohydrate, total fat, dietary fiber.
 * This class contains two parameterised constructors, a copy constructor, accessor, mutao methods, and a toString() method.
 */
import java.util.ArrayList;

public class MenuItem {
	
	private String menuItemName;
	private ArrayList<Nutrient> nutrientList;
      private int menuItemId;
	
	public MenuItem(String menuItemName, ArrayList<Nutrient> nutrientList, int id) {
		
		this.menuItemName = menuItemName;
             this.nutrientList = new ArrayList<>();
		this.nutrientList = nutrientList;
             this.menuItemId = id;
	}
       //constructor to create a menuItem without setting the nutrient list
      public MenuItem(String menuItemName, int id) {
		this.menuItemName = menuItemName;
             this.menuItemId = id;
             nutrientList = new ArrayList<>();
	}
	//This method allocates memory copies all th elements from another menuItem.
      public MenuItem (MenuItem another) {
         this.menuItemName = another.menuItemName;
         this.menuItemId = another.menuItemId;
         this.nutrientList = new ArrayList<>();
         for (Nutrient n: another.getNutrientList())
              this.nutrientList.add(n);
      }
      
      public MenuItem () {
         this.menuItemName = "undefined";
         this.menuItemId =0;
         this.nutrientList = null;
      }
	
	public String getMenuItemName() {
		return menuItemName;
	}
	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
      }
      public int getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
       // return the NutrientList
	public ArrayList<Nutrient> getNutrientList() {
		return nutrientList;
	}
   
      // this can be used get one Nutirent at a given index
      public Nutrient getNutirentAtIndex( int index) {
         
            return nutrientList.get(index);
      }
       // set the nutrient list to some other list
	public void setNutrientList(ArrayList<Nutrient> nutrientList) {
		this.nutrientList = nutrientList;
	}
	
      // a nutrient at an index can be set to another nutrient.
      public void SetNutrientAtIndex (Nutrient nutrient, int index) {
       
         this.nutrientList.set(index, nutrient);
      }
	
	@Override
	public String toString() {
		return String.format("\n\n %-55s", this.menuItemName) + "  " + nutrientList ;
	}
	
	
	
	

}

