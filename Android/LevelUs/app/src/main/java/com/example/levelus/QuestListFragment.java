package com.example.levelus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestListFragment extends Fragment {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://collabtest-71a4d.appspot.com");;
    private StorageReference storageRef = storage.getReference();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static int questNum = 0;

    public QuestListFragment() {
        // Required empty public constructor
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(String title){
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //return super.onCreateDialog(savedInstanceState);

            String title = getArguments().getString("title");
            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(title)
                    .setPositiveButton("수락하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("MyLog", "확인 버튼이 눌림");
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("MyLog", "취소 버튼이 눌림");
                        }
                    })
                    .create();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestListFragment newInstance(String param1, String param2) {
        QuestListFragment fragment = new QuestListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest_list, container, false);
        // Inflate the layout for this fragment

        //        TextView added = view.findViewById(R.id.added);
//        TextView category = view.findViewById(R.id.category);
//        TextView done = view.findViewById(R.id.done);
//        TextView keyword = view.findViewById(R.id.keyword);
//        TextView quest_num = view.findViewById(R.id.quest_num);   //얘가 퀘스트 번호(이미지 번호랑 매칭)
//        TextView title = view.findViewById(R.id.title);
//        TextView way = view.findViewById(R.id.way);
        TextView title_ko = view.findViewById(R.id.title_ko);       //quest title
        TextView title_ko1 = view.findViewById(R.id.title_ko1);
        TextView title_ko2 = view.findViewById(R.id.title_ko2);
        TextView title_ko3 = view.findViewById(R.id.title_ko3);
        TextView title_ko4 = view.findViewById(R.id.title_ko4);
        TextView title_ko5 = view.findViewById(R.id.title_ko5);
        TextView title_ko6 = view.findViewById(R.id.title_ko6);
        TextView title_ko7 = view.findViewById(R.id.title_ko7);
        TextView title_ko8 = view.findViewById(R.id.title_ko8);
        TextView title_ko9 = view.findViewById(R.id.title_ko9);
        TextView title_ko10 = view.findViewById(R.id.title_ko10);
        TextView title_ko11 = view.findViewById(R.id.title_ko11);
        TextView title_ko12 = view.findViewById(R.id.title_ko12);
        TextView title_ko13 = view.findViewById(R.id.title_ko13);
        TextView title_ko14 = view.findViewById(R.id.title_ko14);
        TextView title_ko15 = view.findViewById(R.id.title_ko15);
        TextView title_ko16 = view.findViewById(R.id.title_ko16);
        TextView title_ko17 = view.findViewById(R.id.title_ko17);
        TextView title_ko18 = view.findViewById(R.id.title_ko18);
        TextView title_ko19 = view.findViewById(R.id.title_ko19);
        TextView title_ko20 = view.findViewById(R.id.title_ko20);
        TextView title_ko21 = view.findViewById(R.id.title_ko21);
        TextView title_ko22 = view.findViewById(R.id.title_ko22);
        TextView title_ko23 = view.findViewById(R.id.title_ko23);
        TextView title_ko24 = view.findViewById(R.id.title_ko24);
        TextView title_ko25 = view.findViewById(R.id.title_ko25);
        TextView title_ko26 = view.findViewById(R.id.title_ko26);
        TextView title_ko27 = view.findViewById(R.id.title_ko27);
        TextView title_ko28 = view.findViewById(R.id.title_ko28);
        TextView title_ko29 = view.findViewById(R.id.title_ko29);
        TextView title_ko30 = view.findViewById(R.id.title_ko30);
        TextView title_ko31 = view.findViewById(R.id.title_ko31);
        TextView title_ko32 = view.findViewById(R.id.title_ko32);
        TextView title_ko33 = view.findViewById(R.id.title_ko33);
        TextView title_ko34 = view.findViewById(R.id.title_ko34);
        TextView title_ko35 = view.findViewById(R.id.title_ko35);
        TextView title_ko36 = view.findViewById(R.id.title_ko36);
        TextView title_ko37 = view.findViewById(R.id.title_ko37);
        TextView title_ko38 = view.findViewById(R.id.title_ko38);
        TextView title_ko39 = view.findViewById(R.id.title_ko39);
        TextView title_ko40 = view.findViewById(R.id.title_ko40);
        TextView title_ko41 = view.findViewById(R.id.title_ko41);
        TextView title_ko42 = view.findViewById(R.id.title_ko42);
        TextView title_ko43 = view.findViewById(R.id.title_ko43);
        TextView title_ko44 = view.findViewById(R.id.title_ko44);
        TextView title_ko45 = view.findViewById(R.id.title_ko45);
        TextView title_ko46 = view.findViewById(R.id.title_ko46);
        TextView title_ko47 = view.findViewById(R.id.title_ko47);
        TextView title_ko48 = view.findViewById(R.id.title_ko48);
        TextView title_ko49 = view.findViewById(R.id.title_ko49);
        TextView title_ko50 = view.findViewById(R.id.title_ko50);

        ImageButton quest = view.findViewById(R.id.quest);
        ImageButton quest1 = view.findViewById(R.id.quest1);
        ImageButton quest2 = view.findViewById(R.id.quest2);
        ImageButton quest3 = view.findViewById(R.id.quest3);
        ImageButton quest4 = view.findViewById(R.id.quest4);
        ImageButton quest5 = view.findViewById(R.id.quest5);
        ImageButton quest6 = view.findViewById(R.id.quest6);
        ImageButton quest7 = view.findViewById(R.id.quest7);
        ImageButton quest8 = view.findViewById(R.id.quest8);
        ImageButton quest9 = view.findViewById(R.id.quest9);
        ImageButton quest10 = view.findViewById(R.id.quest10);
        ImageButton quest11 = view.findViewById(R.id.quest11);
        ImageButton quest12 = view.findViewById(R.id.quest12);
        ImageButton quest13 = view.findViewById(R.id.quest13);
        ImageButton quest14 = view.findViewById(R.id.quest14);
        ImageButton quest15 = view.findViewById(R.id.quest15);
        ImageButton quest16 = view.findViewById(R.id.quest16);
        ImageButton quest17 = view.findViewById(R.id.quest17);
        ImageButton quest18 = view.findViewById(R.id.quest18);
        ImageButton quest19 = view.findViewById(R.id.quest19);
        ImageButton quest20 = view.findViewById(R.id.quest20);
        ImageButton quest21 = view.findViewById(R.id.quest21);
        ImageButton quest22 = view.findViewById(R.id.quest22);
        ImageButton quest23 = view.findViewById(R.id.quest23);
        ImageButton quest24 = view.findViewById(R.id.quest24);
        ImageButton quest25 = view.findViewById(R.id.quest25);
        ImageButton quest26 = view.findViewById(R.id.quest26);
        ImageButton quest27 = view.findViewById(R.id.quest27);
        ImageButton quest28 = view.findViewById(R.id.quest28);
        ImageButton quest29 = view.findViewById(R.id.quest29);
        ImageButton quest30 = view.findViewById(R.id.quest30);
        ImageButton quest31 = view.findViewById(R.id.quest31);
        ImageButton quest32 = view.findViewById(R.id.quest32);
        ImageButton quest33 = view.findViewById(R.id.quest33);
        ImageButton quest34 = view.findViewById(R.id.quest34);
        ImageButton quest35 = view.findViewById(R.id.quest35);
        ImageButton quest36 = view.findViewById(R.id.quest36);
        ImageButton quest37 = view.findViewById(R.id.quest37);
        ImageButton quest38 = view.findViewById(R.id.quest38);
        ImageButton quest39 = view.findViewById(R.id.quest39);
        ImageButton quest40 = view.findViewById(R.id.quest40);
        ImageButton quest41 = view.findViewById(R.id.quest41);
        ImageButton quest42 = view.findViewById(R.id.quest42);
        ImageButton quest43 = view.findViewById(R.id.quest43);
        ImageButton quest44 = view.findViewById(R.id.quest44);
        ImageButton quest45 = view.findViewById(R.id.quest45);
        ImageButton quest46 = view.findViewById(R.id.quest46);
        ImageButton quest47 = view.findViewById(R.id.quest47);
        ImageButton quest48 = view.findViewById(R.id.quest48);
        ImageButton quest49 = view.findViewById(R.id.quest49);
        ImageButton quest50 = view.findViewById(R.id.quest50);



        TextView title = view.findViewById(R.id.title);                 //카테고리 이름
        ImageButton category1 = view.findViewById(R.id.category1);     //카테고리들 선택
        ImageButton category2 = view.findViewById(R.id.category2);
        ImageButton category3 = view.findViewById(R.id.category3);
        ImageButton category4 = view.findViewById(R.id.category4);
        ImageButton category5 = view.findViewById(R.id.category5);
        ImageButton category6 = view.findViewById(R.id.category6);
        ImageButton category7 = view.findViewById(R.id.category7);
        ImageButton category8 = view.findViewById(R.id.category8);
        ImageButton category9 = view.findViewById(R.id.category9);
        ImageButton category10 = view.findViewById(R.id.category10);
        ImageButton category11 = view.findViewById(R.id.category11);
        ImageButton category12 = view.findViewById(R.id.category12);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        title_ko.setVisibility(View.GONE);
        title_ko1.setVisibility(View.GONE);    //여기부터 가리기
        title_ko2.setVisibility(View.GONE);
        title_ko3.setVisibility(View.GONE);
        title_ko4.setVisibility(View.GONE);
        title_ko5.setVisibility(View.GONE);
        title_ko6.setVisibility(View.GONE);
        title_ko7.setVisibility(View.GONE);
        title_ko8.setVisibility(View.GONE);
        title_ko9.setVisibility(View.GONE);
        title_ko10.setVisibility(View.GONE);
        title_ko11.setVisibility(View.GONE);
        title_ko12.setVisibility(View.GONE);
        title_ko13.setVisibility(View.GONE);
        title_ko14.setVisibility(View.GONE);
        title_ko15.setVisibility(View.GONE);
        title_ko16.setVisibility(View.GONE);
        title_ko17.setVisibility(View.GONE);
        title_ko18.setVisibility(View.GONE);
        title_ko19.setVisibility(View.GONE);
        title_ko20.setVisibility(View.GONE);
        title_ko21.setVisibility(View.GONE);
        title_ko22.setVisibility(View.GONE);
        title_ko23.setVisibility(View.GONE);
        title_ko24.setVisibility(View.GONE);
        title_ko25.setVisibility(View.GONE);
        title_ko26.setVisibility(View.GONE);
        title_ko27.setVisibility(View.GONE);
        title_ko28.setVisibility(View.GONE);
        title_ko29.setVisibility(View.GONE);
        title_ko30.setVisibility(View.GONE);
        title_ko31.setVisibility(View.GONE);
        title_ko32.setVisibility(View.GONE);
        title_ko33.setVisibility(View.GONE);
        title_ko34.setVisibility(View.GONE);
        title_ko35.setVisibility(View.GONE);
        title_ko36.setVisibility(View.GONE);
        title_ko37.setVisibility(View.GONE);
        title_ko38.setVisibility(View.GONE);
        title_ko39.setVisibility(View.GONE);
        title_ko40.setVisibility(View.GONE);
        title_ko41.setVisibility(View.GONE);
        title_ko42.setVisibility(View.GONE);
        title_ko43.setVisibility(View.GONE);
        title_ko44.setVisibility(View.GONE);
        title_ko45.setVisibility(View.GONE);
        title_ko46.setVisibility(View.GONE);
        title_ko47.setVisibility(View.GONE);
        title_ko48.setVisibility(View.GONE);
        title_ko49.setVisibility(View.GONE);
        title_ko50.setVisibility(View.GONE);

        quest.setVisibility(View.GONE);
        quest1.setVisibility(View.GONE);       //여기부터 가리기
        quest2.setVisibility(View.GONE);
        quest3.setVisibility(View.GONE);
        quest4.setVisibility(View.GONE);
        quest5.setVisibility(View.GONE);
        quest6.setVisibility(View.GONE);
        quest7.setVisibility(View.GONE);
        quest8.setVisibility(View.GONE);
        quest9.setVisibility(View.GONE);
        quest10.setVisibility(View.GONE);
        quest11.setVisibility(View.GONE);
        quest12.setVisibility(View.GONE);
        quest13.setVisibility(View.GONE);
        quest14.setVisibility(View.GONE);
        quest15.setVisibility(View.GONE);
        quest16.setVisibility(View.GONE);
        quest17.setVisibility(View.GONE);
        quest18.setVisibility(View.GONE);
        quest19.setVisibility(View.GONE);
        quest20.setVisibility(View.GONE);
        quest21.setVisibility(View.GONE);
        quest22.setVisibility(View.GONE);
        quest23.setVisibility(View.GONE);
        quest24.setVisibility(View.GONE);
        quest25.setVisibility(View.GONE);
        quest26.setVisibility(View.GONE);
        quest27.setVisibility(View.GONE);
        quest28.setVisibility(View.GONE);
        quest29.setVisibility(View.GONE);
        quest30.setVisibility(View.GONE);
        quest31.setVisibility(View.GONE);
        quest32.setVisibility(View.GONE);
        quest33.setVisibility(View.GONE);
        quest34.setVisibility(View.GONE);
        quest35.setVisibility(View.GONE);
        quest36.setVisibility(View.GONE);
        quest37.setVisibility(View.GONE);
        quest38.setVisibility(View.GONE);
        quest39.setVisibility(View.GONE);
        quest40.setVisibility(View.GONE);
        quest41.setVisibility(View.GONE);
        quest42.setVisibility(View.GONE);
        quest43.setVisibility(View.GONE);
        quest44.setVisibility(View.GONE);
        quest45.setVisibility(View.GONE);
        quest46.setVisibility(View.GONE);
        quest47.setVisibility(View.GONE);
        quest48.setVisibility(View.GONE);
        quest49.setVisibility(View.GONE);
        quest50.setVisibility(View.GONE);

        title.setText("카테고리를 선택해 주세요");



        QuestInfo[] questInfo = new QuestInfo[100];
        category1.setOnClickListener(new View.OnClickListener() {   //diy   51개..?
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("diy").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("diy");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);
                        title_ko13.setVisibility(View.VISIBLE);
                        title_ko14.setVisibility(View.VISIBLE);
                        title_ko15.setVisibility(View.VISIBLE);
                        title_ko16.setVisibility(View.VISIBLE);
                        title_ko17.setVisibility(View.VISIBLE);
                        title_ko18.setVisibility(View.VISIBLE);
                        title_ko19.setVisibility(View.VISIBLE);
                        title_ko20.setVisibility(View.VISIBLE);
                        title_ko21.setVisibility(View.VISIBLE);
                        title_ko22.setVisibility(View.VISIBLE);
                        title_ko23.setVisibility(View.VISIBLE);
                        title_ko24.setVisibility(View.VISIBLE);
                        title_ko25.setVisibility(View.VISIBLE);
                        title_ko26.setVisibility(View.VISIBLE);
                        title_ko27.setVisibility(View.VISIBLE);
                        title_ko28.setVisibility(View.VISIBLE);
                        title_ko29.setVisibility(View.VISIBLE);
                        title_ko30.setVisibility(View.VISIBLE);
                        title_ko31.setVisibility(View.VISIBLE);
                        title_ko32.setVisibility(View.VISIBLE);
                        title_ko33.setVisibility(View.VISIBLE);
                        title_ko34.setVisibility(View.VISIBLE);
                        title_ko35.setVisibility(View.VISIBLE);
                        title_ko36.setVisibility(View.VISIBLE);
                        title_ko37.setVisibility(View.VISIBLE);
                        title_ko38.setVisibility(View.VISIBLE);
                        title_ko39.setVisibility(View.VISIBLE);
                        title_ko40.setVisibility(View.VISIBLE);
                        title_ko41.setVisibility(View.VISIBLE);
                        title_ko42.setVisibility(View.VISIBLE);
                        title_ko43.setVisibility(View.VISIBLE);
                        title_ko44.setVisibility(View.VISIBLE);
                        title_ko45.setVisibility(View.VISIBLE);
                        title_ko46.setVisibility(View.VISIBLE);
                        title_ko47.setVisibility(View.VISIBLE);
                        title_ko48.setVisibility(View.VISIBLE);
                        title_ko49.setVisibility(View.VISIBLE);
                        title_ko50.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);
                        quest13.setVisibility(View.VISIBLE);
                        quest14.setVisibility(View.VISIBLE);
                        quest15.setVisibility(View.VISIBLE);
                        quest16.setVisibility(View.VISIBLE);
                        quest17.setVisibility(View.VISIBLE);
                        quest18.setVisibility(View.VISIBLE);
                        quest19.setVisibility(View.VISIBLE);
                        quest20.setVisibility(View.VISIBLE);
                        quest21.setVisibility(View.VISIBLE);
                        quest22.setVisibility(View.VISIBLE);
                        quest23.setVisibility(View.VISIBLE);
                        quest24.setVisibility(View.VISIBLE);
                        quest25.setVisibility(View.VISIBLE);
                        quest26.setVisibility(View.VISIBLE);
                        quest27.setVisibility(View.VISIBLE);
                        quest28.setVisibility(View.VISIBLE);
                        quest29.setVisibility(View.VISIBLE);
                        quest30.setVisibility(View.VISIBLE);
                        quest31.setVisibility(View.VISIBLE);
                        quest32.setVisibility(View.VISIBLE);
                        quest33.setVisibility(View.VISIBLE);
                        quest34.setVisibility(View.VISIBLE);
                        quest35.setVisibility(View.VISIBLE);
                        quest36.setVisibility(View.VISIBLE);
                        quest37.setVisibility(View.VISIBLE);
                        quest38.setVisibility(View.VISIBLE);
                        quest39.setVisibility(View.VISIBLE);
                        quest40.setVisibility(View.VISIBLE);
                        quest41.setVisibility(View.VISIBLE);
                        quest42.setVisibility(View.VISIBLE);
                        quest43.setVisibility(View.VISIBLE);
                        quest44.setVisibility(View.VISIBLE);
                        quest45.setVisibility(View.VISIBLE);
                        quest46.setVisibility(View.VISIBLE);
                        quest47.setVisibility(View.VISIBLE);
                        quest48.setVisibility(View.VISIBLE);
                        quest49.setVisibility(View.VISIBLE);
                        quest50.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());
                        title_ko13.setText(questInfo[13].getTitle_ko());
                        title_ko14.setText(questInfo[14].getTitle_ko());
                        title_ko15.setText(questInfo[15].getTitle_ko());
                        title_ko16.setText(questInfo[16].getTitle_ko());
                        title_ko17.setText(questInfo[17].getTitle_ko());
                        title_ko18.setText(questInfo[18].getTitle_ko());
                        title_ko19.setText(questInfo[19].getTitle_ko());
                        title_ko20.setText(questInfo[20].getTitle_ko());
                        title_ko21.setText(questInfo[21].getTitle_ko());
                        title_ko22.setText(questInfo[22].getTitle_ko());
                        title_ko23.setText(questInfo[23].getTitle_ko());
                        title_ko24.setText(questInfo[24].getTitle_ko());
                        title_ko25.setText(questInfo[25].getTitle_ko());
                        title_ko26.setText(questInfo[26].getTitle_ko());
                        title_ko27.setText(questInfo[27].getTitle_ko());
                        title_ko28.setText(questInfo[28].getTitle_ko());
                        title_ko29.setText(questInfo[29].getTitle_ko());
                        title_ko30.setText(questInfo[30].getTitle_ko());
                        title_ko31.setText(questInfo[31].getTitle_ko());
                        title_ko32.setText(questInfo[32].getTitle_ko());
                        title_ko33.setText(questInfo[33].getTitle_ko());
                        title_ko34.setText(questInfo[34].getTitle_ko());
                        title_ko35.setText(questInfo[35].getTitle_ko());
                        title_ko36.setText(questInfo[36].getTitle_ko());
                        title_ko37.setText(questInfo[37].getTitle_ko());
                        title_ko38.setText(questInfo[38].getTitle_ko());
                        title_ko39.setText(questInfo[39].getTitle_ko());
                        title_ko40.setText(questInfo[40].getTitle_ko());
                        title_ko41.setText(questInfo[41].getTitle_ko());
                        title_ko42.setText(questInfo[42].getTitle_ko());
                        title_ko43.setText(questInfo[43].getTitle_ko());
                        title_ko44.setText(questInfo[44].getTitle_ko());
                        title_ko45.setText(questInfo[45].getTitle_ko());
                        title_ko46.setText(questInfo[46].getTitle_ko());
                        title_ko47.setText(questInfo[47].getTitle_ko());
                        title_ko48.setText(questInfo[48].getTitle_ko());
                        title_ko49.setText(questInfo[49].getTitle_ko());
                        title_ko50.setText(questInfo[50].getTitle_ko());

                        storageRef.child("quest_thumbnail/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/1.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                        storageRef.child("quest_thumbnail/4.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/5.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/6.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/7.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/8.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/10.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/11.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/12.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/13.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/15.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });
                        storageRef.child("quest_thumbnail/16.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest12);
                            }
                        });
                        storageRef.child("quest_thumbnail/17.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest13);
                            }
                        });
                        storageRef.child("quest_thumbnail/18.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest14);
                            }
                        });
                        storageRef.child("quest_thumbnail/19.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest15);
                            }
                        });
                        storageRef.child("quest_thumbnail/20.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest16);
                            }
                        });
                        storageRef.child("quest_thumbnail/21.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest17);
                            }
                        });
                        storageRef.child("quest_thumbnail/22.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest18);
                            }
                        });
                        storageRef.child("quest_thumbnail/23.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest19);
                            }
                        });
                        storageRef.child("quest_thumbnail/24.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest20);
                            }
                        });
                        storageRef.child("quest_thumbnail/26.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest21);
                            }
                        });
                        storageRef.child("quest_thumbnail/27.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest22);
                            }
                        });
                        storageRef.child("quest_thumbnail/28.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest23);
                            }
                        });
                        storageRef.child("quest_thumbnail/29.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest24);
                            }
                        });
                        storageRef.child("quest_thumbnail/30.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest25);
                            }
                        });
                        storageRef.child("quest_thumbnail/31.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest26);
                            }
                        });
                        storageRef.child("quest_thumbnail/32.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest27);
                            }
                        });
                        storageRef.child("quest_thumbnail/33.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest28);
                            }
                        });
                        storageRef.child("quest_thumbnail/34.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest29);
                            }
                        });
                        storageRef.child("quest_thumbnail/35.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest30);
                            }
                        });
                        storageRef.child("quest_thumbnail/36.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest31);
                            }
                        });
                        storageRef.child("quest_thumbnail/37.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest32);
                            }
                        });
                        storageRef.child("quest_thumbnail/38.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest33);
                            }
                        });
                        storageRef.child("quest_thumbnail/39.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest34);
                            }
                        });
                        storageRef.child("quest_thumbnail/40.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest35);
                            }
                        });
                        storageRef.child("quest_thumbnail/41.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest36);
                            }
                        });
                        storageRef.child("quest_thumbnail/42.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest37);
                            }
                        });
                        storageRef.child("quest_thumbnail/43.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest38);
                            }
                        });
                        storageRef.child("quest_thumbnail/44.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest39);
                            }
                        });
                        storageRef.child("quest_thumbnail/45.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest40);
                            }
                        });
                        storageRef.child("quest_thumbnail/46.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest41);
                            }
                        });
                        storageRef.child("quest_thumbnail/47.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest42);
                            }
                        });
                        storageRef.child("quest_thumbnail/48.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest43);
                            }
                        });
                        storageRef.child("quest_thumbnail/49.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest44);
                            }
                        });
                        storageRef.child("quest_thumbnail/50.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest45);
                            }
                        });
                        storageRef.child("quest_thumbnail/51.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest46);
                            }
                        });
                        storageRef.child("quest_thumbnail/52.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest47);
                            }
                        });
                        storageRef.child("quest_thumbnail/53.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest48);
                            }
                        });
                        storageRef.child("quest_thumbnail/54.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest49);
                            }
                        });
                        storageRef.child("quest_thumbnail/55.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest50);
                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {   //entertainment 11개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("entertainment").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("entertainment");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);



                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);



                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());

                        title_ko11.setVisibility(View.GONE);    //여기부터 가리기
                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);

                        quest11.setVisibility(View.GONE);       //여기부터 가리기
                        quest12.setVisibility(View.GONE);
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/25.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/56.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/57.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/58.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/59.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/60.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/61.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/62.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/63.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/64.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/65.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category3.setOnClickListener(new View.OnClickListener() {   //food  1개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("food").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("food");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title

                        title_ko1.setVisibility(View.GONE);    //여기부터 가리기
                        title_ko2.setVisibility(View.GONE);
                        title_ko3.setVisibility(View.GONE);
                        title_ko4.setVisibility(View.GONE);
                        title_ko5.setVisibility(View.GONE);
                        title_ko6.setVisibility(View.GONE);
                        title_ko7.setVisibility(View.GONE);
                        title_ko8.setVisibility(View.GONE);
                        title_ko9.setVisibility(View.GONE);
                        title_ko10.setVisibility(View.GONE);
                        title_ko11.setVisibility(View.GONE);
                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);

                        quest1.setVisibility(View.GONE);       //여기부터 가리기
                        quest2.setVisibility(View.GONE);
                        quest3.setVisibility(View.GONE);
                        quest4.setVisibility(View.GONE);
                        quest5.setVisibility(View.GONE);
                        quest6.setVisibility(View.GONE);
                        quest7.setVisibility(View.GONE);
                        quest8.setVisibility(View.GONE);
                        quest9.setVisibility(View.GONE);
                        quest10.setVisibility(View.GONE);
                        quest11.setVisibility(View.GONE);
                        quest12.setVisibility(View.GONE);
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);


                        System.out.println(questInfo[0].getTitle_ko());


                        storageRef.child("quest_thumbnail/66.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });

                        quest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyAlertDialogFragment newDialogFragment =
                                        MyAlertDialogFragment.newInstance("Fragment Dialog 테스트 중...");
                                newDialogFragment.show(getFragmentManager(), "dialog");


                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category4.setOnClickListener(new View.OnClickListener() {   //health    3개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("health").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("health");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());

                        title_ko3.setVisibility(View.GONE);                 //여기부터 가리기
                        title_ko4.setVisibility(View.GONE);
                        title_ko5.setVisibility(View.GONE);
                        title_ko6.setVisibility(View.GONE);
                        title_ko7.setVisibility(View.GONE);
                        title_ko8.setVisibility(View.GONE);
                        title_ko9.setVisibility(View.GONE);
                        title_ko10.setVisibility(View.GONE);
                        title_ko11.setVisibility(View.GONE);
                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest3.setVisibility(View.GONE);            //여기부터 가리기기
                        quest4.setVisibility(View.GONE);
                        quest5.setVisibility(View.GONE);
                        quest6.setVisibility(View.GONE);
                        quest7.setVisibility(View.GONE);
                        quest8.setVisibility(View.GONE);
                        quest9.setVisibility(View.GONE);
                        quest10.setVisibility(View.GONE);
                        quest11.setVisibility(View.GONE);
                        quest12.setVisibility(View.GONE);
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/67.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/68.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/69.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category5.setOnClickListener(new View.OnClickListener() {   //hiking    28개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("hiking").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("hiking");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);
                        title_ko13.setVisibility(View.VISIBLE);
                        title_ko14.setVisibility(View.VISIBLE);
                        title_ko15.setVisibility(View.VISIBLE);
                        title_ko16.setVisibility(View.VISIBLE);
                        title_ko17.setVisibility(View.VISIBLE);
                        title_ko18.setVisibility(View.VISIBLE);
                        title_ko19.setVisibility(View.VISIBLE);
                        title_ko20.setVisibility(View.VISIBLE);
                        title_ko21.setVisibility(View.VISIBLE);
                        title_ko22.setVisibility(View.VISIBLE);
                        title_ko23.setVisibility(View.VISIBLE);
                        title_ko24.setVisibility(View.VISIBLE);
                        title_ko25.setVisibility(View.VISIBLE);
                        title_ko26.setVisibility(View.VISIBLE);
                        title_ko27.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);
                        quest13.setVisibility(View.VISIBLE);
                        quest14.setVisibility(View.VISIBLE);
                        quest15.setVisibility(View.VISIBLE);
                        quest16.setVisibility(View.VISIBLE);
                        quest17.setVisibility(View.VISIBLE);
                        quest18.setVisibility(View.VISIBLE);
                        quest19.setVisibility(View.VISIBLE);
                        quest20.setVisibility(View.VISIBLE);
                        quest21.setVisibility(View.VISIBLE);
                        quest22.setVisibility(View.VISIBLE);
                        quest23.setVisibility(View.VISIBLE);
                        quest24.setVisibility(View.VISIBLE);
                        quest25.setVisibility(View.VISIBLE);
                        quest26.setVisibility(View.VISIBLE);
                        quest27.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());
                        title_ko13.setText(questInfo[13].getTitle_ko());
                        title_ko14.setText(questInfo[14].getTitle_ko());
                        title_ko15.setText(questInfo[15].getTitle_ko());
                        title_ko16.setText(questInfo[16].getTitle_ko());
                        title_ko17.setText(questInfo[17].getTitle_ko());
                        title_ko18.setText(questInfo[18].getTitle_ko());
                        title_ko19.setText(questInfo[19].getTitle_ko());
                        title_ko20.setText(questInfo[20].getTitle_ko());
                        title_ko21.setText(questInfo[21].getTitle_ko());
                        title_ko22.setText(questInfo[22].getTitle_ko());
                        title_ko23.setText(questInfo[23].getTitle_ko());
                        title_ko24.setText(questInfo[24].getTitle_ko());
                        title_ko25.setText(questInfo[25].getTitle_ko());
                        title_ko26.setText(questInfo[26].getTitle_ko());
                        title_ko27.setText(questInfo[27].getTitle_ko());

                        title_ko28.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);

                        quest28.setVisibility(View.GONE);           //여기부터 가리기
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/70.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/71.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/72.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/73.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/74.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/75.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/76.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/77.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/78.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/79.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/80.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/81.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });
                        storageRef.child("quest_thumbnail/82.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest12);
                            }
                        });
                        storageRef.child("quest_thumbnail/83.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest13);
                            }
                        });
                        storageRef.child("quest_thumbnail/84.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest14);
                            }
                        });
                        storageRef.child("quest_thumbnail/85.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest15);
                            }
                        });
                        storageRef.child("quest_thumbnail/86.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest16);
                            }
                        });
                        storageRef.child("quest_thumbnail/87.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest17);
                            }
                        });
                        storageRef.child("quest_thumbnail/88.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest18);
                            }
                        });
                        storageRef.child("quest_thumbnail/89.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest19);
                            }
                        });
                        storageRef.child("quest_thumbnail/90.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest20);
                            }
                        });
                        storageRef.child("quest_thumbnail/91.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest21);
                            }
                        });
                        storageRef.child("quest_thumbnail/92.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest22);
                            }
                        });
                        storageRef.child("quest_thumbnail/93.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest23);
                            }
                        });
                        storageRef.child("quest_thumbnail/94.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest24);
                            }
                        });
                        storageRef.child("quest_thumbnail/95.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest25);
                            }
                        });
                        storageRef.child("quest_thumbnail/96.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest26);
                            }
                        });
                        storageRef.child("quest_thumbnail/97.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest27);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {   //life_milestone    31개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("life_milestone").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("life_milestone");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);
                        title_ko13.setVisibility(View.VISIBLE);
                        title_ko14.setVisibility(View.VISIBLE);
                        title_ko15.setVisibility(View.VISIBLE);
                        title_ko16.setVisibility(View.VISIBLE);
                        title_ko17.setVisibility(View.VISIBLE);
                        title_ko18.setVisibility(View.VISIBLE);
                        title_ko19.setVisibility(View.VISIBLE);
                        title_ko20.setVisibility(View.VISIBLE);
                        title_ko21.setVisibility(View.VISIBLE);
                        title_ko22.setVisibility(View.VISIBLE);
                        title_ko23.setVisibility(View.VISIBLE);
                        title_ko24.setVisibility(View.VISIBLE);
                        title_ko25.setVisibility(View.VISIBLE);
                        title_ko26.setVisibility(View.VISIBLE);
                        title_ko27.setVisibility(View.VISIBLE);
                        title_ko28.setVisibility(View.VISIBLE);
                        title_ko29.setVisibility(View.VISIBLE);
                        title_ko30.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);
                        quest13.setVisibility(View.VISIBLE);
                        quest14.setVisibility(View.VISIBLE);
                        quest15.setVisibility(View.VISIBLE);
                        quest16.setVisibility(View.VISIBLE);
                        quest17.setVisibility(View.VISIBLE);
                        quest18.setVisibility(View.VISIBLE);
                        quest19.setVisibility(View.VISIBLE);
                        quest20.setVisibility(View.VISIBLE);
                        quest21.setVisibility(View.VISIBLE);
                        quest22.setVisibility(View.VISIBLE);
                        quest23.setVisibility(View.VISIBLE);
                        quest24.setVisibility(View.VISIBLE);
                        quest25.setVisibility(View.VISIBLE);
                        quest26.setVisibility(View.VISIBLE);
                        quest27.setVisibility(View.VISIBLE);
                        quest28.setVisibility(View.VISIBLE);
                        quest29.setVisibility(View.VISIBLE);
                        quest30.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());
                        title_ko13.setText(questInfo[13].getTitle_ko());
                        title_ko14.setText(questInfo[14].getTitle_ko());
                        title_ko15.setText(questInfo[15].getTitle_ko());
                        title_ko16.setText(questInfo[16].getTitle_ko());
                        title_ko17.setText(questInfo[17].getTitle_ko());
                        title_ko18.setText(questInfo[18].getTitle_ko());
                        title_ko19.setText(questInfo[19].getTitle_ko());
                        title_ko20.setText(questInfo[20].getTitle_ko());
                        title_ko21.setText(questInfo[21].getTitle_ko());
                        title_ko22.setText(questInfo[22].getTitle_ko());
                        title_ko23.setText(questInfo[23].getTitle_ko());
                        title_ko24.setText(questInfo[24].getTitle_ko());
                        title_ko25.setText(questInfo[25].getTitle_ko());
                        title_ko26.setText(questInfo[26].getTitle_ko());
                        title_ko27.setText(questInfo[27].getTitle_ko());
                        title_ko28.setText(questInfo[28].getTitle_ko());
                        title_ko29.setText(questInfo[29].getTitle_ko());
                        title_ko30.setText(questInfo[30].getTitle_ko());

                        title_ko31.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);

                        quest31.setVisibility(View.GONE);           //여기부터 가리기
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/108.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/109.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/110.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/111.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/112.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/113.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/114.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/115.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/116.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/117.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/118.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/119.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });
                        storageRef.child("quest_thumbnail/120.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest12);
                            }
                        });
                        storageRef.child("quest_thumbnail/121.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest13);
                            }
                        });
                        storageRef.child("quest_thumbnail/122.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest14);
                            }
                        });
                        storageRef.child("quest_thumbnail/123.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest15);
                            }
                        });
                        storageRef.child("quest_thumbnail/124.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest16);
                            }
                        });
                        storageRef.child("quest_thumbnail/125.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest17);
                            }
                        });
                        storageRef.child("quest_thumbnail/126.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest18);
                            }
                        });
                        storageRef.child("quest_thumbnail/127.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest19);
                            }
                        });
                        storageRef.child("quest_thumbnail/128.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest20);
                            }
                        });
                        storageRef.child("quest_thumbnail/129.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest21);
                            }
                        });
                        storageRef.child("quest_thumbnail/130.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest22);
                            }
                        });
                        storageRef.child("quest_thumbnail/131.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest23);
                            }
                        });
                        storageRef.child("quest_thumbnail/132.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest24);
                            }
                        });
                        storageRef.child("quest_thumbnail/133.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest25);
                            }
                        });
                        storageRef.child("quest_thumbnail/134.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest26);
                            }
                        });
                        storageRef.child("quest_thumbnail/135.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest27);
                            }
                        });
                        storageRef.child("quest_thumbnail/136.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest28);
                            }
                        });
                        storageRef.child("quest_thumbnail/137.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest29);
                            }
                        });
                        storageRef.child("quest_thumbnail/138.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest30);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category7.setOnClickListener(new View.OnClickListener() {   //love  6개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("love").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("love");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());

                        title_ko6.setVisibility(View.GONE);                 //여기부터 가리기
                        title_ko7.setVisibility(View.GONE);
                        title_ko8.setVisibility(View.GONE);
                        title_ko9.setVisibility(View.GONE);
                        title_ko10.setVisibility(View.GONE);
                        title_ko11.setVisibility(View.GONE);
                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest6.setVisibility(View.GONE);        //여기부터 가리기
                        quest7.setVisibility(View.GONE);
                        quest8.setVisibility(View.GONE);
                        quest9.setVisibility(View.GONE);
                        quest10.setVisibility(View.GONE);
                        quest11.setVisibility(View.GONE);
                        quest12.setVisibility(View.GONE);
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/139.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/140.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/141.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/142.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/143.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/144.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category8.setOnClickListener(new View.OnClickListener() {   //nature    29개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("nature").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("nature");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);
                        title_ko13.setVisibility(View.VISIBLE);
                        title_ko14.setVisibility(View.VISIBLE);
                        title_ko15.setVisibility(View.VISIBLE);
                        title_ko16.setVisibility(View.VISIBLE);
                        title_ko17.setVisibility(View.VISIBLE);
                        title_ko18.setVisibility(View.VISIBLE);
                        title_ko19.setVisibility(View.VISIBLE);
                        title_ko20.setVisibility(View.VISIBLE);
                        title_ko21.setVisibility(View.VISIBLE);
                        title_ko22.setVisibility(View.VISIBLE);
                        title_ko23.setVisibility(View.VISIBLE);
                        title_ko24.setVisibility(View.VISIBLE);
                        title_ko25.setVisibility(View.VISIBLE);
                        title_ko26.setVisibility(View.VISIBLE);
                        title_ko27.setVisibility(View.VISIBLE);
                        title_ko28.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);
                        quest13.setVisibility(View.VISIBLE);
                        quest14.setVisibility(View.VISIBLE);
                        quest15.setVisibility(View.VISIBLE);
                        quest16.setVisibility(View.VISIBLE);
                        quest17.setVisibility(View.VISIBLE);
                        quest18.setVisibility(View.VISIBLE);
                        quest19.setVisibility(View.VISIBLE);
                        quest20.setVisibility(View.VISIBLE);
                        quest21.setVisibility(View.VISIBLE);
                        quest22.setVisibility(View.VISIBLE);
                        quest23.setVisibility(View.VISIBLE);
                        quest24.setVisibility(View.VISIBLE);
                        quest25.setVisibility(View.VISIBLE);
                        quest26.setVisibility(View.VISIBLE);
                        quest27.setVisibility(View.VISIBLE);
                        quest28.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());
                        title_ko13.setText(questInfo[13].getTitle_ko());
                        title_ko14.setText(questInfo[14].getTitle_ko());
                        title_ko15.setText(questInfo[15].getTitle_ko());
                        title_ko16.setText(questInfo[16].getTitle_ko());
                        title_ko17.setText(questInfo[17].getTitle_ko());
                        title_ko18.setText(questInfo[18].getTitle_ko());
                        title_ko19.setText(questInfo[19].getTitle_ko());
                        title_ko20.setText(questInfo[20].getTitle_ko());
                        title_ko21.setText(questInfo[21].getTitle_ko());
                        title_ko22.setText(questInfo[22].getTitle_ko());
                        title_ko23.setText(questInfo[23].getTitle_ko());
                        title_ko24.setText(questInfo[24].getTitle_ko());
                        title_ko25.setText(questInfo[25].getTitle_ko());
                        title_ko26.setText(questInfo[26].getTitle_ko());
                        title_ko27.setText(questInfo[27].getTitle_ko());
                        title_ko28.setText(questInfo[28].getTitle_ko());

                        title_ko29.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest29.setVisibility(View.GONE);           //여기부터 가리기
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/145.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/146.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/147.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/148.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/149.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/150.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/151.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/152.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/153.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/154.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/155.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/156.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });
                        storageRef.child("quest_thumbnail/157.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest12);
                            }
                        });
                        storageRef.child("quest_thumbnail/158.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest13);
                            }
                        });
                        storageRef.child("quest_thumbnail/159.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest14);
                            }
                        });
                        storageRef.child("quest_thumbnail/160.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest15);
                            }
                        });
                        storageRef.child("quest_thumbnail/161.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest16);
                            }
                        });
                        storageRef.child("quest_thumbnail/162.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest17);
                            }
                        });
                        storageRef.child("quest_thumbnail/163.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest18);
                            }
                        });
                        storageRef.child("quest_thumbnail/164.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest19);
                            }
                        });
                        storageRef.child("quest_thumbnail/165.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest20);
                            }
                        });
                        storageRef.child("quest_thumbnail/166.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest21);
                            }
                        });
                        storageRef.child("quest_thumbnail/167.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest22);
                            }
                        });
                        storageRef.child("quest_thumbnail/168.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest23);
                            }
                        });
                        storageRef.child("quest_thumbnail/169.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest24);
                            }
                        });
                        storageRef.child("quest_thumbnail/170.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest25);
                            }
                        });
                        storageRef.child("quest_thumbnail/171.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest26);
                            }
                        });
                        storageRef.child("quest_thumbnail/172.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest27);
                            }
                        });
                        storageRef.child("quest_thumbnail/173.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest28);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category9.setOnClickListener(new View.OnClickListener() {   //new_skill 13개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("new_skill").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("new_skill");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());

                        title_ko13.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest13.setVisibility(View.GONE);           //여기부터 가리기
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/174.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/175.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/176.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/177.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/178.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/179.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/180.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/181.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/182.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/183.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/184.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/185.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/186.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category10.setOnClickListener(new View.OnClickListener() {   //outdoor  12개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("outdoor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("outdoor");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());

                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);

                        quest12.setVisibility(View.GONE);           //여기부터 가리기
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/187.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/188.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/189.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/190.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/191.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/192.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });
                        storageRef.child("quest_thumbnail/193.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/194.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/195.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/196.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/197.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/198.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category11.setOnClickListener(new View.OnClickListener() {   //sports   4개
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("sports").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("sports");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //얘만 한글버전
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());

                        title_ko4.setVisibility(View.GONE);                 //여기부터 가리기
                        title_ko5.setVisibility(View.GONE);
                        title_ko6.setVisibility(View.GONE);
                        title_ko7.setVisibility(View.GONE);
                        title_ko8.setVisibility(View.GONE);
                        title_ko9.setVisibility(View.GONE);
                        title_ko10.setVisibility(View.GONE);
                        title_ko11.setVisibility(View.GONE);
                        title_ko12.setVisibility(View.GONE);
                        title_ko13.setVisibility(View.GONE);
                        title_ko14.setVisibility(View.GONE);
                        title_ko15.setVisibility(View.GONE);
                        title_ko16.setVisibility(View.GONE);
                        title_ko17.setVisibility(View.GONE);
                        title_ko18.setVisibility(View.GONE);
                        title_ko19.setVisibility(View.GONE);
                        title_ko20.setVisibility(View.GONE);
                        title_ko21.setVisibility(View.GONE);
                        title_ko22.setVisibility(View.GONE);
                        title_ko23.setVisibility(View.GONE);
                        title_ko24.setVisibility(View.GONE);
                        title_ko25.setVisibility(View.GONE);
                        title_ko26.setVisibility(View.GONE);
                        title_ko27.setVisibility(View.GONE);
                        title_ko28.setVisibility(View.GONE);
                        title_ko29.setVisibility(View.GONE);
                        title_ko30.setVisibility(View.GONE);
                        title_ko31.setVisibility(View.GONE);
                        title_ko32.setVisibility(View.GONE);
                        title_ko33.setVisibility(View.GONE);
                        title_ko34.setVisibility(View.GONE);
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest4.setVisibility(View.GONE);            //여기부터 가리기
                        quest5.setVisibility(View.GONE);
                        quest6.setVisibility(View.GONE);
                        quest7.setVisibility(View.GONE);
                        quest8.setVisibility(View.GONE);
                        quest9.setVisibility(View.GONE);
                        quest10.setVisibility(View.GONE);
                        quest11.setVisibility(View.GONE);
                        quest12.setVisibility(View.GONE);
                        quest13.setVisibility(View.GONE);
                        quest14.setVisibility(View.GONE);
                        quest15.setVisibility(View.GONE);
                        quest16.setVisibility(View.GONE);
                        quest17.setVisibility(View.GONE);
                        quest18.setVisibility(View.GONE);
                        quest19.setVisibility(View.GONE);
                        quest20.setVisibility(View.GONE);
                        quest21.setVisibility(View.GONE);
                        quest22.setVisibility(View.GONE);
                        quest23.setVisibility(View.GONE);
                        quest24.setVisibility(View.GONE);
                        quest25.setVisibility(View.GONE);
                        quest26.setVisibility(View.GONE);
                        quest27.setVisibility(View.GONE);
                        quest28.setVisibility(View.GONE);
                        quest29.setVisibility(View.GONE);
                        quest30.setVisibility(View.GONE);
                        quest31.setVisibility(View.GONE);
                        quest32.setVisibility(View.GONE);
                        quest33.setVisibility(View.GONE);
                        quest34.setVisibility(View.GONE);
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/199.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/200.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/201.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/202.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        category12.setOnClickListener(new View.OnClickListener() {   //travel   34개
           @Override
            public void onClick(View v) {
                mDatabaseRef.child("quest").child("travel").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        title.setText("travel");
                        for(int i = 0; i<(int)snapshot.getChildrenCount(); i++) {
                            questInfo[i] = snapshot.child(Integer.toString(i)).getValue(QuestInfo.class);
                            questNum++;
                        }
                        title_ko.setVisibility(View.VISIBLE);       //다시 보이게 하기
                        title_ko1.setVisibility(View.VISIBLE);
                        title_ko2.setVisibility(View.VISIBLE);
                        title_ko3.setVisibility(View.VISIBLE);
                        title_ko4.setVisibility(View.VISIBLE);
                        title_ko5.setVisibility(View.VISIBLE);
                        title_ko6.setVisibility(View.VISIBLE);
                        title_ko7.setVisibility(View.VISIBLE);
                        title_ko8.setVisibility(View.VISIBLE);
                        title_ko9.setVisibility(View.VISIBLE);
                        title_ko10.setVisibility(View.VISIBLE);
                        title_ko11.setVisibility(View.VISIBLE);
                        title_ko12.setVisibility(View.VISIBLE);
                        title_ko13.setVisibility(View.VISIBLE);
                        title_ko14.setVisibility(View.VISIBLE);
                        title_ko15.setVisibility(View.VISIBLE);
                        title_ko16.setVisibility(View.VISIBLE);
                        title_ko17.setVisibility(View.VISIBLE);
                        title_ko18.setVisibility(View.VISIBLE);
                        title_ko19.setVisibility(View.VISIBLE);
                        title_ko20.setVisibility(View.VISIBLE);
                        title_ko21.setVisibility(View.VISIBLE);
                        title_ko22.setVisibility(View.VISIBLE);
                        title_ko23.setVisibility(View.VISIBLE);
                        title_ko24.setVisibility(View.VISIBLE);
                        title_ko25.setVisibility(View.VISIBLE);
                        title_ko26.setVisibility(View.VISIBLE);
                        title_ko27.setVisibility(View.VISIBLE);
                        title_ko28.setVisibility(View.VISIBLE);
                        title_ko29.setVisibility(View.VISIBLE);
                        title_ko30.setVisibility(View.VISIBLE);
                        title_ko31.setVisibility(View.VISIBLE);
                        title_ko32.setVisibility(View.VISIBLE);
                        title_ko33.setVisibility(View.VISIBLE);


                        quest.setVisibility(View.VISIBLE);      //다시 보이게 하기
                        quest1.setVisibility(View.VISIBLE);
                        quest2.setVisibility(View.VISIBLE);
                        quest3.setVisibility(View.VISIBLE);
                        quest4.setVisibility(View.VISIBLE);
                        quest5.setVisibility(View.VISIBLE);
                        quest6.setVisibility(View.VISIBLE);
                        quest7.setVisibility(View.VISIBLE);
                        quest8.setVisibility(View.VISIBLE);
                        quest9.setVisibility(View.VISIBLE);
                        quest10.setVisibility(View.VISIBLE);
                        quest11.setVisibility(View.VISIBLE);
                        quest12.setVisibility(View.VISIBLE);
                        quest13.setVisibility(View.VISIBLE);
                        quest14.setVisibility(View.VISIBLE);
                        quest15.setVisibility(View.VISIBLE);
                        quest16.setVisibility(View.VISIBLE);
                        quest17.setVisibility(View.VISIBLE);
                        quest18.setVisibility(View.VISIBLE);
                        quest19.setVisibility(View.VISIBLE);
                        quest20.setVisibility(View.VISIBLE);
                        quest21.setVisibility(View.VISIBLE);
                        quest22.setVisibility(View.VISIBLE);
                        quest23.setVisibility(View.VISIBLE);
                        quest24.setVisibility(View.VISIBLE);
                        quest25.setVisibility(View.VISIBLE);
                        quest26.setVisibility(View.VISIBLE);
                        quest27.setVisibility(View.VISIBLE);
                        quest28.setVisibility(View.VISIBLE);
                        quest29.setVisibility(View.VISIBLE);
                        quest30.setVisibility(View.VISIBLE);
                        quest31.setVisibility(View.VISIBLE);
                        quest32.setVisibility(View.VISIBLE);
                        quest33.setVisibility(View.VISIBLE);


                        title_ko.setText(questInfo[0].getTitle_ko());      //quest title
                        title_ko1.setText(questInfo[1].getTitle_ko());
                        title_ko2.setText(questInfo[2].getTitle_ko());
                        title_ko3.setText(questInfo[3].getTitle_ko());
                        title_ko4.setText(questInfo[4].getTitle_ko());
                        title_ko5.setText(questInfo[5].getTitle_ko());
                        title_ko6.setText(questInfo[6].getTitle_ko());
                        title_ko7.setText(questInfo[7].getTitle_ko());
                        title_ko8.setText(questInfo[8].getTitle_ko());
                        title_ko9.setText(questInfo[9].getTitle_ko());
                        title_ko10.setText(questInfo[10].getTitle_ko());
                        title_ko11.setText(questInfo[11].getTitle_ko());
                        title_ko12.setText(questInfo[12].getTitle_ko());
                        title_ko13.setText(questInfo[13].getTitle_ko());
                        title_ko14.setText(questInfo[14].getTitle_ko());
                        title_ko15.setText(questInfo[15].getTitle_ko());
                        title_ko16.setText(questInfo[16].getTitle_ko());
                        title_ko17.setText(questInfo[17].getTitle_ko());
                        title_ko18.setText(questInfo[18].getTitle_ko());
                        title_ko19.setText(questInfo[19].getTitle_ko());
                        title_ko20.setText(questInfo[20].getTitle_ko());
                        title_ko21.setText(questInfo[21].getTitle_ko());
                        title_ko22.setText(questInfo[22].getTitle_ko());
                        title_ko23.setText(questInfo[23].getTitle_ko());
                        title_ko24.setText(questInfo[24].getTitle_ko());
                        title_ko25.setText(questInfo[25].getTitle_ko());
                        title_ko26.setText(questInfo[26].getTitle_ko());
                        title_ko27.setText(questInfo[27].getTitle_ko());
                        title_ko28.setText(questInfo[28].getTitle_ko());
                        title_ko29.setText(questInfo[29].getTitle_ko());
                        title_ko30.setText(questInfo[30].getTitle_ko());
                        title_ko31.setText(questInfo[31].getTitle_ko());
                        title_ko32.setText(questInfo[32].getTitle_ko());
                        title_ko33.setText(questInfo[33].getTitle_ko());

                        title_ko34.setVisibility(View.GONE);                //여기부터 가리기
                        title_ko35.setVisibility(View.GONE);
                        title_ko36.setVisibility(View.GONE);
                        title_ko37.setVisibility(View.GONE);
                        title_ko38.setVisibility(View.GONE);
                        title_ko39.setVisibility(View.GONE);
                        title_ko40.setVisibility(View.GONE);
                        title_ko41.setVisibility(View.GONE);
                        title_ko42.setVisibility(View.GONE);
                        title_ko43.setVisibility(View.GONE);
                        title_ko44.setVisibility(View.GONE);
                        title_ko45.setVisibility(View.GONE);
                        title_ko46.setVisibility(View.GONE);
                        title_ko47.setVisibility(View.GONE);
                        title_ko48.setVisibility(View.GONE);
                        title_ko49.setVisibility(View.GONE);
                        title_ko50.setVisibility(View.GONE);


                        quest34.setVisibility(View.GONE);           //여기부터 가리기ㅏ
                        quest35.setVisibility(View.GONE);
                        quest36.setVisibility(View.GONE);
                        quest37.setVisibility(View.GONE);
                        quest38.setVisibility(View.GONE);
                        quest39.setVisibility(View.GONE);
                        quest40.setVisibility(View.GONE);
                        quest41.setVisibility(View.GONE);
                        quest42.setVisibility(View.GONE);
                        quest43.setVisibility(View.GONE);
                        quest44.setVisibility(View.GONE);
                        quest45.setVisibility(View.GONE);
                        quest46.setVisibility(View.GONE);
                        quest47.setVisibility(View.GONE);
                        quest48.setVisibility(View.GONE);
                        quest49.setVisibility(View.GONE);
                        quest50.setVisibility(View.GONE);

                        storageRef.child("quest_thumbnail/2.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest);
                            }
                        });
                        storageRef.child("quest_thumbnail/3.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest1);
                            }
                        });
                        storageRef.child("quest_thumbnail/9.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest2);
                            }
                        });
                        storageRef.child("quest_thumbnail/14.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest3);
                            }
                        });
                        storageRef.child("quest_thumbnail/98.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest4);
                            }
                        });
                        storageRef.child("quest_thumbnail/99.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest5);
                            }
                        });

                        storageRef.child("quest_thumbnail/100.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest6);
                            }
                        });
                        storageRef.child("quest_thumbnail/101.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest7);
                            }
                        });
                        storageRef.child("quest_thumbnail/102.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest8);
                            }
                        });
                        storageRef.child("quest_thumbnail/103.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest9);
                            }
                        });
                        storageRef.child("quest_thumbnail/104.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest10);
                            }
                        });
                        storageRef.child("quest_thumbnail/105.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest11);
                            }
                        });
                        storageRef.child("quest_thumbnail/106.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .into(quest12);
                            }
                        });
                        storageRef.child("quest_thumbnail/107.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest13);
                            }
                        });
                        storageRef.child("quest_thumbnail/203.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest14);
                            }
                        });
                        storageRef.child("quest_thumbnail/204.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest15);
                            }
                        });
                        storageRef.child("quest_thumbnail/205.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest16);
                            }
                        });
                        storageRef.child("quest_thumbnail/206.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest17);
                            }
                        });
                        storageRef.child("quest_thumbnail/207.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest18);
                            }
                        });
                        storageRef.child("quest_thumbnail/208.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest19);
                            }
                        });
                        storageRef.child("quest_thumbnail/209.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest20);
                            }
                        });
                        storageRef.child("quest_thumbnail/210.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest21);
                            }
                        });
                        storageRef.child("quest_thumbnail/211.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest22);
                            }
                        });
                        storageRef.child("quest_thumbnail/212.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest23);
                            }
                        });
                        storageRef.child("quest_thumbnail/213.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest24);
                            }
                        });
                        storageRef.child("quest_thumbnail/214.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest25);
                            }
                        });
                        storageRef.child("quest_thumbnail/215.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest26);
                            }
                        });
                        storageRef.child("quest_thumbnail/216.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest27);
                            }
                        });
                        storageRef.child("quest_thumbnail/217.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest28);
                            }
                        });
                        storageRef.child("quest_thumbnail/218.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest29);
                            }
                        });
                        storageRef.child("quest_thumbnail/219.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest30);
                            }
                        });
                        storageRef.child("quest_thumbnail/220.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest31);
                            }
                        });
                        storageRef.child("quest_thumbnail/221.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest32);
                            }
                        });
                        storageRef.child("quest_thumbnail/222.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity().getApplicationContext())
                                        .load(uri)
                                        .override(340,400)
                                        .into(quest33);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });


        return view;
    }
}
