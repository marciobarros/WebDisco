package br.unirio.dsw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.unirio.dsw.model.usuario.Usuario;

@Service
public class LinkedinProvider
{
//	private static final String LINKED_IN = "linkedIn";
	private static final String REDIRECT_LOGIN = "redirect:/login";

	@Autowired
	BaseProvider socialLoginBean;

	public String getLinkedInUserData(Model model, Usuario usuario)
	{
		ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
		
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null)
			return REDIRECT_LOGIN;

		populateUserDetailsFromLinkedIn(usuario);
		model.addAttribute("loggedInUser", usuario);
		return "user";
	}

	private void populateUserDetailsFromLinkedIn(Usuario usuario)
	{
		LinkedIn linkedIn = socialLoginBean.getLinkedIn();
		LinkedInProfileFull linkedInUser = linkedIn.profileOperations().getUserProfileFull();
//		usuario.setEmail(linkedInUser.getEmailAddress());
		usuario.setNome(linkedInUser.getFirstName() + " " + linkedInUser.getLastName());
//		usuario.setImage(linkedInUser.getProfilePictureUrl());
//		usuario.setProvider(LINKED_IN);
	}
}