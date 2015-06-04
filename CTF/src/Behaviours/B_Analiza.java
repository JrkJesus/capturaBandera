package Behaviours;

import java.util.ArrayList;

import Agentes.Inicial;
import Agentes.Enemigo;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class B_Analiza{

	private static int contador;
	private static int contador1;
	private static int informando;
	private static int informado;
	
	
	
	private static int ancho, alto, visx, visy;
	private int x, y;
	private static char[][] mapa;
	private String mapa2;
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
	
	
	public B_Analiza(ACLMessage A, int an, int alt, int vx, int vy, String Equ, String B, int in){
		
		if (in==1){
			informar();
			
		}
		Equipo=Equ;
		ancho=an;
		alto=alt;
		visx=vx;
		visy=vy;
		nombClase=B;
		if(nombClase.equals("Agentes.Inicial")){
			basePropia=Inicial.getBaseAmi();
			baseEnemiga=Inicial.getBaseEne();
		}
		if(nombClase.equals("Agentes.Enemigo")){
			basePropia=Enemigo.getBaseAmi();
			baseEnemiga=Enemigo.getBaseEne();
		}
		String aux= A.getContent();
		int i=0;
		int j=0;
		if(aux.length()>1){
			int m=0;
			while(!aux.substring(i,i+1).equals(",")&&m==0){
			
				i++;
				if(i+1==aux.length()) m++;
		
			}
		}
		
		j=i+1;
		if(aux.length()>1)
			while(!aux.substring(j,j+1).equals(",")){
			
				j++;
			
			}
		
		x=Integer.parseInt(aux.substring(0, i));
		y=Integer.parseInt(aux.substring(i+1, j));
		
		mapa=new char[alto][ancho];
		
		
		j++;
		mapa2=aux.substring(j, aux.length());
		
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
		mapa2=aux.substring(j, aux.length());
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
			if(baseEnemiga[0]!=-5)
				Enemigo.setBaseEne(baseEnemiga);
				if(basePropia[0]!=-5)
				Enemigo.setBaseAmi(basePropia);
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
	
	public int[] defender(int dist, int[] pos){
	
		//int pos[]=basePropia;
		dist--;
		int res[]=new int[2];
		int xaux, yaux;
		xaux=pos[1]; 
		yaux=pos[0]; 
		if(mapa[pos[0]][xaux-dist]==' '){
			res[1]=xaux-dist;
			res[0]=pos[0];
		}
		else if(mapa[pos[0]][xaux+dist]==' '){
			res[1]=xaux+dist;
			res[0]=pos[0];
		}
		else if(mapa[yaux-dist][pos[1]]==' '){
			res[1]=pos[1];
			res[0]=yaux-dist;
		}
		else if(mapa[yaux+dist][pos[1]]==' '){
			res[1]=pos[1];
			res[0]=yaux+dist;
		}
		else{
			//err
			res[0]=-5;
			res[1]=-5;
		}
		
		return res;
	}
	
	public void recuperaBandera(){
		int[] bandera=new int[2];
		bandera=findEnemigoConBandera();
		if(bandera[0]!=-5){
			xfinal=bandera[1];
			yfinal=bandera[0];
		}
	}
	/*public void setfinal(){
		
		
		int[] pos;
	
			if(Equipo.equals("7")){
				if(!haveEnemysFlag()){//Si ninguno de nuestros agentes tiene la bandera
					pos=findBandera();
					if(pos[0]!=-5){
						yfinal=pos[0];
						xfinal=pos[1];
					}//Si el valor es -5 significa que un jugador del equipo contrario tiene su propia bandera
				
					else{
						//La bandera enemiga la tiene un enemigo
					}
				}
				else{//Alguien de nuestro equipo tiene la bandera enemiga
					if(mapa[y][x]!='5'){//Si yo no tengo la bandera
					
						int[] posObj = findJugadorBandera();
						
						int i=2, n=0;
						if(!defendiendo(2, posObj)){
							
							pos=defender(2, posObj);
							yfinal=pos[0];
							xfinal=pos[1];
						
							if(xfinal==-5){//Si no se puede defender el 2 nivel
								if(!defendiendo(3, posObj)){
									pos=defender(3, posObj);
									xfinal=pos[1];
									yfinal=pos[0];
								}
								else{
									xfinal=x;
									yfinal=y;System.out.println("Defendiendo"+ " " + xfinal +" y:" + yfinal);
								}
							}
						}
						else{
							xfinal=x;
							yfinal=y;System.out.println("Defendiendo2"+ " " + xfinal +" y:" + yfinal);
						}
					}
					else{//El agente tiene la bandera
						verMapa();
						pos=basePropia;
						yfinal=pos[0];
						xfinal=pos[1];
				
					}
				}
				}
				else if(Equipo.equals("8")){
					if(!haveEnemysFlag()){//Si ninguno de nuestros agentes tiene la bandera
						pos=findBandera();
						if(pos[0]!=-5){
							yfinal=pos[0];
							xfinal=pos[1];
						}//Si el valor es -5 significa que un jugador del equipo contrario tiene su propia bandera
					
						else{
							//La bandera enemiga la tiene un enemigo
						}
					}
					else{//Alguien de nuestro equipo tiene la bandera enemiga
						if(mapa[y][x]!='6'){//Si yo no tengo la bandera
						
						
							int[] posObj = findJugadorBandera();
							int i=2, n=0;
							if(posObj[0]!=-5)
							if(!defendiendo(2, posObj)){
								pos=defender(2, posObj);
								yfinal=pos[0];
								xfinal=pos[1];
							
								if(xfinal==-5){//Si no se puede defender el 2 nivel
									if(!defendiendo(3, posObj)){
										pos=defender(3, posObj);
										xfinal=pos[1];
										yfinal=pos[0];
									}
									else{
										xfinal=x;
										yfinal=y;System.out.println("DefendiendoAz: "+ " " +xfinal +" y:" + yfinal);
									}
								}
							}
							else{
								xfinal=x;
								yfinal=y;System.out.println("Defendiendo2Az"+ " " + xfinal +" y:" + yfinal);
							}
						}
						else{//El agente tiene la bandera
							verMapa();
							pos=basePropia;
							yfinal=pos[0];
							xfinal=pos[1];System.out.println("Voy a Base Azul"+ " " + xfinal +" y:" + yfinal);
					
						}
					}
			}
		}
	
	*/
	
	public String posForBrigadier(){
		
		int i[]=new int[2];
		i=findJugadorEnemigoBandera();
		
		String A=Integer.toString(y)+"W"+Integer.toString(x)+"W"+Integer.toString(i[0])+"W"+Integer.toString(i[1]);
	
		return A;
	}
	
	public boolean tengoBandera(){
		boolean soyyo=false;
		if(Equipo.equals("7")){
			if(mapa[y][x]=='5'){
				soyyo=true;
			}
		}
		if(Equipo.equals("8")){
			if(mapa[y][x]=='6'){
				soyyo=true;
			}
		}
		return soyyo;
	}
	
public void setfinal(){
		
		int[] pos;
	
			if(Equipo.equals("7")){
				if(!haveEnemysFlag()){//Si ninguno de nuestros agentes tiene la bandera
					pos=findBandera();
					if(pos[0]!=-5){
						yfinal=pos[0];
						xfinal=pos[1];
					}//Si el valor es -5 significa que un jugador del equipo contrario tiene su propia bandera
				
					else{
						//La bandera enemiga la tiene un enemigo
					}
				}
				else{//Alguien de nuestro equipo tiene la bandera enemiga
					if(mapa[y][x]!='5'){//Si yo no tengo la bandera
					
					
					
						int i=2, n=0;
						if(!defendiendo(2, basePropia)){
							pos=defenderBase(2);
							yfinal=pos[0];
							xfinal=pos[1];
						
							if(xfinal==-5){//Si no se puede defender el 2 nivel
								if(!defendiendo(3, basePropia)){
									pos=defenderBase(3);
									xfinal=pos[1];
									yfinal=pos[0];
								}
								else{
									xfinal=x;
									yfinal=y;System.out.println("Defendiendo"+ " " + xfinal +" y:" + yfinal);
								}
							}
						}
						else{
							xfinal=x;
							yfinal=y;System.out.println("Defendiendo2"+ " " + xfinal +" y:" + yfinal);
						}
					}
					else{//El agente tiene la bandera
						verMapa();
						pos=basePropia;
						yfinal=pos[0];
						xfinal=pos[1];
				
					}
				}
				}
				else if(Equipo.equals("8")){
					if(!haveEnemysFlag()){//Si ninguno de nuestros agentes tiene la bandera
						pos=findBandera();
						if(pos[0]!=-5){
							yfinal=pos[0];
							xfinal=pos[1];
						}//Si el valor es -5 significa que un jugador del equipo contrario tiene su propia bandera
					
						else{
							//La bandera enemiga la tiene un enemigo
						}
					}
					else{//Alguien de nuestro equipo tiene la bandera enemiga
						if(mapa[y][x]!='6'){//Si yo no tengo la bandera
						
						
						
							int i=2, n=0;
							if(!defendiendo(2, basePropia)){
								pos=defenderBase(2);
								yfinal=pos[0];
								xfinal=pos[1];
							
								if(xfinal==-5){//Si no se puede defender el 2 nivel
									if(!defendiendo(3, basePropia)){
										pos=defenderBase(3);
										xfinal=pos[1];
										yfinal=pos[0];
									}
									else{
										xfinal=x;
										yfinal=y;System.out.println("DefendiendoAz: "+ " " +xfinal +" y:" + yfinal);
									}
								}
							}
							else{
								xfinal=x;
								yfinal=y;System.out.println("Defendiendo2Az"+ " " + xfinal +" y:" + yfinal);
							}
						}
						else{//El agente tiene la bandera
							verMapa();
							pos=basePropia;
							yfinal=pos[0];
							xfinal=pos[1];System.out.println("Voy a Base Azul"+ " " + xfinal +" y:" + yfinal);
					
						}
					}
			}
		}
	
	
	
	public boolean TheyHaveOurFlag(){
		
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='6'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='5'){
							encontrado=true;
						}
						else j++;
					}
				
				
			}
			if(!encontrado){
				i++;
			}
		}
		
		return encontrado;
		
	}
	
	public boolean haveEnemysFlag(){
		
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='5'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='6'){
							encontrado=true;
						}
						else j++;
					}
				
				
			}
			if(!encontrado){
				i++;
			}
		}
		
		return encontrado;
		
	}
	
	
	
	public int[] defenderBase(int i){
		
		int pos[]=basePropia;
		int res[]=new int[2];
		int xaux, yaux;
		xaux=pos[1]; 
		yaux=pos[0]; 
		if(mapa[pos[0]][xaux-i]==' '){
			res[1]=xaux-i;
			res[0]=pos[0];
		}
		else if(mapa[pos[0]][xaux+i]==' '){
			res[1]=xaux+i;
			res[0]=pos[0];
		}
		else if(mapa[yaux-i][pos[1]]==' '){
			res[1]=pos[1];
			res[0]=yaux-i;
		}
		else if(mapa[yaux+i][pos[1]]==' '){
			res[1]=pos[1];
			res[0]=yaux+i;
		}
		else{
			res[0]=-5;
			res[1]=-5;
		}
		
		return res;
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
	
	
	

	public int[] findJugadorBandera(){
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='5'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='6'){
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
	
	
	public int[] findJugadorEnemigoBandera(){
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='6'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='5'){
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
	
	public boolean enlaEntrada(){
		boolean estoy=false;
		if(Equipo.equals("7")){
			if(mapa[y][x]=='V'){
				estoy=true;
			}
		}
		else if(Equipo.equals("8")){
			if(mapa[y][x]=='W'){
				estoy=true;
			}
		}
		return estoy;
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
						
							if(Equipo.equals("7")){
								if(mapa[m][i]!='1'&&mapa[m][i]!='3'&&mapa[m][i]!='5'){
								
								movimientos[e]=new B_Analiza(this, movimpos[o]);
								movimientos[e].TotalCost();
								e++;o++;
								}
								else o++;
							}
							else if(Equipo.equals("8")){
								if(mapa[m][i]!='2'&&mapa[m][i]!='4'&&mapa[m][i]!='6'){
								
								movimientos[e]=new B_Analiza(this, movimpos[o]);
								movimientos[e].TotalCost();
								e++;o++;
								}
								else o++;
							}
							//if(o!=7)
								//o++;
						
							
						}
						else o++;
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
	
	public int[] findEnemigoConBandera(){
		int j=0,i=0;
		boolean encontrado=false;
		while(!encontrado && i<alto){
			j=0;
			while(!encontrado&&j<ancho){
				
					if(Equipo.equals("7")){
						if(mapa[i][j]=='6'){
							encontrado=true;
						}
						else j++;
					}
					if(Equipo.equals("8")){
						if(mapa[i][j]=='5'){
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
	
	public static boolean informando(){
		
		boolean infor=false;
		
		if(informando==1)
			infor=true;
		return infor;
	}
	
	public static void informar(){
		informando=1;
	}
	public static void heInformado(){//Mision cumplida
		informando=0;
	}
	public static void informado(){// Estos tres por ahora sobran
		informado=1;
	}
	public static boolean estamosinformados(){
		boolean infor=false;
		
		if(informado==1){
			infor=true;
		}
		return infor;
	}
	public static void esperandoNuevaOrden(){
		informado=0;
	}
	public String getMapa(){
		return mapa2;
	}
	
}
