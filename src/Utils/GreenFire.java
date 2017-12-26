package Utils;

import java.util.ArrayList;

import Misc.resultScan;


/********************************************************************
 * Classe aux methodes static qui permet g�rer l'arrayList Status
 ********************************************************************/



public class GreenFire
	{
	/************
	 * Variables
	 ************/
	//Pour g�rer l'�tat d'avancement 
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
