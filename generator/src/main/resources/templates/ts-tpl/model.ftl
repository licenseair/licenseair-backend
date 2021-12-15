class ${camelName}Model {
  public id: number = 0;
  <#list fields as field, options>

  <#assign null = ''>
  <#if options.is_nullable == "YES"><#assign null = '?'></#if>
  // ${field} ${options.comment}
  <#if options.data_type == "numeric" || options.data_type == "smallint" || options.data_type == "bigint">
  public ${field}${null}: number = 0;
  <#elseif options.data_type == "boolean">
  public ${field}${null}: boolean = false;
  <#elseif options.data_type == "ARRAY">
  public ${field}${null}: string[] = [];
  <#elseif options.data_type == "json">
  public ${field}${null}: object = {};
  <#else>
  public ${field}${null}: string;
  </#if>
  </#list>

  public created_at: string;

  public updated_at: string;
}

const ${camelName}ModelFields = {
  id: "id",
  <#list fields as field, options>
  ${field}: "${field}",
  </#list>
  created_at: "created_at",
  updated_at: "updated_at"
};

export {${camelName}Model, ${camelName}ModelFields};
