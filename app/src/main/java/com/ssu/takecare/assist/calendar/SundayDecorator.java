package com.ssu.takecare.assist.calendar;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Calendar;

public class SundayDecorator implements DayViewDecorator {
    private final Calendar calendar= Calendar.getInstance();

    public SundayDecorator(){

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK); //현재 무슨 요일인지 반환
        return weekDay==Calendar.SUNDAY; //비교
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
