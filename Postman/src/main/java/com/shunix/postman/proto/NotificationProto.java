// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: notification.proto

package com.shunix.postman.proto;

public final class NotificationProto {
  private NotificationProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface NotificationMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.shunix.postman.proto.NotificationMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional bytes bytes_icon = 1;</code>
     */
    boolean hasBytesIcon();
    /**
     * <code>optional bytes bytes_icon = 1;</code>
     */
    com.google.protobuf.ByteString getBytesIcon();

    /**
     * <code>optional string str_package_name = 2;</code>
     */
    boolean hasStrPackageName();
    /**
     * <code>optional string str_package_name = 2;</code>
     */
    java.lang.String getStrPackageName();
    /**
     * <code>optional string str_package_name = 2;</code>
     */
    com.google.protobuf.ByteString
        getStrPackageNameBytes();

    /**
     * <code>optional uint64 uint64_timestamp = 3;</code>
     */
    boolean hasUint64Timestamp();
    /**
     * <code>optional uint64 uint64_timestamp = 3;</code>
     */
    long getUint64Timestamp();

    /**
     * <code>optional string str_title = 4;</code>
     */
    boolean hasStrTitle();
    /**
     * <code>optional string str_title = 4;</code>
     */
    java.lang.String getStrTitle();
    /**
     * <code>optional string str_title = 4;</code>
     */
    com.google.protobuf.ByteString
        getStrTitleBytes();

