/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.jsf.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.inject.Named;
import java.util.Scanner;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author r.hendrick
 */
@Named(value = "fileUploaderController")
@SessionScoped
public class FileUploaderController  implements Serializable {

    private Part file = null;
    private String text;

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
        }else{
            
        }
    }
}
