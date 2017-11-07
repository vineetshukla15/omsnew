package com.oms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oms.model.VerificationToken;
import com.oms.repository.VerificationTokenRepository;
import com.oms.service.TokenService;
@Component
public class TokenServiceImpl implements TokenService{

	/* The Logger for this class.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The Spring Data repository for Account entities.
	 */
	
	@Autowired
	VerificationTokenRepository tokenRepository;
	@Override
	public VerificationToken createToken(VerificationToken token) {
		// TODO Auto-generated method stub
		return tokenRepository.save(token);
	}

	@Override
	public VerificationToken findByToken(String existingVerificationToken) {
		// TODO Auto-generated method stub
		return tokenRepository.findByToken(existingVerificationToken);
	}

	@Override
	public void delete(VerificationToken verificationToken) {
		tokenRepository.delete(verificationToken.getId());		
	}

}
