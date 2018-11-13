package Model.Parse;

import java.time.Month;

public class Date {

    private static final String[]  dateArr = {"january" ,"jan" ,"february" ,"feb" ,"march" ,"mar" ,"april" ,"apr" ,
            "may" ,"june" ,"jun" ,"july" ,"jul" ,"august" ,"aug" ,"september" ,"sep" ,
            "october" ,"oct" ,"november" ,"nov" ,"december" ,"dec"};

    public static String dateParse (int index , String str) {
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

    private static boolean isMonth(String targetValue) {
        targetValue=targetValue.toLowerCase();
        if(targetValue.length() == 3 && !targetValue.contains("may"))
            targetValue = shortMonth(targetValue);

        for(String s: dateArr){
            if(s.equals(targetValue))
                return true;
        }
        return false;
    }

    private static String shortMonth(String targetValue){
        for (int i=0; i <dateArr.length;i++){
             if(dateArr[i].contains(targetValue) && targetValue.length()>3){
                 return dateArr[i];
             }
        }
        return "";
    }

    private static boolean isYear(String targetValue) {
        if(targetValue.length()==4) {
            return true;
        }
        return false;
    }
}