/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.jsf.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * <h1>UploaderController</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "uploaderController")
@RequestScoped
public class UploaderController implements Serializable {

    private List<UploadedFile> uploadedFiles;

    @PostConstruct
    public void init() {
        uploadedFiles = new ArrayList<UploadedFile>();
    }

    public void upload(FileUploadEvent event) {
        uploadedFiles.add(event.getFile());
        JsfUtil.out("Finish to load file from event");
    }

    public void save() {
        for (UploadedFile uploadedFile : uploadedFiles) {
            // Process them all here.
        }

        // Send only one email.
    }

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public String toImgBase64(byte[] content){
        return Base64.getEncoder().encodeToString(content);
    }
    
}