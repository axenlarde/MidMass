package Work;


//Import
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import Utils.GreenFire;




/****************************************************
 * Classe qui gère la mise à jour de la fenêtre pour
 * afficher graphiquement l'état d'avancement
 ****************************************************/
public class Progression extends Thread
	{
	/************
	 * Variables
	 ************/
	MIDMass monMID;
	boolean done;
	float progress;
	int count;
	int step;
	boolean direction;
	Long debut;
	SimpleDateFormat formatter;
	
	/***************
	 * Constructeur
	 ***************/
	public Progression(MIDMass monMID)
		{
		this.monMID = monMID;
		count = 0;
		done = true;
		step = 0;
		direction = true;
		formatter = new SimpleDateFormat("HH:mm:ss:SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		//Freeze de l'interface
		monMID.valider.setEnabled(false);
		
		//Calcul du temps de traitement
		debut = System.currentTimeMillis();
		
		start();
		}
	
	public void run()
		{
		while(done)
			{
			for(int i=0; i<GreenFire.getStatus().size(); i++)
				{
				if(GreenFire.getStatus().get(i).getStatus().compareTo("waiting")==0)
					{
					//On ne fait rien
					}
				else if(GreenFire.getStatus().get(i).getStatus().compareTo("processing")==0)
					{
					//On ne fait rien
					}
				else if(GreenFire.getStatus().get(i).getStatus().compareTo("success")==0)
					{
					count ++;
					}
				else if(GreenFire.getStatus().get(i).getStatus().compareTo("error")==0)
					{
					count ++;
					}
				}
			
			MAJProgress();
			UpdateDoodle();
			
			if(finish())
				{
				done = false;
				
				//Calcul du temps écoulé
				Long TEcoule = System.currentTimeMillis() - debut;
				Date date = new Date();
				date.setTime(TEcoule);
				
				int errorCount = 0;
				for(int i=0;i<GreenFire.getStatus().size(); i++)
					{
					if(GreenFire.getStatus().get(i).getStatus().compareTo("error")==0)errorCount++;
					}
				StringBuffer buffResult = new StringBuffer("");
				buffResult.append("Fin d'inspection : "+formatter.format(date));
				if(errorCount != 0)buffResult.append("  -  Erreur : "+errorCount);
				
				monMID.ajoutInfo(buffResult.toString());
				monMID.valider.setText("Valider");
				}
			
			try
				{
				sleep(200);
				}
			catch(Exception e)
				{
				e.printStackTrace();
				}
			}
		
		//Degel de l'interface
		monMID.valider.setEnabled(true);
		
		//Reset du Status
		GreenFire.setStatus();
		
		System.out.println("Fin de progression");
		}
	
	public boolean finish()
		{
		boolean isFinish = true;
		for(int i=0; i< GreenFire.getStatus().size(); i++)
			{
			if(((GreenFire.getStatus().get(i).getStatus().compareTo("success")==0) || (GreenFire.getStatus().get(i).getStatus().compareTo("error")==0)) && isFinish)
				{
				isFinish = true;
				}
			else
				{
				isFinish = false;
				}
			}
		return isFinish;
		}
	
	public void MAJProgress()
		{
		float progress;
		monMID.progression.setText(Integer.toString(count)+"/"+Integer.toString(GreenFire.getStatus().size()));
		if(count != 0)
			{
			progress = (((float)count)/(float)GreenFire.getStatus().size())*100F;
			}
		else
			{
			progress = 0;
			}
		//System.out.println("avancement : "+progress);
		monMID.avancement.setValue((int)progress);
		
		count = 0;
		}
	
	private void UpdateDoodle()
		{
		//10
		switch(step)
			{
			case 0:
				monMID.valider.setText("||        |");break;
				
			case 1:
				monMID.valider.setText("| |       |");break;
				
			case 2:
				monMID.valider.setText("|  |      |");break;
				
			case 3:
				monMID.valider.setText("|   |     |");break;
				
			case 4:
				monMID.valider.setText("|    |    |");break;
				
			case 5:
				monMID.valider.setText("|     |   |");break;
				
			case 6:
				monMID.valider.setText("|      |  |");break;
				
			case 7:
				monMID.valider.setText("|       | |");break;
			
			case 8:
				monMID.valider.setText("|        ||");break;
			}
		
		step = direction?(step+1):(step-1);
		if(step==9)direction=false;
		if(step==0)direction=true;
		}
	
	/*Fin classe*//*AR :)*/
	}
