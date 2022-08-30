package com.ssu.takecare.dialog;

import static android.content.Context.MODE_PRIVATE;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.ssu.takecare.R;
import com.ssu.takecare.assist.medicine.ListViewMedicineAdapter;

public class MedicineDialog {

    private Context context;
    private Dialog dialog;
    private String numMedicine;
    private ListView mListview;
    private ListViewMedicineAdapter mAdapter_medicine;

    public MedicineDialog(Context context){
        this.context = context;
        setDialog();
        findViews();
    }

    public void showDialog(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    private void setDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_medicine);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);

        mListview = dialog.findViewById(R.id.listview_medicine);
    }

    private void findViews() {
        ImageButton btn_mb = dialog.findViewById(R.id.btn_medicine_back);
        Button btn_mp = dialog.findViewById(R.id.btn_medicine_plus);

        btn_mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mAdapter_medicine = new ListViewMedicineAdapter(context);
        mListview.setAdapter(mAdapter_medicine);

        btn_mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getSharedPreferences("MedicineInfo1", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
                    numMedicine = "1";
                else if (context.getSharedPreferences("MedicineInfo2", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
                    numMedicine = "2";
                else if (context.getSharedPreferences("MedicineInfo3", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
                    numMedicine = "3";
                else
                    numMedicine = "FULL";

                if (!numMedicine.equals("FULL")) {
                    MedicineAddDialog mDialog = new MedicineAddDialog(context, numMedicine, mAdapter_medicine);
                    mDialog.showDialog();
                }
                else {
                    Toast.makeText(context, "저장할 수 있는 약의 개수는 최대 3개입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
