package beans;

import dao.UsuarioDAO;
import entidades.Usuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UsuariosBean {

    private List<Usuario> usuarios;

    /**
     * Creates a new instance of UsuariosBean
     */
    public UsuariosBean() {

        usuarios = UsuarioDAO.listaUsuariosEmpresa(1);

    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void actualizaUsuarios() {
        usuarios = UsuarioDAO.listaUsuariosEmpresa(1);

    }

}
