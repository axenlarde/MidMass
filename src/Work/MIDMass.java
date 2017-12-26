package Work;
//Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import Utils.Centrer;
import Utils.variables;
import Work.TraitementPlageIP;



public class MIDMass extends JFrame
	{
	//Variables
	String titre = new String();
	String version = new String();
	String[] waitCombo;
	int rows;
	TraitementPlageIP monTraitement;
	
	//Labels
	JLabel Info;
	JLabel progression;
	JLabel fileName;
	JLabel letSGO;
	
	//Contrôles
	JTextPane Information;
	JButton valider;
	JComboBox waitTime;
	JSlider selectThread;
	JProgressBar avancement;
	JButton browse;
	
	//Panels
	JPanel panneau;
	JPanel global;
	JPanel validation;
	JScrollPane sp;
	JPanel progress;
	
	//Dimensions
	Dimension dimInfo;
	
	//Constructeur
	public MIDMass()
		{
		//Set the software name
		variables.setSoftwareName("MIDMASS");
		//Set the software version
		variables.setSoftwareVersion("3.2");
		
		/****************
		 * Initialization of the logging
		 */
		variables.setLogger(Utils.InitLogging.init(variables.getSoftwareName()+"_LogFile.txt"));
		variables.getLogger().info("\r\n");
		variables.getLogger().info("#### Entering application");
		variables.getLogger().info("## Welcome to : "+variables.getSoftwareName()+" version "+variables.getSoftwareVersion());
		variables.getLogger().info("## Author : RATEL Alexandre : 2017");
		/*******/
		
		//Variables
		waitCombo = new String[15];
		remplirTableau();
		rows = 2;
		
		//Dimensions
		dimInfo = new Dimension(this.getWidth(), 100);
		
		//Titre
		titre = "MIDMASS";
		version = "3.0";
		setTitle(titre+" - "+version);
		
		//Labels
		Info = new JLabel("Temps d'attente :");
		progression = new JLabel("0/0");
		fileName = new JLabel("");
		letSGO = new JLabel("");
		
		//Contrôles
		valider = new JButton("Valider");
		waitTime = new JComboBox(waitCombo);
		waitTime.setSelectedIndex(9);
		selectThread = new JSlider(0,50,20);
		//selectThread.setPaintTicks(true);
		selectThread.setMajorTickSpacing(5);
		selectThread.setPaintLabels( true );
		selectThread.setSnapToTicks( true );
		avancement = new JProgressBar();
		browse = new JButton("Parcourir...");
		
		//Information
		Information = new JTextPane();
		Information.setPreferredSize(dimInfo);
		Information.setCaretPosition(Information.getDocument().getLength());
		sp = new JScrollPane(Information);
		
		//Positionnement
		this.setSize(new Dimension(400,600));
		new Centrer(this);
		
		//Panneaux
		panneau = new JPanel();
		panneau.setMaximumSize(new Dimension(this.getWidth(),150));
		
		global = new JPanel();
		progress = new JPanel();
		validation = new JPanel();
		panneau.setLayout(new GridLayout(3,2));
		panneau.setPreferredSize(new Dimension(400,100));
		global.setLayout(new BoxLayout(global,BoxLayout.Y_AXIS));
		validation.setLayout(new GridLayout(1,1));
		validation.setMaximumSize(new Dimension(400, 50));
		progress.setLayout(new BoxLayout(progress,BoxLayout.X_AXIS));
		progress.setBackground(Color.GRAY);
		
		//Remplissage
		panneau.add(browse);
		panneau.add(fileName);
		panneau.add(new JLabel("Thread simultané :"));
		panneau.add(selectThread);
		//panneau.add(Box.createHorizontalGlue());
		panneau.add(Info);
		panneau.add(waitTime);
		progress.add(avancement);
		progress.add(progression);
		global.add(panneau);
		//global.add(Box.createHorizontalGlue());
		global.add(sp);
		validation.add(letSGO);
		validation.add(valider);
		global.add(validation);
		global.add(progress);
		
		monTraitement = new TraitementPlageIP(this);
		valider.addActionListener(monTraitement);
		browse.addActionListener(monTraitement);
		this.addWindowListener(monTraitement);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		getContentPane().add(global);
		//pack();
		setResizable(false);
		setVisible(true);
		}
	
	public synchronized void ajoutInfo(String ligne)
		{
		StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, Color.black);
	    int len = Information.getDocument().getLength();
	    Information.setCaretPosition(len);
	    Information.setCharacterAttributes(aset, false);
	    
	    Information.replaceSelection(ligne+"\r\n");
		}
	
	public synchronized void ajoutErreur(String ligne)
		{
		StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, Color.red);
	    int len = Information.getDocument().getLength();
	    Information.setCaretPosition(len);
	    Information.setCharacterAttributes(aset, false);
	    
	    Information.replaceSelection(ligne+"\r\n");
		}
	
	public void remplirTableau()
		{
		for(int i=0; i<waitCombo.length; i++)
			{
			waitCombo[i] = Integer.toString(i+1);
			}
		}
	
	
	
	public static void main(String[] argument) 
		{
		new MIDMass();
		}
	
	/*Fin classe*//*AR :)*/
	}
