package br.ufrpe.projetao.rotatour;

public class Usuario {
    private String email, password, token, provider, providerId, avatar;

    public Usuario(String email, String password, String token, String provider, String providerId, String avatar) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.provider = provider;
        this.providerId = providerId;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getAvatar() {
        return avatar;
    }
}
