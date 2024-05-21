#!/bin/bash
# mac x86에 대해서만 작성.

######################
# DO NOT ERASE !!
BACKUP_IFS="$IFS" # 기존 IFS 저장 (시스템)
######################
IFS="" # IFS 초기화 -> function 호출 시 function 내 줄바꿈이 무시되는 것을 막기 위해 초기화

check_docker(){
  echo -e "\033[34m"Docker desktop 설치 여부 확인..."\033[0m\n"
  ls -alt /Applications/Dockers.app/ 2> /dev/null || isDockerInstalled=$?

  if [ "${isDockerInstalled}" = 1 ]; then
    echo -e "\033[31m"
    echo "Docker is not installed"
    echo -e "\033[0m"
    echo false
  else
    echo true
  fi
}

isDockerInstalled=$(check_docker)

######################
# DO NOT ERASE !!
IFS=$BACKUP_IFS # IFS 원복
######################