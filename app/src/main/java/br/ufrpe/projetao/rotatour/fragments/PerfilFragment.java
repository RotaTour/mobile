package br.ufrpe.projetao.rotatour.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.Usuario;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    private TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    private Button bMinhasRotas, bMinhasAvaliacoes, bMinhasFotos, bFavoritos, bConquistas, bConfiguracoes;


    private PerfilFragment.OnFragmentInteractionListener mListener;

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


        //textViewId = v.findViewById(R.id.textViewId); token
        textViewUsername = v.findViewById(R.id.textViewUsername);
        textViewEmail = v.findViewById(R.id.textViewEmail);
        textViewGender = v.findViewById(R.id.textViewGender);




       v.findViewById(R.id.buttonMinhasRotas).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Context context = getApplicationContext();
               String texto = "Minhas Rotas";
               Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
               toast.show();
           }
       });

        //Minhas Anvaliacoes button - replace logic
         v.findViewById(R.id.buttonMinhasAvaliacoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String texto = "Minhas Avaliações";
                Toast toast = Toast.makeText(context,texto,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Minhas Fotos button - replace logic
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
        Usuario user = SharedPrefManager.getInstance(getContext()).getUser();

        //setting the values to the textviews
        //textViewId.setText(String.valueOf(user.getToken()));  token
        textViewEmail.setText(user.getEmail());

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
