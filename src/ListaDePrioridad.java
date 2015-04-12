
public class ListaDePrioridad {

	private Casilla elemento;
	private ListaDePrioridad siguiente;
	private int size;
	private int prioridadTotal;
	
	public ListaDePrioridad(Casilla elementoT)
	{
		elemento=elementoT;
		siguiente=null;
		size=1;
		prioridadTotal=elemento.getPrioridad();
	}

	public Casilla getElemento() {
		return elemento;
	}

	public void setElemento(Casilla elemento) {
		this.elemento = elemento;
	}

	public ListaDePrioridad getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(ListaDePrioridad siguiente) {
		this.siguiente = siguiente;
	}
	
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPrioridadTotal() {
		return prioridadTotal;
	}

	public void setPrioridadTotal(int prioridadTotal) {
		this.prioridadTotal = prioridadTotal;
	}

	public void agregarREOrder(Casilla nuevo)
	{
		if(nuevo.getPrioridad()>elemento.getPrioridad())
		{
			if(siguiente==null)
				siguiente = new ListaDePrioridad(elemento);
			else
				siguiente.agregarREOrder(elemento);
			
			elemento = nuevo;
		}
		else
		{
			if(siguiente==null)
				siguiente = new ListaDePrioridad(nuevo);
			else
				siguiente.agregarREOrder(nuevo);
			
		}
		size++;
		prioridadTotal += nuevo.getPrioridad();
	}
	
	public Object[] asignacionDeResultado(int valor, int fibra, int tiempo)
	{
		if(valor<=elemento.getPrioridad())
		{
			elemento.seleccionarComoVisto();
			return new Object[]{new ResultElement(elemento.getX(), elemento.getY(), tiempo, fibra),(int)(-100)};
			
		}
		else
		{
			if(siguiente!=null)
				return siguiente.asignacionDeResultado(valor-elemento.getPrioridad(), fibra, tiempo);
			else
				return new Object[]{null,(int)valor-elemento.getPrioridad()};
		}
	}

	public int calcularPrioridad() {
		
		return elemento.getPrioridad() + ((siguiente!= null)?siguiente.calcularPrioridad():0);
	}
}
