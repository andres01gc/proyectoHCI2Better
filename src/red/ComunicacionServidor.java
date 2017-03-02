package red;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class ComunicacionServidor extends Observable implements Runnable{

	/*
	 * En data archivos con opciones de puestas, el servidor escoje con cual
	 * trabajar y le indica al cliente que archivo cargar.
	 */
	private int puerto;
	ServerSocket ss;
	private Socket cliente;

	private int turno;
	private int turnoOtroJugador;
	private boolean turnoActivo;
	private static ComunicacionServidor c;

	private ComunicacionServidor(int i) {


		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				puerto = i;
				turnoActivo = true;
				try {
					ss = new ServerSocket(i);
					System.out.println("esperando cliente");
					cliente = ss.accept();
					iniciar();
					System.out.println("Conectado exitosamente :)");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void iniciar() {
		new Thread(this).start();
	}

	public static ComunicacionServidor getInstance() {
		if (c == null) {
			c = new ComunicacionServidor(6001);
		}
		return c;
	}

	public void run() {
		while (true) {
			Object o =recibir();

			setChanged();
			notifyObservers(o);
			clearChanged();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public Object recibir() {
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(cliente.getInputStream());
			return entrada.readObject();


		} catch (IOException e) {
			try {
				if (entrada != null) {
					entrada.close();
				}
				cliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			cliente = null;
		//	conectado = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void enviar(Object o) {
		ObjectOutputStream salida = null;

		try {
			salida = new ObjectOutputStream(cliente.getOutputStream());
			salida.writeObject(o);
			System.out.println("Se envió el mensaje: " + o);
		} catch (IOException e) {
			try {
				if (salida != null) {
					salida.close();
				}
				cliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			cliente = null;
		//	conectado = false;
			e.printStackTrace();
		}
	}

	public int getTurno() {
		return turno;
	}

	public boolean isTurnoActivo() {
		return turnoActivo;
	}
}