package org.mitre.openid.connect.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.ClientDetailsEntity.SubjectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * @author jzuriaga
 *
 */
public class UserInfoTest {
    
    private static final long USER_ID = Math.abs(new Random().nextLong());
    private static final String USER_SUB = UUID.randomUUID().toString();
    private static final String USER_NAME = "dummy@example.com";
    
    private static final String CLIENT_ID1 = "publicClient-1-313124";
    private static final String CLIENT_ID2 = "publicClient-2-4109312";
    private static final String AUTHORITY1 = "ROLE_DUMMY";
    private static final String AUTHORITY2 = "ROLE_OTHER";
    private static final String PROPERTY_KEY1 = "property1";
    private static final String PROPERTY_KEY2 = "property2";
    private static final String PROPERTY_VALUE1 = "value1";
    private static final String PROPERTY_VALUE2 = "value2";
    private static final String PROPERTY_VALUE3 = "value3";
    
    private static final String REALM_NAME1 = "REALM01.COUNTRY";
    private static final String REALM_NAME2 = "REALM02.COUNTRY";
    private static final String REALM_NAME3 = "REALM03.COUNTRY2";
    
    
    private ClientDetailsEntity publicClient1;
    private ClientDetailsEntity publicClient2;
    private RealmDetailsEntity realm1;
    private RealmDetailsEntity realm2;
    private RealmDetailsEntity realm3;
    private DefaultUserInfoRealmDetails userRealm1;
    private DefaultUserInfoRealmDetails userRealm2;
    private DefaultUserInfoRealmDetails userRealm3;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        publicClient1 = new ClientDetailsEntity();
        publicClient1.setClientId(CLIENT_ID1);
        
        publicClient2 = new ClientDetailsEntity();
        publicClient2.setClientId(CLIENT_ID2);
        publicClient2.setSubjectType(SubjectType.PUBLIC);
        
        realm1 = new RealmDetailsEntity();
        realm1.setName(REALM_NAME1);
        realm1.setShortDesc(REALM_NAME1);
        
        realm2 = new RealmDetailsEntity();
        realm2.setName(REALM_NAME2);
        realm2.setShortDesc(REALM_NAME2);
        
