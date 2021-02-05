#!/usr/bin/env bash

#배포할 신규버전 스프링부트 프로젝트를 stop.sh로 종료한 'profile'로 실행
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

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다.."

nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-$IDLE_PROFILE.properties \
        -Dspring.profiles.active=$IDLE_PROFILE \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &


