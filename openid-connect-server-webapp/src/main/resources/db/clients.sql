--
-- Turn off autocommit and start a transaction so that we can use the temp tables
--

SET AUTOCOMMIT FALSE;

START TRANSACTION;

--
-- Insert client information into the temporary tables. To add clients to the HSQL database, edit things here.
-- 

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client', 'secret', 'Test Client', false, null, 3600, 600, true),
	('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'AJvbVefFB_7ILzOsx9Bu6zS78VF7z9JO-NC5393Nloe0188kq-qJglu1VAl1nddq1fl1CPQ4BdNa7bbzzTrwuO4', 'spring-cloud-resource', false, null, 3600, 600, true),
	('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'AMV46qMUbVjdakxokf2GavBPjMYHMyWRAk3IrymYgFvrtAUe5DBIoW2KuIbLUK5SV', 'spring-cloud-web', false, null, 3600, 600, true);

INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client', 'openid'),
	('client', 'profile'),
	('client', 'email'),
	('client', 'address'),
	('client', 'phone'),
	('client', 'offline_access'),
	('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'openid'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'profile'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'email'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'address'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'phone'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'offline_access'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'openid'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'profile'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'email'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'address'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'phone'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'offline_access');

INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client', 'http://localhost/'),
	('client', 'http://localhost:8080/'),
	('bacffdc8-cb3c-4657-a77a-3f3f465f73c5', 'http://localhost:10000/'),
	('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'http://localhost:9999/');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client', 'authorization_code'),
	('client', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client', 'implicit'),
	('client', 'refresh_token'),
	('bacffdc8-cb3c-4657-a77a-3f3f465f73c', 'authorization_code'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c', 'password'),
    ('bacffdc8-cb3c-4657-a77a-3f3f465f73c', 'refresh_token'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'authorization_code'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'password'),
    ('13ae3d92-c41e-45c1-9bed-e4f35db72f67', 'refresh_token');
	
--
-- Merge the temporary clients safely into the database. This is a two-step process to keep clients from being created on every startup with a persistent store.
--

MERGE INTO client_details 
  USING (SELECT client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection FROM client_details_TEMP) AS vals(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection)
  ON vals.client_id = client_details.client_id
  WHEN NOT MATCHED THEN 
    INSERT (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection);

MERGE INTO client_scope 
  USING (SELECT id, scope FROM client_scope_TEMP, client_details WHERE client_details.client_id = client_scope_TEMP.owner_id) AS vals(id, scope)
  ON vals.id = client_scope.owner_id AND vals.scope = client_scope.scope
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, scope) values (vals.id, vals.scope);

MERGE INTO client_redirect_uri 
  USING (SELECT id, redirect_uri FROM client_redirect_uri_TEMP, client_details WHERE client_details.client_id = client_redirect_uri_TEMP.owner_id) AS vals(id, redirect_uri)
  ON vals.id = client_redirect_uri.owner_id AND vals.redirect_uri = client_redirect_uri.redirect_uri
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, redirect_uri) values (vals.id, vals.redirect_uri);

MERGE INTO client_grant_type 
  USING (SELECT id, grant_type FROM client_grant_type_TEMP, client_details WHERE client_details.client_id = client_grant_type_TEMP.owner_id) AS vals(id, grant_type)
  ON vals.id = client_grant_type.owner_id AND vals.grant_type = client_grant_type.grant_type
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, grant_type) values (vals.id, vals.grant_type);
    
-- 
-- Close the transaction and turn autocommit back on
-- 
    
COMMIT;

SET AUTOCOMMIT TRUE;

