package Model.Parse;

import java.util.Arrays;

public class Price extends ANumbers {
    private static String[] values = {"million","billion","trillion"};


    public static String parsePrice(int index, String token){
        String res;
        if(token.contains("$")){
            res = token.replace("$","");
            res = classifyNumber(res);
            String nextToken = Parser.getTokenFromList(index+1).toLowerCase();
            if(Arrays.stream(values).anyMatch(nextToken::equals))
                res = res + " " + Character.toUpperCase(nextToken.charAt(0));
                return res + " Dollars";
        }
        return "";
    }
}
