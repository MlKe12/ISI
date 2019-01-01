import java.util.Set;

import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import java.util.Iterator;

import JFSM.*;
import JFSM.Transducteur.*;

import java.util.Set;

import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import java.util.Map;
import java.util.HashMap;

import java.util.Iterator;

import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;

public class Programme2 {
    public static void main(String argv []) throws JFSMException {
    	Set<String> A1 = new HashSet<String>();      
        A1.add("a");A1.add("b");

        Set<Etat> Q1 = new HashSet<Etat>();
        Q1.add(new Etat("1"));Q1.add(new Etat("2"));
        Q1.add(new Etat("3"));Q1.add(new Etat("4"));
        Q1.add(new Etat("5"));Q1.add(new Etat("6"));
        Q1.add(new Etat("7"));Q1.add(new Etat("8"));
        
        Set<Transition> mu1 = new HashSet<Transition>();
        mu1.add(new Transition("1","a","2"));
        mu1.add(new Transition("1","b","4"));
        mu1.add(new Transition("2","a","2"));
        mu1.add(new Transition("2","b","3"));
        mu1.add(new Transition("3","a","5"));
        mu1.add(new Transition("3","b","6"));
        mu1.add(new Transition("4","a","3"));
        mu1.add(new Transition("4","b","5"));
        mu1.add(new Transition("5","a","6"));
        mu1.add(new Transition("6","b","6"));
        mu1.add(new Transition("6","a","7"));

        Set<String> F1 = new HashSet<String>();
        F1.add("7");
        F1.add("8");
        Automate afn1 = new AFD(A1, Q1, "1", F1, mu1);
        
    	Set<String> A2 = new HashSet<String>();      
        A2.add("a");A2.add("b");

        Set<Etat> Q2 = new HashSet<Etat>();
        Q2.add(new Etat("1"));Q2.add(new Etat("2"));
        Q2.add(new Etat("3"));Q2.add(new Etat("4"));
        Q2.add(new Etat("5"));Q2.add(new Etat("6"));
        Q2.add(new Etat("7"));
        
        Set<Transition> mu2 = new HashSet<Transition>();
        mu2.add(new Transition("1","a","2"));
        mu2.add(new Transition("1","b","4"));
        mu2.add(new Transition("2","a","2"));
        mu2.add(new Transition("2","b","3"));
        mu2.add(new Transition("3","a","5"));
        mu2.add(new Transition("3","b","6"));
        mu2.add(new Transition("4","a","3"));
        mu2.add(new Transition("4","b","5"));
        mu2.add(new Transition("5","a","6"));
        mu2.add(new Transition("6","b","6"));
        mu2.add(new Transition("6","a","7"));

        Set<String> F2 = new HashSet<String>();
        F2.add("7");
        Automate afn2 = new AFD(A2, Q2, "1", F2, mu2);  
        
        Set<String> A3 = new HashSet<String>();      
        A3.add("a");A3.add("b");

        Set<Etat> Q3 = new HashSet<Etat>();
        Q3.add(new Etat("1"));Q3.add(new Etat("2"));
        Q3.add(new Etat("3"));Q3.add(new Etat("4"));
        Q3.add(new Etat("5"));Q3.add(new Etat("6"));
        Q3.add(new Etat("7"));
        
        Set<Transition> mu3 = new HashSet<Transition>();
        mu3.add(new Transition("1","a","2"));
        mu3.add(new Transition("1","b","4"));
        mu3.add(new Transition("2","b","3"));
        mu3.add(new Transition("2","a","5"));
        mu3.add(new Transition("3","b","6"));
        mu3.add(new Transition("5","b","3"));
        mu3.add(new Transition("5","a","4"));

        Set<String> F3 = new HashSet<String>();
        F3.add("6");
        F3.add("7");
        Automate afn3 = new AFD(A3, Q3, "1", F3, mu3);

        Set<String> A4 = new HashSet<String>();      
        A4.add("a");A4.add("b");

        Set<Etat> Q4 = new HashSet<Etat>();
        Q4.add(new Etat("1"));Q4.add(new Etat("2"));
        Q4.add(new Etat("3"));Q4.add(new Etat("4"));
        Q4.add(new Etat("5"));Q4.add(new Etat("6"));
        
        Set<Transition> mu4 = new HashSet<Transition>();
        mu4.add(new Transition("1","a","2"));
        mu4.add(new Transition("1","b","4"));
        mu4.add(new Transition("2","b","3"));
        mu4.add(new Transition("2","a","5"));
        mu4.add(new Transition("3","b","6"));
        mu4.add(new Transition("5","b","3"));
        mu4.add(new Transition("5","a","4"));

        Set<String> F4 = new HashSet<String>();
        F4.add("6");
        Automate afn4 = new AFD(A4, Q4, "1", F4, mu4);
        
        Set<String> A5 = new HashSet<String>();      
        A5.add("a");A5.add("b");

        Set<Etat> Q5 = new HashSet<Etat>();
        Q5.add(new Etat("1"));Q5.add(new Etat("2"));
        Q5.add(new Etat("3"));Q5.add(new Etat("4"));
        Q5.add(new Etat("5"));Q5.add(new Etat("6"));
        Q5.add(new Etat("7"));Q5.add(new Etat("8"));
        
        Set<Transition> mu5 = new HashSet<Transition>();
        mu5.add(new Transition("1","a","2"));
        mu5.add(new Transition("2","b","2"));
        mu5.add(new Transition("2","a","3"));
        mu5.add(new Transition("3","b","6"));
        mu5.add(new Transition("3","a","5"));
        mu5.add(new Transition("4","b","1"));
        mu5.add(new Transition("4","a","2"));
        mu5.add(new Transition("5","a","6"));
        mu5.add(new Transition("6","b","6"));
        mu5.add(new Transition("6","a","7"));

        Set<String> F5 = new HashSet<String>();
        F5.add("7");
        F5.add("8");
        Automate afn5 = new AFD(A5, Q5, "1", F5, mu5);

        Set<String> A6 = new HashSet<String>();      
        A6.add("a");A6.add("b");

        Set<Etat> Q6 = new HashSet<Etat>();
        Q6.add(new Etat("1"));Q6.add(new Etat("2"));
        Q6.add(new Etat("3"));Q6.add(new Etat("4"));
        Q6.add(new Etat("5"));Q6.add(new Etat("6"));
        Q6.add(new Etat("7"));
        
        Set<Transition> mu6 = new HashSet<Transition>();
        mu6.add(new Transition("1","a","2"));
        mu6.add(new Transition("2","b","2"));
        mu6.add(new Transition("2","a","3"));
        mu6.add(new Transition("3","b","6"));
        mu6.add(new Transition("3","a","5"));
        mu6.add(new Transition("4","b","1"));
        mu6.add(new Transition("4","a","2"));
        mu6.add(new Transition("5","a","6"));
        mu6.add(new Transition("6","b","6"));
        mu6.add(new Transition("6","a","7"));

        Set<String> F6 = new HashSet<String>();
        F6.add("7");
        Automate afn6 = new AFD(A6, Q6, "1", F6, mu6);

        
        Set<String> A7 = new HashSet<String>();      
        A7.add("a");A7.add("b");

        Set<Etat> Q7 = new HashSet<Etat>();
        Q7.add(new Etat("1"));Q7.add(new Etat("2"));
        Q7.add(new Etat("3"));Q7.add(new Etat("4"));
        Q7.add(new Etat("5"));Q7.add(new Etat("6"));
        Q7.add(new Etat("7"));Q7.add(new Etat("8"));
        
        Set<Transition> mu7 = new HashSet<Transition>();
        mu7.add(new Transition("1","a","2"));
        mu7.add(new Transition("1","b","6"));
        mu7.add(new Transition("3","b","2"));
        mu7.add(new Transition("3","a","5"));
        mu7.add(new Transition("4","b","1"));
        mu7.add(new Transition("4","a","2"));
        mu7.add(new Transition("5","a","7"));
        mu7.add(new Transition("6","b","7"));

        Set<String> F7 = new HashSet<String>();
        F7.add("7");
        F7.add("8");
        Automate afn7 = new AFD(A7, Q7, "1", F7, mu7);
        
        Set<String> A8 = new HashSet<String>();      
        A8.add("a");A8.add("b");

        Set<Etat> Q8 = new HashSet<Etat>();
        Q8.add(new Etat("1"));Q8.add(new Etat("2"));
        Q8.add(new Etat("3"));Q8.add(new Etat("4"));
        Q8.add(new Etat("5"));Q8.add(new Etat("6"));
        Q8.add(new Etat("7"));
        
        Set<Transition> mu8 = new HashSet<Transition>();
        mu8.add(new Transition("1","a","2"));
        mu8.add(new Transition("1","b","6"));
        mu8.add(new Transition("3","b","2"));
        mu8.add(new Transition("3","a","5"));
        mu8.add(new Transition("4","b","1"));
        mu8.add(new Transition("4","a","2"));
        mu8.add(new Transition("5","a","7"));
        mu8.add(new Transition("6","b","7"));

        Set<String> F8 = new HashSet<String>();
        F8.add("7");
        Automate afn8 = new AFD(A8, Q8, "1", F8, mu8);
        
        System.out.println("afn 1");
        System.out.println(afn1);
        System.out.println(afn1.estUtile());    
        System.out.println(afn1.emonder());  
        System.out.println("afn 2");
        System.out.println(afn2);
        System.out.println(afn2.estUtile());    
        System.out.println(afn2.emonder());
        System.out.println("afn 3");
        System.out.println(afn3);
        System.out.println(afn3.estUtile());    
        System.out.println(afn3.emonder());
        System.out.println("afn 4");
        System.out.println(afn4);
        System.out.println(afn4.estUtile());    
        System.out.println(afn4.emonder());  
        System.out.println("afn 5");
        System.out.println(afn5);
        System.out.println(afn5.estUtile());    
        System.out.println(afn5.emonder());  
        System.out.println("afn 6");
        System.out.println(afn6);
        System.out.println(afn6.estUtile());    
        System.out.println(afn6.emonder());  
        System.out.println("afn 7");
        System.out.println(afn7);
        System.out.println(afn7.estUtile());    
        System.out.println(afn7.emonder());  
        System.out.println("afn 8");
        System.out.println(afn8);
        System.out.println(afn8.estUtile());    
        System.out.println(afn8.emonder()); 
        
        
 
   }
}