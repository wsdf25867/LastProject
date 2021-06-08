package com.example.levelus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IngQuestAdapter extends RecyclerView.Adapter<IngQuestAdapter.ViewHolder>{

    ArrayList<QuestlogInfo> qData;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef = firebaseDatabase.getReference("quest_log");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    String uid = firebaseUser.getUid();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ing_quest_name;
        Button check_button,forgive_button;

        ViewHolder(View view) {
            super(view);
            // 뷰 객체에 대한 참조. (hold strong reference)
            ing_quest_name = (TextView) view.findViewById(R.id.ing_quest_name);
            check_button = (Button) view.findViewById(R.id.check_button);
            forgive_button = (Button) view.findViewById(R.id.forgive_button);
        }
    }

    public IngQuestAdapter(ArrayList<QuestlogInfo> ing_list) {
        this.qData = ing_list;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ing_quest_text, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.ing_quest_name.setText(qData.get(position).getTitle_ko());


    }

    @Override
    public int getItemCount() {
        return qData.size();
    }
}
