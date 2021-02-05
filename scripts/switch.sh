#!/usr/bin/env bash

#엔진엑스가 바라보는 스프링부트를 최신버전으로 변경

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy(){
  IDLE_PORT=$(find_idle_port)

  echo ">전환할 Port: $IDLE_PORT"
  echo ">Port 전환"
  echo ">set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc     # 하나의 문장을 만들어 파이프라인(|)으로 넘겨주기위해 echo를 사용
                                                                                                            # 엔진엑스가 변경할 프록시 주소를 생성
                                                                                                            # 쌍따옴표를 사용해야함(안쓰며 $service-url.inc를 인식하지 못하고 변수를 찾게됨
                                                                                                            # echo로 넘겨준 문장을 service-url.inc에 덮어씀
  echo ">엔진엑스의 Reload"
  sudo service nginx reload         # 엔진엑스 설정을 다시 불러옴. restart와는 다름(restart는 잠시 끊기는 현상이 있지만 reload는 끊김없이 다시 불러옴. 다만 중요한 설정들은 반영되지 않아 restart를 사용해야함
                                    # 여기선 외부의 설정파일은 service-url.inc를 다시 불러오는거라 reload사용!
}