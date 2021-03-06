package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.UsuarioDAO;
import entidades.Usuario;
import java.util.Locale;
import javax.faces.component.UIViewRoot;
import util.SessionUtils;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Usuario.class);

    public static final String HOME_PAGE_REDIRECT = "/secured/main.xhtml?faces-redirect=true";
    public static final String LOGOUT_PAGE_REDIRECT = "/faces/logout.xhtml?faces-redirect=true";

    private Usuario currentUser;
    private String username = "sebastian.catalan@gmail.com";
    private String password = "252525";
    private String idioma = "es";

    //validate login
    public String validateUsernamePassword() {
        currentUser = UsuarioDAO.validar(username, password);
        if (currentUser != null) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("email", currentUser.getEmail());
            session.setAttribute("nombre", currentUser.getNombre());
            LOGGER.info("login successful for '{}'", username);
            
            /* Setear el idioma del usuario */
            idioma = currentUser.getIdioma();
            
            return HOME_PAGE_REDIRECT;
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return null;
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();

        String identifier = username;

        // invalidate the session
        LOGGER.debug("invalidating session for '{}'", identifier);
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();

        LOGGER.info("logout successful for '{}'", identifier);
        return LOGOUT_PAGE_REDIRECT;

    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    
    

}
