import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {InstanceImageModel} from '@app/models/instance-image.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InstanceImageForm {
  form = new FormGroup({});
  model: InstanceImageModel = new InstanceImageModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'busy',
      type: 'input',
      templateOptions: {
        label: '忙碌 false为可用',
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
        label: '',
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
      key: 'image_id',
      type: 'input',
      templateOptions: {
        label: '镜像 ID',
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
