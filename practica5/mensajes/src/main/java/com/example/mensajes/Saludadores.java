package com.example.mensajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Saludadores {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private int id;
    private String nombre;

    public String getNombre() {
        return nombre;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
