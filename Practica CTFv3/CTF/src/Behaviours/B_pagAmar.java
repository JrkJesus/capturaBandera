package Behaviours;

import Agentes.*;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames.InteractionProtocol;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.Behaviour;

public class B_pagAmar extends Behaviour{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3700119452748969312L;

	public void action(){
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("SERVIDOR_CTF");
		sd.setName("SERVIDOR_2015");
		template.addServices(sd);
		AID[ ] creadores = null;
		try {
			DFAgentDescription[ ] result = DFService.search(myAgent,template);
			creadores = new AID[result.length];
			
		
			for (int i=0; i< result.length; i++) {
				
				creadores[i] = result[i].getName();
				System.out.println(creadores[i].getLocalName());
				
				if(result.length==1){
					
					if(myAgent.getClass().getName().equals("Agentes.Inicial")){
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.addReceiver(creadores[i]);
						msg.setContent(Inicial.getEquipo()+Inicial.getClave());
						msg.setProtocol(InteractionProtocol.FIPA_REQUEST);
						Inicial.setSer(creadores[i]);
				
						B_conexPlat a= new B_conexPlat(myAgent,msg);
						myAgent.addBehaviour(a);
					
					}
					
					if(myAgent.getClass().getName().equals("Agentes.Enemigo")){
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.addReceiver(creadores[i]);
						msg.setContent(Enemigo.getEquipo()+Enemigo.getClave());
						msg.setProtocol(InteractionProtocol.FIPA_REQUEST);
						Enemigo.setSer(creadores[i]);
				
						B_conexPlat a= new B_conexPlat(myAgent,msg);
						myAgent.addBehaviour(a);
					
					}
				
				}
				else{
					System.out.println("Hay mas de un agente ofreciendo el servicio del servidor");
				}
				
			}
		} catch (FIPAException fe) {
			creadores = null; fe.printStackTrace();
		}
		if (creadores == null) {
			myAgent.doDelete();
		}
	}
	
	public boolean done(){
		
		return true;
	}
	
}