/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.ism.jsf.util.JsfUtil;

/**
 * <h1>Util</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class Util {

    static public boolean debug = true;

    /**
     * Logging message on server
     *
     * @param msg message to be display
     */
    public static void out(String msg) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(df.format(new Date()) + " >> " + msg);
    }

    /**
     * Logging message on server
     *
     * @param group groupe
     * @param msg message to be display
     */
    public static void out(String group, String msg) {
        if (!debug) {
            return;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(group + " " + df.format(new Date()) + " >> " + msg);
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Object getController(String controller) {
        if (controller == null || controller.isEmpty()) {
            return null;
        }
        FacesContext ctx = getFacesContext();
        return ctx.getApplication().getELResolver().getValue(ctx.getELContext(), null, controller);
    }

    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    public static UIViewRoot getViewRoot() {
        return getFacesContext().getViewRoot();
    }

    public static void setLocale(Locale locale) {
        getViewRoot().setLocale(locale);
    }

    public static ResourceBundle getResourceBundle(String key) {
        return getApplication().getResourceBundle(getFacesContext(), key);
    }

    /// ////////////////////////////////////////////////////////////////////////
    /// 
    ///
    /// File opérations
    ///
    /// ////////////////////////////////////////////////////////////////////////
    /**
     * Convenient method for write an input stream to a file
     *
     * @param initialStream stream to be write to new filename
     * @param filename a path and name which to be create
     */
    public static void writeToFileSystem(InputStream initialStream, String filename) {
        File targetFile = new File(filename);
        targetFile.getParentFile().mkdirs();
        try {
            java.nio.file.Files.copy(
                    initialStream,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        IOUtils.closeQuietly(initialStream);
    }

    /**
     * Utility method that demonstrates how to write an input stream to the
     * server's local file system.
     *
     * @param byteArrayInputStream byte to rite to file system
     * @param exportFile name of the file
     */
    public static void writeToFileSystem(ByteArrayInputStream byteArrayInputStream, String exportFile) {

        //Use the Java I/O libraries to write the exported content to the file system.
        byte byteArray[] = new byte[byteArrayInputStream.available()];

        //Create a new file that will contain the exported result.
        File file = new File(exportFile);
        file.getParentFile().mkdirs();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available())) {
            int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

            byteArrayOutputStream.write(byteArray, 0, x);
            byteArrayOutputStream.writeTo(fileOutputStream);

            //Close streams.
            byteArrayInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Permet de supprimer le rapport si il existe
     *
     * @param fieldname nom de chemin absolu du rapport
     * @return true if successful delete
     */
    public static Boolean fileDelete(String fieldname) {
        File f = new File(fieldname);
        return f.delete();
    }

    /**
     * writeResourceToFileSystem allow to read a resource in the current
     * application defined by absoluteFilePathname then write it in the
     * corresponding resouce place.
     *
     * @param absoluteFilePathname the name and file of the resource example
     * "img/ism/ism.png" for application logo in the resources directory
     * directories img and ism then you have the file
     * @param rewrite if true the file is neccessarly rewrite
     * @return the full path name create
     */
    public static String writeResourceToFileSystem(String absoluteFilePathname, Boolean rewrite) {
        String absFilePathName = absoluteFilePathname;
        File file = new File(absFilePathName);
        String filename = absFilePathName;

        // Check if the file does not already exist
        if (!file.isFile() || rewrite) {
            // maybe it is a resources
            ResourceHandler resourceHandler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
            InputStream in = null;
            try {
                in = resourceHandler.createResource(absFilePathName).getInputStream();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (in == null) {
                JsfUtil.out(filename + " is not a resources of application...");
                return null;
            }

            // If it is an resource now check if it is not already create
            String rscname = "/ISM/rsc/" + absFilePathName;
            file = new File(rscname);
            if (!file.isFile() || rewrite) {
                Util.out("File " + rscname + " does not exist or need to been recreate ... ");
                Util.writeToFileSystem(in, rscname);
            } else {
                Util.out("File " + rscname + " already exist....");
            }

            // Définit le nom du fichier
            filename = rscname;
        }
        return filename;
    }
    
    /**
     * Surcharge writeResouceToFileSystem which default does not rewrite the file
     * 
     * @param absoluteFilePathname the name and file of the resource example
     * "img/ism/ism.png" for application logo in the resources directory
     * directories img and ism then you have the file
     * @return the full path name create
     */
    public static String writeResourceToFileSystem(String absoluteFilePathname) {
        return writeResourceToFileSystem(absoluteFilePathname, false);
    }
    
    

}
