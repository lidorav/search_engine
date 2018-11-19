package Model.Parse;

/**
 * Class that represent only text tokens
 */
public class Text {

    /**
     *  Converting a given token to term by uppercase or lowercase by defined conditions
     * @param index the index in the token list
     * @param token the text token to be converted
     * @return
     */
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
            //check if uppercase cased exist in the dictionary, if so replace to lowercase in dictionary and return it
            if(Parser.checkExist(upperToken)){
                Parser.replaceTerm(upperToken,lowerToken);
                return lowerToken;
            }
            //check if lowered cased exist in the dictionary, if so return lowercased
            else {
                if (Parser.checkExist(lowerToken))
                    return lowerToken;
                //if none if the cases exist = dictionary doesn't contain this word return lowercase
                else {
                    return lowerToken;
                }
            }
        }
    }
}
