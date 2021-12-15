package ${params.packageName}.controller;

import ${params.packageName}.commons.model.*;
import ${params.packageName}.commons.util.HttpRequestException;
import ${params.packageName}.commons.util.HttpRequestFormException;
import ${params.packageName}.domain.${camelName};
import ${params.packageName}.domainModel.${camelName}Model;
import ${params.packageName}.service.${camelName}Service;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ${params.author}
 */
@RestController
@RequestMapping("/${urlString}")
public class ${camelName}Controller extends BaseController {
  @PostMapping("/create")
  private CreateResponse create(@RequestBody @Validated(${camelName}.Uni.class) ${camelName} ${camelNameVar}) throws HttpRequestFormException {
    ${camelName}Service ${camelNameVar}Service = new ${camelName}Service(AuthUser);
    try {
      return new CreateResponse(${camelNameVar}Service.create(${camelNameVar}));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  @PostMapping("/update")
  private UpdateResponse update(@RequestBody ${camelName}Model ${camelNameVar}) throws HttpRequestException, HttpRequestFormException {
    ${camelName}Service ${camelNameVar}Service = new ${camelName}Service(AuthUser);
    try {
      return new UpdateResponse(${camelNameVar}Service.update(${camelNameVar}));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/delete")
  private DeleteResponse delete(@RequestBody @Validated IDModel idModel) throws HttpRequestException {
    ${camelName}Service ${camelNameVar}Service = new ${camelName}Service(AuthUser);
    try {
      return new DeleteResponse(${camelNameVar}Service.delete(idModel));
    } catch (HttpRequestException e) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
  }

  @PostMapping("/get")
  private QueryResponse get(@RequestBody @Validated QueryRequest queryRequest) {
    ${camelName}Service ${camelNameVar}Service = new ${camelName}Service(AuthUser);
    DataResource dataResource = ${camelNameVar}Service.query(queryRequest);
    return new QueryResponse(dataResource);
  }

}
