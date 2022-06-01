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
import com.ssu.takecare.AssistClass.MyMarkerView;
import com.ssu.takecare.R;
import java.util.ArrayList;
import java.util.List;

public class PressureFragment extends Fragment {

    /* Entry는 (x축,y축)형태이며 MPAndroidChart 라이브러리에서 지원하는 자료구조이다. */
    ArrayList<Entry> systolic_blood_pressure_list = new ArrayList<>(); //수축 혈압(최고혈압), (날짜, 최고혈압)
    ArrayList<Entry> diastolic_blood_pressure_list= new ArrayList<>(); //이완 혈압(최저 혈압),(날짜, 최저혈압)

    /* 그래프에 뿌릴 최고 혈압과 최저 혈압 데이터를 생성자에서 만들어준다.. */
    /* x값으로는 날짜를, y값으로는 혈압을 준다. */
    public PressureFragment(List<Integer> systolic_list, List<Integer> systolic_list_date, List<Integer> diastolic_list, List<Integer> diastolic_list_date) {
        for(int i=0; i<systolic_list.size(); i++)
            systolic_blood_pressure_list.add(new Entry(systolic_list_date.get(i), systolic_list.get(i)));

        for(int i=0; i<diastolic_list.size(); i++)
            diastolic_blood_pressure_list.add(new Entry(diastolic_list_date.get(i),diastolic_list.get(i)));

        for(int i=0; i<systolic_blood_pressure_list.size(); i++){
            Log.d("디버그, PressureFragment->sys","x축:"+systolic_blood_pressure_list.get(i).getX()+" y축:"+systolic_blood_pressure_list.get(i).getY());
            Log.d("디버그, PressureFragment->dys","x축:"+diastolic_blood_pressure_list.get(i).getX()+" y축:"+diastolic_blood_pressure_list.get(i).getY());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.linechart_graph, container, false);
        make_graph(view.findViewById(R.id.linechart), systolic_blood_pressure_list, diastolic_blood_pressure_list);
        return view;
    }

    /* LineChart 만들기 */
    public void make_graph(LineChart lineChart,ArrayList<Entry> systolic_blood_pressure_list,ArrayList<Entry> diastolic_blood_pressure_list){
        // 차트 초기화 작업
        lineChart.invalidate();
        lineChart.clear();

        /* LineDataSet 설정하기 */
        // LineDataSet에 데이터를 넣어서 직선 구현하기 (최저혈압)
        LineDataSet lineDataSet = new LineDataSet(diastolic_blood_pressure_list, "최저혈압");

        // 선 굵기
        lineDataSet.setLineWidth(3);

        // Value는 Y값이고, value의 크기를 조정한다. 0으로 할 경우 ValueText를 띄우지 않는다.
        lineDataSet.setValueTextSize(0f);

        // 선 색깔
        lineDataSet.setColor(ContextCompat.getColor(getActivity(), R.color.green));

        // 동그라미 외부 색깔
        lineDataSet.setCircleColor(ContextCompat.getColor(getActivity(),R.color.green));

        // 동그라미 내부 색깔
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(getActivity(),R.color.white));

        // 동그라미의 크기를 조정한다.
        lineDataSet.setCircleRadius(4f);


        // LineDataSet에 데이터를 넣어서 직선 구현하기 (최고혈압)
        LineDataSet lineDataSet2 = new LineDataSet(systolic_blood_pressure_list, "최고혈압");

        // 선 굵기
        lineDataSet2.setLineWidth(2);

        // Value는 Y값이고, value의 크기를 조정한다. 0으로 할 경우 ValueText를 띄우지 않는다.
        lineDataSet2.setValueTextSize(0f);

        // 선 색깔
        lineDataSet2.setColor(ContextCompat.getColor(getActivity(),R.color.red));

        // 동그라미 외부 색깔
        lineDataSet2.setCircleColor(ContextCompat.getColor(getActivity(),R.color.red));

        // 동그라미 내부 색깔
        lineDataSet2.setCircleHoleColor(ContextCompat.getColor(getActivity(),R.color.white));

        // 동그라미의 크기를 조정한다.
        lineDataSet2.setCircleRadius(3f);


        /* 혈압 그래프를 보면 70, 140은 점선이 아니라 실선이다. 실선도 그래프 형태로 구현한것 */
        ArrayList<Entry> line1=new ArrayList<>();
        line1.add(new Entry(1,70)); line1.add(new Entry(31, 70));
        ArrayList<Entry> line2=new ArrayList<>();
        line2.add(new Entry(1,140)); line2.add(new Entry(31, 140));
        LineDataSet lineDataSet3=new LineDataSet(line1,"라인1");
        lineDataSet3.setLineWidth(1);

        // Value는 Y값이고, value의 크기를 조정한다. 0으로 할 경우 ValueText를 띄우지 않는다.
        lineDataSet3.setValueTextSize(0f);

        // 선 색깔
        lineDataSet3.setColor(ContextCompat.getColor(getActivity(),R.color.black));

        // 동그라미의 크기를 조정한다.
        lineDataSet3.setCircleRadius(0f);
        lineDataSet3.setDrawCircleHole(false);
        lineDataSet3.setDrawCircles(false);


        LineDataSet lineDataSet4=new LineDataSet(line2,"라인2");
        lineDataSet4.setLineWidth(1);

        // Value는 Y값이고, value의 크기를 조정한다. 0으로 할 경우 ValueText를 띄우지 않는다.
        lineDataSet4.setValueTextSize(0f);

        // 선 색깔
        lineDataSet4.setColor(ContextCompat.getColor(getActivity(),R.color.black));

        // 동그라미의 크기를 조정한다.
        lineDataSet4.setCircleRadius(0f);
        lineDataSet4.setDrawCircleHole(false);
        lineDataSet4.setDrawCircles(false);


        /* LineData 설정하기 */
        LineData lineData=new LineData();
        lineData.addDataSet(lineDataSet3);
        lineData.addDataSet(lineDataSet4);
        lineData.addDataSet(lineDataSet);
        lineData.addDataSet(lineDataSet2);


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
        xAis.setTextSize(14);

        //x축 text color(R.color.black로 바로 주면 실행x)
        xAis.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

        //x축 grid라인 제거하기
        xAis.setDrawGridLines(false);


        //Y축라벨은 왼쪽 오른쪽 두개가 있다. 그중 왼쪽 설정 class받기
        YAxis yAxisLeft=lineChart.getAxisLeft();

        //왼쪽 y축 라벨 최소값
        yAxisLeft.setAxisMinimum(50.0f);

        //왼쪽 y축 라벨 최대값
        yAxisLeft.setAxisMaximum(150.0f);

        //왼쪽 y축 라벨 개수(5마다 표시하기 위해서 (150-55)/5+1을 수행)
        yAxisLeft.setLabelCount(11,true);

        //왼쪽 y축 text크기
        yAxisLeft.setTextSize(14);

        //왼쪽 y축 text color
        yAxisLeft.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

        //점선으로 표시. 첫번째 인자가 점선의 길이, 두번째 인자가 점선끼리의 간격을 의미
        yAxisLeft.enableGridDashedLine(20,12,0);


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

        //markerview추가하기
        MyMarkerView myMarkerView=new MyMarkerView(getActivity(), R.layout.markerview);
        lineChart.setMarker(myMarkerView);


        /*Legend 설정(Legend는 LineChart하단에 있는 직선에 대한 설명*/
        Legend i=lineChart.getLegend();

        //Legend를 보이지 않도록 설정한다.
        i.setEnabled(false);
    }
}
