/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.controller;

import com.notificaciones.multiservicios.exception.ResourceNotFoundException;
import com.notificaciones.multiservicios.model.Cliente;
import com.notificaciones.multiservicios.objectRequest.ClienteRequest;
import com.notificaciones.multiservicios.repository.ClienteRepository;
import com.notificaciones.multiservicios.repository.UserRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.firebase.database.*;
import com.notificaciones.multiservicios.objectRequest.NotificacionRequest;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/cliente")
    private Cliente guardarCliente(@Valid @RequestBody ClienteRequest cliente) {
        Cliente c = new Cliente();
        c.setApellidos(cliente.getApellido());
        c.setNombres(cliente.getNombre());
        c.setCedula(cliente.getCedula());
        c.setUser(userRepository.findById(cliente.getUser()).get());
        return clienteRepository.save(c);
    }

    @GetMapping("/clientes")
    public List<Cliente> allClientes() {
        return clienteRepository.findAll();
    }

    @PutMapping("/cliente/{id}")
    public Cliente updateClient(@PathVariable(value = "id") Long id,
            @Valid @RequestBody Cliente clientDetails) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));

        cliente.setApellidos(clientDetails.getApellidos());
        cliente.setNombres(clientDetails.getNombres());
        cliente.setCedula(clientDetails.getCedula());
        cliente.setTelefono(clientDetails.getTelefono());
        Cliente updatedClient = clienteRepository.save(cliente);

        return updatedClient;

    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(value = "id") Long id) {
        Cliente client = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));

        clienteRepository.delete(client);

        return ResponseEntity.ok().build();
    }

    public void enviarNotificacionCliente(NotificacionRequest notif, Long id_cliente) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String reference = id_cliente.toString() + "/"
                + notif.getCedula() + "/"
                + notif.getEmpresa() + "/"
                + "servicios/"+notif.getServicio();

        String fechaNotif = reference + "/fecha_notificacion";
        DatabaseReference ref = database.getReference(fechaNotif);

//        cliente/descripcion/direccion_cliente/telefono/NONE/tiempo_estimado_motorizado => NONE va el tiempo estimado del pedido, en este caso es none
        
        ref.setValue(notif.getFecha_notificacion());
        int cont=0;
        reference+="/infoAdicional";
        for (String key : notif.getInfo_adicional().keySet()) {
            ref = database.getReference("/"+reference+"/"+key);
            ref.setValue(notif.getInfo_adicional().get(key));
        }
       

//        ref.push().setValue(new NotificacionMotorizado(
//                parts_descripcion[0],
//                parts_descripcion[1],
//                parts_descripcion[2],
//                parts_descripcion[5],
//                parts_descripcion[3],
//                ubicacion,
//                sucursal.getNombre(),
//                pedido.getIdPedido(),
//                key_pedido,
//                id_sucursal
//        ));
        
        
    }
}
