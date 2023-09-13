import groovy.transform.Field
// 預設變數 建立 指令
@Field def BUILDCMD = "build.sh"
// 預設變數 HARBOR 主機
@Field def HARBOR = ""
// 預設變數 jar 的輸出目錄
@Field def OUTPUT = "target"
// 預設變數 log file 的輸出目錄
@Field def OUTPUT_LOG = "log"
// 預設變數 專案名稱
@Field def PROJECT_NAME = "q91/apifront"
// 預設變數 版號
@Field def DEPLOY_VER = ""
@Field def COMMIT_LOG = ""
// 附加版號到 JOB 的顯示名稱, 設定預設變數
// 依 pom.xml 的內容
def UpdateJobName() {
    script {
        // 讀取 pom.xml 的 version
        if ( ! DEPLOY_VER ) {
            def pkg = readMavenPom file: "pom.xml"
            // 只取 "-" 前的字串為版本
            DEPLOY_VER = pkg.version.tokenize("-")[0]
            if ( DEPLOY_VER ) {
                echo "get DEPLOY_VER = $DEPLOY_VER from pom.xml"
            }
        } 
        currentBuild.displayName = '#' + env.BUILD_NUMBER + ' v' + DEPLOY_VER + ' ' + PROJECT_NAME
        // 讀取 $BUILDCMD 的 HARBOR 主機名稱
        if ( ! HARBOR ) {
            HARBOR = sh(returnStdout: true, script: "grep 'HOST=' $BUILDCMD | sed 's/HOST=//g'").trim()
            if ( HARBOR ) {
                echo "get HARBOR = $HARBOR from $BUILDCMD"
                // 登入 HARBOR
                withCredentials([usernamePassword(
                    credentialsId: 'dockerjava', 
                    usernameVariable: 'USER', passwordVariable: 'PASS'
                    )]) {
                    // TOKEN 要這樣組合字串, 才不會有警告
                    loginCmd = ' -u ' + USER + ' -p ' + PASS
                    sh 'docker login ' + loginCmd + ' ' + HARBOR
                }
            }
        }
        // 當不是手動建立時, 檢查 docker image 己存在則中斷
        def isTriggeredByUser =  currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').size()
        if ( isTriggeredByUser == 0 ) {
            def IMAGE="$HARBOR/java/$PROJECT_NAME:$DEPLOY_VER"
            def IMAGE_FOUND = sh(returnStdout: true, script: "docker manifest inspect $IMAGE > /dev/null && echo exist || echo ").trim()
            if ( IMAGE_FOUND ) {
                def errorMsg = "skip by $PROJECT_NAME:$DEPLOY_VER is existed"
                currentBuild.description = errorMsg + " ! " + currentBuild.description
                currentBuild.result = 'ABORTED'
                error errorMsg
            }
        }
        // 檢查有沒有前一次的成功的 COMMIT
        if (COMMIT_LOG == "" && env.GIT_PREVIOUS_SUCCESSFUL_COMMIT != "null" && env.GIT_COMMIT) {
            def logCmd = 'git log ' + env.GIT_PREVIOUS_SUCCESSFUL_COMMIT 
            logCmd += '..' + env.GIT_COMMIT
            logCmd += ' --date=short --pretty=format:"%cd %s"'
            logCmd += " | awk '{print NR " + '". "' + ' $0}' + "'"
            // 產生變更記錄
            COMMIT_LOG = sh(returnStdout: true, script: logCmd).trim()
            echo COMMIT_LOG
        }
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
        MSG += "\nBRANCH = ${GIT_BRANCH}"
        MSG += "\nCOMMIT = ${GIT_COMMIT}"
        // 當不是 qa 或 vip 時, 顯示變更記錄
        def showLog = false
        if ( env.BUILD_ENV != 'dev' && env.BUILD_ENV != 'qa' ) {
            showLog = true
        }
        // 可用 ENV 指定 SHOW_LOG 強制顯示
        if (showLog || env.SHOW_LOG != null) {
            if (COMMIT_LOG) {
                MSG += "\n\n" + COMMIT_LOG
            }
        }
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
pipeline {
    agent any
    options {
        // 建置保留次數 5
        buildDiscarder(logRotator(numToKeepStr: '5'))
        // 不使用預設的 Checkout
        skipDefaultCheckout()
    }
    triggers {
        gitlab(
            // 只接受 git push 的事件
            triggerOnPush: true,
            triggerOnMergeRequest: false,
            triggerOnNoteRequest: false,
            // 過濾可觸發的 branch 依名稱
            //branchFilterType: "NameBasedFilter",
            //includeBranchesSpec: "develop,master",
            // 過濾可觸發的 branch 依 regex
            branchFilterType: "RegexBasedFilter",
            // 開發中, 先加入 main
            targetBranchRegex: "(main|develop|dev_.*)",
            // 給 gitlab 用的 token
            secretToken: "c135dd01784a6debdb7de814ccf0a67a"
        )
    }
    // 參數 所使用的 branch
    parameters {
        gitParameter (branch:'', 
            name: 'BRANCH', 
            description: '選擇BRANCH', 
            branchFilter: 'origin/(.*)', 
            defaultValue: 'develop', 
            quickFilterEnabled: true, 
            selectedValue: 'TOP', 
            sortMode: 'DESCENDING_SMART', 
            tagFilter: '*', 
            type: 'PT_BRANCH_TAG', 
            useRepository: env.GIT_URL
        )
    }
    // 預先安裝工具
    tools { 
		jdk "JDK8"
		maven "MAVEN3"
    	dockerTool "DOCKER"
	}
    stages {
        stage('下載') {
            steps {
                script {
                    def branch = env.BRANCH
                    // 當由 gitlab 發起時, 改用發起的目標 branch
                    if (env.gitlabTargetBranch  != null ) {
                        branch = env.gitlabTargetBranch
                    }
                    // 用 git 取得 source
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        branches: [[name: branch]],
                        doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                        extensions: scm.extensions,
                        userRemoteConfigs: scm.userRemoteConfigs
                    ])
                    // 設定 ENV 變數
                    env.GIT_URL = scmVars.GIT_URL
                    env.GIT_BRANCH = scmVars.GIT_BRANCH
                    env.GIT_COMMIT = scmVars.GIT_COMMIT
                    env.GIT_PREVIOUS_COMMIT = scmVars.GIT_PREVIOUS_COMMIT
                    env.GIT_PREVIOUS_SUCCESSFUL_COMMIT = scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT
                    //echo sh(script: 'env|sort', returnStdout: true)
                }
            }
            post {
                unsuccessful {
                    SendTG("😡 $STAGE_NAME 失敗")
                }
            }
        }
        stage('建置') {
            steps {
                UpdateJobName()
                // 建置腳本
                sh "sh $BUILDCMD -b"
            }
            post {
                // 成功建置
                success{
                    // 保存佈署檔
                    script {
                        archiveArtifacts artifacts: "$OUTPUT/*.jar"
                        archiveArtifacts artifacts: "$OUTPUT_LOG/*"
                        archiveArtifacts artifacts: "pom.xml"
                    }
                    // 刪除舊檔
                    dir(OUTPUT) { deleteDir() }
                    dir(OUTPUT_LOG) { deleteDir() }
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
        stage('推送') {
            steps {
                // 刪除舊檔
                dir(OUTPUT) { deleteDir() }
                // 取出佈署檔 
                unarchive mapping: ["pom.xml": ""]
                UpdateJobName()
                unarchive mapping: ["$OUTPUT/": ""]
                unarchive mapping: ["$OUTPUT_LOG/": ""]
                script {
                    echo "env is ${env.BUILD_ENV}"
                    // 建置腳本
                    sh "sh $BUILDCMD -s -p"
                }
                // 刪除舊檔
                dir(OUTPUT) { deleteDir() }
                dir(OUTPUT_LOG) { deleteDir() }
            }
            post {
                success{
                    SendTG("😀 $STAGE_NAME 成功")
                }
                unsuccessful {
                    SendTG("😡 $STAGE_NAME 失敗")
                }
            }
        }
        stage('觸發工作') {
            // 當有指定要觸發的 JOB 名稱時才執行
            when {
                expression {
                    return env.RUNJOB != null;
                }
            }
            steps {
                // 帶入版本號
                build job: env.RUNJOB ,parameters: [[$class: 'StringParameterValue', name: 'IMAGE_TAG', value: "$DEPLOY_VER"]]
            }
            post {
                unsuccessful {
                    SendTG("😡 $STAGE_NAME 失敗")
                }
            }
        }
    }
}

