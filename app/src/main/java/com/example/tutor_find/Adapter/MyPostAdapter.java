package com.example.tutor_find.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

//testing if commit possible and push

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.ConfirmActivity;
import com.example.tutor_find.Fragments.MyPostActivity;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.PostEditActivity;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPostAdapter extends FirebaseRecyclerAdapter<Post, MyPostAdapter.MyPostViewHolder> {

    DatabaseReference deletePost;

    public MyPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyPostViewHolder holder, int position, @NonNull final Post model) {

        holder.postGroup.setText("Group: " + model.getGroup());
        holder.postCurriculum.setText("Curriculum: " + model.getCurriculum());
        holder.postStudyClass.setText("Class: " + model.getStudyClass());
        holder.postSubjectList.setText("Subjects: " + model.getSubjectList());
        holder.postSalary.setText("Salary: " + model.getSalary());
        holder.postDescription.setText("Description: " + model.getDescription());
        holder.postArea.setText("Area: " + model.getArea());
        holder.postAddress.setText("Address: " + model.getAddress());

        holder.editButton.setVisibility(View.GONE);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.editButton.getContext(), PostEditActivity.class);

                String postId=model.getPostId();

                intent.putExtra("postId", postId);

                intent.putExtra("group", model.getGroup());
                intent.putExtra("curriculum", model.getCurriculum());
                intent.putExtra("studyClass", model.getStudyClass());
                intent.putExtra("subjectList", model.getSubjectList());
                intent.putExtra("salary", model.getSalary());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("area", model.getArea());
                intent.putExtra("address", model.getAddress());
                intent.putExtra("userId", model.getUserId());



                holder.editButton.getContext().startActivity(intent);

            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder deleteConfirmation;
                deleteConfirmation = new AlertDialog.Builder(holder.deleteButton.getContext());
                deleteConfirmation.setTitle("Confirm delete");
                deleteConfirmation.setMessage("Are you sure you want to delete this post?");

                deleteConfirmation.setCancelable(false);

                deleteConfirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deletePost = FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId());
                        deletePost.removeValue();

                        Toast.makeText(holder.deleteButton.getContext(), "Post Deleted", Toast.LENGTH_SHORT).show();

                    }
                });

                deleteConfirmation.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = deleteConfirmation.create();
                alertDialog.show();


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
        TextView postArea;
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
            postArea = itemView.findViewById(R.id.postArea);
            postAddress = itemView.findViewById(R.id.postAddress);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);

        }

    }
}
