package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area { //una cola que deriva de la clase Area
	private Queue<Patient> waitQueue; //crear un nuevo atribute de la clase paciente
	
	//creamos el constructor con los mismos atributos que la clase de la que hereda
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position); //con super llamamos a la clase padre
		this.waitQueue = new LinkedList<Patient>(); //tenemos que inicializar tambien aqui el nuevo atributo creado
	}
	@Override //reescribimos un metodo de su clase padre
	public synchronized void enter(Patient p) {
		this.waiting++; //antes de todo se le hace esperar, si entra en el while va a tener que seguir esperando, si no luego se le resta este tiempo de espera
		this.waitQueue.add(p); //se añade al paciente a la lista de espera
		while(this.numPatients>=this.capacity || this.waitQueue.peek() != p) { //ademas, aqui se le pregunta a la cola de espera a quien le toca y si es distinto del paciente este tendra que esperar.
			try { //con el wait usar siempre un try catch
				wait(); //se le hace esperar
			}catch(InterruptedException e) {}
		}
	this.waitQueue.remove(); //una vez ha entrado al Area se le tiene que borrar de la lista de espera
	this.waiting--; //si no ha entrado en el while se le tiene que restar el tiempo que ha estado esperando innecesariamente
	this.numPatients++; //por lo contrario, el paciente entra y se incrementa el numero de pacientes
	}
}


