package Model.Parse;

public class Quotation {

    public static String parseQuotation(int index, String token){
        String nextToken = "", res="";
        int i=1;
        if(token.charAt(0)=='"') {
            if (token.charAt(token.length() - 1) == '"') {
                res = token.replace("\"", "");
                return res.toLowerCase();
            } else {
                res = token.substring(1);
                Parser.replaceToken(index, res);
                while (!nextToken.contains("\"")) {
                    if(i>=10)
                        return "";
                    nextToken = Parser.getTokenFromList(index + i);
                    res = res + " " + nextToken;
                    i++;
                }
                nextToken = nextToken.substring(0, nextToken.length() - 1);
                Parser.replaceToken(index + i - 1, nextToken);
                Parser.index--;
                res = (res.substring(0, res.length() - 1)).toLowerCase();
            }
        }
        return res;
    }
}
