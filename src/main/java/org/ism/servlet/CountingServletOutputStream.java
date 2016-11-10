/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.servlet;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import org.apache.commons.io.output.CountingOutputStream;

/**
 * <h1>CountingServletOutputStream</h1>
 *
 * <h2>Description</h2>
 *
 *
 * @author r.hendrick
 */
public class CountingServletOutputStream extends ServletOutputStream {

    private final CountingOutputStream output;

    public CountingServletOutputStream(ServletOutputStream output) {
        this.output = new CountingOutputStream(output);
    }

    @Override
    public void write(int b) throws IOException {
        output.write(b);
    }

    @Override
    public void flush() throws IOException {
        output.flush();
    }

    public long getByteCount() {
        return output.getByteCount();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}