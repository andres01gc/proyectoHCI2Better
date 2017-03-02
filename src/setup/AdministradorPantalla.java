package setup;

public class AdministradorPantalla {
	private static Pantalla pantallaVisible;
	private static Interfaz interfazVisible;

	// esta clase se encarga de administrar la pantalla actual.

	public static Interfaz getInterfazVisible() {
		return interfazVisible;
	}

	public static Pantalla getPantallaVisible() {
		return pantallaVisible;
	}



	public static void cambiarPantalla(Pantalla pantalla) {
		if (pantallaVisible != null)
			pantallaVisible.finalizar();// mata la anterior.

		pantallaVisible = pantalla;// inicia la nueva
		pantallaVisible.iniciar();
	}

	public static void cambiarInterfaz(Interfaz interfaz) {

		if (interfazVisible != null)
			interfazVisible.finalizar();// mata la anterior.

		interfazVisible = interfaz;// inicia la nueva
		interfazVisible.iniciar();
	}

	public static Pantalla getCurrentScreen() {
		return pantallaVisible;
	}

	public static void setInterfazVisible(Interfaz interfazVisible) {
		AdministradorPantalla.interfazVisible = interfazVisible;
	}
}
