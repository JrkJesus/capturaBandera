package Behaviours;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import Agentes.lanzaAgents;

public class B_recibeMens extends Behaviour {

	
	
	boolean equis = false;
	public void action(){
		
		ACLMessage envio = myAgent.receive();
		if(envio!=null){
		
			if(envio.getContent().equals("9")){
				System.out.println("Ganaste!");
			}
			else{
			equis=true;
			System.out.println("Estoy recibiendo: "+myAgent.getLocalName());
			System.out.println(envio.getSender().getLocalName() + ": " + envio.getContent());
	
			int al=B_Analiza.getalto();
			int an=B_Analiza.getancho();
			int vx=B_Analiza.getvisx();
			int vy=B_Analiza.getvisy();
			B_Analiza.setContador();
			
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
			
			B_Analiza A=new B_Analiza(envio, an, al, vx, vy, Equipo,myAgent.getClass().getName());
			myAgent.addBehaviour(new B_Aestrella(A, myAgent.getClass().getName()));
			//El codigo inferior comentado entre los primeros /* y */ es una prueba
			//En la que hemos conseguido acceder al valor de los metodos getEquipo de
			//la clase especifica del agente.
		/*	Class[] methodParamTypes = new Class[]{};
		

			try {
				System.out.println("Equipo:"+ myAgent.getClass().getMethod("getEquipo", methodParamTypes).invoke(myAgent.getClass(), new Object[]{}));
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
			}*/

	

			}
		}
		else block();
	}
		public boolean done()
	    {
	        return equis;
	    }
	}
