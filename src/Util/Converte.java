/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author Tegon Faria
 */
public class Converte {
    public static double textoToValue(String txt){
        String num = "0";
        String digitos = "0123456789";
        double val = 0;
        for(int i =0 ; i <txt.length(); i++){
            if(digitos.indexOf(txt.charAt(i))>=0){
                num = num + txt.charAt(i);
            }else if (txt.charAt(i)==',') num = num +'.';
        }
        try{
            val = Double.parseDouble(num);
        }catch (RuntimeException e){
            val = 0;
        }
        return val;
    }
    
}
