package es.uvlive.model;

public class UVLiveModel {
    
    private static UVLiveModel sUVLiveModel;
    
    private TutorialCatalog catalogoTutorias;
    private SessionManager sessionManager;

    public static UVLiveModel getInstance() {
        if (sUVLiveModel == null) {
            sUVLiveModel = new UVLiveModel();
        }
        return sUVLiveModel;
    }

    public UVLiveModel() {
        catalogoTutorias = new TutorialCatalog();
        sessionManager = new SessionManager();
    }

    public void actualizarCurso() {
            // TODO - implement UVLiveModel.actualizarCurso
            throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param userName
     * @param password
     * @param loginType
     */
    public String login(String userName, String password, String loginType, String key) {
        return sessionManager.login(userName, password, loginType, key);
    }
    
    /**
     * Logout
     * @param token
     */
    public void logout(String token) {
        sessionManager.logout(token);
    }

    /**
     * 
     * @param key
     */
    public boolean containsUser(String key) {
            // TODO - implement UVLiveModel.containsUser
            throw new UnsupportedOperationException();
    }

    public void banearAlumno() {
            // TODO - implement UVLiveModel.banearAlumno
            throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param key
     * @param idAlumno
     */
    public void banearAlumno(String key, int idAlumno) {
            // TODO - implement UVLiveModel.banearAlumno
            throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param key
     * @param traderName
     */
    public void validarNombreComerciante(String key, String traderName) {
            // TODO - implement UVLiveModel.validarNombreComerciante
            throw new UnsupportedOperationException();
    }
    
    // Methods added manually
    
    public User getUser(String key) {
        return sessionManager.getUser(key);
    }

}