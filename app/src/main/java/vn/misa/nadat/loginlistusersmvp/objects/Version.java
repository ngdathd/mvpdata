package vn.misa.nadat.loginlistusersmvp.objects;

public class Version {
    private String id;
    private String version;

    public Version(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
