package Agentes;

import java.util.Scanner;

import jade.core.Agent;
import Behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPANames.InteractionProtocol;
import jade.core.AID;


public class Inicial extends Agent{

	private static AID Ser;
	private static String Equipo;
	private static String Clave;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scanner entrada;
	
	public  void setInfo(){
		System.out.println("Introduzca Equipo:");
		entrada = new Scanner(System.in);
		Equipo=entrada.next();
		System.out.println("Introduzca Clave");
		Clave=entrada.next();
	}
	
	public static void setInf(String Eq, String Cl){
		Equipo=Eq;
		Clave=Cl;
	}
	
	public static void setSer(AID A){
		
		Ser=A;
		
	}
	
	public static AID getSer(){
		return Ser;
	}
	
	public static int getEquipoI(){
		return Equipo.toCharArray()[0]-48-6;
	}
	public static String getEquipo(){
		return Equipo;
	}
	
	public static String getClave(){
		return Clave;
	}
	
	public void setup(){
		
		this.addBehaviour(new B_pagAmar());
		
		
	}
}
