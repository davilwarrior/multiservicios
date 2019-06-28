package com.notificaciones.multiservicios;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.notificaciones.multiservicios.controller.ClienteController;
import com.notificaciones.multiservicios.objectRequest.NotificacionRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EntityScan(basePackageClasses = {
    MultiserviciosApplication.class,
    Jsr310JpaConverters.class
})
public class MultiserviciosApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) throws FileNotFoundException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("src/main/resources/firebase-authentication.json"))
                .setDatabaseUrl("https://multiservicios-b035f.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
        
        //Prueba
//        NotificacionRequest nr= new NotificacionRequest();
//        nr.setCedula("0105785794");
//        nr.setEmpresa("SRI");
//        nr.setFecha_notificacion("2019-06-28");
//        nr.setServicio("DeclaracionIVA");
//        
//        HashMap<String,Object> hashMap= new HashMap<>();
//        hashMap.put("Doctor", "Juan Pablo Japa");
//        hashMap.put("Especialidad", "Proctólogo");
//        hashMap.put("Lugar", "IESS");
//        
//        nr.setInfo_adicional(hashMap);
//        
//        ClienteController cc = new ClienteController();
//        
//        cc.enviarNotificacionCliente(nr,1L);
        SpringApplication.run(MultiserviciosApplication.class, args);
    }

}
