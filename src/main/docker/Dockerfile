FROM openjdk:12.0.2
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY iam.jar /iam.jar
ENTRYPOINT exec java -XX:+UseContainerSupport -Xms128m -Xmx256m -jar /iam.jar