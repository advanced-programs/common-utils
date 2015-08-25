
# 常用开发工具类

> 基于Java实现。

### 使用方式

* 各项目在pom.xml中增加以下内容：

```java
<project ...>
        ...
        
        <!-- 依赖包 -->
        <dependency>
            <groupId>zx.soft</groupId>
            <artifactId>common-utils</artifactId>
            <version>1.0.0</version>
        </dependency>

        ...

        <!-- 指定公司私有Maven仓库地址，以便下载该jar包 -->
        <repositories>
	        <repository>
		        <id>zxsoft-public</id>
		        <name>Nexus Release Repository</name>
		        <url>http://192.168.3.23:18081/nexus/content/groups/public/</url>
	        </repository>
        </repositories>

        ...
</project>
```

### 注意事项

> ProgramDriver目前针对RESTlet启动的接口服务不支持，会导致接口启动不成功。

### 开发人员

WeChat: wgybzb

QQ: 1010437118

E-mail: wgybzb@sina.cn
