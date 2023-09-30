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
 * author Mary Tom
 * This file forms part of the Assignment One Solution written for COIT20256 Term One 2018.
 * 
 * The Customer class is used to store the customer details of name and table number .
 * This class contains  default and  parameterised constructors, and the accessor, mutator methods.
 */

import java.util.ArrayList;

//Customer Class definition
public class Customer   {
	
	private String customerName; // Customer Name
	private int tableNumber = 1; // tableNumber
   
      private final int MIN_TABLE_NUMBER =1;
      private final int MAX_TABLE_NUMBER = 8;
      private final int MAX_NAME_LENGTH =20;
   public Customer() {
      customerName = "undefined";
      
   }
  /**
   * 
   * @param customerName
   * @param table 
   * The constructor  includes validation of data.
   * Customer's first name: Maximum width 20 characters.
   * Table number should be between 1 and 8
   */
   public Customer(String customerName, int table) {
      
      if (customerName.length() >MAX_NAME_LENGTH)
         throw new IllegalArgumentException("Customer name should be less that 20 characters");
      
      if (table < MIN_TABLE_NUMBER || table >MAX_TABLE_NUMBER)
          throw new IllegalArgumentException("Table number should be between 1 and 8");
      
      this.customerName = customerName;
      this.tableNumber = table;
   }
   
   /**
    * 
    * @param customerName 
    * The set methods also validates the input data
    */
   
	public void setCustomerName(String customerName) {
           
             if (customerName.length() >20)
                 throw new IllegalArgumentException("Customer name should be less that 20 characters");
		
             this.customerName = customerName;
	}
	public void setTableNumber(int tableNumber) {
      
              if (tableNumber < 1 || tableNumber > 8)
                 throw new IllegalArgumentException("Table number should be between 1 and 8");
              
		this.tableNumber = tableNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public int getTableNumber() {
		return tableNumber;
	}
	@Override
	public String toString() {
		return " \nCustomer Name: " + customerName + ",           Table Number:   " + tableNumber;
	}
	
	
	

}
