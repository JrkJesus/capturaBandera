package Agentes;

import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import Behaviours.*;

public class Brigadier extends Agent {
	
	private int numCompañeros;
	private int dispuestos=0;

	private int numEnemigos;
	private AID[] compañeros=new AID[5];
	private AID[] enemigos=new AID[5];
	private int[] xPosCompañeros=new int[5];
	private int[] xPosEnemigos=new int[5];
	private int[] yPosCompañeros=new int[5];
	private int[] yPosEnemigos=new int[5];
	
	private int[] xSigCompañeros=new int[5];
	private int[] ySigCompañeros=new int[5];
	
	private Scanner entrada;
	private static String Equipo;
	private String Clave;
	
	public  void setInfo(){
		System.out.println("Introduzca Equipo:");
		entrada = new Scanner(System.in);
		Equipo=entrada.next();
		System.out.println("Introduzca Clave");
		Clave=entrada.next();
	}
	
	
	public AID Comandante(){
		return getAID();
	}
	
	public void inforBrigadier(int y, int x, int ye, int xe){
		xPosCompañeros[dispuestos]=x;
		xPosEnemigos[dispuestos]=xe-x;
		yPosCompañeros[dispuestos]=y;
		yPosEnemigos[dispuestos]=ye-y;
	}
	
	public void ordenar2aid(){//Busca los dos agentes dispuestos que "más cerca esten del objetivo"
		
		int menor1=((yPosEnemigos[0])*10+(xPosEnemigos[0]));
		int menor2=((yPosEnemigos[1])*10+(xPosEnemigos[1]));
		for(int i=2; i<dispuestos; i++)
			if((yPosEnemigos[i])*10+(xPosEnemigos[i])<menor1){
				AID aux=compañeros[0];
				compañeros[0]=compañeros[i];
				compañeros[i]=aux;
			}
			else if((yPosEnemigos[i])*10+(xPosEnemigos[i])<menor2){
				AID aux=compañeros[1];
				compañeros[1]=compañeros[i];
				compañeros[i]=aux;
			}
		}
	
	public void reordenarCompañeros(AID A){//Reordena la lista de compañeros con memoria de los reordenados
		int i=getPosAIDc(A);
		AID aux=compañeros[dispuestos];
		compañeros[dispuestos]=A;
		compañeros[i]=aux;
		
	}
	public int getnumCompañeros(){
		return numCompañeros;
	}
	public void dispuesto(){
		if(dispuestos<4)
			dispuestos++;
	}
	public void setdispuestos(){
		dispuestos=0;
	}
	public int getdispuestos(){
		return dispuestos;
	}
	public int getnumEnemigos(){
		return numEnemigos;
	}
	
	public void setCompañero(AID A){//Usar para añadir a un agente a la lista
		//Solo al lanzar agente por primera vez
		System.out.println("Seteo COMPAÑERO");
		compañeros[numCompañeros]=A;
		System.out.println("Muestro nombres: "+A.getLocalName()+compañeros[numCompañeros].getLocalName());
		if(numCompañeros!=5){
			System.out.println("La lista aun no está llena");
			numCompañeros++;
		}
	}
	
	public String getCompañeros(){
		String A=new String("En la lista de compañeros estan: ");
		for(int i=0; i<numCompañeros; i++){
			System.out.println("En realidad, falla la concatenacionya que: "+compañeros[i].getLocalName());
			A=A+compañeros[i].getLocalName()+", ";
		}
		return A;
	}
	
	public AID getCompañero(int i){
		
		return compañeros[i];
	}
	
	
	public boolean esCompañero(AID A){
		
		boolean es=false;
		int i=0;
		while(!es&&i<numCompañeros){
			if(A.equals(compañeros[i])){
				es=true;
			}
			else i++;
		}
		
		return es;
	}
	
	
	public void setEnemigo(int y, int x){//Usar para añadir a un agente a la lista
		
	}
	
	public void compañeroRevivido(){//Hay que hacer distinción entre que agente se ha revivido
		
	}
	
	public void enemigoRevivido(){//
		
	}
	
