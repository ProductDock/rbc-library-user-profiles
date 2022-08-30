package com.productdock.library.user.profiles.config.dbmigrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.productdock.library.user.profiles.adapter.out.mongo.UserProfileMongoRepository;
import com.productdock.library.user.profiles.adapter.out.mongo.entity.UserProfileEntity;
import com.productdock.library.user.profiles.config.coverage.Generated;

import java.util.UUID;

@Generated
@ChangeLog(order = "001")
public class DatabaseInitChangeLog {

    private static final String ROLE_USER = "ROLE_USER";

    @ChangeSet(order = "001", id = "init_user_profiles", author = "pd")
    public void initUserProfiles(UserProfileMongoRepository userProfileMongoRepository) {
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Suzana Bogojevic")
                .userEmail("suzana.bogojevic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmmumPd4czxsHLImVZQ4-FwA2x-sewzKjTQeopzi=s50-c-k-no")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Nemanja Vasiljevic")
                .userEmail("nemanja.vasiljevic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a-/AFdZucr9gSCGzFhsHfHPBqOfhHEtjufEmZi8l5uZJL4B=s64-p-k-rw-no")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Nikola Lajic")
                .userEmail("nikola.lajic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a-/AFdZucoKfQJIh5wA4ocd6eTLEbrlY-f4LyuxZBsG-8XN=s128-p-k-rw-no")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Bojan Ristic")
                .userEmail("bojan.ristic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmkkek3-3Rf0J6IJJPyZnlMAvN7NSED1RqEOYyE3=s64-p-k-rw-no-mo")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Bojana Armacki")
                .userEmail("bojana.armacki@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmnZpWGsRLg5ch8bl40Bh9MoVigFP_RCbpHfgTdN=s64-p-k-rw-no-mo")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Jaroslav Slivka")
                .userEmail("jaroslav.slivka@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmlMbeIvxoYkuVHHMz6G9fq6mOYEYEP7Gir0uacN=s64-p-k-rw-no-mo")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Andrija Vujasinovic")
                .userEmail("andrija.vujasinovic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a-/AFdZucr9Oaa7X_vELNV8gFWVJqPCR3T7En5KQGpcDaJb=s64-p-k-rw-no")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Jovanka Bobic")
                .userEmail("jovanka.bobic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmlmNnKGiuVw7SM7jihCnxMyzBviQ_WwqrEHlMeB=s72-p-k-rw-no-mo")
                .role(ROLE_USER)
                .build());
        userProfileMongoRepository.save(UserProfileEntity.builder()
                .userId(UUID.randomUUID().toString())
                .userFullName("Adriana Martinovic")
                .userEmail("adriana.martinovic@productdock.com")
                .userProfilePicture("https://lh3.googleusercontent.com/a/AItbvmmdkxgEC7ANOsewcp3LTHKuOvuMN-AFELVNksfB=s72-p-k-rw-no")
                .role(ROLE_USER)
                .build());
    }
}
