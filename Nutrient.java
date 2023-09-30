/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bpreg

/**
 * Author Mary Tom
 *Date: 15th April 2018 
 * Version 1.
 * Written for Java 8
 * The Nutrient class is used to store the nutrient name and nutrient quantity.
 * This class is used in MenuItem for storing the nutrient details.
 * This class contains  default and  parameterised constructors, the accessor, mutator methods, and the toString() method.
 */
import java.util.ArrayList;

public class Nutrient {

	private String nutrientName;
	private double quantity;  // this can be in different units such as gm, kilo jules
	
      // There are no specified data validation required.
   
	public Nutrient(String nutrientName, double quantity) {
		
		this.nutrientName = nutrientName;
		this.quantity = quantity;
	}
	public String getNutrientName() {
		return nutrientName;
	}
	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
   
      @Override
     public  String toString () {
        return String.format(" %s: %.2f  ", this.getNutrientName(), this.getQuantity());
     }
	
}

