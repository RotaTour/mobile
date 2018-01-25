package br.ufrpe.projetao.rotatour.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.Usuario;
import br.ufrpe.projetao.rotatour.activities.RoutesActivity;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class PerfilFragment extends Fragment implements View.OnClickListener {

    private TextView textViewId, textViewUsername, textViewEmail;
    private Button bMinhasRotas, bMinhasAvaliacoes, bMinhasFotos, bFavoritos, bConquistas, bConfiguracoes;
    private PerfilFragment.OnFragmentInteractionListener mListener;
    private CircleImageView circleImage;
    private static int IMAGE_PICKER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_perfil);

        //if the user is not logged in
        //starting the login activity

        //TODO SHAREDPREFMANAGER IF USER NOT LOGGED IN
       /* if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        Usuario user = SharedPrefManager.getInstance(getContext()).getUser();
        //textViewId = v.findViewById(R.id.textViewId); token
        textViewUsername = v.findViewById(R.id.textViewUsername);
        textViewEmail = v.findViewById(R.id.textViewEmail);
        circleImage = v.findViewById(R.id.profile);
        final String token = user.getToken();
        String email = SharedPrefManager.getInstance(getContext()).getUser().getEmail();
        textViewEmail.setText(email);
        VolleySingleton.getInstance(getContext()).getUser(email, getContext(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nome = null;
                        try {
                            nome = response.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textViewUsername.setText(nome);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        v.findViewById(R.id.profile).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                /*Context context = getApplicationContext();
                String texto = "Adicionar lógica de escolha de imagem de perfil";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);

                toast.show();*/

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICKER);
                return false;
            }
        });

        v.findViewById(R.id.buttonMinhasAvaliacoes);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              Context context = getApplicationContext();
               String texto = "Minhas Avaliacoes";
               Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
               toast.show();
            }
        });

        //Minhas Anvaliacoes button - replace logic
         v.findViewById(R.id.buttonMinhasRotas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Context context = getApplicationContext();
                String texto = "Minhas Avaliações";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();*/
                Intent intent = new Intent(getActivity(),RoutesActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //Minhas Fotos button - replace logic
      /*  v.findViewById(R.id.buttonMinhasFotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Minhas Fotos";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/

        v.findViewById(R.id.buttonMinhasFotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Minhas Fotos";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Favoritos buttos - replace logic
       v.findViewById(R.id.buttonFavoritos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Favoritos";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

       //Conquistas button - replace logic
        v.findViewById(R.id.buttonConquistas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Conquistas";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Configuracoes button - replace logic
        v.findViewById(R.id.buttonConfiguracoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Configurações";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //getting the current user
        //Usuario user = SharedPrefManager.getInstance(getContext()).getUser();

        //setting the values to the textviews
        //textViewId.setText(String.valueOf(user.getToken()));  token
        //textViewEmail.setText(user.getEmail());

        //when the user presses logout button
        //calling the logout method
        v.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getContext()).logout();
                getActivity().finish();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PerfilFragment.OnFragmentInteractionListener) {
            mListener = (PerfilFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonMinhasRotas:
                Context context = getApplicationContext();
                String texto = "Minhas Rotas";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
                break;
        }


    }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                Log.d(TAG, String.valueOf(bitmap));

                circleImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
