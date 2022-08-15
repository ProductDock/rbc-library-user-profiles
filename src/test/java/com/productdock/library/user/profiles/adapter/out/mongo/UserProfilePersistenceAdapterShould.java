package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import com.productdock.library.user.profiles.adapter.out.mongo.mapper.UserProfileMapper;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserProfilePersistenceAdapterShould {

    private static final String USER_EMAIL = "::email::";
    private static final Optional<UserProfileEntity> USER_PROFILE_ENTITY = Optional.of(mock(UserProfileEntity.class));
    private static final UserProfile USER_PROFILE = mock(UserProfile.class);

    @InjectMocks
    private UserProfilePersistenceAdapter userProfilePersistenceAdapter;

    @Mock
    private UserProfileMongoRepository entityRepository;

    @Mock
    private UserProfileMapper mapper;

    @Test
    void findByUserEmailWhenEmailExist() {
        given(entityRepository.findByUserEmail(USER_EMAIL)).willReturn(USER_PROFILE_ENTITY);
        given(mapper.toDomain(USER_PROFILE_ENTITY.get())).willReturn(USER_PROFILE);

        var foundUserProfile = userProfilePersistenceAdapter.findByUserEmail(USER_EMAIL);

        assertThat(foundUserProfile).isPresent().contains(USER_PROFILE);
    }

    @Test
    void findByUserEmailWhenEmailNotExist() {
        given(entityRepository.findByUserEmail(USER_EMAIL)).willReturn(Optional.empty());

        var foundUserProfile = userProfilePersistenceAdapter.findByUserEmail(USER_EMAIL);

        assertThat(foundUserProfile).isEmpty();
    }

    @Test
    void save() {
        given(mapper.toEntity(USER_PROFILE)).willReturn(USER_PROFILE_ENTITY.get());
        given(entityRepository.save(USER_PROFILE_ENTITY.get())).willReturn(USER_PROFILE_ENTITY.get());
        given(mapper.toDomain(USER_PROFILE_ENTITY.get())).willReturn(USER_PROFILE);

        var savedUserProfile = userProfilePersistenceAdapter.save(USER_PROFILE);

        assertThat(savedUserProfile).isEqualTo(USER_PROFILE);
    }

}