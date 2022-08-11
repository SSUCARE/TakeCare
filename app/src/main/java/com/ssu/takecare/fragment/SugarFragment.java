package com.ssu.takecare.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ssu.takecare.assist.graph.SugarLevels_Graph;
import com.ssu.takecare.R;
import java.util.ArrayList;
import java.util.List;

public class SugarFragment extends Fragment {

    ArrayList<Entry>data=new ArrayList<>();

    /* 혈당 데이터: 첫번째는 날짜와 관련된 값, 두번째에는 혈당 값을 넣는다. */
    public SugarFragment(List<SugarLevels_Graph> sugarlevels_list, List<Integer> sugarlevels_list_date){
        for(int i=0; i<sugarlevels_list.size(); i++){
            int sugar_size = sugarlevels_list.get(i).getSugarLevels_list().size();
            float divide=(1/(float)sugar_size);
            for(int j=0; j<sugar_size; j++){
                data.add(new Entry((float)sugarlevels_list_date.get(i)+(divide*j),sugarlevels_list.get(i).getSugarLevels_list().get(j)));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sugar_linechart_graph, container, false);
        make_graph(view.findViewById(R.id.sugar_linechart));

        return view;
    }

    void make_graph(LineChart lineChart){
        lineChart.invalidate();
        lineChart.clear();

        //LineDataSet에 데이터를 넣어서 직선 구현하기
        LineDataSet lineDataSet = new LineDataSet(data, "혈당");
        //선 굵기
        lineDataSet.setLineWidth(1.5f);
        //Value는 Y값이고, value의 크기를 조정한다. 0으로 할 경우 ValueText를 띄우지 않는다.
        lineDataSet.setValueTextSize(12);
        //선 색깔
        lineDataSet.setColor(ContextCompat.getColor(getActivity(),R.color.sugar_chart_yellow));
        //동그라미 외부 색깔
        lineDataSet.setCircleColor(ContextCompat.getColor(getActivity(),R.color.sugar_chart_yellow));
        //동그라미 내부 색깔
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(getActivity(),R.color.white));
        //동그라미의 크기를 조정한다.
        lineDataSet.setCircleRadius(4f);

        //lineDataSet의 밑의 부분 채우기
        Drawable drawable=ContextCompat.getDrawable(getActivity(),R.drawable.gradient_bg);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setDrawFilled(true);

        /*LineData 설정하기*/
        LineData lineData=new LineData();
        lineData.addDataSet(lineDataSet);


        //X xAis=lineChart.getXAxis();
        XAxis xAis=lineChart.getXAxis();

        //x축 라벨의 최대 개수
        xAis.resetAxisMinimum();
        xAis.resetAxisMinimum();

        //X축 라벨의 최대 개수(25->31로 변경)
        xAis.setAxisMaxLabels(31);
        //X축에서 보여줄 라벨의 개수
        xAis.setLabelCount(31,true);
        // xAis.mAxisRange=1.0f;
        lineChart.setVisibleXRange(31,31);
        //x축 라벨의 최소값
        xAis.setAxisMinimum(1.0f);
        //x축 라벨의 최대값
        xAis.setAxisMaximum(31.0f);
        //x축 라벨의 위치
        xAis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //x축 text size크기
        xAis.setTextSize(14);

        //x축 text color(R.color.black로 바로 주면 실행x)
        xAis.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        xAis.setDrawGridLines(true);


        YAxis yAxisLeft=lineChart.getAxisLeft();
        //왼쪽 y축 라벨 최소값
        yAxisLeft.setAxisMinimum(60.0f);
        //왼쪽 y축 라벨 최대값
        yAxisLeft.setAxisMaximum(210.0f);
        //왼쪽 y축 라벨 개수(5마다 표시하기 위해서 (150-55)/5+1을 수행)
        yAxisLeft.setLabelCount(6,true);
        //왼쪽 y축 text크기
        yAxisLeft.setTextSize(14);
        //왼쪽 y축 text color
        yAxisLeft.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        //점선으로 표시. 첫번째 인자가 점선의 길이, 두번째 인자가 점선끼리의 간격을 의미
        yAxisLeft.enableGridDashedLine(20,12,0);
        //set*Offset은 그래프의 margin과 같은 기능이다.
        //yAxisLeft.setXOffset(20);
        //yAxisLeft.setYOffset(20);
        yAxisLeft.setDrawGridLines(false);


        //오른쪽 y축 대한 설정 class 받기
        YAxis yAxisRight=lineChart.getAxisRight();
        //오른쪽 y축 라벨을 제거
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        //오른쪽 y축 그리드라인(선)을 제거
        yAxisRight.setDrawGridLines(false);


        //위에서 설장한 LineChart에 LineData넣기
        lineChart.setData(lineData);
        // 해당 차트의 드래그 여부를 결정하는 함수
        lineChart.setDragEnabled(false);
        //해당 차트의 줌 확대 여부를 결정하는 함수
        lineChart.setScaleEnabled(false);
        //linechart오른쪽 하단에 Descprition Label이라는 글자 지우기
        lineChart.setDescription(null);
        //그래프의 margin과 같은 개념이다. bottom에 15를 줌으로써 그래프와 legend간에 간격이 생긴다.
        lineChart.setExtraOffsets(5,5,5,15);
        lineChart.setBorderWidth(1500);

        /*Legend 설정(Legend는 LineChart 하단에 있는 직선의 구분을 위한 text*/
        Legend i=lineChart.getLegend();
        i.setEnabled(false);
    }
}
