package Model.Parse;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ANumbers {
    final static long MILLION = 1000000L;
    final static long BILLION = 1000000000L;
    final static long TRILLION = 1000000000000L;

    public static String parseNumber(int index, String firstToken) {
        String res;
            if (firstToken.contains("\"") && firstToken.length() > 1)
                firstToken = firstToken.replace("\"", "");
            if (firstToken.matches("\\d+\\/\\d+")) {
                return firstToken;
            }
            try {
                String secToken = Parser.getTokenFromList(index + 1).toLowerCase();
                res = parseNumber(firstToken, secToken);
            } catch (Exception e) {
            return firstToken;
        }
        return res;
    }
        public static String parseNumber (String firstToken, String secToken){
            String fraction = "";
            double number = Double.parseDouble(firstToken);
            if(number<1000)
                return "" + round(number,2);
            if ((number >= 1000) && (number < MILLION))
                return round(number / 1000,2) + "K";
            if ((number >= MILLION) && (number < BILLION))
                return round(number / MILLION,2) + "M";
            if (number >= BILLION)
                return round(number / BILLION,2) + "B";
            if (secToken.equals("thousand")) {
                Parser.index++;
                return round(number,2) + "K";
            }
            if (secToken.equals("million")) {
                Parser.index++;
                return round(number,2) + "M";
            }
            if (secToken.equals("billion")) {
                Parser.index++;
                return round(number,2) + "B";
            }
            if (secToken.equals("trillion")) {
                Parser.index++;
                return round(number,2) * (TRILLION / BILLION) + "B";
            }
            if (secToken.matches("\\d+\\/\\d+")) {
                Parser.index++;
                fraction = " " + secToken;
            }
            return number + fraction;
        }

    private static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
