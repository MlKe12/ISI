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

        Set<String> A = new HashSet<String>();      
        A.add("a");A.add("b");A.add("c");

        Set<Etat> Q = new HashSet<Etat>();
        System.out.println(Q);
        Q.add(new Etat("re"));Q.add(new Etat("2d"));
        Q.add(new Etat("Za"));Q.add(new Etat("Wr"));Q.add(new Etat("Do"));

        Set<Transition> mu = new HashSet<Transition>();
        mu.add(new Transition("Za","a","Wr"));
        mu.add(new Transition("Do","a","Do"));
        mu.add(new Transition("re","c","Wr"));
        mu.add(new Transition("Za","b","2d"));
        mu.add(new Transition("re","c","2d"));
        mu.add(new Transition("re","a","Do"));


        Set<String> F = new HashSet<String>();
        F.add("Wr");
        F.add("Za");
        F.add("Do");
        Automate afn = new AFD(A, Q, "re", F, mu);

        List<String> l = new ArrayList<String>();
        l.add("a");l.add("b");l.add("a");l.add("c");


      System.out.println(afn);
      System.out.println(afn.emonder());

   }
}