/*
DROP DATABASE IF EXISTS `oidc`;
CREATE DATABASE IF NOT EXISTS `oidc`
DEFAULT CHARACTER SET = utf8
DEFAULT COLLATE = utf8_unicode_ci;
 */
 
--
-- Tables for OIDC Server functionality, MySQL
--

DROP TABLE IF EXISTS `access_token`;
CREATE TABLE IF NOT EXISTS `access_token` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token_value VARCHAR(4096),
    expiration TIMESTAMP NULL,
    token_type VARCHAR(256),
    refresh_token_id BIGINT,
    client_id BIGINT,
    auth_holder_id BIGINT,
    id_token_id BIGINT,
    approved_site_id BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `access_token_permissions`;
CREATE TABLE IF NOT EXISTS `access_token_permissions` (
    access_token_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    formatted VARCHAR(256),
    street_address VARCHAR(256),
    locality VARCHAR(256),
    region VARCHAR(256),
    postal_code VARCHAR(256),
    country VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `approved_site`;
CREATE TABLE IF NOT EXISTS approved_site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(256),
    client_id VARCHAR(256),
    creation_date TIMESTAMP NULL,
    access_date TIMESTAMP NULL,
    timeout_date TIMESTAMP NULL,
    whitelisted_site_id BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `approved_site_scope`;
CREATE TABLE IF NOT EXISTS approved_site_scope (
    owner_id BIGINT,
    scope VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder`;
CREATE TABLE IF NOT EXISTS authentication_holder (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_auth_id BIGINT,
    approved BOOLEAN,
    redirect_uri VARCHAR(2048),
    client_id VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_authority`;
CREATE TABLE IF NOT EXISTS authentication_holder_authority (
    owner_id BIGINT,
    authority VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_resource_id`;
CREATE TABLE IF NOT EXISTS authentication_holder_resource_id (
    owner_id BIGINT,
    resource_id VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_response_type`;
CREATE TABLE IF NOT EXISTS authentication_holder_response_type (
    owner_id BIGINT,
    response_type VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_extension`;
CREATE TABLE IF NOT EXISTS authentication_holder_extension (
    owner_id BIGINT,
    extension VARCHAR(2048),
    val VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_scope`;
CREATE TABLE IF NOT EXISTS authentication_holder_scope (
    owner_id BIGINT,
    scope VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authentication_holder_request_parameter`;
CREATE TABLE IF NOT EXISTS authentication_holder_request_parameter (
    owner_id BIGINT,
    param VARCHAR(2048),
    val VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `saved_user_auth`;
CREATE TABLE IF NOT EXISTS saved_user_auth (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1024),
    authenticated BOOLEAN,
    source_class VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `saved_user_auth_authority`;
CREATE TABLE IF NOT EXISTS saved_user_auth_authority (
    owner_id BIGINT,
    authority VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_authority`;
CREATE TABLE IF NOT EXISTS client_authority (
    owner_id BIGINT,
    authority VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `authorization_code`;
CREATE TABLE IF NOT EXISTS authorization_code (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(256),
    auth_holder_id BIGINT,
    expiration TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_grant_type`;
CREATE TABLE IF NOT EXISTS client_grant_type (
    owner_id BIGINT,
    grant_type VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_response_type`;
CREATE TABLE IF NOT EXISTS client_response_type (
    owner_id BIGINT,
    response_type VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `blacklisted_site`;
CREATE TABLE IF NOT EXISTS blacklisted_site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uri VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_details`;
CREATE TABLE IF NOT EXISTS client_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    client_description VARCHAR(1024),
    reuse_refresh_tokens BOOLEAN DEFAULT true NOT NULL,
    dynamically_registered BOOLEAN DEFAULT false NOT NULL,
    allow_introspection BOOLEAN DEFAULT false NOT NULL,
    id_token_validity_seconds BIGINT DEFAULT 600 NOT NULL,
    
    client_id VARCHAR(256),
    client_secret VARCHAR(2048),
    access_token_validity_seconds BIGINT,
    refresh_token_validity_seconds BIGINT,
    
    application_type VARCHAR(256),
    client_name VARCHAR(256),
    token_endpoint_auth_method VARCHAR(256),
    subject_type VARCHAR(256),
    
    logo_uri VARCHAR(2048),
    policy_uri VARCHAR(2048),
    client_uri VARCHAR(2048),
    tos_uri VARCHAR(2048),

    jwks_uri VARCHAR(2048),
    jwks TEXT(8196),
    sector_identifier_uri VARCHAR(2048),
    
    request_object_signing_alg VARCHAR(256),
    
    user_info_signed_response_alg VARCHAR(256),
    user_info_encrypted_response_alg VARCHAR(256),
    user_info_encrypted_response_enc VARCHAR(256),
    
    id_token_signed_response_alg VARCHAR(256),
    id_token_encrypted_response_alg VARCHAR(256),
    id_token_encrypted_response_enc VARCHAR(256),
    
    token_endpoint_auth_signing_alg VARCHAR(256),
    
    default_max_age BIGINT,
    require_auth_time BOOLEAN,
    created_at TIMESTAMP NULL,
    initiate_login_uri VARCHAR(2048),
    clear_access_tokens_on_refresh BOOLEAN DEFAULT true NOT NULL,
    UNIQUE (client_id(255))

) ENGINE=InnoDB ROW_FORMAT=DYNAMIC DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `client_request_uri`;
CREATE TABLE IF NOT EXISTS client_request_uri (
    owner_id BIGINT,
    request_uri VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_post_logout_redirect_uri`;
CREATE TABLE IF NOT EXISTS client_post_logout_redirect_uri (
    owner_id BIGINT,
    post_logout_redirect_uri VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_default_acr_value`;
CREATE TABLE IF NOT EXISTS client_default_acr_value (
    owner_id BIGINT,
    default_acr_value VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_contact`;
CREATE TABLE IF NOT EXISTS client_contact (
    owner_id BIGINT,
    contact VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_redirect_uri`;
CREATE TABLE IF NOT EXISTS client_redirect_uri (
    owner_id BIGINT, 
    redirect_uri VARCHAR(2048) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_claims_redirect_uri`;
CREATE TABLE IF NOT EXISTS client_claims_redirect_uri (
    owner_id BIGINT, 
    redirect_uri VARCHAR(2048) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE IF NOT EXISTS refresh_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token_value VARCHAR(4096),
    expiration TIMESTAMP NULL,
    auth_holder_id BIGINT,
    client_id BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_resource`;
CREATE TABLE IF NOT EXISTS client_resource (
    owner_id BIGINT, 
    resource_id VARCHAR(256) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `client_scope`;
CREATE TABLE IF NOT EXISTS client_scope (
    owner_id BIGINT,
    scope VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `token_scope`;
CREATE TABLE IF NOT EXISTS token_scope (
    owner_id BIGINT,
    scope VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `system_scope`;
CREATE TABLE IF NOT EXISTS system_scope (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    scope VARCHAR(256) NOT NULL,
    description VARCHAR(4096),
    icon VARCHAR(256),
    restricted BOOLEAN DEFAULT false NOT NULL,
    default_scope BOOLEAN DEFAULT false NOT NULL,
    structured BOOLEAN DEFAULT false NOT NULL,
    structured_param_description VARCHAR(256),
    UNIQUE (scope(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS user_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sub VARCHAR(256),
    preferred_username VARCHAR(256),
    name VARCHAR(256),
    given_name VARCHAR(256),
    family_name VARCHAR(256),
    middle_name VARCHAR(256),
    nickname VARCHAR(256),
    profile VARCHAR(256),
    picture VARCHAR(256),
    website VARCHAR(256),
    email VARCHAR(256),
    email_verified BOOLEAN,
    gender VARCHAR(256),
    zone_info VARCHAR(256),
    locale VARCHAR(256),
    phone_number VARCHAR(256),
    phone_number_verified BOOLEAN,
    address_id VARCHAR(256),
    updated_time VARCHAR(256),
    birthdate VARCHAR(256),
    src VARCHAR(4096)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `whitelisted_site`;
CREATE TABLE IF NOT EXISTS whitelisted_site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    creator_user_id VARCHAR(256),
    client_id VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `whitelisted_site_scope`;
CREATE TABLE IF NOT EXISTS whitelisted_site_scope (
    owner_id BIGINT,
    scope VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `pairwise_identifier`;
CREATE TABLE IF NOT EXISTS pairwise_identifier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifier VARCHAR(256),
    sub VARCHAR(256),
    sector_identifier VARCHAR(2048)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `resource_set`;
CREATE TABLE IF NOT EXISTS resource_set (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1024) NOT NULL,
    uri VARCHAR(1024),
    icon_uri VARCHAR(1024),
    rs_type VARCHAR(256),
    owner VARCHAR(256) NOT NULL,
    client_id VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `resource_set_scope`;
CREATE TABLE IF NOT EXISTS resource_set_scope (
    owner_id BIGINT NOT NULL,
    scope VARCHAR(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `permission_ticket`;
CREATE TABLE IF NOT EXISTS permission_ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket VARCHAR(256) NOT NULL,
    permission_id BIGINT NOT NULL,
    expiration TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_set_id BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `permission_scope`;
CREATE TABLE IF NOT EXISTS permission_scope (
    owner_id BIGINT NOT NULL,
    scope VARCHAR(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `claim`;
CREATE TABLE IF NOT EXISTS claim (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256),
    friendly_name VARCHAR(1024),
    claim_type VARCHAR(1024),
    claim_value VARCHAR(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `claim_to_policy`;
CREATE TABLE IF NOT EXISTS claim_to_policy (
    policy_id BIGINT NOT NULL,
    claim_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `claim_to_permission_ticket`;
CREATE TABLE IF NOT EXISTS claim_to_permission_ticket (
    permission_ticket_id BIGINT NOT NULL,
    claim_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `policy`;
CREATE TABLE IF NOT EXISTS policy (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1024),
    resource_set_id BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `policy_scope`;
CREATE TABLE IF NOT EXISTS policy_scope (
    owner_id BIGINT NOT NULL,
    scope VARCHAR(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `claim_token_format`;
CREATE TABLE IF NOT EXISTS claim_token_format (
    owner_id BIGINT NOT NULL,
    claim_token_format VARCHAR(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `claim_issuer`;
CREATE TABLE IF NOT EXISTS claim_issuer (
    owner_id BIGINT NOT NULL,
    issuer VARCHAR(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `saved_registered_client`;
CREATE TABLE IF NOT EXISTS saved_registered_client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issuer VARCHAR(1024),
    registered_client VARCHAR(8192)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP INDEX `at_tv_idx` ON access_token;
DROP INDEX `ts_oi_idx` ON token_scope;
DROP INDEX `at_exp_idx` ON access_token;
DROP INDEX `rf_ahi_idx` ON refresh_token;
DROP INDEX `cd_ci_idx` ON client_details;

CREATE INDEX at_tv_idx ON access_token(token_value(255)) ;
CREATE INDEX ts_oi_idx ON token_scope(owner_id) ;
CREATE INDEX at_exp_idx ON access_token(expiration) ;
CREATE INDEX rf_ahi_idx ON refresh_token(auth_holder_id) ;
CREATE INDEX cd_ci_idx ON client_details(client_id(255)) ;