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
    public static final String URL_POSTS = ROOT_URL + "posts";
    public static final String URL_NEW_POST = ROOT_URL + "posts/new";
    public static final String URL_PUB_LIKE = ROOT_URL + "posts/like";
    public static final String URL_ROUTE_LIKE = ROOT_URL + "routes/like";
    public static final String URL_USER= ROOT_URL + "users/";


}
