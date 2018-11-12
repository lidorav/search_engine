package Model.Parse;

public class Percentage extends ANumbers {

    public static String parsePrecent(int index, String token){
        if(token.contains("%"))
            return token;

        String nextToken = Parser.getTokenFromList(index+1).toLowerCase();
        if(nextToken.equals("percent")) {
            token = token+'%';
            Parser.index++;
            return token;
        }

        if(nextToken.equals("percentage")) {
            token = token+'%';
            Parser.index++;
            return token;
        }

        return "";
    }
}
