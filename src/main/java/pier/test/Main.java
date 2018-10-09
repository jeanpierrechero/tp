/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pier.test;

import ar.com.sge.geografia.Transformadores;
import ar.com.sge.geografia.Zonas;
import controllers.TransformadoresJpaController;
import controllers.ZonasJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jean Pierre
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static EntityManagerFactory emf;
    public static void main(String[] args) {
        // TODO code application logic here
        
        emf = Persistence.createEntityManagerFactory("db");
        Zonas zona = new Zonas(12,"la eeeee");
        ZonasJpaController z = new ZonasJpaController(emf);
        z.create(zona);
    }
    
}
