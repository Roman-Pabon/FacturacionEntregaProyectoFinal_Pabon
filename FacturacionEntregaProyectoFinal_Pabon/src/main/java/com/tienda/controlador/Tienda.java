package com.tienda.controlador;
import com.tienda.modelo.Cliente;
import com.tienda.modelo.Producto;
import com.tienda.modelo.Venta;

import java.util.ArrayList;
import java.util.Scanner;

public class Tienda {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();
    private int nextClienteId = 1;
    private int nextProductoId = 1;
    private int nextVentaId = 1;
    private Scanner scanner = new Scanner(System.in);

    public void administrarClientes() {
        int opcion;
        do {
            System.out.println("\nMenú Clientes:");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Volver al menú principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    mostrarClientes();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        } while (opcion != 3);
    }

    private void agregarCliente() {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la dirección del cliente: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(nextClienteId++, nombre, direccion);
        clientes.add(cliente);
        System.out.println("Cliente agregado exitosamente.");
    }

    private void mostrarClientes() {
        System.out.println("\nLista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getIdCliente() + ". " + cliente.getNombre() + " - " + cliente.getDireccion());
        }
    }

    public void administrarProductos() {
        int opcion;
        do {
            System.out.println("\nMenú Productos:");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Mostrar Productos");
            System.out.println("3. Volver al menú principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    mostrarProductos();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        } while (opcion != 3);
    }

    private void agregarProducto() {
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();

        Producto producto = new Producto(nextProductoId++, nombre, precio);
        productos.add(producto);
        System.out.println("Producto agregado exitosamente.");
    }

    private void mostrarProductos() {
        System.out.println("\nLista de Productos:");
        for (Producto producto : productos) {
            System.out.println(producto.getIdProducto() + ". " + producto.getNombre() + " - Precio: $" + producto.getPrecio());
        }
    }

    public void realizarVenta() {
        if (clientes.isEmpty() || productos.isEmpty()) {
            System.out.println("No hay clientes o productos registrados. Por favor, asegúrese de tener al menos un cliente y un producto.");
            return;
        }

        System.out.println("\nRealizar Venta:");
        mostrarClientes();
        System.out.print("Seleccione el cliente (El numero correspondiente): ");
        int idCliente = scanner.nextInt();
        Cliente clienteSeleccionado = null;
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                clienteSeleccionado = cliente;
                break;
            }
        }
        if (clienteSeleccionado == null) {
            System.out.println("Cliente no encontrado. Cancelando venta.");
            return;
        }

        mostrarProductos();
        System.out.print("Seleccione el producto (El numero correspondiente): ");
        int idProducto = scanner.nextInt();
        Producto productoSeleccionado = null;
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProducto) {
                productoSeleccionado = producto;
                break;
            }
        }
        if (productoSeleccionado == null) {
            System.out.println("Producto no encontrado. Cancelando venta.");
            return;
        }

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();

        Venta venta = new Venta(nextVentaId++, clienteSeleccionado, productoSeleccionado, cantidad);
        ventas.add(venta);
        System.out.println("Venta realizada exitosamente.");
    }

    public void mostrarVentas() {
        System.out.println("\nLista de Ventas:");
        for (Venta venta : ventas) {
            System.out.println("ID Venta: " + venta.getIdVenta() + ", Cliente: " + venta.getCliente().getNombre() + ", Producto: " +
                    venta.getProducto().getNombre() + ", Cantidad: " + venta.getCantidad());
        }
    }
}

