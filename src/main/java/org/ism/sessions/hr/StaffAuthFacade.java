package org.ism.sessions.hr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ism.entities.hr.Staff;
import org.ism.jsf.util.JsfUtil;

/**
 * StaffAuthFacade This class allows to connect to application.
 *
 * @author r.hendrick
 */
@Stateless
public class StaffAuthFacade implements Serializable {

    // Serial
    private static final long serialVersionUID = 1L;

    // Persistance unit
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private Staff staff;                        //!< Utilisateur ISM
    private HttpSession session = null;         //!< Session du de l'application
    HttpServletRequest request = null;          //!< Request de l'application
    private boolean authenticated = false;      //!< Spécifie si l'utilisateur est authentifié
    private final String A_AUTHENTICATED = "authenticated"; //!< Attribut permettant d'évaluer l'authentification
    List<String> msgList = new ArrayList<String>();

    /*

     private Company company;
     
     */
    private final String NQ_STAFF_COUNT_ALLOWED = "Staff.countAllowed";
    private final String NQ_STAFF_ALLOWED = "Staff.findByAllowed";
    private final String NQ_STAFF_COUNT_ACTIVE_UNDELETED = "Staff.countActiveUndeleted";
    private final String NQ_STAFF_ACTIVE_UNDELETED = "Staff.findByActiveUndeleted";
    private final String NQ_STAFFCOMPANIES_COUNT = "StaffCompanies.countStaffCompanies";
    private final String NQ_STAFFGROUPS = "StaffGroups.findByStaffCompanyActivated";
    private final String NQ_STAFFGROUPS_COUNT = "StaffGroups.countStaffCompanyActivated";

    public StaffAuthFacade() {
    }

    /**
     * Cette méthode vérifie si le staff est initialisé ! Si ce n'est pas le cas
     * celui-ci l'est avant de retourner cette valeur.
     *
     * @return staff : l'utilisateur d'ISM
     */
    public Staff getStaff() {
        if (this.staff == null) {
            staff = new Staff();
        }
        return staff;
    }

    /**
     * Cette méthode permet de remplacer la valeur de staff actuelle par une
     * autre.
     *
     * @param staff : nouveau staff à initialiser.
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    /**
     * Cette méthode permet de récupérer les informations du staff lorsque celui
     * -ci a préalablement été défini ! Voir la méthode prenant en paramètre le
     * staff pour le spécifier directement
     *
     * @return true if found staff
     */
    public boolean findStaff() {
        try {
            // Libération du tempon de requête
            em.flush();
            // Initialisation du staff si, non définit
            getStaff();

            // Count number of allowed staff
            Query q = em.createNamedQuery(NQ_STAFF_COUNT_ACTIVE_UNDELETED).setParameter("stStaff", this.getStaff().getStStaff());
            q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            Long countActiveUndeletedStaff = (Long) q.getSingleResult();
            if (countActiveUndeletedStaff < 1) { // Pas d'utilisateur avec les informations spécifié !
                JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationNoActiveUndeletedStaff"));
                msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationNoActiveUndeletedStaff"));
                return false;
            }

            // Récupère la totalité des informations staff si stStaff existe
            q = em.createNamedQuery(NQ_STAFF_ACTIVE_UNDELETED).setParameter("stStaff", this.getStaff().getStStaff());
            q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            setStaff((Staff) q.getSingleResult());

            // Message de réussite
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationYesActiveUndeletedStaff"));
            msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationYesActiveUndeletedStaff"));
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationNoActiveUndeletedStaff"));
            msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationNoActiveUndeletedStaff"));
            setStaff(null);
            return false;
        }
    }

    /**
     * Surcharge de la méthode permettant de spécifié la variable de staff
     *
     * @param staff : entités de staff
     * @return true if found staff
     */
    public boolean findStaff(Staff staff) {
        setStaff(staff);
        return findStaff();
    }

    /**
     * Surcharge de la méthode permettant de spécifié la variable de code staff
     *
     * @param staff : code de staff
     * @return true if found staff
     */
    public boolean findStaff(String staff) {
        getStaff().setStStaff(staff);
        return findStaff();
    }

    /**
     * Méthode permet de récupérer la requete active
     *
     * @return la session active
     */
    public HttpServletRequest getRequest() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    /**
     * Méthode permet de récupérer la session active
     *
     * @return la session active
     */
    public HttpSession getSession() {
        getRequest();
        session = request.getSession(true);    // Récupération de la session existante
        return session;
    }

    public boolean login() {
        msgList.clear();    // Efface la mémoire des message
        getSession();   // récupère la requête et la session

        try {
            String txtPwd = staff.getStPassword();      //!< Text password
            if (!findStaff()) {
                return false;           // s'assure d'avoir récupérer toutes les infos utilisateur
            }
            request.login(getStaff().getStStaff(), txtPwd);
            session.setMaxInactiveInterval(getStaff().getStMaxInactiveInterval());
            em.flush();

            // Possibilité d'ajouter des tests supplémentaire ici comme la société 
            // ..... ici
            //Validation de la session
            setAuthenticated(true);
            session.setAttribute("authenticated", true);
            session.setAttribute("astaff", staff);
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationPersistenceSuccessOccured"));
            msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationPersistenceSuccessOccured"));
            return authenticated;

        } catch (NoResultException | ServletException ex) {
            setStaff(null);
            setAuthenticated(false);
            session.setAttribute("authenticated", false);
            session.removeAttribute("astaff");
            if (request != null) {
                try {
                    request.logout();
                } catch (ServletException ex1) {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationIsue") + ex);
                    msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationIsue") + ex);
                }
            }
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationDecline"));
            msgList.add(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationDecline"));
            return false;
        } finally {

        }
    }

    /**
     * @return the isAuthenticated
     */
    public boolean isAuthenticated() {
        if (getSession().getAttribute("authenticated") != null) {
            boolean auth = (Boolean) getSession().getAttribute("authenticated");
            if (auth) {
                authenticated = true;
            }
        } else {
            authenticated = false;
        }
        // System.out.println("Are we authenticated? " + auth);
        return authenticated;
    }

    /**
     * @param isAuthenticated the isAuthenticated to set
     */
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    public List<String> getMsgList() {
        return msgList;
    }

    public void logout() {
        getSession().invalidate();
        this.setAuthenticated(false);
        setStaff(null);
    }

}
