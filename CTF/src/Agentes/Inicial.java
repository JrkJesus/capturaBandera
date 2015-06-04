package Agentes;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import jade.core.Agent;
import Behaviours.*;
import Agentes.Brigadier;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPANames.InteractionProtocol;
import jade.core.AID;


public class Inicial extends Agent{

	private static AID Ser;
	private static AID comandante;
	private static String Equipo;
	private static String Clave;
	
	private boolean esperar=false;
	private boolean peticionPos=false;
	private boolean ordenRecibida=false;
	private int ordenX;
	private int ordenY;
	
	
	private static int[] baseEnemiga=new int[2];
	private static int[] baseAmiga=new int[2];
	
	/**
	 * 
	 */
	public void setOrdenRecibida(){
		
		if(!ordenRecibida)
			ordenRecibida=true;
		else ordenRecibida=false;
		
	}
public void setesperar(){
		
		if(!esperar)
			esperar=true;
		else esperar=false;
		
	}
	public void setPeticionPos(){
		
		if(!peticionPos)
			peticionPos=true;
		else peticionPos=false;
	}
	public void setOrden(int y, int x){
		
		ordenY=y;
		ordenX=x;//No hace falta. El brigadier envia un ataca a los jugadores 2 jugadores que esten más cerca del enemigo. Estos buscarán siempre al 6
		//Si el 6 desaparece enviarán un mensaje (MISION CUMPLIDA) al brigadier, que quitará las selecciones informado y ordenado. Informando lo quitará el agente que informe.
	}
	
	public boolean getOrdenRecibida(){
		return ordenRecibida;
	}
	public boolean getesperando(){
		return esperar;
	}
	public boolean getPeticionPos(){
		return peticionPos;
	}
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
	
	public static void setComandante(AID A){
		
		comandante=A;
		
	}
	
	public static AID getComandante(){
		return comandante;
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