    /**
     * <code>optional string str_content = 5;</code>
     */
    boolean hasStrContent();
    /**
     * <code>optional string str_content = 5;</code>
     */
    java.lang.String getStrContent();
    /**
     * <code>optional string str_content = 5;</code>
     */
    com.google.protobuf.ByteString
        getStrContentBytes();
  }
  /**
   * Protobuf type {@code com.shunix.postman.proto.NotificationMessage}
   */
  public static final class NotificationMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:com.shunix.postman.proto.NotificationMessage)
      NotificationMessageOrBuilder {
    // Use NotificationMessage.newBuilder() to construct.
    private NotificationMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private NotificationMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final NotificationMessage defaultInstance;
    public static NotificationMessage getDefaultInstance() {
      return defaultInstance;
    }

    public NotificationMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private NotificationMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              bytesIcon_ = input.readBytes();
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              strPackageName_ = bs;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              uint64Timestamp_ = input.readUInt64();
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000008;
              strTitle_ = bs;
              break;
            }
            case 42: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000010;
              strContent_ = bs;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.shunix.postman.proto.NotificationProto.internal_static_com_shunix_postman_proto_NotificationMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.shunix.postman.proto.NotificationProto.internal_static_com_shunix_postman_proto_NotificationMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.shunix.postman.proto.NotificationProto.NotificationMessage.class, com.shunix.postman.proto.NotificationProto.NotificationMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<NotificationMessage> PARSER =
        new com.google.protobuf.AbstractParser<NotificationMessage>() {
      public NotificationMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new NotificationMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<NotificationMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int BYTES_ICON_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString bytesIcon_;
    /**
     * <code>optional bytes bytes_icon = 1;</code>
     */
    public boolean hasBytesIcon() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional bytes bytes_icon = 1;</code>
     */
    public com.google.protobuf.ByteString getBytesIcon() {
      return bytesIcon_;
    }

    public static final int STR_PACKAGE_NAME_FIELD_NUMBER = 2;
    private java.lang.Object strPackageName_;
    /**
     * <code>optional string str_package_name = 2;</code>
     */
    public boolean hasStrPackageName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional string str_package_name = 2;</code>
     */
    public java.lang.String getStrPackageName() {
      java.lang.Object ref = strPackageName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          strPackageName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string str_package_name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getStrPackageNameBytes() {
      java.lang.Object ref = strPackageName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        strPackageName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int UINT64_TIMESTAMP_FIELD_NUMBER = 3;
    private long uint64Timestamp_;
    /**
     * <code>optional uint64 uint64_timestamp = 3;</code>
     */
    public boolean hasUint64Timestamp() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional uint64 uint64_timestamp = 3;</code>
     */
    public long getUint64Timestamp() {
      return uint64Timestamp_;
    }

    public static final int STR_TITLE_FIELD_NUMBER = 4;
    private java.lang.Object strTitle_;
    /**
     * <code>optional string str_title = 4;</code>
     */
    public boolean hasStrTitle() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>optional string str_title = 4;</code>
     */
    public java.lang.String getStrTitle() {
      java.lang.Object ref = strTitle_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          strTitle_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string str_title = 4;</code>
     */
    public com.google.protobuf.ByteString
        getStrTitleBytes() {
      java.lang.Object ref = strTitle_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        strTitle_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STR_CONTENT_FIELD_NUMBER = 5;
    private java.lang.Object strContent_;
    /**
     * <code>optional string str_content = 5;</code>
     */
    public boolean hasStrContent() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional string str_content = 5;</code>
     */
    public java.lang.String getStrContent() {
      java.lang.Object ref = strContent_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          strContent_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string str_content = 5;</code>
     */
    public com.google.protobuf.ByteString
        getStrContentBytes() {
      java.lang.Object ref = strContent_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        strContent_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      bytesIcon_ = com.google.protobuf.ByteString.EMPTY;
      strPackageName_ = "";
      uint64Timestamp_ = 0L;
      strTitle_ = "";
      strContent_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, bytesIcon_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getStrPackageNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeUInt64(3, uint64Timestamp_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(4, getStrTitleBytes());
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeBytes(5, getStrContentBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, bytesIcon_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getStrPackageNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(3, uint64Timestamp_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, getStrTitleBytes());
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(5, getStrContentBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.shunix.postman.proto.NotificationProto.NotificationMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.shunix.postman.proto.NotificationProto.NotificationMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.shunix.postman.proto.NotificationMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.shunix.postman.proto.NotificationMessage)
        com.shunix.postman.proto.NotificationProto.NotificationMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.shunix.postman.proto.NotificationProto.internal_static_com_shunix_postman_proto_NotificationMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.shunix.postman.proto.NotificationProto.internal_static_com_shunix_postman_proto_NotificationMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.shunix.postman.proto.NotificationProto.NotificationMessage.class, com.shunix.postman.proto.NotificationProto.NotificationMessage.Builder.class);
      }

      // Construct using com.shunix.postman.proto.NotificationProto.NotificationMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        bytesIcon_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        strPackageName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        uint64Timestamp_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000004);
        strTitle_ = "";
        bitField0_ = (bitField0_ & ~0x00000008);
        strContent_ = "";
        bitField0_ = (bitField0_ & ~0x00000010);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.shunix.postman.proto.NotificationProto.internal_static_com_shunix_postman_proto_NotificationMessage_descriptor;
      }

      public com.shunix.postman.proto.NotificationProto.NotificationMessage getDefaultInstanceForType() {
        return com.shunix.postman.proto.NotificationProto.NotificationMessage.getDefaultInstance();
      }

      public com.shunix.postman.proto.NotificationProto.NotificationMessage build() {
        com.shunix.postman.proto.NotificationProto.NotificationMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.shunix.postman.proto.NotificationProto.NotificationMessage buildPartial() {
        com.shunix.postman.proto.NotificationProto.NotificationMessage result = new com.shunix.postman.proto.NotificationProto.NotificationMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.bytesIcon_ = bytesIcon_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.strPackageName_ = strPackageName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.uint64Timestamp_ = uint64Timestamp_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.strTitle_ = strTitle_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000010;
        }
        result.strContent_ = strContent_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.shunix.postman.proto.NotificationProto.NotificationMessage) {
          return mergeFrom((com.shunix.postman.proto.NotificationProto.NotificationMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.shunix.postman.proto.NotificationProto.NotificationMessage other) {
        if (other == com.shunix.postman.proto.NotificationProto.NotificationMessage.getDefaultInstance()) return this;
        if (other.hasBytesIcon()) {
          setBytesIcon(other.getBytesIcon());
        }
        if (other.hasStrPackageName()) {
          bitField0_ |= 0x00000002;
          strPackageName_ = other.strPackageName_;
          onChanged();
        }
        if (other.hasUint64Timestamp()) {
          setUint64Timestamp(other.getUint64Timestamp());
        }
        if (other.hasStrTitle()) {
          bitField0_ |= 0x00000008;
          strTitle_ = other.strTitle_;
          onChanged();
        }
        if (other.hasStrContent()) {
          bitField0_ |= 0x00000010;
          strContent_ = other.strContent_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.shunix.postman.proto.NotificationProto.NotificationMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.shunix.postman.proto.NotificationProto.NotificationMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString bytesIcon_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes bytes_icon = 1;</code>
       */
      public boolean hasBytesIcon() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional bytes bytes_icon = 1;</code>
       */
      public com.google.protobuf.ByteString getBytesIcon() {
        return bytesIcon_;
      }
      /**
       * <code>optional bytes bytes_icon = 1;</code>
       */
      public Builder setBytesIcon(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        bytesIcon_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes bytes_icon = 1;</code>
       */
      public Builder clearBytesIcon() {
        bitField0_ = (bitField0_ & ~0x00000001);
        bytesIcon_ = getDefaultInstance().getBytesIcon();
        onChanged();
        return this;
      }

      private java.lang.Object strPackageName_ = "";
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public boolean hasStrPackageName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public java.lang.String getStrPackageName() {
        java.lang.Object ref = strPackageName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            strPackageName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public com.google.protobuf.ByteString
          getStrPackageNameBytes() {
        java.lang.Object ref = strPackageName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          strPackageName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public Builder setStrPackageName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        strPackageName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public Builder clearStrPackageName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        strPackageName_ = getDefaultInstance().getStrPackageName();
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_package_name = 2;</code>
       */
      public Builder setStrPackageNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        strPackageName_ = value;
        onChanged();
        return this;
      }

      private long uint64Timestamp_ ;
      /**
       * <code>optional uint64 uint64_timestamp = 3;</code>
       */
      public boolean hasUint64Timestamp() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional uint64 uint64_timestamp = 3;</code>
       */
      public long getUint64Timestamp() {
        return uint64Timestamp_;
      }
      /**
       * <code>optional uint64 uint64_timestamp = 3;</code>
       */
      public Builder setUint64Timestamp(long value) {
        bitField0_ |= 0x00000004;
        uint64Timestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional uint64 uint64_timestamp = 3;</code>
       */
      public Builder clearUint64Timestamp() {
        bitField0_ = (bitField0_ & ~0x00000004);
        uint64Timestamp_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object strTitle_ = "";
      /**
       * <code>optional string str_title = 4;</code>
       */
      public boolean hasStrTitle() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>optional string str_title = 4;</code>
       */
      public java.lang.String getStrTitle() {
        java.lang.Object ref = strTitle_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            strTitle_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string str_title = 4;</code>
       */
      public com.google.protobuf.ByteString
          getStrTitleBytes() {
        java.lang.Object ref = strTitle_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          strTitle_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string str_title = 4;</code>
       */
      public Builder setStrTitle(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        strTitle_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_title = 4;</code>
       */
      public Builder clearStrTitle() {
        bitField0_ = (bitField0_ & ~0x00000008);
        strTitle_ = getDefaultInstance().getStrTitle();
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_title = 4;</code>
       */
      public Builder setStrTitleBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        strTitle_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object strContent_ = "";
      /**
       * <code>optional string str_content = 5;</code>
       */
      public boolean hasStrContent() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>optional string str_content = 5;</code>
       */
      public java.lang.String getStrContent() {
        java.lang.Object ref = strContent_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            strContent_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string str_content = 5;</code>
       */
      public com.google.protobuf.ByteString
          getStrContentBytes() {
        java.lang.Object ref = strContent_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          strContent_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string str_content = 5;</code>
       */
      public Builder setStrContent(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
        strContent_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_content = 5;</code>
       */
      public Builder clearStrContent() {
        bitField0_ = (bitField0_ & ~0x00000010);
        strContent_ = getDefaultInstance().getStrContent();
        onChanged();
        return this;
      }
      /**
       * <code>optional string str_content = 5;</code>
       */
      public Builder setStrContentBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
        strContent_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.shunix.postman.proto.NotificationMessage)
    }

    static {
      defaultInstance = new NotificationMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.shunix.postman.proto.NotificationMessage)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_shunix_postman_proto_NotificationMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_shunix_postman_proto_NotificationMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022notification.proto\022\030com.shunix.postman" +
      ".proto\"\205\001\n\023NotificationMessage\022\022\n\nbytes_" +
      "icon\030\001 \001(\014\022\030\n\020str_package_name\030\002 \001(\t\022\030\n\020" +
      "uint64_timestamp\030\003 \001(\004\022\021\n\tstr_title\030\004 \001(" +
      "\t\022\023\n\013str_content\030\005 \001(\tB-\n\030com.shunix.pos" +
      "tman.protoB\021NotificationProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_shunix_postman_proto_NotificationMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_shunix_postman_proto_NotificationMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_shunix_postman_proto_NotificationMessage_descriptor,
        new java.lang.String[] { "BytesIcon", "StrPackageName", "Uint64Timestamp", "StrTitle", "StrContent", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}