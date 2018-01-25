package br.ufrpe.projetao.rotatour.requests_volley;

import br.ufrpe.projetao.rotatour.R;

public class URLs {
    private static final String ROOT_URL = "https://rotatourapi.herokuapp.com/api/";
    private static final String ROOT_URL_AI = "https://rotatourapi.herokuapp.com/ai/";

    public static final String URL_REGISTER = ROOT_URL + "register";
    public static final String URL_REGISTER_SOCIAL= ROOT_URL + "social/register";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_ROUTES= ROOT_URL + "routes";
    public static final String URL_ADD_ROUTE= ROOT_URL + "routes/addToRoute";
    public static final String URL_PUBS = ROOT_URL_AI + "statuses";
    public static final String URL_USERS = ROOT_URL_AI + "users";


}
