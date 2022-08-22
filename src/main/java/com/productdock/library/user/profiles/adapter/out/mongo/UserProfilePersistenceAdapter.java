package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.mapper.UserProfileEntityMapper;
import com.productdock.library.user.profiles.application.port.out.persistence.UserProfilePersistenceOutPort;
import com.productdock.library.user.profiles.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserProfilePersistenceAdapter implements UserProfilePersistenceOutPort {

    private UserProfileMongoRepository entityRepository;
    private UserProfileEntityMapper mapper;

    @Override
    public Optional<UserProfile> findByUserEmail(String userEmail) {
        var entity = entityRepository.findByUserEmail(userEmail);
        return entity.map(userProfile -> mapper.toDomain(userProfile));
    }

    @Override
    public Collection<UserProfile> findByUserEmails(Collection<String> userEmails) {
        var entities = entityRepository.findByUserEmails(userEmails);
        return mapper.toDomainCollection(entities);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        var entity = mapper.toEntity(userProfile);
        entity.setUserId(UUID.randomUUID().toString());
        return mapper.toDomain(entityRepository.save(entity));
    }
}
