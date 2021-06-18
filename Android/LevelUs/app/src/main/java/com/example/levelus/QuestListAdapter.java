package com.example.levelus;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestListAdapter extends RecyclerView.Adapter<QuestListAdapter.ViewHolder>{
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference qRef = firebaseDatabase.getReference("quest");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = firebaseStorage.getReference();

    ArrayList<QuestInfo> rData = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quest_name;
        CircleImageView quest_thumbnail;

        ViewHolder(View view) {
            super(view);
            quest_name = view.findViewById(R.id.quest_list_name);
            quest_thumbnail = view.findViewById(R.id.quest_list_thumbnail);
        }
    }

    public QuestListAdapter(ArrayList<QuestInfo> list) { this.rData = list; }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestListAdapter.ViewHolder holder, int position) {
        QuestInfo cData = rData.get(position);
        holder.quest_name.setText(cData.getTitle_ko());

        storageRef.child("quest_thumbnail/" + cData.getQuest_num() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.quest_thumbnail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rData.size();
    }

    public static class MyAlertDialogFragment extends AppCompatDialogFragment {
        TextView category, success_rate, check_way, difficulty, period;

        public static MyAlertDialogFragment newInstance(String title_ko,
                                                        String category,
                                                        String added,
                                                        String done,
                                                        String check_way,
                                                        String difficulty,
                                                        String period) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Double rate = Double.parseDouble(done)/Double.parseDouble(added)*100;
            String strRate = String.format("%.2f", rate);
            String success_rate = (strRate)+"%";
            Bundle args = new Bundle();
            args.putString("title_ko", title_ko);
            args.putString("category", category);
            args.putString("success_rate", success_rate);
            args.putString("check_way", check_way);
            args.putString("difficulty", difficulty);
            args.putString("period", period);
            frag.setArguments(args);
            return frag;
        }


        @NonNull
        @NotNull
        @Override
        public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_quest_info, null);
            builder.setView(view).setTitle(getArguments().getString("title_ko"));

            String strCategory = getArguments().getString("category");
            String strSuccess_rate = getArguments().getString("success_rate");
            String strCheck_way = getArguments().getString("check_way");
            String strDifficulty = getArguments().getString("difficulty");
            String strPeriod = getArguments().getString("period");

            category = view.findViewById(R.id.category);
            success_rate = view.findViewById(R.id.success_rate);
            check_way = view.findViewById(R.id.check_way);
            difficulty = view.findViewById(R.id.difficulty);
            period = view.findViewById(R.id.period);

            category.setText(strCategory);
            success_rate.setText(strSuccess_rate);
            check_way.setText(strCheck_way);
            difficulty.setText(strDifficulty);
            period.setText(strPeriod);
            return builder.create();
        }

    }

    public void openDialog(View view){
        MyAlertDialogFragment myAlertDialogFragment = new MyAlertDialogFragment();
        myAlertDialogFragment.show(getActivity().getSupportFragmentManager(), "Quest Infos");
    }
}
