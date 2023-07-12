package com.example.userapp.Orders;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.PaymentResultListener;

import com.example.userapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class Buy_Tickets_Activity extends AppCompatActivity implements PaymentResultListener{
    private ImageView ticketImage;
    private TextView referenceID,RoomID,RoomPass,matchCategory,reward,matchDate,matchTime,price;
    private Button payBtn;

    private String MDate,MTime,MCategory,Price,RID,RP,Reward,URI,ref_no;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_tickets);
        Checkout.preload(getApplicationContext());

        //uploadDate=itemView.findViewById(R.id.uploadDate);
        //uploadTime=itemView.findViewById(R.id.uploadTime);
        referenceID=findViewById(R.id.referenceID);
        RoomID=findViewById(R.id.RoomId);
        RoomPass=findViewById(R.id.RoomPass);
        price=findViewById(R.id.Price);
        matchCategory=findViewById(R.id.matchCategory);
        reward=findViewById(R.id.reward);
        matchDate=findViewById(R.id.matchDate);
        matchTime=findViewById(R.id.matchTime);
        ticketImage=findViewById(R.id.ticketImage);
        payBtn=findViewById(R.id.payBtn);

        MDate = getIntent().getStringExtra("MDate");
        MTime = getIntent().getStringExtra("MTime");
        MCategory = getIntent().getStringExtra("MCategory");
        Price = getIntent().getStringExtra("Price");
        RID = getIntent().getStringExtra("RID");
        RP = getIntent().getStringExtra("RP");
        Reward = getIntent().getStringExtra("Reward");
        URI = getIntent().getStringExtra("URI");
        ref_no = getIntent().getStringExtra("ref_no");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        matchCategory.setText(MCategory);
        matchTime.setText("Match Time: "+MTime);
        matchDate.setText("Match Date: "+MDate);
        //holder.uploadTime.setText(currentItem.getUploadTime());
        //holder.uploadDate.setText(currentItem.getUploadDate());
        referenceID.setText("Reference ID: "+ref_no);
        RoomID.setText("Room ID: "+RID);
        RoomPass.setText("Password: "+RP);
        //holder.price.setText("Price: "+currentItem.getMatchCharge());
        matchCategory.setText("Map: "+MCategory);
        reward.setText("₹"+Reward);
        price.setText("Entry Fees: "+Price);

        Picasso.get().load(URI).into(ticketImage);
        payBtn.setText("Pay ₹"+Price);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
    }

    private void makePayment(){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_vyDumOijh6nLR7");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.cart);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "BGMI User App");
            options.put("description", ref_no+matchCategory);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(Price)*100);//pass amount in currency subunits
            options.put("prefill.email", user.getEmail());
            //options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);
        }
        catch(Exception e) {
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Buy_Tickets_Activity.this,"Payment Successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Buy_Tickets_Activity.this,"Payment Failed",Toast.LENGTH_SHORT).show();
    }
}