package com.ssu.takecare.assist.graph;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.ssu.takecare.R;

public class MyMarkerView extends MarkerView {
    TextView markerview_text;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        markerview_text = findViewById(R.id.markerview_text);
    }

    /* LineChart가 클릭되었을 때 MarkerView를 띄우는 메소드로, 조건문의 else문을 사용한다고 생각하면 된다. */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce;
            ce = (CandleEntry) e;
        }
        else {
            markerview_text.setText(String.valueOf(e.getY()));
        }

        super.refreshContent(e, highlight);
    }

    /* Markerview가 나타나는 위치 */
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
