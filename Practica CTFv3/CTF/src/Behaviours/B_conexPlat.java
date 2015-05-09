package Behaviours;

import java.lang.reflect.InvocationTargetException;

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
	
	public void handleInform(ACLMessage msg) {
		
		System.out.printf(" Inf: "+msg.getContent()+"\n");
		
		String Equipo= new String();
		Class[] methodParamTypes = new Class[]{};
		try {
			Equipo=myAgent.getClass().getMethod("getEquipo", methodParamTypes).invoke(myAgent.getClass(), new Object[]{}).toString();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		B_Analiza A=new B_Analiza(msg, myAgent.getClass().getName(), Equipo);
		myAgent.addBehaviour(new B_Aestrella(A, myAgent.getClass().getName())); 
	
	
	}
	
	public void handleNotUnderstood(ACLMessage msg) {System.out.printf("No entendido");  }
	public void handleOutOfSequence(ACLMessage msg) { System.out.printf("Te pasaste"); }
	
}
