#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=springbootAndAWS

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl springbootAndAWS | grep jar | awk '{print $1}')       #중복된 애플리케이션명이 있을수 있으므로 애플리케이션명으로 된 jar 프로세스 검색 후 id 찾기

echo " 현재 구동중인 애플리케이션 pid: $CURRENT_PID "

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다. "
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar |tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &      #nohup실행시 codeDeploy는 무한대기중이므로 nohup.out파일을 표준입출력용으로 별도 사용, 이렇게 안하면 nohup파일이 생기지 않고 coeDeploy로그엥 표준입출력이 출력됨. nohup이 끝나기 전까지 codedeploy도 끝나지 않으므로 꼭 이렇게 해줘야함
