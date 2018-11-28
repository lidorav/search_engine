package Model.Parse;

public class Combo {

    public static String parseCombo(int index, String token) {
        String res="";
        int counter =0;
        if(Character.isUpperCase(token.charAt(0))){
            res = token;
            counter ++;
            String nextToken = (Parser.getTokenFromList(index+1));
            while (Character.isUpperCase(nextToken.charAt(0))){
                res =  res + " " + nextToken;
                nextToken = (Parser.getTokenFromList(index+1));
            }
        }
        return res;
    }



}
