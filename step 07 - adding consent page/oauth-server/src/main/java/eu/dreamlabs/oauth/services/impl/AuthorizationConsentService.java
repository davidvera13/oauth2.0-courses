package eu.dreamlabs.oauth.services.impl;

import eu.dreamlabs.oauth.entities.oauth.AuthorizationConsentEntity;
import eu.dreamlabs.oauth.repositories.AuthorizationConsentRepository;
import eu.dreamlabs.oauth.repositories.ClientRepository;
import eu.dreamlabs.oauth.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorizationConsentService implements OAuth2AuthorizationConsentService {
	private final AuthorizationConsentRepository authorizationConsentRepository;
	private final ClientService clientService;
	private final ClientRepository clientRepository;

	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		this.authorizationConsentRepository.save(toEntity(authorizationConsent));
	}

	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
				authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
	}

	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		return this.authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(
				registeredClientId, principalName).map(this::toObject).orElse(null);
	}

	private OAuth2AuthorizationConsent toObject(AuthorizationConsentEntity authorizationConsent) {
		String registeredClientId = authorizationConsent.getRegisteredClientId();
		RegisteredClient registeredClient = this.clientService.toRegisteredClient(this.clientRepository.findByClientId(registeredClientId).orElseThrow(() ->
				new RuntimeException("No client found with clientId: " + registeredClientId)));
		if (registeredClient == null)
			throw new DataRetrievalFailureException(
					"The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
		OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
				registeredClientId, authorizationConsent.getPrincipalName());
		if (authorizationConsent.getAuthorities() != null) {
			for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities()))
				builder.authority(new SimpleGrantedAuthority(authority));
		}
		return builder.build();
	}

	private AuthorizationConsentEntity toEntity(OAuth2AuthorizationConsent authorizationConsent) {
		AuthorizationConsentEntity entity = new AuthorizationConsentEntity();
		entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
		entity.setPrincipalName(authorizationConsent.getPrincipalName());

		Set<String> authorities = new HashSet<>();
		for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));
		return entity;
	}
}