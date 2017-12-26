package Work;
//Imports
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.*;

import Misc.device;
import Utils.GreenFire;
import Utils.xMLGear;
import Utils.variables.infoCDPType;
import Utils.variables.infoDeviceType;
import Utils.variables.infoNetworkType;

public class AspiURLCDP
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
	public AspiURLCDP(device myDevice,int waitTime, int index, MIDMass monMID) throws Exception
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
	 * Méthode qui envoi la requete http au serveur et 
	 * réceptionne la réponse
	 ***************************************************/
	public void launch() throws Exception
		{
		try
			{
			Utils.variables.getLogger().debug("Début de récupération des info Network pour le device : "+adresseIP);
			myUrl = new URL("http://"+adresseIP+"/PortInformationX?1");
			
			connexion = null;
			connexion = (HttpURLConnection)myUrl.openConnection();
			connexion.setRequestMethod("GET");
			connexion.setConnectTimeout(waitTime*1000);
			connexion.setRequestProperty("Content-Type", "text/html");
			connexion.setUseCaches(false);
			connexion.setDoInput(true);
			connexion.setDoOutput(true);
			
		    //Accueil de la réponse
			in = new BufferedReader(new InputStreamReader(connexion.getInputStream(),"UTF-8"));
		    while ((inputline = in.readLine()) != null)
	        	{
	            ObjetXMLReponse += inputline;
	         	}
		    
		    Utils.variables.getLogger().debug("Reponse thread "+index+" : "+ObjetXMLReponse);
		    
		    /************************
		     * Lecture de la réponse
		     ************************/
		    
		    if(Pattern.matches(".*<PortInformation>.*", ObjetXMLReponse))
		    	{
		    	ObjetXMLReponse = ObjetXMLReponse.replace("null", "");
		    	ObjetXMLReponse = ObjetXMLReponse.replace("<PortInformation>", "<Device><PortInformation>");
		    	ObjetXMLReponse += "</Device>"; 
		    	Utils.variables.getLogger().debug("Reponse thread "+index+" remise en forme : "+ObjetXMLReponse);
		    	loadAdresse("PortInformation");
		    	}
		    else if(Pattern.matches(".*<EthernetInformation>.*", ObjetXMLReponse))
		    	{
		    	ObjetXMLReponse = ObjetXMLReponse.replace("null", "");
		    	ObjetXMLReponse = ObjetXMLReponse.replace("<EthernetInformation>", "<Device><EthernetInformation>");
		    	ObjetXMLReponse += "</Device>"; 
		    	Utils.variables.getLogger().debug("Reponse thread "+index+" remise en forme : "+ObjetXMLReponse);
		    	loadAdresse("EthernetInformation");
		    	}
		    else
		    	{
		    	//Affichage du résultat
		    	Utils.variables.getLogger().error("Reponse incompréhensible du device "+adresseIP+" : "+ObjetXMLReponse);
		    	}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Utils.variables.getLogger().error("Erreur de récupération d'information "+adresseIP+" : "+exc.getMessage(),exc);
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
				e.printStackTrace();
				Utils.variables.getLogger().error("BIG ERROR : "+e.getMessage(),e);
				}
			}
		}
	
	public void loadAdresse(String mainNode) throws Exception
		{
		try
			{
			ArrayList<String> listParams = new ArrayList<String>();
			listParams.add(mainNode);
			ArrayList<String[][]> result = xMLGear.getResultListTab(ObjetXMLReponse, listParams);
			
			for(int i=0; i<infoCDPType.values().length; i++)
				{
				for(String s[] : result.get(0))
					{
					if(s[0].equals(infoCDPType.values()[i].name()))myDevice.setInfo(infoCDPType.values()[i],s[1].replace(",",""));
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
