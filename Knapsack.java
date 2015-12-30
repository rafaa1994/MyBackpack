/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnapsackProblem;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Rafał Stępień
 */
public class Knapsack {
    String Com ="";
    String solution ="";
    ArrayList<Item> collection = new ArrayList<Item>();
    
    public String readFromFile(String nameFile) {
    try{
      FileInputStream fstream = new FileInputStream(nameFile);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader read = new BufferedReader(new InputStreamReader(in));
      String line = "";
      
      boolean startReading = false;
      collection = new ArrayList<Item>();
      while((line = read.readLine()) != null){
          if(startReading == false) {startReading = true; continue;}
          
         String[] reading = line.split(" ");
         double [] data = {Double.parseDouble(reading[0]),Double.parseDouble(reading[1])};
         collection.add(new Item(data[0],data[1]));
          
                  
          
      }
   
      return Com = "Wczytano poprawnie";
    
    }catch(FileNotFoundException e){
    
        return Com = "Brak takiego pliku";
    
    }catch(IOException e){ return e.getMessage();  }
    
}
    
    public String branchAndBound(int capacity){
    
         
      
      Collections.sort(collection, Item.byRatio());
      
      Node best = new Node();      // węzeł z najlepszym rozwiązaniem
      Node root = new Node();      // węzeł korzenia drzewa
      root.computeBound(collection,capacity);
      
      PriorityQueue<Node> q = new PriorityQueue<Node>();
      q.offer(root);                // umieszczamy korzeń w kolejce
      
      while (!q.isEmpty()) {        // dopóki mamy węzły w kolejce
         Node node = q.poll();      // przypisanie topu z kolejki i usunięcie z kolejki
         
         if (node.bound > best.value && node.h <collection.size() - 1) {        // node.h mają numery głowy węzła +1 poprzednika, 
                                                                                //dlatego mogą wykroczyć poza rozmiar  tablicy
            
            Node with = new Node(node);
            Item item = collection.get(node.h);
            with.weight += item.getWeight();
            
            if (with.weight <= capacity) {
            
               with.taken.add(collection.get(node.h));
               with.value += item.getValue();
               with.computeBound(collection,capacity);
               
               if (with.value > best.value) {
                  best = with;
               }
               if (with.bound > best.value) {
                  q.offer(with);
               }
            }
            
            Node without = new Node(node);
            without.computeBound(collection,capacity);
            
            if (without.bound > best.value) {
               q.offer(without);
            }
         }
      }
      

      return prepareSolutionToDisplay(best.value,best.weight,best.taken);
        
    }
    
    
     public String bruteForce(int capacity){
      solution ="";
      
      Collections.sort(collection, Item.byRatio());
      
      List<Item>tmp = new ArrayList(best_permutation(collection,capacity));
      double tmpValue =getValue(tmp);
      double tmpWeight=getWeight(tmp); 
      
      return prepareSolutionToDisplay(tmpValue,tmpWeight, tmp);
      
    }
     

    private List<Item> best_permutation (List<Item> items,int capacity){
    
        List<Item> best_perm = new ArrayList();
        do {
            
        List<Item> temp = new ArrayList(computeList(items,capacity));
        if(getValue(best_perm) < getValue(temp) )
           best_perm = temp;
        
        } while(nextPermutation(items));
    return best_perm;
    }

    public static <T extends Comparable<? super T>> boolean nextPermutation(List<Item> array) {
		// szukamy największego elementu kolekcji
		int i = array.size() - 1;
		while (i > 0 && array.get(i - 1).compareTo(array.get(i)) >= 0)
			i--;
                // i jest teraz sufiksem
                
                // sprawdzamy czy to ostatnia permutacja
		if (i <= 0)
			return false;
		
                // array[i-1] jest teraz piwotem
                // szukamy elementu największego po prawej stronie piwota
		int j = array.size() - 1;
		while (array.get(j).compareTo(array.get(i - 1)) <= 0)
			j--;
                // teraz array[j], będzie nowym piwotem
                
                //zamienamy miejscami piwot z elementem tablicy
		Collections.swap(array, i - 1, j);
		
		// odwracamy kolejnośc elementów od sufiksu w prawo
		Collections.reverse(array.subList(i, array.size()));
		return true;
	}
    
    	
    
      public double getWeight(List<Item> items) {
      double weight = 0;
      for (Item item : items) {
         weight += item.getWeight();
      }
      return weight;
   }
   
   public double getValue(List<Item> items) {
      double value = 0;
      for (Item item : items) {
         value += item.getValue();
      }
      return value;
   }
       
    public String prepareSolutionToDisplay(Node a){
    solution = "\n";
    solution += "Najwyższa wartosc: " + a.value;
    solution += "\nUzyskana waga: " + a.weight +"\n";
    
    for (Item item : a.taken){
            solution += item.getWeight()+" "+ item.getValue() + "\n";
        }
    return solution;
    }
    
    public String prepareSolutionToDisplay(double V,double W, List<Item> t){
    solution="\n";
    
    solution += "Najwyższa wartosc: " + V;
    solution += "\nUzyskana waga: " + W +"\n";
    
    for (Item item : t){
            solution += item.getWeight()+" "+ item.getValue() + "\n";
        }
    
    return solution;
    }
    
    
    public String displayCollection(){
    
        String collectionInfo="\n";
        for (Item item : collection){
            collectionInfo += item.getWeight()+" "+ item.getValue() + "\n";
        }
        
    return collectionInfo;
    }
    
