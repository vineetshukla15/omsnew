FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD oms-media-1.0-SNAPSHOT.jar omsapp.jar
RUN sh -c 'touch /omsapp.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /omsapp.jar" ]