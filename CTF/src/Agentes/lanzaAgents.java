package Agentes;


import java.util.Scanner;

import jade.core.Agent;
import Behaviours.*;
import jade.lang.acl.ACLMessage;


import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.domain.FIPANames.InteractionProtocol;
import jade.core.AID;

public class lanzaAgents extends Agent{

	private Scanner entrada;
	private String Equipo;
	private String Clave;
	
	public  void setInfo(){
		System.out.println("Introduzca Equipo:");
		entrada = new Scanner(System.in);
		Equipo=entrada.next();
		System.out.println("Introduzca Clave");
		Clave=entrada.next();
	}
	
	public void setup(){
		
	//	int NumAgentes=3;
	
		setInfo();
		Inicial.setInf(Equipo, Clave);
		if(Equipo.equals("7"))
		Enemigo.setInf("8",Clave);
		else Enemigo.setInf("7", Clave);
		
		Object [] args = new String [2];
		args [0] = " Envir";
		args [1] = " Gui";
		
		PlatformController plataforma = this.getContainerController();
		AgentController control;
		int j=5;
		for (int i=0; i<5; i++){


			
			try {
				control = plataforma.createNewAgent("Jugador"+i,"Agentes.Inicial", args) ;
				control.start();
				
			}
			catch (ControllerException e ) {
				e.printStackTrace( );
			
			}
			try{
				Thread.sleep(5);
				}
				catch(Exception e){}
			
			//setInfo();
			//Enemigo.setInf(Equipo,Clave);
			try {
				control = plataforma.createNewAgent("Jugador"+j,"Agentes.Enemigo", args) ;
				control.start();
				j++;
			}
			catch (ControllerException e ) {
				e.printStackTrace( );
				
			
			}
			try{
			Thread.sleep(250);
			}
			catch(Exception e){}
		}
	
	}
}
