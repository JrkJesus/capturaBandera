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
		

			for (int j = 0; j < 5; j++) {
				
			
				try {
					control = plataforma.createNewAgent("agenteA_0"+j,"Agentes.Inicial", args) ;
					control.start();
				}
				catch (ControllerException e ) {
					e.printStackTrace( );
				
				}
		
				//setInfo();
				//Enemigo.setInf(Equipo,Clave);
				try {
					control = plataforma.createNewAgent("agenteB_0"+j,"Agentes.Enemigo", args) ;
					control.start();
				}
				catch (ControllerException e ) {
					e.printStackTrace( );
				
				}
			}
	
	}
}
