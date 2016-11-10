/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ism.servlet.CountingServletResponse;

/**
 * <h1>CountingFilter</h1>
 *
 * <h2>Description</h2>
 *
 * @see CountingServletOutputStream
 * @see CountingServletResponse
 *
 * @author r.hendrick
 */
public class CountingFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // NOOP.
    }

    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpres = (HttpServletResponse) response;
        CountingServletResponse counter = new CountingServletResponse(httpres);
        HttpServletRequest httpreq = (HttpServletRequest) request;
        httpreq.setAttribute("counter", counter);
        chain.doFilter(request, counter);
        counter.flushBuffer(); // Push the last bits containing HTML comment.
    }

    @Override
    public void destroy() {
        // NOOP.
    }

}
