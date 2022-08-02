package com.ssu.takecare.AssistClass.Share;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssu.takecare.AssistClass.Graph.ShareGraph;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.CalendarActivity;
import com.ssu.takecare.UI.PrescriptionActivity;
import com.ssu.takecare.UI.ReportActivity;

import java.util.HashMap;
import java.util.List;

public class ShareGridAdapter extends RecyclerView.Adapter<ShareGridAdapter.ViewHolder>{

    Context mContext;
    List<String> Match_UserName_list;
    List<Integer> Match_UserId_list;
    HashMap<Integer, String> ID_NAME = new HashMap<Integer, String>();
    Intent intent;

    public ShareGridAdapter(List<String> name_list, List<Integer> id_list, Context context) {
        this.Match_UserName_list = name_list;
        this.Match_UserId_list = id_list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageButton btn1,btn2,btn3,btn4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            for (int i = 0; i < Match_UserId_list.size(); i++)
                ID_NAME.put(Match_UserId_list.get(i), Match_UserName_list.get(i));

            name=itemView.findViewById(R.id.share_list_name);
            btn1=itemView.findViewById(R.id.share_list_graph);
            btn2=itemView.findViewById(R.id.share_list_calendar);
            btn3=itemView.findViewById(R.id.share_list_prescription);
            btn4=itemView.findViewById(R.id.share_list_report);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int match_userid=Search_UserId(name.getText().toString());
                    if(match_userid!=-1){
                        intent=new Intent(view.getContext(), ShareGraph.class);
                        intent.putExtra("USER_ID", match_userid);
                        mContext.startActivity(intent);
                    }
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int match_userid=Search_UserId(name.getText().toString());
                    if(match_userid!=-1){
                        intent=new Intent(view.getContext(), CalendarActivity.class);
                        intent.putExtra("USER_NAME",name.getText().toString());
                        intent.putExtra("USER_ID", match_userid);
                        intent.putExtra("ID_NAME", ID_NAME);
                        mContext.startActivity(intent);
                    }
                }
            });

            btn3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int match_userid=Search_UserId(name.getText().toString());
                    intent=new Intent(view.getContext(), PrescriptionActivity.class);
                    intent.putExtra("USER_NAME",name.getText().toString());
                    intent.putExtra("USER_ID", match_userid);
                    mContext.startActivity(intent);
                }
            });

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int match_userid=Search_UserId(name.getText().toString());
                    if(match_userid!=-1){
                        intent=new Intent(view.getContext(), ReportActivity.class);
                        intent.putExtra("USER_ID", match_userid);
                        intent.putExtra("ID_NAME", ID_NAME);
                        mContext.startActivity(intent);
                    }
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
        String name=Match_UserName_list.get(position);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return Match_UserName_list.size();
    }

    // name에 대응되는 id값을 찾아준다.
    public int Search_UserId(String Match_Name){
        int number = -1;
        for (int i = 0; i < Match_UserName_list.size(); i++){
            if (Match_UserName_list.get(i).equals(Match_Name)){
                number = Match_UserId_list.get(i);
                break;
            }
        }
        return number;
    }
}
