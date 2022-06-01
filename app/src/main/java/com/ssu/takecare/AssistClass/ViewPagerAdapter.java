package com.ssu.takecare.AssistClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.ssu.takecare.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;

    public ViewPagerAdapter(Context context){
        this.context=context;
    }

    //page를 생성할때 사용하는 메소드
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.pageview_cardnews,null);
        //TextView tv=(TextView)view.findViewById(R.id.pageview_tv);
        //tv.setText(Integer.toString(position+1));
        ImageView img1,img2,img3,img4;
        switch (position){
            case 0:
                img1=(ImageView)view.findViewById(R.id.pageview_img1);
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));
                break;
            case 1:
                img1=(ImageView)view.findViewById(R.id.pageview_img1);
                img2=(ImageView)view.findViewById(R.id.pageview_img2);
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img2.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));
                break;
            case 2:
                break;
            case 3:
                break;
        }
        //뷰페이저에 해당 뷰 추가
        container.addView(view);
        return view;
    }

    //사용가능한 뷰의 갯수를 리턴한다. Adapter가 관리하는 데이터 리스트의 총 개수
    @Override
    public int getCount() {
        return 4;
    }

    //Position에 해당되는 page를 삭제한다.
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    //page가 key의 연관성을 체크
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(View)object);
    }
}
