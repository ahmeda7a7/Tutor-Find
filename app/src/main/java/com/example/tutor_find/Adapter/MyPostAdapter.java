package com.example.tutor_find.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyPostAdapter extends FirebaseRecyclerAdapter<Post, MyPostAdapter.MyPostViewHolder> {


    public MyPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyPostViewHolder holder, int position, @NonNull Post model) {

        holder.postGroup.setText("Group: " + model.getGroup());
        holder.postCurriculum.setText("Curriculum: " + model.getCurriculum());
        holder.postStudyClass.setText("Class: " + model.getStudyClass());
        holder.postSubjectList.setText("Subjects: " + model.getSubjectList());
        holder.postSalary.setText("Salary: " + model.getSalary());
        holder.postDescription.setText("Description: " + model.getDescription());
        holder.postAddress.setText("Address: " + model.getAddress());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_delete_edit, parent, false);
        return new MyPostAdapter.MyPostViewHolder(view);
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder{

        TextView postGroup;
        TextView postCurriculum;
        TextView postStudyClass;
        TextView postSubjectList;
        TextView postSalary;
        TextView postDescription;
        TextView postAddress;
        Button deleteButton;
        Button editButton;

        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);
            postGroup = itemView.findViewById(R.id.postGroup);
            postCurriculum = itemView.findViewById(R.id.postCurriculum);
            postStudyClass = itemView.findViewById(R.id.postStudyClass);
            postSubjectList = itemView.findViewById(R.id.postSubjectList);
            postSalary = itemView.findViewById(R.id.postSalary);
            postDescription = itemView.findViewById(R.id.postDescription);
            postAddress = itemView.findViewById(R.id.postAddress);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);

        }

    }
}
