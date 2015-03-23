package ca.bcit.info.pms.model;


public class LoginCredential extends Credential {
    private String username;
    private String password;
    
    public LoginCredential() {
        super();
    }

    public LoginCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
