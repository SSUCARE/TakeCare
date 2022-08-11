package com.ssu.takecare.assist.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.ssu.takecare.R;
import java.util.HashMap;

public class ReportDecorator implements DayViewDecorator {

    public HashMap<Integer, Calendar_Day> hash_map;
    int cal_year;
    int cal_month;

    public ReportDecorator(HashMap<Integer,Calendar_Day> hash_map, int now_year, int now_month){
        this.hash_map=hash_map;
        this.cal_year=now_year;
        this.cal_month=now_month;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        //Calendar의 Day가 hashmap에 있는지 확인하면 되겠네
        //day.getDay()로 얻을 수 있겠네
        String s_cal = day.toString();
        String[] str = s_cal.split("-");
        Calendar_Day cal = hash_map.get(Integer.parseInt(str[2].substring(0, str[2].length() - 1)));

        if (cal != null) {
            if (Integer.parseInt(str[0].substring(12, str[0].length())) == cal_year && (Integer.parseInt(str[1])+1) == cal_month) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(8, R.color.calendar_report));
    }
}
