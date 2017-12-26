package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Misc.device;
import Utils.variables.infoCDPType;
import Utils.variables.infoDeviceType;
import Utils.variables.infoMessageType;
import Utils.variables.infoNetworkType;

/**********************************
 * Class used to write report
 * 
 * @author RATEL Alexandre
 **********************************/
public class report
	{
	
	/**
	 * Write report header file 
	 */
	public static void writeHeader()
		{
		File fichierRapport = variables.getReportFile();
		File fichierLog = variables.getReportLogFile();
		FileWriter monFichierRapport = null;
		FileWriter monFichierLog = null;
		BufferedWriter tamponRapport  = null;
		BufferedWriter tamponLog  = null;
		
		try
			{
			monFichierRapport = new FileWriter(fichierRapport,true);
			tamponRapport = new BufferedWriter(monFichierRapport);
			StringBuffer buffRapport = new StringBuffer("");
			
			monFichierLog = new FileWriter(fichierLog,true);
			tamponLog = new BufferedWriter(monFichierLog);
			StringBuffer buffLog = new StringBuffer("");
			
			/**
			 * Writing
			 */
			//Rapport
			buffRapport.append("IP,");
			
			for(int i=0; i<infoDeviceType.values().length; i++)
				{
				buffRapport.append(infoDeviceType.values()[i].name());
				buffRapport.append(",");
				}
			for(int i=0; i<infoNetworkType.values().length; i++)
				{
				buffRapport.append(infoNetworkType.values()[i].name());
				buffRapport.append(",");
				}
			for(int i=0; i<infoMessageType.values().length; i++)
				{
				buffRapport.append(infoMessageType.values()[i].name());
				buffRapport.append(",");
				}
			
			for(int i=0; i<infoCDPType.values().length; i++)
				{
				buffRapport.append(infoCDPType.values()[i].name());
				buffRapport.append(",");
				}
			
			//We remove the last comma
			String toBeWrited = buffRapport.toString();
			toBeWrited = toBeWrited.substring(0, toBeWrited.length()-1);
			toBeWrited = toBeWrited+"\r\n";
			tamponRapport.write(toBeWrited);
			
			//Log
			buffLog.append("IP,Statut,Reason\r\n");
			tamponLog.write(buffLog.toString());
			}
		catch(IOException exception)
			{
			System.out.println("Erreur d'écriture : "+exception.getMessage());
			}
		finally
			{
			try
				{
				tamponRapport.flush();
				tamponRapport.close();
				monFichierRapport.close();
				}
			catch(Exception e)
				{
				System.out.println(e.getMessage());
				}
			try
				{
				tamponLog.flush();
				tamponLog.close();
				monFichierLog.close();
				}
			catch(Exception e)
				{
				System.out.println(e.getMessage());
				}
			}
		}
	
	
	
	
	/**
	 * Write one line with device data
	 */
	public synchronized static void write(device d) throws Exception
		{
		File fichierRapport = variables.getReportFile();
		FileWriter monFichierRapport = null;
		BufferedWriter tamponRapport  = null;
		
		try
			{
			monFichierRapport = new FileWriter(fichierRapport,true);
			tamponRapport = new BufferedWriter(monFichierRapport);
			StringBuffer buffRapport = new StringBuffer("");
			
			//Ecriture
			
			buffRapport.append(d.getData());
			buffRapport.append("\r\n");
			tamponRapport.write(buffRapport.toString());
			}
		catch(Exception exception)
			{
			variables.getLogger().error("Erreur d'écriture : "+exception.getMessage(), exception);
			throw new Exception("Erreur d'écriture : "+exception.getMessage());
			}
		finally
			{
			try
				{
				tamponRapport.flush();
				tamponRapport.close();
				monFichierRapport.close();
				}
			catch(Exception e)
				{
				variables.getLogger().error(e.getMessage(), e);
				throw new Exception(e.getMessage());
				}
			}
		}
	
	
	public synchronized static void writeLog(String ip,int index) throws Exception
		{
		File fichierLog = variables.getReportLogFile();
		FileWriter monFichierLog = null;
		BufferedWriter tamponLog  = null;
		
		try
			{
			monFichierLog = new FileWriter(fichierLog,true);
			tamponLog = new BufferedWriter(monFichierLog);
			StringBuffer buffLog = new StringBuffer("");
			
			//Ecriture
			buffLog.append(ip+",");
			buffLog.append(GreenFire.getStatus().get(index).getStatus()+",");
			buffLog.append(GreenFire.getStatus().get(index).getDesc());
			buffLog.append("\r\n");
			tamponLog.write(buffLog.toString());
			}
		catch(Exception exception)
			{
			variables.getLogger().error("Erreur d'écriture : "+exception.getMessage(), exception);
			throw new Exception("Erreur d'écriture : "+exception.getMessage());
			}
		finally
			{
			try
				{
				tamponLog.flush();
				tamponLog.close();
				monFichierLog.close();
				}
			catch(Exception e)
				{
				variables.getLogger().error(e.getMessage(), e);
				throw new Exception(e.getMessage());
				}
			}
		}
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

