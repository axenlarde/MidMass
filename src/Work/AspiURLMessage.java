package Work;
//Imports
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import Misc.device;
import Utils.GreenFire;
import Utils.variables;
import Utils.xMLGear;
import Utils.variables.infoDeviceType;
import Utils.variables.infoMessageType;

public class AspiURLMessage
	{
	//Variables
	String adresseIP;
	int adresseActuelle;
	String inputline;
	String total;
	int waitTime;
	String ObjetHTMLReponse;
	String[] answer;
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
	public AspiURLMessage(device myDevice,int waitTime, int index, MIDMass monMID) throws Exception
		{
		this.myDevice= myDevice; 
		this.adresseIP = myDevice.getIp();
		inputline = new String();
		total = new String();
		this.waitTime = waitTime;
		this.index = index;
		this.monMID = monMID;
		this.answer = new String[5];
		
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
			Utils.variables.getLogger().debug("Début de récupération des messages d'états");
			
			/**
			 * If the device model is 6921 the url is different
			 */
			if(myDevice.getModelNumber().contains("6921"))
				{
				myUrl = new URL("http://"+adresseIP+"/status.html");
				}
			else
				{
				myUrl = new URL("http://"+adresseIP+"/CGI/Java/Serviceability?adapter=device.settings.status.messages");
				}
			
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
	            ObjetHTMLReponse += inputline;
	         	}
		    
		    /************************
		     * Lecture de la réponse
		     ************************/
		    
		    if(Pattern.matches(".*<TITLE>Cisco.*", ObjetHTMLReponse))
		    	{
		    	ObjetHTMLReponse = ObjetHTMLReponse.replace("null", "");
		    	
		    	if(myDevice.getModelNumber().contains("69"))
					{
					String temp = ObjetHTMLReponse.substring(ObjetHTMLReponse.indexOf("var s0 = "));
					temp = temp.replace("var s0 = \"", "");
					temp = temp.substring(0, temp.indexOf("\""));
					temp = temp.replace("\"", "");
					String[] tab = temp.split("&");//Here every string in "tab" contain a message
					
					for(int i=0; i<4; i++)
						{
						//We remove the time
						if(tab[i].contains("] "))
							{
							String[] timeStriper = tab[i].split("] ");
							answer[i] = timeStriper[1];
							}
						else
							{
							answer[i] = tab[i];
							}
						Utils.variables.getLogger().debug("Reponse thread "+index+" remise en forme, message "+i+" : "+answer[i]);
						}
					}
		    	else
		    		{
			    	String[] tab = ObjetHTMLReponse.split("<B>");
			    	
			    	//we want to keep 4 messages
			    	for(int i=0; i<4; i++)
			    		{
			    		String temp = tab[tab.length-(4-i)];
			    		temp = temp.substring(0, temp.indexOf("</B>"));
				    	
				    	//We remove the time
			    		
			    		if(myDevice.getModelNumber().contains("78"))
							{
							String[] timeStriper = temp.split("] ");
					    	StringBuffer buff = new StringBuffer(""); 
					    	for(int j=1; j<timeStriper.length; j++)
					    		{
					    		buff.append(timeStriper[j]);
					    		if(j != timeStriper.length-1)
					    			{
					    			buff.append(" ");
					    			}
					    		}
					    	temp = buff.toString();
							}
			    		else
			    			{
			    			String[] timeStriper = temp.split(" ");
					    	StringBuffer buff = new StringBuffer(""); 
					    	for(int j=1; j<timeStriper.length; j++)
					    		{
					    		buff.append(timeStriper[j]);
					    		if(j != timeStriper.length-1)
					    			{
					    			buff.append(" ");
					    			}
					    		}
					    	temp = buff.toString();
			    			}
			    	
				    	Utils.variables.getLogger().debug("Reponse thread "+index+" remise en forme, message "+i+" : "+temp);
			    		
			    		answer[i] = temp;
			    		}
		    		}
		    	
		    	/**
		    	 * We now need to detect if something went wrong with the ITL download
		    	 */
		    	ITLCheck();
		    	/****/
		    	
		    	MessagesCleanUp();//To remove the not wanted caracters
		    	
		    	loadAdresse();
		    	}
		    else
		    	{
		    	//Affichage du résultat
		    	Utils.variables.getLogger().error("Reponse incompréhensible du device "+adresseIP+" : "+ObjetHTMLReponse);
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
	
	public void loadAdresse() throws Exception
		{
		try
			{
			myDevice.setInfo(infoMessageType.Message1, answer);
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Utils.variables.getLogger().error("Error Extract du XML :"+exc.getMessage(), exc);
			}
		}
	
	
	/**
	 * Method used to guess if the last ITL download went well
	 */
	private void ITLCheck()
		{
		boolean foundError = false;
		boolean foundDownloaded = false;
		String[] errorList = new String[]{"fail","échec","echec"};
		String[] downloadedList = new String[]{"mise à jour","installed","installé","updated"};
		
		answer[4]=variables.ITLStatusType.Unknown.name();//Default Value
		
		for(int i=0; i<4; i++)
			{
			if((answer[i].toLowerCase().contains("itl")) ||
					(answer[i].toLowerCase().contains("liste de confiance")))
				{
				for(String error : errorList)
					{
					if(answer[i].toLowerCase().contains(error))foundError = true;
					}
				
				for(String downloaded : downloadedList)
					{
					if(answer[i].toLowerCase().contains(downloaded))foundDownloaded = true;
					}
				}
			}
		
		if(foundDownloaded)
			{
			answer[4]=variables.ITLStatusType.Downloaded.name();
			}
		
		if(foundError)
			{
			answer[4]=variables.ITLStatusType.Error.name();
			}
		
		Utils.variables.getLogger().debug("Reponse thread "+index+" ITL Statut : "+answer[4]);
		}
	
	/**
	 * Method used to remove unwanted characters from the messages
	 */
	private void MessagesCleanUp()
		{
		for(int i=0; i<4; i++)
			{
			answer[i] = answer[i].replace(",", "");
			answer[i] = answer[i].replace(";", "");
			}
		}
	
	
	
	
	/*Fin Classe*//*AR :)*/
	}
