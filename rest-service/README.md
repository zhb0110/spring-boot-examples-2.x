尝试构建一个 RESTful Web 服务。JSON 表示形式进行响应(默认使用jackson)，创建资源类、控制器类，并打包jar，连数据库/生成实体类
并测试了以下maven依赖：
开发者工具(改动代码自动重启)：spring-boot-devtools
打包：spring-boot-maven-plugin
监控：spring-boot-starter-actuator


# 效果

访问地址： localhost:8080/greeting
![img.png](img.png)

# 效果2：

访问地址： localhost:8080/greeting?name=User
![img_1.png](img_1.png)

## 开发者工具(改动代码自动重启) spring-boot-devtools

需配置idea，请看语雀文档

## 打包：spring-boot-maven-plugin
命令：
mvn clean package

如果不喜欢默认的项目名+版本号作为文件名，可以加一个配置指定文件名
<project ...>
...
<build>
<finalName>awesome-app</finalName>
...
</build>
</project>
这样打包后文件名就是awesome-app.jar

## 监控：spring-boot-starter-actuator
Spring Boot提供了一个Actuator(内置)，可以方便地实现监控，并可通过Web访问特定类型的监控。

## 连数据库/生成实体类，但有缺陷，还需深度处理下
![img_2.png](img_2.png)
![img_3.png](img_3.png)



