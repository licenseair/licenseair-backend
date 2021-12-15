import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {CityModel} from '@app/models/city.model';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CityForm {
  form = new FormGroup({});
  model: CityModel = new CityModel;
  options: FormlyFormOptions;

  fields: FormlyFieldConfig[] = [
    {
      key: 'province',
      type: 'input',
      templateOptions: {
        label: '',
        type: 'text',
        required: false,
        maxLength: 64,
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
      key: 'city',
      type: 'input',
      templateOptions: {
        label: '',
        type: 'text',
        required: false,
        maxLength: 64,
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
      key: 'join_name',
      type: 'input',
      templateOptions: {
        label: '',
        type: 'text',
        required: false,
        maxLength: 64,
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
