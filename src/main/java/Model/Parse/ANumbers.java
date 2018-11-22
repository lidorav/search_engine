package Model.Parse;

public class ANumbers {
    final static long MILLION = 1000000L;
    final static long BILLION = 1000000000L;
    final static long TRILLION = 1000000000000L;

    public static String parseNumber(int index, String firstToken) {
        if(firstToken.contains("\"") && firstToken.length()>1)
            firstToken = firstToken.replace("\"","");
        if (firstToken.matches("\\d+\\/\\d+")) {
            return firstToken;
        }

        String fraction = "";
        try {
            double number = Double.parseDouble(firstToken);
            if ((number >= 1000) && (number < MILLION))
                return number / 1000 + "K";
            if ((number >= MILLION) && (number < BILLION))
                return number / MILLION + "M";
            if (number >= BILLION)
                return number / BILLION + "B";

            String secToken = Parser.getTokenFromList(index + 1).toLowerCase();
            if (secToken.equals("thousand")) {
                Parser.index++;
                return firstToken + "K";
            }
            if (secToken.equals("million")) {
                Parser.index++;
                return firstToken + "M";
            }
            if (secToken.equals("billion")) {
                Parser.index++;
                return firstToken + "B";
            }
            if (secToken.equals("trillion")) {
                Parser.index++;
                return number * (TRILLION / BILLION) + "B";
            }
            if (secToken.matches("\\d+\\/\\d+")) {
                Parser.index++;
                fraction = " " + secToken;
            }
            return number + fraction;
        }
        catch (Exception e) {
            return firstToken;
        }
    }

    public static String parseNumber(String firstToken, String secToken){
        String fraction= "";
        double number = Double.parseDouble(firstToken);
        if ((number >= 1000) && (number < MILLION))
            return number/1000 + "K";
        if((number >= MILLION) && (number < BILLION))
            return number/MILLION + "M";
        if(number >= BILLION)
            return number/BILLION + "B";
        if(secToken.equals("thousand")){
            return firstToken + "K";
        }
        if(secToken.equals("million")){
            return firstToken + "M";
        }
        if(secToken.equals("billion")){
            return firstToken + "B";
        }
        if(secToken.equals("trillion")){
            return number*(TRILLION/BILLION) + "B";
        }
        if(secToken.matches("\\d+\\/\\d+")) {
            fraction = " " + secToken;
        }
        return number + fraction;
    }
}
