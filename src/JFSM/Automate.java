/**
 * 
 * Copyright (C) 2017 Emmanuel DESMONTILS
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * 
 * 
 * E-mail:
 * Emmanuel.Desmontils@univ-nantes.fr
 * 
 * 
 **/


/**
 * Automate.java
 * 
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */


package JFSM;

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

public class Automate implements Cloneable {
	public Map<String,Etat> Q;
	public Set<String> F, I;
	public Set<String> A;
	public Stack<Transition> histo;
	public Set<Transition> mu;
	protected String current;

	/** 
	* Constructeur de l'automate {A,Q,I,F,mu}
	* @param A l'alphabet de l'automate (toute chaîne de caratères non vide et différente de \u03b5)
	* @param Q l'ensemble des états de l'automate
	* @param I l'ensemble des états initiaux de l'automate
	* @param F l'ensemble des états finaux de l'automate
	* @param mu la fonction de transition de l'automate
	* @exception JFSMException Exception si un état qui n'existe pas est ajouté comme état initial ou final
	*/
	public Automate(Set<String> A, Set<Etat> Q, Set<String> I, Set<String> F, Set<Transition> mu) throws JFSMException {
		// Ajout de l'alphabet
		assert A.size()>0 : "A ne peut pas être vide" ;
		for(String a : A) {
			if ((a=="")||(a=="\u03b5")) throw new JFSMException("Un symbole ne peut pas être vide ou \u03b5");
		}
		this.A = A;
		this.mu = new HashSet<Transition>();

		// Ajout des états
		assert Q.size()>0 : "Q ne peut pas être vide" ;
		this.Q = new HashMap<String,Etat>();

		for (Etat e : Q)
			if (this.Q.containsKey(e.name)) System.out.println("Etat dupliqué ! Seule une version sera conservée.");
			else this.Q.put(e.name,e); 
		
		// Création de l'historique (chemin)
		this.histo = new Stack<Transition>();

		// Ajout des transitions
		this.mu.addAll(mu);

		// On collecte les états initiaux, on les positionne comme tel. S'il n'existe pas, il est oublié.
		// assert I.size()>0 : "I ne peut pas être vide" ;
		this.I = new HashSet<String>();
		for (String i : I) setInitial(i);

		// On collecte les états finaux, on les positionne comme tel. S'il n'existe pas, il est oublié.
		this.F = new HashSet<String>();
		for(String f : F) setFinal(f);
	}

