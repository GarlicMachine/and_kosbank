package com.teamproject.sample.kosbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.accountVO;
import com.teamproject.sample.kosbank.test.fmt;

import java.util.List;

public class Main_myAccountAdapter extends BaseAdapter {
    private List<accountVO> list;

    public Main_myAccountAdapter(List<accountVO> list){this.list = list;}

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_account_list_view, parent, false);

            TextView text_myAccount = convertView.findViewById(R.id.text_myAccount);
            TextView text_myAccountType = convertView.findViewById(R.id.text_myAccountType);
            TextView text_myBalance = convertView.findViewById(R.id.text_myBalance);
            holder.text_myAccount=text_myAccount;
            holder.text_myAccountType=text_myAccountType;
            holder.text_myBalance=text_myBalance;

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();//setTag()를 읽어서 재사용시 꺼내쓴다.
        }
        accountVO vo = list.get(position);
        holder.text_myAccount.setText(vo.getAccount());
        holder.text_myAccountType.setText(vo.getAccountType());
        holder.text_myBalance.setText(fmt.moneyFormatToWon(vo.getBalance()));
        return convertView;
    }

    static class ViewHolder{
        TextView text_myAccount;
        TextView text_myAccountType;
        TextView text_myBalance;
    }
}
