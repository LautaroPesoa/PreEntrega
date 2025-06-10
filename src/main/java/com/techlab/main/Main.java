package com.techlab.main;

import com.techlab.productos.Productos;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedidos;
import com.techlab.excepciones.StockInsuficienteException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Productos> productos = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static int contadorProductos = 1;
    private static int contadorPedido = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN DE INVENTARIO ---");
            System.out.println("1) Registrar nuevo producto");
            System.out.println("2) Mostrar productos registrados");
            System.out.println("3) Buscar y actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Generar un pedido");
            System.out.println("6) Consultar pedidos realizados");
            System.out.println("7) Salir");
            System.out.print("Por favor, seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido, por favor.");
                continue;
            }
            switch (opcion) {
                case 1:
                    System.out.println("\n*** Registro de Producto ***");
                    System.out.print("Indique el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    double precio;
                    int stock;
                    try {
                        System.out.print("Introduzca el precio unitario: ");
                        precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Ingrese la cantidad en inventario: ");
                        stock = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error en el formato numérico, la operación ha sido cancelada.");
                        break;
                    }
                    Productos producto = new Productos(contadorProductos++, nombre, precio, stock);
                    productos.add(producto);
                    System.out.println("El producto ha sido registrado exitosamente.");
                    break;
                case 2:
                    System.out.println("\n*** Lista de Productos ***");
                    if (productos.isEmpty()) {
                        System.out.println("No se han registrado productos hasta el momento.");
                    } else {
                        for (Productos p : productos) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n*** Búsqueda y Actualización de Producto ***");
                    System.out.print("Por favor, introduzca el ID del producto: ");
                    int idBuscar;
                    try {
                        idBuscar = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: ID con formato incorrecto.");
                        break;
                    }
                    Productos prodEncontrado = null;
                    for (Productos p : productos) {
                        if (p.getId() == idBuscar) {
                            prodEncontrado = p;
                            break;
                        }
                    }
                    if (prodEncontrado == null) {
                        System.out.println("No se encontró ningún producto con el ID indicado.");
                        break;
                    }
                    System.out.println("Producto encontrado: " + prodEncontrado);
                    System.out.print("¿Desea actualizar los datos del producto? (SI/NO): ");
                    if (scanner.nextLine().trim().toUpperCase().equals("SI")) {
                        try {
                            System.out.print("Introduzca el nuevo precio: ");
                            double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                            System.out.print("Ingrese la nueva cantidad en inventario: ");
                            int nuevoStock = Integer.parseInt(scanner.nextLine());
                            if (nuevoStock < 0) {
                                System.out.println("Error: La cantidad en inventario no puede ser negativa.");
                                break;
                            }
                            prodEncontrado.setPrecio(nuevoPrecio);
                            prodEncontrado.setStock(nuevoStock);
                            System.out.println("El producto ha sido actualizado correctamente.");
                        } catch (NumberFormatException e) {
                            System.out.println("Error en el formato numérico. La actualización se ha cancelado.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("\n*** Eliminación de Producto ***");
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    int idEliminar;
                    try {
                        idEliminar = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: ID incorrecto.");
                        break;
                    }
                    Productos prodEliminar = null;
                    for (Productos p : productos) {
                        if (p.getId() == idEliminar) {
                            prodEliminar = p;
                            break;
                        }
                    }
                    if (prodEliminar == null) {
                        System.out.println("No se encontró el producto solicitado.");
                        break;
                    }
                    System.out.print("¿Confirma la eliminación de este producto? (SI/NO): ");
                    if (scanner.nextLine().trim().toUpperCase().equals("SI")) {
                        productos.remove(prodEliminar);
                        System.out.println("El producto ha sido eliminado satisfactoriamente.");
                    }
                    break;
                case 5:
                    System.out.println("\n*** Creación de Pedido ***");
                    Pedido pedido = new Pedido(contadorPedido++);
                    System.out.print("¿Cuántos productos desea incluir en el pedido? ");
                    int numLineas;
                    try {
                        numLineas = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Número inválido. Se cancela la operación.");
                        break;
                    }
                    for (int i = 0; i < numLineas; i++) {
                        System.out.print("Introduzca el ID del producto: ");
                        int idProd;
                        try {
                            idProd = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Formato de ID incorrecto.");
                            i--;
                            continue;
                        }
                        Productos prod = null;
                        for (Productos p : productos) {
                            if (p.getId() == idProd) {
                                prod = p;
                                break;
                            }
                        }
                        if (prod == null) {
                            System.out.println("El producto no existe. Intente de nuevo.");
                            i--;
                            continue;
                        }
                        System.out.print("Ingrese la cantidad deseada de " + prod.getNombre() + ": ");
                        int cant;
                        try {
                            cant = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Error en la cantidad. Intente nuevamente.");
                            i--;
                            continue;
                        }
                        if (cant > prod.getStock()) {
                            System.out.println("No hay suficiente inventario para " + prod.getNombre() +
                                    ". Inventario disponible: " + prod.getStock());
                            try {
                                throw new StockInsuficienteException("Inventario insuficiente para " + prod.getNombre());
                            } catch (StockInsuficienteException e) {
                                System.out.println(e.getMessage());
                                i--;
                                continue;
                            }
                        }
                        System.out.print("¿Confirma la inclusión de " + cant + " unidades de " + prod.getNombre() + " en el pedido? (SI/NO): ");
                        if (scanner.nextLine().trim().toUpperCase().equals("SI")) {
                            pedido.agregarLinea(new LineaPedidos(prod, cant));
                            prod.setStock(prod.getStock() - cant);
                            System.out.println("Producto agregado al pedido.");
                        } else {
                            System.out.println("El producto no se ha agregado al pedido.");
                        }
                    }
                    pedidos.add(pedido);
                    System.out.println("El pedido ha sido generado exitosamente.");
                    System.out.println(pedido);
                    break;
                case 6:
                    System.out.println("\n*** Consulta de Pedidos ***");
                    if (pedidos.isEmpty()) {
                        System.out.println("No se han registrado pedidos hasta el momento.");
                    } else {
                        for (Pedido p : pedidos) {
                            System.out.println(p);
                            System.out.println("-------------------------------------------------");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Gracias por utilizar el sistema. Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no reconocida, por favor intente nuevamente.");
                    break;
            }
        } while (opcion != 7);
        scanner.close();
    }
}
