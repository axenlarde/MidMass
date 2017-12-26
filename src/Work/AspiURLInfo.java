package Work;
//Imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Misc.device;
import Utils.xMLGear;
import Utils.variables.infoDeviceType;

public class AspiURLInfo
	{
	//Variables
	String adresseIP;
	int adresseActuelle;
	String inputline;
	String total;
	int waitTime;
	String ObjetXMLReponse;
	int index;
	MIDMass monMID;
	private device myDevice;
	
	//Connexion Socket
	BufferedReader in;
	BufferedWriter  out;
	
	//Connexion http
	URL myUrl;
	HttpURLConnection connexion;
	
	
	//Constructeur
	public AspiURLInfo(device myDevice,int waitTime, int index, MIDMass monMID) throws Exception
		{
		this.myDevice= myDevice; 
		this.adresseIP = myDevice.getIp();
		inputline = new String();
		total = new String();
		this.waitTime = waitTime;
		this.index = index;
		this.monMID = monMID;
		
		launch();
		}

	/***************************************************
	 * M�thode qui envoi la requete http au serveur et 
	 * r�ceptionne la r�ponse
	 ***************************************************/
	public void launch() throws Exception
		{
		try
			{
			Utils.variables.getLogger().debug("D�but de r�cup�ration des info Device pour l'entr�e : "+adresseIP);
			myUrl = new URL("http://"+adresseIP+"/DeviceInformationX");
			
			connexion = null;
			connexion = (HttpURLConnection)myUrl.openConnection();
			connexion.setRequestMethod("GET");
			connexion.setConnectTimeout(waitTime*1000);
			connexion.setRequestProperty("Content-Type", "text/html");
			connexion.setUseCaches(false);
			connexion.setDoInput(true);
			connexion.setDoOutput(true);
			
		    //Accueil de la r�ponse
			in = new BufferedReader(new InputStreamReader(connexion.getInputStream(),"UTF-8"));
		    while ((inputline = in.readLine()) != null)
	        	{
	            ObjetXMLReponse += inputline;
	         	}
		    
		    Utils.variables.getLogger().debug("Reponse thread "+index+" : "+ObjetXMLReponse);
		    
		    /************************
		     * Lecture de la r�ponse
		     ************************/
		    
		    if(Pattern.matches(".*<DeviceInformation>.*", ObjetXMLReponse))
		    	{
		    	ObjetXMLReponse = ObjetXMLReponse.replace("null", "");
		    	ObjetXMLReponse = ObjetXMLReponse.replace("<DeviceInformation>", "<Device><DeviceInformation>");
		    	ObjetXMLReponse += "</Device>"; 
		    	Utils.variables.getLogger().debug("Reponse thread "+index+" remise en forme : "+ObjetXMLReponse);
		    	loadAdresse();
		    	}
		    else
		    	{
		    	//Affichage du r�sultat
		    	Utils.variables.getLogger().error("Reponse incompr�hensible du device "+adresseIP+" : "+ObjetXMLReponse);
		    	}
			}
		catch(UnknownHostException e)
			{
			e.printStackTrace();
			throw new Exception("Erreur de r�cup�ration d'information : "+e.getMessage());
			}
		catch(IOException ioe)
			{
			ioe.printStackTrace();
			throw new Exception("Erreur de r�cup�ration d'information : IOException "+ioe.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			throw new Exception("Erreur de r�cup�ration d'information : "+exc.getMessage());
			}
		finally
			{
			try
				{
				in.close();
				connexion.disconnect();
				}
			catch(Exception e)
				{
				Utils.variables.getLogger().debug("BIG ERROR : "+e.getMessage());
				e.printStackTrace();
				throw new Exception("Erreur de r�cup�ration d'information : "+e.getMessage());
				}
			}
		}
	
	public void loadAdresse() throws Exception
		{
		try
			{
			ArrayList<String> listParams = new ArrayList<String>();
			listParams.add("DeviceInformation");
			ArrayList<String[][]> result = xMLGear.getResultListTab(ObjetXMLReponse, listParams);
			
			for(int i=0; i<infoDeviceType.values().length; i++)
				{
				for(String s[] : result.get(0))
					{
					if(s[0].equals(infoDeviceType.values()[i].name()))myDevice.setInfo(infoDeviceType.values()[i],s[1]);
					}
				}
			}
		catch(Exception exc)
			{
			Utils.variables.getLogger().debug("Error Extract du XML :"+exc.getMessage());
			exc.printStackTrace();
			throw new Exception("Error Extract du XML :"+exc.getMessage());
			}
		}
	
	
	/*Fin Classe*//*AR :)*/
	}
