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
import com.android.volley.toolbox.Volley;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.RoutesViewHolder;
import br.ufrpe.projetao.rotatour.activities.RouteActivity;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Victor Alexandre on 1/19/2018.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>{

        public static  final String ROUTE_NAME = "routeName";
        public static final String ROUTE_DESCRIPTION = "routeDescription";
        public static final String ROUTE_CREATED = "routeCreated";
        //public static final int ROUTE_ID = 0;
        public static final String ROUTE_ID = "";
        public static final String ROUTE_TAGS = "tags";
        public static final String ROUTE_LIKED = "liked";


    private List<Routes> mList;
        private LayoutInflater mLayoutInflater;
        private Context mContext;

    public RoutesAdapter(Context c, List<Routes> l){
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RoutesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_route, parent, false);
       // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_route, parent, false);
        RoutesViewHolder routesViewHolder = new RoutesViewHolder(v);
        return routesViewHolder;
    }


    public void onBindViewHolder(final RoutesViewHolder holder, final int position) {

        holder.routeName.setText(mList.get(position).getName());
        holder.routeDescription.setText(mList.get(position).getDescription());
        holder.createdDate.setText(mList.get(position).getCreated());

        holder.tgRouteTags.setTags(mList.get(position).getTags());
        holder.routeLiked.setLiked(mList.get(position).isLiked());

        holder.routeLiked.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                VolleySingleton.getInstance(mContext).toggleRouteLike(mContext, String.valueOf(mList.get(position).getId()),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("olimpicus", "response "+ response);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error != null)
                                    Log.d("olimpicus", "error "+ error.toString());

                            }
                        });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                VolleySingleton.getInstance(mContext).toggleRouteLike(mContext, String.valueOf(mList.get(position).getId()),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("olimpicus", "response "+ response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error != null)
                                    Log.d("olimpicus", "error "+ error.toString());
                            }
                        });
            }
        });


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routes routes = mList.get(position);
                Intent route_data= new Intent(v.getContext(), RouteActivity.class);
                route_data.putStringArrayListExtra(ROUTE_TAGS, (ArrayList<String>) mList.get(position).getTags());
                route_data.putExtra(ROUTE_NAME,routes.getName());
                route_data.putExtra(ROUTE_DESCRIPTION,routes.getDescription());
                route_data.putExtra(ROUTE_CREATED,routes.getCreated());
                route_data.putExtra(ROUTE_ID, String.valueOf(routes.getId()));
                route_data.putExtra(ROUTE_LIKED, holder.routeLiked.isLiked());
                //route_data.putExtra(ROUTE_ID, String.valueOf(routes.getId()));
                v.getContext().startActivity(route_data);
            }
        });
        // criar holder para ID

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder {
        public TextView routeName;
        public  TextView routeDescription;
        public  TextView createdDate;
        public LinearLayout linearLayout;
        public TagGroup tgRouteTags;
        public LikeButton routeLiked;
        public TextView routeID;

        public RoutesViewHolder(View itemView) {
            super(itemView);

            tgRouteTags = itemView.findViewById(R.id.route_ViewTag);
            routeName = itemView.findViewById(R.id.textViewRouteName);
            routeDescription =  itemView.findViewById(R.id.textViewRouteDescription);
            createdDate = itemView.findViewById(R.id.textViewCreated);
            linearLayout = itemView.findViewById(R.id.linearLayoutCardview);
            routeLiked = itemView.findViewById(R.id.btnRouteLike);
            //adicionar view para ID

        }
    }
}
