FROM docker.io/openshift/jenkins-2-centos7:latest
COPY plugins.txt /usr/share/jenkins/ref/
COPY configs/init.groovy.d/configure-shared-library.groovy /opt/openshift/configuration/init.groovy.d/configure-shared-library.groovy
USER root
RUN /usr/local/bin/install-plugins.sh /usr/share/jenkins/ref/plugins.txt
