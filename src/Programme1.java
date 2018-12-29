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

public class Programme1 {
    public static void main(String argv []) throws JFSMException {

        Set<String> A1 = new HashSet<String>();      
        A1.add("a");A1.add("b");

        Set<Etat> Q1 = new HashSet<Etat>();
        Q1.add(new Etat("1"));Q1.add(new Etat("2"));
        Q1.add(new Etat("3"));Q1.add(new Etat("4"));
        
        Set<Transition> mu1 = new HashSet<Transition>();
        mu1.add(new Transition("1","b","2"));
        mu1.add(new Transition("1","a","3"));
        mu1.add(new Transition("2","a","4"));
        mu1.add(new Transition("3","b","4"));

        Set<String> F1 = new HashSet<String>();
        F1.add("2");
        F1.add("4");
        Automate afn1 = new AFD(A1, Q1, "1", F1, mu1);


      System.out.println(afn1);
      System.out.println(afn1.estUtile());
      System.out.println(afn1.emonder());
      

      Set<String> A2 = new HashSet<String>();      
      A2.add("a");A2.add("b");

      Set<Etat> Q2 = new HashSet<Etat>();
      Q2.add(new Etat("1"));Q2.add(new Etat("2"));
      Q2.add(new Etat("3"));Q2.add(new Etat("4"));
      Q2.add(new Etat("6"));Q2.add(new Etat("5"));
      
      Set<Transition> mu2 = new HashSet<Transition>();
      mu2.add(new Transition("1","b","2"));
      mu2.add(new Transition("1","a","3"));
      mu2.add(new Transition("2","a","4"));
      mu2.add(new Transition("3","b","4"));
      mu2.add(new Transition("6","b","3"));
      mu2.add(new Transition("2","b","5"));
      mu2.add(new Transition("4","a","1"));

      Set<String> F2 = new HashSet<String>();
      F2.add("2");
      F2.add("4");
      Set<String> I2 = new HashSet<String>();
      I2.add("1");
      I2.add("6");
      Automate afn2 = new AFN(A2, Q2, I2, F2, mu2);


    System.out.println(afn2);
    System.out.println(afn2.estUtile());
    System.out.println(afn2.emonder());
    

    Set<String> A3 = new HashSet<String>();      
    A3.add("a");A3.add("b");
    A3.add("c");A3.add("d");
    A3.add("e");

    Set<Etat> Q3 = new HashSet<Etat>();
    Q3.add(new Etat("1"));Q3.add(new Etat("2"));
    Q3.add(new Etat("3"));Q3.add(new Etat("4"));
    Q3.add(new Etat("6"));Q3.add(new Etat("5"));
    
    Set<Transition> mu3 = new HashSet<Transition>();
    mu3.add(new Transition("1","c","5"));
    mu3.add(new Transition("2","d","5"));
    mu3.add(new Transition("2","b","4"));
    mu3.add(new Transition("3","a","4"));
    mu3.add(new Transition("4","e","6"));
    mu3.add(new Transition("5","d","6"));

    Set<String> F3 = new HashSet<String>();
    F3.add("6");
    Automate afn3 = new AFD(A3, Q3, "1", F3, mu3);


  System.out.println(afn3);
  System.out.println(afn3.estUtile());
  System.out.println(afn3.emonder());
  
  
  Set<String> A4 = new HashSet<String>();      
  A4.add("a");A4.add("b");
  A4.add("c");

  Set<Etat> Q4 = new HashSet<Etat>();
  Q4.add(new Etat("1"));Q4.add(new Etat("2"));
  Q4.add(new Etat("3"));Q4.add(new Etat("4"));
  Q4.add(new Etat("6"));Q4.add(new Etat("5"));
  Q4.add(new Etat("7"));Q4.add(new Etat("8"));
  
  Set<Transition> mu4 = new HashSet<Transition>();
  mu4.add(new Transition("1","a","7"));
  mu4.add(new Transition("1","b","7"));
  mu4.add(new Transition("1","c","7"));
  mu4.add(new Transition("2","b","7"));
  mu4.add(new Transition("4"," ","5"));
  mu4.add(new Transition("5"," ","7"));

  Set<String> F4 = new HashSet<String>();
  F4.add("6");
  F4.add("7");
  F4.add("8");
  Set<String> I4 = new HashSet<String>();
  I4.add("1");
  I4.add("2");
  I4.add("3");
  I4.add("4");
  I4.add("8");
  Automate afn4 = new AFN(A4, Q4, I4, F4, mu4);
  
  System.out.println(afn4);
  System.out.println(afn4.estUtile());
  System.out.println(afn4.emonder());
  
  Set<String> A5 = new HashSet<String>();      
  A5.add("a");A5.add("b");A5.add("d");

  Set<Etat> Q5 = new HashSet<Etat>();
  Q5.add(new Etat("1"));Q5.add(new Etat("2"));
  Q5.add(new Etat("3"));
  
  Set<Transition> mu5 = new HashSet<Transition>();
  mu5.add(new Transition("1","b","2"));
  mu5.add(new Transition("2","d","1"));
  mu5.add(new Transition("1","a","3"));

  Set<String> F5 = new HashSet<String>();
  F5.add("3");
  Automate afn5 = new AFD(A5, Q5, "1", F5, mu5);

  System.out.println(afn5);
  System.out.println(afn5.estUtile());
  System.out.println(afn5.emonder());
   }
}