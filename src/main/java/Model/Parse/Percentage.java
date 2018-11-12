package Model.Parse;

public class Percentage extends ANumbers {

    public String toPrecent(int index, String token){
        if(token.contains("%"))
            return token;

        String nextToken = Parser.getTokenFromList(index+1);
        if(nextToken.equals("precent")) {
            token = token+'%';
            Parser.index++;
            return token;
        }

        if(nextToken.equals("precentage")) {
            token = token+'%';
            Parser.index++;
            return token;
        }

        return "";

    }


}
