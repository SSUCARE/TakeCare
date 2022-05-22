package com.ssu.takecare.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.CalendarActivity;
import com.ssu.takecare.UI.PresciptionActivity;
import com.ssu.takecare.UI.ReportActivity;
import com.ssu.takecare.UI.ShareGraph;

import java.util.List;

public class ShareGridAdapter extends RecyclerView.Adapter<ShareGridAdapter.ViewHolder>{

    Context mContext;
    List<String> list;

    public ShareGridAdapter(List<String>list, Context context) {
        this.mContext = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageButton btn1,btn2,btn3,btn4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.share_list_name);
            btn1=itemView.findViewById(R.id.share_list_graph);
            btn2=itemView.findViewById(R.id.share_list_calendar);
            btn3=itemView.findViewById(R.id.share_list_prescription);
            btn4=itemView.findViewById(R.id.share_list_report);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(view.getContext(), ShareGraph.class));
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(view.getContext(), CalendarActivity.class));
                }
            });

            btn3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(view.getContext(), PresciptionActivity.class));
                }
            });

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(view.getContext(), ReportActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.share_list_container,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name=list.get(position);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
