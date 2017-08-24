/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.ism.entities.admin.Company;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroupDefRole;
import org.ism.entities.hr.StaffGroups;
import org.ism.jsf.hr.StaffGroupDefController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "ctrlAccessService")
@SessionScoped
public class CtrlAccessService implements Serializable {

    @Inject
    StaffGroupDefController staffGroupDefCtrl;

    /**
     * Allow to create TreeNode comparing to company
     *
     * @param sCompaniesList comapny list
     * @param sGroupsList group list
     * @return tree node corresponding
     */
    public TreeNode makeTreeNodeCompanyGroups(List<Company> sCompaniesList,
            List<StaffGroupDef> sGroupsList) {

        Company entreprise = new Company();
        entreprise.setCCompany("E");
        entreprise.setCDesignation("Entreprise");

        // RACINE
        TreeNode root = new CheckboxTreeNode(new CtrlAccess(entreprise), null);

        // CREE LES COMPANIES SI ELLES EXISTE
        Iterator<Company> sCompaniesIter = sCompaniesList.iterator();
        while (sCompaniesIter.hasNext()) {
            Company company = sCompaniesIter.next();
            TreeNode companyNode
                    = new CheckboxTreeNode(
                            new CtrlAccess(company), root);
            //JsfUtil.out(company.getCCompany() + " - " + company.getCDesignation());

            // Add group to current company
            Iterator<StaffGroupDef> sGroupsIter = sGroupsList.iterator();
            while (sGroupsIter.hasNext()) {
                StaffGroupDef groups = sGroupsIter.next();
                //JsfUtil.out("Loop : ");
                if (Objects.equals(company.getCId(), groups.getStgdCompany().getCId())) {
                    CheckboxTreeNode checkboxTreeNode
                            = new CheckboxTreeNode(
                                    new CtrlAccess(groups), companyNode);
                }
            }
        }
        return root;
    }

