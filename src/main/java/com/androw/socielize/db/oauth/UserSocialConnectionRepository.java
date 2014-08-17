package com.androw.socielize.db.oauth;

import com.androw.socielize.model.oauth.UserSocialConnection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Androw on 17/08/2014.
 */
public interface UserSocialConnectionRepository extends MongoRepository<UserSocialConnection, String> {
    List<UserSocialConnection> findByUserId(String userId);

    List<UserSocialConnection> findByUserIdAndProviderId(String userId, String providerId);

    List<UserSocialConnection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);

    UserSocialConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    List<UserSocialConnection> findByProviderIdAndProviderUserIdIn(String providerId, Collection<String> providerUserIds);
}