#!/bin/bash 
# 在办公机上必须用bash下执行才能成功。
# 主要是环境变量的问题，手工导出也是可以的。
# export JAVA_HOME=/opt/jdk1.8.0_102/
all:
	/opt/gradle-3.3/bin/gradle build makeApk

#自动生成了相应的apk文件并重新命名。