    public List<Item> computeList(List<Item> items, int capacity){
    
        List<Item> newOfPerm = new LinkedList<Item>();
        double weight=0;
        int i = 0;
        while(weight <= capacity || i <items.size()){
           weight += items.get(i).getWeight();
           if(weight <= capacity ){
            newOfPerm.add(new Item(items.get(i).getWeight(),items.get(i).getValue()));
            i++;
           }
           
           else return newOfPerm; 
            
        } 
    return newOfPerm;
    }
    
    public String DynamicProgramming(int capacity){
    
        solution = "";
        double [][] table = new double[collection.size()+1][capacity +1];
        double [][] Q = new double[collection.size()+1][capacity +1];
        for(int i = 0; i < collection.size() + 1;i++){
            for(int j = 0; j < capacity + 1;j++){
                 table[i][0] = 0.0;
                 table[0][j] = 0.0;
                 Q[i][0] = 0.0;
                 Q[0][j] = 0.0;
              }
        }
       
        for(int i = 1; i < collection.size() + 1;i++)
            for(int j = 1; j < capacity + 1;j++){
                
                if(j >= (int)collection.get(i-1).getWeight() && 
                    table[i-1][j] < table[i-1][j - (int)collection.get(i-1).getWeight()] + collection.get(i - 1).getValue()){
                    table[i][j] = table[i-1][j - (int)collection.get(i-1).getWeight()] + collection.get(i - 1).getValue(); 
                    Q[i][j] = i;
            }
                else{
                
                    table[i][j] = table[i-1][j];
                    Q[i][j] = Q[i-1][j];
                }
        }
        
        for(int i = 0; i < collection.size() + 1;i++,System.out.print("\n"))
            for(int j = 0; j < capacity + 1;j++){
                System.out.print(table[i][j] + "\t");
            }
        
        // szukamy rozwiązania w plecaku
        
        int i = collection.size();
        int j = capacity; 
        List<Item> tmp = new ArrayList();
 
        double tmpWeight = 0;
        double tmpValue = 0;
        while (i > 0 && j > 0 ){
        
            if (table[i][j] != table[i-1][j])
            {
                tmpWeight += collection.get(i-1).getWeight();
                tmpValue += collection.get(i-1).getValue();
                tmp.add(collection.get(i-1));
                j -= (int)collection.get(i-1).getWeight();
                i--;
                
            }
            else i--;
        
        }
        
            solution =  prepareSolutionToDisplay(tmpValue,tmpWeight, tmp);
        
        return solution;
    }
    
    public String pseudoPolynomialApproximateTimeScheme(int capacity){
    solution = "";
    
    double best_value = 0;
    double best_weight = 0;
    int value_p = 0 ;
    
    if(!collection.isEmpty()){
    
    // wydobywamy najwieksza wartosc z kolekcji przedmiotow
    for(int i=0;i<collection.size();i++){
    if (collection.get(i).getValue()>best_value)
        best_value = collection.get(i).getValue();
    }
    
    // okreslamy ograniczenie błędu względnego
    int limit = collection.size() * (int)best_value;
    System.out.println("Limit:" + limit + "\n");
    
    // tworzymy tabele wyniku
    double [][]table = new double[collection.size()+1][limit+1];
    
    for(int i = 0; i <= collection.size();i++){
            table[i][0] = 0;
        for(int j = 1 ; j <= limit ; j++)
            table[i][j] = 9999;
        }
    
    // algorytm PTAS
    
    do {
    value_p += 1;
        for(int i = 1; i <= collection.size() ; i++){
        
            Item item_p = collection.get(i-1);
            if(value_p - (int)item_p.getValue() < 0 || table[i-1][value_p - (int)item_p.getValue()] == 9999)
            table[i][value_p] = table[i-1][value_p];
          
            else  table[i][value_p] = minimum(table[i-1][value_p],table[i-1][value_p - (int)item_p.getValue()] + item_p.getWeight());
            if(table[i][value_p] <= capacity) {best_value = value_p; best_weight = table[i][value_p]; }
         
        }
        
    }while(value_p < limit);
    
        // szukamy rozwiązania w plecaku
        
        int i = collection.size();
        int j = (int)best_value; 
        List<Item> tmp = new ArrayList();
 
        while (i > 0 && j > 0 ){
        
            if (table[i][j] != table[i-1][j])
            {
                
                tmp.add(collection.get(i-1));
                j -= (int)collection.get(i-1).getValue();
                i--;
                
            }
            else i--;
        
        }
            
            solution =  prepareSolutionToDisplay(best_value,best_weight, tmp);
    
    }else solution = "\nBrak danych do przetworzenia";
    
    return solution;
    }

    public void generateCollection(int size){
        collection = new ArrayList<Item>();
        
        for (int i=0;i<size;i++){
        Random generator = new Random();
        Item tmp = new Item(Math.floor(generator.nextDouble()*29),Math.floor(generator.nextDouble()*100));
        collection.add(tmp);
        }
    }
    
    public double minimum(double v1, double v2){
 
    if (v1<v2) return v1;
    else return v2;
    
    }
    
}
