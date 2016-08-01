/subsystem=security/security-domain=cosmDomain:add(cache-type="default")
/subsystem=security/security-domain=cosmDomain/authentication=classic:add(login-modules=[{code="Database", flag="required", module-options={dsJndiName="java:jboss/datasources/EntitlementDS", principalsQuery="select passwd from User where username=?", rolesQuery="select role, 'Roles' from UserRole where username=?", hashAlgorithm="SHA-256", hashEncoding="BASE64", unauthenticatedIdentity="guest"}}, {code="RoleMapping", flag="required", module-options={rolesProperties="file:${jboss.server.config.dir}/cosmDomain.properties", replaceRole="false"}}])