package Utils;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/*******************************************
 * Classe qui regroupe des methodes de test
 *******************************************/


public class testeur
	{
	/*******************************************************
	 * Methode qui vérifie une adresse IP
	 *******************************************************/
	public static boolean IPValide(String IP)
		{
		boolean done = true;
		int a=0;
		try
			{
			if(Pattern.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}", IP))
				{
				StringTokenizer st = new StringTokenizer(IP,".");
				while(st.hasMoreTokens() && done)
					{
					String tok = st.nextToken();
					if(Pattern.matches("\\d*", tok))
						{
						int nb = Integer.parseInt(tok);
						if((nb>=0)&&(nb<=255))
							{
							done = true;
							}
						else
							{
							done = false;
							}
						}
					else
						{
						done = false;
						}
					a++;
					}
				if(done && (a == 4))
					{
					return true;
					}
				}
			}
		catch(Exception e)
			{
			e.printStackTrace();
			}
		return false;
		}
	
	/*************************************
	 * Methode qui vérfie une adresse MAC
	 *************************************/
	public static boolean macValide(String mac) throws Exception
		{
		try
			{
			if((mac.compareTo("") != 0) && (mac.length() == 12) && Pattern.matches("\\p{XDigit}*", mac))
				{
				return true;
				}
			else
				{
				return false;
				}
			}
		catch(Exception e)
			{
			e.printStackTrace();
			throw new Exception("Mauvaise IP");
			}
		}
	
	
	
	/*Fin Classe*//*AR :)*/
	}
