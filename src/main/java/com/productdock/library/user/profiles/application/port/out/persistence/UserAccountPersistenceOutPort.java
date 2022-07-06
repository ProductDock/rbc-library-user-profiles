package com.productdock.library.user.profiles.application.port.out.persistence;

import com.productdock.library.user.profiles.domain.UserAccount;

import java.util.Optional;

public interface UserAccountPersistenceOutPort {
    Optional<UserAccount> findByUserEmail(String userEmail);

    void save(UserAccount userAccount);
}
