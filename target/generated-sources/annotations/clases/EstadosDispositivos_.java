package clases;

import clases.Estados;
import clases.UsuarioDispositivos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:24")
@StaticMetamodel(EstadosDispositivos.class)
public class EstadosDispositivos_ { 

    public static volatile SingularAttribute<EstadosDispositivos, Float> consumo;
    public static volatile SingularAttribute<EstadosDispositivos, Estados> estado;
    public static volatile SingularAttribute<EstadosDispositivos, UsuarioDispositivos> usuarioDispositivo;
    public static volatile SingularAttribute<EstadosDispositivos, Date> fechaInicio;
    public static volatile SingularAttribute<EstadosDispositivos, Integer> id;
    public static volatile SingularAttribute<EstadosDispositivos, Date> fechaFin;

}