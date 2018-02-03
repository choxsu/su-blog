package com.choxsu.admin.shiro;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chox su
 * @date 2018/02/03 11:24
 */
public class JdbcRealmExt extends JdbcRealm {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    /**
     * The set query used to retrieve account data for the user.
     */
    private static final String AUTHENTICATION_QUERY = "select password from users where username = ?";

    /**
     * The set query used to retrieve account data for the user when {@link #saltStyle} is COLUMN.
     */
    private static final String SALTED_AUTHENTICATION_QUERY = "select password, password_salt from users where username = ?";

    /**
     * The set query used to retrieve the roles that apply to a user.
     */
    private static final String USER_ROLES_QUERY = "select role_name from user_roles where username = ?";

    /**
     * The set query used to retrieve permissions that apply to a particular role.
     */
    private static final String PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";

    private static final Logger log = LoggerFactory.getLogger(JdbcRealmExt.class);

    public JdbcRealmExt() {

    }

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private String authenticationQuery = AUTHENTICATION_QUERY;

    private String userRolesQuery = USER_ROLES_QUERY;

    private String permissionsQuery = PERMISSIONS_QUERY;

    private SaltStyle saltStyle = SaltStyle.NO_SALT;


    @Override
    public void setAuthenticationQuery(String authenticationQuery) {
        this.authenticationQuery = authenticationQuery;
    }

    @Override
    public void setUserRolesQuery(String userRolesQuery) {
        this.userRolesQuery = userRolesQuery;
    }

    @Override
    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = permissionsQuery;
    }

    @Override
    public void setSaltStyle(SaltStyle saltStyle) {
        this.saltStyle = saltStyle;
    }

}
