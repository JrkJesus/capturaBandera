package Behaviours;

import Agentes.Inicial;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.Behaviour;


public class B_enviaMove extends Behaviour{
  
	private AID Destino;
	private String Mensaje;
	private int Intencion;

	
	
	public B_enviaMove(AID dest, String mens){
		super();
		Destino = dest;
		Mensaje = mens;

		//Intencion = intenc;
	}
	
	public void action() {
		ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);
		mensaje.addReceiver(Destino);
		mensaje.setContent(Mensaje);
		if (!Mensaje.equals("-1"))
			myAgent.send(mensaje);
		else System.out.println("Mensaje erroneo");
		if(!Destino.equals(Inicial.getComandante()))
		myAgent.addBehaviour(new B_recibeMens());
	}
	
	public boolean done(){
		return true;
	}
}