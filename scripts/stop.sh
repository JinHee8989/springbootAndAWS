#!/usr/bin/env bash

# 기존 엔진엑스에 연결되어있진 않지만, 실행중이던 스프링부트 종료
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)        #현재 stop.sh가 속해있는 경로를 찾음
source ${ABSDIR}/profile.sh       #자바로 치면 일종의 import문. 해당 코드로 인해 profile의 function을 사용할 수 있게 됨

IDLE_PORT=$(find_idle_port)

echo "> IDLE_PORT에서 구동중인 애플리케이션 PID확인"

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
