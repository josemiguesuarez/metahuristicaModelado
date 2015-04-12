import java.util.ArrayList;


public class MetaHeuristicaP2 {

	private int dimXmat=9;
	private int dimYmat=9;
	private int numTiempo=4;
	private int numFibras=35;
	private ArrayList<ResultElement> resultados;
	private Casilla[][] matrizPesos;
	private int[][] ventana;
	private ListaDePrioridad listaPrioridad;

	public static void main(String[] args) throws Exception {
		new MetaHeuristicaP2().ejecutarMetaAlgoritmo();
	}
	
	public MetaHeuristicaP2()
	{

		//Inicializa matriz de pesos
		matrizPesos = new Casilla[9][9];
		for(int i=0;i<dimXmat;i++)
			for(int j=0;j<dimYmat;j++)
				matrizPesos[i][j]=new Casilla(1,i,j);

		matrizPesos[0][2].setPesoInicial(4+1);
		matrizPesos[0][3].setPesoInicial(4+1);
		matrizPesos[0][8].setPesoInicial(1+1);
		matrizPesos[1][2].setPesoInicial(4+1);
		matrizPesos[1][3].setPesoInicial(4+1);
		matrizPesos[1][7].setPesoInicial(1+1);
		matrizPesos[2][6].setPesoInicial(1+1);
		matrizPesos[4][0].setPesoInicial(2+1);
		matrizPesos[6][2].setPesoInicial(1+1);
		matrizPesos[6][3].setPesoInicial(1+1);
		matrizPesos[7][7].setPesoInicial(3+1);
		matrizPesos[7][8].setPesoInicial(3+1);
		matrizPesos[8][7].setPesoInicial(3+1);
		matrizPesos[8][8].setPesoInicial(4+1);

		matrizPesos[0][2].setPrioridad(4+1);
		matrizPesos[0][3].setPrioridad(4+1);
		matrizPesos[0][8].setPrioridad(1+1);
		matrizPesos[1][2].setPrioridad(4+1);
		matrizPesos[1][3].setPrioridad(4+1);
		matrizPesos[1][7].setPrioridad(1+1);
		matrizPesos[2][6].setPrioridad(1+1);
		matrizPesos[4][0].setPrioridad(2+1);
		matrizPesos[6][2].setPrioridad(1+1);
		matrizPesos[6][3].setPrioridad(1+1);
		matrizPesos[7][7].setPrioridad(3+1);
		matrizPesos[7][8].setPrioridad(3+1);
		matrizPesos[8][7].setPrioridad(3+1);
		matrizPesos[8][8].setPrioridad(4+1);


		//Inicializa demas parametros

		ventana = new int[numTiempo][4];
		//Primera ventana
		ventana[0][0]=0;
		ventana[0][1]=8;
		ventana[0][2]=0;
		ventana[0][3]=5;
		//Segunda ventana
		ventana[1][0]=0;
		ventana[1][1]=8;
		ventana[1][2]=0;
		ventana[1][3]=5;
		//Tercera ventana
		ventana[2][0]=0;
		ventana[2][1]=8;
		ventana[2][2]=3;
		ventana[2][3]=8;
		//Cuarta ventana
		ventana[3][0]=0;
		ventana[3][1]=8;
		ventana[3][2]=3;
		ventana[3][3]=8;

		//Inicializa arreglo resultados y parciales

		resultados = new ArrayList<ResultElement>();
		listaPrioridad = null;

	}

	

	public void ejecutarMetaAlgoritmo() throws Exception
	{
		for(int tiempo = 0; tiempo<numTiempo;tiempo++)
		{
			System.out.println("Tiempo: "+tiempo);
			listaPrioridad=null;
			//Prioridad total de la lista de prioridades no determinóstica
			int PrioridadTotal = 0;

			//for(int i=0;i<dimXmat&&i<=ventana[tiempo][1]&&i>=ventana[tiempo][0];i++)
			for(int i=0;i<dimXmat;i++)
			{
				if(i<=ventana[tiempo][1]&&i>=ventana[tiempo][0])
				{
					System.out.println("El i es "+i);
					//for(int j=0;j<dimYmat&&j<=ventana[tiempo][3]&&j>=ventana[tiempo][2];j++)
					for(int j=0;j<dimYmat;j++)
					{
						if(j<=ventana[tiempo][3]&&j>=ventana[tiempo][2])
						{
							System.out.println("El j es "+j);
							if(listaPrioridad==null)
								listaPrioridad=new ListaDePrioridad(matrizPesos[i][j]);
							else
								listaPrioridad.agregarREOrder(matrizPesos[i][j]);
							PrioridadTotal += matrizPesos[i][j].getPrioridad();
							System.out.println("La prioridad de"+i+","+j+" NO visto es: "+matrizPesos[i][j].getPrioridad());

						}
					}
				}
			}

			//Inicia a cada fibra su respuesta
			for(int fibra =0; fibra<numFibras;fibra++)
			{
				System.out.println("Fibra: "+fibra+" del tiempo: "+tiempo);
				//Determina el n�mero aleatorio al cual va a observar la fibra
				System.out.println("Prioridad total es: "+PrioridadTotal+ "-" +listaPrioridad.calcularPrioridad());
				int elNumero = (int) (Math.random()*listaPrioridad.calcularPrioridad());
				System.out.println("elNumero es: "+elNumero);



				//Busca el numero y lo asigna a la respuesta

				Object[] resultado;

				resultado = listaPrioridad.asignacionDeResultado(elNumero, fibra, tiempo);
				if(resultado[0]!=null)
					resultados.add((ResultElement)resultado[0]);
				else
					throw new Exception("La lista simepre debería retornar un elemento");


			}
		}

		//Imprime el resultado del algoritmo



		for(int i = 0; i<resultados.size();i++)
		{
			ResultElement resp = resultados.get(i);
			System.out.println("Resultado "+i+": X: "+resp.getX()+" Y: "+resp.getY()+" f: "+resp.getF()+" t: "+resp.getT());
		}

		int contador = 0;
		for(int i=0;i<dimXmat;i++)
			for(int j=0;j<dimXmat;j++)
				if(!matrizPesos[i][j].isFueVisto())
					contador++;

		System.out.println("Las casillas sin observar fueron: "+contador);

		System.out.println("Total fibras usadas: "+resultados.size());
		System.out.println("Total fibras disponibles: "+numFibras*numTiempo);
		int porcentaje = resultados.size()*100/(numFibras*numTiempo);
		System.out.println("Total de procentaja usado: "+porcentaje);
	}
}
