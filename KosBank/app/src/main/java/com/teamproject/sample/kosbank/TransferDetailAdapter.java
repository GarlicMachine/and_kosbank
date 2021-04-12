package com.teamproject.sample.kosbank;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.TransferDetailVO;
import com.teamproject.sample.kosbank.test.fmt;

import java.util.List;

public class TransferDetailAdapter extends BaseAdapter {
    private List<TransferDetailVO> list;
    int img;

    public TransferDetailAdapter(List<TransferDetailVO> list){
        this.list = list;
        img=R.drawable.blueclock;
    }

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_detail_list_view, parent, false);
            TextView text_InOut_Day = convertView.findViewById(R.id.text_InOut_Day);
            TextView text_InOut_Time = convertView.findViewById(R.id.text_InOut_Time);
            TextView text_InOut = convertView.findViewById(R.id.text_InOut);
            TextView text_InOut_Comment = convertView.findViewById(R.id.text_InOut_Comment);
            TextView text_TranAccount = convertView.findViewById(R.id.text_TranAccount);
            TextView text_Money = convertView.findViewById(R.id.text_Money);
            TextView text_TranBalance = convertView.findViewById(R.id.text_TranBalance);
            holder.text_InOut_Day=text_InOut_Day;
            holder.text_InOut_Time=text_InOut_Time;
            holder.text_InOut=text_InOut;
            holder.text_InOut_Comment=text_InOut_Comment;
            holder.text_TranAccount=text_TranAccount;
            holder.text_Money=text_Money;
            holder.text_TranBalance=text_TranBalance;

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();//setTag()를 읽어서 재사용시 꺼내쓴다.
        }
        TransferDetailVO vo = list.get(position);
        holder.text_InOut_Day.setText(vo.getIn_outday());
        holder.text_InOut_Time.setText(vo.getIn_outtime());
        holder.text_InOut.setText(vo.getIn_out());
        if(vo.getIn_out().equals("입금") ){
            holder.text_InOut_Comment.setText(vo.getIn_comment());
            holder.text_InOut.setTextColor(Color.parseColor("#1E88E5"));
        }
        else if(vo.getIn_out().equals("출금")){
            holder.text_InOut_Comment.setText(vo.getOut_comment());
            holder.text_InOut.setTextColor(Color.RED);
        }
        //Log.d("Adapter Account == >", vo.getAccount());
        holder.text_TranAccount.setText(vo.getAccount());
        holder.text_Money.setText(fmt.moneyFormatToWon(vo.getMoney()));
        holder.text_TranBalance.setText(fmt.moneyFormatToWon(vo.getBalance()));

        return convertView;
    }


    static class ViewHolder{
        TextView text_InOut_Day, text_InOut_Time, text_InOut, text_InOut_Comment, text_TranAccount, text_Money, text_TranBalance;
    }


}
