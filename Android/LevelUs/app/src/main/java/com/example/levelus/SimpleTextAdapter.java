package com.example.levelus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private ArrayList<QuestInfo> mData;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("ing_quest");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    String uid = firebaseUser.getUid();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quest_name ;
        Button agree_button;

        ViewHolder(View view) {
            super(view) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            quest_name = (TextView) view.findViewById(R.id.quest_name);
            agree_button = (Button) view.findViewById(R.id.agree_button);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SimpleTextAdapter(ArrayList<QuestInfo> list) {
        this.mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_quest_text,parent,false);

        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull SimpleTextAdapter.ViewHolder holder, int position) {

        holder.quest_name.setText(mData.get(position).getTitle_ko());

        holder.agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabaseRef.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        for(int i=0;i<10;i++){
                        QuestInfo questInfo = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                        if(questInfo==null){
                            mDatabaseRef.child(uid).child(Integer.toString(i)).setValue(mData.get(position));
                            break;
                        }
                        break;

                    }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }

        });
    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}