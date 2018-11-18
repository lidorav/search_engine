package Model.Parse;

public class Text {

    public static String parseText(int index, String token){
        String upperToken = token.toUpperCase();
        String lowerToken = token.toLowerCase();
        if(lowerToken.equals("between"))
            return "";
        //check if first letter is uppercased
        if(Character.isUpperCase(token.charAt(0))) {
            //check if uppercased word exist in dictionary, if so return uppercased
            if (Parser.checkExist(upperToken)) {
                return upperToken;
            }
            else {
                //check if lowered cased exist in the dictionary, if so return lowercased
                if (Parser.checkExist(lowerToken))
                    return lowerToken;
                //if none if the cases exist = dictionary doesn't contain this word return uppercased
                else
                    return upperToken;
            }
        }
        else{
            if(Parser.checkExist(upperToken)){
                Parser.replaceTerm(upperToken,lowerToken);
                return lowerToken;
            }
            else {
                if (Parser.checkExist(lowerToken))
                    return lowerToken;
                else {
                    return lowerToken;
                }
            }
        }
    }
}
