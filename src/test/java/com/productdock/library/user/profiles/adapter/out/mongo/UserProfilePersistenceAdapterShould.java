package com.productdock.library.user.profiles.adapter.out.mongo;

import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import com.productdock.library.user.profiles.adapter.out.mongo.mapper.UserProfileEntityMapper;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserProfilePersistenceAdapterShould {

    private static final String FIRST_USER_EMAIL = "::email1::";
    private static final String SECOND_USER_EMAIL = "::email2::";
    private static final Optional<UserProfileEntity> FIRST_USER_PROFILE_ENTITY = Optional.of(mock(UserProfileEntity.class));
    private static final Optional<UserProfileEntity> SECOND_USER_PROFILE_ENTITY = Optional.of(mock(UserProfileEntity.class));
    private static final UserProfile FIRST_USER_PROFILE = mock(UserProfile.class);
    private static final UserProfile SECOND_USER_PROFILE = mock(UserProfile.class);

    @InjectMocks
    private UserProfilePersistenceAdapter userProfilePersistenceAdapter;

    @Mock
    private UserProfileMongoRepository entityRepository;

    @Mock
    private UserProfileEntityMapper mapper;

    @Test
    void findByUserEmailWhenEmailExist() {
        given(entityRepository.findByUserEmail(FIRST_USER_EMAIL)).willReturn(FIRST_USER_PROFILE_ENTITY);
        given(mapper.toDomain(FIRST_USER_PROFILE_ENTITY.get())).willReturn(FIRST_USER_PROFILE);

        var foundUserProfile = userProfilePersistenceAdapter.findByUserEmail(FIRST_USER_EMAIL);

        assertThat(foundUserProfile).isPresent().contains(FIRST_USER_PROFILE);
    }

    @Test
    void findByUserEmailWhenEmailNotExist() {
        given(entityRepository.findByUserEmail(FIRST_USER_EMAIL)).willReturn(Optional.empty());

        var foundUserProfile = userProfilePersistenceAdapter.findByUserEmail(FIRST_USER_EMAIL);

        assertThat(foundUserProfile).isEmpty();
    }

    @Test
    void findByUserEmailsWhenEmailsExist() {
        var userEmails = List.of(FIRST_USER_EMAIL, SECOND_USER_EMAIL);
        var userProfileEntities = List.of(FIRST_USER_PROFILE_ENTITY.get(), SECOND_USER_PROFILE_ENTITY.get());
        given(entityRepository.findByUserEmails(userEmails)).willReturn(userProfileEntities);
        given(mapper.toDomainCollection(userProfileEntities)).willReturn(List.of(FIRST_USER_PROFILE, SECOND_USER_PROFILE));

        var foundUserProfiles = userProfilePersistenceAdapter.findByUserEmails(userEmails);

        assertThat(foundUserProfiles).contains(FIRST_USER_PROFILE);
        assertThat(foundUserProfiles).contains(SECOND_USER_PROFILE);
    }

    @Test
    void findByUserEmailsWhenEmailsNotExist() {
        var userEmails = List.of(FIRST_USER_EMAIL, SECOND_USER_EMAIL);
        given(entityRepository.findByUserEmails(userEmails)).willReturn(new ArrayList<>());

        var foundUserProfiles = userProfilePersistenceAdapter.findByUserEmails(List.of(FIRST_USER_EMAIL, SECOND_USER_EMAIL));

        assertThat(foundUserProfiles).isEmpty();
    }

    @Test
    void save() {
        given(mapper.toEntity(FIRST_USER_PROFILE)).willReturn(FIRST_USER_PROFILE_ENTITY.get());
        given(entityRepository.save(FIRST_USER_PROFILE_ENTITY.get())).willReturn(FIRST_USER_PROFILE_ENTITY.get());
        given(mapper.toDomain(FIRST_USER_PROFILE_ENTITY.get())).willReturn(FIRST_USER_PROFILE);

        var savedUserProfile = userProfilePersistenceAdapter.save(FIRST_USER_PROFILE);

        assertThat(savedUserProfile).isEqualTo(FIRST_USER_PROFILE);
    }

}