package com.example.userapp.Tickets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userapp.Orders.Buy_Tickets_Activity;
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
        holder.matchTime.setText("Match Time: "+currentItem.getMatchTime());
        holder.matchDate.setText("Match Date: "+currentItem.getMatchDate());
        //holder.uploadTime.setText(currentItem.getUploadTime());
        //holder.uploadDate.setText(currentItem.getUploadDate());
        holder.referenceID.setText("Reference ID: "+currentItem.getReferID());
        holder.RoomID.setText("Room ID: "+currentItem.getRoom_Id());
        holder.RoomPass.setText("Password: "+currentItem.getRoom_pass());
        holder.price.setText("Price: "+currentItem.getMatchCharge());
        holder.matchCategory.setText("Map: "+currentItem.getMatchCategory());
        holder.reward.setText("₹"+currentItem.getReward());

        Picasso.get().load(currentItem.getImageUrl()).into(holder.ticketImage);

        holder.mTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Buy_Tickets_Activity.class);
                intent.putExtra("MDate",currentItem.getMatchDate());
                intent.putExtra("MTime",currentItem.getMatchTime());
                intent.putExtra("MCategory",currentItem.getMatchCategory());
                intent.putExtra("Price",currentItem.getMatchCharge());
                intent.putExtra("RID",currentItem.getRoom_Id());
                intent.putExtra("RP",currentItem.getRoom_pass());
                intent.putExtra("ref_no",currentItem.getReferID());
                intent.putExtra("Reward",currentItem.getReward());
                intent.putExtra("URI",currentItem.getImageUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class matchViewAdapter extends RecyclerView.ViewHolder {
        private ImageView ticketImage;
        private TextView referenceID,RoomID,RoomPass,matchCategory,reward,matchDate,matchTime,price;
        //private TextView uploadDate,uploadTime;
        private CardView mTickets;

        public matchViewAdapter(@NonNull View itemView) {
            super(itemView);
            ticketImage=itemView.findViewById(R.id.ticketImage);
            //uploadDate=itemView.findViewById(R.id.uploadDate);
            //uploadTime=itemView.findViewById(R.id.uploadTime);
            referenceID=itemView.findViewById(R.id.referenceID);
            RoomID=itemView.findViewById(R.id.RoomId);
            RoomPass=itemView.findViewById(R.id.RoomPass);
            price=itemView.findViewById(R.id.Price);
            matchCategory=itemView.findViewById(R.id.matchCategory);
            reward=itemView.findViewById(R.id.reward);
            matchDate=itemView.findViewById(R.id.matchDate);
            matchTime=itemView.findViewById(R.id.matchTime);
            mTickets = itemView.findViewById(R.id.mTickets);
        }
    }
}