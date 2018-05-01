/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DataConnect;

/**
 *
 * @author SeBa
 */
public class EmpresaDAO {

    public boolean cargarEmpresa(Empresa e) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * from empresa where idempresa = ? ");
            ps.setInt(1, e.getIdEmpresa());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e.setNombre(rs.getString("nombre"));
                e.setTipo(rs.getString("tipo"));
                e.setDireccion(rs.getString("direccion"));
                e.setEmail(rs.getString("email"));
                e.setCodPostal(rs.getString("codPostal"));
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;

    }

}
