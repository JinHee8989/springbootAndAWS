#!/usr/bin/env bash

#쉬고있는 profile 찾기: real1이 사용중이면 real2가 쉬고있음

function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)        #현재 엔진엑스가 바라보는 스프링부트가 정상적으로 수행하는지 확인 (응답값을 HttpStatus로 받음)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400보다 크면(즉, 40x/50x에러 모두 포함) 예외로 보고 real2를 현재 profile로 사용
  then
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1]
  then
    IDLE_PROFILE=real2    #현재 엔진엑스와 연결되지 않은 profile로 스프링부트 프로젝트를 이 profile로 연결하기위해 반환함
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"  #bash라는 스크립트는 값을 반환하는 기능이없음. 제일 마지막줄에 echo로 결과 출력(단, 중간에 echo를 써선 안됨)
}

#쉬고있는 profile의 port찾기
function find_idle_port()
{
  IDLE_PROFILE = ${find_idle_profile}

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  then
    echo "8082"
  fi
}