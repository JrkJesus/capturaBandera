package Behaviours;

import java.util.ArrayList;

import Agentes.Inicial;
import Agentes.Enemigo;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class B_Analiza{

	private static int contador;
	private static int contador1;
	
	private static int ancho, alto, visx, visy;
	private int x, y;
	private static char[][] mapa;
	private int movim;  
	
	private String nombClase;
	
	private int[] baseEnemiga;
	private int[] basePropia;
	private int xfinal;
	private int yfinal;
	private String Equipo;
	
	private B_Analiza padre;
	
	private int DManhattan;
	private int costeTotal;
	
	
	public B_Analiza(ACLMessage A, int an, int alt, int vx, int vy, String Equ, String B){
		
		Equipo=Equ;
		ancho=an;
		alto=alt;
		visx=vx;
		visy=vy;
		nombClase=B;
		basePropia=findBase();
		baseEnemiga=findBaseEne();
		String aux= A.getContent();
		int i=0;
		int j=0;
		
		while(!aux.substring(i,i+1).equals(",")){
			
			i++;
		
		}
		
		j=i+1;
		while(!aux.substring(j,j+1).equals(",")){
		
			j++;
		
		}
		
		x=Integer.parseInt(aux.substring(0, i));
		y=Integer.parseInt(aux.substring(i+1, j));
		
		mapa=new char[alto][ancho];
		
		
		j++;
		
		
		int posx=0;
		int posy=0;
		
		for(; j<aux.length();j++){
				
				mapa[posy][posx]=aux.charAt(j);
				posx++;
			
				if(posx==ancho){
					posy++;
					posx=0;
		
				}
			
			}
		
		movim=8;
		
		setfinal();
		if(nombClase.equals("Agentes.Inicial"))
			setContador();
		else if(nombClase.equalsIgnoreCase("Agentes.Enemigo"))
			setcontador1();
		
		DManhattan=distanciaManhattan();
		costeTotal=0;
		
		
	}
	
	public static void setcontador1(){
		contador1=0;
	}
	
	public B_Analiza(B_Analiza Padre, int mov){

		Equipo=Padre.Equipo;
		baseEnemiga=Padre.baseEnemiga;
		basePropia=Padre.basePropia;
		ancho=Padre.ancho;
		alto=Padre.alto;
		visx=Padre.visx;
		visy=Padre.visy;
		x=Padre.x;
		y=Padre.y;
		nombClase=Padre.nombClase;
		movim=mov;
		mapa=Padre.mapa;//Actualizar posicion;
		//verMapa();
		move(mov);
		padre=Padre;
		
		xfinal=Padre.xfinal;
		yfinal=Padre.yfinal;
		
		DManhattan=distanciaManhattan();
		costeTotal=Padre.costeTotal;
		
	}
	
	
	
	public B_Analiza(ACLMessage A, String B, String Equip){
		
		movim=0;
		Equipo=Equip;
		int i=0, j=0;
		
		nombClase=B;
		
		String aux=A.getContent();
		
		int indicesfinales[];
		
		movim=8;//Movimiento inicial inexistente. Para la cuenta.
		
		while(!aux.substring(i,i+1).equals(",")){
			
			i++;
		
		}
		
		j=i+1;
		while(!aux.substring(j,j+1).equals(",")){
		
			j++;
		
		}
		
		ancho=Integer.parseInt(aux.substring(0, i));
		alto=Integer.parseInt(aux.substring(i+1,j));
		
		mapa=new char[alto][ancho];
		
		indicesfinales=new int[6+alto];
		indicesfinales[0]=i;
		indicesfinales[1]=j;
		
		j++;
		i=2;
		
		int posx=0;
		int posy=0;
		
		for(; j<aux.length();j++){
			
			if(i>5){
				
				mapa[posx][posy]=aux.charAt(j);
				posy++;
			
				if(posy==ancho){
					posx++;
					posy=0;
		
				}
			
			}
			
			if(aux.substring(j,j+1).equals(",")){
			
				indicesfinales[i]=j;
				i++;
			
			}
		
		}
		
		visx=Integer.parseInt(aux.substring(indicesfinales[1]+1, indicesfinales[2]));
		visy=Integer.parseInt(aux.substring(indicesfinales[2]+1, indicesfinales[3]));
		x=Integer.parseInt(aux.substring(indicesfinales[3]+1, indicesfinales[4]));
		y=Integer.parseInt(aux.substring(indicesfinales[4]+1, indicesfinales[5]));
		
		basePropia=findBase();
		baseEnemiga=findBaseEne();
		setfinal();
		
		if(nombClase.equals("Agentes.Inicial")){
			setContador();
			if(baseEnemiga[0]!=-5)
			Inicial.setBaseEne(baseEnemiga);
			if(basePropia[0]!=-5)
			Inicial.setBaseAmi(basePropia);//Comprobar que no den -5
		}
		else if(nombClase.equals("Agentes.Enemigo")){
			setcontador1();
			/*if(baseEnemiga[0]!=-5)
				Enemigo.setBaseEne(baseEnemiga);
				if(basePropia[0]!=-5)
				Enemigo.setBaseAmi(basePropia);*/
		}
		
		DManhattan=distanciaManhattan();
		costeTotal=0;
	}
	
	public static void setContador(){
		contador=0;
	}
	public boolean defendiendo(int i, int[] pos){
	
		boolean def=false;
			if(((y==pos[0])&&((x==pos[1]+i)||(x==pos[1]-i))) ||((x==pos[1])&&((y==pos[0]+i)||(y==pos[0]-i)))){
				def=true;
			}
		
		return def;
	}
	
	public int[] defender(int i, int[] pos){
		
		if(mapa[pos[0]+i][pos[1]]==' '){
			pos[0]+=i;
		}
		else if(mapa[pos[0]-i][pos[1]]==' '){
			pos[0]-=i;
		}
		else if(mapa[pos[0]][pos[1]+i]==' '){
			pos[1]+=i;
		}
		else if(mapa[pos[0]][pos[1]-i]==' '){
			pos[1]-=i;
		}
		else{
			pos[0]=-5;
			pos[1]=-5;
		}
		
		return pos;
	}
	
	public void setfinal(int x,int y){
		xfinal=x;
		yfinal=y;
	}
	public void setfinal(){
		
		int[] pos;
	
			if(Equipo.equals("7")){
				if(mapa[y][x]!='5'){
					pos=findBandera();
					if(pos[0]!=-5){
						yfinal=pos[0];
						xfinal=pos[1];
					}
					
					else{          //Si nuestro agente no tiene la bandera
						yfinal=y;  //No est� en la base enemiga ni por el mapa
						xfinal=x; //Defendemos nuestra base
						
						pos=basePropia;
						yfinal=pos[0];
						xfinal=pos[1];
						//if(x==xfinal+2&&y==yfinal)||
						int i=2, n=0;
						if(!defendiendo(2, pos)){
							pos=defender(2, pos);
							yfinal=pos[0];
							xfinal=pos[1];
						
							if(xfinal==-5){
								if(!defendiendo(3, pos)){
									pos=defender(3, pos);
									xfinal=pos[1];
									yfinal=pos[0];
								}
								else{
									xfinal=x;
									yfinal=y;
								}
							}
						}
						else{
							xfinal=x;
							yfinal=y;
						}
					}
				}
				else{//El agente tiene la bandera
					verMapa();
					pos=basePropia;
					yfinal=pos[0];
					xfinal=pos[1];
				}
			}
			else if(Equipo.equals("8")){
				if(mapa[y][x]!='6'){
					pos=findBandera();
					if(pos[0]!=-5){
						yfinal=pos[0];
						xfinal=pos[1];
					}
					else{
						yfinal=y;
						xfinal=x;
						pos=basePropia;
						yfinal=pos[0];
						xfinal=pos[1];
						int i=2, n=0;
						if(!defendiendo(2, pos)){
							pos=defender(2, pos);
							yfinal=pos[0];
							xfinal=pos[1];
						
							if(xfinal==-5){
								if(!defendiendo(3, pos)){
									pos=defender(3, pos);
									xfinal=pos[1];
									yfinal=pos[0];
								}
								else{
									xfinal=x;
									yfinal=y;
								}
							}
						}
						else{
							xfinal=x;
							yfinal=y;
						}
					}
				}
				else{
					pos=basePropia;
					yfinal=pos[0];
					xfinal=pos[1];
				}
			}
		}
		
	public int[] findBase(){
		
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='A' || mapa[i][j]=='C'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='B'|| mapa[i][j]=='D'){
							encontrado=true;
						}
						else j++;
					}
				
			}
			
			if(!encontrado){
				i++;
			}
		}
		int[] pos=new int[2];
		if(encontrado){
			
			pos[0]=i;
			pos[1]=j;
		
		}
		else{
			pos[0]=-5;
			pos[1]=-5;
			
		}
		return pos;
		
	}
	
	
	
