import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {WalletLogModel} from '@app/models/wallet-log.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WalletLogForm {
  form = new FormGroup({});
  model: WalletLogModel = new WalletLogModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'user_id',
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
      key: 'money',
      type: 'input',
      templateOptions: {
        label: '资金变动',
        type: 'number',
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
      key: 'description',
      type: 'input',
      templateOptions: {
        label: '资金变动',
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
