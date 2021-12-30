import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {UserModel} from '@app/models/user.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserForm {
  form = new FormGroup({});
  model: UserModel = new UserModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'email',
      type: 'input',
      templateOptions: {
        label: '邮箱地址',
        type: 'text',
        required: true,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    {
      key: 'mobile',
      type: 'input',
      templateOptions: {
        label: '手机号',
        type: 'text',
        required: false,
        maxLength: 11,
        minLength: 1,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    {
      key: 'password',
      type: 'input',
      templateOptions: {
        label: '密码',
        type: 'text',
        required: false,
        maxLength: 60,
        minLength: 1,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    {
      key: 'domain',
      type: 'input',
      templateOptions: {
        label: '用户域名',
        type: 'text',
        required: true,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    {
      key: 'active',
      type: 'input',
      templateOptions: {
        label: '是否激活',
        type: 'text',
        required: false,
        minLength: 1,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
    {
      key: 'username',
      type: 'input',
      templateOptions: {
        label: '用户名',
        type: 'text',
        required: false,
        maxLength: 32,
        minLength: 1,
      },
      validators: {
        // pattern: {
        //   expression: (c) => !c.value || /(\d{1,3}\.){3}\d{1,3}/.test(c.value),
        //   message: (error, field: FormlyFieldConfig) => `"$\{field.formControl.value\}" is not a valid IP Address`,
        // },
      },
    },
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
