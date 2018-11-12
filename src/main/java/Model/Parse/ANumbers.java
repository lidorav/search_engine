package Model.Parse;

public class ANumbers {

    public static String classifyNumber(String str){
        final long MILLION = 1000000L;
        final long BILLION = 1000000000L;
        double number;
        number = Double.parseDouble(str);

        if ((number >= 1000) && (number < MILLION))
            return "" + number/1000 + "K";
        if((number >= MILLION) && (number < BILLION))
            return "" + number/MILLION + "M";
        if(number >= BILLION)
            return "" + number/BILLION + "B";

        return str;
    }


}
