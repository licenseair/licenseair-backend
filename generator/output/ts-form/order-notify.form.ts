import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {OrderNotifyModel} from '@app/models/order-notify.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderNotifyForm {
  form = new FormGroup({});
  model: OrderNotifyModel = new OrderNotifyModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'method',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'sign',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'trade_no',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'charset',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'auth_app_id',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'out_trade_no',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'timestamp',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'app_id',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'version',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'seller_id',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'total_amount',
      type: 'input',
      templateOptions: {
        label: '',
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
      key: 'sign_type',
      type: 'input',
      templateOptions: {
        label: '',
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
