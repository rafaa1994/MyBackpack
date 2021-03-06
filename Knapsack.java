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
    ArrayList<Item> collection = new ArrayList();
    
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
      
      PriorityQueue<Node> q = new PriorityQueue();
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
    
    public String prepareSolutionToDisplay2(double V,double V2,double W, List<Item> t,List<Item> t2){
    solution="\n";
    
    solution += "Najwyższa wartosc: " + V + "\nNajwyższa wartosc po skalowaniu: " + V2;
    solution += "\nUzyskana waga: " + W +"\n";
    
    for (int i =0; i<t.size();i++){
            solution += t.get(i).getWeight()+" "+ t.get(i).getValue() + "\t" + t2.get(i).getWeight()+" "+ t2.get(i).getValue() +"\n";
        }
    
    return solution;
    }
    
    public String displayCollection(){
    
        String collectionInfo="\n";
        for(Item item : collection){
            collectionInfo += item.getWeight()+" "+ item.getValue() + "\n";
        }
        
    return collectionInfo;
    }
    
    public List<Item> computeList(List<Item> items, int capacity){
    
        List<Item> newOfPerm = new LinkedList();
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
        
      //  for(int i = 0; i < collection.size() + 1;i++,System.out.print("\n"))
       //     for(int j = 0; j < capacity + 1;j++){
        //        System.out.print(table[i][j] + "\t");
       //     }
        
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
    
    public String fullyPolynomialApproximateTimeScheme(int capacity, double expo){
    solution = "";
    
    double best_value = 0;
    double tmp_best_value = 0;
    double tmp_value = 0;
    double best_weight = 0;
    int value_p = 0 ;
    
    if(!collection.isEmpty()){
    
    // kolekcja tymczasowa
       
      List<Item> tmp = new ArrayList(); 
        
    // wydobywamy najwieksza wartosc z kolekcji przedmiotow
    for(int i=0;i<collection.size();i++){
    if (collection.get(i).getValue()>best_value)
        best_value = collection.get(i).getValue();
    }
    
    // okreslamy skalar listy
    double scale = (expo * best_value) / collection.size();
    
    System.out.println("Skalar: " + scale + "\n");
    
    // skalujemy kolekcje i zawartosc dodajemy do tymczasowej
    for(int i=0;i<collection.size();i++){
        tmp_value  = Math.floor(collection.get(i).getValue() / scale);
        tmp.add(new Item(collection.get(i).getWeight(), tmp_value));
        if(tmp_value > tmp_best_value) tmp_best_value = tmp_value;
    }
    
    //System.out.println("collection\ttmp" );
    //for(int i=0;i<collection.size();i++){
    //System.out.println(collection.get(i).getValue() +"\t"+ tmp.get(i).getValue());
    //}
    
    // Ustalamy wielkości tablicy według wzoru (n * tmp_best_value)
    int limit = (tmp.size() * (int)tmp_best_value);
     System.out.println("Limit: " + limit + "\n");
    // tworzymy tabele wyniku
    double [][]table = new double[tmp.size()+1][limit+1];
    
    for(int i = 0; i <= collection.size();i++){
            table[i][0] = 0;
        for(int j = 1 ; j <= limit ; j++)
            table[i][j] = 9999;
        }
    
    // algorytm FPTAS
    
    do {
    value_p += 1;
        for(int i = 1; i <= tmp.size() ; i++){
        
            Item item_p = tmp.get(i-1);
            if(value_p - (int)item_p.getValue() < 0 || table[i-1][value_p - (int)item_p.getValue()] == 9999)
            table[i][value_p] = table[i-1][value_p];
          
            else  table[i][value_p] = minimum(table[i-1][value_p],table[i-1][value_p - (int)item_p.getValue()] + item_p.getWeight());
            if(table[i][value_p] <= capacity) {best_value = value_p; best_weight = table[i][value_p]; }
         
        }
        
    }while(value_p < limit);
    
        // szukamy rozwiązania w plecaku
        
        int i = collection.size();
        int j = (int)best_value; 
        
        //tmp2 dla przedmiotów przeskalowanych - opcjonalnie
       // List<Item>tmp2 = new ArrayList();
        List<Item>tmp3 = new ArrayList();
 
        while (i > 0 && j > 0 ){
        
            if (table[i][j] != table[i-1][j])
            {
                
                //tmp2.add(tmp.get(i-1));
                tmp3.add(collection.get(i-1));
                j -= (int)tmp.get(i-1).getValue();
                i--;
                
            }
            else i--;
        
        }
            
        
       // for(int ix=0;ix<collection.size()+1;ix++,System.out.println(ix + ". "))
       //      for(int jx=0;jx<limit;jx++,System.out.print("\t")){
       //      System.out.print(table[ix][jx]);
       //      }
        
        // w tym momencie interesuje nas najlepsza wartość w plecaku przedmiotów PRZED skalowaniem
            double best_value2 = getValue(tmp3);
        
            // wyświetlenie zawartości przeskalowanych oraz pierwotnych w celu porównania
            //solution =  prepareSolutionToDisplay2(best_value2,best_value,best_weight, tmp3,tmp2);
            
            // Wynik algorytmu
            solution =  prepareSolutionToDisplay(best_value2,best_weight, tmp3);
    
    }else solution = "\nBrak danych do przetworzenia";
    
    return solution;
    }

    public void generateCollection(int size){
        collection = new ArrayList();
        
        for (int i=0;i<size;i++){
        Random generator = new Random();
        Item tmp = new Item(Math.floor(generator.nextDouble()*29),Math.floor(generator.nextDouble()*100));
        if(tmp.getValue() == 0.0 || tmp.getWeight() == 0.0) i--;
        else collection.add(tmp);
        }
    }
    
    public double minimum(double v1, double v2){
 
    if (v1<v2) return v1;
    else return v2;
    
    }
    
}
