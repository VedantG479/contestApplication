package com.example.userapp.Tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userapp.R;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class  Match_Adapter extends RecyclerView.Adapter<Match_Adapter.matchViewAdapter> {
    private Context context;
    private List<Match_Data> list;

    public Match_Adapter(Context context, List<Match_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public matchViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_layout,parent,false);
        return new matchViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull matchViewAdapter holder, int position) {
        Match_Data currentItem = list.get(position);
        holder.matchTime.setText(currentItem.getMatchTime());
        holder.matchDate.setText(currentItem.getMatchDate());
        holder.uploadTime.setText(currentItem.getUploadTime());
        holder.uploadDate.setText(currentItem.getUploadDate());
        holder.referenceID.setText(currentItem.getReferID());
        holder.RoomID.setText(currentItem.getRoom_Id());
        holder.RoomPass.setText(currentItem.getRoom_pass());
        holder.price.setText(currentItem.getMatchCharge());
        holder.matchCategory.setText(currentItem.getMatchCategory());
        holder.reward.setText(currentItem.getReward());

        Picasso.get().load(currentItem.getImageUrl()).into(holder.ticketImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class matchViewAdapter extends RecyclerView.ViewHolder {
        private CircleImageView ticketImage;
        private TextView uploadDate, uploadTime,referenceID,RoomID,RoomPass,price,matchCategory,reward,matchDate,matchTime;

        public matchViewAdapter(@NonNull View itemView) {
            super(itemView);
            ticketImage=itemView.findViewById(R.id.ticketImage);
            uploadDate=itemView.findViewById(R.id.uploadDate);
            uploadTime=itemView.findViewById(R.id.uploadTime);
            referenceID=itemView.findViewById(R.id.referenceID);
            RoomID=itemView.findViewById(R.id.RoomId);
            RoomPass=itemView.findViewById(R.id.RoomPass);
            price=itemView.findViewById(R.id.Price);
            matchCategory=itemView.findViewById(R.id.matchCategory);
            reward=itemView.findViewById(R.id.reward);
            matchDate=itemView.findViewById(R.id.matchDate);
            matchTime=itemView.findViewById(R.id.matchTime);
        }
    }
}