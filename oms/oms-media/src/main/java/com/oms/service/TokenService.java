package com.oms.service;

import com.oms.model.VerificationToken;

public interface TokenService {
	VerificationToken createToken(VerificationToken token);

	VerificationToken findByToken(String existingVerificationToken);

	void delete(VerificationToken verificationToken);
}
