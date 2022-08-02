package com.ssu.takecare.AssistClass.CardNews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    // page를 생성할때 사용하는 메소드
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.pageview_cardnews,null);

        ImageView img0=(ImageView) view.findViewById(R.id.pageview_img0);
        ImageView img1=(ImageView) view.findViewById(R.id.pageview_img1);
        ImageView img2=(ImageView) view.findViewById(R.id.pageview_img2);
        ImageView img3=(ImageView) view.findViewById(R.id.pageview_img3);
        ImageView img4=(ImageView) view.findViewById(R.id.pageview_img4);

        switch (position){
            case 0:
                img0.setImageDrawable(context.getDrawable(R.drawable.card_news3));
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));
                img2.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img3.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img4.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));

                img0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://health.severance.healthcare/health/media/card.do?mode=view&articleNo=67235&article.offset=0&articleLimit=9&srSearchVal=%EB%AA%B8%EB%AC%B4%EA%B2%8C"));
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                img0.setImageDrawable(context.getDrawable(R.drawable.card_news1));
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img2.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));
                img3.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img4.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));

                img0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amc.seoul.kr/asan/healthstory/medicalcolumn/medicalColumnDetail.do?medicalColumnId=34055"));
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                img0.setImageDrawable(context.getDrawable(R.drawable.card_news2));
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img2.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img3.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));
                img4.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));

                img0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.snuh.org/health/tv/view.do?seq_no=222&pageIndex=1&searchKey=&searchWord=%EB%8B%B9%EB%87%A8%EB%B3%91"));
                        context.startActivity(intent);
                    }
                });
                break;
            case 3:
                img0.setImageDrawable(context.getDrawable(R.drawable.card_news4));
                img1.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img2.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img3.setImageDrawable(context.getDrawable(R.drawable.pageview_circle));
                img4.setImageDrawable(context.getDrawable(R.drawable.pageview_circle2));

                img0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.snuh.org/health/myDoctor/view.do?seq_no=10"));
                        context.startActivity(intent);
                    }
                });
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
