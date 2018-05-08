/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Empresa;
import entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DataConnect;

/**
 *
 * @author SeBa
 */
public class UsuarioDAO {

    /**
     * Permite validar si usuario es valido o no
     *
     * @param username
     * @param password
     * @return
     */
    public static Usuario validar(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        Usuario u;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from usuario where email = ? and password = md5(?)");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setNombre(rs.getString("nombre"));
                u.setCargo(rs.getString("cargo"));
                u.setTelefono(rs.getString("telefono"));
                u.setBemail(rs.getString("bemail"));
                Empresa e = new Empresa(rs.getInt("idEmpresa"));
                EmpresaDAO ed = new EmpresaDAO();
                ed.cargarEmpresa(e);
                u.setEmpresa(e);
                u.setEsAdmin(rs.getInt("esAdmin"));
                return u;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
        return null;
    }

    public boolean addUsuario(Usuario u) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("INSERT INTO usuario(email, password, "
                    + "nombre, cargo, telefono, bemail, idEmpresa, esAdmin) VALUES (?,md5(?),?,?,?,?,?,?);");
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getCargo());
            ps.setString(5, u.getTelefono());
            ps.setInt(7, u.getEmpresa().getIdEmpresa());
            ps.setInt(8, u.getEsAdmin());
            int res = ps.executeUpdate();
            return res > 0;

        } catch (SQLException ex) {
            System.out.println("Error insertando usuario -->" + ex.getMessage());
            return false;

        }

    }

    public static List<Usuario> listaUsuariosEmpresa(int idEmpresa) {
        List<Usuario> listado = new ArrayList<Usuario>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE idEmpresa = ? ;");
            ps.setInt(1, idEmpresa);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Usuario u = new Usuario();
                u.setEmail(rs.getString("email"));
                u.setPassword("");
                u.setNombre(rs.getString("nombre"));
                u.setCargo(rs.getString("cargo"));
                u.setTelefono(rs.getString("telefono"));
                u.setBemail(rs.getString("bemail"));
                Empresa e = new Empresa(rs.getInt("idEmpresa"));
                EmpresaDAO ed = new EmpresaDAO();
                ed.cargarEmpresa(e);
                u.setEmpresa(e);
                u.setEsAdmin(rs.getInt("esAdmin"));
                listado.add(u);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error insertando usuario -->" + ex.getMessage());
        }finally{
            return listado;
        }

    }

}
