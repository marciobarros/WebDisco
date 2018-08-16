package br.unirio.dsw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.unirio.dsw.model.usuario.Usuario;

@Service
public class GoogleProvider
{
	private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/login";
//	private static final String GOOGLE = "google";

	@Autowired
	BaseProvider socialLoginBean;

	public String getGoogleUserData(Model model, Usuario usuario)
	{
		ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
	
		if (connectionRepository.findPrimaryConnection(Google.class) == null)
			return REDIRECT_CONNECT_GOOGLE;

		populateUserDetailsFromGoogle(usuario);
		model.addAttribute("loggedInUser", usuario);
		return "user";
	}

	protected void populateUserDetailsFromGoogle(Usuario usuario)
	{
		Google google = socialLoginBean.getGoogle();
		Person googleUser = google.plusOperations().getGoogleProfile();
//		usuario.setEmail(googleUser.getAccountEmail());
		usuario.setNome(googleUser.getGivenName() + " " + googleUser.getFamilyName());
//		usuario.setImage(googleUser.getImageUrl());
//		usuario.setProvider(GOOGLE);
	}
}