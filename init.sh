#!/bin/bash
# mac x86에 대해서만 작성.

######################
# DO NOT ERASE !!
BACKUP_IFS="$IFS" # 기존 IFS 저장 (시스템)
######################
IFS="" # IFS 초기화 -> function 호출 시 function 내 줄바꿈이 무시되는 것을 막기 위해 초기화

check_docker(){
  echo -e "\033[33m"Checking docker by dir..."\033[0m\n"
  ls -alt /Applications/Docker.app/ 2> /dev/null || isDockerInstalled=$?
  echo ""
  if [ "${isDockerInstalled}" = 1 ]; then
    echo -e "\033[31mDocker is not installed \033[0m"
    return 0
  else
    echo -e "\033[32mDocker is installed !!\033[0m"
    return 1
  fi
}

install_docker(){
  while true; do

  read -p "> Will install docker desktop? (Y/N): " confirm;

  case $confirm in
  	[yY] ) echo "Installing docker with brew...";
  	  brew install docker --cask
  		break;;
  	[nN] ) echo exiting...;
  		exit;;
  	* ) echo invalid response;;
  esac
  done
  echo ""
}

# Docker Desktop 확인 및 설치
echo -e "\033[34m"Docker desktop 설치 여부 확인"\033[0m"
check_docker
if [[ $? -eq 1 ]]; then
  echo "Mac OS의 경우 Docker desktop 대신 OrbStack 사용이 좋을 수 있습니다."
  echo "> https://orbstack.dev"
else
  echo -e "\n\033[34m"Docker desktop 설치"\033[0m"
  install_docker
fi

######################
# DO NOT ERASE !!
IFS=$BACKUP_IFS # IFS 원복
######################