// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: task.proto

package cn.shuangbofu.nocturne.protobuf;

public final class TaskProto {
  private TaskProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface SubmitTaskRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SubmitTaskRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>uint64 taskId = 1;</code>
     * @return The taskId.
     */
    long getTaskId();

    /**
     * <code>string content = 2;</code>
     * @return The content.
     */
    java.lang.String getContent();
    /**
     * <code>string content = 2;</code>
     * @return The bytes for content.
     */
    com.google.protobuf.ByteString
        getContentBytes();
  }
  /**
   * <pre>
   * 执行器注册
   * </pre>
   *
   * Protobuf type {@code SubmitTaskRequest}
   */
  public  static final class SubmitTaskRequest extends
      com.google.protobuf.GeneratedMessageLite<
          SubmitTaskRequest, SubmitTaskRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:SubmitTaskRequest)
      SubmitTaskRequestOrBuilder {
    private SubmitTaskRequest() {
      content_ = "";
    }
    public static final int TASKID_FIELD_NUMBER = 1;
    private long taskId_;
    /**
     * <code>uint64 taskId = 1;</code>
     * @return The taskId.
     */
    @java.lang.Override
    public long getTaskId() {
      return taskId_;
    }
    /**
     * <code>uint64 taskId = 1;</code>
     * @param value The taskId to set.
     */
    private void setTaskId(long value) {
      
      taskId_ = value;
    }
    /**
     * <code>uint64 taskId = 1;</code>
     */
    private void clearTaskId() {
      
      taskId_ = 0L;
    }

    public static final int CONTENT_FIELD_NUMBER = 2;
    private java.lang.String content_;
    /**
     * <code>string content = 2;</code>
     * @return The content.
     */
    @java.lang.Override
    public java.lang.String getContent() {
      return content_;
    }
    /**
     * <code>string content = 2;</code>
     * @return The bytes for content.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getContentBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(content_);
    }
    /**
     * <code>string content = 2;</code>
     * @param value The content to set.
     */
    private void setContent(
        java.lang.String value) {
      value.getClass();
  
      content_ = value;
    }
    /**
     * <code>string content = 2;</code>
     */
    private void clearContent() {
      
      content_ = getDefaultInstance().getContent();
    }
    /**
     * <code>string content = 2;</code>
     * @param value The bytes for content to set.
     */
    private void setContentBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      content_ = value.toStringUtf8();
      
    }

    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * <pre>
     * 执行器注册
     * </pre>
     *
     * Protobuf type {@code SubmitTaskRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:SubmitTaskRequest)
        cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequestOrBuilder {
      // Construct using cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>uint64 taskId = 1;</code>
       * @return The taskId.
       */
      @java.lang.Override
      public long getTaskId() {
        return instance.getTaskId();
      }
      /**
       * <code>uint64 taskId = 1;</code>
       * @param value The taskId to set.
       * @return This builder for chaining.
       */
      public Builder setTaskId(long value) {
        copyOnWrite();
        instance.setTaskId(value);
        return this;
      }
      /**
       * <code>uint64 taskId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearTaskId() {
        copyOnWrite();
        instance.clearTaskId();
        return this;
      }

      /**
       * <code>string content = 2;</code>
       * @return The content.
       */
      @java.lang.Override
      public java.lang.String getContent() {
        return instance.getContent();
      }
      /**
       * <code>string content = 2;</code>
       * @return The bytes for content.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getContentBytes() {
        return instance.getContentBytes();
      }
      /**
       * <code>string content = 2;</code>
       * @param value The content to set.
       * @return This builder for chaining.
       */
      public Builder setContent(
          java.lang.String value) {
        copyOnWrite();
        instance.setContent(value);
        return this;
      }
      /**
       * <code>string content = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearContent() {
        copyOnWrite();
        instance.clearContent();
        return this;
      }
      /**
       * <code>string content = 2;</code>
       * @param value The bytes for content to set.
       * @return This builder for chaining.
       */
      public Builder setContentBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setContentBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:SubmitTaskRequest)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "taskId_",
              "content_",
            };
            java.lang.String info =
                "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0003\u0002\u0208" +
                "";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest> parser = PARSER;
          if (parser == null) {
            synchronized (cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:SubmitTaskRequest)
    private static final cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest DEFAULT_INSTANCE;
    static {
      SubmitTaskRequest defaultInstance = new SubmitTaskRequest();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        SubmitTaskRequest.class, defaultInstance);
    }

    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<SubmitTaskRequest> PARSER;

    public static com.google.protobuf.Parser<SubmitTaskRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface SubmitTaskResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SubmitTaskResponse)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>bool accept = 1;</code>
     * @return The accept.
     */
    boolean getAccept();
  }
  /**
   * Protobuf type {@code SubmitTaskResponse}
   */
  public  static final class SubmitTaskResponse extends
      com.google.protobuf.GeneratedMessageLite<
          SubmitTaskResponse, SubmitTaskResponse.Builder> implements
      // @@protoc_insertion_point(message_implements:SubmitTaskResponse)
      SubmitTaskResponseOrBuilder {
    private SubmitTaskResponse() {
    }
    public static final int ACCEPT_FIELD_NUMBER = 1;
    private boolean accept_;
    /**
     * <code>bool accept = 1;</code>
     * @return The accept.
     */
    @java.lang.Override
    public boolean getAccept() {
      return accept_;
    }
    /**
     * <code>bool accept = 1;</code>
     * @param value The accept to set.
     */
    private void setAccept(boolean value) {
      
      accept_ = value;
    }
    /**
     * <code>bool accept = 1;</code>
     */
    private void clearAccept() {
      
      accept_ = false;
    }

    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code SubmitTaskResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse, Builder> implements
        // @@protoc_insertion_point(builder_implements:SubmitTaskResponse)
        cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponseOrBuilder {
      // Construct using cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>bool accept = 1;</code>
       * @return The accept.
       */
      @java.lang.Override
      public boolean getAccept() {
        return instance.getAccept();
      }
      /**
       * <code>bool accept = 1;</code>
       * @param value The accept to set.
       * @return This builder for chaining.
       */
      public Builder setAccept(boolean value) {
        copyOnWrite();
        instance.setAccept(value);
        return this;
      }
      /**
       * <code>bool accept = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearAccept() {
        copyOnWrite();
        instance.clearAccept();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:SubmitTaskResponse)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "accept_",
            };
            java.lang.String info =
                "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0007";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse> parser = PARSER;
          if (parser == null) {
            synchronized (cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:SubmitTaskResponse)
    private static final cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse DEFAULT_INSTANCE;
    static {
      SubmitTaskResponse defaultInstance = new SubmitTaskResponse();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        SubmitTaskResponse.class, defaultInstance);
    }

    public static cn.shuangbofu.nocturne.protobuf.TaskProto.SubmitTaskResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<SubmitTaskResponse> PARSER;

    public static com.google.protobuf.Parser<SubmitTaskResponse> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}