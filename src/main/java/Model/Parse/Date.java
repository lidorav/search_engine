package Model.Parse;


import java.lang.reflect.Array;
import java.time.Month;

public class Date {

    private static final String[]  dateArr = {"january" ,"jan" ,"february" ,"feb" ,"march" ,"mar" ,"april" ,"apr" ,
            "may" ,"june" ,"jun" ,"july" ,"jul" ,"august" ,"aug" ,"september" ,"sep" ,
            "october" ,"oct" ,"november" ,"nov" ,"december" ,"dec"};

    public String datePars (int index , String str) {
        String nextToken = Parser.getTokenFromList(index + 1);

        //DD Month pattern
        if (str.chars().allMatch(Character::isDigit) && isMonth(nextToken)) {
            Parser.index++;
            return Month.valueOf(nextToken.toUpperCase()).getValue() + "-" + str;
        }
        //Month DD pattern
        if(isMonth(str) && !isYear(nextToken)){
            Parser.index++;
            return Month.valueOf(str.toUpperCase()).getValue() + "-" + nextToken;
        }
        //YYYY MM pattern
        if(isMonth(str) && isYear((nextToken))){
            Parser.index++;
            return Month.valueOf(str.toUpperCase()).getValue() + "-" + nextToken;
        }


        return "";
    }

    public boolean isMonth(String targetValue) {
        targetValue=targetValue.toLowerCase();
        for(String s: dateArr){
            if(s.equals(targetValue))
                return true;
        }
        return false;
    }

    public boolean isYear(String targetValue) {
        if(targetValue.length()==4) {
            return true;
        }
        return false;
    }
}