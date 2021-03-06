package Agentes;

import java.util.Scanner;

import jade.core.Agent;
import Behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPANames.InteractionProtocol;
import jade.core.AID;


public class Inicial extends Agent{

	
	private static String Equipo;
	private static String Clave;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scanner entrada;
	
	public void setInfo(){
		System.out.println("Introduzca Equipo:");
		entrada = new Scanner(System.in);
		Equipo=entrada.next();
		System.out.println("Introduzca Clave");
		Clave=entrada.next();
	}
	
	
	public static String getEquipo(){
		return Equipo;
	}
	
	public void setup(){
		//String Equipo="8";
		//String Clave="a";
		setInfo();
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("entorno3213",AID.ISLOCALNAME));
		msg.setContent(Equipo+Clave);
		msg.setProtocol(InteractionProtocol.FIPA_REQUEST);
		
		
		B_conexPlat a= new B_conexPlat(this,msg);
		this.addBehaviour(a);

		
	}
}
