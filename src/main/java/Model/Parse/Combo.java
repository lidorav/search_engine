package Model.Parse;

public class Combo {

    public static String parseCombo(int index, String token) {
        String res="";
        int counter =1;
        if (token.contains("!"))
            return "";
        if(Character.isUpperCase(token.charAt(0))){
            res = token;
            String nextToken = (Parser.getTokenFromList(index+1));
            while (Character.isUpperCase(nextToken.charAt(0))){
                res =  res + " " + nextToken;
                Parser.replaceToken(index + counter , token + "!");
                counter ++;
                nextToken = (Parser.getTokenFromList(index+counter));

            }
            if(counter>1) {
                Parser.replaceToken(index, token + "!");
                Parser.index--;
            }
        }
        return res;
    }




}
