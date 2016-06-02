package org.ism.jsf;

import org.ism.entities.StaffGroupDefRole;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.StaffGroupDefRoleFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import org.ism.services.CtrlAccess;
import org.ism.services.CtrlAccessService;
import org.ism.entities.IsmRole;
import org.ism.entities.StaffGroupDef;
import org.ism.sessions.IsmRoleFacade;
import org.primefaces.component.picklist.PickList;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

@Named("staffGroupDefRoleController")
@SessionScoped
public class StaffGroupDefRoleController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.StaffGroupDefRoleFacade ejbFacade;
    private List<StaffGroupDefRole> items = null;
    private StaffGroupDefRole selected;
    private TreeNode nodeAccessTree = null;
    private TreeNode[] nodeAccessTreeSelected = null;
    private TreeNode selectedAccess = null;

    @EJB
    private IsmRoleFacade irf;
    private DualListModel<IsmRole> sgdr;

    public StaffGroupDefRoleController() {
    }

    public StaffGroupDefRole getSelected() {
        return selected;
    }

    public void setSelected(StaffGroupDefRole selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private StaffGroupDefRoleFacade getFacade() {
        return ejbFacade;
    }

    public StaffGroupDefRole prepareCreate() {
        selected = new StaffGroupDefRole();
        initializeEmbeddableKey();
        return selected;
    }

    public StaffGroupDefRole prepareCreateTree() {
        /*
        //CtrlAccessService cas = new CtrlAccessService();
        //nodeAccessTree = cas.securityAccess();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Sauvegarde la vue RH.
        RibbonView ribbonView = (RibbonView) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ribbonView");
        ///ribbonView.setActiveIndex(2);// 2 - RH
        */
        return prepareCreate();
        
    }

    public void create() {
        selected.setStgdrCreated(new Date());
        selected.setStgdrChanged(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createPicklist() {
        selected.setStgdrCreated(new Date());
        selected.setStgdrChanged(new Date());
        selected.setStgdrActivated(true);

        System.out.println("********* Start affecting role to groupe ....." + selected.getStgdrGroupDef().getStgdDesignation());
        Iterator<IsmRole> target = sgdr.getTarget().iterator();
        int i = 0;
        while (target.hasNext()) {
            i++;
            String value = String.valueOf(target.next());
            String val = value.split("=")[1].split("]")[0].trim();
            int id = Integer.valueOf(val);

            IsmRole role = new IsmRole();
            role.setId(id);
            System.out.println(i + " >> " + role.getId() + " " + role.getRole() + " " + role.getRolename());
            selected.setStgdrRole(role);
            persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        }
        System.out.println("********* End affecting role to groupe ..... nbRole = " + i);
        /*if (!JsfUtil.isValidationFailed()) {
         items = null;    // Invalidate list of items to trigger re-query.
         }*/
    }

    public void update() {
        selected.setStgdrChanged(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void listenerOnGroupChange(ValueChangeEvent event) {
        StaffGroupDef stgd = (StaffGroupDef) event.getNewValue();
        if (stgd != null) {
            // Reset node access tree
            CtrlAccessService cas = new CtrlAccessService();
            nodeAccessTree = cas.securityAccess();

            // Set node tree with existing property 
            cas.securitySetPropertyByGroup(nodeAccessTree, getItems(stgd));
        } else {
            nodeAccessTree = null;
        }

    }

    public void listenerOnSelectNode(NodeSelectEvent event) {
        selectedAccess = event.getTreeNode();
    }

    public void changeAccessOnSelected(TreeNode root, CtrlAccess.AccessType type) {
        if (root != null) {
            ((CtrlAccess) root.getData()).setAccess(type);
            if (root.getChildCount() > 0) {// if existing child do same
                Iterator<TreeNode> inode = root.getChildren().iterator();
                while (inode.hasNext()) { // for each child
                    TreeNode node = inode.next();
                    ((CtrlAccess) node.getData()).setAccess(type);
                    if (node.getChildCount() > 0) {
                        changeAccessOnSelected(node, type);
                    }
                }
            }
        }
    }

    public void listenerOnNodeAccessNone() {
        changeAccessOnSelected(selectedAccess, CtrlAccess.AccessType.A_N);
    }

    public void listenerOnNodeAccessRead() {
        changeAccessOnSelected(selectedAccess, CtrlAccess.AccessType.A_R);
    }

    public void listenerOnNodeAccessWrite() {
        changeAccessOnSelected(selectedAccess, CtrlAccess.AccessType.A_W);
    }

    public void applyChangedTree() {
        // Vérifie qu'un groupe à été sélectionné !
        if (selected.getStgdrGroupDef() == null) {
            JsfUtil.addErrorMessage("stgdGroupDef",
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("StaffGroupsRequiredMsg_stgGroupDef"));
            return;
        }

        // Pour chaque composant de l'abre vérfier déterminer le type d'accès
        // Ensuite, ensuite vérifier si ce type existe en fonction on supprime
        // ou met à jour.
        updateAccessRole(nodeAccessTree);
        //JsfUtil.addSuccessMessage("Apply finished !");
    }

    /**
     * Récursive méthod allowing to apply change on tree node
     *
     * @param root
     */
    private void updateAccessRole(TreeNode root) {
        // Escape first entreprise
        if (root.getParent() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // Sauvegarde la vue RH.
            IsmRoleController ismRoleCtrl = (IsmRoleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ismRoleController");
            ismRoleCtrl.prepareCreate();

            // Get child setup
            CtrlAccess ctrl = (CtrlAccess) root.getData();
            //JsfUtil.out(">> Current is " + ctrl.toString());
            IsmRole ismRole = ismRoleCtrl.getItemBy(ctrl.getCode()); // Check role as if parent
            StaffGroupDefRole stgdr;
            if (selected == null) {
                //JsfUtil.out("Gros problème le groupe est indéfini !");
                //JsfUtil.addErrorMessage("Gros problème le groupe est indéfini !");
            }
            if (ismRole != null & ctrl.getAccess() != CtrlAccess.AccessType.A_N) {

                stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                if (stgdr == null) { // n'exite pas
                    selected.setStgdrRole(ismRole);
                    selected.setStgdrActivated(true);
                    this.create();
                }
            } else if (ctrl.getAccess() == CtrlAccess.AccessType.A_W) {        // Must accept read and write
                //JsfUtil.out("Access is in write");

                ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_W");
                // Check if already existing in table
                stgdr = null;
                if (ismRole != null) {
                    stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);

                    if (stgdr == null) { // n'exite pas
                        selected.setStgdrRole(ismRole);
                        selected.setStgdrActivated(true);
                        this.create();
                        // Check if read access is setup otherwise create it !
                        ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_R");
                        stgdr = null;
                        if (ismRole != null) {
                            stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                        }
                        if (stgdr == null) { // n'exite pas
                            selected.setStgdrRole(ismRole);
                            selected.setStgdrActivated(true);
                            this.create();
                        }
                    }
                }

            } else if (ctrl.getAccess() == CtrlAccess.AccessType.A_R) {  // Must accept only read
                //JsfUtil.out("Access is in read");
                // Supprime uniquement l'écriture si il existe
                //JsfUtil.out("Code is not parent !");
                ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_W");
                // Check if already existing in table
                stgdr = null;
                if (ismRole != null) {
                    stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                }
                if (stgdr != null) {// Résultat
                    StaffGroupDef g = selected.getStgdrGroupDef();
                    selected = stgdr;
                    this.destroy();
                    selected = new StaffGroupDefRole();
                    selected.setStgdrGroupDef(g);
                }
                // Read part
                ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_R");
                stgdr = null;
                if (ismRole != null) {
                    stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                }
                if (stgdr == null) { // n'exite pas
                    //JsfUtil.out("Add read role with !");
                    selected.setStgdrRole(ismRole);
                    selected.setStgdrActivated(true);
                    this.create();
                }

            }
            // Suppression
            if (ctrl.getAccess() == CtrlAccess.AccessType.A_N) {                                                // Remove any existing right
                //JsfUtil.out("Access is in none");
                stgdr = null;
                if (ismRole != null) {
                    stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                    if (stgdr != null) {
                        StaffGroupDef g = selected.getStgdrGroupDef();
                        selected = stgdr;
                        this.destroy();
                        selected = new StaffGroupDefRole();
                        selected.setStgdrGroupDef(g);
                    }
                } else {
                    // Supprime uniquement l'écriture si il existe
                    ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_W");
                    // Check if already existing in table
                    stgdr = null;
                    if (ismRole != null) {
                        stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                        if (stgdr != null) {
                            StaffGroupDef g = selected.getStgdrGroupDef();
                            selected = stgdr;
                            this.destroy();
                            selected = new StaffGroupDefRole();
                            selected.setStgdrGroupDef(g);
                        }
                    }

                    ismRole = ismRoleCtrl.getItemBy(ctrl.getCode() + "_R");
                    // Check if already existing in table
                    stgdr = null;
                    if (ismRole != null) {
                        stgdr = this.getItemsBy(selected.getStgdrGroupDef(), ismRole);
                        if (stgdr != null) {
                            StaffGroupDef g = selected.getStgdrGroupDef();
                            selected = stgdr;
                            this.destroy();
                            selected = new StaffGroupDefRole();
                            selected.setStgdrGroupDef(g);
                        }
                    }
                }

            }
        }
        // check same job to child otherwise has find
        if (root.getChildCount() > 0) {
            Iterator<TreeNode> iTreeNode = root.getChildren().iterator();
            while (iTreeNode.hasNext()) { // For each child
                updateAccessRole(iTreeNode.next());
            }
        }
    }

    public List<StaffGroupDefRole> getItems() {
        //if (items == null) {
            items = getFacade().findAll();
        //}
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
            }
        }
    }

    public StaffGroupDefRole getStaffGroupDefRole(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<StaffGroupDefRole> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<StaffGroupDefRole> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<StaffGroupDefRole> getItems(StaffGroupDef groupDef) {
        return getFacade().findByStaffGroups(groupDef);
    }

    private StaffGroupDefRole getItemsBy(StaffGroupDef stgdrGroupDef, IsmRole role) {
        return getFacade().findBy(stgdrGroupDef, role);
    }

    public TreeNode getNodeAccessTree() {
        return nodeAccessTree;
    }

    public void setNodeAccessTree(TreeNode nodeAccessTree) {
        this.nodeAccessTree = nodeAccessTree;
    }

    public TreeNode[] getNodeAccessTreeSelected() {
        return nodeAccessTreeSelected;
    }

    public void setNodeAccessTreeSelected(TreeNode[] nodeAccessTreeSelected) {
        this.nodeAccessTreeSelected = nodeAccessTreeSelected;
    }

    /**
     * Init View PickList
     */
    @PostConstruct
    public void init() {
        //Cities
        List<IsmRole> sgdrSrc = new ArrayList<IsmRole>();
        List<IsmRole> sgdrTarget = new ArrayList<IsmRole>();
        sgdrSrc = irf.findAll();
        sgdr = new DualListModel<IsmRole>(sgdrSrc, sgdrTarget);
    }

    public DualListModel<IsmRole> getSgdr() {
        return sgdr;
    }

    public void setSgdr(DualListModel<IsmRole> sgdr) {
        this.sgdr = sgdr;
    }

    @FacesConverter(forClass = StaffGroupDefRole.class)
    public static class StaffGroupDefRoleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StaffGroupDefRoleController controller = (StaffGroupDefRoleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffGroupDefRoleController");
            return controller.getStaffGroupDefRole(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof StaffGroupDefRole) {
                StaffGroupDefRole o = (StaffGroupDefRole) object;
                return getStringKey(o.getStgdrId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), StaffGroupDefRole.class.getName()});
                return null;
            }
        }

    }

    @ManagedBean
    @RequestScoped
    public class IsmRoleConverter implements Converter {

        @EJB
        private IsmRoleFacade operationService;

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            // Convert here Operation object to String value for use in HTML.
            if (!(value instanceof IsmRole) || ((IsmRole) value).getId() == null) {
                return null;
            }
            return String.valueOf(((IsmRole) value).getId() + "-" + ((IsmRole) value).getRolename());
        }

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            // Convert here String submitted value to Operation object.
            if (value == null || !value.matches("\\d+")) {
                return null;
            }

            IsmRole operation = operationService.find(Long.valueOf(value));

            if (operation == null) {
                throw new ConverterException(new FacesMessage("Unknown operation ID: " + value));
            }

            return operation;
        }
    }

    @ManagedBean
    @RequestScoped
    @FacesConverter(value = "PrimeFacesPickListConverter")
    public class PrimeFacesPickListConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
            Object ret = null;
            if (arg1 instanceof PickList) {
                Object dualList = ((PickList) arg1).getValue();
                DualListModel dl = (DualListModel) dualList;
                for (Object o : dl.getSource()) {
                    String id = "";
                    if (o instanceof IsmRole) {
                        id += ((IsmRole) o).getId();
                    }
                    /*if (o instanceof Grupo) {
                     id += ((Grupo) o).getId();
                     }*/
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                if (ret == null) {
                    for (Object o : dl.getTarget()) {
                        String id = "";
                        if (o instanceof IsmRole) {
                            id += ((IsmRole) o).getId();
                        }
                        /*if (o instanceof Grupo) {
                         id += ((Grupo) o).getId();
                         }*/
                        if (arg2.equals(id)) {
                            ret = o;
                            break;
                        }
                    }
                }
            }
            return ret;
        }

        @Override
        public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
            String str = "";
            if (arg2 instanceof IsmRole) {
                str = "" + ((IsmRole) arg2).getId() + ((IsmRole) arg2).getRolename();
            }
            /*if (arg2 instanceof IsmRole) {
             str = "" + ((IsmRole) arg2).getId();
             }*/
            return str;
        }
    }

}
