package com.designproject.takecare.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.designproject.takecare.R;
import java.util.List;

public class ShareGridAdapter extends RecyclerView.Adapter<ShareGridAdapter.ViewHolder>{

    List<String> list;

    public ShareGridAdapter(List<String>list) {
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
