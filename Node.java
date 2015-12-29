/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnapsackProblem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafał Stępień
 */
   public class Node implements Comparable<Node> {
      
      public int h;
      List<Item> taken;
      public double bound;
      public double value;
      public double weight;
      
      public Node() {
         taken = new ArrayList<Item>();
      }
      
      public Node(Node parent) {
         h = parent.h + 1;
         taken = new ArrayList<Item>(parent.taken);
         bound = parent.bound;
         value = parent.value;
         weight = parent.weight;
      }
      
      
      public int compareTo(Node other) {
         return (int) (other.bound - bound);
      }
      
      public void computeBound(ArrayList<Item> items, int capacity) {
		  // indeks od którego zaczynamy przeliczanie będzie równy głowie węzła
         int i = h;                                     
         double w = weight;
         bound = value;
         Item item;
         do {
            item = items.get(i);
            if (w + item.getWeight() > capacity) break;
            w += item.getWeight();
            bound += item.getValue();
            i++;
         } while (i < items.size());
		 // doliczamy niepełną część przedmiotu który się nie zmieścił w całości
         bound += (capacity - w) * (item.getRatio());  
      }
   }