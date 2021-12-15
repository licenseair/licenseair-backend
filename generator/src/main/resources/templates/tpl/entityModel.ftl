package ${params.packageName}.domainModel;

import java.util.List;
import lombok.Data;

<#list fields as field, options>
<#if field == "url" || field == "uri" || field == "link">
import org.hibernate.validator.constraints.URL;
</#if>
</#list>
<#list udtNames as k, v>
<#if k != "">
import ${k};
</#if>
</#list>
import java.sql.Timestamp;

/**
* Created by ${params.author}
* ${tableComment}
*/
@Data()
public class ${camelName}Model {
  public Long id = null;
  <#list fields as field, options>

  /**
  * ${options.comment}
  */
  <#if (options.data_type == "ARRAY" || options.data_type == "numeric" || options.data_type == "bigint" || options.data_type = "smallint") && options.column_default?exists == true>
  <#if Commons.SQLTypeToJavaType(options.udt_name) == "Long">
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = null;
  <#elseif Commons.SQLTypeToJavaType(options.udt_name) == "BigDecimal" || Commons.SQLTypeToJavaType(options.udt_name) == "Boolean">
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = null;
  <#else>
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = null;
  </#if>
  <#else>
  <#if options.data_type == "ARRAY">
  public List<String> ${field} = null;
  <#else>
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = null;
  </#if>
  </#if>
  </#list>

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
