/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.jsf.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tools.MDGenerator;

/**
 *
 * @author r.hendrick
 */
public class JsfSecurity {

    public static class CODING {

        public static final String SHA256 = "SHA-256";
        public static final String MDA = "MDA";

        public static final int LENGTH_SHA256 = 65;
    }

    /**
     * Convert your string password in corresponding
     *
     * admin : 8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
     * lshi170786 :
     * 33abe8d5235ff29542e141d9bdd7fad168850d67f0e7953755f63b74b6fbdf0a keito :
     * cda19643119b1fd086d76fb23cf4e47c95e93cbf09259ae36486fee41e5b5ec0 fox :
     * 776cb326ab0cd5f0a974c1b9606044d8485201f2db19cf8e3749bdee5f36e200 iSm :
     * d2cd76186c93a0d46befa655f222ffe47bbc362d55057460b1ba32ad91c22e9a
     *
     * @param pwd password
     * @param code code of password like MDA, SHA-256
     * @return coded password
     */
    public static String convert(String pwd, String code) {
        String safePwd = null;
        if (CODING.SHA256.matches(code)) {
            try {
                MessageDigest md = MessageDigest.getInstance(CODING.SHA256);
                String password = pwd;
                md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                safePwd = bigInt.toString(16);
                if (safePwd.length() < 64) {
                    safePwd = "0" + safePwd;
                }
                //System.out.println(safePwd);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(MDGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Encodage non définit !!");
        }
        return safePwd;
    }
}
