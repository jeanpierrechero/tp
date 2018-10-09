package clases;

import clases.Roles;
import clases.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-09T01:06:23")
@StaticMetamodel(UsuarioRoles.class)
public class UsuarioRoles_ { 

    public static volatile SingularAttribute<UsuarioRoles, Date> fechaAlta;
    public static volatile SingularAttribute<UsuarioRoles, Usuarios> usuario;
    public static volatile SingularAttribute<UsuarioRoles, Integer> id;
    public static volatile SingularAttribute<UsuarioRoles, Roles> rol;

}