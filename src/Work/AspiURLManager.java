package Work;

//Import
import Misc.device;
import Utils.GreenFire;
import Utils.report;
import Work.AspiURLCDP;
import Work.AspiURLInfo;
import Work.AspiURLMessage;
import Work.AspiURLNetwork;


/**********************************
 * Class used to manage AspiUrl classes
 * 
 * @author RATEL Alexandre
 **********************************/
public class AspiURLManager extends Thread
	{
	/**
	 * Variables
	 */
	private device d;
	private String ip;
	private int timeOut;
	private int index;
	private MIDMass monMID;
	
	
	public AspiURLManager(String ip, int timeOut, int index, MIDMass monMID)
		{
		this.ip = ip;
		this.timeOut = timeOut;
		this.index = index;
		this.monMID = monMID;
		
		d = new device(ip);
		GreenFire.getStatus().get(index).setStatus("processing");
		
		start();
		}
	
	public void run()
		{
		try
			{
			new AspiURLInfo(d, timeOut, index, monMID);
			new AspiURLNetwork(d, timeOut, index, monMID);
			new AspiURLMessage(d, timeOut, index, monMID);
			new AspiURLCDP(d, timeOut, index, monMID);
			
			//Affichage du résultat
	    	GreenFire.getStatus().get(index).setStatus("success");
	    	monMID.ajoutInfo("Thread "+index+" : informations récupérées");
	    	report.write(d);
			}
		catch(Exception exc)
			{
			Utils.variables.getLogger().error(exc.getMessage(), exc);
			GreenFire.getStatus().get(index).setStatus("error");
			GreenFire.getStatus().get(index).setDesc("ERROR : "+exc.getMessage());
			monMID.ajoutErreur("Thread "+index+" : "+d.getIp()+" : échec");
			}
		finally
			{
			try
				{
				report.writeLog(ip, index);
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
