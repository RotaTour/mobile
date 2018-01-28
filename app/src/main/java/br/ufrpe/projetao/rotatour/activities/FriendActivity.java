package br.ufrpe.projetao.rotatour.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.FriendsAdapter;

public class FriendActivity extends AppCompatActivity {
    private Context mContext;
   ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Intent intent = getIntent();
        final String friendName = intent.getStringExtra(FriendsAdapter.FRIEND_NAME);
        final String friendUsername = intent.getStringExtra(FriendsAdapter.FRIEND_USERNAME);
        final String friendEmail = intent.getStringExtra(FriendsAdapter.FRIEND_EMAIL);
        final String friendAvatar = intent.getStringExtra(FriendsAdapter.FRIEND_AVATAR);

       TextView fname = (TextView)findViewById(R.id.textViewDetailsFriendName);
       TextView fUname = (TextView)findViewById(R.id.textViewDetailsFriendUsername);
       TextView fEmail = (TextView)findViewById(R.id.textViewDetailsFriendEmail);
       photo = (ImageView)findViewById(R.id.friendProfilePhoto);

       Picasso.with(mContext).load(friendAvatar).into(photo);
       fname.setText(friendName);
       fUname.setText(friendUsername);
       fEmail.setText(friendEmail);

    }
}