public int[] findBaseEne(){
		
	int j=0,i=0;
	boolean encontrado=false;
	while(!encontrado && i<alto){
		j=0;
		while(!encontrado&&j<ancho){
			
				if(Equipo.equals("7")){
					if(mapa[i][j]=='B' || mapa[i][j]=='D'){
						encontrado=true;
					}
					else j++;
				}
				if(Equipo.equals("8")){
					if(mapa[i][j]=='A'|| mapa[i][j]=='C'){
						encontrado=true;
					}
					else j++;
				}
			
		}
		
		if(!encontrado){
			i++;
		}
	}
	int[] pos=new int[2];
	if(encontrado){
		
		pos[0]=i;
		pos[1]=j;
	
	}
	else{
		pos[0]=-5;
		pos[1]=-5;
		
	}
	return pos;
	
}
	
	
	

	
	
	public int[] findBandera(){
		
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='B'||mapa[i][j]=='F'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='A'|| mapa[i][j]=='E'){
							encontrado=true;
						}
						else j++;
					}
				
				
			}
			if(!encontrado){
				i++;
			}
		}
		int[] pos=new int[2];
		if(encontrado){
			
		
			pos[0]=i;
			pos[1]=j;
			return pos;
		}
		else{
			pos[0]=-5;
			pos[1]=-5;
			return pos;
		}
	}
		
		
	
	
	public void verMapa(){
		
		for(int i=0; i<alto; i++){
		
			for(int j=0; j<ancho; j++){
			
				System.out.print( mapa[i][j]);
			
			}
			
			System.out.printf("\n");
		
		}
	
	}
	
	public void verAtributos(){
		
		System.out.println("Alto:"+alto+"\nAncho: "+ancho);
		System.out.println("VisAlto:"+visy+"\nVisAncho: "+visx);
		System.out.println("PosAlto:"+y+"\nPosAncho: "+x);
	
	}
	
	public B_Analiza move(int moves){
		
		switch (moves){
		
		case 4://Sur
			//this.setMapa(y, x, y+1, x);
			//this.resetPos(y, x);
			y+=1;
			
			break;
			
		case 1://Este
			//this.setMapa(y, x, y, x+1);
			//this.resetPos(y, x);
			x+=1;
			break;
			
		case 3://Norte
			//this.setMapa(y, x, y-1, x);
			//this.resetPos(y, x);
			y-=1;
			break;
			
		case 2://Oeste
			//this.setMapa(y, x, y, x-1);
			//this.resetPos(y, x);
			x-=1;
			break;
		
		case 22://Noroeste
			//this.setMapa(y, x, y-1, x-1);
			//this.resetPos(y, x);
			x-=1;
			y-=1;
			break;
			
		case 21://Noreste
			//this.setMapa(y, x, y-1, x+1);
			//this.resetPos(y, x);
			x+=1;
			y-=1;
			
			break;
			
		case 23://Sureste
			//this.setMapa(y, x, y+1, x-1);
			//this.resetPos(y, x);
			x+=1;
			y+=1;
			break;
			
		case 24: //Suroeste
			//this.setMapa(y, x, y+1, x+1);
			//this.resetPos(y, x);
			x-=1;
			y+=1;
			break;
		}
			
		
		return this;
	}
	
	public void resetPos(int i, int j){
		
		//Controlar no borrar parte del mapa
		mapa[i][j]=' ';
		
	}
	
	public void setMapa(int i, int j, int m, int n){
			
		mapa[m][n]='2';//mapa[m][n];
			
	}

	public int getMove(){
		return movim;
	}
	public ArrayList<B_Analiza> genHijos(){
		ArrayList<B_Analiza> hijos = new ArrayList<B_Analiza>();

		//Estamos en la posicion mapa[y][x];
		
		int[] movimpos=new int[8];
		int e=0, o=0;
		
		movimpos[0]=22;
		movimpos[1]=3;
		movimpos[2]=21;
		movimpos[3]=2;
		movimpos[4]=1;
		movimpos[5]=24;
		movimpos[6]=4;
		movimpos[7]=23;
		
		B_Analiza[] movimientos=new B_Analiza[8];
		
		for(int m=y-1; m<y+2; m++){
			for(int i=x-1; i<x+2;i++){
				
				if(m!=y||i!=x){
					if(i<ancho&&m<alto&&m>-1&&i>-1){
						if(mapa[m][i]!='H'){//&& mapa[m][i]!='2'){
							if(nombClase.equals("Agentes.Inicial")){
								if((Inicial.getEquipo().equals("7")&&mapa[m][i]!=1&&mapa[m][i]!=3&&mapa[m][i]!=5)){
									movimientos[e]=new B_Analiza(this, movimpos[o]);
									movimientos[e].TotalCost();
									e++;
								}
								else if(Inicial.getEquipo().equals("8")&&mapa[m][i]!=2&&mapa[m][i]!=4&&mapa[m][i]!=6){
									movimientos[e]=new B_Analiza(this, movimpos[o]);
									movimientos[e].TotalCost();
									e++;
								}
								o++;
							}
							else if(nombClase.equals("Agentes.Enemigo")){
								if((Enemigo.getEquipo().equals("7")&&mapa[m][i]!=1&&mapa[m][i]!=3&&mapa[m][i]!=5)){//||(Enemigo.getEquipo().equals("8")&&mapa[m][i]!=2&&mapa[m][i]!=4&&mapa[m][i]!=6)){
									movimientos[e]=new B_Analiza(this, movimpos[o]);
									movimientos[e].TotalCost();
									e++;
								}
								else if(Enemigo.getEquipo().equals("8")&&mapa[m][i]!=2&&mapa[m][i]!=4&&mapa[m][i]!=6){
									movimientos[e]=new B_Analiza(this, movimpos[o]);
									movimientos[e].TotalCost();
									e++;
								}
								o++;
							}
						}
					}
				}
			}
		}
		
		for(int i=0; i<e; i++){
			hijos.add(movimientos[i]);
		
		}
		
		return hijos;
		
	}
	
	public int distanciaManhattan(){
		
		int i=0;
		int j=0;
		int cambios=0;
		int Total=0;

		//while(j<3){
			
			//i=0;
			//while(i<3){
				cambios=0;
				cambios=Math.abs(xfinal-x);
				cambios=cambios+Math.abs(yfinal-y);
				Total=Total+cambios;
				i++;
			//}
			j++;
		//}
		
		return Total;
	}
	
	public void setManhattan(){
		
		DManhattan=distanciaManhattan();
	}
	
	public int getDManhattan(){
		return DManhattan;
	}
	
	public void TotalCost(){
		costeTotal+=distanciaManhattan();
	}
	
	
	public boolean esFinal(){
		
		boolean finalx=false;
		if(x==xfinal){
			if(y==yfinal)
				finalx=true;
		}
		/*if(Inicial.getEquipo().equals("7")){
			if(mapa[y][x]=='5')
				finalx=true;
		}
		
		if(Inicial.getEquipo().equals("8")){
			if(mapa[y][x]=='6')
					finalx=true;
		}*/
		
		return finalx;
	}
	
	public boolean esigual(B_Analiza A){
		boolean igual=true;
		
		if(this.x!=A.x || this.y!=A.y){
			
			igual=false;
			
		}
		
		return igual;
	}
	
	public B_Analiza mPadre(){
		return padre;
	}
	
	public int getCosteTotal(){
		
		return costeTotal;
		
	}
	
	public int getxfinal(){
		return xfinal;
	}
	public int getyfinal(){
		return yfinal;
	}
	
	public static int getancho(){
		return ancho;
	}
	public static int getalto(){
		return alto;
	}
	
	public static int getvisx(){
		return visx;
	}
	
	public static int getvisy(){
		return visy;
	}
	
	
	/*public void action(){
		if(nombClase.equals("Agentes.Inicial")){
			if(contador==0){
				verMapa();
				contador++;
				//verAtributos();
				System.out.println(getxfinal()+","+getyfinal());
				myAgent.addBehaviour(new B_Aestrella(this, nombClase));

			}
		}
		else if(nombClase.equals("Agentes.Enemigo")){
			if(contador1==0){
				verMapa();
				contador1++;
				//verAtributos();
				System.out.println(getxfinal()+","+getyfinal());
				myAgent.addBehaviour(new B_Aestrella(this, nombClase));

			}
		}
	}
	
	public boolean done(){
	
		return true;
	
	}
	*/

}
