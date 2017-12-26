package Utils;

import java.util.ArrayList;

import Misc.resultScan;


/********************************************************************
 * Classe aux methodes static qui permet gérer l'arrayList Status
 ********************************************************************/



public class GreenFire
	{
	/************
	 * Variables
	 ************/
	//Pour gérer l'état d'avancement 
	private static ArrayList<resultScan> status; 
	
	public static void setStatus()
		{
		status = new ArrayList<resultScan>();
		}
	
	public static void addStatus(String currentStatus)
		{
		status.add(new resultScan(currentStatus));
		}
	
	public synchronized static ArrayList<resultScan> getStatus()
		{
		return status;
		}
	
	/*Fin classe*//*AR :)*/
	}
