package clases;

import clases.ComandosAdaptadores;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Adaptadores.class)
public class Adaptadores_ { 

    public static volatile SingularAttribute<Adaptadores, String> descripcion;
    public static volatile SingularAttribute<Adaptadores, Integer> id;
    public static volatile CollectionAttribute<Adaptadores, ComandosAdaptadores> comandosAdaptadoresCollection;

}