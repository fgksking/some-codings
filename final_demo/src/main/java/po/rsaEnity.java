package po;

public class rsaEnity {
    private String username;
    private String privateKey;
    private String publicKey;

    public rsaEnity(String username, String privateKey, String publicKey) {
        this.username = username;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public rsaEnity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
