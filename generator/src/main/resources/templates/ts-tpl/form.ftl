import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {${camelName}Model} from '@app/models/${urlString}.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ${camelName}Form {
  form = new FormGroup({});
  model: ${camelName}Model = new ${camelName}Model;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    <#list fields as field, options>
    <#if field == "deleted">
      <#continue>
    </#if>
    {
      key: '${field}',
      type: 'input',
      templateOptions: {
        <#if options.comment?exists>
        label: '${options.comment}',
        <#else>
        label: '${field}',
        </#if>
        <#if options.data_type == "numeric">
        type: 'number',
        <#else>
        type: 'text',
        </#if>
        <#if options.is_nullable == "NO">
        required: false,
        <#else>
        required: true,
        </#if>
        <#if options.is_nullable == "NO">
        <#if options.character_maximum_length?exists>
        maxLength: ${options.character_maximum_length},
        </#if>
        minLength: 1,
        </#if>
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    </#list>
  ];

  getFields = (columns: string[]) => {
    const selectFields: FormlyFieldConfig[] = [];
    columns.forEach(item => {
      this.fields.forEach(field => {
        if ( field.key === item ) {
          selectFields.push(field);
        }
      });
    });
    return selectFields;
  }

}
