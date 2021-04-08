package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.Deposit_productVO;
import android.widget.Button;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepositAdapter extends BaseAdapter {
    private List<Deposit_productVO> list;
    // drawable 폴더에 이미지를 복사해서 붙여넣는다. 이미지는 숫자로 사용할 수 없고, 소문자이여야 한다.
    // Map<key, 이미지리소스ID 즉 숫자>
    public DepositAdapter(List<Deposit_productVO> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    // position번째의 아이템
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // position번째의 아이디
    @Override
    public long getItemId(int position) {
        return position;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder; // 맨아래 ViewHolder 클래스를 먼저 작성후 선언한다.

        if(convertView == null) {
            holder = new ViewHolder(); // 성능 개선 클래스

            // 자식 View 생성방법
            // LayoutInflater : Activity 이외의 클래스에서 Context를 통해 XML로 정의한 레이아웃을 로드하여 View로 반환해주는 클래스이다.
            // activity_item_weather.xml을 로드하고, 이 레이아웃의 부모뷰그룹인 parent를 지정해주고, 이 레이아웃이 루트레이아웃인지 아닌지 지정한다.
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_loans, parent, false);

            // 날씨, 도시명, 기온 View
            //ImageView weatherImage = convertView.findViewById(R.id.image);
            TextView d_name = convertView.findViewById(R.id.d_name);
            TextView d_summary = convertView.findViewById(R.id.d_summary);

            //holder.weatherImage = weatherImage;
            holder.d_name = d_name;
            holder.d_summary = d_summary;


            // setTag() : 모든 객체를 담을 수 있는 다용도 메서드로 특정한 목적이 없으므로 용도에 맞게 자유롭게 쓸 수 있다.
            convertView.setTag(holder); // 현재 보여지는 객체
        } else {
            holder = (ViewHolder) convertView.getTag();  // setTag()를 읽어서 재사용시 꺼내쓴다.
        }

        // 현재 포지션에 날씨 데이터
        Deposit_productVO vo = list.get(position);

        // 데이터를 설정할 때 각 아이템의 뷰홀더에 데이터를 설정해서 레이아웃 로드와 뷰를 찾는 동작을 최소화하여
        // ListView의 성능을 최적화할 수 있다. (리스트뷰 스크롤시 느리지 않게 하기 위함,, 세션 느낌)
        holder.d_name.setText(vo.getY_name());
        holder.d_summary.setText(vo.getY_summary());

        return convertView;
    }



    // 내부클래스
    // 성능 개선 클래스 viewHolder 패턴은 자주 사용하는 뷰를 한번 로드하면 재사용이고, 표시할 내용만 교체하기 위한 패턴이다.
    static  class ViewHolder {
        TextView d_name;
        TextView d_summary;
    }
}
