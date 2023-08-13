package com.example.userapp.Orders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userapp.R;
import com.example.userapp.Tickets.Match_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.matchViewAdapter> {
    private Context context;
    private List<Match_Data> list;

    public Order_Adapter(Context context, List<Match_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public matchViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
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

        holder.price.setText("Price: "+currentItem.getMatchCharge());
        holder.matchCategory.setText("Map: "+currentItem.getMatchCategory());
        holder.reward.setText("₹"+currentItem.getReward());

        Picasso.get().load(currentItem.getImageUrl()).into(holder.ticketImage);

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this ticket");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = auth.getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid()).child(currentItem.getReferID());
                        reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(), "Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Something Went Wrong",Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = null;
                try{
                    dialog=builder.create();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(dialog!=null){
                    dialog.show();
                }
            }
        });
        /*holder.mTickets.setOnClickListener(new View.OnClickListener() {
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
        });*/

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Matches").child(currentItem.getMatchDuration()).child(currentItem.getReferID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String roomID = snapshot.child("room_Id").getValue(String.class);
                String roomPass = snapshot.child("room_pass").getValue(String.class);

                holder.RoomID.setText("Room ID: "+currentItem.getRoom_Id());
                holder.RoomPass.setText("Password: "+currentItem.getRoom_pass());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.chooseSquadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Choose_Squad_Activity.class);
                intent.putExtra("ref_no",currentItem.getReferID());
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
        private Button removeBtn,chooseSquadBtn;
        //private TextView uploadDate,uploadTime;
        //private CardView mTickets;

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
            //mTickets = itemView.findViewById(R.id.mTickets);

            removeBtn = itemView.findViewById(R.id.removeBtn);
            chooseSquadBtn = itemView.findViewById(R.id.chooseSquadBtn);
        }
    }
}