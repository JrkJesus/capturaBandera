package Behaviours;

import java.util.ArrayList;

import Agentes.Inicial;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.domain.FIPANames.InteractionProtocol;
import jade.lang.acl.ACLMessage;

public class B_Aestrella extends Behaviour {

	private ArrayList<B_Analiza> Abiertos;        // Lista de Estados abiertos (A�n no visitados).
	private ArrayList<B_Analiza> Cerrados;        // Lista de Estados cerrados (Ya visitados).
	private ArrayList<B_Analiza> CaminoSol;       // Lista que contendr� el camino solucionado.
	private int visitados;					   // Experimentacion. Lleva la cuenta de los nodos Visitados.
	private int abiertosMax;                   // Experimentaci�n. Lleva la cuenta del n�mero m�ximo de Estados Abiertos.
	private int cerradosMax;                   // Experimentaci�n. Lleva la cuenta del n�mero m�ximo de Estados Cerrados.
	private int solnofound;
	private String nombClase;
	
	public B_Aestrella(B_Analiza inicial, String A) {
		Abiertos=new ArrayList<B_Analiza>();
		Cerrados=new ArrayList<B_Analiza>();
		CaminoSol = new ArrayList<B_Analiza>();
		visitados=0;
		abiertosMax=0;
		cerradosMax=0;
		solnofound=0;
		AStar(inicial);
		nombClase=A;
		
	}
	
	public void AStar(B_Analiza inicial){
		Abiertos.add(inicial);
		B_Analiza actual=Abiertos.get(0);
		ArrayList<B_Analiza> hijos=new ArrayList<B_Analiza>();
		
		while((actual.esFinal()==false)&&(!Abiertos.isEmpty())){
			
			Abiertos.remove(0);
			Cerrados.add(actual);
			eCerradosmax();
			hijos=actual.genHijos();
			repeat(hijos);
		
			int i=0,j=0;
			boolean Correcto=false;
			while(j<hijos.size()){
				while(i<Abiertos.size()&&(!Correcto)){
					if(Abiertos.get(i).getCosteTotal()>hijos.get(j).getCosteTotal()){
						
						Abiertos.add(i, hijos.get(j));
						eAbiertosmax();
						Correcto=true;
				
					}
				
					i++;
				}
				
				if(!Correcto){
				
					Abiertos.add(hijos.get(j));
					eAbiertosmax();
				}
				
				j++;
				i=0;
				Correcto=false;
			}
	
			if(!Abiertos.isEmpty()){
				visitados++;
				int posicion=AbiertosMejorCoste();
				actual=Abiertos.get(posicion);
			}
		}
		if(actual.esFinal()){
			
			CaminoSol.add(actual);
			solucionar();
			
		}
		else{ System.out.println("Solucion no encontrada.");
		solnofound=1;
		}
	}
	
	public int AbiertosMejorCoste(){
		
		int MenorCoste=Abiertos.get(0).getCosteTotal();
		int posicion=0;
		
		for(int i=0; i<Abiertos.size()-1; i++){
			if(MenorCoste>Abiertos.get(i+1).getCosteTotal()){
				MenorCoste=Abiertos.get(i+1).getCosteTotal();
				posicion=i+1;
			}
		}
		
		return posicion;
	}
	
	
	public void solucionar(){
		
		System.out.println("Camino encontrado");
		int i = 0;
	
		while(CaminoSol.get(i).getMove()!=8){
	
			CaminoSol.add(CaminoSol.get(i).mPadre());
			i++;
			
		}
		invertirCaminoSol();
		
	}
	
	public void invertirCaminoSol(){  
		
		ArrayList<B_Analiza> Aux=new ArrayList<B_Analiza>();
		for (int i=CaminoSol.size(); i>0; i--){
			Aux.add(CaminoSol.get(i-1));
		}
			
		CaminoSol=Aux;
		
	}
	/*public final void getCamino(){
		
		System.out.print("Camino:\n");
			
		for(int m = 0; m<CaminoSol.size(); m++){
			CaminoSol.get(m).verEstado();
			System.out.print("-->");
		}
			
	}*/

	
	
	public int TamCamino(){
		return CaminoSol.size();
	}

	
	

	
	public int eVisitados(){
		return visitados;
	}
	
public void repeat(ArrayList<B_Analiza> Hijo){
		
		
		int a=0;
		while(a<Abiertos.size()){
			int i=0;
			while(i<Hijo.size()){
				if(Hijo.get(i).esigual(Abiertos.get(a))){
					if(Abiertos.get(a).getCosteTotal()<=Hijo.get(i).getCosteTotal()){	
						
						Hijo.remove(Hijo.get(i));
					
					}
					
				}
				i++;
			}
			a++;
		}
		
		int c=0;
		while(c<Cerrados.size()){
			int i=0;
			while(i<Hijo.size()){
				if(Hijo.get(i).esigual(Cerrados.get(c))){
					if(Cerrados.get(c).getCosteTotal()<=Hijo.get(i).getCosteTotal()){
						
					Hijo.remove(Hijo.get(i));
				
					}

				}
				i++;
			}
			
			c++;
		}
		
	}
	
	
	public void eAbiertosmax(){
		if( abiertosMax<Abiertos.size())
			abiertosMax=Abiertos.size();
	}
	
	
	

	
	public int eAbiertos(){
		return abiertosMax;
	}
	
	
	
	public void eCerradosmax(){
		if( cerradosMax<Cerrados.size())
			cerradosMax=Cerrados.size();
	}
	
	
	
	public int eCerrados(){
		return cerradosMax;
	}
	
	public void action(){
		
		
		String a="0";
		if(CaminoSol.size()>1){
			System.out.println("PASO: "+CaminoSol.get(1).getMove());
			a=Integer.toString(CaminoSol.get(1).getMove());
		}
		
	
		AID aux=Inicial.getSer();
	
		
		
		myAgent.addBehaviour(new B_enviaMove(aux, a));
				//new B_enviaMove("entorno3213","7a",3));
		System.out.println("Fin");
	}
	
	public boolean done(){
		return true;
	}
	
}
