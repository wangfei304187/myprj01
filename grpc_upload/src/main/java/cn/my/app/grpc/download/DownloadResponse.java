// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: download.proto

package cn.my.app.grpc.download;

/**
 * Protobuf type {@code download.DownloadResponse}
 */
public final class DownloadResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:download.DownloadResponse)
    DownloadResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DownloadResponse.newBuilder() to construct.
  private DownloadResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DownloadResponse() {
    name_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DownloadResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private DownloadResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 18: {
            cn.my.app.grpc.download.Chunk.Builder subBuilder = null;
            if (chunk_ != null) {
              subBuilder = chunk_.toBuilder();
            }
            chunk_ = input.readMessage(cn.my.app.grpc.download.Chunk.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(chunk_);
              chunk_ = subBuilder.buildPartial();
            }

            break;
          }
          case 26: {
            cn.my.app.grpc.download.DownloadStatus.Builder subBuilder = null;
            if (downloadStatus_ != null) {
              subBuilder = downloadStatus_.toBuilder();
            }
            downloadStatus_ = input.readMessage(cn.my.app.grpc.download.DownloadStatus.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(downloadStatus_);
              downloadStatus_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.my.app.grpc.download.Download.internal_static_download_DownloadResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.my.app.grpc.download.Download.internal_static_download_DownloadResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.my.app.grpc.download.DownloadResponse.class, cn.my.app.grpc.download.DownloadResponse.Builder.class);
  }

  public static final int NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   * file name with ext
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * file name with ext
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CHUNK_FIELD_NUMBER = 2;
  private cn.my.app.grpc.download.Chunk chunk_;
  /**
   * <code>.download.Chunk chunk = 2;</code>
   * @return Whether the chunk field is set.
   */
  @java.lang.Override
  public boolean hasChunk() {
    return chunk_ != null;
  }
  /**
   * <code>.download.Chunk chunk = 2;</code>
   * @return The chunk.
   */
  @java.lang.Override
  public cn.my.app.grpc.download.Chunk getChunk() {
    return chunk_ == null ? cn.my.app.grpc.download.Chunk.getDefaultInstance() : chunk_;
  }
  /**
   * <code>.download.Chunk chunk = 2;</code>
   */
  @java.lang.Override
  public cn.my.app.grpc.download.ChunkOrBuilder getChunkOrBuilder() {
    return getChunk();
  }

  public static final int DOWNLOADSTATUS_FIELD_NUMBER = 3;
  private cn.my.app.grpc.download.DownloadStatus downloadStatus_;
  /**
   * <code>.download.DownloadStatus downloadStatus = 3;</code>
   * @return Whether the downloadStatus field is set.
   */
  @java.lang.Override
  public boolean hasDownloadStatus() {
    return downloadStatus_ != null;
  }
  /**
   * <code>.download.DownloadStatus downloadStatus = 3;</code>
   * @return The downloadStatus.
   */
  @java.lang.Override
  public cn.my.app.grpc.download.DownloadStatus getDownloadStatus() {
    return downloadStatus_ == null ? cn.my.app.grpc.download.DownloadStatus.getDefaultInstance() : downloadStatus_;
  }
  /**
   * <code>.download.DownloadStatus downloadStatus = 3;</code>
   */
  @java.lang.Override
  public cn.my.app.grpc.download.DownloadStatusOrBuilder getDownloadStatusOrBuilder() {
    return getDownloadStatus();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    if (chunk_ != null) {
      output.writeMessage(2, getChunk());
    }
    if (downloadStatus_ != null) {
      output.writeMessage(3, getDownloadStatus());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    if (chunk_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getChunk());
    }
    if (downloadStatus_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getDownloadStatus());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof cn.my.app.grpc.download.DownloadResponse)) {
      return super.equals(obj);
    }
    cn.my.app.grpc.download.DownloadResponse other = (cn.my.app.grpc.download.DownloadResponse) obj;

    if (!getName()
        .equals(other.getName())) return false;
    if (hasChunk() != other.hasChunk()) return false;
    if (hasChunk()) {
      if (!getChunk()
          .equals(other.getChunk())) return false;
    }
    if (hasDownloadStatus() != other.hasDownloadStatus()) return false;
    if (hasDownloadStatus()) {
      if (!getDownloadStatus()
          .equals(other.getDownloadStatus())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    if (hasChunk()) {
      hash = (37 * hash) + CHUNK_FIELD_NUMBER;
      hash = (53 * hash) + getChunk().hashCode();
    }
    if (hasDownloadStatus()) {
      hash = (37 * hash) + DOWNLOADSTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getDownloadStatus().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.my.app.grpc.download.DownloadResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(cn.my.app.grpc.download.DownloadResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code download.DownloadResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:download.DownloadResponse)
      cn.my.app.grpc.download.DownloadResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.my.app.grpc.download.Download.internal_static_download_DownloadResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.my.app.grpc.download.Download.internal_static_download_DownloadResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.my.app.grpc.download.DownloadResponse.class, cn.my.app.grpc.download.DownloadResponse.Builder.class);
    }

    // Construct using cn.my.app.grpc.download.DownloadResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      name_ = "";

      if (chunkBuilder_ == null) {
        chunk_ = null;
      } else {
        chunk_ = null;
        chunkBuilder_ = null;
      }
      if (downloadStatusBuilder_ == null) {
        downloadStatus_ = null;
      } else {
        downloadStatus_ = null;
        downloadStatusBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.my.app.grpc.download.Download.internal_static_download_DownloadResponse_descriptor;
    }

    @java.lang.Override
    public cn.my.app.grpc.download.DownloadResponse getDefaultInstanceForType() {
      return cn.my.app.grpc.download.DownloadResponse.getDefaultInstance();
    }

    @java.lang.Override
    public cn.my.app.grpc.download.DownloadResponse build() {
      cn.my.app.grpc.download.DownloadResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.my.app.grpc.download.DownloadResponse buildPartial() {
      cn.my.app.grpc.download.DownloadResponse result = new cn.my.app.grpc.download.DownloadResponse(this);
      result.name_ = name_;
      if (chunkBuilder_ == null) {
        result.chunk_ = chunk_;
      } else {
        result.chunk_ = chunkBuilder_.build();
      }
      if (downloadStatusBuilder_ == null) {
        result.downloadStatus_ = downloadStatus_;
      } else {
        result.downloadStatus_ = downloadStatusBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof cn.my.app.grpc.download.DownloadResponse) {
        return mergeFrom((cn.my.app.grpc.download.DownloadResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.my.app.grpc.download.DownloadResponse other) {
      if (other == cn.my.app.grpc.download.DownloadResponse.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.hasChunk()) {
        mergeChunk(other.getChunk());
      }
      if (other.hasDownloadStatus()) {
        mergeDownloadStatus(other.getDownloadStatus());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      cn.my.app.grpc.download.DownloadResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.my.app.grpc.download.DownloadResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     * file name with ext
     * </pre>
     *
     * <code>string name = 1;</code>
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * file name with ext
     * </pre>
     *
     * <code>string name = 1;</code>
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * file name with ext
     * </pre>
     *
     * <code>string name = 1;</code>
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * file name with ext
     * </pre>
     *
     * <code>string name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * file name with ext
     * </pre>
     *
     * <code>string name = 1;</code>
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private cn.my.app.grpc.download.Chunk chunk_;
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.my.app.grpc.download.Chunk, cn.my.app.grpc.download.Chunk.Builder, cn.my.app.grpc.download.ChunkOrBuilder> chunkBuilder_;
    /**
     * <code>.download.Chunk chunk = 2;</code>
     * @return Whether the chunk field is set.
     */
    public boolean hasChunk() {
      return chunkBuilder_ != null || chunk_ != null;
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     * @return The chunk.
     */
    public cn.my.app.grpc.download.Chunk getChunk() {
      if (chunkBuilder_ == null) {
        return chunk_ == null ? cn.my.app.grpc.download.Chunk.getDefaultInstance() : chunk_;
      } else {
        return chunkBuilder_.getMessage();
      }
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public Builder setChunk(cn.my.app.grpc.download.Chunk value) {
      if (chunkBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        chunk_ = value;
        onChanged();
      } else {
        chunkBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public Builder setChunk(
        cn.my.app.grpc.download.Chunk.Builder builderForValue) {
      if (chunkBuilder_ == null) {
        chunk_ = builderForValue.build();
        onChanged();
      } else {
        chunkBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public Builder mergeChunk(cn.my.app.grpc.download.Chunk value) {
      if (chunkBuilder_ == null) {
        if (chunk_ != null) {
          chunk_ =
            cn.my.app.grpc.download.Chunk.newBuilder(chunk_).mergeFrom(value).buildPartial();
        } else {
          chunk_ = value;
        }
        onChanged();
      } else {
        chunkBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public Builder clearChunk() {
      if (chunkBuilder_ == null) {
        chunk_ = null;
        onChanged();
      } else {
        chunk_ = null;
        chunkBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public cn.my.app.grpc.download.Chunk.Builder getChunkBuilder() {
      
      onChanged();
      return getChunkFieldBuilder().getBuilder();
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    public cn.my.app.grpc.download.ChunkOrBuilder getChunkOrBuilder() {
      if (chunkBuilder_ != null) {
        return chunkBuilder_.getMessageOrBuilder();
      } else {
        return chunk_ == null ?
            cn.my.app.grpc.download.Chunk.getDefaultInstance() : chunk_;
      }
    }
    /**
     * <code>.download.Chunk chunk = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.my.app.grpc.download.Chunk, cn.my.app.grpc.download.Chunk.Builder, cn.my.app.grpc.download.ChunkOrBuilder> 
        getChunkFieldBuilder() {
      if (chunkBuilder_ == null) {
        chunkBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            cn.my.app.grpc.download.Chunk, cn.my.app.grpc.download.Chunk.Builder, cn.my.app.grpc.download.ChunkOrBuilder>(
                getChunk(),
                getParentForChildren(),
                isClean());
        chunk_ = null;
      }
      return chunkBuilder_;
    }

    private cn.my.app.grpc.download.DownloadStatus downloadStatus_;
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.my.app.grpc.download.DownloadStatus, cn.my.app.grpc.download.DownloadStatus.Builder, cn.my.app.grpc.download.DownloadStatusOrBuilder> downloadStatusBuilder_;
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     * @return Whether the downloadStatus field is set.
     */
    public boolean hasDownloadStatus() {
      return downloadStatusBuilder_ != null || downloadStatus_ != null;
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     * @return The downloadStatus.
     */
    public cn.my.app.grpc.download.DownloadStatus getDownloadStatus() {
      if (downloadStatusBuilder_ == null) {
        return downloadStatus_ == null ? cn.my.app.grpc.download.DownloadStatus.getDefaultInstance() : downloadStatus_;
      } else {
        return downloadStatusBuilder_.getMessage();
      }
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public Builder setDownloadStatus(cn.my.app.grpc.download.DownloadStatus value) {
      if (downloadStatusBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        downloadStatus_ = value;
        onChanged();
      } else {
        downloadStatusBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public Builder setDownloadStatus(
        cn.my.app.grpc.download.DownloadStatus.Builder builderForValue) {
      if (downloadStatusBuilder_ == null) {
        downloadStatus_ = builderForValue.build();
        onChanged();
      } else {
        downloadStatusBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public Builder mergeDownloadStatus(cn.my.app.grpc.download.DownloadStatus value) {
      if (downloadStatusBuilder_ == null) {
        if (downloadStatus_ != null) {
          downloadStatus_ =
            cn.my.app.grpc.download.DownloadStatus.newBuilder(downloadStatus_).mergeFrom(value).buildPartial();
        } else {
          downloadStatus_ = value;
        }
        onChanged();
      } else {
        downloadStatusBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public Builder clearDownloadStatus() {
      if (downloadStatusBuilder_ == null) {
        downloadStatus_ = null;
        onChanged();
      } else {
        downloadStatus_ = null;
        downloadStatusBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public cn.my.app.grpc.download.DownloadStatus.Builder getDownloadStatusBuilder() {
      
      onChanged();
      return getDownloadStatusFieldBuilder().getBuilder();
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    public cn.my.app.grpc.download.DownloadStatusOrBuilder getDownloadStatusOrBuilder() {
      if (downloadStatusBuilder_ != null) {
        return downloadStatusBuilder_.getMessageOrBuilder();
      } else {
        return downloadStatus_ == null ?
            cn.my.app.grpc.download.DownloadStatus.getDefaultInstance() : downloadStatus_;
      }
    }
    /**
     * <code>.download.DownloadStatus downloadStatus = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.my.app.grpc.download.DownloadStatus, cn.my.app.grpc.download.DownloadStatus.Builder, cn.my.app.grpc.download.DownloadStatusOrBuilder> 
        getDownloadStatusFieldBuilder() {
      if (downloadStatusBuilder_ == null) {
        downloadStatusBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            cn.my.app.grpc.download.DownloadStatus, cn.my.app.grpc.download.DownloadStatus.Builder, cn.my.app.grpc.download.DownloadStatusOrBuilder>(
                getDownloadStatus(),
                getParentForChildren(),
                isClean());
        downloadStatus_ = null;
      }
      return downloadStatusBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:download.DownloadResponse)
  }

  // @@protoc_insertion_point(class_scope:download.DownloadResponse)
  private static final cn.my.app.grpc.download.DownloadResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.my.app.grpc.download.DownloadResponse();
  }

  public static cn.my.app.grpc.download.DownloadResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DownloadResponse>
      PARSER = new com.google.protobuf.AbstractParser<DownloadResponse>() {
    @java.lang.Override
    public DownloadResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new DownloadResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<DownloadResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DownloadResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.my.app.grpc.download.DownloadResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

