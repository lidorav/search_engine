package Model.Parse;

import Model.Term;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Tokens {

    private List<String> tokenList;
    private HashMap<String, Term> terms = new HashMap<>();

    public String getTokenFromList(int index){
        return tokenList.get(index);
    }

    public void saveTerm(String termName, Term term){
        terms.put(termName, term);
    }

    public Collection<Term> getTerms(){
        return terms.values();
    }



}
