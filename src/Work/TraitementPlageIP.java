package Work;
//Imports
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Utils.sourceFile;
import Utils.GreenFire;
import Utils.variables;


public class TraitementPlageIP implements ActionListener, WindowListener
	{
	//Variables
	MIDMass monMID;
	String adresseIP;
	int PIPDebut;
	int PIPFin;
	String MAC;
	String ID;
	int waitTime;
	int thread;
	
	//Constructeur
	public TraitementPlageIP(MIDMass monMID)
		{
		this.monMID = monMID;
		adresseIP = new String();
		thread = 20;
		}
	
	
	/*********************
	 * Début des Listener
	 *********************/
	
	public void actionPerformed(ActionEvent evt)
		{
		
		if(evt.getSource() == monMID.valider)
			{
			variables.getLogger().info("Bouton appuyé");
			
			traitementPlage();
			}
		
		//Récupération du fichier source
		if(evt.getSource() == monMID.browse)
			{
			JFileChooser fcSource = new JFileChooser();
			fcSource.setCurrentDirectory(new File("."));
			fcSource.setDialogTitle("Choix du fichier source");
			int resultat = fcSource.showDialog(fcSource, "Choisir");
			if(resultat == fcSource.APPROVE_OPTION)
				{
				variables.setSourceFile(new File(fcSource.getSelectedFile().toString()));
				monMID.fileName.setText(" "+variables.getSourceFile().getAbsolutePath());
				}
			else
				{
				JOptionPane.showMessageDialog(monMID,"Veuillez préciser un fichier","Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	
	
	/*******************
	 * Fin des listener
	 *******************/
	
	public int extractPlage(String Addr)
		{
		return Integer.parseInt(Addr.substring(Addr.lastIndexOf(".")+1));
		}
	
	public String extractIP(String Addr)
		{
		return Addr.substring(0,Addr.lastIndexOf(".")+1);
		}
	
	public void writeInfo(String info)
		{
		System.out.println("longueur :"+monMID.Information.getText().length());
		}
	
	public void traitementPlage()
		{
		if((variables.getSourceFile() != null) && (variables.getSourceFile().exists()))
			{
			try
				{
				ArrayList<String> ipRange = sourceFile.read();
				waitTime = Integer.parseInt(monMID.waitTime.getSelectedItem().toString());
				thread = monMID.selectThread.getValue();
				if(thread==0)thread = 1;
				
				//Initialisation des status
				GreenFire.setStatus();
				for(int i = 0; i<ipRange.size(); i++)
					{
					GreenFire.addStatus("waiting");
					}
					
				//Lancement du Thread de progression
				new Progression(monMID);
				
				new Travailleur(ipRange,thread,waitTime,monMID);
				}
			catch (Exception exc)
				{
				JOptionPane.showMessageDialog(monMID,"Une erreur de lecture est survenue.\r\nFichier source incorrect\r\n"+exc.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				exc.printStackTrace();
				}
			}
		else
			{
			JOptionPane.showMessageDialog(monMID,"Une erreur de lecture est survenue.\r\nLe fichier source n'existe pas","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e)
		{
		System.exit(0);
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e)
		{
		// TODO Auto-generated method stub
		
		}

	
	/*Fin classe*//*AR :)*/
	}
