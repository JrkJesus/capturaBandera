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
	
	private static int[] baseEnemiga=new int[2];
	private static int[] baseAmiga=new int[2];
	
	/**
	 * 
	 */
	
	public static int[] getBaseEne(){
		return baseEnemiga;
	}
	
	public static int[] getBaseAmi(){
		return baseAmiga;
	}
	
	public static void setBaseEne(int[] a){
		baseEnemiga[0]=a[0];
		baseEnemiga[1]=a[1];
	}
	
	public static void setBaseAmi(int[] a){
		baseAmiga[0]=a[0];
		baseAmiga[1]=a[1];
	}
	
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
