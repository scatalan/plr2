/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author SeBa
 */
@Named(value = "empresasBean")
@RequestScoped
public class EmpresasBean {

    /**
     * Creates a new instance of EmpresasBean
     */
    public EmpresasBean() {
    }
    
}
