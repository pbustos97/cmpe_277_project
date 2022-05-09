package com.noidea.hootel.Cognito;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.R;

public class CognitoSettings {
    private static final String TAG = CognitoSettings.class.getSimpleName();

    private String userPoolId;
    private String clientId;
    private String clientSecret;
    private Regions cognitoRegion = Regions.US_WEST_2;

    private CognitoUserPool cognitoUserPool;
    private CognitoUserAttributes cognitoUserAttributes;

    private Context context;
    private String userPassword;

    private AuthenticationHandler authenticationHandler;

    public CognitoSettings(Context ctx) {
        this.context = ctx;
        userPoolId = "us-west-2_QIwRmEuzt";
        clientId = "7o9ehqd2u5ars02an5ajg08jaf";
        clientSecret = "qufi54a043ghgct19mcrn0qs3trr9o5peiek0aq534menvsa0qa";
        cognitoUserPool = new CognitoUserPool(this.context, this.userPoolId, this.clientId, this.clientSecret, this.cognitoRegion);
        cognitoUserAttributes = new CognitoUserAttributes();
        authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                Log.d(TAG, userSession.getIdToken().getJWTToken());
                User.setToken(userSession.getIdToken().getJWTToken());
                User.setUserPoolId(userPoolId);

            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                AuthenticationDetails authDetails = new AuthenticationDetails(userId, userPassword, null);
                authenticationContinuation.setAuthenticationDetails(authDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {
            }

            @Override
            public void onFailure(Exception exception) {
                User.setUserId(null);
                User.setToken(null);
            }
        };
    }

    public void login(String userId, String password) {
        CognitoUser user = cognitoUserPool.getUser(userId);
        this.userPassword = password;
        User.setUserId(userId);
        user.getSessionInBackground(authenticationHandler);
    }
}
