package Misc;

import java.lang.reflect.Field;

import Utils.variables.infoCDPType;
import Utils.variables.infoDeviceType;
import Utils.variables.infoMessageType;
import Utils.variables.infoNetworkType;

/**********************************
 * Class used to store device data
 * 
 * @author RATEL Alexandre
 **********************************/
public class device
	{
	/**
	 * Variable
	 */
	private String ip,
	MAC,
	SN,
	DN,
	loadID,
	bootLoadID,
	versionID,
	modelNumber,
	time,
	date,
	userLocale,
	tftpServer1,
	tftpServer2,
	ccm1,
	ccm2,
	ccm3,
	userLocaleVersion,
	DHCP,
	AlternateTFTP,
	Message1,
	Message2,
	Message3,
	Message4,
	ITLStatus,
	CDPNeighborDeviceId,
	CDPNeighborIP,
	CDPNeighborPort,
	PortSpeed;
	
	public device(String ip)
		{
		this.ip = ip;
		Utils.variables.getLogger().debug("Nouveau device : "+ip);
		
		//Init variables
		MAC = "";
		SN = "";
		DN = "";
		loadID = "";
		bootLoadID = "";
		versionID = "";
		modelNumber = "";
		time = "";
		date = "";
		userLocale = "";
		tftpServer1 = "";
		tftpServer2 = "";
		ccm1 = "";
		ccm2 = "";
		ccm3 = "";
		userLocaleVersion = "";
		DHCP = "";
		AlternateTFTP ="";
		Message1 = "";
		Message2 = "";
		Message3 = "";
		Message4 = "";
		ITLStatus = "";
		CDPNeighborDeviceId = "";
		CDPNeighborIP = "";
		CDPNeighborPort = "";
		PortSpeed = "";
		}
	
	public String getData() throws Exception
		{
		StringBuffer st = new StringBuffer("");
		Field[] tab = this.getClass().getDeclaredFields();
		for(int i=0; i<tab.length; i++)
			{
			st.append(((String)tab[i].get(this)).trim());
			if(i != tab.length-1)
				{
				st.append(",");
				}
			}
		return st.toString();
		}
	
	public void setInfo(infoDeviceType id, String data)
		{
		if(id.equals(infoDeviceType.MACAddress))setMAC(data);
		if(id.equals(infoDeviceType.serialNumber))setSN(data);
		if(id.equals(infoDeviceType.phoneDN))setDN(data);
		if(id.equals(infoDeviceType.appLoadID))setLoadID(data);
		if(id.equals(infoDeviceType.bootLoadID))setBootLoadID(data);
		if(id.equals(infoDeviceType.versionID))setVersionID(data);
		if(id.equals(infoDeviceType.modelNumber))setModelNumber(data);
		if(id.equals(infoDeviceType.time))setTime(data);
		if(id.equals(infoDeviceType.date))setDate(data);
		}
	
	public void setInfo(infoNetworkType nt, String data)
		{
		if(nt.equals(infoNetworkType.UserLocale))setUserLocale(data);
		if(nt.equals(infoNetworkType.TFTPServer1))setTftpServer1(data);
		if(nt.equals(infoNetworkType.TFTPServer2))setTftpServer2(data);
		if(nt.equals(infoNetworkType.CallManager1))setCcm1(data);
		if(nt.equals(infoNetworkType.CallManager2))setCcm2(data);
		if(nt.equals(infoNetworkType.CallManager3))setCcm3(data);
		if(nt.equals(infoNetworkType.UserLocaleVersion))setUserLocaleVersion(data);
		if(nt.equals(infoNetworkType.DHCPServer))setDHCP(data);
		if(nt.equals(infoNetworkType.AltTFTP))setAlternateTFTP(data);
		}

	public void setInfo(infoMessageType mt, String[] data)
		{
		if(mt.equals(infoMessageType.Message1))
			{
			Message1 = data[0];
			Message2 = data[1];
			Message3 = data[2];
			Message4 = data[3];
			ITLStatus = data[4];
			}
		}
	
	public void setInfo(infoCDPType ct, String data)
		{
		if(ct.equals(infoCDPType.CDPNeighborDeviceId))setCDPNeighborDeviceId(data);
		if(ct.equals(infoCDPType.CDPNeighborIP))setCDPNeighborIP(data);
		if(ct.equals(infoCDPType.CDPNeighborPort))setCDPNeighborPort(data);
		if(ct.equals(infoCDPType.PortSpeed))setPortSpeed(data);
		}

	public String getIp()
		{
		return ip;
		}


	public void setIp(String ip)
		{
		this.ip = ip;
		}


	public String getMAC()
		{
		return MAC;
		}


	public void setMAC(String mAC)
		{
		MAC = mAC;
		}


	public String getSN()
		{
		return SN;
		}


	public void setSN(String sN)
		{
		SN = sN;
		}


	public String getDN()
		{
		return DN;
		}


	public void setDN(String dN)
		{
		DN = dN;
		}


	public String getLoadID()
		{
		return loadID;
		}


	public void setLoadID(String loadID)
		{
		this.loadID = loadID;
		}


	public String getBootLoadID()
		{
		return bootLoadID;
		}


	public void setBootLoadID(String bootLoadID)
		{
		this.bootLoadID = bootLoadID;
		}


	public String getVersionID()
		{
		return versionID;
		}


	public void setVersionID(String versionID)
		{
		this.versionID = versionID;
		}


	public String getModelNumber()
		{
		return modelNumber;
		}


	public void setModelNumber(String modelNumber)
		{
		this.modelNumber = modelNumber;
		}


	public String getTime()
		{
		return time;
		}


	public void setTime(String time)
		{
		this.time = time;
		}


	public String getDate()
		{
		return date;
		}


	public void setDate(String date)
		{
		this.date = date;
		}


	public String getUserLocale()
		{
		return userLocale;
		}


	public void setUserLocale(String userLocale)
		{
		this.userLocale = userLocale;
		}


	public String getCcm1()
		{
		return ccm1;
		}


	public void setCcm1(String ccm1)
		{
		this.ccm1 = ccm1;
		}


	public String getCcm2()
		{
		return ccm2;
		}


	public void setCcm2(String ccm2)
		{
		this.ccm2 = ccm2;
		}


	public String getCcm3()
		{
		return ccm3;
		}

	public void setCcm3(String ccm3)
		{
		this.ccm3 = ccm3;
		}

	public String getUserLocaleVersion()
		{
		return userLocaleVersion;
		}

	public void setUserLocaleVersion(String userLocaleVersion)
		{
		this.userLocaleVersion = userLocaleVersion;
		}

	public String getTftpServer1()
		{
		return tftpServer1;
		}

	public void setTftpServer1(String tftpServer1)
		{
		this.tftpServer1 = tftpServer1;
		}

	public String getCDPNeighborDeviceId()
		{
		return CDPNeighborDeviceId;
		}

	public void setCDPNeighborDeviceId(String cDPNeighborDeviceId)
		{
		CDPNeighborDeviceId = cDPNeighborDeviceId;
		}

	public String getCDPNeighborIP()
		{
		return CDPNeighborIP;
		}

	public void setCDPNeighborIP(String cDPNeighborIP)
		{
		CDPNeighborIP = cDPNeighborIP;
		}

	public String getCDPNeighborPort()
		{
		return CDPNeighborPort;
		}

	public void setCDPNeighborPort(String cDPNeighborPort)
		{
		CDPNeighborPort = cDPNeighborPort;
		}

	public String getPortSpeed()
		{
		return PortSpeed;
		}

	public void setPortSpeed(String portSpeed)
		{
		PortSpeed = portSpeed;
		}

	public String getTftpServer2()
		{
		return tftpServer2;
		}

	public void setTftpServer2(String tftpServer2)
		{
		this.tftpServer2 = tftpServer2;
		}

	public String getMessage1()
		{
		return Message1;
		}

	public void setMessage1(String message1)
		{
		Message1 = message1;
		}

	public String getMessage2()
		{
		return Message2;
		}

	public void setMessage2(String message2)
		{
		Message2 = message2;
		}

	public String getMessage3()
		{
		return Message3;
		}

	public void setMessage3(String message3)
		{
		Message3 = message3;
		}

	public String getMessage4()
		{
		return Message4;
		}

	public void setMessage4(String message4)
		{
		Message4 = message4;
		}

	public String getITLStatus()
		{
		return ITLStatus;
		}

	public void setITLStatus(String iTLStatus)
		{
		ITLStatus = iTLStatus;
		}

	public String getDHCP()
		{
		return DHCP;
		}

	public void setDHCP(String dHCP)
		{
		DHCP = dHCP;
		}

	public String getAlternateTFTP()
		{
		return AlternateTFTP;
		}

	public void setAlternateTFTP(String alternateTFTP)
		{
		AlternateTFTP = alternateTFTP;
		}
	
	/*2017*//*RATEL Alexandre 8)*/
	}

