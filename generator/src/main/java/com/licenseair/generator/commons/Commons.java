package com.licenseair.generator.commons;

public class Commons {
  public static String SQLTypeToJavaType(String udt_name) {
    String type = "";
    switch (udt_name) {
      case "int4":
      case "int2":
        type = "Integer";
        break;
      case "int8":
        type = "Long";
        break;
      case "float4":
        type = "Float";
        break;
      case "float8":
        type = "Double";
        break;
      case "varchar":
      case "bpchar":
      case "text":
        type = "String";
        break;
      case "bool":
      case "bit":
        type = "Boolean";
        break;
      case "cidr":
      case "inet":
      case "macaddr":
      case "varbit":
      case "json":
        type = "String";
        break;
      case "date":
        type = "Date";
        break;
      case "time":
      case "timetz":
        type = "Time";
        break;
      case "timestamp":
      case "timestamptz":
        type = "Timestamp";
        break;
      case "numeric":
        type = "BigDecimal";
        break;
      case "point":
        type = "PGpoint";
        break;
      case "lseg":
        type = "PGlseg";
        break;
      case "box":
        type = "PGbox";
        break;
      case "path":
        type = "PGpath";
        break;
      case "polygon":
        type = "PGpolygon";
        break;
      case "circle":
        type = "PGcircle";
        break;
    }
    return type;
  }

  public static String SQLTypeWithImport(String udt_name) {
    String toImport = "";
    switch (udt_name) {
      case "int4":
      case "int2":
        toImport = "java.lang.Integer";
        break;
      case "int8":
        toImport = "java.lang.Long";
        break;
      case "float4":
        toImport = "java.lang.Float";
        break;
      case "float8":
        toImport = "java.lang.Double";
        break;
      case "varchar":
      case "bpchar":
      case "text":
        toImport = "java.lang.String";
        break;
      case "bool":
      case "bit":
        toImport = "java.lang.Boolean";
        break;
      case "cidr":
      case "inet":
      case "macaddr":
      case "varbit":
        toImport = "java.lang.Object";
        break;
      case "date":
        toImport = "java.sql.Date";
        break;
      case "time":
      case "timetz":
        toImport = "java.sql.Time";
        break;
      case "timestamp":
      case "timestamptz":
        toImport = "java.sql.Timestamp";
        break;
      case "numeric":
        toImport = "java.math.BigDecimal";
        break;
      case "point":
        toImport = "org.postgresql.geometric.PGpoint";
        break;
      case "lseg":
        toImport = "org.postgresql.geometric.PGlseg";
        break;
      case "box":
        toImport = "org.postgresql.geometric.PGbox";
        break;
      case "path":
        toImport = "org.postgresql.geometric.PGpath";
        break;
      case "polygon":
        toImport = "org.postgresql.geometric.PGpolygon";
        break;
      case "circle":
        toImport = "org.postgresql.geometric.PGcircle";
        break;
    }
    return toImport;
  }
}
