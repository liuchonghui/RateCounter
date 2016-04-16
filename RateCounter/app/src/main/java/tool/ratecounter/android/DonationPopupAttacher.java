package tool.ratecounter.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.sinagame.windowattacher.FooterWindowAttacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 弹窗
 *
 * @author liu_chonghui
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("deprecation")
public class DonationPopupAttacher extends FooterWindowAttacher implements
        View.OnClickListener {

    public DonationPopupAttacher(Activity attachedActivity) {
        this(attachedActivity, R.layout.donation_popupattacher);
    }

    private DonationPopupAttacher(Activity attachedActivity, int layoutResId) {
        super(attachedActivity, R.layout.donation_popupattacher,
                R.id.popup_animation_layout);
    }

    TextView benjin;
    ListView listView;
    NewsListAdapter mAdapter;

    @Override
    public void findViewByContentView(View contentView) {
        benjin = (TextView) contentView.findViewById(R.id.benjin);
        listView = (ListView) contentView.findViewById(R.id.list);
        mAdapter = new NewsListAdapter();
    }

    @Override
    public void adjustContentView(View contentView) {
        super.adjustContentView(contentView);
        if (this.markState) {
            benjin.setText("当年本金(累加)");
        } else {
            benjin.setText("当年本金");
        }

        mAdapter.setData(list);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    class RateResult {
        String year;
        Money benjin;
        Money zongjine;
    }

    List<RateResult> list = new ArrayList<RateResult>();

    protected Money getInput(String input) {
        BigDecimal bd = new BigDecimal(input);
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        Money money = new Money();
        money.setValue(bd.toString());
        return money;
    }

    boolean markState = false;

    public void setData(String yuan, String rate, String years, boolean markState, int addYear) {
        this.markState = markState;
        Money m = getInput(yuan);
        Money leijia = getInput(yuan);
        Money moneyTemp = getInput(yuan);
        double money = m.getDouble();
        m = getInput(rate);
        double rat = m.getDouble();
        m = getInput(years);
        int year = Integer.valueOf(m.getInteger());

        list.clear();
        Money moneyTempAll;
        for (int i = 0; i < year; i++) {
            RateResult rr = new RateResult();
            rr.year = "第" + (i + 1) + "年";
            rr.benjin = getInput(String.valueOf(moneyTemp.getDouble()));
            if (this.markState && i > 0 && i< addYear) {
                rr.benjin = getInput(String.valueOf(leijia.getDouble() + moneyTemp.getDouble()));
            }
            double all = rr.benjin.getDouble() * (1 + rat);
            moneyTempAll = getInput(String.valueOf(all));
            rr.zongjine = getInput(String.valueOf(moneyTempAll.getDouble()));
            moneyTemp = moneyTempAll;

            list.add(rr);
        }
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
    }

    class NewsListAdapter extends BaseAdapter {
        List<RateResult> datalist;

        public NewsListAdapter() {
        }

        public void setData(List<RateResult> list) {
            this.datalist = list;
        }

        @Override
        public int getCount() {
            if (datalist != null) {
                return datalist.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        protected int getItemLayout() {
            return R.layout.question_list_item;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RateResult model = datalist.get(position);

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(
                        getItemLayout(), null);
                holder.year = (TextView) convertView.findViewById(R.id.years);
                holder.benjin = (TextView) convertView.findViewById(R.id.benjin);
                holder.zongjine = (TextView) convertView.findViewById(R.id.zongjine);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.year.setText(model.year);
            holder.benjin.setText(model.benjin.getValue());
            holder.zongjine.setText(model.zongjine.getValue());
            return convertView;
        }

        class ViewHolder {
            TextView year;
            TextView benjin;
            TextView zongjine;
        }
    }
}
