package edu.upc.dsa.android_upz_apocalypse;

public class Object {
    int id;
    String nombre;
    int precio;
    int valor;
    String url;

    public Object(int id, String nombre, int precio, int valor, String url) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.valor=valor;
        this.url=url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
