/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.crystal.jsf;


import com.crystaldecisions.report.web.viewer.CrPrintMode;
import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import javax.faces.application.ResourceHandler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.ism.jsf.util.JsfUtil;

/**
 * <h1>crystalController</h1><br>
 * crystalController class
 *
 * @author r.hendrick
 *
 */
@ManagedBean(name = "crystalController")
@SessionScoped
public class CrystalController implements Serializable {

    /**
     * Defined attribute on which to found reporte source. This is useful for
     * crystal report viewer.
     */
    final String ATT_REPORTSOURCE = "ReportSource";

    private String fileReport = "";
    private String filenameExport = "";
    private String fieldname = "";
    private String fieldvalue = "";
    private String fieldDescription = "";

    /**
     * Read mode allow to know if the file should be read in the browser ture
     * otherwise download if false
     */
    private Boolean readMode = true;

    ////////////////////////////////////////////////////////////////////////////
    //
    // Report with database setup
    //
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Handle Crystal Report With Setups for Export
     *
     */
    public void handleCSSetupExport() {
        Object reportSource;
        ReportClientDocument reportClientDocument;
        ByteArrayInputStream byteArrayInputStream;
        byte[] byteArray;
        int bytesRead;

        try {
            // Start by openning the report
            reportClientDocument = new ReportClientDocument();
            reportClientDocument.setReportAppServer(ReportClientDocument.inprocConnectionString);
            reportClientDocument.open(fileReport, OpenReportOptions._openAsReadOnly);

            // Logon to the report
            //reportClientDocument.getDatabaseController().logon(dbProp.getUser(), dbProp.getPassword());
            // Setup some parameter
            ParameterFieldController parameterFieldController = reportClientDocument.getDataDefController()
                    .getParameterFieldController();
            parameterFieldController.setCurrentValue("", fieldname, fieldvalue);
            //reportClientDocument.getDataDefController().getParameterFieldController().setCurrentValue("", fieldname, fieldvalue);

            // Change db location
//            changeDBLocation(reportClientDocument);
            // switch table
            //switch_tables(reportClientDocument);
            // Get coresponding stream
            byteArrayInputStream = (ByteArrayInputStream) reportClientDocument
                    .getPrintOutputController().export(ReportExportFormat.PDF);

            // 
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            //ServletContext servletContext = (ServletContext) ec.getContext();
//
//            //HttpServletRequest request = (HttpServletRequest) ec.getRequest();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            if (readMode) {// show in browser
                response.setHeader("Content-disposition", "inline;filename=cr_report_" + fieldname + ".pdf");
            } else {
                response.setHeader("Content-disposition", "attachment;filename=cr_report_" + fieldname + ".pdf");
            }
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("ReportSource", reportClientDocument.getReportSource());

            //response.reset();
            //response.setHeader("Content-Disposition", "inline;filename=crreport.pdf");
            //response.setContentType("application/pdf");
            byteArray = new byte[1024];
            while ((bytesRead = byteArrayInputStream.read(byteArray)) != -1) {
                response.getOutputStream().write(byteArray, 0, bytesRead);
            }

//            response.getOutputStream().flush();
//            response.getOutputStream().close();
            reportClientDocument.close();

//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            response.addHeader("Content-disposition", "attachment;filename=cr_report_" + fieldname);
//            ServletOutputStream stream = response.getOutputStream();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (ReportSDKException | IOException ex) {
            Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handle Crystal Report Viewer allow to open a file report and set the
     * resource for the viewer with the attribute "ReportSource"
     */
    public void handleCSReportViewer() {
        handleCSReportViewerClose();
        if (fileReport.isEmpty()) {
            JsfUtil.out("File report is empty...");
            return;
        }

        try {

            File file = new File(fileReport);
            String filename = "";

            // Check if the file already exist
            if (!file.isFile()) {
                // maybe it is a resources
                ResourceHandler resourceHandler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
                InputStream in = resourceHandler.createResource(fileReport).getInputStream();
                if (in == null) {
                    JsfUtil.out("File event not exist in resource");
                    return;
                }

                // If it is an resource now check if it is not already create
                String rscname = "C:/ISM/rsc/" + fileReport;
                file = new File(rscname);
                if (!file.isFile()) {
                    JsfUtil.out("File " + rscname + " does not exist ... create one...");
                    writeToFileSystem(in, rscname);
                }else {
                    JsfUtil.out("File " + rscname + " already exist....");
                }

                // Définit le nom du fichier
                filename = rscname;
            } else {
                filename = fileReport;
            }

            // Start by openning the report
            ReportClientDocument reportClientDocument = new ReportClientDocument();
            reportClientDocument.setReportAppServer(ReportClientDocument.inprocConnectionString);
            reportClientDocument.open(filename, OpenReportOptions._openAsReadOnly);

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute(ATT_REPORTSOURCE, reportClientDocument.getReportSource());

            reportClientDocument.close();

        } catch (ReportSDKException | IOException ex) {
            Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handle CS Report Viewer Close allow to close a resource if any
     *
     * Retrieve ReportSource from HTTP Session, pass to the viewer and dispose.
     *
     * This page is used to purely dispose the ReportSource for final report
     * cleanup, and not for viewing.
     *
     * Once the ReportClientDocument instance is closed and all ReportSource
     * objects are disposed, the backend Java Print Engine will close the report
     * and release all resources taken up by the report.
     */
    public void handleCSReportViewerClose() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Object reportSource = session.getAttribute(ATT_REPORTSOURCE);
        if (reportSource != null) {
            try {
                session.removeAttribute(ATT_REPORTSOURCE);
                CrystalReportViewer crystalReportViewer = new CrystalReportViewer();
                crystalReportViewer.setReportSource(reportSource);
                crystalReportViewer.dispose();
                JsfUtil.out("Dispose preview resource");
            } catch (ReportSDKExceptionBase ex) {
                Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            JsfUtil.out("No resource found to be closed");

    }

    /**
     * Pas fonctionnel
     *
     * @deprecated
     */
    public void handleCSView() {
        try {
            CrystalReportViewer crystalReportViewer;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Object reportSource = session.getAttribute("ReportSource");

            crystalReportViewer = new CrystalReportViewer();
            crystalReportViewer.setOwnPage(true);
            crystalReportViewer.setPrintMode(CrPrintMode.ACTIVEX);
            crystalReportViewer.setReportSource(reportSource);

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            crystalReportViewer.processHttpRequest(request, response, servletContext, null);

        } catch (ReportSDKException ex) {
            Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReportSDKExceptionBase ex) {
            Logger.getLogger(CrystalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Utility method that demonstrates how to write an input stream to the
     * server's local file system.
     */
    private void writeToFileSystem(ByteArrayInputStream byteArrayInputStream, String exportFile) throws Exception {

        //Use the Java I/O libraries to write the exported content to the file system.
        byte byteArray[] = new byte[byteArrayInputStream.available()];

        //Create a new file that will contain the exported result.
        File file = new File(exportFile);
        file.getParentFile().mkdirs();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available());
            int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

            byteArrayOutputStream.write(byteArray, 0, x);
            byteArrayOutputStream.writeTo(fileOutputStream);

            //Close streams.
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
        }

    }

    /**
     * Convenient method for write an input stream to a file 
     * @param initialStream stream to be write to new filename 
     * @param filename a path and name which to be create
     * @throws IOException  exception
     */
    public void writeToFileSystem(InputStream initialStream, String filename) throws IOException {

        File targetFile = new File(filename);
        targetFile.getParentFile().mkdirs();
        java.nio.file.Files.copy(
                initialStream,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        IOUtils.closeQuietly(initialStream);
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Getter / Setter
    //
    ////////////////////////////////////////////////////////////////////////////
    public String getFileReport() {
        return fileReport;
    }

    public void setFileReport(String fileReport) {
        this.fileReport = fileReport;
    }

    public String getFilenameExport() {
        return filenameExport;
    }

    public void setFilenameExport(String filenameExport) {
        this.filenameExport = filenameExport;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldvalue() {
        return fieldvalue;
    }

    public void setFieldvalue(String fieldvalue) {
        this.fieldvalue = fieldvalue;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public Boolean getReadMode() {
        return readMode;
    }

    public void setReadMode(Boolean readMode) {
        this.readMode = readMode;
    }

}
