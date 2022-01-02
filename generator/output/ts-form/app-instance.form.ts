import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {AppInstanceModel} from '@app/models/app-instance.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppInstanceForm {
  form = new FormGroup({});
  model: AppInstanceModel = new AppInstanceModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'auto_save',
      type: 'input',
      templateOptions: {
        label: '是否删除',
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
      key: 'status',
      type: 'input',
      templateOptions: {
        label: '实例状态 Pending | Starting | Running ｜ Stopping',
        type: 'text',
        required: false,
        maxLength: 16,
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
      key: 'image_id',
      type: 'input',
      templateOptions: {
        label: '镜像id',
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
      key: 'application_id',
      type: 'input',
      templateOptions: {
        label: 'APPid',
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
      key: 'public_address',
      type: 'input',
      templateOptions: {
        label: '公有地址',
        type: 'text',
        required: false,
        maxLength: 15,
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
      key: 'origin_image_id',
      type: 'input',
      templateOptions: {
        label: '原有镜像id',
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
      key: 'instance_type',
      type: 'input',
      templateOptions: {
        label: '实例规格',
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
      key: 'hours',
      type: 'input',
      templateOptions: {
        label: '使用时长',
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
      key: 'password',
      type: 'input',
      templateOptions: {
        label: '实例id',
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
      key: 'private_address',
      type: 'input',
      templateOptions: {
        label: '私有地址',
        type: 'text',
        required: false,
        maxLength: 15,
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
      key: 'user_id',
      type: 'input',
      templateOptions: {
        label: '用户id',
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
      key: 'instance_id',
      type: 'input',
      templateOptions: {
        label: '实例id',
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
