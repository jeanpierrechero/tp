package clases;

import clases.Dispositivos;
import clases.EstadosDispositivos;
import clases.Sensores;
import clases.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(UsuarioDispositivos.class)
public class UsuarioDispositivos_ { 

    public static volatile SingularAttribute<UsuarioDispositivos, Integer> estado;
    public static volatile SingularAttribute<UsuarioDispositivos, Boolean> encendido;
    public static volatile SingularAttribute<UsuarioDispositivos, Dispositivos> dispositivo;
    public static volatile CollectionAttribute<UsuarioDispositivos, EstadosDispositivos> estadosDispositivosCollection;
    public static volatile SingularAttribute<UsuarioDispositivos, Date> periodo;
    public static volatile SingularAttribute<UsuarioDispositivos, Sensores> sensor;
    public static volatile SingularAttribute<UsuarioDispositivos, Usuarios> usuario;
    public static volatile SingularAttribute<UsuarioDispositivos, Integer> id;
    public static volatile SingularAttribute<UsuarioDispositivos, Boolean> apagadoAutomatico;
    public static volatile SingularAttribute<UsuarioDispositivos, Boolean> modulo;

}