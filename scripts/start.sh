#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)        #현재 stop.sh가 속해있는 경로를 찾음
source ${ABSDIR}/profile.sh       #자바로 치면 일종의 import문. 해당 코드로 인해 profile의 function을 사용할 수 있게 됨

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=springbootAndAWS

echo ">build 파일 복사"
echo ">cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"
JAR_NAME=$(LS -TR $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."

nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-$IDLE_PROFILE.properties \
        -Dspring.profiles.active=$IDLE_PROFILE \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &









echo "> IDLE_PORT에서 구동중인 애플리케이션 PID확인"

IDLE_PID = $(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID}]
then
  echo ">현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo ">kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
