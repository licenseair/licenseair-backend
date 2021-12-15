<#include "../layout/header.ftl">
<div class="jumbotron">
  <div class="row">

    <div class="col-md-12 order-md-1">
      <form class="needs-validation" method="post" action="/post" enctype="application/x-www-form-urlencoded">
        <div class="card">
          <div class="card-body">
            <div class="row">
              <div class="col">
                <div class="form-group">
                  <label for="packageName">设置包名称</label>
                  <input type="text" class="form-control" required name="packageName" id="packageName"  value="com.licenseair.backend">
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <div class="form-group">
                  <label for="author">设置作者</label>
                  <input type="text" class="form-control" required name="author" id="author"  value="programschool.com">
                </div>
              </div>
            </div>
          </div>
          <div class="card-body">
            <h5 class="card-title">生成选项</h5>
            <div class="row">
              <div class="col col-sm">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="controller" value="controller" id="controller" checked>
                  <label class="custom-control-label" for="controller">Controller</label>
                </div>
                <p>控制器</p>
              </div>
              <div class="col col-sm">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="entity" value="entity" id="entity" checked>
                  <label class="custom-control-label" for="entity">Entity</label>
                </div>
                <p>数据表实体</p>
              </div>
              <div class="col col-sm">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="entityModel" value="entityModel" id="entityModel" checked>
                  <label class="custom-control-label" for="entityModel">EntityModel</label>
                </div>
                <p>NullEntity</p>
              </div>
              <div class="col col-sm">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="service" value="service" id="service" checked>
                  <label class="custom-control-label" for="service">Service</label>
                </div>
                <p>CRUD服务</p>
              </div>
              <div class="col col-sm">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="frontend" value="frontend" id="frontend" checked>
                  <label class="custom-control-label" for="frontend">生成Angular代码</label>
                </div>
              </div>
            </div>
          </div>
          <div class="card-body">
          <#list tableList as table>
            <div class="row">
              <div class="col">
                <div class="custom-control custom-checkbox">
                  <input type="checkbox" class="custom-control-input" name="tables[]" value="${table}" id="${table}">
                  <label class="custom-control-label" for="${table}">${table}</label>
                </div>
              </div>
            </div>
          </#list>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col">
                <button class="btn btn-primary" type="button" onclick="allCheck()">全选</button>
              </div>
            </div>
          </div>
        </div>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="submit">生成代码</button>
      </form>
    </div>

  </div>
</div>

<script>
  function allCheck() {
    document.querySelectorAll("input[name='tables[]']").forEach(input => {
      input.setAttribute("checked", "checked");
    })
  }
</script>

<#include "../layout/footer.ftl">
