// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: password.proto

package ie.gmit.ds;

public final class Password {
  private Password() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ie_gmit_ds_HashInput_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ie_gmit_ds_HashInput_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ie_gmit_ds_HashOutput_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ie_gmit_ds_HashOutput_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ie_gmit_ds_ValidateInput_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ie_gmit_ds_ValidateInput_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016password.proto\022\nie.gmit.ds\032\036google/pro" +
      "tobuf/wrappers.proto\032\033google/protobuf/em" +
      "pty.proto\"-\n\tHashInput\022\016\n\006userID\030\001 \001(\005\022\020" +
      "\n\010password\030\002 \001(\t\"B\n\nHashOutput\022\016\n\006userID" +
      "\030\001 \001(\005\022\026\n\016hashedPassword\030\002 \001(\014\022\014\n\004salt\030\003" +
      " \001(\014\"G\n\rValidateInput\022\020\n\010password\030\001 \001(\t\022" +
      "\026\n\016hashedPassword\030\002 \001(\014\022\014\n\004salt\030\003 \001(\0142\213\001" +
      "\n\017PasswordService\0225\n\004hash\022\025.ie.gmit.ds.H" +
      "ashInput\032\026.ie.gmit.ds.HashOutput\022A\n\010vali" +
      "date\022\031.ie.gmit.ds.ValidateInput\032\032.google" +
      ".protobuf.BoolValueB\016\n\nie.gmit.dsP\001b\006pro" +
      "to3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.WrappersProto.getDescriptor(),
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_ie_gmit_ds_HashInput_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ie_gmit_ds_HashInput_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ie_gmit_ds_HashInput_descriptor,
        new java.lang.String[] { "UserID", "Password", });
    internal_static_ie_gmit_ds_HashOutput_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ie_gmit_ds_HashOutput_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ie_gmit_ds_HashOutput_descriptor,
        new java.lang.String[] { "UserID", "HashedPassword", "Salt", });
    internal_static_ie_gmit_ds_ValidateInput_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ie_gmit_ds_ValidateInput_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ie_gmit_ds_ValidateInput_descriptor,
        new java.lang.String[] { "Password", "HashedPassword", "Salt", });
    com.google.protobuf.WrappersProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
