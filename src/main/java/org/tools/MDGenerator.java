/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tools;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r.hendrick
 */
public class MDGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // admin : 8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
            // lshi170786 : 33abe8d5235ff29542e141d9bdd7fad168850d67f0e7953755f63b74b6fbdf0a
            // keito : cda19643119b1fd086d76fb23cf4e47c95e93cbf09259ae36486fee41e5b5ec0
            // fox : 776cb326ab0cd5f0a974c1b9606044d8485201f2db19cf8e3749bdee5f36e200
            // iSm : d2cd76186c93a0d46befa655f222ffe47bbc362d55057460b1ba32ad91c22e9a
            String text = "310310";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            System.out.println(output);
            System.out.println("Length :" + output.length());
            if (output.length() < 64) {
                output = "0" + output;
            }
            System.out.println(output);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(MDGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
