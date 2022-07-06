package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.mapper.UserProfileMapper;
import com.productdock.library.user.profiles.application.port.out.persistence.UserAccountPersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserAccountRepository implements UserAccountPersistenceOutPort {

    private UserAccountMongoRepository entityRepository;
    private UserProfileMapper mapper;

    @Override
    public Optional<UserAccount> findByUserEmail(String userEmail) {
        var entity = entityRepository.findByUserEmail(userEmail);
        if (entity.isPresent()) {
            return Optional.of(mapper.toDomain(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public void save(UserAccount userAccount) {
        var entity = mapper.toEntity(userAccount);
        entity.setUserId(UUID.randomUUID().toString());
        entityRepository.save(entity);
    }
}
