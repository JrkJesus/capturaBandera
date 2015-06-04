package Behaviours;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames.InteractionProtocol;
import jade.lang.acl.ACLMessage;
import Agentes.Inicial;
import Agentes.lanzaAgents;

public class B_recibeMens extends CyclicBehaviour {

	
	
	//boolean equis = false;
	public void action(){
		
		ACLMessage envio = myAgent.receive();
		if(envio!=null){
		
			/*if(envio.getContent().equals("9")){
				System.out.println("Ganaste!");
			}*/
			//else{
			
			//equis=true;
			System.out.println("Estoy recibiendo y soy: "+myAgent.getLocalName());
			System.out.println(envio.getSender().getLocalName() + ": " + envio.getContent());
			
			
			//Si me lo ha enviado el entorno   entorno-jugadores
			//Si me lo ha enviado uno de nuestros agentes  agentes-brigadier
			//si me lo ha enviado el brigadier   brigadier-agentes
			AID emisor=envio.getSender();
			String mens=envio.getContent();
			
			 if(myAgent.getClass().getName().equals("Agentes.Brigadier")){
				
				boolean esCompañero=false;
				Class[] methodParamTypes = new Class[]{AID.class};
 				try {
 					esCompañero=(boolean)myAgent.getClass().getMethod("esCompañero", methodParamTypes).invoke(myAgent, new Object[]{emisor});
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
					
 				if(esCompañero){
 					//Aquí va lo que tiene que hacer brigadier con el mensaje
 					if(mens.equals("Valar Morghulis")){
 						AID compañero;
 						for(int i=0; i<5; i++){
 							
 							Class[] methodParamTypes2 = new Class[]{int.class};
 			 				try {
 			 					compañero=(AID)myAgent.getClass().getMethod("getCompañero", methodParamTypes2).invoke(myAgent, new Object[]{i});
 			 					myAgent.addBehaviour(new B_enviaMove(compañero,"Valar Dohaeris"));
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
 							
 							
 						}
 						myAgent.addBehaviour(new B_recibeMens());
 						System.out.println("Brigadier recibe Valar Morghulis y envia Valar Dohaeris");
 					}
 					
 					if(mens.startsWith("W")){
 						System.out.println("El mensaje que recibe brigadier empieza por W");
 						int indicesfinales[]=new int[3];
 						int y=0,x=0,ye=0,xe=0;
 						int contador=0;
 						int i=1;
 						int primpos=1;
 						while(contador<=3&&i<mens.length()){
 							if(mens.substring(i,i+1).equals("W")){
 								indicesfinales[contador]=i;
 								contador++;
 							}
 							i++;
 						}
 						
 						y=Integer.parseInt(mens.substring(1,indicesfinales[0]));
 						x=Integer.parseInt(mens.substring(indicesfinales[0]+1,indicesfinales[1]));
 						ye=Integer.parseInt(mens.substring(indicesfinales[1]+1,indicesfinales[2]));
 						xe=Integer.parseInt(mens.substring(indicesfinales[2]+1,mens.length()));
 						
 						System.out.println("Mensaje con pos recibido del brig:"+mens +" "+ y+" "+x+" "+ye+" "+xe);
 						int num=0;
 						Class[] methodParamTypes3 = new Class[]{};
 						Class[] methodParamTypes4 = new Class[]{AID.class};
 						Class[] methodParamTypes5 = new Class[]{int.class,int.class,int.class,int.class};
 						Class[] methodParamTypes6 = new Class[]{int.class};
 		 				try {
 		 					myAgent.getClass().getMethod("dispuesto", methodParamTypes3).invoke(myAgent, new Object[]{});
 		 					myAgent.getClass().getMethod("reordenarCompañeros", methodParamTypes4).invoke(myAgent, new Object[]{emisor});
 		 					myAgent.getClass().getMethod("inforBrigadier", methodParamTypes5).invoke(myAgent, new Object[]{y,x,ye,xe});
 		 					num=Integer.parseInt(myAgent.getClass().getMethod("getdispuestos", methodParamTypes3).invoke(myAgent, new Object[]{}).toString());
 		 					
 		 					System.out.println("Antes del If llego");
 		 					
 		 					if(num>=2){
 		 						System.out.println("Y entro en el IF");
 		 						//myAgent.getClass().getMethod("ordenar2aid", methodParamTypes3).invoke(myAgent, new Object[]{});
 		 						boolean diferentes=false;
 		 						AID aux, aux2;
 		 							
 		 							
 		 		 					aux=(AID)myAgent.getClass().getMethod("getCompañero", methodParamTypes6).invoke(myAgent, new Object[]{0});
 		 		 					aux2=aux;
 		 		 					int n=1;
 		 		 					while(aux2==aux){
 		 		 						
 	 		 		 					aux2=(AID)myAgent.getClass().getMethod("getCompañero", methodParamTypes6).invoke(myAgent, new Object[]{n});
 	 		 		 					n++;
 		 		 					
 		 		 					}
 		 		 					myAgent.addBehaviour(new B_enviaMove(aux, "Buona Sorte"));
 		 		 					myAgent.addBehaviour(new B_enviaMove(aux2, "Buona Sorte"));
 		 		 					System.out.println("Brigadier manda a matar Jugador "+aux.getLocalName() );
 		 						
 		 						myAgent.getClass().getMethod("setdispuestos", methodParamTypes3).invoke(myAgent, new Object[]{});
 		 					}  
 		 					else{
 		 						myAgent.addBehaviour(new B_enviaMove(emisor, "Espera"));;
 		 					}
 					
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
 						// Seguir aqui
 						// El agente debe recibirlo y activar orden.
 						//En los posteriores mensajes del servidor, actuará con otro setfinal.
 					}
 				}
				
			}
			
			if(emisor.equals(Inicial.getSer())){
				//System.out.println("ENTROOOOOOO");
		//De aqui para abajo segundo mensaje del emisor
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
			
			
			
			System.out.println("Me han enviado: "+mens);
			if(mens.length()>1){
				Class[] methodParamTypes6 = new Class[]{};
				boolean espe=false;
 				try {
 					espe=(boolean)myAgent.getClass().getMethod("getesperando", methodParamTypes6).invoke(myAgent, new Object[]{});

 					
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
 				int d=0;
				if(espe)
					d=1;
				B_Analiza A=new B_Analiza(envio, an, al, vx, vy, Equipo,myAgent.getClass().getName(),d);
				
				if(A.TheyHaveOurFlag()){
					if(!B_Analiza.informando()){
						System.out.println("Envio Valar Morgulis a: "+Inicial.getComandante());
						myAgent.addBehaviour(new B_enviaMove(Inicial.getComandante(),"Valar Morghulis"));//MODIFICAR
						B_Analiza.informar();
						
					}
					boolean peticion=false, orden=false;
					Class[] methodParamTypes2 = new Class[]{};
	 				try {
	 					peticion=(boolean)myAgent.getClass().getMethod("getPeticionPos", methodParamTypes2).invoke(myAgent, new Object[]{});
	 					orden=(boolean)myAgent.getClass().getMethod("getOrdenRecibida", methodParamTypes2).invoke(myAgent, new Object[]{});

	 					
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
	 				System.out.println("Esto si que es raro");
	 				System.out.println("Este es el valor de la peticion "+peticion);
				if(!espe&&peticion&&!orden){
					
					
					
					boolean tengoBandera=A.tengoBandera();
					System.out.println("Paso1");
					
	 					System.out.println("Valor de la Bandera"+tengoBandera);
	 					if(!tengoBandera){
	 						System.out.println("Me excuso o voy Mensaje:" +"W"+A.posForBrigadier() + "y soy: "+myAgent.getLocalName());
	 						myAgent.addBehaviour(new B_enviaMove(Inicial.getComandante(),"W"+A.posForBrigadier()));
	 					}
					
				}
				if(orden){
					
					
					System.out.println("Modifico el set final");
					A.recuperaBandera();//Set final 2 en el que se redirige al enemigo con bandera
				}
				B_Aestrella B= new B_Aestrella(A, myAgent.getClass().getName());
				
				String a="0";
				if(B.getCaminoSol().size()>1){
					System.out.println("PASO: "+B.getCaminoSol().get(1).getMove());
					a=Integer.toString(B.getCaminoSol().get(1).getMove());
				}
				
			
				AID aux=Inicial.getSer();
			
				
				
				myAgent.addBehaviour(new B_enviaMove(aux, a));
				
				}
				else if(!A.TheyHaveOurFlag()){
					
					Class[] methodParamTypes2 = new Class[]{};
					boolean orden=false;
					try {
	 					orden=(boolean)myAgent.getClass().getMethod("getOrdenRecibida", methodParamTypes2).invoke(myAgent, new Object[]{});
	 					if(orden){
		 					myAgent.getClass().getMethod("setOrdenRecibida", methodParamTypes2).invoke(myAgent, new Object[]{});

	 					}
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
					
					
					
					B_Aestrella B= new B_Aestrella(A, myAgent.getClass().getName());
					
					String a="0";
					if(B.getCaminoSol().size()>1){
						System.out.println("PASO: "+B.getCaminoSol().get(1).getMove());
						a=Integer.toString(B.getCaminoSol().get(1).getMove());
					}
					
					myAgent.addBehaviour(new B_enviaMove(Inicial.getSer(), a));
				
				}
			}
			/*else{
				ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
				msg1.addReceiver(Inicial.getSer());
				msg1.setContent(Equipo+"a");
				msg1.setProtocol(InteractionProtocol.FIPA_REQUEST);
				myAgent.addBehaviour(new B_conexPlat(myAgent,msg1));
			}*/
			}
			//System.out.println(emisor);
			else if(emisor.equals(Inicial.getComandante())){
				if(mens.equals("Valar Dohaeris")){
					System.out.println("Carl");
					Class[] methodParamTypes2 = new Class[]{};
		 				try {
		 					System.out.println("Carl2");
		 					myAgent.getClass().getMethod("setPeticionPos", methodParamTypes2).invoke(myAgent, new Object[]{});
		 					System.out.println("Activarlo lo activa");
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
		 			myAgent.addBehaviour(new B_recibeMens());
				}
				if(mens.equals("Espera")){
					System.out.println("Agentes reciben el penultimo mensaje");
					Class[] methodParamTypes2 = new Class[]{};
		 				try {
		 					myAgent.getClass().getMethod("setesperar", methodParamTypes2).invoke(myAgent, new Object[]{});
		 					
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
		 			myAgent.addBehaviour(new B_recibeMens());
				}
				if(mens.equals("Buona Sorte")){
					System.out.println("Agentes reciben el ultimo mensaje");
					Class[] methodParamTypes2 = new Class[]{boolean.class};
		 				try {
		 					myAgent.getClass().getMethod("setOrdenRecibida", methodParamTypes2).invoke(myAgent, new Object[]{true});
		 					
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
		 			myAgent.addBehaviour(new B_recibeMens());
				}
				
				
			}
			
			}
		else block();
	}
	/*	public boolean done()
	    {
	        return equis;
	    }*/
	}
