import groovy.transform.Field
// 預設變數 暫存檔前綴
@Field def OUTPUT = "tmp-docker"
// 預設變數 專案名稱
@Field def PROJECT_NAME = "q91/apifront"
// 預設變數 版號
@Field def DEPLOY_VER = ""
// 預設變數 docker 的 stack 名稱
@Field def STACK_NAME = "apifront"
// 預設變數 portainer 的 URL
@Field def PORTAINER = " https://docker-ptn.icchance.com.tw:9443/api"
// 預設變數 docker 的 stack Id
@Field def STACK_ID = ""
// 預設變數 docker 的 EndpointId
@Field def STACK_ENDPOINT = ""
// 預設變數 檢查開始更新的最大等待秒數 (2分)
@Field def MAX_START_SEC = 120
// 預設變數 檢查更新完成的最大等待秒數 (15分)
@Field def MAX_UPDATE_SEC = 900
// 附加版號到 JOB 的顯示名稱, 設定預設變數
def UpdateJobName() {
    script {
        if ( DEPLOY_VER == "" ) {
            if ( env.IMAGE_TAG ) {
                DEPLOY_VER = env.IMAGE_TAG
            }
        }
        // 沒有輸入 tag 時中斷
        if ( DEPLOY_VER == "" ) {
            RaiseAbort("skip by docker image tag is ''")
        }
        currentBuild.displayName = '#' + env.BUILD_NUMBER + ' v' + DEPLOY_VER + ' ' + PROJECT_NAME
        // 用 stack 名稱取得 Id EndpointId
        // 保存 stack 狀態到 ${OUTPUT}_stack.json
        withCredentials([string(credentialsId: 'portainer_token', variable: 'TOKEN')]) {
            // TOKEN 要這樣組合字串, 才不會有警告
            APIURI = 'curl -k -H "X-API-Key: $TOKEN"'
            APIURI += ' ' + PORTAINER + '/stacks'
            APIURI += " | jq -je '.[] | select (.Name == \"" + STACK_NAME + "\")' "
            sh APIURI + " > " + OUTPUT + "_stack.json"
        }
        def stack = readJSON file: OUTPUT + "_stack.json"
        STACK_ID = stack.Id
        STACK_ENDPOINT = stack.EndpointId
        if ( ! STACK_ID ) {
            error "stack Id is empty !"
        }
        echo "STACK_ID = " + STACK_ID + ' ' + "STACK_ENDPOINT = " + STACK_ENDPOINT
    }
}
// TG 通知 
// 依系統變數 tg_token 取得 BOT token
// 依環境變數 決定收件者 TG_CHAT_ID 或 TG_CHAT_ID_LOCAL
def SendTG(MSG) {
    script {
        MSG += " <a href='${BUILD_URL})'>${JOB_BASE_NAME} #${BUILD_NUMBER}</a>"
        if ( currentBuild.description ) {
            MSG += "\nDESC = ${currentBuild.description}"
        }
        MSG += "\nUSER = ${BUILD_USER}"
        MSG += "\nIMAGE = " + PROJECT_NAME + ':' + DEPLOY_VER
        try {
            // 先檢查 JOB 的 TG群組ID 有沒有指定在ENV
            CHATID = ''
            if (env.TG_CHAT_ID_LOCAL == null ) {
                // 使用全域的 TG群組ID
                if (env.TG_CHAT_ID == null ) {
                    error('$TG_CHAT_ID not config')
                } else {
                    CHATID = env.TG_CHAT_ID
                }
            } else {
                CHATID = env.TG_CHAT_ID_LOCAL
            }
            withCredentials([string(credentialsId: 'tg_token', variable: 'TOKEN')]) {
                // TOKEN 要這樣組合字串, 才不會有警告
                APIURI = ' https://api.telegram.org/bot$TOKEN/sendMessage'
                APIURI += ' -d chat_id=' + CHATID
                APIURI += ' -d parse_mode=HTML'
                APIURI += ' -d disable_web_page_preview=true'
                echo MSG
                sh 'curl -s -X POST' + APIURI + """ -d text="$MSG" """
            }
        } catch (err) {
            echo "skip send message"
            echo err.getMessage()
        }
    }
}
// 依 STACK_NAME 取得 Image 列表
def getContainer() {
    script {
        // 用 stack 名稱取得 
        withCredentials([string(credentialsId: 'portainer_token', variable: 'TOKEN')]) {
            // TOKEN 要這樣組合字串, 才不會有警告
            APIURI = 'curl -k -H "X-API-Key: $TOKEN"'
            APIURI += ' ' + PORTAINER + '/endpoints/' + STACK_ENDPOINT + '/docker/containers/json'
            APIURI += " | jq '.[] | select (.Labels.\"com.docker.stack.namespace\" == \"" + STACK_NAME + "\") | .Image' "
            APIURI += " | sed 's|.*/" + PROJECT_NAME + "|" + PROJECT_NAME + "|g;s|@.*||g;s|\"\$||g'"
            APIURI += " | grep " + PROJECT_NAME
            // 返回 list 
            return sh(script: APIURI, returnStdout: true).tokenize("\n")
        }
    }
}
// 觸發中斷
def RaiseAbort(MSG, RESULT ='ABORTED') {
    def errorMsg = MSG
     if ( currentBuild.description != null ) {
        currentBuild.description = errorMsg + " ! " + currentBuild.description
    } else {
        currentBuild.description = errorMsg + " ! "
    }
    if ( RESULT != "" ) {
        currentBuild.result = RESULT
    }
    error errorMsg
}

