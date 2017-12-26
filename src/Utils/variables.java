package Utils;

import java.io.File;

import org.apache.log4j.Logger;

/**********************************
 * Class used to store static variable
 * 
 * @author RATEL Alexandre
 **********************************/
public class variables
	{
	/**
	 * Variables
	 */
	public enum infoDeviceType{MACAddress,serialNumber,phoneDN,appLoadID,bootLoadID,versionID,modelNumber,time,date};
	public enum infoNetworkType{UserLocale,TFTPServer1,TFTPServer2,CallManager1,CallManager2,CallManager3,UserLocaleVersion,DHCPServer,AltTFTP};
	public enum infoMessageType{Message1,Message2,Message3,Message4,ITLStatus};
	public enum infoCDPType{CDPNeighborDeviceId,CDPNeighborIP,CDPNeighborPort,PortSpeed};
	public enum ITLStatusType{Downloaded,Error,Unknown};
	
	private static File reportFile;
	private static File reportLogFile;
	private static File sourceFile;
	private static Logger logger;
	private static String softwareName, softwareVersion;
	
	public static synchronized File getReportFile()
		{
		return reportFile;
		}

	public static void setReportFile(File reportFile)
		{
		variables.reportFile = reportFile;
		}

	public static File getSourceFile()
		{
		return sourceFile;
		}

	public static void setSourceFile(File sourceFile)
		{
		variables.sourceFile = sourceFile;
		}

	public synchronized static File getReportLogFile()
		{
		return reportLogFile;
		}

	public static void setReportLogFile(File reportLogFile)
		{
		variables.reportLogFile = reportLogFile;
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		variables.softwareVersion = softwareVersion;
		}

	public synchronized static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		variables.logger = logger;
		}

		
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

