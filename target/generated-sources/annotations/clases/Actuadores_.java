package clases;

import clases.ActuadoresComandos;
import clases.Reglas;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Actuadores.class)
public class Actuadores_ { 

    public static volatile SingularAttribute<Actuadores, String> descripcion;
    public static volatile CollectionAttribute<Actuadores, Reglas> reglasCollection;
    public static volatile CollectionAttribute<Actuadores, ActuadoresComandos> actuadoresComandosCollection;
    public static volatile SingularAttribute<Actuadores, Integer> id;

}