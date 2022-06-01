package com.ssu.takecare.Fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.ssu.takecare.R;
import java.util.ArrayList;
import java.util.List;

public class WeightFragment extends Fragment {

    /* Entry는 (x축,y축)형태이며 MPAndroidChart라이브러리에서 지원하는 자료구조이다. */
    ArrayList<Entry> weight_list = new ArrayList<>(); //몸무게, (날짜, 몸무게)

    public WeightFragment(List<Integer> weight_list, List<Integer> weight_list_date){
        for(int i=0; i<weight_list.size(); i++)
            this.weight_list.add(new Entry(weight_list_date.get(i), weight_list.get(i)));

        for(int i=0; i<this.weight_list.size(); i++)
            Log.d("디버그,WeightFragment->weights:","x축:"+this.weight_list.get(i).getX()+" y축:"+this.weight_list.get(i).getY());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.linechart_graph, container, false);
        make_graph(view.findViewById(R.id.linechart), weight_list);

        return view;
    }

    /* LineChart 만들기 */
    public void make_graph(LineChart lineChart,ArrayList<Entry> weight_list){
        // 차트 초기화 작업
        lineChart.invalidate();
        lineChart.clear();

        /* LineDataSet 설정하기 */
        // LineDataSet에 데이터를 넣어서 직선 구현하기
        LineDataSet lineDataSet = new LineDataSet(weight_list, "몸무게");

        // 선 굵기
        lineDataSet.setLineWidth(2);

        // 선 색깔
        lineDataSet.setColor(ContextCompat.getColor(getActivity(),R.color.weight_chart));

        // 동그라미 그리지 않기기
        lineDataSet.setDrawCircles(false);

        /* LineData에 LineDataSet을 통해 만든 직선들 포함시키기 */
        LineData lineData=new LineData();
        lineData.addDataSet(lineDataSet);
        lineData.setValueTextSize(12);



        /* Line Chart 구성하기 */
        XAxis xAis=lineChart.getXAxis();

        //x축 라벨의 최대 개수
        xAis.setLabelCount(16,true);

        //x축 라벨의 최소값
        xAis.setAxisMinimum(1.0f);

        //x축 라벨의 최대값
        xAis.setAxisMaximum(31.0f);

        //x축 라벨의 위치
        xAis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //x축 text size크기
        xAis.setTextSize(11);

        //x축 text color(R.color.black로 바로 주면 실행x)
        xAis.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

        //x축 gridline제거하기
        xAis.setDrawGridLines(false);

        //Y축라벨은 왼쪽 오른쪽 두개가 있다. 그중 왼쪽 설정 class받기
        YAxis yAxisLeft=lineChart.getAxisLeft();

        /*왼쪽 y축값 표현 방법: list.get(0)의 값이 중앙에 오도록 그래프 구현*/
        //왼쪽 y축 라벨 최소값(해당 달의 처음 입력된 값-10을 최소값으로 두기, 단 짝수로)
        int YLabelMin=60;
        if(weight_list.size()!=0){
            YLabelMin=((int)weight_list.get(0).getY())-10;
            if(weight_list.get(0).getY()%2!=0)
                YLabelMin=+1;
        }

        yAxisLeft.setAxisMinimum(YLabelMin);

        //왼쪽 y축 라벨 최대값(최소값과 20만큼 차이가 나도록하기)
        yAxisLeft.setAxisMaximum(YLabelMin+20);

        //왼쪽 y축 라벨 개수(5마다 표시하기 위해서 (150-55)/5+1을 수행)
        yAxisLeft.setLabelCount(11,true);

        //왼쪽 y축 text크기
        yAxisLeft.setTextSize(14);

        //왼쪽 y축 text color
        yAxisLeft.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        //왼쪽 y축 라인 제거
        yAxisLeft.setDrawAxisLine(false);

        //오른쪽 y축 대한 설정 class 받기
        YAxis yAxisRight=lineChart.getAxisRight();
        //오른쪽 y축 라벨을 제거
        yAxisRight.setDrawLabels(false);

        //오른쪽 y축 그리드라인(선)을 제거
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);


        //위에서 설장한 LineChart에 LineData넣기
        lineChart.setData(lineData);
        //linechart오른쪽 하단에 Descprition Label이라는 글자 지우기
        lineChart.setDescription(null);

        /*Legend 설정(Legend는 LineChart하단에 있는 직선에 대한 설명*/
        Legend i=lineChart.getLegend();
        i.setEnabled(false);
    }
}
