/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.io.Serializable;
import java.util.Objects;
import org.ism.entities.Company;
import org.ism.entities.StaffGroupDef;

/**
 *
 * @author r.hendrick
 */
public class CtrlAccess implements Serializable, Comparable<CtrlAccess> {

    public enum TypeCtrl {

        A_DIRECTORY, A_FILE
    }

    public enum AccessType {

        A_R, A_W, A_N
    }

    private static final long serialVersionUID = 1L;

    private String name;
    private String code = null;
    private String path = null;
    private String description = null;
    private TypeCtrl type = TypeCtrl.A_FILE;
    private AccessType access = AccessType.A_N;
    private Company company = null;
    private StaffGroupDef staffGroupDef = null;
    
    /**
     *
     * @param entity
     */
    public CtrlAccess(String entity) {
        this.name = entity;
        this.type = TypeCtrl.A_DIRECTORY;
        this.access = AccessType.A_N;
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param element
     * @param access
     */
    public CtrlAccess(String element, AccessType access) {
        this.name = element;
        this.type = TypeCtrl.A_DIRECTORY;
        this.access = access;
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param element
     * @param type
     * @param access
     */
    public CtrlAccess(String element, TypeCtrl type, AccessType access) {
        this.name = element;
        this.type = type;
        this.access = access;
    }
    
    public CtrlAccess(String element, String code, String path, String description, TypeCtrl type, AccessType access){
        this.name = element;
        this.code = code;
        this.path = path;
        this.description = description;
        this.type = type;
        this.access = access;
    }
    
    /**
     *
     * @param company
     */
    public CtrlAccess(Company company) {
        this.company = company;
        this.name = company.getCCompany() + " - " + company.getCDesignation();
        this.type = TypeCtrl.A_DIRECTORY;
        this.access = AccessType.A_N;
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param company
     * @param access
     */
    public CtrlAccess(Company company, AccessType access) {
        this.company = company;
        this.name = company.getCCompany() + " - " + company.getCDesignation();
        this.type = TypeCtrl.A_DIRECTORY;
        this.access = access;
    }
    
        /**
     *
     * @param staffGroupDef
     */
    public CtrlAccess(StaffGroupDef staffGroupDef) {
        this.staffGroupDef = staffGroupDef;
        this.name = staffGroupDef.getStgdGroupDef() + " - " + staffGroupDef.getStgdDesignation();
        this.type = TypeCtrl.A_FILE;
        this.access = AccessType.A_N;
    }

    /**
     * Creates a new instance of CtrlAccess
     *
     * @param staffGroupDef
     * @param access
     */
    public CtrlAccess(StaffGroupDef staffGroupDef, AccessType access) {
        this.staffGroupDef = staffGroupDef;
        this.name = staffGroupDef.getStgdGroupDef() + " - " + staffGroupDef.getStgdDesignation();
        this.type = TypeCtrl.A_FILE;
        this.access = access;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TypeCtrl getType() {
        return type;
    }

    public void setType(TypeCtrl type) {
        this.type = type;
    }

    public AccessType getAccess() {
        return access;
    }

    public void setAccess(AccessType access) {
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
            if (other.company != null) return false;
        } else if (!company.equals(other.company))    return false;
        
        if (staffGroupDef == null) {
            if (other.staffGroupDef != null) return false;
        } else if (!staffGroupDef.equals(other.staffGroupDef))    return false;
        
        if (access == null) {
            if (other.access != null) {
                return false;
            }
        } else if (!access.equals(other.access)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
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
        hash = 89 * hash + Objects.hashCode(this.type);
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
