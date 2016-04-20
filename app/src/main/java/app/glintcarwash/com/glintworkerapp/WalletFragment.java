package app.glintcarwash.com.glintworkerapp;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import global.ProgressWheel;
import model.OrderInfo;

/**
 * Created by ACER on 20-04-2016.
 */
public class WalletFragment extends Fragment {
    Dialog DialogForTransaction, DialogForCard;
    ProgressWheel progressWheel;
    ListView lstTransactions;
    ArrayList<Integer> m_draw = new ArrayList<Integer>();
    Button btnWithdraw;
    int selectedcard = 5555;

    public static WalletFragment getInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.wallet_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        m_draw.add(Color.parseColor("#779ECB"));
        m_draw.add(Color.parseColor("#FFD1DC"));
        m_draw.add(Color.parseColor("#FFB347"));
        m_draw.add(Color.parseColor("#03C03C"));
        m_draw.add(Color.parseColor("#FDFD96"));
        lstTransactions = (ListView) view.findViewById(R.id.lstCards);
        btnWithdraw = (Button) view.findViewById(R.id.btnWithdraw);
        ArrayList<OrderInfo> m_temp = new ArrayList<OrderInfo>();
        for (int i = 0; i < 4; i++) {
            OrderInfo car = new OrderInfo();
            car.OrderTitle = "Car was job " + i + 1;
            car.OrderDate = "Feb 15, 2016";
            car.OrderAddress = "A - 607, Titenium city center, Anand nagar main road, Ahmedabad";
            if (i == 0) {
                car.OrderStatus = "In Progress";
            } else if (i == 1) {
                car.OrderStatus = "Cancel";
            } else if (i == 2) {
                car.OrderStatus = "Completed";
            } else if (i == 3) {
                car.OrderStatus = "Pending";
            }
            m_temp.add(car);
        }
        CustomAdapter adapter = new CustomAdapter(getActivity(), m_temp);
        lstTransactions.setAdapter(adapter);

        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showCardList() {
        DialogForCard = new Dialog(getActivity());
        DialogForCard.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogForCard.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        DialogForCard.getWindow().setLayout(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View v1 = getActivity().getLayoutInflater().inflate(R.layout.card_list_dialog,
                null);
        ListView lstCards = (ListView) v1.findViewById(R.id.lstCards);
        ArrayList<OrderInfo> m_temp = new ArrayList<OrderInfo>();
        for (int i = 0; i < 4; i++) {
            OrderInfo car = new OrderInfo();
            car.OrderTitle = "Car was job " + i + 1;
            car.OrderDate = "Feb 15, 2016";
            car.OrderAddress = "A - 607, Titenium city center, Anand nagar main road, Ahmedabad";
            if (i == 0) {
                car.OrderStatus = "In Progress";
            } else if (i == 1) {
                car.OrderStatus = "Cancel";
            } else if (i == 2) {
                car.OrderStatus = "Completed";
            } else if (i == 3) {
                car.OrderStatus = "Pending";
            }
            m_temp.add(car);
        }
        CustomCardAdapter adapter = new CustomCardAdapter(getActivity(), m_temp);
        lstCards.setAdapter(adapter);
        DialogForCard.setContentView(v1);
        DialogForCard.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        DialogForCard.setCancelable(false);
        DialogForCard.show();
    }

    public void showProgress() {
//    i = 0;
        if (DialogForTransaction == null) {
            DialogForTransaction = new Dialog(getActivity());

            DialogForTransaction.requestWindowFeature(Window.FEATURE_NO_TITLE);
            DialogForTransaction.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            DialogForTransaction.getWindow().setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            View v1 = getActivity().getLayoutInflater().inflate(R.layout.progress_dialog,
                    null);
            progressWheel = (ProgressWheel) v1
                    .findViewById(R.id.progress_wheel);
            final TextView txtMessage = (TextView) v1
                    .findViewById(R.id.txtMessage);
//        txtMessage.setText(m_str.get(getRandom()));
            progressWheel.setBarColor(Color.BLUE);
            progressWheel.setCallback(new ProgressWheel.ProgressCallback() {
                @Override
                public void onProgressUpdate(float progress) {
                    if (progress == -1.0) {
                        try {
                            progressWheel.setBarColor(m_draw.get(getRandom()));

//                        if (i % 3 == 0) {
//                            txtMessage.setText(m_str.get(getRandom()));
//                        }
//                        i++;
                            // Thread.sleep(2000);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            DialogForTransaction.setContentView(v1);
            DialogForTransaction.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            DialogForTransaction.setCancelable(false);
            DialogForTransaction.show();
        } else {
            DialogForTransaction.show();
        }
    }

    public int getRandom() {
        int min = 0;
        int max = 4;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }

    public void hideProgress() {
        if (DialogForTransaction != null) {
            DialogForTransaction.dismiss();
            DialogForTransaction = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        }
    }

    public class CustomAdapter extends BaseAdapter {

        //    private List<BookmarkObject> objects = new ArrayList<BookmarkObject>();
        private List<OrderInfo> objects = new ArrayList<OrderInfo>();

        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(Context context, List<OrderInfo> objects) {
            this.context = context;
            this.objects = objects;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public OrderInfo getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.wallat_list_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.txtTransactionType = (TextView) convertView.findViewById(R.id.txtTransactionType);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return convertView;
        }


        protected class ViewHolder {
            private TextView txtAmount, txtDate, txtTransactionType;
        }
    }

    public class CustomCardAdapter extends BaseAdapter {

        //    private List<BookmarkObject> objects = new ArrayList<BookmarkObject>();
        private List<OrderInfo> objects = new ArrayList<OrderInfo>();

        private Context context;
        private LayoutInflater layoutInflater;

        public CustomCardAdapter(Context context, List<OrderInfo> objects) {
            this.context = context;
            this.objects = objects;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public OrderInfo getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.card_list_item_dialog, null);
            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
//            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
//            viewHolder.txtTransactionType = (TextView) convertView.findViewById(R.id.txtTransactionType);
            viewHolder.rbCard = (RadioButton) convertView.findViewById(R.id.rbCard);
            convertView.setTag(viewHolder);
            if (selectedcard == position) {
                viewHolder.rbCard.setChecked(true);
            } else {
                viewHolder.rbCard.setChecked(false);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedcard = position;
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        protected class ViewHolder {
            RadioButton rbCard;
            private TextView txtAmount, txtDate, txtTransactionType;
        }
    }
}
