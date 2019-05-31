- 更新版本步骤
1. 切换到master分支
2. 更新extVersionCode & extVersionName
3. ./gradlew pushJsonVersion
pushJsonVersion脚本作用：打包release并push apk,修改MyGodData中version.json版本号以及md5值

- task调用顺序
```
task A{
    doLast{
        print "AAAA"
    }
}

task B{
    doLast{
        print "BBBB"
    }
}

task C{
    dependsOn B
    dependsOn A
    doLast{
        print "CCCC"
    }
}
```
以上代码的执行结果为
```
> Task :A
AAAA
> Task :B
BBBB
> Task :C
CCCC
```
即：不是以dependsOn的顺序为准，而是以task申明顺序为准