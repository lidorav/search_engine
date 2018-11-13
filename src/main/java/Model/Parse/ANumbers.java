package Model.Parse;

public class ANumbers {

    public static String parseNumber(int index, String firstToken){
        final long MILLION = 1000000L;
        final long BILLION = 1000000000L;
        final long TRILLION = 1000000000000L;

        String fraction = "";
        double number = Double.parseDouble(firstToken);
        if ((number >= 1000) && (number < MILLION))
            return number/1000 + "K";
        if((number >= MILLION) && (number < BILLION))
            return number/MILLION + "M";
        if(number >= BILLION)
            return number/BILLION + "B";
        String secToken = Parser.getTokenFromList(index+1).toLowerCase();
        if(secToken.equals("million")){
            Parser.index++;
            return firstToken + " M";
        }
        if(secToken.equals("billion")){
            Parser.index++;
            return firstToken + " B";
        }
        if(secToken.equals("trillion")){
            Parser.index++;
            return number*(TRILLION/BILLION) + " B";
        }
        if(secToken.matches("\\d+\\/\\d+")) {
            Parser.index++;
            fraction = " " + secToken;
        }
        return number + fraction;
    }


}
