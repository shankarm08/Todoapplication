package com.wedddingapp.shankar.todoappy;

/**
 * Created by shan on 12/20/2017.
 */

public class SEntry implements Comparable<SEntry>
{

    public static final String TABLE = "SEntry";
    public static final String KEY_ID = "id";       //fields of the database
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";


    public int entry_ID;
    public String title;
    public String description;
    public String dueDate;
    public int status;



    public int compareTo(SEntry o)
    {
        //Spliting the Strings into String arrays.
        String[] currentObj = this.dueDate.split("/");
        String[] passedObj = o.dueDate.split("/");

        //we are Seperating  day,month and year from String Array.
        int currentDay=Integer.parseInt(currentObj[0]);
        int currentMonth=Integer.parseInt(currentObj[1]);
        int currentYear=Integer.parseInt(currentObj[2]);

        //we are Seperating the day,month and year from String Array.
        int passedDay=Integer.parseInt(passedObj[0]);
        int passedMonth=Integer.parseInt(passedObj[1]);
        int passedYear=Integer.parseInt(passedObj[2]);

        //Comparing parameters and returning values according to it.
        if(currentYear < passedYear)
            return -1;
        else if(currentYear > passedYear)
            return 1;
        else if(currentYear == passedYear && currentMonth < passedMonth)
            return -1;
        else if(currentYear == passedYear && currentMonth > passedMonth)
            return 1;
        else if(currentYear == passedYear && currentMonth == passedMonth && currentDay < passedDay)
            return -1;
        else if(currentYear == passedYear && currentMonth == passedMonth && currentDay > passedDay)
            return 1;

        //return 0 if both are same.
        return 0;
    }
}




