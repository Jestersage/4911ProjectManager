package ca.bcit.info.pms.model;


public class LoginCredential extends Credential {
    public LoginCredential() {
        super();
    }

    public LoginCredential(String username, String password) {
        super(username, password);
    }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
