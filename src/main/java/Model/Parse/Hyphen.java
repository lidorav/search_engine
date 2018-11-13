package Model.Parse;

public class Hyphen {

    public static String parseHyphen(int index, String token){
        String res="";
        if(token.contains("-")){
            return token;
        }
        if(token.toLowerCase().equals("between")){
            String secToken =  Parser.getTokenFromList(index+1);
            if(secToken.chars().allMatch(Character::isDigit)){
                String thirdToken = Parser.getTokenFromList(index+2);
                if(thirdToken.toLowerCase().equals("and")){
                    String forthToken = Parser.getTokenFromList(index+3);
                    if(forthToken.chars().allMatch(Character::isDigit)){
                        res = token + " " + secToken + " " + thirdToken + " " + forthToken;
                        Parser.index = Parser.index + 3;
                    }
                }
            }
        }
        return res;
    }
}
