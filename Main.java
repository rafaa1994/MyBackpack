/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnapsackProblem;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Rafał Stępień
 */
public class Main {
    
   public static void main(String[] args)throws IOException{
       
       Timer timer = new Timer();
        Knapsack knapsack = new Knapsack();
        String option ="";
        double expo= 0;
        Scanner scanner = new Scanner(System.in);
        int capacity;
        double total=0;
        ArrayList<Double> times = new ArrayList<Double>();
        
    System.out.println("\nWitaj w programie\n");

    do{
            System.out.print("MENU:"
            + "\n1.Wczytaj dane z pliku"
            + "\n2.Wyswietl dane"
            + "\n3.Wykonaj algorytm plecakowy - zupełny"
            + "\n4.Wykonaj algorytm plecakowy - Branch and Bound"
            + "\n5.Wykonaj algorytm plecakowy - programowanie dynamiczne"
            + "\n6.Wykonaj algorytm FPTAS - oparty o algorytm programowania dynamicznego"
            + "\n7.Wykonaj losowe generowanie kolekcji"
            + "\n8.Zakoncz");
    System.out.println("\nOpcja: ");
    scanner = new Scanner(System.in);
    option = scanner.next();
    switch(option.charAt(0)){
        
        case '1':
            System.out.print("\nWprowadz nazwe pliku .txt\nNazwa: ");
            scanner = new Scanner(System.in);
            String nameFile = scanner.next();
            System.out.print("\n"+knapsack.readFromFile(nameFile)+"\n");
            break;
            
        case '2':
           
            System.out.print("\n"+knapsack.displayCollection()+"\n");
            break;
            
        case '3':
            //timer = new Timer();
            scanner = new Scanner(System.in);
            System.out.print("\nWprowadz pojemnosc plecaka\nPojemnosc: ");
            capacity = scanner.nextInt();
            //timer.start();
            System.out.print("\n"+knapsack.bruteForce(capacity)+"\n");
             //timer.end();
             //total = timer.getTotalTime();
             //System.out.print("\n"+total+"\n");
            break;
            
        case '4':
       
            scanner = new Scanner(System.in);
            System.out.print("\nWprowadz pojemnosc plecaka\nPojemnosc: ");
            capacity = scanner.nextInt();
           System.out.print("\n" + knapsack.branchAndBound(capacity)+"\n");

           break;
            
        case '5':
            timer = new Timer();
            System.out.print("\nWprowadz pojemnosc plecaka\nPojemnosc: ");
            scanner = new Scanner(System.in);
            capacity = scanner.nextInt();
            timer.start();
            System.out.print("\n" + knapsack.DynamicProgramming(capacity)+"\n");
            timer.end();
            total = timer.getTotalTime();
            total = (double)total/1000000.0;
            System.out.print("\n"+total+"ms\n");
            break;
            
        case '6':
            timer = new Timer();
            System.out.print("\nWprowadz pojemnosc plecaka\nPojemnosc: ");
            scanner = new Scanner(System.in);
            capacity = scanner.nextInt();
            System.out.print("\nWprowadz ograniczenie bledu\n(Warunek : 0 < E < 1 !)\nE: ");
            scanner = new Scanner(System.in);
            expo = scanner.nextDouble();
            timer.start();
            System.out.print("\n" + knapsack.fullyPolynomialApproximateTimeScheme(capacity,expo)+"\n");
            timer.end();
            total = (double)timer.getTotalTime();
            total = total/1000000.0;
            System.out.print("\n"+total+"ms\n");
            break;
            
        case '7':
            System.out.print("\nWprowadz ilosc przedmiotow: ");
            scanner = new Scanner(System.in);
            capacity = scanner.nextInt();
            knapsack.generateCollection(capacity);
            System.out.println("\nWygenerowano");
            break;
            
        case '8':
            System.out.println("\nGoodbye! ");
            break;
            
        default:
            break;
    
        }
    }while(option.charAt(0) != '8');
    
    
    
   }
}
