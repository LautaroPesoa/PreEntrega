package com.techlab.pedidos;

import com.techlab.productos.Productos;

public class LineaPedidos {
    private Productos producto;
    private int cantidad;

    public LineaPedidos(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Productos getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotalLinea() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " - Cantidad: " + cantidad + " - Total: $" + getTotalLinea();
    }
}