pipeline {
    agent any
    options {
        // 建置保留次數 5
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    // 參數 所使用的 IMAGE 版號TAG
    parameters { 
        string(name: 'IMAGE_TAG', 
            defaultValue: '', 
            description: 'DOKCER IMAGE ' + PROJECT_NAME + ' 的版號TAG'
        ) 
    }
    stages {
        stage('下載設定') {
            steps {
                UpdateJobName()
                script {
                    // 下載舊的設定檔到 ${OUTPUT}_stack.yml
                    withCredentials([string(credentialsId: 'portainer_token', variable: 'TOKEN')]) {
                        // TOKEN 要這樣組合字串, 才不會有警告
                        APIURI = 'curl -ks '
                        APIURI += ' -H "X-API-Key: $TOKEN"'
                        APIURI += ' ' + PORTAINER + '/stacks/' + STACK_ID + '/file'
                        APIURI += ' | jq -r ".StackFileContent" '
                        sh APIURI + " > " + OUTPUT + "_stack.yml"
                    }
                    // 更新的設定檔到 ${OUTPUT}_stack_update.yml
                    def sedCmd = "sed 's|/$PROJECT_NAME[:][a-zA-Z0-9.]*|/$PROJECT_NAME:$DEPLOY_VER|g'"
                    sh "${sedCmd} ${OUTPUT}_stack.yml > ${OUTPUT}_stack-update.yml"
                }
            }
            post {
                // 成功建置
                success{
                    // 保存佈署檔
                    script {
                        archiveArtifacts artifacts: "${OUTPUT}_*"
                    }
                }
                unsuccessful {
                    script {
                        if ( currentBuild.result == 'ABORTED' ) {
                            SendTG("😴 $STAGE_NAME 中斷")
                        } else {
                            SendTG("😡 $STAGE_NAME 失敗")
                        }
                    }
                }
            }
        }
        stage('更新設定') {
            steps {
                // 取出佈署檔 
                UpdateJobName()
                unarchive mapping: ["${OUTPUT}_*": ""]
                script {
                    def diffText = sh(returnStdout: true, script: "diff -urN ${OUTPUT}_stack.yml ${OUTPUT}_stack-update.yml || exit 0").trim()
                    // 顯示 差異
                    if ( diffText != "" ) {
                        echo diffText
                    }
                        withCredentials([string(credentialsId: 'portainer_token', variable: 'TOKEN')]) {
                            // TOKEN 要這樣組合字串, 才不會有警告
                            APIURI = "jq -Rs '{ stackFileContent: . }' " + OUTPUT + "_stack-update.yml"
                            APIURI += ' | curl -k -X PUT'
                            APIURI += ' -H "X-API-Key: $TOKEN"'
                            APIURI += ' -H "Content-Type: application/json"'
                            APIURI += ' ' + PORTAINER + '/stacks/' + STACK_ID + '?endpointId=' + STACK_ENDPOINT
                            APIURI += ' -d "$(cat)"'
                            APIURI += ' | jq '
                            sh APIURI
                        }
                    //} else {
                    //    RaiseAbort("skip by stack config is the same")
                    //}
                }
            }
            post {
                success{
                    SendTG("😀 $STAGE_NAME 成功")
                }
                unsuccessful {
                    script {
                        if ( currentBuild.result == 'ABORTED' ) {
                            SendTG("😴 $STAGE_NAME 中斷")
                        } else {
                            SendTG("😡 $STAGE_NAME 失敗")
                        }
                    }
                }
            }
        }
        stage('更新檢查') {
            steps {
                // 取出佈署檔 
                UpdateJobName()
                script {
                    // 取得線上容器的IMAGE列表
                    def images = getContainer()
                    echo "online container count : " + images.size()
                    // 更新的 IMAGE 名稱
                    def newImage = PROJECT_NAME + ':' + DEPLOY_VER
                    def updateStart = false
                    def updateSuccess = false
                    def waitSec = 0
                    // 是否開始更新, 檢查只要有一個 image 是換成新的
                    while (waitSec <= MAX_START_SEC) {
                        images = getContainer()
                        for (int i = 0; i < images.size(); i++) {
                            echo images[i] + " ==> " + newImage
                            if ( images[i] == newImage ) {
                                updateStart = true
                            }
                        }
                        if ( updateStart ) {
                            waitSec = MAX_START_SEC + 1
                        } else {
                            waitSec += 5
                            sleep(5)
                        }
                    }
                    // 更新未開始
                    echo "updateStart = " + updateStart
                    if ( ! updateStart ) {
                        RaiseAbort("docker stack update don't start", "")
                    } else {
                        SendTG("😀 $STAGE_NAME 開始容器更新")
                    }
                    // 是否全都更新完成, 檢查所有的 image 是換成新的
                    waitSec = 0
                    while (waitSec <= MAX_UPDATE_SEC) {
                        images = getContainer()
                        def allUpdate = true
                        for (int i = 0; i < images.size(); i++) {
                            echo images[i] + " ==> " + newImage
                            if (images[i] != newImage) {
                                allUpdate = false
                            }
                        }
                        if ( allUpdate ) {
                            waitSec = MAX_UPDATE_SEC +1
                            updateSuccess = true
                        } else {
                            waitSec += 5
                            sleep(5)
                        }
                    }
                    echo " updateSuccess = " + updateSuccess
                    if ( ! updateSuccess ) {
                        RaiseAbort("docker stack update fail", "")
                    }
                }
            }
            post {
                success{
                    SendTG("😀 $STAGE_NAME 容器更新成功")
                }
                unsuccessful {
                    SendTG("😡 $STAGE_NAME 失敗")
                }
            }
        }
    }
}
