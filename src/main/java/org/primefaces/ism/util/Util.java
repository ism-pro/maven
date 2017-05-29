/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.ism.util;

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

    public static final String UNSORTED = "UNSORTED";
    public static final String ASCENDING = "ASCENDING";
    public static final String DESCENDING = "DESCENDING";

    public static String convertSortOrderParam(String order) {
        String sortOrder = null;
        int orderNumber = Integer.parseInt(order);

        switch (orderNumber) {
            case 0:
                sortOrder = "UNSORTED";
                break;

            case 1:
                sortOrder = "ASCENDING";
                break;

            case -1:
                sortOrder = "DESCENDING";
                break;
        }

        return sortOrder;
    }
}
