package com.ntam.tech.metamac.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.api.modelResponse.QuestionResponse;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.model.Answer;
import com.ntam.tech.metamac.model.FirebaseModel;
import com.ntam.tech.metamac.model.Question;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.SharedPref;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveVoteWithRequestsFragment extends Fragment {


    private Toolbar mToolbar;
    ValueEventListener valueEventListener;
    DatabaseReference myRef;
    FrameLayout radioGroupFrame;
    RadioGroup radioGroup;
    Button btnSubmit;
    TextView tvQuestion, tvNoQuestion;
    ProgressBar progress;
    int userId, questionID;
    private int answerId=0;
    private int currentQuestionId;
    private FirebaseModel model;

    public LiveVoteWithRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_vote, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        onClick();
        valueEventListener();
    }

    private void onClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerId == 0) {
                    Toast.makeText(getActivity(), "Please Select Answer", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    progress.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                    RetrofitRequest.answerQuestion(currentQuestionId, answerId, userId, new RetrofitResponse() {
                        @Override
                        public void onSuccess(Object o) {
                            progress.setVisibility(View.GONE);
                            SharedPref.setQuestionID(getContext(),currentQuestionId);
                            FirebaseDatabase.getInstance().getReference()
                            .child("counter").setValue((model.counter+1));
                            Toast.makeText(getContext(), "Thanks", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            progress.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        userId = SharedPref.getMyAccount(getContext()).getUserId(); // get id from sharedpref
        questionID = SharedPref.getQuestionID(getContext());
        addListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeListener();
    }

    private void removeListener() {
        if (valueEventListener != null && myRef != null)
            myRef.removeEventListener(valueEventListener);
    }

    private void addListener() {
        myRef = FirebaseDatabase.getInstance().getReference();
        if (valueEventListener != null) ;
        myRef.addValueEventListener(valueEventListener);

    }


    private void isNewQuestion(boolean question) {
        if (question) {
            tvQuestion.setVisibility(View.VISIBLE);
            radioGroupFrame.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            tvNoQuestion.setVisibility(View.GONE);
        } else {
            tvQuestion.setVisibility(View.GONE);
            radioGroupFrame.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            tvNoQuestion.setVisibility(View.VISIBLE);
        }
        progress.setVisibility(View.GONE);
    }

    private void findViewById(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        radioGroupFrame = view.findViewById(R.id.radio_group_frame);
        btnSubmit = view.findViewById(R.id.btn_submit);
        tvQuestion = view.findViewById(R.id.tv_question);
        tvNoQuestion = view.findViewById(R.id.tv_no_question);
        progress = view.findViewById(R.id.progress);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Live Vote");

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnSubmit.setVisibility(View.INVISIBLE);
    }

    private void valueEventListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model = dataSnapshot.getValue(FirebaseModel.class);
                // check if current question id not equal 0
                if(model.current_question_id == 0){
                    isNewQuestion(false);
                    return;
                }
                // new question
                // make request
                if (model.current_question_id != questionID) {
                    currentQuestionId = model.current_question_id ;
                    RetrofitRequest.getQuestion(model.current_question_id, new RetrofitResponse<QuestionResponse>() {
                        @Override
                        public void onSuccess(QuestionResponse questionResponse) {
                            setQuestions(questionResponse.getQuestion());
                            isNewQuestion(true);
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // same question
                    isNewQuestion(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    private void setQuestions(Question questions) {
        tvQuestion.setText(questions.question);
        radioGroupFrame.removeAllViews();
        radioGroup = new RadioGroup(getContext());
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < questions.answers.size(); i++) {
            Answer answer = questions.answers.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(answer.getBody());
            final int finalI = answer.getId();
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    answerId = finalI;
                }
            });
            radioGroup.addView(radioButton);
        }
        radioGroupFrame.addView(radioGroup);
    }
}



