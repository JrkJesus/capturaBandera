package Behaviours;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class B_recibeMens extends Behaviour {

	
	
	boolean equis = false;
	public void action(){
		
		ACLMessage envio = myAgent.receive();
		if(envio!=null){
			equis=true;
			System.out.println("Estoy recibiendo: "+myAgent.getLocalName());
			System.out.println(envio.getSender().getLocalName() + ": " + envio.getContent());
			
			int al=B_Analiza.getalto();
			int an=B_Analiza.getancho();
			int vx=B_Analiza.getvisx();
			int vy=B_Analiza.getvisy();
			B_Analiza.setContador();
			myAgent.addBehaviour(new B_Analiza(envio, an, al, vx, vy ));
		/*	if(envio.getSender().getLocalName().equalsIgnoreCase("Duplicamelo")){
				
				int entero = Integer.parseInt(envio.getContent());
				entero*=2;
				String a= entero+"";
				System.out.println("Envio respuesta: "+myAgent.getLocalName());
				
				B_enviaMe A = new B_enviMensaj("Duplicamelo", a, 3 );
				myAgent.addBehaviour(A);}
		*/
		}
		else block();
	}
		public boolean done()
	    {
	        return equis;
	    }
	}
