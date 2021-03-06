package Behaviours;

import jade.core.behaviours.DataStore;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

public class B_conexPlat extends SimpleAchieveREInitiator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7848428522328206406L;

	public B_conexPlat(Agent a, ACLMessage msg){
		
		super(a,msg);

	}
	
	public B_conexPlat(Agent a, ACLMessage msg, DataStore store){
		
		super(a, msg, store);

	}
	//handleInform nos da la posicion en la que estamos!
	public void handleAgree(ACLMessage msg) {System.out.printf("Aceptado");}
	public void handleRefuse(ACLMessage msg) {System.out.printf("Rechazado");  }
	public void handleInform(ACLMessage msg) {System.out.printf(" Inf: "+msg.getContent()+"\n"); myAgent.addBehaviour(new B_Analiza(msg)); }
	public void handleNotUnderstood(ACLMessage msg) {System.out.printf("No entendido");  }
	public void handleOutOfSequence(ACLMessage msg) { System.out.printf("Te pasaste"); }
	
}