        realm3 = new RealmDetailsEntity();
        realm3.setName(REALM_NAME3);
        realm3.setShortDesc(REALM_NAME3);
        

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.mitre.openid.connect.model.DefaultUserInfo#toJson()}.
     */
    @Test
    public void testToJson() {
        UserInfo userInfo = null;
        JsonObject result = null;
        
        _given: {
            userInfo = createDefaultUserInfo(USER_ID, USER_SUB, USER_NAME);

            Set<UserInfoClientDetails> details = createClientDetails();
            userInfo.setAccountDetails(Collections.<UserInfoClientDetails>unmodifiableSet(details));
            
            Set<UserInfoClientAuthority> authorities = createClientAuthorities();
            userInfo.setAccountAuthorities(Collections.<UserInfoClientAuthority>unmodifiableSet(authorities));
            
            Set<UserInfoClientProperty> properties = createClientProperties();
            userInfo.setAccountProperties(Collections.<UserInfoClientProperty>unmodifiableSet(properties));
            
            Set<UserInfoRealmDetails> realms = createUserRealms();
            userInfo.setAccountRealms(realms);
            
        }

        _then: try {
            result = userInfo.toJson();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        _expect: {
            assertNotNull(result);
            assertEquals(result.get("sub").getAsString(), USER_SUB);
            assertEquals(result.get("preferred_username").getAsString(), USER_NAME);
            assertTrue(result.has("accountDetails"));
            assertTrue(result.has("accountAuthorities"));
            assertTrue(result.has("accountProperties"));
            assertTrue(result.has("accountRealms"));
            JsonObject accDetailsObj = result.get("accountDetails").getAsJsonObject();
            assertEquals(accDetailsObj.entrySet().size(), 2);
            assertTrue(accDetailsObj.has(CLIENT_ID1));
            assertTrue(accDetailsObj.has(CLIENT_ID2));
            JsonObject detailsObj = accDetailsObj.get(CLIENT_ID1).getAsJsonObject();
            assertEquals(detailsObj.get("enabled").getAsBoolean(), Boolean.TRUE);
            detailsObj = accDetailsObj.get(CLIENT_ID2).getAsJsonObject();
            assertEquals(detailsObj.get("enabled").getAsBoolean(), Boolean.TRUE);
            JsonObject accAuthsObj = result.get("accountAuthorities").getAsJsonObject();
            assertEquals(accAuthsObj.entrySet().size(), 2);
            assertTrue(accAuthsObj.has(CLIENT_ID1));
            assertTrue(accAuthsObj.has(CLIENT_ID2));
            JsonArray authorityArr = accAuthsObj.get(CLIENT_ID1).getAsJsonArray();
            assertEquals(authorityArr.size(), 2);
            authorityArr = accAuthsObj.get(CLIENT_ID2).getAsJsonArray();
            assertEquals(authorityArr.size(), 1);
            JsonObject accPropertiesObj = result.get("accountProperties").getAsJsonObject();
            assertEquals(accPropertiesObj.entrySet().size(), 2);
            assertTrue(accPropertiesObj.has(CLIENT_ID1));
            assertTrue(accPropertiesObj.has(CLIENT_ID2));
            JsonObject propertiesObj = accPropertiesObj.get(CLIENT_ID1).getAsJsonObject();
            assertTrue(propertiesObj.has(PROPERTY_KEY1));
            assertEquals(propertiesObj.get(PROPERTY_KEY1).getAsString(), PROPERTY_VALUE1);
            assertTrue(propertiesObj.has(PROPERTY_KEY2));
            assertEquals(propertiesObj.get(PROPERTY_KEY2).getAsString(), PROPERTY_VALUE2);
            propertiesObj = accPropertiesObj.get(CLIENT_ID2).getAsJsonObject();
            assertTrue(propertiesObj.has(PROPERTY_KEY1));
            assertEquals(propertiesObj.get(PROPERTY_KEY1).getAsString(), PROPERTY_VALUE3);
            assertFalse(propertiesObj.has(PROPERTY_KEY2));
            JsonObject accRealmsObj = result.get("accountRealms").getAsJsonObject();
            assertEquals(accRealmsObj.entrySet().size(), 3);
            assertTrue(accRealmsObj.has(REALM_NAME1));
            assertTrue(accRealmsObj.has(REALM_NAME2));
            assertTrue(accRealmsObj.has(REALM_NAME3));
            
            JsonObject realmsObj = accRealmsObj.get(REALM_NAME1).getAsJsonObject();
            assertTrue(realmsObj.get("isAdmin").getAsBoolean());
            JsonObject realmPropsObj = realmsObj.get("properties").getAsJsonObject(); 
            assertTrue(realmPropsObj.has(PROPERTY_KEY1));
            assertEquals(realmPropsObj.get(PROPERTY_KEY1).getAsString(), PROPERTY_VALUE1);
            assertTrue(realmPropsObj.has(PROPERTY_KEY2));
            assertEquals(realmPropsObj.get(PROPERTY_KEY2).getAsString(), PROPERTY_VALUE2);
            
            realmsObj = accRealmsObj.get(REALM_NAME2).getAsJsonObject();
            assertFalse(realmsObj.get("isAdmin").getAsBoolean());
            realmPropsObj = realmsObj.get("properties").getAsJsonObject(); 
            assertTrue(realmPropsObj.has(PROPERTY_KEY1));
            assertEquals(realmPropsObj.get(PROPERTY_KEY1).getAsString(), PROPERTY_VALUE3);
            assertFalse(realmPropsObj.has(PROPERTY_KEY2));
            
            realmsObj = accRealmsObj.get(REALM_NAME3).getAsJsonObject();
            assertFalse(realmsObj.get("isAdmin").getAsBoolean());
            realmPropsObj = realmsObj.get("properties").getAsJsonObject(); 
            assertTrue(realmPropsObj.entrySet().isEmpty());
        }
    }

    /**
     * @return
     */
    private Set<UserInfoRealmDetails> createUserRealms() {
        userRealm1 = new DefaultUserInfoRealmDetails();
        userRealm1.setRealm(realm1);
        userRealm1.setAdmin(true);
        
        userRealm2 = new DefaultUserInfoRealmDetails();
        userRealm2.setRealm(realm2);
        userRealm2.setAdmin(false);
        
        userRealm3 = new DefaultUserInfoRealmDetails();
        userRealm3.setRealm(realm3);
        userRealm3.setAdmin(false);
        
        Set<UserInfoRealmDetails> realmDetails = new HashSet<>();
        Set<UserInfoRealmProperty> realmProperties1 = new HashSet<>();
        DefaultUserInfoRealmProperty realmProp1 = new DefaultUserInfoRealmProperty();
        realmProp1.setUserInfoRealm(userRealm1);
        realmProp1.setProperty(PROPERTY_KEY1);
        realmProp1.setValue(PROPERTY_VALUE1);
        realmProperties1.add(realmProp1);
        DefaultUserInfoRealmProperty realmProp2 = new DefaultUserInfoRealmProperty();
        realmProp2.setUserInfoRealm(userRealm1);
        realmProp2.setProperty(PROPERTY_KEY2);
        realmProp2.setValue(PROPERTY_VALUE2);
        realmProperties1.add(realmProp2);
        userRealm1.setRealmProperties(realmProperties1);
        realmDetails.add(userRealm1);
        
        Set<UserInfoRealmProperty> realmProperties2 = new HashSet<>();
        DefaultUserInfoRealmProperty realmProp3 = new DefaultUserInfoRealmProperty();
        realmProp3.setUserInfoRealm(userRealm2);
        realmProp3.setProperty(PROPERTY_KEY1);
        realmProp3.setValue(PROPERTY_VALUE3);
        realmProperties2.add(realmProp3);
        userRealm2.setRealmProperties(realmProperties2);
        realmDetails.add(userRealm2);
        
        // Add a realm without properties.
        realmDetails.add(userRealm3);
        return realmDetails;
    }

    /**
     * @return
     */
    private Set<UserInfoClientProperty> createClientProperties() {
        Set<UserInfoClientProperty> properties = new HashSet<>();
        DefaultUserInfoClientProperty property1 = new DefaultUserInfoClientProperty();
        property1.setClient(publicClient1);
        property1.setProperty(PROPERTY_KEY1);
        property1.setValue(PROPERTY_VALUE1);
        properties.add(property1);
        DefaultUserInfoClientProperty property2 = new DefaultUserInfoClientProperty();
        property2.setClient(publicClient1);
        property2.setProperty(PROPERTY_KEY2);
        property2.setValue(PROPERTY_VALUE2);
        properties.add(property2);
        DefaultUserInfoClientProperty property3 = new DefaultUserInfoClientProperty();
        property3.setClient(publicClient2);
        property3.setProperty(PROPERTY_KEY1);
        property3.setValue(PROPERTY_VALUE3);
        properties.add(property3);
        return properties;
    }

    /**
     * @return
     */
    private Set<UserInfoClientAuthority> createClientAuthorities() {
        Set<UserInfoClientAuthority> authorities = new HashSet<>();
        DefaultUserInfoClientAuthority authority1 = new DefaultUserInfoClientAuthority();
        authority1.setClient(publicClient1);
        authority1.setAuthority(AUTHORITY1);
        authorities.add(authority1);
        DefaultUserInfoClientAuthority authority2 = new DefaultUserInfoClientAuthority();
        authority2.setClient(publicClient1);
        authority2.setAuthority(AUTHORITY2);
        authorities.add(authority2);
        DefaultUserInfoClientAuthority authority3 = new DefaultUserInfoClientAuthority();
        authority3.setClient(publicClient2);
        authority3.setAuthority(AUTHORITY1);
        authorities.add(authority3);
        return authorities;
    }

    /**
     * @return
     */
    private Set<UserInfoClientDetails> createClientDetails() {
        Set<UserInfoClientDetails> details = new HashSet<>();
        DefaultUserInfoClientDetails detail1 = new DefaultUserInfoClientDetails();
        detail1.setClient(publicClient1);
        detail1.setEnabled(true);
        details.add(detail1);
        DefaultUserInfoClientDetails detail2 = new DefaultUserInfoClientDetails();
        detail2.setClient(publicClient2);
        detail2.setEnabled(true);
        details.add(detail2);
        return details;
    }

    /**
     * 
     * @param id
     * @param sub
     * @param username
     * @return
     */
    private DefaultUserInfo createDefaultUserInfo(long id, String sub, String username) {
        DefaultUserInfo user = new DefaultUserInfo();
        user.setId(id);
        user.setSub(sub);
        user.setPreferredUsername(username);
        return user;
    }

}
