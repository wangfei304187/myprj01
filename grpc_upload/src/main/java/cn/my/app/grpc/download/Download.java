// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: download.proto

package cn.my.app.grpc.download;

public final class Download {
  private Download() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_download_DownloadRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_download_DownloadRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_download_DownloadResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_download_DownloadResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_download_Chunk_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_download_Chunk_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_download_DownloadStatus_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_download_DownloadStatus_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016download.proto\022\010download\"\037\n\017DownloadRe" +
      "quest\022\014\n\004name\030\001 \001(\t\"r\n\020DownloadResponse\022" +
      "\014\n\004name\030\001 \001(\t\022\036\n\005chunk\030\002 \001(\0132\017.download." +
      "Chunk\0220\n\016downloadStatus\030\003 \001(\0132\030.download" +
      ".DownloadStatus\"*\n\005Chunk\022\017\n\007content\030\001 \001(" +
      "\014\022\020\n\010blockSeq\030\002 \001(\005\"/\n\016DownloadStatus\022\014\n" +
      "\004code\030\001 \001(\005\022\017\n\007message\030\002 \001(\t2X\n\017Download" +
      "Service\022E\n\010download\022\031.download.DownloadR" +
      "equest\032\032.download.DownloadResponse\"\0000\001B!" +
      "\n\027cn.my.app.grpc.downloadP\001\242\002\003DLDb\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_download_DownloadRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_download_DownloadRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_download_DownloadRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_download_DownloadResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_download_DownloadResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_download_DownloadResponse_descriptor,
        new java.lang.String[] { "Name", "Chunk", "DownloadStatus", });
    internal_static_download_Chunk_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_download_Chunk_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_download_Chunk_descriptor,
        new java.lang.String[] { "Content", "BlockSeq", });
    internal_static_download_DownloadStatus_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_download_DownloadStatus_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_download_DownloadStatus_descriptor,
        new java.lang.String[] { "Code", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}