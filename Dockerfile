# registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:latest
FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift:latest
#172.30.1.1:5000/yanni-test/java:latest

# TODO: Put the maintainer name in the image metadata
# LABEL maintainer="Your Name <your@email.com>"

# TODO: Rename the builder environment variable to inform users about application you provide them
# ENV BUILDER_VERSION 1.0
ENV	  JAVA_OPTIONS -Xmx512m
# TODO: Set labels used in OpenShift to describe the builder image
#LABEL io.k8s.description="Platform for building xyz" \
#      io.k8s.display-name="builder x.y.z" \
#      io.openshift.expose-services="8080:http" \
#      io.openshift.tags="builder,x.y.z,etc."

# TODO: Install required packages here:
# RUN yum install -y ... && yum clean all -y

# TODO (optional): Copy the builder files into /opt/app-root
# COPY ./<builder_folder>/ /opt/app-root/

# TODO: Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image
# sets io.openshift.s2i.scripts-url label that way, or update that label

# TODO: Drop the root user and make the content of /opt/app-root owned by user 1001
COPY  ./s2i/bin/ /opt/app-root/bin/

#RUN  chgrp -R 0 /opt/app-root && \
#     chmod -R g=u /opt/app-root
COPY  ./target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar /opt/app-root/app.jar
EXPOSE 8080
# This default user is created in the openshift/base-centos7 image
USER 1001

#RUN /opt/app-root/bin/assemble
CMD /opt/app-root/bin/run
# TODO: Set the default port for applications built using this image
# EXPOSE 8080

# TODO: Set the default CMD for the image
# CMD ["/usr/libexec/s2i/usage"]