	public void compañeroCaido(AID A){
		int i=getPosAIDc(A);
		
		if(i==numCompañeros-1){
			numCompañeros--;
		}
		else{
			
			AID aux= compañeros[i];
			compañeros[i]=compañeros[numCompañeros-1];
			compañeros[numCompañeros-1]=aux;
			
			int xaux=xPosCompañeros[i];
			int yaux=yPosCompañeros[i];
			
			xPosCompañeros[i]=xPosCompañeros[numCompañeros-1];
			yPosCompañeros[i]=yPosCompañeros[numCompañeros-1];
			
			xPosCompañeros[numCompañeros-1]=xaux;
			yPosCompañeros[numCompañeros-1]=yaux;
			
			numCompañeros--;
		}
		
	}
	
	public void enemigoCaido(int y, int x){
		int i=getPosAIDe(y,x);
		
		if(i==numEnemigos-1){
			numEnemigos--;
		}
		else{
			
			AID aux= enemigos[i];
			enemigos[i]=enemigos[numEnemigos-1];
			enemigos[numEnemigos-1]=aux;
			
			int xaux=xPosEnemigos[i];
			int yaux=yPosEnemigos[i];
			
			xPosEnemigos[i]=xPosEnemigos[numEnemigos-1];
			yPosEnemigos[i]=yPosEnemigos[numEnemigos-1];
			
			xPosEnemigos[numEnemigos-1]=xaux;
			yPosCompañeros[numEnemigos-1]=yaux;
			
			numEnemigos--;
		}
		
	}
	public int getPosAIDc(AID A){
		boolean encontrado=false;
		int pos=0;
		
		while(!encontrado&&pos<numCompañeros){
			if(!compañeros[pos].equals(A)){
				pos++;
			}
			else{
				encontrado=true;
			}
		}
		
		return pos;
	}
	
	public AID getAIDc(int i){
		return compañeros[i];
	}
	
	public int getPosAIDe(int y, int x){
		int pos=0;
		boolean encontrado=false;
		while(!encontrado && pos<numEnemigos){
			if(xPosEnemigos[pos]==x&& yPosEnemigos[pos]==y){
				encontrado=true;
			}
			else{
				pos++;
			}
		}
		return pos;
	}
	
	public AID getAIDe(int i){
		return enemigos[i];
	}
	
	public static String getEquipo(){
		return Equipo;
	}
	
	protected void takeDown()
    {
        try
        {
            DFService.deregister(this);
        }
        catch (FIPAException fe)
        {
            fe.printStackTrace();
        }
        System.out.println("El agente "+getAID().getName()+" ya no ofrece sus servicios.");
    }
	
	public void setup(){
		numCompañeros=0;
		numEnemigos=0;
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
	
		ServiceDescription sd=new ServiceDescription();
		sd.setType("Tipo");
		sd.setName("Gondor");
	
		dfd.addServices(sd);
		
		try{
			DFService.register(this, dfd);
			//System.out.println("Tipo:Registrado");
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
	
		
		setInfo();
		Inicial.setInf(Equipo, Clave);
		if(Equipo.equals("7"))
		Enemigo.setInf("8",Clave);
		else Enemigo.setInf("7", Clave);
		
		this.addBehaviour(new B_lanzaAgente());
		//Lanzar un compañero. Comportamiento que lanza 1 agente.
		//Brigadier se pondrá a escuchar.
		//El agente le envia su posicion al haber hecho el primer movimiento
		//Brigadier lanzará otro agente hasta que estén los 5
			//Solo para pruebas, lanzar un enemigo.
		
		
		//Así solucionamos el problema de que dos agentes se inicien a la vez
		//Para solucionar el problema de que dos agentes tomen como siguiente posicion
			//la misma... Tendremos que seguir enviando mensajes a Brigadier y este controlará
			//Que la posicion siguiente no sea la misma
	}
	

// Debe guardar el AID de cada agente en el juego
	// Principalmente de los del mismo equipo
	// Estrategicamente mediante paginas blancas, los enemigos
// Debe recibir mensajes de los agentes del mismo equipo
	//No debe permitir que un agente entre en el juego estando otro agente del mismo equipo en la entrada
// Debe enviar mensajes a los agentes del mismo equipo
	//Calcular qué agente es mejor para cumplir un objetivo
	//Enviarle la orden al agente
	//El agente debe ignorar su mision y cumplir la orden
// Si un agente muere teniendo una bandera debe informarle de donde murió
	//Dará una orden a otro agente para que busque la bandera
	//Relanzará al agente que murió
	
//Llevará la cuenta de cuantos agentes hay en el juego (Tanto amigos como enemigos).
}
