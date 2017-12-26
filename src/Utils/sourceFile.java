package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Utils.variables.infoDeviceType;
import Utils.variables.infoNetworkType;

/**********************************
 * Class used to read source file
 * 
 * @author RATEL Alexandre
 **********************************/
public class sourceFile
	{
	
	public static ArrayList<String> read() throws Exception
		{
		File fichierSource = variables.getSourceFile();
		FileReader fileReader= null;
		BufferedReader tampon  = null;
		ArrayList<String> ipRange = new ArrayList<String>();
		
		try
			{
			fileReader = new FileReader(fichierSource);
			tampon = new BufferedReader(fileReader);
			String inputLine = new String(); 
			
			while (((inputLine = tampon.readLine()) != null) && (inputLine.compareTo("") !=0))
	        	{
	            ipRange.add(inputLine);
	            System.out.println(inputLine);
	         	}
		
			return ipRange;
			}
		catch(IOException exception)
			{
			exception.printStackTrace();
			System.out.println("Erreur de lecture : "+exception.getMessage());
			throw new Exception("Fichier erroné");
			}
		finally
			{
			try
				{
				tampon.close();
				fileReader.close();
				}
			catch(Exception e)
				{
				e.printStackTrace();
				System.out.println(e.getMessage());
				}
			}
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