    /**
     * Make treeNode with selected components
     *
     * @param sCompaniesList companies
     * @param sGroupsList groupe list
     * @param sStaffGroupsSelected selected staff group
     * @param sSelectedTreeNode selected tree node
     * @return tree node
     */
    public TreeNode makeTreeNodeCompanyGroups(List<Company> sCompaniesList,
            List<StaffGroupDef> sGroupsList,
            List<StaffGroups> sStaffGroupsSelected, TreeNode[] sSelectedTreeNode) {

        // Init Selected treeNode
        sSelectedTreeNode = new TreeNode[sStaffGroupsSelected.size()];

        // RACINE
        Company entreprise = new Company();
        entreprise.setCCompany("E");
        entreprise.setCDesignation("Entreprise");
        TreeNode root = new CheckboxTreeNode(new CtrlAccess(entreprise), null);

        // CREE LES COMPANIES SI ELLES EXISTE
        Iterator<Company> sCompaniesIter = sCompaniesList.iterator();
        while (sCompaniesIter.hasNext()) {
            Company company = sCompaniesIter.next();
            TreeNode companyNode
                    = new CheckboxTreeNode(
                            new CtrlAccess(company), root);
            companyNode.setSelectable(true);
            companyNode.setSelected(false);
            companyNode.setPartialSelected(false);

            // Add group to current company
            Iterator<StaffGroupDef> sGroupsIter = sGroupsList.iterator();
            while (sGroupsIter.hasNext()) {
                StaffGroupDef groups = sGroupsIter.next();
                //JsfUtil.out("Loop : ");
                if (Objects.equals(company.getCId(), groups.getStgdCompany().getCId())) {
                    CheckboxTreeNode checkboxTreeNode
                            = new CheckboxTreeNode(
                                    new CtrlAccess(groups), companyNode);
                    checkboxTreeNode.setSelected(false);

                    // Check if 
                    for (int i = 0; i < sStaffGroupsSelected.size(); i++) {
                        if (company.getCCompany().matches(sStaffGroupsSelected.get(i).getStgCompany().getCCompany())
                                & groups.getStgdGroupDef().matches(sStaffGroupsSelected.get(i).getStgGroupDef().getStgdGroupDef())) {
                            checkboxTreeNode.getParent().setExpanded(true);
                            // Add selected
                            sSelectedTreeNode[i] = checkboxTreeNode;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < sSelectedTreeNode.length; i++) {
            if (sSelectedTreeNode[i] != null) {
                sSelectedTreeNode[i].setSelected(true);
            }
        }

        return root;
    }

    /**
     *
     * @return tree node of security company
     */
    public TreeNode securityCompany() {
        // 0 - RACINE
        CheckboxTreeNode nodeRoot = new CheckboxTreeNode(new CtrlAccess("Entreprise"), null);

        // 1 - 
        Iterator<StaffGroupDef> companyItr = staffGroupDefCtrl.getItemsGroupByCompany().iterator();
        while (companyItr.hasNext()) {
            Company cp = companyItr.next().getStgdCompany();
            CheckboxTreeNode cpNode = new CheckboxTreeNode(cp.getCCompany() + " - " + cp.getCDesignation(),
                    nodeRoot);

            Iterator<StaffGroupDef> itr = staffGroupDefCtrl.getItemsByCompany(cp).iterator();
            while (itr.hasNext()) {
                StaffGroupDef groupdef = itr.next();
                CheckboxTreeNode node = new CheckboxTreeNode(groupdef.getStgdDesignation(),
                        cpNode);
            }
        }
        return nodeRoot;

    }

    public CheckboxTreeNode securityStaff() {
        // 0 - RACINE
        CheckboxTreeNode root = new CheckboxTreeNode(new CtrlAccess("Entreprise"), null);

        if (staffGroupDefCtrl == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            staffGroupDefCtrl = (StaffGroupDefController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffGroupDefController");
        }
        // 1 - 
        Iterator<StaffGroupDef> companyItr = staffGroupDefCtrl.getItemsGroupByCompany().iterator();
        while (companyItr.hasNext()) {
            StaffGroupDef gdef = companyItr.next();
            Company cp = gdef.getStgdCompany();
            CtrlAccess access = new CtrlAccess(gdef);
            access.setName(gdef.getStgdCompany().getCCompany() + " - " + gdef.getStgdCompany().getCDesignation());
            CheckboxTreeNode cpNode = new CheckboxTreeNode(access, root);

            Iterator<StaffGroupDef> itr = staffGroupDefCtrl.getItemsByCompany(cp).iterator();
            while (itr.hasNext()) {
                StaffGroupDef groupdef = itr.next();
                CheckboxTreeNode node = new CheckboxTreeNode(new CtrlAccess(groupdef), cpNode);
            }
        }
        return root;

    }

    public CheckboxTreeNode securityStaff(TreeNode[] selected) {
        // 0 - RACINE
        CheckboxTreeNode root = new CheckboxTreeNode(new CtrlAccess("Entreprise"), null);

        // 1 - 
        // Init. Staff Group def controller if not exist
        if (staffGroupDefCtrl == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            staffGroupDefCtrl = (StaffGroupDefController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffGroupDefController");
        }
        // Loop over each company defined in staff group def
        Iterator<StaffGroupDef> companyItr = staffGroupDefCtrl.getItemsGroupByCompany().iterator();
        while (companyItr.hasNext()) {
            StaffGroupDef gdef = companyItr.next();
            Company cp = gdef.getStgdCompany();
            CtrlAccess access = new CtrlAccess(gdef);
            access.setName(gdef.getStgdCompany().getCCompany() + " - " + gdef.getStgdCompany().getCDesignation());
            CheckboxTreeNode cpNode = new CheckboxTreeNode(access, root);

            // Loop over each group in the existing in staff group def for a specific company
            Iterator<StaffGroupDef> itr = staffGroupDefCtrl.getItemsByCompany(cp).iterator();
            while (itr.hasNext()) {
                StaffGroupDef groupdef = itr.next();
                // Add the new company
                CheckboxTreeNode node = new CheckboxTreeNode(new CtrlAccess(groupdef), cpNode);
                // Check if this group is selected
                if (selected != null) {
                    for (int i = 0; i < selected.length; i++) {
                        CtrlAccess selectedAccess = (CtrlAccess) selected[i].getData();
                        CtrlAccess currentNode = (CtrlAccess) node.getData();
                        if (currentNode.getName().matches(selectedAccess.getName())) {
                            node.setSelected(true);
                        }
                    }
                }
            }
        }
        return root;
    }

    /**
     * *
     *
     * @return a checkbox trenode of security access
     */
    public CheckboxTreeNode securityAccess() {

        // 0 - RACINE
        CheckboxTreeNode nodeRoot = new CheckboxTreeNode(new CtrlAccess("Entreprise"), null);

        {
            CheckboxTreeNode nodeGuest = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("guest_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("guest_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("guest_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("guest_Description")),
                    nodeRoot);

            CheckboxTreeNode nodeCompany = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("company_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("company_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("company_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("company_Description")),
                    nodeRoot);

            CheckboxTreeNode nodeAdmin = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("admin_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("admin_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("admin_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("admin_Description")),
                    nodeCompany);

            CheckboxTreeNode nodeACompany = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompany_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompany_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompany_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompany_Description")),
                    nodeAdmin);

            CheckboxTreeNode nodeCompanyCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyCreate_Description")),
                    nodeACompany);

            CheckboxTreeNode nodeACompanyEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyEdit_Description")),
                    nodeACompany);

            CheckboxTreeNode nodeACompanyList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyList_Description")),
                    nodeACompany);

            CheckboxTreeNode nodeACompanyView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("aCompanyView_Description")),
                    nodeACompany);

            CheckboxTreeNode nodeEntreprise = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entreprise_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entreprise_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entreprise_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entreprise_Description")),
                    nodeAdmin);

            CheckboxTreeNode nodeEntrepriseCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseCreate_Description")),
                    nodeEntreprise);

            CheckboxTreeNode nodeEntrepriseEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseEdit_Description")),
                    nodeEntreprise);

            CheckboxTreeNode nodeEntrepriseList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseList_Description")),
                    nodeEntreprise);

            CheckboxTreeNode nodeEntrepriseView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("entrepriseView_Description")),
                    nodeEntreprise);

            CheckboxTreeNode nodeApp = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("app_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("app_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("app_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("app_Description")),
                    nodeCompany);

            CheckboxTreeNode nodeGenre = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genre_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genre_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genre_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genre_Description")),
                    nodeApp);

            CheckboxTreeNode nodeGenreCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreCreate_Description")),
                    nodeGenre);

            CheckboxTreeNode nodeGenreEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreEdit_Description")),
                    nodeGenre);

            CheckboxTreeNode nodeGenreList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreList_Description")),
                    nodeGenre);

            CheckboxTreeNode nodeGenreView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("genreView_Description")),
                    nodeGenre);

            CheckboxTreeNode nodeNcstate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstate_Description")),
                    nodeApp);

            CheckboxTreeNode nodeNcstateCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateCreate_Description")),
                    nodeNcstate);

            CheckboxTreeNode nodeNcstateEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateEdit_Description")),
                    nodeNcstate);

            CheckboxTreeNode nodeNcstateList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateList_Description")),
                    nodeNcstate);

            CheckboxTreeNode nodeNcstateView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncstateView_Description")),
                    nodeNcstate);

            CheckboxTreeNode nodeNcrstate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstate_Description")),
                    nodeApp);

            CheckboxTreeNode nodeNcrstateCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateCreate_Description")),
                    nodeNcrstate);

            CheckboxTreeNode nodeNcrstateEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateEdit_Description")),
                    nodeNcrstate);

            CheckboxTreeNode nodeNcrstateList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateList_Description")),
                    nodeNcrstate);

            CheckboxTreeNode nodeNcrstateView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncrstateView_Description")),
                    nodeNcrstate);

            CheckboxTreeNode nodeRole = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("role_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("role_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("role_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("role_Description")),
                    nodeApp);

            CheckboxTreeNode nodeRoleCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleCreate_Description")),
                    nodeRole);

            CheckboxTreeNode nodeRoleEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleEdit_Description")),
                    nodeRole);

            CheckboxTreeNode nodeRoleList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleList_Description")),
                    nodeRole);

            CheckboxTreeNode nodeRoleView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("roleView_Description")),
                    nodeRole);

            CheckboxTreeNode nodeHr = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("hr_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("hr_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("hr_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("hr_Description")),
                    nodeCompany);

            CheckboxTreeNode nodeStaff = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staff_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staff_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staff_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staff_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffCreate_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffEdit_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffList_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffView_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffMainMenu = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffMainMenu_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffMainMenu_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffMainMenu_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffMainMenu_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffProfile = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffProfile_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffProfile_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffProfile_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffProfile_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffSetup = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_Description")),
                    nodeStaff);

            CheckboxTreeNode nodeStaffGroupDef = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDef_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDef_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDef_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDef_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffGroupDefCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefCreate_Description")),
                    nodeStaffGroupDef);

            CheckboxTreeNode nodeStaffGroupDefEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefEdit_Description")),
                    nodeStaffGroupDef);

            CheckboxTreeNode nodeStaffGroupDefList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefList_Description")),
                    nodeStaffGroupDef);

            CheckboxTreeNode nodeStaffGroupDefView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefView_Description")),
                    nodeStaffGroupDef);

            CheckboxTreeNode nodeStaffGroupDefRole = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRole_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRole_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRole_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRole_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffGroupDefRoleCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreate_Description")),
                    nodeStaffGroupDefRole);

            CheckboxTreeNode nodeStaffGroupDefRoleEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleEdit_Description")),
                    nodeStaffGroupDefRole);

            CheckboxTreeNode nodeStaffGroupDefRoleList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleList_Description")),
                    nodeStaffGroupDefRole);

            CheckboxTreeNode nodeStaffGroupDefRoleView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleView_Description")),
                    nodeStaffGroupDefRole);

            CheckboxTreeNode nodeStaffGroupDefRoleCreateTree = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreateTree_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreateTree_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreateTree_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupDefRoleCreateTree_Description")),
                    nodeStaffGroupDefRole);

            CheckboxTreeNode nodeStaffGroups = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroups_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroups_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroups_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroups_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffGroupsCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsCreate_Description")),
                    nodeStaffGroups);

            CheckboxTreeNode nodeStaffGroupsEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsEdit_Description")),
                    nodeStaffGroups);

            CheckboxTreeNode nodeStaffGroupsList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsList_Description")),
                    nodeStaffGroups);

            CheckboxTreeNode nodeStaffGroupsView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffGroupsView_Description")),
                    nodeStaffGroups);

            CheckboxTreeNode nodeStaffManager = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManager_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManager_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManager_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManager_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffManagerCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerCreate_Description")),
                    nodeStaffManager);

            CheckboxTreeNode nodeStaffManagerEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerEdit_Description")),
                    nodeStaffManager);

            CheckboxTreeNode nodeStaffManagerList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerList_Description")),
                    nodeStaffManager);

            CheckboxTreeNode nodeStaffManagerView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffManagerView_Description")),
                    nodeStaffManager);

            CheckboxTreeNode nodeStaffSetup_2 = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_2_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_2_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_2_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetup_2_Description")),
                    nodeHr);

            CheckboxTreeNode nodeStaffSetupCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupCreate_Description")),
                    nodeStaffSetup_2);

            CheckboxTreeNode nodeStaffSetupEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupEdit_Description")),
                    nodeStaffSetup_2);

            CheckboxTreeNode nodeStaffSetupList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupList_Description")),
                    nodeStaffSetup_2);

            CheckboxTreeNode nodeStaffSetupView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupView_Description")),
                    nodeStaffSetup_2);

            CheckboxTreeNode nodeStaffSetupMySetup = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupMySetup_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupMySetup_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupMySetup_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("staffSetupMySetup_Description")),
                    nodeStaffSetup_2);

            CheckboxTreeNode nodeSMQ = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("SMQ_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("SMQ_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("SMQ_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("SMQ_Description")),
                    nodeCompany);

            CheckboxTreeNode nodeDocExplorer = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorer_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorer_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorer_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorer_Description")),
                    nodeSMQ);

            CheckboxTreeNode nodeDocExplorerCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreate_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEdit_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerList_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerView_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerCreateAcces = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreateAcces_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreateAcces_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreateAcces_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerCreateAcces_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerEditAcces = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEditAcces_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEditAcces_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEditAcces_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerEditAcces_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerListAcces = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerListAcces_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerListAcces_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerListAcces_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerListAcces_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocExplorerViewAccess = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerViewAccess_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerViewAccess_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerViewAccess_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docExplorerViewAccess_Description")),
                    nodeDocExplorer);

            CheckboxTreeNode nodeDocType = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docType_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docType_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docType_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docType_Description")),
                    nodeSMQ);

            CheckboxTreeNode nodeDocTypeCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeCreate_Description")),
                    nodeDocType);

            CheckboxTreeNode nodeDocTypeEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeEdit_Description")),
                    nodeDocType);

            CheckboxTreeNode nodeDocTypeList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeList_Description")),
                    nodeDocType);

            CheckboxTreeNode nodeDocTypeView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("docTypeView_Description")),
                    nodeDocType);

            CheckboxTreeNode nodeNc = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("nc_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("nc_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("nc_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("nc_Description")),
                    nodeSMQ);

            CheckboxTreeNode nodeActions = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("actions_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("actions_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("actions_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("actions_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcActionsCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsCreate_Description")),
                    nodeActions);

            CheckboxTreeNode nodeNcActionsEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsEdit_Description")),
                    nodeActions);

            CheckboxTreeNode nodeNcActionsList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsList_Description")),
                    nodeActions);

            CheckboxTreeNode nodeNcActionsView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncActionsView_Description")),
                    nodeActions);

            CheckboxTreeNode nodeNcFrequence = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequence_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequence_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequence_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequence_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcFrequenceCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceCreate_Description")),
                    nodeNcFrequence);

            CheckboxTreeNode nodeNcFrequenceEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceEdit_Description")),
                    nodeNcFrequence);

            CheckboxTreeNode nodeNcFrequenceList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceList_Description")),
                    nodeNcFrequence);

            CheckboxTreeNode nodeNcFrequenceView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncFrequenceView_Description")),
                    nodeNcFrequence);

            CheckboxTreeNode nodeNcGravite = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGravite_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGravite_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGravite_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGravite_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcGraviteCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteCreate_Description")),
                    nodeNcGravite);

            CheckboxTreeNode nodeNcGraviteEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteEdit_Description")),
                    nodeNcGravite);

            CheckboxTreeNode nodeNcGraviteList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteList_Description")),
                    nodeNcGravite);

            CheckboxTreeNode nodeNcGraviteView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncGraviteView_Description")),
                    nodeNcGravite);

            CheckboxTreeNode nodeNcNature = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNature_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNature_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNature_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNature_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcNatureCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureCreate_Description")),
                    nodeNcNature);

            CheckboxTreeNode nodeNcNatureEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureEdit_Description")),
                    nodeNcNature);

            CheckboxTreeNode nodeNcNatureList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureList_Description")),
                    nodeNcNature);

            CheckboxTreeNode nodeNcNatureView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncNatureView_Description")),
                    nodeNcNature);

            CheckboxTreeNode nodeNcRequest = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequest_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequest_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequest_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequest_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcRequestCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCreate_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestEdit_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestList_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestView_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestAction = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestAction_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestAction_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestAction_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestAction_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestReview = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestReview_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestReview_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestReview_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestReview_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestvlidate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestvlidate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestvlidate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestvlidate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestvlidate_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcRequestCloture = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCloture_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCloture_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCloture_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncRequestCloture_Description")),
                    nodeNcRequest);

            CheckboxTreeNode nodeNcUnite = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUnite_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUnite_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUnite_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUnite_Description")),
                    nodeNc);

            CheckboxTreeNode nodeNcUniteCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteCreate_Description")),
                    nodeNcUnite);

            CheckboxTreeNode nodeNcUniteEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteEdit_Description")),
                    nodeNcUnite);

            CheckboxTreeNode nodeNcUniteList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteList_Description")),
                    nodeNcUnite);

            CheckboxTreeNode nodeNcUniteView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("ncUniteView_Description")),
                    nodeNcUnite);

            CheckboxTreeNode nodeProcessus = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processus_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processus_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processus_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processus_Description")),
                    nodeSMQ);

            CheckboxTreeNode nodeProcessusCreate = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusCreate_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusCreate_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusCreate_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusCreate_Description")),
                    nodeProcessus);

            CheckboxTreeNode nodeProcessusEdit = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusEdit_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusEdit_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusEdit_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusEdit_Description")),
                    nodeProcessus);

            CheckboxTreeNode nodeProcessusList = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusList_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusList_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusList_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusList_Description")),
                    nodeProcessus);

            CheckboxTreeNode nodeProcessusView = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusView_Name"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusView_RoleName"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusView_Path"),
                    ResourceBundle.getBundle(JsfUtil.SECURITY).getString("processusView_Description")),
                    nodeProcessus);

        }

        // RACINE 
        return nodeRoot;
    }

    public CheckboxTreeNode securitySetPropertyByGroup(CheckboxTreeNode root, List<StaffGroupDefRole> stgdr) {
        if (stgdr != null & root != null) {// If define
            if (!stgdr.isEmpty()) { // If contains stuff
                Iterator<StaffGroupDefRole> iStgdr = stgdr.iterator();
                while (iStgdr.hasNext()) { // Associate role set right property.
                    StaffGroupDefRole groupRole = iStgdr.next();
                    // Loop on tree and set write property
                    securitySetAccess(root, groupRole);
                }
            }
        }
        return root;
    }

    /**
     * Passe au travers de tous l'arbre pour activer l'état son état
     *
     * @param root roort asecurity access
     * @param stgdr staff groupd definition role
     */
    private void securitySetAccess(CheckboxTreeNode root, StaffGroupDefRole stgdr) {
        // Get back controller
        CtrlAccess ctrl = (CtrlAccess) root.getData();

        // if as child child will selected parent
        if (root.getChildCount() > 0) {
            Iterator<TreeNode> iTreeNode = root.getChildren().iterator();
            while (iTreeNode.hasNext()) { // For each child
                securitySetAccess((CheckboxTreeNode) iTreeNode.next(), stgdr);
            }
        } else if (ctrl.getCode().equals(stgdr.getStgdrRole().getRole())) {
            root.setSelected(true);
        }

    }

}
