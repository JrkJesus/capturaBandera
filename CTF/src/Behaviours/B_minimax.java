package Behaviours;

import java.util.ArrayList;

public class B_minimax 
{
/*
	public ArrayList<B_Analiza> busqueda(B_Analiza mapa)
	{
		return getSolucion(busqueda(mapa, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
	}

	private int busqueda(B_Analiza mapa, int profundidad, 
			int alpha, int beta, boolean playerMax) 
	{
		
		if(mapa.esSolucion())
		{
			if(playerMax)
			{
				mapa.setAlpha(Integer.MAX_VALUE);
				return Integer.MAX_VALUE;
			}
			else
			{
				mapa.setBeta(Integer.MIN_VALUE);
				return Integer.MIN_VALUE;
			}
		}
		else if(profundidad == 0)
		{
			// anadir funcion heuristica al nodo
			return 0;
		}
		else
		{
			int pos = 0;
			ArrayList<B_Analiza> hijos = mapa.generaHijos();
			if (playerMax) //Turno max
			{
				while(pos<hijos.size() && alpha>beta)
				{
					alpha = Math.max(alpha, busqueda(hijos.get(pos), profundidad-1, alpha, beta, !playerMax));
					//añadir heuristica al hijo
					pos++;
				}
				mapa.setAlpha(alpha);
				return alpha;
			}
			else
			{
				while(pos<hijos.size() && alpha>beta)
				{
					beta = Math.min(beta, busqueda(hijos.get(pos), profundidad-1, alpha, beta, !playerMax));
					//añadir heuristica al hijo
					pos++;
				}
				mapa.setBeta(beta);
				return beta;
			}
		}
	}*/
}

