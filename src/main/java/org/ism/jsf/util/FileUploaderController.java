/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.jsf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.UploadedFile;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "fileUploaderController")
@SessionScoped
public class FileUploaderController implements Serializable {

    private Part file = null;
    private String text;
    private String saveFileName;
    
    public static String alternatedocroot_1 = "C:\\ISM\\rsc\\img\\";   // from=/img/*
    public static String alternatedocroot_2 = "C:\\ISM\\rsc\\json\\";  // from=/json/*
    public static String alternatedocroot_3 = "C:\\ISM\\rsc\\smq\\";   // from/smq/*

    public static String diaporama = "C:\\ISM\\rsc\\img\\diaporama\\";   // from=/img/*
    
    /**
     * Creates a new instance of FileUploaderController
     */
    public FileUploaderController() {
    }

    public String uploadF() throws IOException {
        String fileName = getFileName(file);
        System.out.println("############ filename = " + fileName);
        //file.write("\\C:\\GOPFiles\\" + fileName);
        JsfUtil.addErrorMessage("############ filename = " + fileName);

        InputStream is = null;
        FileOutputStream fos = null;

        try {
            is = file.getInputStream();
            fos = new FileOutputStream("\\C:\\GOPFiles\\" + fileName);

            int c;
            while ((c = is.read()) != -1) {
                fos.write(c);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

        return "/public/index";
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("Content-Disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void upload() {
        if (null != file) {
            try {
                InputStream is = file.getInputStream();
                text = new Scanner(is).useDelimiter("\\A").next();
            } catch (IOException ex) {
            }
        } else {

        }
    }

    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void handleUploadPointInfos(FileUploadEvent event) throws IOException {
        UploadedFile uploadedFile = event.getFile();
        //FileUpload fu = (FileUpload) event.getSource();
        String fileName = uploadedFile.getFileName();
        //String contentType = uploadedFile.getContentType();

        InputStream is = null;
        FileOutputStream fos = null;
        Boolean success = (new File(diaporama)).mkdirs();
        try {
            is = uploadedFile.getInputstream();
            fos = new FileOutputStream(diaporama + fileName);

            int c;
            while ((c = is.read()) != -1) {
                fos.write(c);
            }
            saveFileName = diaporama + fileName;
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    
    public void handleUploadChangePointInfos(FileUploadEvent event){
        JsfUtil.out("Handle Change listener");
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

}
