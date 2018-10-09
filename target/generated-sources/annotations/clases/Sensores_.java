package clases;

import clases.UsuarioDispositivos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Sensores.class)
public class Sensores_ { 

    public static volatile SingularAttribute<Sensores, String> descripcion;
    public static volatile CollectionAttribute<Sensores, UsuarioDispositivos> usuarioDispositivosCollection;
    public static volatile SingularAttribute<Sensores, Integer> id;

}