package ifm3b.miniproject11.skyhotels.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.StringTokenizer;

public class dates {

    private CalendarDay startDate;
    private CalendarDay endDate;

    public dates(String start, String end){
        startDate = new CalendarDay(Integer.parseInt(start.split("\\/")[2]),
                                    Integer.parseInt(start.split("\\/")[1])-1,
                                    Integer.parseInt(start.split("\\/")[0]));
        endDate = new CalendarDay(Integer.parseInt(end.split("\\/")[2]),
                                  Integer.parseInt(end.split("\\/")[1])-1,
                                  Integer.parseInt(end.split("\\/")[0]));
    }

    public CalendarDay getEndDate(){ return endDate;}
    public CalendarDay getStartDate(){ return startDate;}

}
