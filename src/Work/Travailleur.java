package Work;


//Import
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Utils.GreenFire;
import Utils.report;
import Utils.variables;

/************************************************************
 * Classe qui s'occupe de lancer les Threads de récupération
 * des informations
 ************************************************************/


public class Travailleur extends Thread
	{
	/************
	 * Variables
	 ************/
	private int NbrThreadSimultane;
	private int timeOut;
	private ArrayList<String> ipRange;
	private MIDMass monMID;
	
	/***************
	 * Constructeur
	 ***************/
	public Travailleur(ArrayList<String> ipRange, int NbrThreadSimultane, int timeOut, MIDMass monMID)
		{
		this.NbrThreadSimultane = NbrThreadSimultane;
		this.timeOut = timeOut;
		this.monMID = monMID;
		this.ipRange = ipRange;
		
		start();
		}
	
	public void run()
		{
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String time = formatter.format(now);
		variables.setReportFile(new File("./ExtractMacID_"+time+".txt"));
		variables.setReportLogFile(new File("./ReportMacID_"+time+".txt"));
		report.writeHeader();
		
		Utils.variables.getLogger().debug("Nombre d'ip a traiter : "+GreenFire.getStatus().size());
		
		for(int i=0; i<ipRange.size(); i++)
			{
			while(true)
				{
				if(processing())
					{
					String ip = new String(ipRange.get(i).toString());
					Utils.variables.getLogger().debug("Thread pour inspecter le device "+(i+1)+" ip: "+ip+" lancé");
					monMID.ajoutInfo("Thread "+i+" : tentative IP Phone : "+ip+" lancé");
					new AspiURLManager(ip,timeOut,i,monMID);
					break;
					}
				try
					{
					sleep(10);
					System.gc();
					}
				catch(Exception e)
					{
					e.printStackTrace();
					variables.getLogger().error(e.getMessage(), e);
					}
				}
			Utils.variables.getLogger().debug("Travailleur thread "+i+" terminé");
			}
		}

	public boolean processing()
		{
		int compteur = 0;
		for(int i=0; i<GreenFire.getStatus().size(); i++)
			{
			if(GreenFire.getStatus().get(i).getStatus().compareTo("processing")==0)
				{
				compteur++;
				}
			}
		if(compteur < NbrThreadSimultane)
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	
	
	/*Fin classe*/
	}
