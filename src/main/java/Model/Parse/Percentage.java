package Model.Parse;

public class Percentage extends ANumbers {

    private final String precent ="%";


    public String toPrecent(int index, String token){
        if(token.contains("%"))
            return token;

        String nexToken = Parser.getTokenFromList(index+1);
        if(nexToken.contains("precent")) {
            token = token+precent;
            Parser.index++;
        }

        if(nexToken.contains("precentage")) {
            token = token+precent;
            Parser.index++;
        }
        return token;

    }


}
