package com.example.auth.oauth;

import java.util.Scanner;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class OAuthCLient4Github {

	private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
	public static void main(String[] args) throws Exception {
		try{
			OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.GITHUB)
					.setClientId("920450fb24a203a4131d")
					.setRedirectURI("http://github.com")
					.setScope("repo, user")
					.setState("state")
					.buildQueryMessage();
			System.out.println(request.getLocationUri());
	
			System.out.println(
					"Now enter the OAuth code you have received in redirect uri (browser 'http://localhost/?code=XXX' ");
			Scanner in = new Scanner(System.in);
			String authcode = in.nextLine();
			in.close();
			request = OAuthClientRequest.tokenProvider(OAuthProviderType.GITHUB)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId("920450fb24a203a4131d")
					.setClientSecret("e637c59a7ec0adc4ddc109c441a771869b52e064")
					.setRedirectURI("http://github.com")
					.setCode(authcode)
					.buildBodyMessage();
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

	        GitHubTokenResponse response = oAuthClient.accessToken(request, OAuth.HttpMethod.POST, GitHubTokenResponse.class);
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
