package clases;

import clases.ActuadoresComandos;
import clases.ComandosAdaptadores;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Comandos.class)
public class Comandos_ { 

    public static volatile SingularAttribute<Comandos, String> descripcion;
    public static volatile CollectionAttribute<Comandos, ActuadoresComandos> actuadoresComandosCollection;
    public static volatile SingularAttribute<Comandos, Integer> id;
    public static volatile CollectionAttribute<Comandos, ComandosAdaptadores> comandosAdaptadoresCollection;

}