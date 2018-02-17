package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Favorites;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.activities.FavoriteActivity;
import br.ufrpe.projetao.rotatour.activities.RouteActivity;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;
import me.gujun.android.taggroup.TagGroup;

import static br.ufrpe.projetao.rotatour.adapters.RoutesAdapter.ROUTE_TAGS;

/**
 * Created by Victor Alexandre on 2/12/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {


    public static  final String ROUTE_NAME = "routeName";
    public static final String ROUTE_DESCRIPTION = "routeDescription";
    public static final String ROUTE_CREATED = "routeCreated";
    //public static final int ROUTE_ID = 0;
    public static final String ROUTE_ID = "";



    private List<Favorites> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public FavoritesAdapter(Context c, List<Favorites> l){
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_favorites, parent, false);
        // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_route, parent, false);
        FavoritesAdapter.FavoritesViewHolder favoritesViewHolder = new FavoritesAdapter.FavoritesViewHolder(v);
        return favoritesViewHolder;
    }


    public void onBindViewHolder(final FavoritesAdapter.FavoritesViewHolder holder, final int position) {


        holder.favoriteName.setText(mList.get(position).getName());
        holder.favoriteDescription.setText(mList.get(position).getDescription());
        holder.createdDate.setText(mList.get(position).getCreated());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorites favorites = mList.get(position);
                Intent route_data= new Intent(v.getContext(), FavoriteActivity.class);
                route_data.putExtra(ROUTE_NAME,favorites.getName());
                route_data.putExtra(ROUTE_DESCRIPTION,favorites.getDescription());
                route_data.putExtra(ROUTE_CREATED,favorites.getCreated());
                route_data.putExtra(ROUTE_ID, String.valueOf(favorites.getId()));
                //route_data.putExtra(ROUTE_ID, String.valueOf(routes.getId()));
                v.getContext().startActivity(route_data);
            }
        });
        // criar holder para ID

    }


    public int getItemCount() {
        return mList.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public TextView favoriteName;
        public  TextView favoriteDescription;
        public  TextView createdDate;
        public LinearLayout linearLayout;
        public TextView routeID;

        public FavoritesViewHolder(View itemView) {
            super(itemView);


            favoriteName = itemView.findViewById(R.id.textViewRouteName);
            favoriteDescription =  itemView.findViewById(R.id.textViewRouteDescription);
            createdDate = itemView.findViewById(R.id.textViewCreated);
            linearLayout = itemView.findViewById(R.id.linearLayoutCardview);
            //adicionar view para ID

        }
    }

}
