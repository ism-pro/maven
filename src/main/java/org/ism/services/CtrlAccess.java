/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.io.Serializable;
import java.util.Objects;
import org.ism.entities.admin.Company;
import org.ism.entities.hr.StaffGroupDef;

/**
 *
 * @author r.hendrick
 */
public class CtrlAccess implements Serializable, Comparable<CtrlAccess> {

    public enum AccessChecked {
        A_N, A_P, A_C
    }

    private static final long serialVersionUID = 1L;

    //private String xName, xRoleName, xPath, xDescription, 
    private String name;
    private String code = null;
    private String path = null;
    private String description = null;
    private AccessChecked access = AccessChecked.A_N;
    private Company company = null;
    private StaffGroupDef staffGroupDef = null;

    /**
     *
     * @param entity name string entity
     */
    public CtrlAccess(String entity) {
        this.name = entity;
    }

    public CtrlAccess(String name, String code, String path, String description) {
        this.name = name;
        this.code = code;
        this.path = path;
        this.description = description;
        access = AccessChecked.A_N;
    }

    public CtrlAccess(String name, String code, String path, String description, AccessChecked access) {
        this.name = name;
        this.code = code;
        this.path = path;
        this.description = description;
        access = AccessChecked.A_N;
    }

    /**
     *
     * @param company the company
     */
    public CtrlAccess(Company company) {
        this.company = company;
        this.name = company.getCCompany() + " - " + company.getCDesignation();
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param company the company
     * @param access the access
     */
    public CtrlAccess(Company company, AccessChecked access) {
        this.company = company;
        this.name = company.getCCompany() + " - " + company.getCDesignation();
        this.access = access;
    }

    /**
     *
     * @param staffGroupDef staff group definition
     */
    public CtrlAccess(StaffGroupDef staffGroupDef) {
        this.staffGroupDef = staffGroupDef;
        this.name = staffGroupDef.getStgdGroupDef() + " - " + staffGroupDef.getStgdDesignation();
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param staffGroupDef the staff group definition
     * @param access the corresponding access
     */
    public CtrlAccess(StaffGroupDef staffGroupDef, AccessChecked access) {
        this.staffGroupDef = staffGroupDef;
        this.name = staffGroupDef.getStgdGroupDef() + " - " + staffGroupDef.getStgdDesignation();
        this.access = access;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AccessChecked getAccess() {
        return access;
    }

    public void setAccess(AccessChecked access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StaffGroupDef getStaffGroupDef() {
        return staffGroupDef;
    }

    public void setStaffGroupDef(StaffGroupDef staffGroupDef) {
        this.staffGroupDef = staffGroupDef;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        CtrlAccess other = (CtrlAccess) obj;

        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }

        if (staffGroupDef == null) {
            if (other.staffGroupDef != null) {
                return false;
            }
        } else if (!staffGroupDef.equals(other.staffGroupDef)) {
            return false;
        }

        if (access == null) {
            if (other.access != null) {
                return false;
            }
        } else if (!access.equals(other.access)) {
            return false;
        }
        if (access == null) {
            if (other.access != null) {
                return false;
            }
        } else if (!access.equals(other.access)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.company);
        hash = 89 * hash + Objects.hashCode(this.staffGroupDef);
        hash = 89 * hash + Objects.hashCode(this.access);
        return hash;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(CtrlAccess o) {
        return this.getName().compareTo(o.getName());
    }

}
