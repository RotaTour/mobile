package br.ufrpe.projetao.rotatour;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public ImageView coverImageView;
    public ImageView likeImageView;
    public ImageView shareImageView;

    public MyViewHolder(View v) {
        super(v);
        titleTextView = v.findViewById(R.id.titleTextView);
        coverImageView = v.findViewById(R.id.coverImageView);
        likeImageView = v.findViewById(R.id.likeImageView);
        shareImageView = v.findViewById(R.id.shareImageView);
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int)likeImageView.getTag();
                if( id == R.drawable.ic_like){

                    likeImageView.setTag(R.drawable.ic_like);
                    likeImageView.setImageResource(R.drawable.ic_like);

                    Toast.makeText(v.getContext(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                }else{
                    likeImageView.setTag(R.drawable.ic_like);
                    likeImageView.setImageResource(R.drawable.ic_like);
                    Toast.makeText(v.getContext() ,titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + v.getResources().getResourcePackageName(coverImageView.getId())
                        + '/' + "drawable" + '/' + v.getResources().getResourceEntryName((int)coverImageView.getTag()));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                shareIntent.setType("image/jpeg");
                //startActivity(Intent.createChooser(shareIntent, "Texto")); //v.getResources().getText(R.string.send_to)));
            }
        });
    }
}