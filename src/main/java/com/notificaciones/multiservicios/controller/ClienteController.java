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
import com.notificaciones.multiservicios.model.LogTransacciones;
import com.notificaciones.multiservicios.model.MapaParametros;
import com.notificaciones.multiservicios.model.Parametros;
import com.notificaciones.multiservicios.model.Servicio;
import com.notificaciones.multiservicios.objectRequest.InfoConsulta;
import com.notificaciones.multiservicios.objectRequest.NotificacionRequest;
import com.notificaciones.multiservicios.repository.LogTransaccionesRepository;
import com.notificaciones.multiservicios.repository.MapaParametrosRepository;
import com.notificaciones.multiservicios.repository.ParametrosRepository;
import com.notificaciones.multiservicios.repository.ServicioRepository;
import ec.systemnotifyextractor.test.NewMain;
import ec.systemnotifyextractor.util.DatosEntrada;
import ec.systemnotifyextractor.util.DatosRespuesta;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final String nombrePaquete = "ec.systemnotifyextractor.manager";
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
     ServicioRepository servicioRepository;

    @Autowired
     ParametrosRepository parametrosRepository;
    
    @Autowired
    LogTransaccionesRepository logTransaccionesRepository;
    
    
    @Autowired
     MapaParametrosRepository mapaparametrosRepository;

    @PostMapping("/cliente")
    private Cliente guardarCliente(@Valid @RequestBody ClienteRequest cliente) {
        Cliente c = new Cliente();

        c.setApellidos(cliente.getApellidos());
        c.setNombres(cliente.getNombres());
        c.setCedula(cliente.getCedula());
        c.setUser(userRepository.findById(cliente.getUser()).get());
        c.setTelefono(cliente.getTelefono());
        return clienteRepository.save(c);
    }

    @GetMapping("/clientes")
    public List<Cliente> allClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public Cliente getCliente(@PathVariable(value = "id") Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        return cliente;
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
        System.out.println("------------------------------------");
        System.out.println(id_cliente);
        System.out.println("------------------------------------");
        Cliente cliente = clienteRepository.findById(id_cliente).get();

        String id = java.util.UUID.randomUUID().toString();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String reference = id_cliente.toString() + "/"
                + notif.getCedula() + "/"
                + notif.getEmpresa() + "/"
                + "servicios/" + notif.getServicio() + "-" + id;

        String fechaNotif = reference + "/fecha_notificacion";
        DatabaseReference ref = database.getReference(fechaNotif);

//        cliente/descripcion/direccion_cliente/telefono/NONE/tiempo_estimado_motorizado => NONE va el tiempo estimado del pedido, en este caso es none
        ref.setValue(notif.getFecha_notificacion());
        int cont = 0;
        reference += "/infoAdicional";
        for (String key : notif.getInfo_adicional().keySet()) {
            ref = database.getReference("/" + reference + "/" + key);
            ref.setValue(notif.getInfo_adicional().get(key));
        }

        String datosEntrada = notif.getCedula() + "/" + notif.getEmpresa() + "/" + notif.getServicio();
//        guardarLogTransaccion(notif.getCedula(), id_cliente,  id, reference, notif.getFecha_notificacion());

    }

    public LogTransacciones guardarLogTransaccion(String datosEntrada, Long id_cliente, String uuid, String datosSalida, String fecha_notificacion) {

        LogTransacciones lt = new LogTransacciones();
        lt.setCodigoUUID(uuid);
        lt.setEstado("A"); //A=Activo N=Notificado
        System.out.println("Cliente: " + id_cliente);
        //Cliente client = this.getCliente(id_cliente);

        //System.out.println("_____----------- "+id_cliente);
        //lt.setCliente(client);
        lt.setDatosEntrada(datosEntrada);
        lt.setDatosSalida(datosSalida);
        lt.setFecha_notificacion(fecha_notificacion);
        return logTransaccionesRepository.save(lt);

    }

    public DatosRespuesta consultar( DatosEntrada datosEntrada) {
           try {
               
               
               
            Class clase = Class.forName(this.nombrePaquete + "." + datosEntrada.getNombreEntidad());
            Object instancia = clase.newInstance();
            Method method = clase.getMethod("execute", DatosEntrada.class);
            DatosRespuesta datosRespuesta = (DatosRespuesta) method.invoke(instancia, new Object[]{datosEntrada});
            return datosRespuesta;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DatosRespuesta(true, "");
    }

    @PostMapping("/recuperar")
    public DatosRespuesta recuperarDatosEntrada (@Valid @RequestBody InfoConsulta info){
        
        
    Servicio s = servicioRepository.findById(info.getIdServicio()).get();
    
    
    
    String url = s.getUrl();
    List<Parametros> parametros = parametrosRepository.findByServicio(s.getId_servicio());
    
    HashMap<String,Object> mapaParametros = this.getMapaParametros(parametros);
    
    DatosEntrada datosEntrada = new DatosEntrada();
    datosEntrada.setUrlConsultar(url);
    datosEntrada.setDatos(mapaParametros);
    
    datosEntrada.setNombreEntidad(s.getNombreEntidad());
    datosEntrada.setIdServicio(s.getId_servicio().toString());
    
    datosEntrada.setFechaTransacion(new Date());
    
    NotificacionRequest notif = new NotificacionRequest();
    
    notif.setCedula("0105785794");
    notif.setEmpresa(datosEntrada.getNombreEntidad());
    notif.setServicio(datosEntrada.getIdServicio());
    notif.setFecha_notificacion(datosEntrada.getFechaTransacion().toString());
    notif.setInfo_adicional(mapaParametros);
   enviarNotificacionCliente(notif, 1L);
    
    return consultar(datosEntrada);
    }
    
    public HashMap<String,Object> getMapaParametros(List<Parametros> parametros){
    
    HashMap<String,Object> mapaParam = new HashMap<>();
    for (Parametros parametro : parametros) {
            List<MapaParametros> mps = mapaparametrosRepository.findByParametro(parametro.getId_param());
            for (MapaParametros mp : mps) {
                        mapaParam.put(mp.getClave(), mp.getValor());

        }
        }
    
    
    
    return mapaParam;
    }
}
