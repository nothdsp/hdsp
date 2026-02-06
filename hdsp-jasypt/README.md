# 敏感信息加密

## 一、如何使用

1. 在依赖中添加hdsp-jasypt

```xml
<dependency>
    <groupId>io.github.nothdsp</groupId>
    <artifactId>hdsp-jasypt</artifactId>
</dependency>
```

2. 在配置文件中使用ENC()包裹的加密值

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC
    username: root
    password: ENC(AiB5j9610k7MPIaFVytJC834EwGcp9gh)
```

## 二、如何加密

1. 在依赖中添加插件jasypt-maven-plugin

```xml
<plugin>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-maven-plugin</artifactId>
</plugin>
```

2. 执行加密命令加密单个值

```bash
mvn jasypt:encrypt-value -Djasypt.encryptor.password="AiB5j9610k7MPIaFVytJC834EwGcp9gh" -Djasypt.plugin.value="明文"
```

3. 执行加密命令加密文件
   文件中待加密的敏感信息项需要使用DEC()包裹，加密后的值使用ENC()包裹

```bash
mvn jasypt:encrypt -Djasypt.encryptor.password="AiB5j9610k7MPIaFVytJC834EwGcp9gh" -Djasypt.plugin.path=file:src/main/resources/application.yml
```

## 三、如何解密

1. 执行解密命令解密单个值

```bash
mvn jasypt:decrypt-value -Djasypt.encryptor.password="AiB5j9610k7MPIaFVytJC834EwGcp9gh" -Djasypt.plugin.value="ENC(密文)"
```

2. 执行解密命令解密文件

```bash
mvn jasypt:decrypt -Djasypt.encryptor.password="AiB5j9610k7MPIaFVytJC834EwGcp9gh" -Djasypt.plugin.path=file:src/main/resources/application.yml
```
