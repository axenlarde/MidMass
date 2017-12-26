package Misc;

/**********************************
 * Class used to store scan device result
 * 
 * @author RATEL Alexandre
 **********************************/
public class resultScan
	{
	/**
	 * Variables
	 */
	private String status,desc;
	
	
	public resultScan(String status)
		{
		this.status = status;
		this.desc = "Nothing";
		}


	public synchronized String getStatus()
		{
		return status;
		}

	public synchronized void setStatus(String status)
		{
		this.status = status;
		}

	public synchronized String getDesc()
		{
		return desc;
		}

	public synchronized void setDesc(String desc)
		{
		this.desc = desc;
		}
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

