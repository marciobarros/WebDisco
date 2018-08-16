package br.unirio.dsw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.unirio.dsw.model.usuario.Usuario;

@Service
public class FacebookProvider
{
//	private static final String FACEBOOK = "facebook";
	private static final String REDIRECT_LOGIN = "redirect:/login";

	@Autowired
	BaseProvider baseProvider;

	public String getFacebookUserData(Model model, Usuario usuario)
	{
		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();

		if (connectionRepository.findPrimaryConnection(Facebook.class) == null)
			return REDIRECT_LOGIN;
	
		populateUserDetailsFromFacebook(usuario);
		model.addAttribute("loggedInUser", usuario);
		return "user";
	}

	protected void populateUserDetailsFromFacebook(Usuario usuario)
	{
		Facebook facebook = baseProvider.getFacebook();
		User user = facebook.userOperations().getUserProfile();
//		usuario.setUsername(user.getEmail());
		usuario.setNome(user.getFirstName() + " " + user.getLastName());
//		usuario.setImage(user.getCover().getSource());
//		usuario.setProvider(FACEBOOK);
	}
}