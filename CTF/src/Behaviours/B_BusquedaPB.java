package Behaviours;

import java.lang.reflect.InvocationTargetException;

import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;

public class B_BusquedaPB extends Behaviour {
		
	public void action(){
			
		AMSAgentDescription [] agentes = null;
        System.out.println("El agente " + myAgent.getAID().getName() + " se ha iniciado.");
 
        try
        {
            SearchConstraints restricciones = new SearchConstraints();
            restricciones.setMaxResults ( new Long(-1) ); /// Todos
            agentes = AMSService.search( myAgent, new AMSAgentDescription (), restricciones );
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
  
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
        


        int contador=1;
        
        for(int j=0; j<agentes.length;j++){
        
        	
        	AID idaux = agentes[j].getName();
        	String nombreAID=idaux.getLocalName();
       
        	if(nombreAID.startsWith(Equipo)&&nombreAID.length()<4){
        			
        		String B=nombreAID.substring(1, nombreAID.length());
        		int num=-1;
        					
        		try{
        				
        			num=Integer.parseInt(B);
        			
        		}
        		catch(Exception e){
        			System.out.println(nombreAID +" no es legal.");
        		}
        			
        		if(num!=-1 && num<5){
        				
        			Class[] methodParamTypes3 = new Class[]{AID.class};
    				try {
    					myAgent.getClass().getMethod("setCompañero", methodParamTypes3).invoke(myAgent, new Object[]{agentes[j].getName()});
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
    					
    			/*	try {
    					Class[] methodParamTypes2 = new Class[]{};
    					String Lista;
    					System.out.println("Entrar entro");
    					Lista=myAgent.getClass().getMethod("getCompañeros", methodParamTypes2).invoke(myAgent, new Object[]{}).toString();
    					System.out.println(Lista);
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
        		*/
        		}
        	}
        }
        	
        myAgent.addBehaviour(new B_recibeMens());
    }
		

	public boolean done(){
			
		return true;
	}
		
}


