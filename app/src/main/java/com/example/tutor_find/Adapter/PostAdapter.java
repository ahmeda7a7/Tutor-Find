package com.example.tutor_find.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import org.w3c.dom.Text;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PostViewHolder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {

        holder.postGroup.setText(model.getGroup());
        holder.postCurriculum.setText(model.getCurriculum());
        holder.postStudyClass.setText(model.getStudyClass());
        holder.postSubjectList.setText(model.getSubjectList());
        holder.postSalary.setText(model.getSalary());
        holder.postDescription.setText(model.getDescription());
        holder.postAddress.setText(model.getAddress());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlepost, parent, false);
        return new PostViewHolder(view);

    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        TextView postGroup;
        TextView postCurriculum;
        TextView postStudyClass;
        TextView postSubjectList;
        TextView postSalary;
        TextView postDescription;
        TextView postAddress;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postGroup = itemView.findViewById(R.id.postGroup);
            postCurriculum = itemView.findViewById(R.id.postCurriculum);
            postStudyClass = itemView.findViewById(R.id.postStudyClass);
            postSubjectList = itemView.findViewById(R.id.postSubjectList);
            postSalary = itemView.findViewById(R.id.postSalary);
            postDescription = itemView.findViewById(R.id.postDescription);
            postAddress = itemView.findViewById(R.id.postAddress);
        }
    }

}
