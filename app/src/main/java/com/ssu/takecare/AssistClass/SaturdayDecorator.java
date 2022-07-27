package com.ssu.takecare.AssistClass;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Calendar;

public class SaturdayDecorator implements DayViewDecorator {
    private final Calendar calendar= Calendar.getInstance();

    public SaturdayDecorator(){

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK); //현재 무슨 요일인지 반환
        return weekDay==Calendar.SATURDAY; //비교
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
    }
}
