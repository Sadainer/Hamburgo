package com.hamburgo.tecnoparque.hamburgo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by YOLIMA on 25/08/2016.
 */
public class Formatos {

    public String ConvertirMoneda (Integer valor){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("$ #,###", symbols);
        return decimalFormat.format(valor);
    }
    public String ConvertirMoneda (String valor){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("$ #,###", symbols);
        return decimalFormat.format(Integer.valueOf(valor));
    }
    public String ConvertirMoneda (Double valor){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("$ #,###", symbols);
        return decimalFormat.format(valor);
    }
    public String RedondearDouble (Double valor){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("#", symbols);
        return decimalFormat.format(valor);
    }


}
