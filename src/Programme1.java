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

        Set<String> A = new HashSet<String>();      
        A.add("a");A.add("b");A.add("c");

        Set<Etat> Q = new HashSet<Etat>();
        System.out.println(Q);
        Q.add(new Etat("A"));Q.add(new Etat("2"));
        Q.add(new Etat("Z"));Q.add(new Etat("N"));Q.add(new Etat("W"));

        Set<Transition> mu = new HashSet<Transition>();
        mu.add(new Transition("2","b","W"));
        mu.add(new Transition("Z","c","A"));
        mu.add(new Transition("W","a","2"));
        mu.add(new Transition("Z","b","Z"));
        mu.add(new Transition("2","a","W"));
        mu.add(new Transition("W","c","N"));


        Set<String> F = new HashSet<String>();
        F.add("W");
        F.add("Z");
        F.add("A");
        Automate afn = new AFD(A, Q, "A", F, mu);

        List<String> l = new ArrayList<String>();
        l.add("a");l.add("b");l.add("a");l.add("c");


      System.out.println(afn);
      System.out.println(afn.emonder());

   }
}