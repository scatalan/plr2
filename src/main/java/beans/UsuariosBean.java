/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UsuarioDAO;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author SeBa
 */
@Named(value = "usuariosBean")
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
