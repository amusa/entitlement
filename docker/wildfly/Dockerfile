FROM jboss/wildfly:latest

ADD /docker-settings/customization /opt/jboss/wildfly/customization/
ADD /docker-settings/cosmDomain.properties /opt/jboss/wildfly/standalone/configuration/cosmDomain.properties

CMD ["/opt/jboss/wildfly/customization/execute.sh"]