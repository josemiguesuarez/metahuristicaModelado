
public class Casilla {

	private boolean fueVisto;
	private int pesoInicial;
	private int prioridad;
	private int x;
	private int y;
	
	private final static int constDistribucion = 5;
	
	public Casilla(int pesoInicialT, int xT, int yT) 
	{
		fueVisto=false;
		pesoInicial=pesoInicialT;
		prioridad=pesoInicial*constDistribucion;
		x=xT;
		y=yT;
	}

	public boolean isFueVisto() {
		return fueVisto;
	}

	public void setFueVisto(boolean fueVisto) {
		this.fueVisto = fueVisto;
	}

	public int getPesoInicial() {
		return pesoInicial;
	}

	public void setPesoInicial(int pesoInicial) {
		this.pesoInicial = pesoInicial;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		if(!isFueVisto())
			this.prioridad = prioridad*constDistribucion;
		else
			this.prioridad = prioridad;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void seleccionarComoVisto()
	{
		if(!fueVisto)
		{
			fueVisto=true;
			prioridad=prioridad/constDistribucion;
		}
		prioridad = (prioridad==1)?1:(prioridad-2);
	}
	
}
