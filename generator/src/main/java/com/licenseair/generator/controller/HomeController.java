package com.licenseair.generator.controller;

import java.io.*;
import java.sql.Statement;

import com.google.common.base.CaseFormat;
import com.licenseair.generator.commons.Commons;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import freemarker.template.*;

@Controller
@RequestMapping("/")
class HomeController {
  private Connection psql;
  private Statement stmt;

  @PostConstruct
  public void init() {
    String databaseURL = "jdbc:postgresql://192.168.50.110:5432/licenseair?serverTimezone=PRC&stringtype=unspecified&user=licenseair&password=123456";
    try {
      psql = DriverManager.getConnection(databaseURL);
      if (psql != null) {
        stmt = psql.createStatement();
      }
      // if (psql != null) {
      //   System.out.println("Connected to the database");
      //   // String sql = psql.nativeSQL("SELECT * FROM information_schema.tables WHERE table_schema = 'public';");
      //   String sql = psql.nativeSQL("SELECT * FROM information_schema.columns WHERE table_name ='vip_plan'");
      //   java.sql.Statement stmt = psql.createStatement();
      //   ResultSet rs = stmt.executeQuery(sql);
      //
      //   while (rs.next()) {
      //     System.out.println("==============: " + rs.getString("column_name"));
      //     for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
      //       String columnName = rs.getMetaData().getColumnName(i);
      //       System.out.println(columnName +": "+ rs.getString(columnName));
      //     }
      //     // System.out.println(rs.getString("data_type"));
      //     // System.out.println(rs.getString("column_default"));
      //   }
      //
      // }
    } catch (SQLException ex) {
      System.out.println("An error occurred. Maybe user/password is invalid");
      ex.printStackTrace();
    }
  }

  @GetMapping({"", "/index"})
  public String home(Model model) throws SQLException {
    String showTableSql = psql.nativeSQL("SELECT * FROM information_schema.tables WHERE table_schema = 'public' limit 100;");
    ResultSet rs = stmt.executeQuery(showTableSql);

    System.out.println(rs.getRow());

    List tableList = new ArrayList();
    while (rs.next()) {
      tableList.add(rs.getString("table_name"));
    }
    model.addAttribute("tableList", tableList);

    return "home/index";
  }

