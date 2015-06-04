package Behaviours;

import java.lang.reflect.InvocationTargetException;

import Agentes.*;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;


public class B_lanzaAgente extends Behaviour {
	
	
	public void action(){
		
		
		Object [] args = new String [2];
		args [0] = " Envir";
		args [1] = " Gui";
		
		PlatformController plataforma = myAgent.getContainerController();
		AgentController control;

		String Equipo=new String();
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
		
		for (int i = 0; i < 5; i++) {
			
		
			try {
				
				control = plataforma.createNewAgent(Equipo+i,"Agentes.Inicial", args);
				control.start();
				
			}
			catch (ControllerException e ) {
				e.printStackTrace( );
			
			}
			try{
				Thread.sleep(5);
				}
				catch(Exception e){}
			
			
			try {
				control = plataforma.createNewAgent("Jugador"+i,"Agentes.Enemigo", args) ;
				control.start();
				
			}
			catch (ControllerException e ) {
				e.printStackTrace( );
				
			
			}
			try{
			Thread.sleep(500);
			}
			catch(Exception e){}
		
			
			
			//myAgent.addBehaviour(new B_recibeMens());
		}
		myAgent.addBehaviour(new B_BusquedaPB());
	}
	public boolean done(){
		return true;
	}
}
