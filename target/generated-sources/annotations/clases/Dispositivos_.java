package clases;

import clases.UsuarioDispositivos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Dispositivos.class)
public class Dispositivos_ { 

    public static volatile SingularAttribute<Dispositivos, String> inteligente;
    public static volatile CollectionAttribute<Dispositivos, UsuarioDispositivos> usuarioDispositivosCollection;
    public static volatile SingularAttribute<Dispositivos, Integer> consumoMax;
    public static volatile SingularAttribute<Dispositivos, String> nombreDispositivo;
    public static volatile SingularAttribute<Dispositivos, Integer> consumoMin;
    public static volatile SingularAttribute<Dispositivos, String> bajoConsumo;
    public static volatile SingularAttribute<Dispositivos, Integer> id;
    public static volatile SingularAttribute<Dispositivos, Float> consumoKwh;

}