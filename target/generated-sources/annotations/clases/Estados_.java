package clases;

import clases.EstadosDispositivos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:24")
@StaticMetamodel(Estados.class)
public class Estados_ { 

    public static volatile SingularAttribute<Estados, String> descripcion;
    public static volatile CollectionAttribute<Estados, EstadosDispositivos> estadosDispositivosCollection;
    public static volatile SingularAttribute<Estados, Integer> id;

}