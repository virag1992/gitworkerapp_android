package app.glintcarwash.com.glintworkerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {
    Button btnAdd;
    ListView listView2;
    TextView txtDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedule_fragment,container,false);
        btnAdd = (Button) v.findViewById(R.id.btnAdd);
        listView2 = (ListView) v.findViewById(R.id.listView2);
        ListViewCustomAdapter adapter = new ListViewCustomAdapter(getActivity());
        listView2.setAdapter(adapter);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        String my_date = format.format(c.getTime());
        txtDate.setText(my_date);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(),TimeDialog.class);
                startActivity(start);
            }
        });
        return v;
    }



    public class ListViewCustomAdapter extends BaseAdapter {
        public Activity context;
        LayoutInflater inflater;

        public ListViewCustomAdapter(Activity context)

        {
            super();
            this.context = context;
            this.inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 15;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        public class Holder {
            TextView title1;
            ImageView imgDelete,imgEdit;
            RelativeLayout rlMain;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub

            Holder hv;

            if (arg1 == null) {
                hv = new Holder();
                arg1 = inflater.inflate(R.layout.schedule_list_item, null);
                hv.title1 = (TextView) arg1.findViewById(R.id.txtTime);
                hv.rlMain = (RelativeLayout) arg1.findViewById(R.id.rlMain);
                hv.imgDelete = (ImageView) arg1.findViewById(R.id.imgDelete);
                hv.imgEdit = (ImageView) arg1.findViewById(R.id.imgEdit);
                arg1.setTag(hv);
            } else {
                hv = (Holder) arg1.getTag();
            }

            hv.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return arg1;
        }

    }
}
