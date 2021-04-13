package com.example.mensajes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import me.tell.mensajes.BuscarSaludosResponse;
import me.tell.mensajes.DeleteRequest;
import me.tell.mensajes.DeleteResponse;
import me.tell.mensajes.SaludarRequest;
import me.tell.mensajes.SaludarResponse;
import me.tell.mensajes.UpdateRequest;
import me.tell.mensajes.UpdateResponse;

@Endpoint
public class SaludadoresEndPoint {
    @Autowired
    private Isaludadores isaludadores;

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "SaludarRequest")
    @ResponsePayload
    public SaludarResponse dameSaludo(@RequestPayload SaludarRequest peticion){
        SaludarResponse respuesta=new SaludarResponse();
        respuesta.setRespuesta("Hola "+peticion.getNombre());
        //Salvar a la BD
        Saludadores saludadores=new Saludadores();
        saludadores.setNombre(peticion.getNombre());
        isaludadores.save(saludadores);
        return respuesta;
    }

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "BuscarSaludosRequest")
    @ResponsePayload
    public BuscarSaludosResponse dameSaludos(){
        BuscarSaludosResponse respuesta =new BuscarSaludosResponse();
        Iterable<Saludadores> listaSaludadores= isaludadores.findAll();

        for(Saludadores ls:listaSaludadores){
            BuscarSaludosResponse.Saludador e=new BuscarSaludosResponse.Saludador();
            e.setId(ls.getId());
            e.setNombre(ls.getNombre());
            respuesta.getSaludador().add(e);
        } 

        return respuesta;
    }

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "UpdateRequest")
    @ResponsePayload
    public UpdateResponse actualizarSaludo(@RequestPayload UpdateRequest peticion){
        UpdateResponse respuesta=new UpdateResponse();
        Saludadores saludador=new Saludadores();

        try {
            saludador=isaludadores.findById(peticion.getId()).get();
            saludador.setNombre(peticion.getNombre());
            isaludadores.save(saludador);
            respuesta.setRespuesta("Saludador con el ID: "+saludador.getId()+" actualizado");
            return respuesta;
        } catch (Exception e) {
            respuesta.setRespuesta("Saludador con el ID: "+peticion.getId()+ "no encontrado :(");
            return respuesta;
        }
    }

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "DeleteRequest")
    @ResponsePayload
    public DeleteResponse eliminarSaludo(@RequestPayload DeleteRequest peticion){
        DeleteResponse respuesta=new DeleteResponse();

        try {
            if(isaludadores.existsById(peticion.getId())){
                isaludadores.deleteById(peticion.getId());
                respuesta.setRespuesta("Saludador con el ID: "+peticion.getId()+" eliminado");
            }else{
                respuesta.setRespuesta("Saludador con el ID: "+peticion.getId()+" no encontrado");
            }
            return respuesta;
        } catch (Exception e) {
            respuesta.setRespuesta("Error con la base de datos");
            return respuesta;
        }
    }
}
