# Add MySQL module
module add --name=com.mysql --resources=/opt/jboss/wildfly/customization/mysql-connector-java-5.1.31-bin.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
#data-source add --name=EntitlementDS --driver-name=mysql --jndi-name=java:jboss/datasources/EntitlementDS --connection-url=jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/entitlement?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
#data-source add --name=EntitlementDS --driver-name=mysql --jndi-name=java:jboss/datasources/EntitlementDS --connection-url=jdbc:mysql://$MYSQL_SERVICE_HOST:$MYSQL_SERVICE_PORT/entitlement?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
data-source add --name=EntitlementDS --driver-name=mysql --jndi-name=java:jboss/datasources/EntitlementDS --connection-url=jdbc:mysql://$DB_PORT_3306_TCP_ADDR:$DB_PORT_3306_TCP_PORT/entitlement?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
#data-source add --name=EntitlementDS --driver-name=mysql --jndi-name=java:jboss/datasources/EntitlementDS --connection-url=jdbc:mysql://$MYSQL_SERVICE_SERVICE_HOST:$MYSQL_SERVICE_SERVICE_PORT/entitlement --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
#data-source add --name=EntitlementDS --driver-name=mysql --jndi-name=java:jboss/datasources/EntitlementDS --connection-url=jdbc:mysql://$MYSQL_URI/entitlement?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
#data-source add --name=UserDS --driver-name=mysql --jndi-name=java:jboss/datasources/UserDS --connection-url=jdbc:mysql://$DB_PORT_3306_TCP_ADDR:$DB_PORT_3306_TCP_PORT/wildfly?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=cosm --password=cosm --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
