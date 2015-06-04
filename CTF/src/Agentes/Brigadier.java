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
	
	private int numCompa�eros;
	private int dispuestos=0;

	private int numEnemigos;
	private AID[] compa�eros=new AID[5];
	private AID[] enemigos=new AID[5];
	private int[] xPosCompa�eros=new int[5];
	private int[] xPosEnemigos=new int[5];
	private int[] yPosCompa�eros=new int[5];
	private int[] yPosEnemigos=new int[5];
	
	private int[] xSigCompa�eros=new int[5];
	private int[] ySigCompa�eros=new int[5];
	
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
		xPosCompa�eros[dispuestos]=x;
		xPosEnemigos[dispuestos]=xe-x;
		yPosCompa�eros[dispuestos]=y;
		yPosEnemigos[dispuestos]=ye-y;
	}
	
	public void ordenar2aid(){//Busca los dos agentes dispuestos que "m�s cerca esten del objetivo"
		
		int menor1=((yPosEnemigos[0])*10+(xPosEnemigos[0]));
		int menor2=((yPosEnemigos[1])*10+(xPosEnemigos[1]));
		for(int i=2; i<dispuestos; i++)
			if((yPosEnemigos[i])*10+(xPosEnemigos[i])<menor1){
				AID aux=compa�eros[0];
				compa�eros[0]=compa�eros[i];
				compa�eros[i]=aux;
			}
			else if((yPosEnemigos[i])*10+(xPosEnemigos[i])<menor2){
				AID aux=compa�eros[1];
				compa�eros[1]=compa�eros[i];
				compa�eros[i]=aux;
			}
		}
	
	public void reordenarCompa�eros(AID A){//Reordena la lista de compa�eros con memoria de los reordenados
		int i=getPosAIDc(A);
		AID aux=compa�eros[dispuestos];
		compa�eros[dispuestos]=A;
		compa�eros[i]=aux;
		
	}
	public int getnumCompa�eros(){
		return numCompa�eros;
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
	
	public void setCompa�ero(AID A){//Usar para a�adir a un agente a la lista
		//Solo al lanzar agente por primera vez
		System.out.println("Seteo COMPA�ERO");
		compa�eros[numCompa�eros]=A;
		System.out.println("Muestro nombres: "+A.getLocalName()+compa�eros[numCompa�eros].getLocalName());
		if(numCompa�eros!=5){
			System.out.println("La lista aun no est� llena");
			numCompa�eros++;
		}
	}
	
	public String getCompa�eros(){
		String A=new String("En la lista de compa�eros estan: ");
		for(int i=0; i<numCompa�eros; i++){
			System.out.println("En realidad, falla la concatenacionya que: "+compa�eros[i].getLocalName());
			A=A+compa�eros[i].getLocalName()+", ";
		}
		return A;
	}
	
	public AID getCompa�ero(int i){
		
		return compa�eros[i];
	}
	
	
	public boolean esCompa�ero(AID A){
		
		boolean es=false;
		int i=0;
		while(!es&&i<numCompa�eros){
			if(A.equals(compa�eros[i])){
				es=true;
			}
			else i++;
		}
		
		return es;
	}
	
	
	public void setEnemigo(int y, int x){//Usar para a�adir a un agente a la lista
		
	}
	
	public void compa�eroRevivido(){//Hay que hacer distinci�n entre que agente se ha revivido
		
	}
	
	public void enemigoRevivido(){//
		
	}
	
	public void compa�eroCaido(AID A){
		int i=getPosAIDc(A);
		
		if(i==numCompa�eros-1){
			numCompa�eros--;
		}
		else{
			
			AID aux= compa�eros[i];
			compa�eros[i]=compa�eros[numCompa�eros-1];
			compa�eros[numCompa�eros-1]=aux;
			
			int xaux=xPosCompa�eros[i];
			int yaux=yPosCompa�eros[i];
			
			xPosCompa�eros[i]=xPosCompa�eros[numCompa�eros-1];
			yPosCompa�eros[i]=yPosCompa�eros[numCompa�eros-1];
			
			xPosCompa�eros[numCompa�eros-1]=xaux;
			yPosCompa�eros[numCompa�eros-1]=yaux;
			
			numCompa�eros--;
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
			yPosCompa�eros[numEnemigos-1]=yaux;
			
			numEnemigos--;
		}
		
	}
	public int getPosAIDc(AID A){
		boolean encontrado=false;
		int pos=0;
		
		while(!encontrado&&pos<numCompa�eros){
			if(!compa�eros[pos].equals(A)){
				pos++;
			}
			else{
				encontrado=true;
			}
		}
		
		return pos;
	}
	
	public AID getAIDc(int i){
		return compa�eros[i];
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
		numCompa�eros=0;
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
		//Lanzar un compa�ero. Comportamiento que lanza 1 agente.
		//Brigadier se pondr� a escuchar.
		//El agente le envia su posicion al haber hecho el primer movimiento
		//Brigadier lanzar� otro agente hasta que est�n los 5
			//Solo para pruebas, lanzar un enemigo.
		
		
		//As� solucionamos el problema de que dos agentes se inicien a la vez
		//Para solucionar el problema de que dos agentes tomen como siguiente posicion
			//la misma... Tendremos que seguir enviando mensajes a Brigadier y este controlar�
			//Que la posicion siguiente no sea la misma
	}
	

// Debe guardar el AID de cada agente en el juego
	// Principalmente de los del mismo equipo
	// Estrategicamente mediante paginas blancas, los enemigos
// Debe recibir mensajes de los agentes del mismo equipo
	//No debe permitir que un agente entre en el juego estando otro agente del mismo equipo en la entrada
// Debe enviar mensajes a los agentes del mismo equipo
	//Calcular qu� agente es mejor para cumplir un objetivo
	//Enviarle la orden al agente
	//El agente debe ignorar su mision y cumplir la orden
// Si un agente muere teniendo una bandera debe informarle de donde muri�
	//Dar� una orden a otro agente para que busque la bandera
	//Relanzar� al agente que muri�
	
//Llevar� la cuenta de cuantos agentes hay en el juego (Tanto amigos como enemigos).
}
