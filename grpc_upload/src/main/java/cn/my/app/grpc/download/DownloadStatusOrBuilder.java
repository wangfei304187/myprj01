// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: download.proto

package cn.my.app.grpc.download;

public interface DownloadStatusOrBuilder extends
    // @@protoc_insertion_point(interface_extends:download.DownloadStatus)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 0--unknown, 1--ok, 2-failure
   * </pre>
   *
   * <code>int32 code = 1;</code>
   * @return The code.
   */
  int getCode();

  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}
