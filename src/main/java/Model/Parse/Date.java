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
            if(isShortMonth(nextToken))
                nextToken=toLongMonth(nextToken);
            Parser.index++;
            return Month.valueOf(nextToken.toUpperCase()).getValue() + "-" + str;
        }
        //Month DD pattern
        if(isMonth(str) && !isYear(nextToken)){
            if(isShortMonth(str))
                str=toLongMonth(str);
            Parser.index++;
            return Month.valueOf(str.toUpperCase()).getValue() + "-" + nextToken;
        }
        //YYYY MM pattern
        if(isMonth(str) && isYear((nextToken))){
            if(isShortMonth(str))
                str=toLongMonth(str);
            Parser.index++;
            return Month.valueOf(str.toUpperCase()).getValue() + "-" + nextToken;
        }
        return "";
    }

    private static boolean isMonth(String targetValue) {
        targetValue=targetValue.toLowerCase();
        for(String s: dateArr){
            if(s.equals(targetValue))
            return true;
        }
        return false;
    }

    private static boolean isShortMonth(String token) {
        token = token.toLowerCase();
        if (token.length() == 3 && (!token.equals("may")) || (!token.equals("mar")))
            return true;

        return false;
    }

    private static String toLongMonth(String targetValue){
        targetValue=targetValue.toLowerCase();
        for (int i=0; i <dateArr.length;i++){
             if(dateArr[i].contains(targetValue) && dateArr[i].length()>3){
                 return dateArr[i];
             }
        }
        return "";
    }

    private static boolean isYear(String targetValue) {
        if (targetValue.length() == 4 && allDigits(targetValue)) {
            return true;
        }
        return false;
    }
    private static boolean allDigits(String s) {
        return s.replaceAll("\\d", "").equals("");
    }
}