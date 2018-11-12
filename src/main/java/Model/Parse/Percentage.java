package Model.Parse;

public class Percentage extends ANumbers {

    private final String precent ="%";


    public String toPrecent(int index, String token){

        if(token.contains("%"))
            return token;
        
        if(token.contains("precent")) {
            token = token.replace(" percent", this.precent);
        }

        if(token.contains("precentage")) {
            token = token.replace(" precentage", this.precent);
        }
        return token;

    }


}
