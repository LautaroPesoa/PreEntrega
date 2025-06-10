package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<LineaPedidos> lineas;

    public Pedido(int id) {
        this.id = id;
        this.lineas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<LineaPedidos> getLineas() {
        return lineas;
    }

    public void agregarLinea(LineaPedidos linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedidos linea : lineas) {
            total += linea.getTotalLinea();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append("\n");
        for (LineaPedidos linea : lineas) {
            sb.append(linea.toString()).append("\n");
        }
        sb.append("Total del pedido: $").append(calcularTotal());
        return sb.toString();
    }
}
