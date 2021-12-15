package ${params.packageName}.domain;

import ${params.packageName}.validation.isUnique.Unique;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.DbArray;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

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
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.${params.table}")
public class ${camelName} extends Model {
  @Id
  public Long id;
  <#list fields as field, options>

  /**
  * ${options.comment}
  */
  <#if options.character_maximum_length?exists>
  @Size(min = 0, max = ${options.character_maximum_length}, message = "${options.comment}长度不正确")
  </#if>
  <#if options.data_type == "numeric">
  @Digits(integer = ${options.numeric_precision}, fraction = ${options.numeric_scale}, message = "${options.comment}长度不正确")
  </#if>
  <#if options.is_nullable == "NO" && Commons.SQLTypeToJavaType(options.udt_name) != "Integer" && options.data_type != "bigint">
  @NotNull(message = "${options.comment}不能是空")
  <#if (options.data_type != "ARRAY" || options.data_type == "bigint") && Commons.SQLTypeToJavaType(options.udt_name) != "Boolean" && Commons.SQLTypeToJavaType(options.udt_name) != "BigDecimal">
  @NotBlank(message = "${options.comment}不能是空")
  @NotEmpty(message = "${options.comment}不能是空")
  </#if>
  <#elseif options.is_nullable == "NO">
  @NotNull(message = "${options.comment}不能是空")
  </#if>
  <#if field == "email" || field == "mail">
  @Email(message = "邮箱格式不正确")
  </#if>
  <#if field == "url" || field == "uri" || field == "link">
  @URL(message = "链接格式不正确")
  </#if>
  <#if options.index == "UNIQUE">
  @Unique(table = "public.${params.table}", column = "${field}", message = "${options.comment}已经存在", groups = {Uni.class})
  </#if>
  <#if (options.data_type == "ARRAY" || options.data_type == "numeric" || options.data_type == "bigint" || options.data_type = "smallint") && options.column_default?exists == true>
  <#if Commons.SQLTypeToJavaType(options.udt_name) == "Long">
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = ${options.column_default}L;
  <#elseif Commons.SQLTypeToJavaType(options.udt_name) == "BigDecimal">
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = new BigDecimal('${options.column_default}');
  <#elseif Commons.SQLTypeToJavaType(options.udt_name) == "Boolean">
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = ${options.column_default};
  <#else>
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field} = ${options.column_default};
  </#if>
  <#else>
  <#if options.data_type == "ARRAY">
  @DbArray
  public List<String> ${field} = new ArrayList<>();
  <#else>
  public ${Commons.SQLTypeToJavaType(options.udt_name)} ${field};
  </#if>
  </#if>
  </#list>

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, ${camelName}> find = new Finder<>(${camelName}.class);

  public interface Uni{};
}


