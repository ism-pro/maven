/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.beans;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.ism.entities.Company;
import org.ism.entities.StaffGroupDef;
import org.ism.entities.StaffGroupDefRole;
import org.ism.entities.StaffGroups;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author r.hendrick
 */
@Named(value = "ctrlAccessService")
@Dependent
public class CtrlAccessService {

    /**
     * Allow to create TreeNode comparing to company
     *
     * @param sCompaniesList
     * @param sGroupsList
     * @return
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
     * @param sCompaniesList
     * @param sGroupsList
     * @param sStaffGroupsSelected
     * @param sSelectedTreeNode
     * @return
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

    public TreeNode securityAccess() {

        // 0 - RACINE
        TreeNode root = new CheckboxTreeNode(new CtrlAccess("Entreprise", CtrlAccess.AccessType.A_N), null);

        // 1 - PUBLIC
        CheckboxTreeNode nodePublic = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("userName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("userRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("userPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("userDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), root);

        // /public/company
        CheckboxTreeNode nodeCompany = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("companyDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodePublic);

        //*** COMPANY CHILD***
        // /public/company/admin
        CheckboxTreeNode nodeAdmin = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("adminName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("adminRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("adminPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("adminDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodeCompany);
        // /public/company/hr
        CheckboxTreeNode nodeHR = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodeCompany);
        //*** /public/company/hr/staff
        CheckboxTreeNode nodeStaff = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffCompanies
        CheckboxTreeNode nodeStaffCompanies = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffCompagnieName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffCompagnieRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffCompagniePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffCompagnieDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffGroupDef
        CheckboxTreeNode nodeStaffGroupDef = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffGroupDefRole
        CheckboxTreeNode nodeStaffGroupDefRole = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefRoleName1"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefRoleRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefRolePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupDefRoleDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffGroups
        CheckboxTreeNode nodeStaffGroups = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupsName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupsRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupsPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffGroupsDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffManager
        CheckboxTreeNode nodeStaffManager = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffManagerName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffManagerRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffManagerPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffManagerDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffProfile
        CheckboxTreeNode nodeStaffProfile = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffProfileName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffProfileRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffProfilePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffProfileDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/hr/staffSetup
        CheckboxTreeNode nodeStaffSetup = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffSetupName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffSetupRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffSetupPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("rhStaffSetupDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeHR);
        //*** /public/company/smq
        CheckboxTreeNode nodeSmq = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodeCompany);
        //*** /public/company/smq/docExplorer
        CheckboxTreeNode nodeDocExplorer = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocExplorerName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocExplorerRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocExplorerPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocExplorerDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeSmq);
        //*** /public/company/smq/docType
        CheckboxTreeNode nodeDocType = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocTypeName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocTypeRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocTypePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqDocTypeDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeSmq);
        //*** /public/company/smq/nc
        CheckboxTreeNode nodeNC = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodeSmq);
        //*** /public/company/smq/nc/frequence
        CheckboxTreeNode nodeNCFrequence = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCFrequenceName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCFrequenceRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCFrequencePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCFrequenceDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNC);
        //*** /public/company/smq/nc/gravite
        CheckboxTreeNode nodeNCGravite = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCGraviteName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCGraviteRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCGravitePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCGraviteDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNC);
        //*** /public/company/smq/nc/nature
        CheckboxTreeNode nodeNCNature = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCNatureName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCNatureRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCNaturePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCNatureDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNC);
        //*** /public/company/smq/nc/request
        CheckboxTreeNode nodeNCRequest = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestDescription"),
                CtrlAccess.TypeCtrl.A_DIRECTORY, CtrlAccess.AccessType.A_N), nodeNC);
        //*** /public/company/smq/nc/request*Cloture
        CheckboxTreeNode nodeNCRequestCloture = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestClotureName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestClotureRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestCloturePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestClotureDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNCRequest);

        //*** /public/company/smq/nc/request*Refuse
        CheckboxTreeNode nodeNCRequestRefuse = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestRefuseName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestRefuseRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestRefusePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestRefuseDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNCRequest);

        //*** /public/company/smq/nc/request*Validation
        CheckboxTreeNode nodeNCRequestValidation = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestValidationName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestValidationRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestValidationPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestValidationDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNCRequest);

        //*** /public/company/smq/nc/request*Action
        CheckboxTreeNode nodeNCRequestAction = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestActionName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestActionRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestActionPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestActionDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNCRequest);

        //*** /public/company/smq/nc/request*ManageState
        CheckboxTreeNode nodeNCRequestManageState = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestManageStateName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestManageStateRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestManageStatePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCRequestManageStateDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNCRequest);

        //*** /public/company/smq/nc/unite
        CheckboxTreeNode nodeNCUnite = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCUniteName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCUniteRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCUnitePath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqNCUniteDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeNC);
        //*** /public/company/smq/process
        CheckboxTreeNode nodeProcess = new CheckboxTreeNode(new CtrlAccess(ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqProcessusName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqProcessusRoleName"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqProcessusPath"),
                ResourceBundle.getBundle(JsfUtil.SECURITY).getString("smqProcessusDescription"),
                CtrlAccess.TypeCtrl.A_FILE, CtrlAccess.AccessType.A_N), nodeSmq);

        // RACINE 
        return root;
    }

    public TreeNode securitySetPropertyByGroup(TreeNode root, List<StaffGroupDefRole> stgdr) {

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

    private void securitySetAccess(TreeNode root, StaffGroupDefRole stgdr) {
        // Get back controller
        CtrlAccess ctrl = (CtrlAccess) root.getData();

        // Identify if read or write or none mode
        CtrlAccess.AccessType ctrlA = CtrlAccess.AccessType.A_N;
        if (ctrl.getCode() != null) {
            String role;//= stgdr.getStgdrRole().getRole().replace("_R", "").replace("_W", "");
            role = replaceLast(stgdr.getStgdrRole().getRole(), "_R", "");
            role = replaceLast(role, "_W", "");
            if (role.matches(ctrl.getCode())) {
                if (stgdr.getStgdrRole().getRole().endsWith("_R")) {
                    ctrl.setAccess(CtrlAccess.AccessType.A_R);
                } else if (stgdr.getStgdrRole().getRole().endsWith("_W")) {
                    ctrl.setAccess(CtrlAccess.AccessType.A_W);
                } else {
                    ctrl.setAccess(CtrlAccess.AccessType.A_N);
                    if (stgdr.getStgdrRole().getRole().matches(ctrl.getCode())) {
                        ctrl.setAccess(CtrlAccess.AccessType.A_W);
                    }
                }
            }
        }

        // check same job to child otherwise has find
        if (root.getChildCount() > 0) {
            Iterator<TreeNode> iTreeNode = root.getChildren().iterator();
            while (iTreeNode.hasNext()) { // For each child
                securitySetAccess(iTreeNode.next(), stgdr);
            }
        }

    }

    String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index) + replacement
                + string.substring(index + substring.length());
    }
}
