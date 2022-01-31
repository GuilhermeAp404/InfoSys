/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Tegon Faria
 */
public class Mascara {
    
    public static DefaultFormatterFactory getCpfMask(){
        MaskFormatter mask = null;
        try{
            mask = new MaskFormatter("###.###.###-##");
            mask.setPlaceholderCharacter('_');
        }catch (ParseException ex){
            return null;
        }
        return (new DefaultFormatterFactory(mask, mask));
    }
    
    public static DefaultFormatterFactory getFoneMask(){
        MaskFormatter mask = null;
        try{
            mask = new MaskFormatter("(##) ####-####");
            mask.setPlaceholderCharacter('_');
        }catch (ParseException ex){
            return null;
        }
        return (new DefaultFormatterFactory(mask, mask));
    }
    
    public static DefaultFormatterFactory getCelMask(){
        MaskFormatter mask = null;
        try{
            mask = new MaskFormatter("(##) #####-####");
            mask.setPlaceholderCharacter('_');
        }catch (ParseException ex){
            return null;
        }
        return (new DefaultFormatterFactory(mask, mask));
    }
    
    public static DefaultFormatterFactory getValorMask(){
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setRoundingMode(RoundingMode.HALF_UP);
        NumberFormatter nf = new NumberFormatter(df);
        nf.setAllowsInvalid(false);
        nf.setMinimum(0.00);
        nf.setMaximum(999999999.99);
        return (new DefaultFormatterFactory(nf));   
    }
    
    
}
