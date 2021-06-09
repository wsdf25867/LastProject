package com.example.levelus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<QuestlogInfo> searchlist;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public SearchAdapter(List<QuestlogInfo> searchlist, Context context){
        this.searchlist = searchlist;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return searchlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.row_listview,null);

            viewHolder = new ViewHolder();
            viewHolder.quest_finished_imgView = convertView.findViewById(R.id.quest_finished_imgView);
            viewHolder.quest_finished_title = convertView.findViewById(R.id.quest_finished_title);
            viewHolder.quest_finished_category = convertView.findViewById(R.id.quest_finished_category);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다. ***********(CharSequence 의미 찾기)
        viewHolder.label.setText((CharSequence) searchlist.get(position).getTitle_ko());

        return convertView;
    }

    class ViewHolder{
        public ImageView quest_finished_imgView;
        public TextView quest_finished_title;
        public TextView quest_finished_category;
    }

}