  @RequestMapping(value = "/post", method = RequestMethod.POST)
  public String post(Model model, HttpServletRequest request) throws SQLException {
    String error = "";
    // ArrayList<String> tables = new ArrayList<>();
    String[] tables = request.getParameterValues("tables[]");
    if(tables != null) {
      Arrays.stream(request.getParameterValues("tables[]")).forEach(table -> {
        try {
          Map params = new Hashtable();
          List<String> types = new ArrayList();
          if(request.getParameter("controller") != null) {
            types.add("controller");
          }
          if(request.getParameter("entity") != null) {
            types.add("entity");
          }
          if(request.getParameter("entityModel") != null) {
            types.add("entityModel");
          }
          if(request.getParameter("service") != null) {
            types.add("service");
          }
          params.put("packageName", request.getParameter("packageName"));
          params.put("author", request.getParameter("author"));

          for(String type : types) {
            generateCode(table, params, type, "backend");
          }

          List<String> tsTypes = new ArrayList(){{
            add("service");
            add("form");
            add("model");
          }};
          for(String type : tsTypes) {
            generateCode(table, params, type, "frontend");
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      });
    } else {
      error = "至少选择一个Table";
    }

    model.addAttribute("error", error);
    return "home/post";
  }

  private void generateCode(String table, Map params, String type, String tpType) throws SQLException, IOException {
    Map<String, String> comments = new Hashtable<>();
    try {
      String commentSql = this.psql.nativeSQL(String.format("SELECT * FROM pg_description WHERE  objoid = 'public.%s'::regclass", table));
      ResultSet commentRs = stmt.executeQuery(commentSql);
      while (commentRs.next()) {
        comments.put(
          commentRs.getString("objsubid"),
          commentRs.getString("description")
        );
      }
    } catch (Exception e) {
      System.out.println(e.getMessage() + "comment");
    }

    Map<String, String> indexs = new Hashtable<>();
    try {
      String indexSql = this.psql.nativeSQL(String.format("select tablename,indexname,tablespace,indexdef from pg_indexes where tablename ='%s'", table));
      ResultSet indexRs = stmt.executeQuery(indexSql);
      while (indexRs.next()) {
        String ci = indexRs.getString("indexdef");
        String[] index = ci.split(" ");
        indexs.put(
          index[index.length-1].replace("(", "").replace(")", ""),
          index[1]
        );
      }
    } catch (Exception e) {
      System.out.println(e.getMessage() + "comment");
    }


    String sql = this.psql.nativeSQL(String.format("SELECT * FROM information_schema.columns WHERE table_name ='%s'", table));
    ResultSet rs = stmt.executeQuery(sql);

    ArrayList<String> columns = new ArrayList<>();

    for (var i = 1; i < rs.getMetaData().getColumnCount(); i ++ ) {
      String name = rs.getMetaData().getColumnName(i);
      columns.add(name);
    }

    Map<String, Map> fields = new Hashtable<>();
    while (rs.next()) {
      Map<String, Object> field = new Hashtable<>();
      columns.forEach(name -> {
        try {
          // System.out.println(name + " ::: "+ rs.getString(name));
          field.put(name, rs.getString(name));

          if(rs.getString("ordinal_position") != null && comments.get(rs.getString("ordinal_position")) != null) {
            field.put("comment", comments.get(rs.getString("ordinal_position")));
          } else {
            field.put("comment", "");
          }

          if(rs.getString("column_name") != null && indexs.get(rs.getString("column_name")) != null) {
            field.put("index", indexs.get(rs.getString("column_name")));
          } else {
            field.put("index", "");
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      });
      if (
        rs.getString("column_name").equals("id") == false &&
        rs.getString("column_name").equals("created_at") == false &&
        rs.getString("column_name").equals("updated_at") == false
      ) {
        fields.put(rs.getString("column_name"), field);
      }
      // System.out.println("========================================================");
      // System.out.println("========================================================");
    }

    Hashtable<String, String> udtNames = new Hashtable<>();
    fields.forEach((field, options) -> {
      udtNames.put(
        Commons.SQLTypeWithImport(options.get("udt_name").toString()),
        Commons.SQLTypeWithImport(options.get("udt_name").toString())
      );
    });

    String camelName = CaseFormat.UPPER_UNDERSCORE.to(
      CaseFormat.UPPER_CAMEL, String.format("%s", table)
    );
    params.put("table", table);
    String camelNameVar = String.format(
      "%s%s",
      CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelName.substring(0, 1)),
      camelName.substring(1)
    );
    Map data = Map.of(
      "params", params,
      "fields", fields,
      "udtNames", udtNames,
      "tableComment", comments.get(0) != null ? comments.get(0) : camelName,
      "camelName", camelName,
      "urlString", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelName),
      "camelNameVar", camelNameVar,
      "Commons", new Commons()
    );

    try {
      // 结果写入文件
      // StringWriter stringWriter = new StringWriter();
      // String string = stringWriter.toString();
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
      String templateRoot = "generator/src/main/resources/templates";
      cfg.setDirectoryForTemplateLoading(new File(templateRoot));

      Template temp = null;
      String output = "";
      if (tpType.equals("backend")) {
        temp = cfg.getTemplate(String.format("tpl/%s.ftl", type));
        output = String.format("generator/output/%s", type);
      } else {
        temp = cfg.getTemplate(String.format("ts-tpl/%s.ftl", type));
        output = String.format("generator/output/ts-%s", type);
      }

      File theDir = new File(output);
      if (!theDir.exists()) {
        try{
          theDir.mkdir();
        }  catch(Exception e){
          System.out.println(e.getMessage());
        }
      }

      String fileSuffix = "";
      switch (type) {
        case "controller":
          fileSuffix = "Controller";
          break;
        case "service":
          fileSuffix = "Service";
          break;
        case "form":
          fileSuffix = "Form";
          break;
        case "entityModel":
          fileSuffix = "Model";
          break;
        case "model":
          fileSuffix = "Model";
          break;
      }

      PrintWriter printWriter = null;
      if (tpType.equals("backend")) {
        printWriter = new PrintWriter(String.format("%s/%s.java", output, camelName + fileSuffix));
      } else {
        String urlString = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelName);
        printWriter = new PrintWriter(String.format(
                "%s/%s.%s.ts",
                output,
                urlString,
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, fileSuffix)
        ));
      }

      temp.process(data, printWriter);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // String[] index = "CREATE UNIQUE INDEX user_pkey ON public.\"user\" USING btree (id)".split(" ");
    // System.out.println(index[1] + " ::: " + index[8]);
    // objsubid = ordinal_position;
    // tablename | indexname | tablespace |                               indexdef
    // -----------+-----------+------------+----------------------------------------------------------------------
    // user      | user_pkey |            | CREATE UNIQUE INDEX user_pkey ON public."user" USING btree (id)
    // user      | mobile    |            | CREATE UNIQUE INDEX mobile ON public."user" USING btree (mobile)
    // user      | username  |            | CREATE UNIQUE INDEX username ON public."user" USING btree (username)
    // user      | email     |            | CREATE UNIQUE INDEX email ON public."user" USING btree (email)

    // SELECT * FROM   pg_description WHERE  objoid = 'public.user'::regclass;
    // objoid | classoid | objsubid |  description
    // --------+----------+----------+---------------
    // 16673 |     1259 |        2 | 网站显示
    // 16673 |     1259 |        5 | 用户链接
    // 16673 |     1259 |        6 | 会员类型
    // 16673 |     1259 |        7 | 过期时间
    // 16673 |     1259 |        8 | 0未激活 1激活
    // 16673 |     1259 |        9 | 是否删除
    // 16673 |     1259 |       10 | 创建时间

  }

}
