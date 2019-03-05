<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户注册</title>
    <meta charset="utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="Content/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <style type="text/css">
        .errorMessage li {
            list-style-type: none;
        }

        .errorMessage {
            padding: 0;
        }

        body {
            background: url("login.jpg") no-repeat center/cover;
        }

        .fun {
            padding-top: 50px;
        }
    </style>
</head>
  <body>
      <center>
          <h1>BP算法应用：鸢尾花种类判断</h1>
          <h2>用户注册</h2>
          <form action="registerAction.action" method="post">
              <table class="wwFormTable">
                  <div class="fun" style="width: 400px">
                      <div class="input-group">
                          <div class="input-group-addon"></div>
                          <input type="text" id="RegisterAction_username" name="username" class="form-control" placeholder="请输入用户名" />

                      </div>
                      <br />
                      <div class="input-group">
                          <div class="input-group-addon"></div>
                          <input type="password" id="RegisterAction_password" name="password" class="form-control" placeholder="请输入密码" />

                      </div>
                      <br />
                      <div class="input-group">
                          <div class="input-group-addon"></div>
                          <input type="password" id="RegisterAction_passwordConf" name="passwordConf" class="form-control" placeholder="请确认密码" />

                      </div>
                      <br />
                      <div>
                          <div class="input-group-addon"><input type="submit" id="RegisterAction_submit" name="submit" value="注册" class="form-control btn btn-success" />&nbsp;</div>
                          <input type="reset" class="form-control btn btn-success" value="重置" />
                      </div>
                      <br />
                      <p style="text-align: right"><a href="Login.jsp">返回登录</a></p>
                  </div>
                  <s:fielderror fieldName="username"></s:fielderror>
                  <s:fielderror fieldName="password"></s:fielderror>
                  <s:fielderror fieldName="passwordConf"></s:fielderror>
              </table>
          </form>
      </center>
  </body>
</html>