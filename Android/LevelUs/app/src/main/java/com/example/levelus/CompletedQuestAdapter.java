package com.example.levelus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CompletedQuestAdapter extends BaseAdapter implements Filterable {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList. (원본 데이터 리스트)
    private ArrayList<ListViewItem> listViewItemList ;
    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<ListViewItem> filteredItemList;
    Filter listFilter ;
    Drawable icon;
    CompletedQuestActivity activity;
    // ListViewAdapter의 생성자
    public CompletedQuestAdapter(ArrayList<ListViewItem> listViewItemList, CompletedQuestActivity activity) {
        this.listViewItemList = listViewItemList;
        this.filteredItemList = listViewItemList;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return filteredItemList.size() ;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position) ;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.quest_finished_imgView) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.quest_finished_title) ;
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.quest_finished_category) ;
        TextView ratingTextView = (TextView) convertView.findViewById(R.id.quest_finished_rating) ;
        TextView acceptedDateTextView = (TextView) convertView.findViewById(R.id.quest_finished_accepted_date) ;
        TextView finishedDateTextView = (TextView) convertView.findViewById(R.id.quest_finished_finished_date) ;

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = filteredItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIconDrawable());
        titleTextView.setText(listViewItem.getTitle_ko());
        categoryTextView.setText(listViewItem.getCategory());
        ratingTextView.setText(listViewItem.getRating());
        acceptedDateTextView.setText(listViewItem.getAccepted_date());
        finishedDateTextView.setText(listViewItem.getFinished_date());

        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedQuestAdapter.CompletedQuestAlertDialogFragment newDialogFragment;
                newDialogFragment = CompletedQuestAdapter.CompletedQuestAlertDialogFragment.newInstance(listViewItem.getIconDrawable());
                newDialogFragment.show(activity.getSupportFragmentManager(),"quest_img");
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }

        return listFilter ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title_ko, String rating, String category, String accepted_date, String finished_date ) {
        ListViewItem item = new ListViewItem();
        this.icon = icon;
        item.setIconDrawable(icon);
        item.setTitle_ko(title_ko);
        item.setRating(rating);
        item.setCategory(category);
        item.setAccepted_date(accepted_date);
        item.setFinished_date(finished_date);

        listViewItemList.add(item);
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;
            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>() ;

                for (ListViewItem item : listViewItemList) {
                    if (item.getTitle_ko().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // update listview by filtered data list.
            filteredItemList = (ArrayList<ListViewItem>) results.values ;
            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }


    }

    public static class CompletedQuestAlertDialogFragment extends AppCompatDialogFragment {
        ImageView completed_quest_img;

        public static CompletedQuestAdapter.CompletedQuestAlertDialogFragment newInstance(Drawable img) {
            CompletedQuestAdapter.CompletedQuestAlertDialogFragment frag = new CompletedQuestAdapter.CompletedQuestAlertDialogFragment();
            Bundle args = new Bundle();
            Bitmap bitmap = ((BitmapDrawable)img).getBitmap();
            args.putParcelable("bitmap",bitmap);
            frag.setArguments(args);
            return frag;
        }


        @NonNull
        @NotNull
        @Override
        public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_completed_quest_img, null);
            builder.setView(view).setTitle(getArguments().getString("title_ko"));

            completed_quest_img = view.findViewById(R.id.completed_quest_img);
            completed_quest_img.setImageBitmap(getArguments().getParcelable("bitmap"));
            return builder.create();
        }

    }
}

