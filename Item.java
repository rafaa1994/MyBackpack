/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnapsackProblem;

import java.util.Comparator;

/**
 *
 * @author Rafał Stępień
 */
public class Item {
    
    private double value;
    private double weight;
    
    
    
    public Item(double weight, double value){
    this.weight = weight;
    this.value = value;
}
  
     public double getRatio(){
     return value/weight;
     }
  
    public void setValue(double v){
        value = v;
    }
    public void setWeight(double w){
        weight = w;
    }
    
    public double getValue(){
       return value;
    }
    public double getWeight(){
        return weight;
    }
    
   
    public int compareTo(Item other){
    
        if(getRatio() < other.getRatio())
            return 1;
        else if (getRatio() > other.getRatio())
            return -1;
        else return 0;
    }
    
    
       public static Comparator<Item> byRatio() {
        return new Comparator<Item>() {
         public int compare(Item i1, Item i2) {
            return Double.compare(i2.getRatio(), i1.getRatio());
         }
      };
   }
    
}
