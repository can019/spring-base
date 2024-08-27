# Spring-base ![Coverage](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=coverage) ![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=reliability_rating) ![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=security_rating) ![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=sqale_rating) ![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=vulnerabilities) ![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=can019_spring-base&metric=code_smells)

프로젝트 설명...


- 개인 프로젝트에 사용 될 Spring-boot boiler plate입니다.
- Template repository로 설정되어있습니다.

## 특징
### 🌐 전역적, 유지보수가 쉬운 에러 핸들링
- 모든 에러에 대한 [RFC 7807](https://datatracker.ietf.org/doc/html/rfc7807) spec response
 
### 🚪 Test Container
- Test Container를 통해 격리된 한경에서 test 수행이 가능합니다.

### 📝 Github pages
- Test 정보, api명세서등 document는 release가 publish되면 [Github page](https://can019.github.io/spring-base)에 자동으로 upload됩니다.

### ⚙️ Release note 자동화

- Release가 되면 자동으로 workflow가 draft를 작성해줍니다.

### ⚙️ Sonar cloud를 통한 정적 분석

- CI에서 Sonar cloud를 이용해 정적 분석을 진행합니다.
  - 해당 PR에서 추가된 source code에 대해 평가합니다.

### Test 수행 시간 측정 및 export report
- [총 수행 시간, method별 수행 시간 측정 후 csv로 export 하는 TestExecutionListener](https://github.com/can019/spring-base/pull/58)


## 프로젝트 환경
- Java version: 21
  - distribution: 'temuri
- Gradle version: 8.7

## 환경변수
### 유효 프로퍼티
- local_docker
- local
- dev
- prod
- test

### 환경변수 세팅
#### Intellij community
> ![intellij-community-env](./docs/resource/intellij_comunity_env_set.png)

## 초기 설정
### Github pages
main branch에서 아래 명령어를 입력 해 gh-pages branch로 checkout 합니다.

```shell
git checkout --orphan gh-pages
```
> [!IMPORTANT]
> gh-pages branch는 orphan branch입니다.

아래 명령어를 통해 git에 캐시된 정보를 모두 삭제합니다.
```shell
git rm --cached -r .
```
아래 명령어를 통해 Github pages 구성에 필요한 artifacts를 docs로 이동 합니다.
```shell
rm -rf docs
mkdir docs
cp -R gh-pages/docs ./
```
swagger-ui/dist/swagger-initializer.js를 열어 아래와 같이 수정합니다.
```javascript
window.onload = function() {
  window.ui = SwaggerUIBundle({
    ...
    configUrl:"https://github-id.github.io/repository-name/swagger-ui/swagger-config.yaml",
    ...
  });
};
```

gh-pages에 push
```shell
git add docs
git commit -m "init"
git push origin gh-pages
```
이 다음 repository setting에 들어가서 github pages를 설정합니다.

> ![github-repo-setting](./docs/resource/gh-pages-repo-setting.png)

> ![github-gh-pages-deploy-setting](./docs/resource/gh-pages-deploy-setting.png)

### Gradle configuration cache 활성화
Configuration cache를 활성화 하는 경우 `gradle-transforms`을 캐싱할 수 있습니다.

> [!IMPORTANT]
> Github action secret에 GradleEncryptionKey이란 이름으로 secret key를 등록해야 활성화 됩니다.
>  - Secret key가 없어도 정상 작동 합니다. 다만 configure cache는 무시됩니다.

`ci.yml`에서 아래 부분이 관련 코드입니다.
```yaml
      - name: Set up gradle with gradle cache
        uses: gradle/actions/setup-gradle@v3
        continue-on-error: true
        with:
          # ...
          cache-encryption-key: ${{ secrets.GradleEncryptionKey }} # << github action secret 등록해야 활성화

      - name: Build
        run: gradle build --configuration-cache --info
```

> [!IMPORTANT]
> configuration cache를 암호화 시 AES-128  AES-192, AES-256을 기반으로 하기 때문에 16byte, 24byte, 32byte 중 하나로 secret을 설정해야 합니다.


### Docker (설치)
- mac x86에서만 확인되었습니다.

`./init.sh` 실행

``` shell
./init.sh
```

### Sonar cloud
[Sonar cloud repository 설정](https://chaerim1001.tistory.com/94)
