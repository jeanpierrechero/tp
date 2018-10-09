package clases;

import clases.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Domicilios.class)
public class Domicilios_ { 

    public static volatile SingularAttribute<Domicilios, Integer> coordenadaY;
    public static volatile SingularAttribute<Domicilios, Integer> coordenadaX;
    public static volatile CollectionAttribute<Domicilios, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<Domicilios, String> nombreDomicilio;
    public static volatile SingularAttribute<Domicilios, Integer> id;

}