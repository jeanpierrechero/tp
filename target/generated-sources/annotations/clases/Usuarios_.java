package clases;

import clases.Categorias;
import clases.Domicilios;
import clases.UsuarioDispositivos;
import clases.UsuarioRoles;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:24")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile CollectionAttribute<Usuarios, UsuarioRoles> usuarioRolesCollection;
    public static volatile CollectionAttribute<Usuarios, UsuarioDispositivos> usuarioDispositivosCollection;
    public static volatile SingularAttribute<Usuarios, Categorias> categoria;
    public static volatile SingularAttribute<Usuarios, String> nombreUsuario;
    public static volatile SingularAttribute<Usuarios, Integer> puntos;
    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile SingularAttribute<Usuarios, Integer> codigoUnico;
    public static volatile SingularAttribute<Usuarios, String> tipoDocumento;
    public static volatile SingularAttribute<Usuarios, Domicilios> domicilio;
    public static volatile SingularAttribute<Usuarios, String> apellido;
    public static volatile SingularAttribute<Usuarios, String> contrasenia;
    public static volatile SingularAttribute<Usuarios, Integer> id;
    public static volatile SingularAttribute<Usuarios, Integer> numeroDocumento;
    public static volatile SingularAttribute<Usuarios, Integer> telefono;

}