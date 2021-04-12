package com.teamproject.sample.kosbank.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.sample.kosbank.R;

import java.util.List;

public class testAdapter extends BaseAdapter {

    private List<testVO> list;

    //drowable 폴더에 이미지를 복사해서 붙여넣는다. 이미지는 숫자로 사용할 수 없고 반드시 소문자 여야한다.
    public testAdapter(List<testVO> list) {
        this.list = list;
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
        ViewHolder holder; //맨아래 ViewHolder 클래스를 먼저 작성후 선언한다.

        if(convertView == null) {
            holder = new ViewHolder();
            //LayoutInflater: Activity 이외의 클래스에서 Context를 통해 XML로 정의한 레이아웃을 로드하여 View로 반환해주는 클래스이다.
            //inflate(activity_item_weather.xml을 로드하고, 이 레이아웃의 부모뷰그룹인 parent를 지정해주고, 이 레이아웃이 루트 레이아웃인지 아닌지 지정한다.)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_view, parent, false);

            TextView text1 = convertView.findViewById(R.id.text1);
            TextView text2 = convertView.findViewById(R.id.text2);
            TextView text3 = convertView.findViewById(R.id.text3);
            holder.text1=text1;
            holder.text2=text2;
            holder.text3=text3;

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();//setTag()를 읽어서 재사용시 꺼내쓴다.
        }
        testVO vo = list.get(position);
        holder.text1.setText(vo.getText1());
        holder.text2.setText(vo.getText2());
        holder.text3.setText(vo.getText3());
        return convertView;
    }

    static class ViewHolder{
        TextView testimg;
        TextView text1;
        TextView text2;
        TextView text3;
    }
}
