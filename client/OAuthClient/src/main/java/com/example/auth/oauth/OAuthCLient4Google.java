package com.example.auth.oauth;

import java.util.Scanner;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class OAuthCLient4Google {

	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	public static void main(String[] args) throws Exception {
		try{
			OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.GOOGLE)
					.setClientId("223401689130-b7gk7hro1vd9cpjuhenviiltckup28te.apps.googleusercontent.com")
					.setRedirectURI("http://github.com")
					.setScope("email profile")
					.setResponseType("code")
					.buildQueryMessage();
			System.out.println(request.getLocationUri());
	
			System.out.println(
					"Now enter the OAuth code you have received in redirect uri (browser 'http://localhost/?code=XXX' ");
			Scanner in = new Scanner(System.in);
			String authcode = in.nextLine();
			in.close();
			request = OAuthClientRequest.tokenProvider(OAuthProviderType.GOOGLE)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId("223401689130-b7gk7hro1vd9cpjuhenviiltckup28te.apps.googleusercontent.com")
					.setClientSecret("Y-g5affvFtrKo-CAuoqOMnaq")
					.setRedirectURI("http://github.com")
					.setCode(authcode)
					.buildBodyMessage();
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);
			System.out.println("\nAccess Token: " + response.getAccessToken() + "\nExpires in: " + response.getExpiresIn());
			// Use the access token to retrieve the data.
			OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(PROTECTED_RESOURCE_URL)
					.setAccessToken(response.getAccessToken()).buildQueryMessage();
			OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET,
					OAuthResourceResponse.class);
			if (resourceResponse.getResponseCode() == 200) {
				System.out.println(resourceResponse.getBody());
			} else {
				System.err.println("Could not access resource: " + resourceResponse.getResponseCode() + " "
						+ resourceResponse.getBody());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
