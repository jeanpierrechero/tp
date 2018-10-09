package clases;

import clases.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(Categorias.class)
public class Categorias_ { 

    public static volatile SingularAttribute<Categorias, String> descripcion;
    public static volatile SingularAttribute<Categorias, Float> valorVariable;
    public static volatile SingularAttribute<Categorias, Integer> consumoMinimo;
    public static volatile SingularAttribute<Categorias, Float> valorFijo;
    public static volatile CollectionAttribute<Categorias, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<Categorias, Integer> id;
    public static volatile SingularAttribute<Categorias, Integer> consumoMaximo;

}