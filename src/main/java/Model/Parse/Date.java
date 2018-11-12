package Model.Parse;


import java.time.Month;

public class Date {

    private String[]  dateArr = {"January" ,"February" ,"March" ,"April","May","June","July","August" };





    public String datePars (int index , String str) {
        String nextToken = Parser.getTokenFromList(index+1);;

        if(str.chars().allMatch(Character::isDigit)){

        }

        Month.valueOf(str.toUpperCase()).getValue();
        return str;
    }


}