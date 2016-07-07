#FROM mysql

#COPY initdb.sql /data/initdb.sql
# Change the working directory
#WORKDIR data
#CMD mysql -u $MYSQL_USER -p $MYSQL_PASSWORD $MYSQL_DATABASE < initdb.sql

#ADD init_db.sh /tmp/init_db.sh
#ADD dump.sql /tmp/dump.sql
#RUN /tmp/init_db.sh
#RUN chmod +x /tmp/init_db.sh
#RUN sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
#CMD /tmp/init_db.sh


FROM mysql
MAINTAINER  Ayemi Musa <ayemi_jmusa@yahoo.co.uk>

# Copy the database schema to the /data directory
#ADD /run_db.sh /init_db.sh /dump.sql /tmp/
ADD  /dump.sql/ /docker-entrypoint-initdb.d/

VOLUME ["/var/lib/mysql"]
#CMD sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
# init_db will create the default
# database from epcis_schema.sql, then
# stop mysqld, and finally copy the /var/lib/mysql directory
# to default_mysql_db.tar.gz
#RUN chmod +x /tmp/init_db.sh /tmp/run_db.sh
##RUN /tmp/init_db.sh

# run_db starts mysqld, but first it checks
# to see if the /var/lib/mysql directory is empty, if
# it is it is seeded with default_mysql_db.tar.gz before
# the mysql is fired up

#ENTRYPOINT "/tmp/run_db.sh"