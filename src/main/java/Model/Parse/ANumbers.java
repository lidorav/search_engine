package Model.Parse;

public class ANumbers {

    private String K;
    private String M;
    private String B;
    final long MILLION = 1000000L;
    final long BILLION = 1000000000L;

    public ANumbers() {
        K = "K";
        M = "M";
        B = "B";
    }

    public String classifyNumber(String str){
        double number;
        number = Double.parseDouble(str);

        if ((number >= 1000) && (number < MILLION))
            return "" + number/1000 + K;
        if((number >= MILLION) && (number < BILLION))
            return "" + number/MILLION + M;
        if(number >= BILLION)
            return "" + number/BILLION + B;

        return str;
    }


}
