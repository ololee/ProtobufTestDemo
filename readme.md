以前也只是某本书中看过Protobuf的字样，但是从未使用过。今天偶然看到一个google的开源项目里有不少的protobuf。然后就搜索了一下这是一个什么东西，结果原理看起来真是让人头大。寻思着先写一个apk试试怎么用吧，结果发现坑也是不少。
# 环境
- Android Studio Dolphin | 2021.3.1 Patch 1
- gradle version: 7.3.1
- gradle distributionUrl=https://services.gradle.org/distributions/gradle-7.4-bin.zip

# gradle配置
- 修改项目的`build.gradle`
```
buildscript {
  dependencies {
    classpath 'com.google.protobuf:protobuf-gradle-plugin:0.8.18'
  }
}

plugins {
 ...
}
```
- 修改app这个Module下的`build.gradle`
```
plugins {
  id 'com.android.application'
  id 'com.google.protobuf'
}

android {
  ...
  sourceSets{
    main{
      proto {
        //指定proto文件位置,你的proto文件放置在此文件夹中
        srcDir 'src/main/proto'
      }
    }
  }

  viewBinding{
    enabled = true
  }
}

dependencies {
  ...
  implementation 'com.google.protobuf:protobuf-java:3.21.9'
  implementation 'com.google.protobuf:protoc:3.21.9'
  ...
}

protobuf {
  protoc {
    artifact = 'com.google.protobuf:protoc:3.21.9' // 也可以配置本地编译器路径
  }

  generateProtoTasks {
    all().each { task ->
      task.builtins {
        remove java
      }
      task.builtins {
        java {}// 生产java源码
      }
    }
  }
}
```
- 在`app/src/main/`下新建`person.proto`
```protobuf
syntax = "proto3";
package proto;
option java_package = "cn.ololee.protobuftestdemo";
option java_outer_classname = "PersonProto";
message Person{
  string name = 1;
  int32 age = 2;
  bool sex = 3;
  string address = 4;
  string email = 5;
}
```
- rebuild 然后载java中就可以使用`cn.ololee.protobuftestdemo.PersonProto`这个类了
```java
PersonProto.Person person = PersonProto.Person.newBuilder()
    .setName("ololee")
    .setAge(25)
    .setSex(true)
    .setAddress("中国四川省成都市武侯区")
    .setEmail("ololeecn@gmail.com")
    .build();
binding.textView.setOnClickListener(v -> {
  binding.textView.setText(person.getAddress());
});
```

# 传送门 

[github: https://github.com/ololee/ProtobufTestDemo](https://github.com/ololee/ProtobufTestDemo)

# 参考

- [知乎专栏-protobuf详解](https://zhuanlan.zhihu.com/p/432875529)
- [github-protobuf-java](https://github.com/protocolbuffers/protobuf/tree/main/java)
- [Protobuf协议实现原理](https://www.jianshu.com/p/105d6effc279)
- [protobuf从听过到了解](https://www.jianshu.com/p/4d5fd24044ff)
- [google -Protocol Buffers](https://developers.google.com/protocol-buffers)
- [protobuf 基本使用介绍](http://www.45fan.com/article.php?aid=1COO95Vicv6G1uXx)
- [Android中使用protobuf](https://blog.csdn.net/cat_is_so_cute/article/details/122729234)
- [Protobuf在Android中的基本使用](https://blog.csdn.net/cat_is_so_cute/article/details/122729234)