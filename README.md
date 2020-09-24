# yitu-speech

#### 介绍
依图实时语音转写
依图实时语音转写通过与转写引擎建立长连接，提供实时的文字输出能力。
实时语音转写的客户端需使用gRPC接口与Protobuf协议与服务器端进行通信。

#### 软件架构
Java、Swing、gRPC、protobuf


#### 安装教程

1.  生成model #protoc.exe --java_out=./ asr_streaming.proto
2.  生成service #protoc.exe --plugin=protoc-gen-grpc-java=./protoc-gen-grpc-java-1.32.1-windows-x86_32.exe --grpc-java_out=./ asr_streaming.proto


#### 使用说明


