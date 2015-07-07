package Behaviours;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
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
