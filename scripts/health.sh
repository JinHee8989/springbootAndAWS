#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)        #현재 stop.sh가 속해있는 경로를 찾음
source ${ABSDIR}/profile.sh       #자바로 치면 일종의 import문. 해당 코드로 인해 profile의 function을 사용할 수 있게 됨
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT : $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile"      #curl이란 서버와 통신할 수 있는 커맨드 명령어 툴
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]         # $up_count >= 1 ("real"문자열이 있는지 검증)
  then
    echo "> Health check 성공"
    switch_proxy        # 잘 떴는지 확인되어야 엔진엑스 프록시 설정을 변경 (switch_proxy는 switch.sh에서 가져온 함수임)
    break
  else
    echo "> Health check의 응답을 알 수 없거나 혹은 실행상태가 아닙니다."
    echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]         # $up_count >= 1 ("real"문자열이 있는지 검증)
  then
    echo "> Health check 실패"
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결실패. 재시도..."
  sleep 10
done
