package com.example.han.compass.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.han.compass.MyApplication;
import com.example.han.compass.R;
import com.example.han.compass.utils.Repo_User;

import java.util.ArrayList;

//import android.widget.Button;
//import android.view.View.OnClickListener;

/**
 * Created by han on 16. 8. 20..
 */
public class CustomMemberAdapter extends BaseAdapter {
    // 문자열을 보관 할 ArrayList
//    private ArrayList<String> m_List;
    private ArrayList<Integer> m_CheckList;
    private ArrayList<Repo_User> m_UserList;

    // 생성자
    public CustomMemberAdapter() {
//        m_List = new ArrayList<String>();
        m_CheckList = new ArrayList<Integer>();
        m_UserList = new ArrayList<>();
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
//        return m_List.size();
        return m_UserList.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
//        return m_List.get(position);
        return m_UserList.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_member_item, parent, false);

            // TextView에 현재 position의 문자열 추가
            final TextView text = (TextView) convertView.findViewById(R.id.text);
//            text.setText(m_List.get(position));
            text.setText(m_UserList.get(position).getName());
            final TextView text_place = (TextView) convertView.findViewById(R.id.text_place);
            text_place.setText(m_UserList.get(position).getHome().getName());

            final LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.member_item);
            // 버튼을 터치 했을 때 이벤트 발생
            final ToggleButton btn = (ToggleButton)convertView.findViewById(R.id.btn_check);


            btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        btn.setBackgroundResource(R.drawable.correct_select);
                        linearLayout.setBackgroundColor(Color.parseColor("#234fe4FF"));
                        text.setTextColor(Color.parseColor("#234fe4"));
                        text_place.setTextColor(Color.parseColor("#234fe4"));
                        MyApplication.invite_idList.add(m_UserList.get(pos).get_id());
                        Log.d("CMA addUser  : ", MyApplication.invite_idList.toString());
                    } else {
                        btn.setBackgroundResource(R.drawable.correct);
                        linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        text.setTextColor(Color.parseColor("#50d17e"));
                        text_place.setTextColor(Color.parseColor("#50d17e"));
                        MyApplication.invite_idList.remove(MyApplication.invite_idList.indexOf(m_UserList.get(pos).get_id()));
                        Log.d("CMA removeUser  : ", MyApplication.invite_idList.toString());
                    }
                }
            });


//            btn.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 터치 시 해당 아이템 이름 출력
//                    Toast.makeText(context, m_List.get(pos), Toast.LENGTH_SHORT).show();
////                    Log.d(btn.getBackground());
//                    //m_CheckList
//
//                    btn.setBackgroundResource(R.drawable.correct_select);
//                    linearLayout.setBackgroundColor(Color.parseColor("#234fe4FF"));
//                }
//            });

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    //Toast.makeText(context, "리스트 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                }
            });

            // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
//                    Toast.makeText(context, "리스트 롱 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(Repo_User user) {
//        m_List.add(_msg);
        m_UserList.add(user);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
//        m_List.remove(_position);
        m_UserList.remove(_position);
    }


    // 외부에서 아이템 삭제 요청 시 사용
    public void clear() {
//        m_List.remove(_position);
        m_UserList.clear();
    }
}