	public Object clone() {
		Automate o = null;
		try {
			o = (Automate)super.clone();
			o.Q = (Map<String,Etat>) ((HashMap<String,Etat>)Q).clone() ;
			o.F = (Set<String>)  ((HashSet<String>)F).clone();
			o.I = (Set<String>)  ((HashSet<String>)I).clone();
			o.A = (Set<String>)  ((HashSet<String>)A).clone();
			o.histo = (Stack<Transition>) ((Stack<Transition>)histo).clone();
			o.mu = (Set<Transition>) ((HashSet<Transition>)mu).clone();
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	public String toString() {
		String s = "{ A={ ";
		for(String a : A ) s = s + a + " ";
		s = s + "} Q={ ";
		for(String q : Q.keySet() ) s = s + q + " ";
		s = s + "} I={ " ;
		for(String q : I ) s = s + q + " ";
		s = s + "} F={ " ;
		for(String q : F ) s = s + q + " ";
		s = s + "} \n   mu={ \n" ;
		for(Transition t : mu ) s = s + "\t"+ t + "\n";
		s = s + "   }\n}" ;

		return s ;
	}

	/** 
	* Ajoute une transition à mu.  
	* @param t transition à ajouter
	*/
	public void addTransition(Transition t) {
		mu.add(t);
	}

	/** 
	* Ajoute un état à Q.  
	* @param e L'état
	*/
	public void addEtat(Etat e){
		if (!Q.containsKey(e.name))
			Q.put(e.name,e);
	}

	/** 
	* Retrouve un état par son nom.  
	* @param n Le nom de l'état 
	* @return l'état retrouvé, null sinon
	*/
	public Etat getEtat(String n) {
		if (Q.containsKey(n))
			return Q.get(n);
		else return null;
	}

	/** 
	* Fixe le vocabulaire de l'automate.  
	* @param A la vocabulaire 
	*/
	public void setA(Set<String> A){
		this.A = A;
	}

	/** 
	* Indique qu'un état (par son nom) est un état initial.  
	* @param e Le nom de l'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setInitial(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			I.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Indique qu'un état est un état initial.  
	* @param e L'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setInitial(Etat e) throws JFSMException {	
		setInitial(e.name);
	}

	/** 
	* Indique qu'un état (par son nom) est un état final.  
	* @param e Le nom de l'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setFinal(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			F.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Indique qu'un état est un état final.  
	* @param e L'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setFinal(Etat e) throws JFSMException {	
		setFinal(e.name);
	}

	/** 
	* Détermine si un état (par son nom) est un état initial.  
	* @param e Le nom de l'état
	* @return vrai si initial, faux sinon
	*/
	public boolean isInitial(String e){
		assert Q.containsKey(e) : "isInitial : l'état doit être un état de l'automate." ;
		return I.contains(e);
	}

	/** 
	* Détermine si un état est un état initial.  
	* @param e L'état
	* @return vrai si initial, faux sinon
	*/
	public boolean isInitial(Etat e){
		return isInitial(e.name);
	}

	/** 
	* Détermine si un état (par son nom) est un état final.  
	* @param e Le nom de l'état
	* @return vrai si final, faux sinon
	*/
	public boolean isFinal(String e){
		assert Q.containsKey(e) : "isFinal : l'état doit être un état de l'automate." ;
		return F.contains(e);
	}

	/** 
	* Détermine si un état est un état final.  
	* @param e L'état
	* @return vrai si final, faux sinon
	*/
	public boolean isFinal(Etat e){
		return isFinal(e.name);
	}

	/** 
	* Initialise l'exécution de l'automate.  
	*/
	public void init() {
		histo.clear();
	}

	/** 
	* Indique si l'automate est dans un état final.  
	* @return vrai si final, faux sinon
	*/
	public boolean accepte(){return isFinal(current);}

	/** 
	* Indique si l'automate est epsilon-libre.  
	* @return vrai si e-libre, faux sinon
	*/
	public boolean epsilonLibre(){
		boolean ok = true ;
		for(Transition t : mu) {
			if (t instanceof EpsilonTransition) {
				ok = false;
				break;
			}
		}
		return ok;
	}

	/** 
	* Supprime les états qui ne sont pas utiles (accessible et co-accessible)  
	* @return un automate équivalent utile (tous les états sont utiles)
	*/
	
	public Automate emonder() {
		Automate afn = (Automate) this.clone();
		if (afn.estUtile()) { // si aucun changement n'est nécessaire
			return afn;
		} else {
			Iterator<String>touslestermes = afn.Q.keySet().iterator();
			while (touslestermes.hasNext()) { // vérifie pour tous les termes
				String teste = touslestermes.next();
				if (!((estAccessible (teste)) && ((estcoAccessible (teste))))){
					Set<String> aretirer = new HashSet<String>();
					aretirer.add(teste); // Sert à comparer via contains
					Map<String,Etat> nouveau = new HashMap<String,Etat>();
					Set<String> retirer = afn.Q.keySet();
					Iterator<String> pointeur = retirer.iterator();
					while (pointeur.hasNext()) {
						String actuel = pointeur.next();
						if (!(aretirer.contains(actuel))) {
							Etat agarder = new Etat(actuel);
							nouveau.put(actuel,agarder);
						}
					}
					afn.I.remove(teste); // enlève l'état de Q, I et F s'il y est
					afn.F.remove(teste);
					afn.Q = nouveau;
					Iterator<Transition>enlever = afn.mu.iterator();
					Set <Transition> lestransitions= new HashSet<Transition>();
					while (enlever.hasNext()) {
						Transition potentielle = enlever.next();
						if (!((aretirer.contains(potentielle.source) || (aretirer.contains(potentielle.cible))))){
							lestransitions.add(potentielle); // garde uniquement les transitions ne contenant pas l'état retiré
						}
					}
					afn.mu=lestransitions;
				}
			}
		return afn;
		}
	}

	/** 
	* Détermine si l'automate est utile  
	* @return booléen
	*/
	public boolean estUtile() {
		Iterator<String>touslestermes = Q.keySet().iterator();
		while (touslestermes.hasNext()) {
			String teste = touslestermes.next();
			if (!((estAccessible(teste)) && (estcoAccessible(teste)))){
				return false;
			}
		}
		return true;
	}

	
	public boolean estcoAccessible (String teste) {
		if (!(F.contains(teste))) {
			boolean test=false;
			ArrayList<String> parcourt = new ArrayList<String>(); // historique de tous les états par lesquels la fonction passe
			parcourt.add(teste); // On commence par le terme recherché
			Set<String> rejet = new HashSet<String>(); // Si un état ne mène nulle part, on l'ajoute à la liste de rejet
			while (test==false) {
				Iterator<Transition> Transition = mu.iterator();
				Iterator<String> fait = parcourt.iterator();
				if (!(fait.hasNext())) {
					return false;
				}
				String fin="";
				boolean active = false;
				while (fait.hasNext()) {
					fin=fait.next();
				}
				while (Transition.hasNext() && active==false) {
					Transition Transitionactuelle=Transition.next();
					if (Transitionactuelle.source==fin && (!(rejet.contains(Transitionactuelle.cible)))) {
						if (F.contains(Transitionactuelle.cible)){
							return true; // Co-accessible
						} else {
							if (!(parcourt.contains(Transitionactuelle.cible))) { // Evite de tourner en rond
								parcourt.add(Transitionactuelle.cible);
								active=true;
							}
						}
					}
				}
				if (active==false) {
					parcourt.remove(fin);
					rejet.add(fin); // ajout à la liste de rejet
				}
			}
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean estAccessible (String teste) {
		Set<String> letesteur = new HashSet<String>();
		letesteur.add(teste); // Comme nous allons devoir comparer le teste d'origine avec les cibles, nous l'ajoutons à un Set<String> pour pouvoir le comparer en faisant contains
		if (!(I.contains(teste))) {
			Iterator<String> acc = I.iterator();
			while (acc.hasNext()) { // teste pour tous les états initiaux
				boolean test=false;
				teste = acc.next();
				ArrayList<String> parcourt = new ArrayList<String>();
				parcourt.add(teste);
				Set<String> rejet = new HashSet<String>();
				while (test==false) {
					Iterator<Transition> Transition = mu.iterator();
					Iterator<String> fait = parcourt.iterator();
					if (!(fait.hasNext())) {
						test=true;
					} else {
						String fin="";
						boolean active = false;
						while (fait.hasNext()) {
							fin=fait.next();
						}
						while (Transition.hasNext() && active==false) {
							Transition Transitionactuelle=Transition.next();
							if (Transitionactuelle.source==fin && (!rejet.contains(Transitionactuelle.cible))) {
								if (letesteur.contains(Transitionactuelle.cible)){ // Si c'est le terme recherché
									return true;
								} else {
									if (!(parcourt.contains(Transitionactuelle.cible))) {
										parcourt.add(Transitionactuelle.cible);
										active=true;
									}
								}
							}
						}
						if (active==false) {
							rejet.add(fin);
							parcourt.remove(fin);
						}	
					}
				}
			}
			
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
	/** 
	* Permet de transformer l'automate en un automate standard  
	* @return un automate équivalent standard
	 * @throws JFSMException 
	*/
	public Automate standardiser() throws JFSMException {
		System.out.println("standardiser() : méthode implémentée avec succès ;)");
		Automate afn = (Automate) this.clone();
		try {
		if (!estStandard(afn)) {
			Iterator<String> it = afn.I.iterator();
			String y;
			Iterator<String> c = afn.Q.keySet().iterator();
			String search;
			String z ="0"; // Correspond au nouvel état qui va être avant le I
				while (c.hasNext()){ 
					search = c.next();
					if (search == z) { // Si un état dans l'ensemble est égal à z, on change la valeur de z
						z=z+"0";
						c = afn.Q.keySet().iterator();
					}
				}
			while(it.hasNext()) {
				y = it.next();
				Etat zo = new Etat (z);
				afn.Q.put(z,zo);
				Set <Transition> VraiClone= new HashSet<Transition>(); /* On va créer un vrai clone car on va modifier les valeurs à l'intérieur
				du clone. Or si on fait égal directement, on a une copie partielle donc les modifications se répercuterait sur afn */
				Iterator<Transition> Vraitest = afn.mu.iterator();
				while (Vraitest.hasNext()) {
					Transition to=Vraitest.next();
					Transition t = new Transition(to.source,to.symbol,to.cible);
					VraiClone.add(t);
				}
				Iterator<Transition> Tractuel = VraiClone.iterator();
				while (Tractuel.hasNext()) {
					Transition T = Tractuel.next();
					if (T.source==y) { // Vérifie si un état anciennement initial est source et s'il l'est, on ajoute à l'ensemble des transitions avec z pour source 
						T.source=z;
						afn.mu.add(T);
					}
				}			
			}
			Set<String> Initial = new HashSet<String>();
			Initial.add(z);
			afn.I=Initial;
		}
		
		}
		catch(JFSMException e) {
			System.out.println(e.getMessage());
		}
		return afn;
	}

	/** 
	* Détermine si l'automate est standard  
	* @return booléen
	*/
	public boolean estStandard(Automate b) {
		if (b.I.size()>1) {
			return false;
		} else {
			Iterator<Transition> it = b.mu.iterator();
			Iterator<String> initial = b.I.iterator();
			String i=initial.next();
			while (it.hasNext()) {
				Transition transition = it.next();
				if (transition.cible==i) {
					return false;
				}
			}
		}
		return true;
	}

	/** 
	* Permet de transformer l'automate en un automate normalisé  
	* @return un automate équivalent normalisé
	*/
	public Automate normaliser() {
		System.out.println("normaliser() : méthode non implémentée");
		Automate afn = (Automate) this.clone();
		
		// A compléter

		return afn;
	}

	/** 
	* Détermine si l'automate est normalisé  
	* @return booléen
	*/
	public boolean estNormalise() {
		System.out.println("estNormalise() : méthode non implémentée");
		boolean ok = false;

		// A compléter
		
		return ok;
	}

	/** 
	* Construit un automate reconnaissant le produit du langage de l'automate avec celui de "a" : L(this)xL(a)
	* @param a un Automate
	* @return un automate reconnaissant le produit
	*/
	public Automate produit(Automate a) {
		Automate afn = (Automate) this.clone();
		try {
		afn=afn.standardiser(); // Pour le faire le produit d'automate, le second automate doit être standardisé
		}
		catch(JFSMException e) {
			System.out.println(e.getMessage()); // en cas d'erreur, affiche l'erreur
		}
		renommer (a,afn); // renomme le premier automate en ajoutant "1" avant chacun de ses états et "2" pour le second
		Iterator<String> it = a.F.iterator();
		String z0 = afn.I.iterator().next(); // Prend l'état initial de l'automate standardisé
		while (it.hasNext()){
			String z = it.next();
			Iterator<Transition> Transition = afn.mu.iterator(); // Parcourt l'ensemble des transitions
			while (Transition.hasNext()) {
				Transition actuelle = Transition.next();
					try {
						int i = Integer.parseInt(actuelle.source); 
						int j = Integer.parseInt(z0); 
						if (i==j) { //
					Transition nouvelle2 = new Transition(z,actuelle.symbol,actuelle.cible); // crée une nouvelle transition ou z remplace actuelle.source
					a.mu.add(nouvelle2);
						} else {
							if (!(a.mu.contains(actuelle))) { // ajoute les éléments non liés à l'état initial de afn dans a
								a.mu.add(actuelle);
							}
						}
					}
					catch(JFSMException e) {
						System.out.println(e.getMessage());
					}
			}
		}
		addelementString(a.F,afn.F,z0);
		addelementMap(a.Q,afn.Q,z0);
		addlangage(a.A,afn.A);
		return a;
	}
	
	/** 
	* ajoute tous les éléments de donne différents de non dans recoit
	* @param recoit,donne, deux Set<String> et non, un String 
	*/
	public void addelementString(Set<String> recoit, Set<String> donne, String non) {
		Iterator<String> copie = donne.iterator();
		int j = Integer.parseInt(non);
		while (copie.hasNext()) {
			String ajout = copie.next();
			int i = Integer.parseInt(ajout); 
			if (i!=j) { 
				recoit.add(ajout);
			}
		}
	}
	
	
	/** 
	* ajoute tous les éléments de donne différents de non dans recoit
	* @param recoit,donne, deux Map<String,Etat> et non, un String 
	*/
	public void addelementMap(Map<String,Etat> recoit, Map<String,Etat> donne, String non) {
		Set<String> liste = donne.keySet();
		Iterator<String> copie = liste.iterator();
		int j = Integer.parseInt(non);
		while (copie.hasNext()) {
			String ajout = copie.next();
			int i = Integer.parseInt(ajout); 
			if (i!=j) { 
				Etat zo = new Etat(ajout);
				recoit.put(ajout,zo);
			}
		}
	}
	
	/** 
	* ajoute tous les éléments de donne dans recoit
	* @param recoit,donne, deux Set<String> 
	*/
	public void addlangage(Set<String> recoit, Set<String> donne) {
		Iterator<String> copie = donne.iterator();
		while (copie.hasNext()) {
			String ajout = copie.next();
			if (!(recoit.contains(ajout))) { 
				recoit.add(ajout);
			}
		}
	}
	
	
	
	
	/** 
	* renomme tous les états de deux automates afin que tous les états soient différents 
	* @param a,afn deux Automates
	*/
	public void renommer(Automate a, Automate afn) {
			Set<String> Etatsa = a.Q.keySet();
			Map<String,Etat> Qa = new HashMap<String,Etat>();
			Iterator<String> ita = Etatsa.iterator();
			while (ita.hasNext()) {
				String etat = "1" + ita.next();
				Etat zo = new Etat (etat); // Renomme tous les états dans afn.Q l'ensemble des états
				Qa.put(etat,zo);
			}
			Set<String> Etatsafn = afn.Q.keySet();
			Map<String,Etat> Q = new HashMap<String,Etat>();
			Iterator<String> it = Etatsafn.iterator();
			while (it.hasNext()) {
				String etat2 = "2" + it.next();
				Etat zo2 = new Etat (etat2);
				Q.put(etat2,zo2);
			}
			afn.Q=Q;
			a.Q=Qa; // a.Q prend le set mis en place dans while(ita.hasNext()) 
			a.I=unSetString("1",a.I);
			afn.I=unSetString("2",afn.I);
			a.F=unSetString("1",a.F);
			afn.F=unSetString("2",afn.F);
			a.mu=renommerTransition("1",a.mu);
			afn.mu=renommerTransition("2",afn.mu);
	}
	
	/** 
	* renomme les états de toutes les transitions pour leur ajouter nb 
	* @param nb, une chaine et SetTransition, un Set<Transition>
	* @return SetTransition
	*/
	public Set<Transition> renommerTransition(String nb, Set<Transition> SetTransition) {
		Iterator<Transition> it = SetTransition.iterator();
		while (it.hasNext()) {
			Transition b = it.next();
			b.source = nb + b.source;
			b.cible = nb + b.cible;
		}
		return SetTransition;
	}
	
	
	
	
	/** 
	* Construit un Set<String> avec une valeur et un Set<String>
	* @param nb, une chaine et SetString, un Set<String>
	* @return un Set<String>
	*/
	public Set<String> unSetString(String avant, Set<String> SetString) {
		Iterator<String> it = SetString.iterator();
		Set<String> RenvoiString = new HashSet<String>();
		while (it.hasNext()) {
			String b = it.next();
			RenvoiString.add(avant + b);
		}
		return RenvoiString;
	}
	/** 
	* Construit un automate reconnaissant le langage de l'automate à l'étoile : L(this)*
	* @return un automate reconnaissant la mise à l'étoile
	*/
	public Automate etoile() {
		System.out.println("etoile() : méthode non implémentée");
		Automate afn = (Automate) this.clone();

		// A compléter

		return afn;
	}

	/** 
	* Construit un automate reconnaissant l'union du langage de l'automate avec celui de "a" : L(this) U L(a)
	* @param a un Automate
	* @return un automate reconnaissant l'union
	*/
	public Automate union(Automate a) {
		System.out.println("union() : méthode non implémentée");
		return a;
	}

	/** 
	* Construit un automate reconnaissant l'intersection du langage de l'automate avec celui de "a" 
	* @param a un Automate
	* @return un automate reconnaissant l'intersection
	*/
	public Automate intersection(Automate a) {
		System.out.println("intersection() : méthode non implémentée");
		return a;
	}

	/** 
	* Construit un automate reconnaissant le complémentaire du langage 
	* @return un automate reconnaissant le complémentaire
	*/
	public Automate complementaire() {
		System.out.println("complémentaire() : méthode non implémentée");
		return this;
	}

	/** 
	* Construit un automate complet
	* @return l'automate complet
	*/
	public Automate complet() {
		System.out.println("complet() : méthode non implémentée");
		return this;
	}

	/** 
	* Teste si un automate est complet
	* @return booléen
	*/
	public boolean estComplet() {
		System.out.println("estComplet() : méthode non implémentée");
		return true;
	}

	/** 
	* Construit un automate reconnaissant le langage transposé
	* @return l'automate complet
	*/
	public Automate transpose() {
		System.out.println("transpose() : méthode non implémentée");
		return this;
	}

	/** 
	* Détermine des transitions possibles que peut emprunter l'automate en fonction de l'état courant et du symbole courant
	* @param symbol le symbole
	* @exception JFSMException Exception levée si la méthode n'est pas implémentée
	* @return la liste des transitions possibles 
	*/
	public Queue<Transition> next(String symbol) throws JFSMException  {
		throw new JFSMException("Méthode next non implémentée");
	}

	/** 
	* Exécute l'automate sur un mot (une liste de symboles)
	* @param l la liste de symboles
	* @return un booléen indiquant sur le mot est reconnu 
	* @exception JFSMException Exception levée si la méthode n'est pas implémentée
	*/
	public boolean run(List<String> l) throws JFSMException  {
		throw new JFSMException("Méthode run non implémentée");
	}

	/** 
	* Enregistre un automate sous le format XML "JFLAP 4".
	* @param file le nom du fichier
	*/
	public void save(String file) {
		try{
			File ff=new File(file); 
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			ffw.write("<?xml version='1.0' encoding='UTF-8' standalone='no'?><!--Created with JFSM.--><structure>\n");  
			ffw.write("\t<type>fa</type>\n"); 
			ffw.write("\t<automaton>\n");
			for(Etat e : Q.values()) {
				ffw.write("\t\t<state id='"+e.no+"' name='"+e.name+"'>\n\t\t\t<x>0</x>\n\t\t\t<y>0</y>\n");
				if (isInitial(e.name)) ffw.write("\t\t\t<initial/>\n");
				if (isFinal(e.name)) ffw.write("\t\t\t<final/>\n");
				ffw.write("\t\t</state>\n");
			}
			for(Transition t : mu){
				ffw.write("\t\t<transition>\n");
				Etat from = getEtat(t.source);
				ffw.write("\t\t\t<from>"+from.no+"</from>\n");
				Etat to = getEtat(t.cible);
				ffw.write("\t\t\t<to>"+to.no+"</to>\n");
				if (!(t instanceof EpsilonTransition)) ffw.write("\t\t\t<read>"+t.symbol+"</read>\n");
				else ffw.write("\t\t\t<read/>\n");
				ffw.write("\t\t</transition>\n");
			}
			ffw.write("\t</automaton>\n");
			ffw.write("</structure>\n"); 
			ffw.close(); 
		} catch (Exception e) {}
	}

	/** 
	* Charge un automate construit avec JFLAP au format XML "JFLAP 4".
	* @param file le nom du fichier
	* @return un automate 
	*/
	static public Automate load(String file) {
		JFLAPHandler handler = new JFLAPHandler();
		try {
			XMLReader saxParser = XMLReaderFactory.createXMLReader();
			saxParser.setContentHandler(handler);
			saxParser.setErrorHandler(handler);
			saxParser.parse( file ); 
		} catch (Exception e) {
			System.out.println("Exception capturée : ");
			e.printStackTrace(System.out);
			return null;
		}
		try {
			if (AFD.testDeterminisme(handler.res) ) 
				return new AFD(handler.res) ;
				else return new AFN(handler.res) ;
		} catch (JFSMException e) {return null;}
	}

}

class JFLAPHandler extends DefaultHandler {
	String cdc ;
	public Set<Etat> Q;
	public Set<String> F, I;
	public Set<String> A;
	public Set<Transition> mu;
	private Etat e;
	private Transition t;
	private String from, to, read, state_name, state_id;
	private boolean final_state, initial_state;
	public Automate res ;

	public JFLAPHandler() {super();}

	public void characters(char[] caracteres, int debut, int longueur) {
		cdc = new String(caracteres,debut,longueur);
	}

	public void startDocument() {
		cdc="";
		A = new HashSet<String>();
		I = new HashSet<String>();
		F = new HashSet<String>();
		Q = new HashSet<Etat>();
		mu = new HashSet<Transition>();
		res = null;
	}

	public void endDocument() {
		try{
			res = new Automate(A,Q,I,F,mu);
		} catch (JFSMException e) {
				System.out.println("Erreur:"+e);
		}
	}

	public void startElement(String namespaceURI, String sName, String name, Attributes attrs) {
		if (name=="state") {
			state_name = attrs.getValue("name");
			state_id = attrs.getValue("id");
			final_state = false;
			initial_state = false;
		} else if (name=="initial") {
			initial_state = true;
		} else if (name=="final") {
			final_state = true;
		}
		cdc="";
	}

	public void endElement(String uri, String localName, String name) {
		if (name=="state") {
			e = new Etat(state_id);
			Q.add(e);
			if (initial_state) I.add(state_id);
			if (final_state) F.add(state_id);
		} else if (name=="transition") {
			try{
				if (read!="") {
					A.add(read);
					Transition t = new Transition(from,read,to);
					mu.add(t);
				} else {
					EpsilonTransition t = new EpsilonTransition(from,to);
					mu.add(t);
				}
			} catch (JFSMException e) {
				System.out.println("Erreur:"+e);
			}
		} else if (name=="type") {
		
		} else if (name=="from") {
			from = cdc;
		} else if (name=="to") {
			to = cdc;
		} else if (name=="read") {
			read = cdc;
		}
	}
}


