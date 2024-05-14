import com.tienda.modelo.Cliente;
import com.tienda.modelo.Producto;
import com.tienda.modelo.Venta;
import com.tienda.controlador.Tienda;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Tienda tienda = new Tienda();

    public static void main(String[] args) {   	
        int opcion;
        do {
            System.out.println("Menu:");
            System.out.println("1. Administrar Clientes");
            System.out.println("2. Administrar Productos");
            System.out.println("3. Realizar Venta");
            System.out.println("4. Mostrar Ventas");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    tienda.administrarClientes();
                    break;
                case 2:
                    tienda.administrarProductos();
                    break;
                case 3:
                    tienda.realizarVenta();
                    break;
                case 4:
                    tienda.mostrarVentas();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, ingrese una opción valida.");
            }
        } while (opcion != 5);
    }
}
