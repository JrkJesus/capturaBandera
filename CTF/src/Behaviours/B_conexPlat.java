package Behaviours;

import java.lang.reflect.InvocationTargetException;

import Agentes.Inicial;
import jade.core.behaviours.DataStore;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames.InteractionProtocol;
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
	public void handleRefuse(ACLMessage msg) {
		System.out.printf("Rechazado"); 
		try{
			Thread.sleep(500);
			}
			catch(Exception e){}
		ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
		msg1.addReceiver(Inicial.getSer());
		
		String Equipo1= new String();
		Class[] methodParamTypes = new Class[]{};
		try {
			Equipo1=myAgent.getClass().getMethod("getEquipo", methodParamTypes).invoke(myAgent.getClass(), new Object[]{}).toString();
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
		
		msg1.setContent(Equipo1+"a");
		//msg1.setContent(Inicial.getEquipo()+Inicial.getClave());
		//msg1.setProtocol(InteractionProtocol.FIPA_PROPOSE);
		
		if(msg1.getContent().equals("-1"))
			System.out.println("Intento reconectarme con un mensaje erroneo");
		myAgent.addBehaviour(new B_conexPlat(myAgent, msg1));
		myAgent.addBehaviour(new B_recibeMens());
	
	}
	
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
		
		if(A.TheyHaveOurFlag()){
			if(!B_Analiza.informando()){
			myAgent.addBehaviour(new B_enviaMove(Inicial.getComandante(),"Valar Morghulis"));//MODIFICAR
			B_Analiza.informar();
			}
		
			B_Aestrella B =new B_Aestrella(A, myAgent.getClass().getName()); 
		
			String a="0";
			if(B.getCaminoSol().size()>1){
				System.out.println("PASO: "+B.getCaminoSol().get(1).getMove());
				a=Integer.toString(B.getCaminoSol().get(1).getMove());
			}
			
			myAgent.addBehaviour(new B_enviaMove(Inicial.getSer(), a));
			//myAgent.addBehaviour(new B_enviaMove(Inicial.getComandante(),"Valar Morgulis"));
	
					
			System.out.println("Fin");
		}
		else if(!A.TheyHaveOurFlag()){
			
			B_Aestrella B= new B_Aestrella(A, myAgent.getClass().getName());
			
			String a="0";
			if(B.getCaminoSol().size()>1){
				System.out.println("PASO: "+B.getCaminoSol().get(1).getMove());
				a=Integer.toString(B.getCaminoSol().get(1).getMove());
			}
			
			myAgent.addBehaviour(new B_enviaMove(Inicial.getSer(), a));
			
			}
	
	}
	
	public void handleNotUnderstood(ACLMessage msg) {System.out.printf("No entendido");  }
	public void handleOutOfSequence(ACLMessage msg) { System.out.printf("Te pasaste"); }
	
}
